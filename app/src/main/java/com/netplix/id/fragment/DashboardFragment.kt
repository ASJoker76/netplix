package com.netplix.id.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.netplix.id.R
import com.netplix.id.adapter.GenreHorizontalAdapter
import com.netplix.id.adapter.MovieAdapter
import com.netplix.id.adapter.MovieHorizontalAdapter
import com.netplix.id.adapter.MovieHorizontalLastAdapter
import com.netplix.id.databinding.FragmentDashboardBinding
import com.netplix.id.model.Genre
import com.netplix.id.viewmodel.DashboardViewModel
import com.netplix.id.model.Result
import com.ramotion.cardslider.CardSliderLayoutManager
import com.ramotion.cardslider.CardSnapHelper
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter


class DashboardFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var genreHorizontalAdapter: GenreHorizontalAdapter
    private lateinit var movieHorizontalAdapter: MovieHorizontalAdapter

    private val genreArrayList: ArrayList<Genre> = java.util.ArrayList<Genre>()
    private val movieArrayList: ArrayList<Result> = java.util.ArrayList<Result>()

    private var binding: FragmentDashboardBinding? = null
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding!!.getRoot()

        loadRecylerView()
        getData()

        return root
    }

    private fun loadRecylerView() {
        movieHorizontalAdapter = MovieHorizontalAdapter{
                position ->  onListItemClick(position)
        }
        binding!!.rvNowPlaying.setAdapter(AlphaInAnimationAdapter(movieHorizontalAdapter!!))
        binding!!.rvNowPlaying.setHasFixedSize(true)
        binding!!.rvNowPlaying.setLayoutManager(CardSliderLayoutManager(requireActivity()))
        CardSnapHelper().attachToRecyclerView(binding!!.rvNowPlaying)
        val alphaInAnimationAdapter = AlphaInAnimationAdapter(movieHorizontalAdapter!!)
        alphaInAnimationAdapter.setDuration(1000)
        alphaInAnimationAdapter.setInterpolator(OvershootInterpolator())
        alphaInAnimationAdapter.setFirstOnly(false)

        genreHorizontalAdapter = GenreHorizontalAdapter{
                position ->  onListItemClick2(position)
        }
        binding!!.rvGendre.setAdapter(AlphaInAnimationAdapter(genreHorizontalAdapter!!))
        binding!!.rvGendre.setHasFixedSize(true)
        binding!!.rvGendre.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL ,false)
        val alphaInAnimationAdapter2 = AlphaInAnimationAdapter(genreHorizontalAdapter!!)
        alphaInAnimationAdapter2.setDuration(1000)
        alphaInAnimationAdapter2.setInterpolator(OvershootInterpolator())
        alphaInAnimationAdapter2.setFirstOnly(false)

        movieAdapter = MovieAdapter{
                position ->  onListItemClick3(position)
        }
        binding!!.rvFilm.setAdapter(AlphaInAnimationAdapter(movieAdapter!!))
        binding!!.rvFilm.setHasFixedSize(true)
        binding!!.rvFilm.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL ,false)
        val alphaInAnimationAdapter3 = AlphaInAnimationAdapter(movieAdapter!!)
        alphaInAnimationAdapter3.setDuration(1000)
        alphaInAnimationAdapter3.setInterpolator(OvershootInterpolator())
        alphaInAnimationAdapter3.setFirstOnly(false)
    }

    private fun getData() {
        dashboardViewModel.apply {
            setListMovie()
            setListGenre()
            mainListMovie.observe(requireActivity()) {

                movieArrayList.clear()
                movieHorizontalAdapter?.setDataList(it.results as ArrayList<Result>)

                movieAdapter?.setDataList(it.results as ArrayList<Result>)
                movieArrayList.addAll(it.results)
            }
            mainListGendre.observe(requireActivity()){
                genreHorizontalAdapter?.setDataList(it.genres as ArrayList<Genre>)
                genreArrayList.addAll(it.genres)
            }
            mainListByGendre.observe(requireActivity()){
                movieArrayList.clear()
                movieAdapter?.setDataList(it.results as ArrayList<Result>)
                movieArrayList.addAll(it.results)
            }
        }
    }

    private fun onListItemClick3(position: Int) {
        Log.e("posisi", position.toString())

        val bundle = Bundle()
        bundle.putParcelableArrayList(
            "detailovie", movieArrayList
        )
        bundle.putInt("posisilist",position)
        val fragementIntent = DetailMovieFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.fl_view, fragementIntent)
        fragementIntent.setArguments(bundle)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun onListItemClick2(position: Int) {
        binding!!.tvRekomendasi.setText(genreArrayList[position].name)
        dashboardViewModel.apply {
            setListbygenre(genreArrayList[position].id)
        }
    }

    private fun onListItemClick(position: Int) {
        //Toast.makeText(this, mRepos[position].name, Toast.LENGTH_SHORT).show()
    }
}