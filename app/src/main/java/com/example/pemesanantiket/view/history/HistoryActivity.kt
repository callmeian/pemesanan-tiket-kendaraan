package com.example.pemesanantiket.view.history

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azhar.pemesanantiket.view.history.HistoryAdapter
import com.example.pemesanantiket.databinding.ActivityHistoryBinding
import com.example.pemesanantiket.model.ModelDatabase
import com.example.pemesanantiket.viewmodel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    lateinit var historyAdapter: HistoryAdapter
    private val viewModel by viewModels<HistoryViewModel> {
        HistoryViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBar()
        setToolbar()
        setViewModel()
        setInitLayout()
        setUpItemTouchHelper()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    private fun setInitLayout() {

        with(binding) {
            historyAdapter = HistoryAdapter(mutableListOf())
            rvHistory.setHasFixedSize(true)
            rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
            rvHistory.adapter = historyAdapter
        }
    }

    private fun setViewModel() {
        viewModel.dataList.observe(this) { modelDatabases: List<ModelDatabase> ->
            if (modelDatabases.isEmpty()) {
                with(binding) {
                    tvNotFound.visibility = View.VISIBLE
                    rvHistory.visibility = View.GONE
                }
            } else {
                with(binding) {
                    tvNotFound.visibility = View.GONE
                    rvHistory.visibility = View.VISIBLE
                }
            }
            historyAdapter = HistoryAdapter(modelDatabases.toMutableList())
            historyAdapter.setDataAdapter(modelDatabases)
        }
    }

    private fun setUpItemTouchHelper() {
        val simpleCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedPosition = viewHolder.adapterPosition
                val modelDatabase = historyAdapter.setSwipeRemove(swipedPosition)
                val uid = modelDatabase.uid
                viewModel.deleteDataById(uid)
                Toast.makeText(
                    this@HistoryActivity,
                    "Data yang dipilih sudah dihapus",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvHistory)
    }

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }
}