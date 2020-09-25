package org.kenspeckle.price_comparison;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.kenspeckle.price_comparison.db.artikelTyp.ArtikelTyp;
import org.kenspeckle.price_comparison.db.dtos.ArtikelListViewDto;
import org.kenspeckle.price_comparison.listener.DeleteArtikelButtonClickListener;
import org.kenspeckle.price_comparison.adapter.ArtikelAdapter;
import org.kenspeckle.price_comparison.viewModels.MainViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class ArtikelListActivity
        extends AppCompatActivity
        implements DeleteArtikelButtonClickListener, AdapterView.OnItemSelectedListener {

    private ArtikelAdapter artikelAdapter;
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private Spinner spinnerArtikelTyp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get Viewmodel
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Intent intent = getIntent();
        long artikelTypId = intent.getLongExtra(IntentStrings.ARTIKEL_TYP_ID_MESSAGE, -1);


        setContentView(R.layout.artikel_list_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerArtikelTyp = findViewById(R.id.spinnerArtikelTypeArtikelList);
        List<ArtikelTyp> artikelTypList = mainViewModel.getAllArtikelTypNormalList();
        if (artikelTypList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    artikelTypList
                            .stream()
                            .map(ArtikelTyp::getName).collect(Collectors.toList())
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerArtikelTyp.setAdapter(adapter);

            if (artikelTypId != -1) {
                int position = -1;
                for (int i = 0; i < artikelTypList.size(); i++) {
                    if (artikelTypList.get(i).getArtikelTypId() == artikelTypId) {
                        position = i;
                        break;
                    }
                }
                if (position != -1) {
                    spinnerArtikelTyp.setSelection(position);
                }
            }
        }

        spinnerArtikelTyp.setOnItemSelectedListener(this);

        artikelAdapter = new ArtikelAdapter(this, this);
        recyclerView = findViewById(R.id.recyclerViewArtikelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(artikelAdapter);

        if (artikelTypId != -1) {
            mainViewModel
                    .getAllArtikelListWithArtikelTypId(artikelTypId)
                    .observe(this, posts -> artikelAdapter.setData(posts));
        } else {
            mainViewModel.getAllArtikelWithMinKilopreis().observe(this, posts -> artikelAdapter.setData(posts));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addPost) {
            Intent i = new Intent(getApplicationContext(), AddActivity.class);
            i.putExtra(IntentStrings.ADD_TYPE_MESSAGE, "Artikel");
            i.putExtra(
                    IntentStrings.ADD_BASE_ID_MESSAGE,
                    getArtikelTypIdFromSpinner(spinnerArtikelTyp.getSelectedItemPosition()));
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteArtikelButtonClicked(ArtikelListViewDto artikel) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", (dialog, which) -> {
            mainViewModel.deleteArtikel(artikel);
            dialog.dismiss();
        });
        alert.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alert.show();
    }


    // Methods for the selection Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mainViewModel
                .getAllArtikelListWithArtikelTypId(getArtikelTypIdFromSpinner(position))
                .observe(this, artikelDtos -> artikelAdapter.setData(artikelDtos));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private long getArtikelTypIdFromSpinner(int position) {
        String value = (String) spinnerArtikelTyp.getAdapter().getItem(position);
        List<ArtikelTyp> artikelTypByName = mainViewModel.findArtikelTypByName(value);
        long artikelTypId = -1;
        if (!artikelTypByName.isEmpty()) {
            artikelTypId = artikelTypByName.get(0).getArtikelTypId();
        }
        return artikelTypId;
    }
}
