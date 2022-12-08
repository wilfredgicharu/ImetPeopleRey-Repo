package com.raywenderlich.android.imet.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.raywenderlich.android.imet.IMetApp
import com.raywenderlich.android.imet.data.PeopleRepository
import com.raywenderlich.android.imet.data.model.People

class PeopleListViewodel(application: Application): AndroidViewModel(application) {
    private val peopleRepository = getApplication<IMetApp>().getPeopleRepository()
    private val peopleList = MediatorLiveData<List<People>>()  //mediatorLiveData is a special type of LiveData that can observe other LiveData objects and react on their emissions.

    init {
        getAllPeople()
    }

    //returns an observable LiveData version of the peopleList, making it accessible to the attached Activity or Fragment.
    private fun getAllList(): LiveData<List<People>> {
        return peopleList
    }

    //sets the data source of peopleList from PeopleRepository
    fun getAllPeople() {
        peopleList.addSource(peopleRepository.getAllPeople()) { peoples ->
            peopleList.postValue(peoples)
        }
    }
}