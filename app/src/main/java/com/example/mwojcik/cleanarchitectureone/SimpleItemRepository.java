package com.example.mwojcik.cleanarchitectureone;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class SimpleItemRepository {

    private SimpleItemDao simpleItemDao;
    private LiveData<List<SimpleItem>> allItems;

    public SimpleItemRepository(Application application){
        SimpleItemDatabase simpleItemDatabase = SimpleItemDatabase.getInstance(application);
        //Teraz pytanie - jak ta metoda zadziala, skoro nie ma ciala? Ano proste - Room wygenerowal go za nas, w momencie
        //kiedy odwołamy się do metody getInstance, bo tam tworzymy cala baze danych.
        simpleItemDao = simpleItemDatabase.simpleItemDao();
        allItems = simpleItemDao.getAllItems();
    }


    public void insert(SimpleItem simpleItem){
        //Tutaj juz musimy sami kod zrobic na bazie danych, a jako ze android nie obsluguje
        //operacji baz dnaych na main threadzie, uzyjemy sobie async taskow
        new InsertSimpleItemAsyncTask(simpleItemDao).execute(simpleItem);
    }

    public void update(SimpleItem simpleItem){
        new UpdateSimpleItemAsyncTask(simpleItemDao).execute(simpleItem);
    }

    public void delete(SimpleItem simpleItem){
        new DeleteSimpleItemAsyncTask(simpleItemDao).execute(simpleItem);
    }

    public void deleteAll(){
        new DeleteAllSimpleItemAsyncTask(simpleItemDao).execute();
    }

    //Dzieki temu Room wykona tego LiveData automatycznie w background threadzie
    public LiveData<List<SimpleItem>> getAllItems() {
        return allItems;
    }

    //static zeby nie mialo referencji do repository, bo to mogloby doprowadzic do memory leaksow
    private static class InsertSimpleItemAsyncTask extends AsyncTask<SimpleItem, Void, Void>{

        private SimpleItemDao simpleItemDao;
        private InsertSimpleItemAsyncTask(SimpleItemDao simpleItemDao){
            this.simpleItemDao = simpleItemDao;
        }

        @Override
        protected Void doInBackground(SimpleItem... simpleItems) {
            simpleItemDao.insert(simpleItems[0]); //bo dodajemy jedna tylko, a ona bedzie na indeksie 0 - to jasne
            return null;
        }
    }

    private static class UpdateSimpleItemAsyncTask extends AsyncTask<SimpleItem, Void, Void>{

        private SimpleItemDao simpleItemDao;
        private UpdateSimpleItemAsyncTask(SimpleItemDao simpleItemDao){
            this.simpleItemDao = simpleItemDao;
        }

        @Override
        protected Void doInBackground(SimpleItem... simpleItems) {
            simpleItemDao.update(simpleItems[0]); //bo dodajemy jedna tylko, a ona bedzie na indeksie 0 - to jasne
            return null;
        }
    }


    private static class DeleteSimpleItemAsyncTask extends AsyncTask<SimpleItem, Void, Void>{

        private SimpleItemDao simpleItemDao;
        private DeleteSimpleItemAsyncTask(SimpleItemDao simpleItemDao){
            this.simpleItemDao = simpleItemDao;
        }

        @Override
        protected Void doInBackground(SimpleItem... simpleItems) {
            simpleItemDao.remove(simpleItems[0]); //bo dodajemy jedna tylko, a ona bedzie na indeksie 0 - to jasne
            return null;
        }
    }


    private static class DeleteAllSimpleItemAsyncTask extends AsyncTask<Void, Void, Void>{

        private SimpleItemDao simpleItemDao;

        private DeleteAllSimpleItemAsyncTask(SimpleItemDao simpleItemDao){
            this.simpleItemDao = simpleItemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            simpleItemDao.deleteAllItems();
            return null;
        }
    }
}
