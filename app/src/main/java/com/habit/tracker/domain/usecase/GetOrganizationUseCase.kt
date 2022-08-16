package com.habit.tracker.domain.usecase

import com.habit.tracker.domain.repository.RequestRepository
import javax.inject.Inject

class GetOrganizationUseCase @Inject constructor(private val repository: RequestRepository) {

    suspend operator fun invoke(organizationId: Int) =
        repository.getOrganizationById(organizationId)
}