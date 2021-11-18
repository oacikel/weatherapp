package com.oacikel.baseapp.core

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.datastore.core.DataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.oacikel.baseapp.AppSettings
import com.oacikel.baseapp.R
import com.oacikel.baseapp.StaticUser
import com.oacikel.baseapp.User
import com.oacikel.baseapp.databinding.ActivityBaseBinding
import com.oacikel.baseapp.util.ContextUtils
import com.oacikel.baseapp.viewModel.BaseViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.*
import javax.inject.Inject

abstract class BaseInjectableActivity<VModel : ViewModel, DataBinding : ViewDataBinding> :
    BaseActivity(), HasSupportFragmentInjector {
    @get:LayoutRes
    protected abstract val layoutResourceId: Int

    @Inject
    lateinit var factory: BaseViewModelFactory

    abstract val viewModelClass: Class<VModel>
    lateinit var viewModel: VModel
    lateinit var binding: DataBinding
    lateinit var dataBinding: ActivityBaseBinding

    @Inject
    lateinit var userDataStore: DataStore<User>

    @Inject
    lateinit var appSettingsDataStore: DataStore<AppSettings>

    @Inject
    lateinit var sUserDataStore: DataStore<StaticUser>

    override fun attachBaseContext(newBase: Context) {
        // get chosen language from shread preference
        val sharedPref = newBase.getSharedPreferences(newBase.getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        val defaultValue = Locale.getDefault().language
        val langCode = sharedPref.getString("LangCode", defaultValue)

        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, Locale(langCode))
        super.attachBaseContext(localeUpdatedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)
        viewModel = createViewModel()
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        binding = DataBindingUtil.inflate(
            layoutInflater,
            layoutResourceId,
            dataBinding.llMainContent,
            true
        )

    }

    //abstract fun getLayoutID(): Int

    private fun createViewModel(): VModel {
        viewModel = ViewModelProviders.of(this, factory).get(viewModelClass)
        return viewModel
    }

    fun <T : ViewDataBinding> putContentView(id: Int): T {
        return DataBindingUtil.inflate(layoutInflater, id, dataBinding.llMainContent, true)
    }
    //abstract fun initViewModelClass(): Class<VModel>
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return dispatchingAndroidInjector
    }


    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}