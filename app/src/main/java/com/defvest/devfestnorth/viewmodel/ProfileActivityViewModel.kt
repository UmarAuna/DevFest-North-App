package com.defvest.devfestnorth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.net.Uri
import android.text.TextUtils
import androidx.work.*
import com.defvest.devfestnorth.activities.ProfileActivity
import com.defvest.devfestnorth.worker.UploadImageWorker
import javax.inject.Inject

class ProfileActivityViewModel @Inject constructor() : ViewModel() {
    var mImageUri: Uri? = null
    lateinit var mWorkManager: WorkManager
    var WORK_TAG = "status Tag"
    var status = MutableLiveData<WorkInfo>()
    lateinit var uploadImageRequest: OneTimeWorkRequest

    init {
        mWorkManager = WorkManager.getInstance()
    }

    /**
     * This request has no constraints. Therefore the task will b execute right away
     */
    fun uploadImage() {
        uploadImageRequest = OneTimeWorkRequest.Builder(UploadImageWorker::class.java)
                .setInputData(createInputDataForUri())
                .build()
        status = mWorkManager.getWorkInfoByIdLiveData(uploadImageRequest.id) as MutableLiveData<WorkInfo>
        mWorkManager.enqueue(uploadImageRequest)
    }

    /**
     * Get uploadImageRequest work status
     */
    fun getUploadWorkStatus(): LiveData<WorkInfo> {
        return status!!
    }

    private fun uriOrNull(uriString: String): Uri? {
        return if (!TextUtils.isEmpty(uriString)) {
            Uri.parse(uriString)
        } else null
    }

    /**
     * Setters
     */
    fun setImageUri(uri: String) {
        mImageUri = uriOrNull(uri)
    }

    /**
     * Getters
     */
    fun getImageUri(): Uri {
        return mImageUri!!
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     * @return Data which contains the Image Uri as a String
     */
    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        if (mImageUri != null) {
            builder.putString(ProfileActivity.KEY_IMAGE_URI, mImageUri.toString())
        }
        return builder.build()
    }
}