package idv.hsu.exchangediary.ui.writing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import idv.hsu.exchangediary.R
import idv.hsu.exchangediary.databinding.FragmentWritingBinding
import idv.hsu.exchangediary.ui.utils.getNowDateString
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class WritingFragment : Fragment(R.layout.fragment_writing) {

    private var _binding: FragmentWritingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WritingViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    Timber.d("FREEMAN, ${result.data?.data}")
//                    binding.imagePicker.setImageBitmap(
//                        MediaStore.Images.Media.getBitmap(
//                            requireActivity().contentResolver,
//                            result.data?.data
//                        )
//                    )
                    result.data?.data?.let { uri ->
                        val input = requireActivity().contentResolver.openInputStream(uri)?.let {
                            val file = File(requireActivity().filesDir, "Freeman2.png")
                            it.copyTo(file.outputStream(), 16 * 1024)
                        }
                    }

                    result.data?.data?.let {
                        requireActivity().contentResolver.query(it, null, null, null, null)
                    }?.use {
                        val nameIdx = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        val sizeIdx = it.getColumnIndex(OpenableColumns.SIZE)
                        it.moveToFirst()
                        Timber.d("FREEMAN, name: ${it.getString(nameIdx)}")
                        Timber.d("FREEMAN, size: ${it.getString(sizeIdx)}")
                    }

                    Glide.with(this)
                        .load(result.data?.data)
                        .into(binding.imagePicker)
                }
                else -> {
                    Timber.d("FREEMAN, $result")
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWritingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textToday.text = getNowDateString()
        binding.imagePicker.setOnClickListener {
            startForResult.launch(Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            })
        }
    }
}