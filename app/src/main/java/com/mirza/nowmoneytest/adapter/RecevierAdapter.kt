package com.mirza.nowmoneytest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mirza.nowmoneytest.R
import com.mirza.nowmoneytest.databinding.ReceiverItemListBinding
import com.mirza.nowmoneytest.db.entities.Receiver

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
        this.position = position
        holder.bind(receiverList[position])
    }

    override fun getItemCount(): Int {
        return receiverList.size
    }

    fun setList(receiverResponse: List<Receiver>?) {
        receiverList.clear()
        receiverResponse?.let { receiverList.addAll(it) }
    }

    fun removeAt(position: Int) {
        receiverList.removeAt(position)
        notifyItemRemoved(position)
    }
}

class MyViewHolder(val binding: ReceiverItemListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(receiver: Receiver) {
        binding.nameTextView.text = "Name: ${receiver.name}"
        binding.numberTextView.text = "Number: ${receiver.number}"
        binding.addressTextView.text = "Address: ${receiver.address}"

    }
}
