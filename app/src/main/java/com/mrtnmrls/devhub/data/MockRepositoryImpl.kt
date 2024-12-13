package com.mrtnmrls.devhub.data

import com.google.gson.Gson
import com.mrtnmrls.devhub.domain.model.NintendoSwitch
import com.mrtnmrls.devhub.domain.model.NintendoSwitchGames
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
