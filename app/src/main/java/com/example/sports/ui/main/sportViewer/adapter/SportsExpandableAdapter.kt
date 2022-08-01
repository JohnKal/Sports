package com.example.sports.ui.main.sportViewer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.data.businessmodel.EventModel
import com.example.data.businessmodel.SportEventsModel
import com.example.data.businessmodel.SportType
import com.example.sports.R
import com.example.sports.databinding.SportViewerEventsLayoutBinding
import com.example.sports.databinding.SportViewerHeaderLayoutBinding
import com.example.sports.extensions.False
import com.example.sports.extensions.True
import org.xml.sax.ContentHandler
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class SportsExpandableAdapter(
    private val context: Context,
    private val eventsGroup: SportEventsModel):
    RecyclerView.Adapter<SportsExpandableAdapter.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_HEADER = 2

        private const val IC_EXPANDED_ROTATION_DEG = 0F
        private const val IC_COLLAPSED_ROTATION_DEG = 180F
    }

    var isExpanded: Boolean by Delegates.observable(true) { _: KProperty<*>, _: Boolean, newExpandedValue: Boolean ->
        if (newExpandedValue) {
            notifyItemRangeInserted(1, 1)
            //To update the header expand icon
            notifyItemChanged(0)
        } else {
            notifyItemRangeRemoved(1, 1)
            //To update the header expand icon
            notifyItemChanged(0)
        }
    }

    private val onHeaderClickListener = View.OnClickListener {
        isExpanded = !isExpanded
    }

    override fun getItemViewType(position: Int): Int {
        return (position == 0) True VIEW_TYPE_HEADER False VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return (isExpanded) True 2 False 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> ViewHolder.HeaderVH(
                SportViewerHeaderLayoutBinding.inflate(inflater, parent, false)
            )
            else -> ViewHolder.ItemVH(
                SportViewerEventsLayoutBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.ItemVH -> {
                holder.bind(context, eventsGroup.events.toMutableList())
            }
            is ViewHolder.HeaderVH -> {
                holder.bind(eventsGroup.sportType, eventsGroup.title, isExpanded, onHeaderClickListener)
            }
        }
    }

    sealed class ViewHolder(viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        class ItemVH(itemView: SportViewerEventsLayoutBinding) : ViewHolder(itemView) {
            internal val eventsRecycler = itemView.eventsRecycler

            fun bind(context: Context, events: MutableList<EventModel>) {

                val favoriteClickListener: () -> Unit = {
                      eventsRecycler.smoothScrollToPosition(0)
                }

                events.sortByDescending { eventModel ->
                    eventModel.isFav
                }

                val eventsAdapter = EventsAdapter(
                    context = context,
                    events = events,
                    favoriteClickListener = favoriteClickListener
                )

                eventsRecycler.apply {
                    this.layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    this.adapter = eventsAdapter
                }
            }
        }

        class HeaderVH(itemView: SportViewerHeaderLayoutBinding) : ViewHolder(itemView) {
            internal val tvTitle = itemView.tvSport
            internal val icSportIcon = itemView.icSport
            internal val icExpand = itemView.icExpand

            fun bind(sportType: SportType, sportName: String, expanded: Boolean, onClickListener: View.OnClickListener) {
                tvTitle.text = sportName
                icSportIcon.setImageResource(sportType.drawableRes)
                icExpand.rotation =
                    if (expanded) IC_EXPANDED_ROTATION_DEG else IC_COLLAPSED_ROTATION_DEG
                itemView.setOnClickListener(onClickListener)
            }
        }
    }
}