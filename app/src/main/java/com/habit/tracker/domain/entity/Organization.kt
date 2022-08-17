package com.habit.tracker.domain.entity

data class Organization(
    val id: Int,
    val name: String,
    val address: String,
    val geo: Geo,
)