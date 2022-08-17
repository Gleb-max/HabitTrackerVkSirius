package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.habit.tracker.R
import com.habit.tracker.TrackerApp
import com.habit.tracker.core.LocationPermissionHelper
import com.habit.tracker.databinding.FragmentMapBinding
import com.habit.tracker.domain.entity.Organization
import com.habit.tracker.presentation.stateholder.MapViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import java.lang.ref.WeakReference
import javax.inject.Inject

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskListBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MapViewModel
    private val component by lazy {
        (requireActivity().application as TrackerApp).component
    }

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        binding.mapView.gestures.focalPoint = binding.mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private lateinit var locationPermissionHelper: LocationPermissionHelper

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MapViewModel::class.java]
        locationPermissionHelper = LocationPermissionHelper(WeakReference(requireActivity()))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
    }

    private fun observeViewModel() {
        viewModel.organizations.observe(viewLifecycleOwner) {
            for (org in it) addOrganizationMarkerToMap(org)
        }
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    private fun onMapReady() {
        binding.mapView.apply {
            getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .zoom(14.0)
                    .build()
            )
            getMapboxMap().loadStyleUri(
//                Style.OUTDOORS
                "mapbox://styles/gleb-max/cl5m6rbl6004l14o82i8q872n"
            ) {
                initLocationComponent()
                setupGesturesListener()
                observeViewModel()
            }
        }
    }

    private fun addOrganizationMarkerToMap(organization: Organization) {
        val geo = organization.geo
        activity?.let {
            AppCompatResources.getDrawable(it, R.drawable.ic_profile)?.toBitmap(100, 100)
                ?.let { b ->
                    val annotationApi = binding.mapView.annotations
                    val pointAnnotationManager =
                        annotationApi.createPointAnnotationManager(binding.mapView)
                    pointAnnotationManager.addClickListener(OnPointAnnotationClickListener {
                        openOrganizationDetails(organization)
                        true
                    })
                    val pointAnnotationOptions: PointAnnotationOptions =
                        PointAnnotationOptions()
                            .withPoint(Point.fromLngLat(geo.long, geo.lat))
                            .withIconImage(b)
                    pointAnnotationManager.create(pointAnnotationOptions)
                }
        }
    }

    private fun openOrganizationDetails(organization: Organization) {
        val organizationBottomSheetFragment =
            OrganizationBottomSheetFragment.newInstance(organization.id)
        organizationBottomSheetFragment.show(
            parentFragmentManager,
            organizationBottomSheetFragment.tag
        )
    }

    private fun setupGesturesListener() {
        binding.mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = binding.mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_map_label,
                ),
//                shadowImage = AppCompatResources.getDrawable(
//                    requireContext(),
//                    R.drawable.mapbox_user_icon_shadow,
//                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )
    }

    private fun onCameraTrackingDismissed() {
        binding.mapView.apply {
            location
                .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
            location
                .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
            gestures.removeOnMoveListener(onMoveListener)
        }
    }

    override fun onDestroyView() {
        onCameraTrackingDismissed()
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}