package com.example.mwojcik.cleanarchitectureone;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/***
 * Jak widzimy, klasa ta musi byc abstrakcyjna i rozszerzac RoomDatabase
 */
@Database(entities = SimpleItem.class, version = 1)//mozemy tu dodac wiecej entitys: entities={A.class, B.class) itp.
public abstract class SimpleItemDatabase extends RoomDatabase {

    //klasa ta musi byc singletonem
    private static SimpleItemDatabase instance;

    //uzjemy tej metody do dostepu do naszego dao - Room zajmie się całym kodem, my nie dajemy ciała
    public abstract SimpleItemDao simpleItemDao();

    //po co synchronized? No synchronized oznacza, ze tylko jeden wątek w tym samym czasie moze
    //dostać się do tej metody - dzieki temu nie zrobimy przypadkowo dwoch instancji tej bazytdanych kiedy
    //2 rozne thready sproboja dostac się do tej metody - a to mogłoby się zdarzyć. Tej metody uzyjemy w dao.
    public static synchronized SimpleItemDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), SimpleItemDatabase.class, "simple_item_database")
                    .fallbackToDestructiveMigration() //jak zmienimy version nubmer to musimy powiedziec roomowi jak ma to obsluzyc, jak ma zmigrowac do nowego schema. Bez tego mielibysmyt krasza
                    //a po dodaniu tego nasza baza zwyczajnie sie zdeletuje z tabelami i zrobi od nowa. czyli zaczniemy z nowa baza danych
                    .addCallback(roomCallback) //dodajemy utworzony nizej callback do naszej bazy
                    .build();
        }
        return instance;
    }


    //Teraz pytanie jak zasilic baze na starcie? w standardowym podejsciu moglismy to zrobic w onCreate metodzie. A tutaj?
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private SimpleItemDao simpleItemDao;

        public PopulateDbAsyncTask(SimpleItemDatabase db){
            simpleItemDao = db.simpleItemDao(); //mozliwe, bo to wywloywane juz po utworzeniu bazy danych
        }

        @Override
        protected Void doInBackground(Void... voids) {
            simpleItemDao.insert(new SimpleItem("title1", "description1", 1));
            simpleItemDao.insert(new SimpleItem("title2", "description2", 5));
            simpleItemDao.insert(new SimpleItem("title3", "description3", 3));
            return null;
        }
    }

}
