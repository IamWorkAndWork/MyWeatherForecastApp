package com.example.myweatherforecastapp.ui.weatherforecast

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.model.ResultState
import com.example.myweatherforecastapp.R
import com.example.myweatherforecastapp.databinding.FragmentWeatherForecastBinding
import com.example.myweatherforecastapp.model.ForecastUiModel
import com.example.myweatherforecastapp.model.SearchCityListener
import com.example.myweatherforecastapp.ui.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherForecastFragment : BaseFragment<FragmentWeatherForecastBinding>() {

    private val weatherForecastViewModel: WeatherForecastViewModel by viewModel()

    private val weatherForecastUiController by lazy {
        WeatherForecastUiController()
    }

    override fun layout(): Int = R.layout.fragment_weather_forecast

    override fun init() {
        initWidget()
        setupListener()
        observeViewModel()
    }

    private fun setupListener() {

        weatherForecastUiController.apply {

            setSearchCityListener(object : SearchCityListener {
                override fun onSearchCity(view: View) {

                    val direction = WeatherForecastFragmentDirections.actionNavForecastToNavSearchCity(getString(R.string.menu_weather_forecast))
                    findNavController().navigate(direction)

                }
            })

        }
    }

    private fun observeViewModel() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                weatherForecastViewModel.appSettingModel
                    .collectLatest {
                        weatherForecastViewModel.loadWeatherForecastUiData()
                    }

            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                weatherForecastViewModel.forecastUiModel
                    .collectLatest { result ->
                        errorTextView.visibility = View.GONE

                        when (result) {
                            is ResultState.Loading -> {

                                progressBar.visibility = View.VISIBLE

                            }
                            is ResultState.Error -> {

                                progressBar.visibility = View.GONE
                                errorTextView.visibility = View.VISIBLE
                                errorTextView.text = String.format(
                                    getString(R.string.home_error_message),
                                    result.exception.localizedMessage
                                )

                            }
                            is ResultState.Success -> {

                                progressBar.visibility = View.GONE
                                renderForecast(result.data)

                            }
                        }
                    }

            }
        }
    }

    private fun renderForecast(uiModel: ForecastUiModel) {
        weatherForecastUiController.apply {
            this.forecastUiModel = uiModel
        }
    }

    private fun initWidget() = with(binding) {

        forecastRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weatherForecastUiController.adapter
        }

    }


}