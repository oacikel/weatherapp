package com.oacikel.baseapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import com.oacikel.baseapp.R
import com.oacikel.baseapp.core.BaseInjectableDialogFragment
import com.oacikel.baseapp.databinding.DialogLoadingBinding
import com.oacikel.baseapp.di.Injectable
import com.oacikel.baseapp.viewModel.CommonDialogViewModel

class LoadingDialogFragment :
    BaseInjectableDialogFragment<CommonDialogViewModel, DialogLoadingBinding>(),
    Injectable {
    override val layoutResourceId: Int = R.layout.dialog_loading
    override val viewModelClass: Class<CommonDialogViewModel> = CommonDialogViewModel::class.java

    companion object {
        var loadingMessage: String? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        binding.dialogFragment = this@LoadingDialogFragment
        binding.loadingMessage = loadingMessage
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window!!.setLayout(width, height)
            dialog.setCanceledOnTouchOutside(false)

            val linkedinIcon =
                resources.getDrawable(R.drawable.ic_loading_empty_circle, requireActivity().theme)

            binding.apLoading.setProgressImage(linkedinIcon.toBitmap(), 24.0f)
            binding.apLoading.setCircleSize(0.0f)
            binding.apLoading.setArchSize(32.0f)
            binding.apLoading.setArchLength(210)
            binding.apLoading.setArchStroke(8.85f)
            binding.apLoading.setArchSpeed(5)

            //val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), UtilityFunctions.dpToPx(requireContext(), 16))
            //dialog.window!!.setBackgroundDrawable(inset)
        }
    }
}