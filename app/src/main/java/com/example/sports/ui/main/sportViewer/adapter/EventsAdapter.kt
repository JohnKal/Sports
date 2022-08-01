package com.example.sports.ui.main.sportViewer.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.data.businessmodel.EventModel
import com.example.sports.R
import com.example.sports.databinding.EventLayoutBinding
import com.example.sports.extensions.setOnSingleClickListener
import com.example.sports.utils.TimeCounterView

class EventsAdapter(
    private val context: Context,
    private val events: MutableList<EventModel>,
    private val favoriteClickListener: () -> Unit
) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: EventLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        val startTime: TimeCounterView = itemView.startTime
        val favoriteIcon: AppCompatImageView = itemView.favoriteIcon
        val homeTeam: TextView = itemView.homeTeam
        val awayTeam: TextView = itemView.awayTeam
    }

    override fun getItemCount(): Int {
        return events.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.homeTeam.text = events[position].teamNames.split("-")[0]
        holder.awayTeam.text = events[position].teamNames.split("-")[1]

        if (events[position].isFav) {
            holder.favoriteIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_star_24))
        }
        else {
            holder.favoriteIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_outline_24))
        }

        holder.favoriteIcon.setOnSingleClickListener {
            if (events[position].isFav) {
                holder.favoriteIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_outline_24))
            }
            else {
                holder.favoriteIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_star_24))
            }
            Handler(Looper.getMainLooper()).postDelayed({
                updateEvent(events[position], events[position].isFav.not())
                favoriteClickListener()
            }, 1000)
        }

        holder.startTime.startCountDown(events[position].startGameTime.toLong())
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.startTime.stopCounter()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(EventLayoutBinding.inflate(inflater, parent, false))
    }

    private fun updateEvent(eventModel: EventModel, isFav: Boolean) {
        eventModel.isFav = isFav
        events.sortByDescending { eventModel ->
            eventModel.isFav
        }
        notifyDataSetChanged()
    }
}