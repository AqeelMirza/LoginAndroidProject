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
import com.mirza.nowmoneytest.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ReceiverActivity : AppCompatActivity(), KodeinAware {

    //Dependency Injection
    override val kodein by kodein()
    private val factory: ReceiverViewModelFactory by instance()

    //DataBinding
    private lateinit var binding: ActivityMainReceiverBinding

    //Viewmodel
    private lateinit var viewModel: ReceiverViewModel

    //RecyclerViewAdapter
    private lateinit var adapter: RecevierAdapter

    //Auth token
    private var auth: String? = null

    //ReceiverList
    private lateinit var receiverList: List<Receiver>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main_receiver)
        viewModel = ViewModelProvider(this, factory).get(ReceiverViewModel::class.java)

        //reading the token
        auth = intent.getStringExtra(getString(R.string.auth))

        getReceiver(auth)
        initRecyclerView()
        swipeToDelete()

        binding.includeActivityReceiver.fab.setOnClickListener {
            navigateToAddReceiverActivity(this.auth)
        }
    }

    private fun swipeToDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteReceiver(viewHolder.adapterPosition)
            }
        }
        attachSwipeHandler(swipeHandler)
    }

    private fun attachSwipeHandler(swipeHandler: SwipeToDeleteCallback) {
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.includeActivityReceiver.receiverRecyclerView)
    }

    private fun deleteReceiver(pos: Int) {
        var _id = receiverList.get(pos)._id
        lifecycleScope.launch {
            try {
                val deleteResp = _id?.let { viewModel.deleteReceiver(auth!!, _id) }
                if (deleteResp != null) {
                    toast(getString(R.string.deleted))
                    //removing from the ROOM DB
                    viewModel.deleteReceiverFromDb(receiverList.get(pos))
                    adapter.removeAt(pos)
                    if ((receiverList.size == 1 && pos == 0) || receiverList.isEmpty()) {
                        showNoReceiverText()
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
                        viewModel.addAllReceiver(receiverResp)
                        displayReceiverList(receiverResp)
                    }
                }
            } catch (e: ApiException) {
                toast(e.message.toString())
                e.printStackTrace()
            } catch (e: NoInternetException) {
                //Displaying from ROOM DB, if the no respone due to internet connection
                displayReceiverList(viewModel.getAllReceivers())
                binding.parentLayout.snackbar(e.message.toString())
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

    private fun displayReceiverList(receiverResponse: List<Receiver>) {
        hideNoReceiverText()
        receiverList = receiverResponse
        adapter.setList(receiverResponse)
        adapter.notifyDataSetChanged()
    }

    private fun navigateToAddReceiverActivity(auth: String?) {
        val intent = Intent(this, AddReceiverActivity::class.java).apply {
            putExtra(getString(R.string.auth), auth)
        }
        startActivity(intent)
    }
}