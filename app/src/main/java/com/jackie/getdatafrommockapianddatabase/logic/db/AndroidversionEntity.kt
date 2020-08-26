package com.jackie.getdatafrommockapianddatabase.logic.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jackie.getdatafrommockapianddatabase.logic.model.AndroidVersion
import com.jackie.getdatafrommockapianddatabase.utils.addCoroutineDebugInfo
import timber.log.Timber

@Entity(tableName = "androidversions")
data class AndroidVersionEntity(@PrimaryKey val apiLevel: Int, val name: String) {

}

fun AndroidVersionEntity.mapToUiModel() = AndroidVersion(this.apiLevel, this.name)

fun List<AndroidVersionEntity>.mapToUiModelList() = map {
    Timber.d(addCoroutineDebugInfo("db get $it"))
    it.mapToUiModel()
}

fun AndroidVersion.mapToEntity() = AndroidVersionEntity(this.apiLevel, this.name)

fun List<AndroidVersion>.mapToEntityList() = map {
    it.mapToEntity()
}