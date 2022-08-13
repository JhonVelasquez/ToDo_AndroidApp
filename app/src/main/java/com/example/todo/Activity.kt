package com.example.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activity (
    @PrimaryKey(autoGenerate = true) val activityID: Int,
    @ColumnInfo(name="title") val title: String?,
    @ColumnInfo(name="content") val content: String?,
    @ColumnInfo(name="hour") val hour: String?,
    @ColumnInfo(name="date") val date: String?
    ){
    constructor(title: String?,content: String?,hour: String?,date: String):
            this(0,title,content,hour,date)



}