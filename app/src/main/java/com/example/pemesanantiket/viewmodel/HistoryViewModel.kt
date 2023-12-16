package com.example.pemesanantiket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pemesanantiket.database.DatabaseClient.Companion.getInstance
import com.example.pemesanantiket.database.dao.DatabaseDao
import com.example.pemesanantiket.model.ModelDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class HistoryViewModel public constructor(application: Application) : AndroidViewModel(application) {
    var dataList: LiveData<List<ModelDatabase>>
    var databaseDao: DatabaseDao?

    fun deleteDataById(uid: Int) {
        Completable.fromAction {
            databaseDao?.deleteDataById(uid) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    init {
        databaseDao = getInstance(application)?.appDatabase?.databaseDao()
        dataList = databaseDao?.getAllData()!!
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                HistoryViewModel(
                    application =
                    (this[APPLICATION_KEY]!!),
                )
            }
        }
    }
}