package com.oacikel.baseapp.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.oacikel.baseapp.api.Status
import com.oacikel.baseapp.ui.callback.DialogOkButtonCallback
import com.oacikel.baseapp.ui.common.ErrorDialogFragment
import com.oacikel.baseapp.ui.common.LoadingDialogFragment
import com.oacikel.baseapp.ui.common.SuccessDialogFragment
import com.oacikel.baseapp.viewModel.BaseViewModel

object NavigationAdapters {

    @JvmStatic
    @BindingAdapter(
        value = ["loadingManager", "loadingStatus", "errorOkClick"],
        requireAll = false
    )
    fun onLoading(
        view: View,
        fragmentManager: Fragment,
        baseViewModel: BaseViewModel?,
        okClick: DialogOkButtonCallback?
    ) {
        //crash logları için bulabildiğim her çözümü denedm. 4.0.4 te çözülür umarım
        /*
        java.lang.IllegalStateException:
  at androidx.fragment.app.Fragment.getParentFragmentManager (Unknown Source:29)
  at androidx.fragment.app.Fragment.requireFragmentManager (Unknown Source)
  at com.fordotosan.apps.customerapp.binding.NavigationAdapters$onLoading$1$1.onChanged (Unknown Source:4)
  at com.fordotosan.apps.customerapp.binding.NavigationAdapters$onLoading$1$1.onChanged (Unknown Source:2)
  at androidx.lifecycle.LiveData.considerNotify (Unknown Source:29)
  at androidx.lifecycle.LiveData.c (Unknown Source:44)
  at androidx.lifecycle.LiveData.setValue (Unknown Source:14)
  at androidx.lifecycle.MutableLiveData.setValue (Unknown Source)
  at com.fordotosan.apps.customerapp.util.SingleLiveEvent.setValue (Unknown Source:6)
  at androidx.lifecycle.LiveData$1.run (Unknown Source:18)
  at android.os.Handler.handleCallback (Handler.java:938)
  at android.os.Handler.dispatchMessage (Handler.java:99)
  at android.os.Looper.loop (Looper.java:246)
  at android.app.ActivityThread.main (ActivityThread.java:8528)
  at java.lang.reflect.Method.invoke (Native Method)
  at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run (RuntimeInit.java:602)
  at com.android.internal.os.ZygoteInit.main (ZygoteInit.java:1130)
         */
        view?.post {
            try {
                fragmentManager.lifecycleScope.launchWhenResumed {
                    if (fragmentManager.isAdded) {
                        var loadingDialogFragment: LoadingDialogFragment

                        baseViewModel?.status?.observeForever {

                            val showingLoading = fragmentManager.requireFragmentManager()
                                .findFragmentByTag("LoadingDialogFragment")
                            if (baseViewModel.status.value == Status.LOADING) {
                                if (showingLoading == null) {
                                    LoadingDialogFragment.loadingMessage =
                                        baseViewModel.loadingMessage.value
                                    loadingDialogFragment = LoadingDialogFragment()
                                    loadingDialogFragment.isCancelable = false

                                    loadingDialogFragment.show(
                                        fragmentManager.requireFragmentManager(),
                                        "LoadingDialogFragment"
                                    )
                                    fragmentManager.requireFragmentManager()
                                        .executePendingTransactions()
                                }
                            } else {
                                val showingLoading = fragmentManager.requireFragmentManager()
                                    .findFragmentByTag("LoadingDialogFragment")
                                if (showingLoading != null) {
                                    (showingLoading as LoadingDialogFragment).dismiss()
                                    fragmentManager.requireFragmentManager()
                                        .executePendingTransactions()
                                }


                                if (baseViewModel.status.value == Status.ERROR) {
                                    onError(fragmentManager, baseViewModel, okClick)
                                } else if (baseViewModel.status.value == Status.ONLY_MESSAGE_ERROR) {
                                    onError(fragmentManager, baseViewModel, okClick, true)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                //fragment assocation error
            }

        }

    }

    private fun onError(
        fragmentManager: Fragment,
        baseViewModel: BaseViewModel,
        dialogOkButtonCallback: DialogOkButtonCallback? = null,
        onlyMessage: Boolean? = false
    ) {
        baseViewModel.errorMessage.observeForever {
            ErrorDialogFragment.errorMessage = it
            ErrorDialogFragment.dialogOkButtonCallback = dialogOkButtonCallback
            if (onlyMessage != null && onlyMessage)
                ErrorDialogFragment.onlyMessage = onlyMessage
            val errorDialogFragment = ErrorDialogFragment()
            errorDialogFragment.isCancelable = true
            val showingError = fragmentManager.requireFragmentManager()
                .findFragmentByTag("ErrorDialogFragment")
            if (showingError == null) {
                errorDialogFragment.show(
                    fragmentManager.requireFragmentManager(),
                    "ErrorDialogFragment"
                )
                fragmentManager.requireFragmentManager().executePendingTransactions()
            } else {
                (showingError as ErrorDialogFragment).binding.errorMessage = it
            }
        }

    }

    @JvmStatic
    @BindingAdapter(
        value = ["successManager", "successStatus", "successCallback"],
        requireAll = false
    )
    fun onSuccess(
        view: View,
        fragmentManager: Fragment,
        baseViewModel: BaseViewModel?,
        okClick: DialogOkButtonCallback?
    ) {
        view?.post {
            baseViewModel?.status?.observeForever {
                if (baseViewModel.status.value == Status.SUCCESS) {
                    SuccessDialogFragment.successMessage = baseViewModel.successMessage.value
                    SuccessDialogFragment.dialogOkButtonCallback = okClick
                    val successDialogFragment = SuccessDialogFragment()
                    successDialogFragment.isCancelable = false
                    baseViewModel.status.value = Status.NULL
                    successDialogFragment.show(
                        fragmentManager.requireFragmentManager(),
                        "SuccessDialogFragment"
                    )
                    fragmentManager.requireFragmentManager().executePendingTransactions()
                }
            }
        }
    }
}