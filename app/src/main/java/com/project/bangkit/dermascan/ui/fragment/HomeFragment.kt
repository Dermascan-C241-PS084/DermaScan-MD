package com.project.bangkit.dermascan.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.bangkit.dermascan.databinding.FragmentHomeBinding
import com.project.bangkit.dermascan.ui.scan.ScanActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set onClickListener for the logout button
//        binding.tvContainArticle.setOnClickListener {
//            // Handle the button click event
//           Intent(requireContext(), Article::class.java).also {
//                startActivity(it)
//            }
//            // You can add your logout logic here
//        }

        // Set onClickListener for the scan button
        binding.iButtonScan.setOnClickListener {
            // Handle the button click event
            Intent(requireContext(), ScanActivity::class.java).also {
                startActivity(it)
            }
            // You can add your scan logic here
        }

        // Set text for tv_name_account
//        binding.tvNameAccount.text = getString(R.string.welcome_user)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
