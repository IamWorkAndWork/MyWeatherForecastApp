package com.example.myweatherforecastapp.ui.home

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.model.ResultState
import com.example.myweatherforecastapp.R
import com.example.myweatherforecastapp.databinding.FragmentHomeBinding
import com.example.myweatherforecastapp.model.HomeUiModel
import com.example.myweatherforecastapp.model.SearchCityListener
import com.example.myweatherforecastapp.ui.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val homeUiController by lazy {
        HomeUiController()
    }

    override fun layout(): Int = R.layout.fragment_home

    override fun init() {
        initWidget()
        setupListener()
        observeViewModel()
    }

    private fun initWidget() = with(binding) {

        homeRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            adapter = homeUiController.adapter
        }

    }

    private fun observeViewModel() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                homeViewModel.appSettingModel
                    .collectLatest { settingModel ->

                        homeViewModel.loadHomeUiData()

                    }

            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                homeViewModel.homeUiModel
                    .collectLatest { result ->

                        homeErrorTextView.visibility = View.GONE

                        when (result) {
                            is ResultState.Loading -> {

                                homeProgressBar.visibility = View.VISIBLE

                            }
                            is ResultState.Error -> {

                                homeErrorTextView.visibility = View.VISIBLE
                                homeProgressBar.visibility = View.GONE
                                homeErrorTextView.text = String.format(
                                    getString(R.string.home_error_message),
                                    result.exception.localizedMessage
                                )

                            }
                            is ResultState.Success -> {

                                homeProgressBar.visibility = View.GONE
                                val homeHiModelList = result.data

                                renderHomeUi(homeHiModelList)

                            }
                        }
                    }

            }
        }

    }

    private fun renderHomeUi(homeHiModelList: List<HomeUiModel>) {

        homeUiController.apply {
            this.homeHiModelList = homeHiModelList
        }

    }

    private fun setupListener() {

        homeUiController.setSearchCityListener(object : SearchCityListener {

            override fun onSearchCity(view: View) {
                val direction = HomeFragmentDirections.actionNavHomeToNavSearchCity(getString(R.string.menu_home))
                findNavController().navigate(direction)
            }

        })

    }


}