package com.example.mwojcik.cleanarchitectureone;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class SimpleItemViewModel extends AndroidViewModel {

    private SimpleItemRepository simpleItemRepository;
    private LiveData<List<SimpleItem>> allItems;

    public SimpleItemViewModel(@NonNull Application application) {
        super(application);
        simpleItemRepository = new SimpleItemRepository(application);
        allItems = simpleItemRepository.getAllItems();
    }

    public void insert(SimpleItem simpleItem){
        simpleItemRepository.insert(simpleItem);
    }

    public void update(SimpleItem simpleItem){
        simpleItemRepository.update(simpleItem);
    }

    public void delete(SimpleItem simpleItem){
        simpleItemRepository.delete(simpleItem);
    }

    public void deleteAllItems(){
        simpleItemRepository.deleteAll();
    }

    public LiveData<List<SimpleItem>> getAllItems() {
        return allItems;
    }
}
