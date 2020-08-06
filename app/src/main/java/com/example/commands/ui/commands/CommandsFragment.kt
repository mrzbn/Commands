package com.example.commands.ui.commands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commands.R
import com.example.commands.utils.DataState
import com.example.commands.viewmodels.CommandsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_commands.*

@AndroidEntryPoint
class CommandsFragment : Fragment() {

    private val viewModel: CommandsViewModel by activityViewModels()

    private lateinit var commandsAdapter: CommandsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_commands, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            findNavController(this).navigate(R.id.action_commandsFragment_to_addCommandFragment)
        }
        setupRecyclerView()
        subscribeObservers()
    }


    private fun setupRecyclerView() {
        commandsAdapter = CommandsListAdapter()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = commandsAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.commandsState.observe(viewLifecycleOwner, Observer {dataState ->
            when (dataState) {
                is DataState.Success -> {
                    displayProgressBar(false)
                    displayError(false)
                    if (dataState.data.size == 0)
                        empty_text.visibility = View.VISIBLE
                    else {
                        empty_text.visibility = View.GONE
                        displayCommandsRecyclerView(true)
                        commandsAdapter.submitList(dataState.data)
                    }
                }

                is DataState.Error -> {
                    displayCommandsRecyclerView(false)
                    displayProgressBar(false)
                    displayError(true)

                    val msg = dataState.exception.message
                    if(msg != null)
                        error_text.text = msg
                    else
                        error_text.text = "Unknown Error"
                }

                is DataState.Loading -> {
                    displayError(false)
                    displayCommandsRecyclerView(false)
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(isDisplayed: Boolean) {
        error_text.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayCommandsRecyclerView(isDisplayed: Boolean) {
        recycler_view.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }
}