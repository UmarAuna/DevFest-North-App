package com.defvest.devfestnorth.activities

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.defvest.devfestnorth.R
import com.defvest.devfestnorth.R.id.*
import com.defvest.devfestnorth.hideProgress
import com.defvest.devfestnorth.models.User
import com.defvest.devfestnorth.showProgress
import com.defvest.devfestnorth.viewmodel.AppViewModelFactory
import com.defvest.devfestnorth.viewmodel.MainActivityViewModel
import com.defvest.devfestnorth.viewmodel.ProfileActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*
import javax.inject.Inject


class ProfileActivity : DaggerAppCompatActivity() {
    private val TAG = "SelectImageActivity"
    private val REQUEST_CODE_IMAGE = 100
    private val REQUEST_CODE_PERMISSIONS = 101

    private val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"
    private val MAX_NUMBER_REQUEST_PERMISSIONS = 2

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory
    private lateinit var profileActivityViewModel: ProfileActivityViewModel
    private lateinit var ref: DatabaseReference

    private val sPermissions = Arrays.asList(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private var mPermissionRequestCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //viewmodel
        profileActivityViewModel = ViewModelProviders.of(this).get(ProfileActivityViewModel::class.java)

        var currentUser = FirebaseAuth.getInstance().currentUser

        //Firebase Database
        ref = FirebaseDatabase.getInstance().reference.child("Profile").child(currentUser!!.uid)

        // Get permissions for your app once it launched
        requestPermissionsIfNecessary()

        fab_take_picture.setOnClickListener { view ->
            val chooseIntent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(chooseIntent, REQUEST_CODE_IMAGE)
        }

        profileActivityViewModel.getUploadWorkStatus().observe(this, android.arch.lifecycle.Observer {status ->

            if(status!!.state.isFinished){
                progressBar.hideProgress()
            }else progressBar.showProgress()
        })
    }

    /**
     * Request permissions twice - if the user denies twice then show a toast about how to update
     * the permission for storage. Also disable the button if we don't have access to pictures on
     * the device.
     */
    private fun requestPermissionsIfNecessary() {
        if (!checkAllPermissions()) {
            if (mPermissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                mPermissionRequestCount += 1
                ActivityCompat.requestPermissions(
                        this,
                        sPermissions.toTypedArray(),
                        REQUEST_CODE_PERMISSIONS)
            } else {
                Toast.makeText(this, "Set Permission in Settings",
                        Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkAllPermissions(): Boolean {
        var hasPermissions = true
        for (permission in sPermissions) {
            hasPermissions = hasPermissions and (ContextCompat.checkSelfPermission(
                    this, permission) == PackageManager.PERMISSION_GRANTED)
        }
        return hasPermissions
    }


    /** Permission Checking  */
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            requestPermissionsIfNecessary() // no-op if permissions are granted already.
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE -> handleImageRequestResult(data!!)
                else -> Log.d(TAG, "Unknown request code.")
            }
        } else {
            Log.e(TAG, String.format("Unexpected Result code %s", resultCode))
        }
    }


    /**
     * Get ImageUri from Result
     */
    private fun handleImageRequestResult(data: Intent) {
        var imageUri: Uri? = null
        if (data.clipData != null) {
            imageUri = data.clipData!!.getItemAt(0).uri
        } else if (data.data != null) {
            imageUri = data.data
        }

        if (imageUri == null) {
            Log.e(TAG, "Invalid input image Uri.")
            return
        }

        profileActivityViewModel.setImageUri(imageUri.toString())
        profileActivityViewModel.uploadImage()


    }


    override fun onStart() {
        super.onStart()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue<User>(User::class.java)
                userName.text = user!!.fullName
                email.text = user!!.email
                if(userImage != null){
                    Glide.with(userImage.context).load(user.imageUrl).into(userImage)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }
    companion object {
        @JvmField
        var KEY_IMAGE_URI = "key_image_uri"
    }
}
