package org.kenspeckle.price_comparison;

import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.kenspeckle.price_comparison.db.artikelTyp.ArtikelTyp;
import org.kenspeckle.price_comparison.listener.DeleteArtikelTypButtonClickListener;
import org.kenspeckle.price_comparison.adapter.ArtikelTypAdapter;
import org.kenspeckle.price_comparison.viewModels.MainViewModel;

public class MainActivity extends AppCompatActivity implements DeleteArtikelTypButtonClickListener {

    private ArtikelTypAdapter artikelTypAdapter;
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rvPostsLis);

        artikelTypAdapter = new ArtikelTypAdapter(this, this);
        setAdapter();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getAllArtikelTypList().observe(this, posts -> artikelTypAdapter.setData(posts));
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
            i.putExtra(IntentStrings.ADD_TYPE_MESSAGE, "ArtikelTyp");
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.resetSearch) {
            mainViewModel.getAllArtikelTypList().observe((LifecycleOwner) getContext(), posts -> artikelTypAdapter.setData(posts));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(artikelTypAdapter);
    }

    @Override
    public void onDeleteArtikelTypButtonClicked(ArtikelTyp artikelTyp) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", (dialog, which) -> {
            mainViewModel.deleteArtikelTyp(artikelTyp);
            dialog.dismiss();
        });
        alert.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    protected Context getContext() {
        return MainActivity.this;
    }

}
