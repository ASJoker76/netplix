package com.netplix.id.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.netplix.id.R
import com.netplix.id.adapter.SliderAdapter
import com.netplix.id.databinding.FragmentDashboardBinding
import com.netplix.id.model.Image


class DashboardFragment : Fragment() {

    private var adapterImageSlider: SliderAdapter? = null
    private var runnable: Runnable? = null
    private val handler = Handler()

    private var dataItems = arrayListOf<Image>(
        Image(R.drawable.movie, "Dui fringilla ornare finibus, orci odio", "Foggy Hill"),
        Image(R.drawable.movie, "Mauris sagittis non elit quis fermentum", "The Backpacker"),
        Image(R.drawable.movie, "Mauris ultricies augue sit amet est sollicitudin", "River Forest"),
        Image(R.drawable.movie, "Suspendisse ornare est ac auctor pulvinar", "Mist Mountain"),
        Image(R.drawable.movie, "Vivamus laoreet aliquam ipsum eget pretium", "Side Park")
    )

    private var binding: FragmentDashboardBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding!!.getRoot()

        init()
        setupUI()

        return root
    }

    private fun init() {
        adapterImageSlider = SliderAdapter(requireActivity(), arrayListOf())
        binding!!.pager!!.adapter = adapterImageSlider
    }

    private fun setupUI() {
        getData()

        // displaying selected image first
        binding!!.pager!!.currentItem = 0
        addBottomDots(binding!!.llDots, adapterImageSlider!!.count, 0)
        binding!!.tvTitle?.text = adapterImageSlider!!.getItem(0).name
        binding!!.tvPlace?.text = adapterImageSlider!!.getItem(0).place

        handleViewPager()

        startAutoSlider(adapterImageSlider!!.count)
    }

    private fun getData() {
        retrieveList(dataItems)
    }

    private fun retrieveList(items: List<Image>) {
        adapterImageSlider?.apply {
            setItems(items)
            notifyDataSetChanged()
        }
    }

    private fun handleViewPager() {
        binding!!.pager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                pos: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(pos: Int) {
                binding!!.tvTitle?.text = adapterImageSlider!!.getItem(pos).name
                binding!!.tvPlace?.text = adapterImageSlider!!.getItem(pos).place
                addBottomDots(binding!!.llDots, adapterImageSlider!!.count, pos)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun addBottomDots(llDots: LinearLayout?, size: Int, current: Int) {
        val dots = arrayOfNulls<ImageView>(size)
        llDots!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(requireActivity())
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(15, 15))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle_outline)
            llDots.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[current]!!.setImageResource(R.drawable.shape_circle)
        }
    }

    private fun startAutoSlider(count: Int) {
        runnable = Runnable {
            var pos = binding!!.pager!!.currentItem
            pos += 1
            if (pos >= count) pos = 0
            binding!!.pager!!.currentItem = pos
            handler.postDelayed(runnable!!, 3000)
        }
        handler.postDelayed(runnable!!, 3000)
    }

    override fun onDestroy() {
        if (runnable != null) handler.removeCallbacks(runnable!!)
        super.onDestroy()
    }
}