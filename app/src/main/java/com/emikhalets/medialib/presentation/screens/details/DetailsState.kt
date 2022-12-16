package com.emikhalets.medialib.presentation.screens.details

import com.emikhalets.medialib.data.entity.support.ViewListItem

sealed class DetailsState {
    object Loading : DetailsState()
    object ItemEmpty : DetailsState()
    object ItemDeleted : DetailsState()
    data class Item(val item: ViewListItem?) : DetailsState()
    data class Error(val message: String?) : DetailsState()
}
