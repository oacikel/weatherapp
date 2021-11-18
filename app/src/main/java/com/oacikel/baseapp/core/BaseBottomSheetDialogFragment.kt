package com.oacikel.baseapp.core

import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oacikel.baseapp.AppSettings
import com.oacikel.baseapp.StaticUser
import com.oacikel.baseapp.User
import javax.inject.Inject

open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {


    @Inject
    lateinit var userDataStore: DataStore<User>
    @Inject
    lateinit var appSettingsDataStore: DataStore<AppSettings>
    @Inject
    lateinit var sUserDataStore: DataStore<StaticUser>

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.add(this, tag)
            ft.commit()
        } catch (e: IllegalStateException) {
            //handle this exception
        }
    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window!!.setLayout(width, height)
            //val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), UtilityFunctions.dpToPx(requireContext(), 0))
            //dialog.window!!.setBackgroundDrawable(inset)
            dialog.setCanceledOnTouchOutside(false)
        }
    }
}
