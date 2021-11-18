package com.oacikel.baseapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oacikel.baseapp.R
import com.oacikel.baseapp.core.BaseInjectableDialogFragment
import com.oacikel.baseapp.databinding.DialogErrorBinding
import com.oacikel.baseapp.di.Injectable
import com.oacikel.baseapp.ui.callback.DialogOkButtonCallback
import com.oacikel.baseapp.viewModel.BaseViewModel

class ErrorDialogFragment : BaseInjectableDialogFragment<BaseViewModel, DialogErrorBinding>(),
    Injectable {
    override val layoutResourceId: Int = R.layout.dialog_error
    override val viewModelClass: Class<BaseViewModel> = BaseViewModel::class.java

    companion object {
        var errorMessage: String? = null
        var onlyMessage: Boolean? = false
        var dialogOkButtonCallback: DialogOkButtonCallback? = null
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
        binding.dialogFragment = this@ErrorDialogFragment
        binding.errorMessage = errorMessage
        binding.buttonOk.setOnClickListener {
            this@ErrorDialogFragment.dismiss()
            dialogOkButtonCallback?.onOKClicked()
        }
        if (onlyMessage != null && onlyMessage as Boolean)
            binding.textViewTitle.visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window!!.setLayout(width, height)
            dialog.setCanceledOnTouchOutside(true)
        }
    }
}