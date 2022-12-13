package com.D121201044.taskmanagement.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.D121201044.taskmanagement.MainActivity
import com.D121201044.taskmanagement.R
import com.D121201044.taskmanagement.databinding.FragmentUpdateNoteBinding
import com.D121201044.taskmanagement.model.Task
import com.D121201044.taskmanagement.toast
import com.D121201044.taskmanagement.viewmodel.NoteViewModel




class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentTask: Task
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        currentTask = args.note!!

        val category = resources.getStringArray(R.array.category)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, category)

        binding.NoteCategoryUpdate.setAdapter(arrayAdapter)
        binding.etNoteBodyUpdate.setText(currentTask.noteBody)
        binding.etNoteTitleUpdate.setText(currentTask.noteTitle)

        binding.fabDone.setOnClickListener {

            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()
            val category= binding.NoteCategoryUpdate.text.toString().trim()

            if (title.isNotEmpty()) {
                val task = Task(currentTask.id, title, body, category)
                noteViewModel.updateNote(task)

                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)

            } else {
                activity?.toast("Enter a note title please")
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to permanently delete this note?")
            setPositiveButton("DELETE") { _, _ ->
                noteViewModel.deleteNote(currentTask)
                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }
            setNegativeButton("CANCEL", null)
        }.create().show()

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}