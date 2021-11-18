package com.oacikel.baseapp.viewModel

import com.oacikel.baseapp.api.ApiResponse
import com.oacikel.baseapp.db.entity.marvelEntities.CharacterEntity
import com.oacikel.baseapp.repository.CharacterRepository
import com.oacikel.baseapp.repository.UserRepository
import com.oacikel.baseapp.util.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject
constructor(val characterRepository: CharacterRepository) : BaseViewModel() {
    val charactersLiveData: SingleLiveEvent<ApiResponse<List<CharacterEntity>>>

    init {
        this.charactersLiveData = SingleLiveEvent()
    }

    fun getCharacters(){
        characterRepository.getCharactersWithoutSaving(charactersLiveData)
    }
}