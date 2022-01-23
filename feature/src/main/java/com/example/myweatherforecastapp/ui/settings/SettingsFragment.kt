package com.example.myweatherforecastapp.ui.settings

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myweatherforecastapp.R
import com.example.myweatherforecastapp.databinding.FragmentSettingsBinding
import com.example.myweatherforecastapp.ext.UnitTypeToTemperatureIdText
import com.example.myweatherforecastapp.model.UnitType
import com.example.myweatherforecastapp.ui.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun layout(): Int {
        return R.layout.fragment_settings
    }

    override fun init() {

        initListener()
        observeViewModel()

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                settingsViewModel.appSettingModel.collectLatest {
                    setTextTemperature(it.unit)
                }

            }
        }
    }

    private fun setTextTemperature(unit: String) {
        binding.temperatureSettingTextView.text = getString(unit.UnitTypeToTemperatureIdText())
    }

    private fun initListener() {
        binding.temperatureCardView.setOnClickListener {

            dialogChooseTemperature()

        }
    }

    private fun dialogChooseTemperature() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.label_dialog_title_temperature))

        val items = arrayOf(getString(R.string.label_celsius), getString(R.string.label_fahrenheit))
        builder.setItems(items) { dialog, pos ->
            val unit = when (pos) {
                0 -> UnitType.Celsius.unit
                else -> UnitType.Fahrenheit.unit
            }
            settingsViewModel.saveAppSetting(unit = unit)

            Toast.makeText(requireContext(), getString(R.string.label_setting_updated), Toast.LENGTH_SHORT).show()
        }
        val dialog = builder.create()
        dialog.show()
    }

}