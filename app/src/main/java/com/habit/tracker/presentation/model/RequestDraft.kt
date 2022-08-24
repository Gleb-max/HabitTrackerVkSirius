package com.habit.tracker.presentation.model

//todo хранить его в руме, чтоб юзер мог дописать потом
data class RequestDraft(
    val name: String,
    val description: String,
    val photos: List<String>,
) {

    companion object {

        fun empty() = RequestDraft("", "", listOf())
    }
}