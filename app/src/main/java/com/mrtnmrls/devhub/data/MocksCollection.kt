package com.mrtnmrls.devhub.data

import android.content.Context
import javax.inject.Inject

interface MocksProvider {
    fun getMock(mock: MockCollection): String
}

class MocksProviderImpl @Inject constructor(
    private val context: Context
): MocksProvider {
    override fun getMock(mock: MockCollection): String {
        return context.assets
            .open(mock.assetFileName)
            .bufferedReader()
            .use { it.readText() }
    }
}

enum class MockCollection(val assetFileName: String) {
    NintendoGamesMock("nintendo-games-fake-database.json")
}
