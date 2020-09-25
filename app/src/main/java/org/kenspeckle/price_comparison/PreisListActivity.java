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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.kenspeckle.price_comparison.db.artikel.Artikel;
import org.kenspeckle.price_comparison.db.preis.Preis;
import org.kenspeckle.price_comparison.listener.DeletePreisButtonClickListener;
import org.kenspeckle.price_comparison.adapter.PreisAdapter;
import org.kenspeckle.price_comparison.viewModels.MainViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class PreisListActivity extends AppCompatActivity implements DeletePreisButtonClickListener, AdapterView.OnItemSelectedListener {

    private PreisAdapter preisAdapter;
    private MainViewModel mainViewModel;

    private Spinner spinnerArtikel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preis_list_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Intent intent = getIntent();
        long artikelId = intent.getLongExtra(IntentStrings.ARTIKEL_ID_MESSAGE, -1);

        spinnerArtikel = findViewById(R.id.spinnerArtikelPreisList);
        List<Artikel> artikelList = mainViewModel.getAllArtikelNormalList();
        if (artikelList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    artikelList
                            .stream()
                            .map(Artikel::getName).collect(Collectors.toList())
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerArtikel.setAdapter(adapter);

            if (artikelId != -1) {
                int position = -1;
                for (int i = 0; i < artikelList.size(); i++) {
                    if (artikelList.get(i).getArtikelId() == artikelId) {
                        position = i;
                        break;
                    }
                }
                if (position != -1) {
                    spinnerArtikel.setSelection(position);
                }
            }
        }
        spinnerArtikel.setOnItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPreisList);
        preisAdapter = new PreisAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(preisAdapter);

        if (artikelId != -1) {
            mainViewModel
                    .getAllPreisListWithArtikelId(artikelId)
                    .observe(this, preisList -> preisAdapter.setData(preisList));
        } else {
            mainViewModel.getAllPreisList().observe(this, preisList -> preisAdapter.setData(preisList));
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
            i.putExtra(IntentStrings.ADD_TYPE_MESSAGE, "Preis");
            i.putExtra(
                    IntentStrings.ADD_BASE_ID_MESSAGE,
                    getArtikelIdFromSpinner(spinnerArtikel.getSelectedItemPosition()));
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeletePreisButtonClicked(Preis preis) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", (dialog, which) -> {
            mainViewModel.deletePreis(preis);
            dialog.dismiss();
        });
        alert.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alert.show();
    }



    private long getArtikelIdFromSpinner(int position) {
        String value = (String) spinnerArtikel.getAdapter().getItem(position);
        List<Artikel> artikelByName = mainViewModel.findArtikelByName(value);
        long artikelId = -1;
        if (!artikelByName.isEmpty()) {
            artikelId = artikelByName.get(0).getArtikelId();
        }
        return artikelId;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mainViewModel
                .getAllPreisListWithArtikelId(getArtikelIdFromSpinner(position))
                .observe(this, preisDtos -> preisAdapter.setData(preisDtos));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
