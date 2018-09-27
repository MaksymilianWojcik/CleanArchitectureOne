package com.example.mwojcik.cleanarchitectureone;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/***
 * Pierwsza roznica miedzy tym dao a standardowym, która już się rzuca w oczy? No że to interfejs a nie klasa.
 * Dobrym podejscem jest tworzenie Dao dla kazdej entity - tak jak standardowo.
 * Kolejna lepsza rzecz: tutaj jak napiszemy zle zapytanie sql to juz tutaj android studio nam podkresli
 * ze jest blad - w standardowym podejsciu takiego czegos nie bylo, apka z tego co pamietam i tak się odpalała,
 * chociac - mozliwe tez ze przy projekcie ostatnim juz to bylo dodane, ze nawet przy standardowym podejsciu
 * juz to bylo sprawdzane - ale tez pewnosci nie mam.
 */

@Dao
public interface SimpleItemDao {

    @Insert //tak ustawiamy co ta metoda robi
    void insert(SimpleItem simpleItem); //nazwe mozemy sobie sami ustawic i to nie my ustawiamy co ta metoda robi

    @Update
    void update(SimpleItem simpleItem);

    @Delete //ciekawostka: ctr + b po znaznaczeniu adnotacji
    void remove(SimpleItem simpleItem);

    @Query("DELETE FROM simple_item_table") //nie ma domyslnie takiej emtody, trzeba ja zrobic samemu
    void deleteAllItems();

//    @Query("SELECT * FROM simple_item_table ORDER BY priority DESC")
//    List<SimpleItem> getAllItems();

    @Query("SELECT * FROM simple_item_table ORDER BY priority DESC")
    LiveData<List<SimpleItem>> getAllItems(); //jako ze dodalismy tutaj LiveData, to mozemy teraz obserwowac na zmiany.
    //Teraz kazda zmiana w tabeli simple_item, to wartosc ta automatycznie sie zupdejtuje i activity bedzie powiadomione o tym.
    //Room zajmie sie wszystkim sprawa updejtowania obiektu, nie musimy sami tego robic. My musimy jedynie tutaj zwrocic
    //po prostu LiveData obiekt (w tym przypadku obiektem jest lista SimpleItemow)

}
