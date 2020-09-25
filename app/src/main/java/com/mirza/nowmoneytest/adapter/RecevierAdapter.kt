package com.mirza.nowmoneytest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mirza.nowmoneytest.R
import com.mirza.nowmoneytest.databinding.ReceiverItemListBinding
import com.mirza.nowmoneytest.db.entities.Receiver
import com.mirza.nowmoneytest.network.responses.ReceiverResponse

class RecevierAdapter() : RecyclerView.Adapter<MyViewHolder>() {

    private val receiverList = ArrayList<Receiver>()
    var position: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ReceiverItemListBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.receiver_item_list, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        this.position=position
        holder.bind(receiverList[position])
    }

    override fun getItemCount(): Int {
        return receiverList.size
    }

    fun setList(receiver: List<ReceiverResponse>?) {
        receiverList.clear()
        receiverList.addAll(receiverList)
    }
}

class MyViewHolder(val binding: ReceiverItemListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(receiver: Receiver) {
        binding.nameTextView.text = receiver.name
        binding.numberTextView.text = receiver.number
        binding.addressTextView.text = receiver.address

    }
}
