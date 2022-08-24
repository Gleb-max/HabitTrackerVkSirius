package com.habit.tracker.presentation.stateholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.habit.tracker.core.BaseViewModel
import com.habit.tracker.domain.usecase.CreateRequestUseCase
import com.habit.tracker.presentation.model.RequestDraft
import javax.inject.Inject

class CreateRequestViewModel @Inject constructor(
    private val createRequestUseCase: CreateRequestUseCase
) : BaseViewModel() {

    private val _requestDraft = MutableLiveData(RequestDraft.empty())
    val requestDraft: LiveData<RequestDraft> = _requestDraft

    fun setTitle(value: String) {
        _requestDraft.value = requestDraft.value!!.copy(name = value)
    }

    fun setDescription(value: String) {
        _requestDraft.value = requestDraft.value!!.copy(description = value)
    }

    fun addPhoto(path: String) {
        val oldDraft = requestDraft.value!!
        val photos = oldDraft.photos.toMutableList()
        if (photos.size == 4) {
            photos[0] = path
        } else {
            photos.add(path)
        }
        _requestDraft.value = oldDraft.copy(photos = photos)
    }

    fun createRequest(organizationId: Int) {
        val requestDraft = requestDraft.value!!
        viewModelScope.execute {
            createRequestUseCase(
                organizationId,
                requestDraft.name,
                requestDraft.description,
                requestDraft.photos
            )
        }
    }
}