package com.D121201044.taskmanagement.fragments



import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.D121201044.taskmanagement.MainActivity
import com.D121201044.taskmanagement.R
import com.D121201044.taskmanagement.adapter.NoteAdapter
import com.D121201044.taskmanagement.databinding.FragmentHomeBinding
import com.D121201044.taskmanagement.model.Task
import com.D121201044.taskmanagement.viewmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home),
    SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        setUpRecyclerView()

        binding.fabAddNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter()

        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            notesViewModel.getAllNote().observe(viewLifecycleOwner, { note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            })
        }

    }

    private fun updateUI(task: List<Task>) {
        if (task.isNotEmpty()) {
            binding.cardView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.cardView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)
        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this)

    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        /*if (query != null) {
            searchNote(query)
        }*/
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if (newText != null) {
            searchNote(newText)
        }
        return true
    }


    private fun searchNote(query: String?) {
        val searchQuery = "%$query%"
        notesViewModel.searchNote(searchQuery).observe(
            this, { list ->
                noteAdapter.differ.submitList(list)
            }
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}