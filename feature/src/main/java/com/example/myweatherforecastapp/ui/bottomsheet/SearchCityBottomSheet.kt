package com.example.myweatherforecastapp.ui.bottomsheet

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.model.ResultState
import com.example.domain.model.CityEntity
import com.example.myweatherforecastapp.R
import com.example.myweatherforecastapp.databinding.FragmentSearchCityBottomSheetListDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SearchCityBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentSearchCityBottomSheetListDialogBinding? = null

    private val binding get() = _binding!!

    private val args by navArgs<SearchCityBottomSheetArgs>()

    private val searchCityViewModel: SearchCityViewModel by viewModel()
    private val searchCityAdapter by lazy {
        SearchCityAdapter(
            onSelectedCountry = onSelectedCountry
        )
    }

    val progressDialog by lazy {
        ProgressDialog(requireActivity()).apply {
            setTitle(getString(R.string.label_loading_city_title))
            setMessage(getString(R.string.label_loading_city_description))
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.also {
            val bottomSheet = dialog?.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from<View>(it)
                behavior.peekHeight =
                    (resources.displayMetrics.heightPixels * 0.8).toInt()
                view?.requestLayout()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentSearchCityBottomSheetListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        searchCityViewModel.setupLoadAllCities()

        initWidget()
        initListener()
        observeViewModel()

    }

    private fun initWidget() = with(binding) {

        cityRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchCityAdapter
        }

    }

    private val onSelectedCountry: (CityEntity) -> Unit = { cityEntity ->

        searchCityViewModel.loadWeatherForUpdateLatLonSetting(cityName = cityEntity.city)

    }

    private fun observeViewModel() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                searchCityViewModel.searchPagingData.collectLatest { cityPagingData ->
                    searchCityAdapter.submitData(cityPagingData)
                }

            }
        }

        searchCityViewModel.showFullPageLoading.observe(viewLifecycleOwner) { isLoading ->
            when (isLoading) {
                true -> progressDialog.show()
                false -> progressDialog.dismiss()
            }
        }

        searchCityViewModel.saveSettingResponse.observe(viewLifecycleOwner) { model ->
            when (model) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if (model.data.lat.isEmpty() || model.data.lon.isEmpty()) {

                        showErrorNotFoundToast()

                    } else {
                        when (args.openScreen) {
                            getString(R.string.menu_home) -> {
                                val direction =
                                    SearchCityBottomSheetDirections.actionNavSearchCityToNavHome()
                                findNavController().navigate(direction)
                            }
                            getString(R.string.menu_weather_forecast) -> {
                                val direction =
                                    SearchCityBottomSheetDirections.actionNavSearchCityToNavForecast()
                                findNavController().navigate(direction)
                            }
                        }
                    }

                }
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showErrorNotFoundToast()
                }
            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                searchCityAdapter.loadStateFlow
                    .distinctUntilChanged()
                    .collectLatest { loadState ->

                        if (loadState.append.endOfPaginationReached &&
                            binding.cityNameEditText.text.toString().length > 0
                        ) {

                            if (searchCityAdapter.itemCount < 1) {
                                binding.statusCitySearchTextView.text =
                                    getString(R.string.label_not_found_city)
                                binding.statusCitySearchTextView.visibility = View.VISIBLE
                            } else {
                                binding.statusCitySearchTextView.visibility = View.GONE
                            }

                        }


                    }

            }
        }

    }

    private fun showErrorNotFoundToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.label_search_result_not_found_city),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initListener() = with(binding) {

        cityNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(text: Editable?) {

                searchCityViewModel.searchCity(cityName = text.toString().trim())
                if (text.isNullOrEmpty()) {
                    binding.statusCitySearchTextView.text =
                        getString(R.string.label_please_input_the_city)
                    binding.cityRecyclerView.visibility = View.GONE
                    binding.statusCitySearchTextView.visibility = View.VISIBLE
                } else {
                    binding.cityRecyclerView.visibility = View.VISIBLE
                    binding.statusCitySearchTextView.visibility = View.GONE
                }
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}