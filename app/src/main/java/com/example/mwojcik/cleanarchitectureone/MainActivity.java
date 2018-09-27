package com.example.mwojcik.cleanarchitectureone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;


/***
 * Tutaj pokazemy sobie przyklad Architecture Components androidowych, takich jak Room, LiveData, ViewModel (MVVM)
 *
 *
 * 1. Dlaczego Room?
 * Poniewaz standardowy sposob, czyli tworzenie dbhelpera i roznych dao wymuszal pisanie duzo kodu. Room jest takim
 * jakby wrapperem SQLItea i dao (i encji). DAO komunikuje się z SQLite, a Entity to jak wiemy obiekty bazy (java klasy).
 * Room tez zapobiega różnym błędom, które db miała - np. zmiana tabeli itp. powodować mogło krasze.
 *
 * 2. MVVM (Model-View-ViewModel)
 *
 * Activity/Fragment -> ViewModel (LiveData) -> Repository -> Room (dao -> SQLite -> Entity)
 *
 * ViewModel - trzymanie i przygotowywanie danych do UI, tak że nie musimy tego robić bezpośrednio w Activity/Fragmencie
 * ViewModel jest takim więc gatewayaem miedzy UI a danymi. Najlepsze w ViewModel jest to, że jest odporna na zmiany
 * konfiguracji! - co domyślnie trzeba robić samemu (saving/restoring states etc). Dzieki ViewModel i temu podejsciu
 * nasza Activity/Fragment zawierac będzie duuuuużo mniej kodu.
 * Repository - ViewModel nie komunikuje się bezpośrednio z Roomem, a robi to za pośrednictwem właśnie tzw. Repozytorium.
 * Jest to prosta Javowa klasa (nic specjalnego z architektury), jest to taki mediator między ViewModelem a danymi,
 * nie tylko z Rooma, ale też z np. WebServiców (z RESTów, tj. różnych APIs itp.).
 *
 * MVVM zapewnia bardzo fajny clean architecture, jest porządek, łatwiej testować i nasz kod jest czytelniejszy.
 *
 * LiveData - też ważny komponent w architekturze MVVM, jest to wrapper, który może przechowywać dowolny typ danych i
 * może być 'obserwowany' przez UI kontroler, tzn. kiedykolwiek dane się zmienią, LiveData poinformuje o tym nasze UI.
 * LiveData jest lifecycle aware - nie przejmuje się lifecyclem aplikacji. CO to oznacza? Ze nie musimy się przejmować
 * tym czy Activity, która obserwuje jest w np. backgroundzie bo LiveData automatycznie przestanie updejtować (obserwować)
 * dopóki nie wrócimy do Activity. Samo czysci referencje itp.
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SimpleItemViewModel simpleItemViewModel;

    private RecyclerView recyclerView;
    private SimpleItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); //jak wimey ze jego rozmiar sie nie zmieni
        adapter = new SimpleItemAdapter();
        recyclerView.setAdapter(adapter);

        simpleItemViewModel = ViewModelProviders.of(MainActivity.this).get(SimpleItemViewModel.class);
        simpleItemViewModel.getAllItems().observe(this, new Observer<List<SimpleItem>>() {
            @Override
            public void onChanged(@Nullable List<SimpleItem> simpleItems) {
                Log.d(TAG, "onChanged() called");
                adapter.setDataList(simpleItems);
            }
        });
    }
}
