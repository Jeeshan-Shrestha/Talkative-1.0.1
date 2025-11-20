package com.example.talkative.model.customDataPassing

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class ProfileArgument(
    val avatar: String?,
    val bio: String?,
    val displayName: String?,
    val coverPhoto: String?
) : Parcelable
