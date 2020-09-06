package com.geektree0101.githubrepositoryexample.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class User(
    val login: String,
    val avatar_url: String
) : Parcelable {

}