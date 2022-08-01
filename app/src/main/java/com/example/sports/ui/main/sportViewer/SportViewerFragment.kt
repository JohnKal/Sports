package com.example.sports.ui.main.sportViewer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sports.databinding.SportViewerFragmentLayoutBinding
import com.example.sports.extensions.gone
import com.example.sports.extensions.visible
import com.example.sports.ui.main.base.BaseFragment
import com.example.sports.ui.main.sportViewer.adapter.SportsExpandableAdapter
import com.example.sports.ui.main.sportViewer.state.RenderState
import com.example.sports.ui.main.sportViewer.state.SportsEventsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportViewerFragment : BaseFragment<SportViewerFragmentLayoutBinding, SportViewerViewModel>() {

    override val viewModel by viewModels<SportViewerViewModel>()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SportViewerFragmentLayoutBinding {
        return SportViewerFragmentLayoutBinding.inflate(inflater, container, false)
    }

    override fun initLayout() {}

    override fun observeViewModels() {
        getFGViewModel().getSportsEventState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
    }

    override fun clickListeners() {}

    /**
     * General method for handling all the different states
     */
    fun renderState(state: RenderState) {
        when (state) {
            is SportsEventsState.Loading -> {
                showLoader(show = true)
            }
            is SportsEventsState.Error -> {
                showLoader(show = false)
                showErrorView(show = true)
            }
            is SportsEventsState.Success -> {
                showLoader(show = false)
                showEvents(show = true)

                val sportEventsList = state.listSportsEventsModel

                val adapters: List<SportsExpandableAdapter>? = sportEventsList?.map { itemsGroup ->
                    SportsExpandableAdapter(
                        context = requireContext(),
                        eventsGroup = itemsGroup
                    )
                }
                val concatAdapterConfig = ConcatAdapter.Config.Builder()
                    .setIsolateViewTypes(false)
                    .build()
                val concatAdapter = adapters?.let { ConcatAdapter(concatAdapterConfig, it) }

                with(getFGBinding().sportsRecycler) {
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    itemAnimator =
                        ExpandableItemAnimator()
                    adapter = concatAdapter
                }
            }
        }
    }

    private fun showLoader(show: Boolean) {
        if (show) {
            showErrorView(show = false)
            showEvents(show = false)
            getFGBinding().animationView.playAnimation()
            getFGBinding().animationView.visible()
        } else {
            getFGBinding().animationView.cancelAnimation()
            getFGBinding().animationView.gone()
        }
    }

    private fun showErrorView(show: Boolean) {
        if (show) {
            getFGBinding().errorView.visible()
        } else {
            getFGBinding().errorView.gone()
        }
    }

    private fun showEvents(show: Boolean) {
        if (show) {
            showErrorView(show = false)
            getFGBinding().sportsRecycler.visible()
        } else {
            getFGBinding().sportsRecycler.gone()
        }
    }

    companion object {
        fun newInstance() = SportViewerFragment()
    }
}