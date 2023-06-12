package com.example.appdocumentaciontransito.activity.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Bienvenido\nLas opciones disponibles estan a la izquierda⬅️");
    }

    public LiveData<String> getText() {
        return mText;
    }
}