package com.me.cakeapp.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.me.cakeapp.R
import com.me.cakeapp.model.Cake
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*

@AndroidEntryPoint
class CakeDetailsFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
        isCancelable = true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        LayoutInflater.from(requireContext()).inflate(R.layout.fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(KEY_CAKE_OBJECT) }?.apply {
            val cake = getParcelable<Cake>(KEY_CAKE_OBJECT)
            Glide.with(requireContext())
                .load(cake?.image)
                .placeholder(R.drawable.ic_error_24)
                .into(image)
            image.clipToOutline = true
            text.text = cake?.title?.lowercase()?.capitalize()
            description.text = cake?.desc?.lowercase()?.capitalize()
        }
    }


}