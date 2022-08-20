package com.habit.tracker.domain.entity

data class Request(
    val id: Int,
    val title: String,
    val description: String,
    val isDone: Boolean,
    val photos: List<String>
    //todo
//    val likes:
    // val dislikes
//val creator: User
)