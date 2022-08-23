package com.habit.tracker.presentation.view

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.habit.tracker.R
import com.habit.tracker.TrackerApp
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onMapReady()
        } else {
            Toast.makeText(
                requireActivity(),
                context?.getString(R.string.label_require_geo),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun startLocationPermissionRequest() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

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
        startLocationPermissionRequest()

        binding.tilSearch.setEndIconOnClickListener {
            val filterDialog = BottomSheetDialog(requireContext())
            val filterView = layoutInflater.inflate(R.layout.fragment_filters_bottom_sheet, null)
            filterDialog.setContentView(filterView)
            filterDialog.show()
            seekBarTracker(filterView)
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
            AppCompatResources.getDrawable(it, R.drawable.ic_baseline_location_on_24)
                ?.toBitmap(100, 100)
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
                            .withPoint(Point.fromLngLat(geo.long.toDouble(), geo.lat.toDouble()))
                            .withIconImage(b)
                    pointAnnotationManager.create(pointAnnotationOptions)
                }
        }
    }

    private fun openOrganizationDetails(organization: Organization) {
        findNavController().navigate(
            MapFragmentDirections.actionNavigationMapToNavigationOrganizationDetails(
                organization.id
            )
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

    private fun seekBarTracker(view: View) {
        val seekBar = view.findViewById<SeekBar>(R.id.seek_bar_filter)
        val seekNum = view.findViewById<TextView>(R.id.seek_num)

        var startPoint = 0
        var endPoint = 100

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                seekNum.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                if (seekBar != null) {
                    startPoint = seekBar.progress
                }
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                if (seekBar != null) {
                    endPoint = seekBar.progress
                }
            }

        })
    }

    override fun onDestroyView() {
        onCameraTrackingDismissed()
        super.onDestroyView()
        _binding = null
    }
}