package com.app.shaditest

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import com.app.shaditest.databinding.ListItemBinding
import com.app.shaditest.model.Result


class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null

    var isListEmpty = ObservableBoolean(false)

    private var list = ArrayList<Result>()

    fun updateList(list: ArrayList<Result>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }

        return ViewHolder(ListItemBinding.inflate(inflater!!, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.model = list[position]
        holder.mBinding.remove.setOnClickListener {
            deleteItem(holder.itemView, position)
        }

        holder.mBinding.add.setOnClickListener {
            saveItem(holder.itemView, position)
        }
    }

    class ViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var mBinding = binding
    }

    private fun saveItem(rowView: View, position: Int) {
        val anim = AnimationUtils.loadAnimation(rowView.context, R.anim.slide_out_right)
        anim.duration = 300
        rowView.startAnimation(anim)
        Handler().postDelayed({
            list.removeAt(position)
            notifyDataSetChanged()

            if (itemCount == 0) {
                isListEmpty.set(true)
            }

        }, anim.duration)
    }

    private fun deleteItem(rowView: View, position: Int) {
        val anim = AnimationUtils.loadAnimation(rowView.context, R.anim.slide_out_left)
        anim.duration = 300
        rowView.startAnimation(anim)
        Handler().postDelayed({
            list.removeAt(position)
            notifyDataSetChanged()

            if (itemCount == 0) {
                isListEmpty.set(true)
            }

        }, anim.duration)
    }

}