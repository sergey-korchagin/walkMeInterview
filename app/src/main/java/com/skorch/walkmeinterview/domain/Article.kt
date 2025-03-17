package com.skorch.walkmeinterview.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName


@Parcelize
data class Article(
    val id: Int,

    @SerializedName("headline")
    val title: String,

    @SerializedName("body")
    val content: String,

    @SerializedName("article_uri")
    val url: String,

    val author: String
) : Parcelable