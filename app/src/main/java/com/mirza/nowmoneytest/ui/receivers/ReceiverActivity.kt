package com.mirza.nowmoneytest.ui.receivers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirza.nowmoneytest.R
import com.mirza.nowmoneytest.adapter.RecevierAdapter
import com.mirza.nowmoneytest.databinding.ActivityMainReceiverBinding
import com.mirza.nowmoneytest.db.entities.Receiver
import com.mirza.nowmoneytest.ui.receivers.add.AddReceiverActivity
import com.mirza.nowmoneytest.util.ApiException
import com.mirza.nowmoneytest.util.NoInternetException
import com.mirza.nowmoneytest.util.SwipeToDeleteCallback
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
    private lateinit var receiverList: List<Receiver>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main_receiver)
        viewModel = ViewModelProvider(this, factory).get(ReceiverViewModel::class.java)

        auth = intent.getStringExtra(getString(R.string.auth))

        getReceiver(auth)
        initRecyclerView()
        swipeToDelete()

        binding.includeActivityReceiver.fab.setOnClickListener {
            navigateToAddReceiverActivity(auth)
        }
    }

    private fun swipeToDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteReceiver(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.includeActivityReceiver.receiverRecyclerView)
    }

    private fun deleteReceiver(pos: Int) {
        var _id = receiverList.get(pos)._id
        lifecycleScope.launch {
            try {
                val deleteResp = _id?.let { viewModel.deleteReceiver(auth, _id) }
                if (deleteResp != null) {
                    toast(getString(R.string.deleted))
                    adapter.removeAt(pos)
                }
            } catch (e: ApiException) {
                toast(getString(R.string.failure))
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
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
                        receiverList = receiverResp
                        displayReceiverList(receiverResp)
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

    private fun initRecyclerView() {
        binding.includeActivityReceiver.receiverRecyclerView.visibility = View.VISIBLE
        binding.includeActivityReceiver.receiverRecyclerView.layoutManager =
            LinearLayoutManager(this)
        adapter = RecevierAdapter()
        binding.includeActivityReceiver.receiverRecyclerView.adapter = adapter
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