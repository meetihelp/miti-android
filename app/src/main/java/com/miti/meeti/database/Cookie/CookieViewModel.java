package com.miti.meeti.database.Cookie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CookieViewModel extends AndroidViewModel {
    private CookieRepository repository;
    private LiveData<String[]>miticookie;
    public CookieViewModel(@NonNull Application application) {
        super(application);
        repository = new CookieRepository(application);
        miticookie = repository.getCookie();
    }
    public void insert(Cookie cookie){
        repository.insert(cookie);
    }
    public String getCookie1(){
        return repository.getCookie1();
    }
    public LiveData<String[]> getCookie(){
        return miticookie;
    }
}
