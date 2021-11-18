package com.oacikel.baseapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.oacikel.baseapp.MainActivity
import com.oacikel.baseapp.R
import com.oacikel.baseapp.binding.listAdapters.CharactersAdapter
import com.oacikel.baseapp.binding.listAdapters.ComicsAdapter
import com.oacikel.baseapp.core.BaseInjectableFragment
import com.oacikel.baseapp.databinding.FragmentMainBinding
import com.oacikel.baseapp.db.entity.marvelEntities.CharacterEntity
import com.oacikel.baseapp.db.entity.marvelEntities.ResourceListEntity
import com.oacikel.baseapp.db.entity.marvelEntities.SummaryViewEntity
import com.oacikel.baseapp.di.Injectable
import com.oacikel.baseapp.ui.callback.ListItemFocusCallback
import com.oacikel.baseapp.viewModel.MainViewModel

class MainFragment : BaseInjectableFragment<MainViewModel, FragmentMainBinding>(),
    Injectable,ListItemFocusCallback{

    override val layoutResourceId: Int = R.layout.fragment_main
    override val viewModelClass: Class<MainViewModel> = MainViewModel::class.java
    private val charactersAdapter by lazy { CharactersAdapter(this) }
    private val comicsAdapter by lazy { ComicsAdapter() }

    companion object {
    }

    override fun onCreatePersistentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return binding.root
    }

    override fun onPersistentViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onPersistentViewCreated(view, savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        init()
    }

    private fun init() {
        //binding sets
        binding.viewModel = viewModel
        binding.activity = (activity as MainActivity)
        binding.fragment = this@MainFragment
        addAdapterToCharacterList()
        getCharacters()
    }

    fun getCharacters(){
        viewModel.charactersLiveData.observe(viewLifecycleOwner){
            if(it.data!=null){
                charactersAdapter.submitList(it.data!!.content as MutableList<CharacterEntity>)
            }
        }
        viewModel.getCharacters()
    }

    // TODO: Bu metodlar extensio olarak konulsun 
    fun  addAdapterToCharacterList(){
        binding.viewPagerCharacters.apply {
            adapter = charactersAdapter
        }
    }

    fun  addAdapterToComicsList(comics: List<SummaryViewEntity>?){
        binding.recyclerViewComics.apply {
            adapter = comicsAdapter
            comicsAdapter.submitList(comics as MutableList<SummaryViewEntity>)
        }
    }
    override fun onComicListAvailable(comics: List<SummaryViewEntity>?) {
        addAdapterToComicsList(comics)
    }
}