package com.example.commands.ui.addcommand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.commands.R
import com.example.commands.utils.DataState
import com.example.commands.viewmodels.CommandsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_add_command.*
import kotlinx.android.synthetic.main.fragment_commands.*
import kotlinx.android.synthetic.main.fragment_commands.fab


class AddCommandFragment : Fragment() {

    private val TAG = "AddCommandFragment"

    private val viewModel: CommandsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_command, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            val howTo = how_to.text.toString()
            val line = line.text.toString()
            viewModel.saveCommand(howTo, line).observe(viewLifecycleOwner, Observer {dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        displayProgressFab(false)
                        NavHostFragment.findNavController(this).popBackStack()
                    }

                    is DataState.Error -> {
                        displayProgressFab(false)
                        displayError(dataState.exception.message)
                    }

                    is DataState.Loading -> {
                        displayProgressFab(true)
                    }
                }
            })
        }
    }

    private fun displayError(msg: String?) {
        if(msg != null)
            error_text.text = msg
        else
            error_text.text = "Unknown Error"
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun displayProgressFab(isDisplayed: Boolean) {
        if(isDisplayed) {
            add_command_progress_bar.visibility = View.VISIBLE
            fab.isEnabled = false
        } else {
            add_command_progress_bar.visibility = View.GONE
            fab.isEnabled = true
        }

    }
}