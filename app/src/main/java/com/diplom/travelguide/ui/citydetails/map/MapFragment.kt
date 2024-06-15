package com.diplom.travelguide.ui.citydetails.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diplom.travelguide.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapCity: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация MapKit
        MapKitFactory.initialize(requireContext())

        // Остальной код фрагмента
        mapCity = binding.mapCity
        mapCity.map.move(
            CameraPosition(Point(55.755864, 37.617698), 11.0f, 0.0f, 0.0f), // в Point надо передавать координаты города!!!
            Animation(Animation.Type.SMOOTH, 300F),
            null
        )
    }


    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapCity.onStart()
    }

    override fun onStop() {
        mapCity.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}

