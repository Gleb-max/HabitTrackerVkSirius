package com.habit.tracker.di

import android.app.Application
import com.habit.tracker.presentation.view.OrganizationBottomSheetFragment
import com.habit.tracker.TrackerApp
import com.habit.tracker.presentation.MainActivity
import com.habit.tracker.presentation.view.MapFragment
import com.habit.tracker.presentation.view.ProfileFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: TrackerApp)

    fun inject(activity: MainActivity)

    fun inject(todoListFragment: MapFragment)

    fun inject(todoItemFragment: ProfileFragment)

    fun inject(organizationFragment: OrganizationFragment)

    fun inject(requestDetailsFragment: RequestDetailsFragment)

    fun inject(splashFragment: SplashFragment)

    fun inject(enterPhoneFragment: EnterPhoneFragment)

    fun inject(enterCodeFragment: EnterCodeFragment)

    fun inject(registrationFragment: RegistrationFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}