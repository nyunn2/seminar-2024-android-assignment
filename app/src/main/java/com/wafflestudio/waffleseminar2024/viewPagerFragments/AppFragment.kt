package com.wafflestudio.waffleseminar2024.viewPagerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.wafflestudio.waffleseminar2024.R
import com.wafflestudio.waffleseminar2024.databinding.FragmentAppBinding

class AppFragment : Fragment() {
    private var _binding: FragmentAppBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppBinding.inflate(inflater, container, false)

        // NavHostFragment 초기화
        val navHostFragment = childFragmentManager.findFragmentById(R.id.app_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


