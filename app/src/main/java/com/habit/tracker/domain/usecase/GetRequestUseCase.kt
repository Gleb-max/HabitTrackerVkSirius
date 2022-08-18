package com.habit.tracker.domain.usecase

import com.habit.tracker.domain.repository.RequestRepository
import javax.inject.Inject

class GetRequestUseCase @Inject constructor(private val repository: RequestRepository) {

    suspend operator fun invoke(organizationId: Int, requestId: Int) =
        repository.getOrganizationRequestById(organizationId, requestId)
}