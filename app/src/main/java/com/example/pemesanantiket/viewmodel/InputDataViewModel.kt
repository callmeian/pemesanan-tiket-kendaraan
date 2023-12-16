package com.example.pemesanantiket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pemesanantiket.MyApplication
import com.example.pemesanantiket.database.DatabaseClient.Companion.getInstance
import com.example.pemesanantiket.database.dao.DatabaseDao
import com.example.pemesanantiket.model.ModelDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class InputDataViewModel(application: Application) : AndroidViewModel(application) {
    private var databaseDao: DatabaseDao?

    fun addDataPemesanan(
        nama_penumpang: String,
        keberangkatan: String,
        tujuan: String,
        tanggal: String,
        nomor_telepon: String,
        anak_anak: Int,
        dewasa: Int,
        harga_tiket: Int,
        kelas: String,
        status: String
    ) {
        Completable.fromAction {
            val modelDatabase = ModelDatabase()
            modelDatabase.namaPenumpang = nama_penumpang
            modelDatabase.keberangkatan = keberangkatan
            modelDatabase.tujuan = tujuan
            modelDatabase.tanggal = tanggal
            modelDatabase.nomorTelepon = nomor_telepon
            modelDatabase.anakAnak = anak_anak
            modelDatabase.dewasa = dewasa
            modelDatabase.hargaTiket = harga_tiket
            modelDatabase.kelas = kelas
            modelDatabase.status = status
            databaseDao?.insertData(modelDatabase)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    init {
        databaseDao = getInstance(application)?.appDatabase?.databaseDao()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                InputDataViewModel(
                    application =
                    (this[APPLICATION_KEY]!!),
                )
            }
        }
    }
}