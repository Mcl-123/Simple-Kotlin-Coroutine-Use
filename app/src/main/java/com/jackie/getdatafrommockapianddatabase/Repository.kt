package com.jackie.getdatafrommockapianddatabase

import com.jackie.getdatafrommockapianddatabase.logic.db.AndroidVersionDatabase
import com.jackie.getdatafrommockapianddatabase.logic.db.mapToEntityList
import com.jackie.getdatafrommockapianddatabase.logic.db.mapToUiModelList
import com.jackie.getdatafrommockapianddatabase.logic.model.AndroidVersion
import com.jackie.getdatafrommockapianddatabase.logic.network.mockApi
import com.jackie.getdatafrommockapianddatabase.utils.addCoroutineDebugInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber

object Repository {

    private val api = mockApi()
    private val dao = AndroidVersionDatabase.getInstance().androidVersionDao()
    private val scope = AndroidVersionApplication.scope

    suspend fun loadDataFromDb() =
        scope.async {
            dao.getAndroidVersions().mapToUiModelList()
        }.await()

    suspend fun loadDataFromNetwork() =
        withContext(Dispatchers.IO) {
            Timber.d(addCoroutineDebugInfo("network get"))
            val recentAndroidVersions = api.getRecentAndroidVersions()
            insertToDb(recentAndroidVersions)
            recentAndroidVersions
        }

    private suspend fun insertToDb(recentAndroidVersions: List<AndroidVersion>) {
        coroutineScope {
            recentAndroidVersions.mapToEntityList().map {
                Timber.d(addCoroutineDebugInfo("db insert $it"))
                dao.insert(it)
            }
        }
    }

    suspend fun clearDb() =
        scope.async {
            Timber.d(addCoroutineDebugInfo("db clear"))
            dao.clear()
        }.await()
}