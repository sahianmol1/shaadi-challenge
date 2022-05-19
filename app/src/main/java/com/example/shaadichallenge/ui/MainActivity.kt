package com.example.shaadichallenge.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shaadichallenge.R
import com.example.shaadichallenge.databinding.ActivityMainBinding
import com.example.shaadichallenge.viewmodel.ShaadiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ShaadiViewModel by viewModels()
    private lateinit var adapter: ProfilesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()

        binding.rvProfiles.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        setObservers()

        binding.swipeRefresh.apply {
            setOnRefreshListener {
                isRefreshing = true
                viewModel.getProfiles(true)
            }
        }
    }

    private fun setupAdapter() {
        adapter = ProfilesAdapter(onAcceptClick = { prf ->
            val profile = prf.copy(isAccepted = true)
            viewModel.updateProfile(profile)
        }) { prf ->
            val profile = prf.copy(isAccepted = false)
            viewModel.updateProfile(profile)
        }
    }

    private fun setObservers() {
        viewModel.userProfiles.observe(this) {
            adapter.submitList(it)
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { event ->
                when(event) {
                    is ShaadiViewModel.CustomEvent.ErrorEvent -> {
                        showErrorDialog()
                        binding.swipeRefresh.isRefreshing = false
                    }
                }
            }
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error))
            .setMessage(getString(R.string.something_went_wrong))
            .setPositiveButton(
                getString(R.string.retry)
            ) { _, _ -> viewModel.getProfiles(true) }
            .setNegativeButton("cancel"){_, _ -> }
            .setCancelable(false)
            .show()
    }
}