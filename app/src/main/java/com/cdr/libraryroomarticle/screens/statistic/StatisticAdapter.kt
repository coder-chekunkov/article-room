package com.cdr.libraryroomarticle.screens.statistic

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cdr.libraryroomarticle.R
import com.cdr.libraryroomarticle.databinding.ItemStatisticBinding
import com.cdr.libraryroomarticle.model.database.StatisticInfoTuple

class StatisticDiffUtil(
    private val oldList: List<StatisticInfoTuple>,
    private val newList: List<StatisticInfoTuple>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem == newItem
    }
}

interface StatisticItemListener {
    fun getInfoAboutStatistic(id: Long)
    fun removeStatistic(id: Long)
}

class StatisticAdapter(private val statisticItemListener: StatisticItemListener) :
    RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder>(), View.OnClickListener {

    var data: List<StatisticInfoTuple> = emptyList()
        set(newValue) {
            val diffUtil = StatisticDiffUtil(field, newValue)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
            field = newValue
            diffUtilResult.dispatchUpdatesTo(this@StatisticAdapter)
        }


    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatisticBinding.inflate(inflater, parent, false)
        binding.more.setOnClickListener(this)

        return StatisticViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        val statistic = data[position]

        with(holder.binding) {
            more.tag = statistic

            statusTextView.text = statistic.result
            difficultTextView.text = statistic.difficult
            mistakesTextView.text = statistic.mistakes.toString()

            image.setImageResource(if (statistic.result == "Победа") R.drawable.ic_win else R.drawable.ic_lost)
        }
    }

    override fun onClick(view: View) {
        showPopupMenu(view)
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val statistic = view.tag as StatisticInfoTuple

        popupMenu.menu.add(0, ID_INFORMATION, Menu.NONE, "Информация")
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Удалить")

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_INFORMATION -> statisticItemListener.getInfoAboutStatistic(statistic.id)
                ID_REMOVE -> statisticItemListener.removeStatistic(statistic.id)
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    companion object {
        private const val ID_INFORMATION = 1
        private const val ID_REMOVE = 2
    }

    class StatisticViewHolder(val binding: ItemStatisticBinding) :
        RecyclerView.ViewHolder(binding.root)
}