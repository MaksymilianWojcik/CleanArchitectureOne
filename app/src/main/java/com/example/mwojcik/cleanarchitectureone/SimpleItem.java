package com.example.mwojcik.cleanarchitectureone;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/***
 * Co tutaj warto zaobserwowac - Room sam nam wygeneruje kolumny z tej encji, wystarczy oznaczyc klasę
 * annotacją @Entity. Jednak! jak damy zmienne na private, to musimy dodać gettery/settery dla zmiennych,
 * zeby Room to zrobił. Alternatywnie - choć to trochę mija się z celem clean architecture - możemy oznaczyć
 * zmienne na public, wtedy nie potrzebna getterów i setterów. My zostanmy przy getterach i setterach.
 * Room potrzebuje tez konstruktor, oczywiscie nie musi byc z id, bo id będzie generowane automatycznie.
 *
 * Dokladnijsze wyjasnienie z getterami/setterami: tak naprawde, to do zrobienia bazy wystarczylyby nam tylko gettery,
 * jedynie na idiku musielibysmy dodac settery bo nie ma go w konstruktorze.
 *
 * Wydaje mi sie ze mimo wszystko jakbysmy probowalo na sztywno przypadkowo dodac idka, to lecialyby bledy Rooma, ale
 * nie pamietam dokladnie czy tak bylo
 */
@Entity(tableName = "simple_item_table") //Defaultowa nazwa bylaby simpleitem
public class SimpleItem {

    //kazda sql table potrzebuje primary key - dlatego robimy jeszce id w formie integera
    @PrimaryKey(autoGenerate = true) //czyli z kazdym nowym rowem sql bedzie automatycznie generowal nowy id
    private int id;

//    @ColumnInfo(name = "title") - mozemy sobie tak ustawic inną nazwę w tabeli niż ze zmiennej
    private String title;
    private String description;
    private int priority;

    public SimpleItem(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
