package com.mirza.nowmoneytest.ui.receivers.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mirza.nowmoneytest.R
import com.mirza.nowmoneytest.databinding.ActivityAddReceiverBinding
import com.mirza.nowmoneytest.ui.receivers.ReceiverViewModel
import com.mirza.nowmoneytest.ui.receivers.ReceiverViewModelFactory
import com.mirza.nowmoneytest.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AddReceiverActivity : AppCompatActivity(), KodeinAware {

    //Dependency Injection
    override val kodein by kodein()
    private val factory: ReceiverViewModelFactory by instance()

    //Databinding
    private lateinit var binding: ActivityAddReceiverBinding

    //Viewmodel
    private lateinit var viewModel: ReceiverViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_receiver)
        viewModel = ViewModelProvider(this, factory).get(ReceiverViewModel::class.java)

        val auth = intent.getStringExtra(getString(R.string.auth))
        binding.addButton.setOnClickListener {
            addReceiver(auth)
        }
    }

    //Adding contact
    private fun addReceiver(auth: String?) {
        val name = binding.addName.text.toString().trim()
        val number = binding.addNumber.text.toString().trim()
        val address = binding.addAddress.text.toString().trim()

        if (name.isEmpty() || address.isEmpty() || number.length != 10) {
            toast(getString(R.string.please_enter_values))
        } else {
            progress_bar.show()
            //using Coroutine to handle asynchronously
            lifecycleScope.launch {
                try {
                    val resp = viewModel.addReceiver(auth!!, name, number, address)
                    progress_bar.hide()
                    toast(getString(R.string.added))
                    //closing the Activity
                    finish()
                } catch (e: ApiException) {
                    progress_bar.hide()
                    //Server error response
                    toast(e.message.toString())
                    e.printStackTrace()
                } catch (e: NoInternetException) {
                    progress_bar.hide()
                    //No Internet error message
                    binding.rootLayout.snackbar(e.message.toString())
                    e.printStackTrace()
                }
            }
        }
    }
}