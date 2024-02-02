package com.ntpro.mobileandroiddevtestwork

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ntpro.mobileandroiddevtestwork.databinding.FragmentTableRowBinding


class DealsListAdapter(private val context: Context) :
    PagingDataAdapter<Server.Deal, DealsListAdapter.DealsListHolder>(
        DealCallback
    ) {

    override fun onBindViewHolder(holder: DealsListHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealsListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentTableRowBinding.inflate(inflater, parent, false)
        return DealsListHolder(binding, context)
    }

    class DealsListHolder(
        private val binding: FragmentTableRowBinding,
        context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        private val sellColor = ContextCompat.getColor(context, R.color.sellColor)
        private val buyColor = ContextCompat.getColor(context, R.color.buyColor)

        fun bind(deal: Server.Deal) {
            binding.apply {
                dealId.text = deal.id.toString()
                dealAmount.text = roundDouble(deal.amount, "whole")
                dealSide.text = deal.side.toString()
                dealPrice.text = roundDouble(deal.price, "hundred")
                updateTime.text = deal.timeStamp.toString()
                toolName.text = deal.instrumentName
            }
            changeColor(deal)
        }
//      округление
        private fun roundDouble(number: Double, purpose: String): String {
            return when (purpose) {
                "hundred" -> (Math.round(number * 100) / 100.0).toString()
                "whole" -> Math.round(number).toString()
                else -> ""
            }
        }
//      изменение цвета в зависимости от типа сделки
        private fun changeColor(deal: Server.Deal) {
            val color =
                if (deal.side == Server.Deal.Side.SELL)
                    sellColor
                else
                    buyColor
            binding.dealSide.setBackgroundColor(color)
        }
    }

    companion object {
        private val DealCallback = object : DiffUtil.ItemCallback<Server.Deal>() {
            override fun areItemsTheSame(oldItem: Server.Deal, newItem: Server.Deal): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Server.Deal, newItem: Server.Deal): Boolean =
                oldItem == newItem
        }
    }
}