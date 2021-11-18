package com.oacikel.baseapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oacikel.baseapp.R
import com.oacikel.baseapp.core.BaseInjectableDialogFragment
import com.oacikel.baseapp.databinding.DialogSuccessBinding
import com.oacikel.baseapp.di.Injectable
import com.oacikel.baseapp.ui.callback.DialogOkButtonCallback
import com.oacikel.baseapp.viewModel.BaseViewModel
import com.oacikel.baseapp.viewModel.CommonDialogViewModel

class SuccessDialogFragment :
    BaseInjectableDialogFragment<CommonDialogViewModel, DialogSuccessBinding>(), Injectable {
    override val layoutResourceId: Int = R.layout.dialog_success
    override val viewModelClass: Class<CommonDialogViewModel> = CommonDialogViewModel::class.java

    companion object {
        var successMessage: String? = null
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
        binding.dialogFragment = this@SuccessDialogFragment
        binding.successMessage = successMessage
        binding.buttonOk.setOnClickListener {
            this@SuccessDialogFragment.dismiss()
            dialogOkButtonCallback?.onOKClicked()
        }
        if (dialogOkButtonCallback != null)
            this@SuccessDialogFragment.dialog?.setOnDismissListener {
                dialogOkButtonCallback?.onOKClicked()
            }
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