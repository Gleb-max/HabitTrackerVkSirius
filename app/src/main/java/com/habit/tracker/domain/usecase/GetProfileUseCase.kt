package com.habit.tracker.domain.usecase

import com.habit.tracker.domain.repository.RequestRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: RequestRepository) {

    suspend operator fun invoke() = repository.fetchProfile()
}