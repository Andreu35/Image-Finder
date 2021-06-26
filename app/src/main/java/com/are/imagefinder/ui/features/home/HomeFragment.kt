package com.are.imagefinder.ui.features.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.are.imagefinder.R
import com.are.imagefinder.data.model.Item
import com.are.imagefinder.data.pref.AppPreferences
import com.are.imagefinder.databinding.FragmentHomeBinding
import com.are.imagefinder.extensions.autoCleared
import com.are.imagefinder.extensions.goneUnless
import com.are.imagefinder.ui.BaseFragment
import com.are.imagefinder.utils.Constants
import com.are.imagefinder.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()

    private var querySearch: String = ""
    private var isFirstTime = true

    @Inject
    lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)

        val actionMenuItem = menu.findItem(R.id.nav_search)
        val searchView = actionMenuItem.actionView as SearchView
        searchView.isIconified = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                fetch(query)
                preferences.setString(Constants.PREF_QUERY, query)
                actionMenuItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setUpToolbar(binding.toolbar, showTitle = true, showHome = false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeToRefresh.setOnRefreshListener {
            fetchData()
        }

        viewModel.homeList.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.goneUnless(false)
                    if (it.data != null && !it.data.items.isNullOrEmpty()) {
                        pushAdapter(it.data.items)
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.goneUnless(false)
                }
                Resource.Status.LOADING -> {
                    binding.progressBar.goneUnless(true)
                }
            }
        })
    }

    /**
     * Get the searched pictures from API
     */
    private fun fetchPicturesByTag(tags: String) {
        querySearch = tags
        binding.progressBar.goneUnless(true)
        binding.recyclerView.goneUnless(true)
        viewModel.searchPictureByTags(tags)
    }

    /**
     * Get the searched movies from API by next page
     */
    private fun fetchRecentUploadedPictures() {
        binding.progressBar.goneUnless(true)
        binding.recyclerView.goneUnless(true)
        viewModel.searchRecentUploadedPictures()
    }

    /**
     * Initialize the Adapter if null and bind to RecyclerView.
     * If not null, insert new items to the list
     * @param items Item List
     */
    private fun pushAdapter(items: MutableList<Item>) {
        binding.recyclerView.apply {
            adapter = HomeAdapter(items).apply {
                itemClickListener = { _, items, position ->
                    val bundle = Bundle()
                    bundle.putInt(Constants.POSITION, position)
                    bundle.putSerializable(Constants.ITEMS, items as Serializable)
                    openNewFragment(R.id.action_home_to_details, bundle)
                }
                binding.swipeToRefresh.isRefreshing = false
            }
        }
    }

    /**
     * Fetch new data from query if not null or blank.
     * If query is null or blank, fetch recent uploaded.
     */
    private fun fetchData() {
        if(preferences.contains(Constants.PREF_QUERY)){
            preferences.getString(Constants.PREF_QUERY, "")?.let {
                fetch(it)
            }
        } else {
            fetch(null)
        }
    }

    private fun fetch(query: String?){
        if (query.isNullOrBlank()) {
            fetchRecentUploadedPictures()
        } else {
            fetchPicturesByTag(query)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFirstTime) {
            isFirstTime = false
            fetchData()
        }
    }
}