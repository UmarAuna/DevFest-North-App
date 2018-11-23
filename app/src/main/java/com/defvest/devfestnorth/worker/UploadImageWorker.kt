package com.defvest.devfestnorth.worker

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.defvest.devfestnorth.activities.ProfileActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.FirebaseStorage

class UploadImageWorker(context: Context, params: WorkerParameters) : Worker(context, params) {


    val TAG = UploadImageWorker::class.java.simpleName

    override fun doWork(): Result {
        var mStorage = FirebaseStorage.getInstance().reference

        val resourceUri = inputData.getString(ProfileActivity.KEY_IMAGE_URI)

        if (TextUtils.isEmpty(resourceUri)) {
            Log.e(TAG, "Invalid input uri")
            throw IllegalArgumentException("Invalid input uri")
        }

        val photoRef = mStorage.child("photo").child(Uri.parse(resourceUri).lastPathSegment).putFile(Uri.parse(resourceUri))

        return Result.SUCCESS

        }


}