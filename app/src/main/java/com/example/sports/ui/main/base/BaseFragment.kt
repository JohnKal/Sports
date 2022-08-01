package com.example.sports.ui.main.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.sports.SportsApplication
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<FragmentBinding : ViewBinding, VM : ViewModel>
    : Fragment() {

    private var _binding: ViewBinding? = null
    abstract val viewModel : VM
    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentBinding
    private val <VM : ViewModel> VM.classNameJava: Class<out ViewModel>
        get() {
            return this::class.java
        }

    @Suppress("UNCHECKED_CAST")
    private val binding: FragmentBinding
        get() = _binding as FragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateViewBinding(inflater, container)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        observeViewModels()
        clickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getFGBinding(): FragmentBinding = binding
    fun getFGViewModel(): VM = viewModel

    abstract fun initLayout()
    abstract fun observeViewModels()
    abstract fun clickListeners()
}