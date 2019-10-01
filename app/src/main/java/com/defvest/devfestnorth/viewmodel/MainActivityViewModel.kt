package com.defvest.devfestnorth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.defvest.devfestnorth.activities.MainActivity
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import android.widget.Toast
import com.defvest.devfestnorth.models.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject


class MainActivityViewModel @Inject constructor() : ViewModel() {

    val user = MutableLiveData<User>()
    lateinit var  database: FirebaseDatabase

    init {
        database = FirebaseDatabase.getInstance()
    }
    fun setCurrentUser(firebaseUser: FirebaseUser) {

    }

    fun getCurrentUser():MutableLiveData<User>{
        return  user
    }

    fun saveUserInfoToFirebase(firebaseUser: FirebaseUser){
        var mRef = database.getReference("Profile")
        mRef.child(firebaseUser.uid).setValue(User(firebaseUser.uid,firebaseUser.displayName!!, firebaseUser.email!!, firebaseUser.photoUrl.toString()))
    }

}