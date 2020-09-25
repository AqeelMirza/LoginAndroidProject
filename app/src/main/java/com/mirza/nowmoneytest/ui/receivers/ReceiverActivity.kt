package com.mirza.nowmoneytest.ui.receivers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirza.nowmoneytest.R
import com.mirza.nowmoneytest.adapter.RecevierAdapter
import com.mirza.nowmoneytest.databinding.ActivityReceiverBinding
import com.mirza.nowmoneytest.network.responses.ReceiverResponse
import com.mirza.nowmoneytest.ui.receivers.add.AddReceiverActivity
import com.mirza.nowmoneytest.util.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ReceiverActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: ReceiverViewModelFactory by instance()

    private lateinit var binding: ActivityReceiverBinding
    private lateinit var viewModel: ReceiverViewModel
    private lateinit var adapter: RecevierAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_receiver)
        viewModel = ViewModelProvider(this, factory).get(ReceiverViewModel::class.java)

        val auth = intent.getStringExtra(getString(R.string.auth))

        getReceiver(auth)

        binding.fab.setOnClickListener {
            navigateToAddReceiverActivity(auth)
        }

    }

    private fun getReceiver(auth: String?) {
        lifecycleScope.launch {
            try {
                val receiverResp = auth?.let { viewModel.getReceiver(it) }
                if (receiverResp != null) {
                    toast(getString(R.string.success))
                    if (receiverResp.isEmpty()) {
                        showNoReceiverText()
                    } else {
                        initRecyclerView(receiverResp)
                    }
                }
            } catch (e: ApiException) {
                toast(getString(R.string.failure))
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }
    }

    private fun showNoReceiverText() {
        binding.receiverNoItemText.visibility == View.VISIBLE
    }

    private fun initRecyclerView(receiverResponse: List<ReceiverResponse>?) {
        hideNoReceiverText()
        binding.receiverRecyclerView.visibility == View.VISIBLE
        binding.receiverRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecevierAdapter()
        binding.receiverRecyclerView.adapter = adapter
        displayReceiverList(receiverResponse)
    }

    private fun hideNoReceiverText() {
        binding.receiverNoItemText.visibility == View.GONE
    }

    private fun displayReceiverList(receiverResponse: List<ReceiverResponse>?) {
        adapter.setList(receiverResponse!!)
        adapter.notifyDataSetChanged()
    }

    private fun navigateToAddReceiverActivity(auth: String) {
        val intent = Intent(this, AddReceiverActivity::class.java).apply {
            putExtra(getString(R.string.auth), auth)
        }
        startActivity(intent)
    }
}