package com.geektree0101.githubrepositoryexample.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Repository(
    val id: Int,
    val owner: User?,
    val fullName: String?,
    val description: String?,
    val private: Boolean,
    val fork: Boolean
) : Parcelable {

}