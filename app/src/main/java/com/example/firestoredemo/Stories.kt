package com.example.firestoredemo

import com.google.firebase.Timestamp


data class Stories(
    val name: String = "",
    val desc: String = "",
    val image: String = "",
    val ts: Timestamp? = null,
    val post_type: Long = 0
)