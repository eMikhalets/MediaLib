package com.emikhalets.medialib.domain.entities.ratings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.medialib.R

enum class CrewType {
    DIRECTOR, ACTOR, WRITER;

    companion object {

        @Composable
        fun CrewType.getTypeName(): String {
            return when (this) {
                DIRECTOR -> stringResource(id = R.string.crew_type_director)
                ACTOR -> stringResource(id = R.string.crew_type_actor)
                WRITER -> stringResource(id = R.string.crew_type_writer)
            }
        }
    }
}