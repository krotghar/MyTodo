package com.example.mvvmsample.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmsample.R
import com.example.mvvmsample.databinding.FragmentTasksListBinding
import com.example.mvvmsample.domain.model.ModelTask
import com.example.mvvmsample.presentation.extension.appComponent
import com.example.mvvmsample.presentation.viewmodel.MainViewModel
import javax.inject.Inject

class FragmentTaskList : Fragment(), OnItemClickListener {

    private lateinit var recyclerAdapter: TasksListAdapter
    private var _binding: FragmentTasksListBinding? = null
    private val binding get() = _binding!!
    private val model: MainViewModel by viewModels { factory.create() }

    @Inject
    lateinit var factory: MainViewModel.MainViewModelFactory.Factory

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initData()
        initListener()
    }

    private fun initData() {
        model.getTasks()
        model.tasksLiveData.observe(viewLifecycleOwner, {
            recyclerAdapter.submitList(it)
        })
    }

    private fun initListener() {
        binding.btnAddTask.setOnClickListener {
            findNavController().navigate(R.id.navigate_to_add_new_task)
        }
    }

    private fun initRecycler() {
        recyclerAdapter = TasksListAdapter(this)
        binding.rcvTasks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvTasks.adapter = recyclerAdapter

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemOnClicked(item: ModelTask) {
        val bundle = bundleOf("task" to item)
        findNavController().navigate(R.id.navigate_to_task, bundle)
    }
}