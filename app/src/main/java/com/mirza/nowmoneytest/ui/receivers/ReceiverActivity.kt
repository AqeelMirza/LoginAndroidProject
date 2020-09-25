package com.mirza.nowmoneytest.ui.receivers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirza.nowmoneytest.R
import com.mirza.nowmoneytest.adapter.RecevierAdapter
import com.mirza.nowmoneytest.databinding.ActivityMainReceiverBinding
import com.mirza.nowmoneytest.db.entities.Receiver
import com.mirza.nowmoneytest.ui.receivers.add.AddReceiverActivity
import com.mirza.nowmoneytest.util.ApiException
import com.mirza.nowmoneytest.util.NoInternetException
import com.mirza.nowmoneytest.util.toast
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ReceiverActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: ReceiverViewModelFactory by instance()

    private lateinit var binding: ActivityMainReceiverBinding
    private lateinit var viewModel: ReceiverViewModel
    private lateinit var adapter: RecevierAdapter
    private lateinit var auth: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main_receiver)
        viewModel = ViewModelProvider(this, factory).get(ReceiverViewModel::class.java)

        auth = intent.getStringExtra(getString(R.string.auth))

        getReceiver(auth)

        binding.includeActivityReceiver.fab.setOnClickListener {
            navigateToAddReceiverActivity(auth)
        }
    }

    override fun onRestart() {
        super.onRestart()
        getReceiver(auth)
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
        binding.includeActivityReceiver.receiverNoItemText.visibility = View.VISIBLE
    }

    private fun hideNoReceiverText() {
        binding.includeActivityReceiver.receiverNoItemText.visibility = View.GONE
    }

    private fun initRecyclerView(receiverResponse: List<Receiver>?) {
        binding.includeActivityReceiver.receiverRecyclerView.visibility = View.VISIBLE
        binding.includeActivityReceiver.receiverRecyclerView.layoutManager =
            LinearLayoutManager(this)
        adapter = RecevierAdapter()
        binding.includeActivityReceiver.receiverRecyclerView.adapter = adapter
        displayReceiverList(receiverResponse)
    }

    private fun displayReceiverList(receiverResponse: List<Receiver>?) {
        hideNoReceiverText()
        adapter.setList(receiverResponse)
        adapter.notifyDataSetChanged()
    }

    private fun navigateToAddReceiverActivity(auth: String) {
        val intent = Intent(this, AddReceiverActivity::class.java).apply {
            putExtra(getString(R.string.auth), auth)
        }
        startActivity(intent)
    }
}