package idv.hsu.exchangediary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import idv.hsu.exchangediary.R
import idv.hsu.exchangediary.databinding.FragmentHomeBinding
import idv.hsu.exchangediary.ui.utils.getNowDateString
import java.text.DateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textToday.text = getNowDateString()
        binding.buttonWriteDiary.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionHomeToWriting())
        }
//        binding.textToday.text = Calendar.getInstance().time.toString()
    }
}