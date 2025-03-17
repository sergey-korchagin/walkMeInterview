package com.skorch.walkmeinterview.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "headline")
    val title: String,

    @ColumnInfo(name = "body")
    val content: String,

    @ColumnInfo(name = "article_uri")
    val url: String,

    val author: String
)
