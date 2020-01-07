package com.miti.meeti.database.Keyvalue;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.miti.meeti.database.DatabaseInit;
import com.miti.meeti.mitiutil.network.Keyvalue;

public class KeyvalueViewModel extends AndroidViewModel {
    private KeyvalueDao keyvalueDao;
    private KeyvalueRepository repository;
    public KeyvalueViewModel(@NonNull Application application) {
        super(application);
        repository=new KeyvalueRepository(application);
    }
    public void insert(keyvalue kp){
        repository.insert(kp);
    }
    public keyvalue get(String key) {
        keyvalue temp= repository.get(key);
        return temp;
    }
}
