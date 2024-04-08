package com.example.gitbuddy.ui.detail.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitbuddy.data.remote.model.ItemsItem
import com.example.gitbuddy.databinding.FragmentFollowBinding
import com.example.gitbuddy.ui.adapter.FollowAdapter
import com.example.gitbuddy.ui.detail.DetailViewModel
import com.example.gitbuddy.utils.UserResult

@Suppress("UNCHECKED_CAST")
class FollowFragment : Fragment() {

    private val detailViewModel by activityViewModels<DetailViewModel>()
    private var binding: FragmentFollowBinding? = null
    private val adapter by lazy {
        FollowAdapter{

        }
    }
    private var position = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollow?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }

        when (position){
            FOLLOWERS -> {
                detailViewModel.followerUserResult.observe(viewLifecycleOwner, this ::manageFollowResult)
            }
            FOLLOWING -> {
                detailViewModel.followingUserResult.observe(viewLifecycleOwner,this ::manageFollowResult)

            }
        }
    }

    private fun manageFollowResult(state:UserResult){
        when(state) {
            is UserResult.Success<*> -> {
                adapter.setData(state.data as MutableList<ItemsItem>)
            }
            is UserResult.Error -> {
                Toast.makeText(requireActivity(),state.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is UserResult.Loading -> {
                binding?.progressBarFollow?.isVisible = state.isLoading
            }
        }
    }
    companion object {
        const val FOLLOWING = 0
        const val FOLLOWERS = 1
        @JvmStatic
        fun newInstance(position: Int) = FollowFragment().apply {
            this.position = position
        }
    }
}