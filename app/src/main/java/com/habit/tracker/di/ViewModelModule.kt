package com.habit.tracker.di

import androidx.lifecycle.ViewModel
import com.habit.tracker.presentation.stateholder.AuthViewModel
import com.habit.tracker.presentation.stateholder.MapViewModel
import com.habit.tracker.presentation.stateholder.OrganizationViewModel
import com.habit.tracker.presentation.stateholder.RequestDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    fun bindMapViewModel(viewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrganizationViewModel::class)
    fun bindOrganizationViewModel(viewModel: OrganizationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RequestDetailsViewModel::class)
    fun bindRequestDetailsViewModel(viewModel: RequestDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}