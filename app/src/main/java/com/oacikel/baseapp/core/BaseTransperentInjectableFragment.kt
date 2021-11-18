package com.oacikel.baseapp.core

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.datastore.core.DataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.oacikel.baseapp.AppSettings
import com.oacikel.baseapp.R
import com.oacikel.baseapp.StaticUser
import com.oacikel.baseapp.User
import com.oacikel.baseapp.di.Injectable
import com.oacikel.baseapp.util.Constants
import com.oacikel.baseapp.util.FontCache
import com.oacikel.baseapp.viewModel.BaseViewModel
import com.oacikel.baseapp.viewModel.BaseViewModelFactory
import timber.log.Timber
import java.io.Serializable
import javax.inject.Inject

abstract class BaseTransperentInjectableFragment<VModel : BaseViewModel, DataBinding : ViewDataBinding> : BaseFragment(),
    Injectable {
    @get:LayoutRes
    protected abstract val layoutResourceId: Int

    @Inject
    lateinit var factory: BaseViewModelFactory

    abstract val viewModelClass: Class<VModel>
    lateinit var viewModel: VModel
    lateinit var binding: DataBinding

    @Inject
    lateinit var userDataStore: DataStore<User>
    @Inject
    lateinit var appSettingsDataStore: DataStore<AppSettings>
    @Inject
    lateinit var sUserDataStore: DataStore<StaticUser>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = createViewModel()
    }


    private fun createViewModel(): VModel {
        viewModel = ViewModelProviders.of(this, factory).get(viewModelClass)
        return viewModel
    }

    companion object {
        fun <T> newInstance(fragment: T, o: Any? = null): T {

            val bundle = Bundle()
            if (o != null) {
                when (o) {
                    is String -> bundle.putString(Constants.FRAGMENT_DATA, o)
                    is Boolean -> bundle.putBoolean(Constants.FRAGMENT_DATA, o)
                    is Int -> bundle.putInt(Constants.FRAGMENT_DATA, o)
                    is Float -> bundle.putFloat(Constants.FRAGMENT_DATA, o)
                    is Byte -> bundle.putByte(Constants.FRAGMENT_DATA, o)
                    is ByteArray -> bundle.putByteArray(Constants.FRAGMENT_DATA, o)
                    is Char -> bundle.putChar(Constants.FRAGMENT_DATA, o)
                    is Short -> bundle.putShort(Constants.FRAGMENT_DATA, o)
                    is IntArray -> bundle.putIntArray(Constants.FRAGMENT_DATA, o)
                    is Parcelable -> bundle.putParcelable(Constants.FRAGMENT_DATA, o)
                    is Serializable -> bundle.putSerializable(Constants.FRAGMENT_DATA, o)
                }
            }

            (fragment as Fragment).arguments = bundle
            return fragment
        }
    }

    override fun startActivity(intent: Intent?) {
        activity?.startActivity(intent)
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    fun onBackPressed() {
        activity?.onBackPressed()
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    fun setCollapsingToolbarTitle(collapsingToolbarLayout: CollapsingToolbarLayout, title: String){
        collapsingToolbarLayout.title = title
        collapsingToolbarLayout.apply {
            setCollapsedTitleTypeface(FontCache["fonts/red_hat_display_bold.ttf", requireActivity().applicationContext])
            setExpandedTitleTypeface(FontCache["fonts/red_hat_display_bold.ttf", requireActivity().applicationContext])
        }
    }

    private var persistingView: View? = null

    private fun persistingView(view: View): View {
        val root = persistingView
        if (root == null) {
            persistingView = view
            return view
        } else {
            (root.parent as? ViewGroup)?.removeView(root)
            return root
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        activity?.window?.statusBarColor = Color.TRANSPARENT

        viewModel = createViewModel()
        val p = if (persistingView == null) onCreatePersistentView(inflater, container, savedInstanceState) else persistingView // prevent inflating
        if (p != null) {
            return persistingView(p)
        }
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return binding.root
    }
    protected open fun onCreatePersistentView(inflater: LayoutInflater,
                                              container: ViewGroup?,
                                              savedInstanceState: Bundle?): View? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (persistingView != null) {
            onPersistentViewCreated(view, savedInstanceState)
        }
    }

    protected open fun onPersistentViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onPersistentViewCreated")
    }
}