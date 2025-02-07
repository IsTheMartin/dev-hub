package com.mrtnmrls.devhub.common.data.repository

import com.google.gson.Gson
import com.mrtnmrls.devhub.common.domain.model.NintendoSwitch
import com.mrtnmrls.devhub.common.domain.model.NintendoSwitchGames
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(
    private val mocksProvider: MocksProvider
): MockRepository {
    override fun getNintendoGames(): List<NintendoSwitch> {
        val nintendoGamePayload = Gson().fromJson(
            mocksProvider.getMock(MockCollection.NintendoGamesMock),
            NintendoSwitchGames::class.java
        )
        return nintendoGamePayload.nintendoSwitch
    }
}

interface MockRepository {
    fun getNintendoGames(): List<NintendoSwitch>
}
