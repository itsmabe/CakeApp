package com.me.cakeapp.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.me.cakeapp.view.adapter.CakeAdapter
import com.me.cakeapp.viewmodel.HomeViewModel
import com.me.cakeapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cakes.*

const val KEY_CAKE_OBJECT = "key_cake_object"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()
    private var adapter: CakeAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCakesLD().observe(this, { cakes ->
            title.visibility = VISIBLE
            refreshLayout.isRefreshing = false
            // Updating list with deleting duplicate entries and sort them by name.
            adapter?.updateCakes(cakes.distinct().sortedBy { it.title })
        })
        viewModel.getErrorLD()
            .observe(this, { resId ->
                refreshLayout.isRefreshing = false
                // Displaying an error message if the list cannot be loaded.
                Snackbar.make(refreshLayout, getString(resId), Snackbar.LENGTH_LONG).show()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_cakes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CakeAdapter(requireContext())
        adapter?.onClickListener = { cake ->
            // Showing a bottom sheet dialog to display item details.
            val dialog = CakeDetailsFragment()
            dialog.arguments = Bundle().apply { putParcelable(KEY_CAKE_OBJECT, cake) }
            dialog.showNow(
                requireActivity().supportFragmentManager,
                CakeDetailsFragment::class.java.canonicalName
            )
        }

        rvCakes.adapter = adapter

        // Applying pull to refresh to reload data or retry when an error is presented.
        refreshLayout.setOnRefreshListener { viewModel.getCakes() }
        refreshLayout.isRefreshing = true

        viewModel.getCakes()
    }
}