package com.habit.tracker.data.mapper

import com.habit.tracker.data.source.remote.model.dto.RequestDTO
import com.habit.tracker.data.source.remote.model.response.RequestListResponse
import com.habit.tracker.data.source.remote.model.response.RequestResponse
import com.habit.tracker.domain.entity.Request
import javax.inject.Inject

class RequestResponseMapper @Inject constructor() {

    private fun map(data: RequestDTO): Request {
        return Request(
            id = data.id,
            title = data.title,
            description = data.description,
            isDone = data.done,
            photos = data.photos,
        )
    }

    fun map(data: RequestListResponse): List<Request> {
        return data.results.map { map(it) }
    }

    fun map(data: RequestResponse): Request {
        return map(data.result)
    }
}