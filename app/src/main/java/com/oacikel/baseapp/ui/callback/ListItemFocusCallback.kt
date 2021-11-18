package com.oacikel.baseapp.ui.callback

import com.oacikel.baseapp.db.entity.marvelEntities.ResourceListEntity
import com.oacikel.baseapp.db.entity.marvelEntities.SummaryViewEntity

interface ListItemFocusCallback {
    fun onComicListAvailable(comics: List<SummaryViewEntity>?)
}