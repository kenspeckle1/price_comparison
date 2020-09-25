package org.kenspeckle.price_comparison;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import org.apache.commons.lang3.StringUtils;

import org.kenspeckle.price_comparison.db.artikel.Artikel;
import org.kenspeckle.price_comparison.db.artikelTyp.ArtikelTyp;
import org.kenspeckle.price_comparison.db.preis.Preis;
import org.kenspeckle.price_comparison.viewModels.MainViewModel;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private LinearLayout layoutAddArtikelTyp;
    private LinearLayout layoutAddArtikel;
    private LinearLayout layoutAddPreis;

    private MainViewModel mainViewModel;

    //Add ArtikelTyp
    private EditText editTextArtikelTypAddArtikelTyp;

    //Add Artikel
    private Spinner spinnerArtikelTypAddArtikel;
    private EditText editTextArtikelAddArtikel;
    private List<ArtikelTyp> artikelTyps;

    //Add Preis
    private Spinner spinnerArtikelAddPreis;
    private EditText editTextStueckpreisAddPreis;
    private EditText editTextKilopreisAddPreis;
    private Switch switchIsNormalPreisAddPreis;
    private Spinner spinnerLaden;
    private EditText editTextDateGueltigBisAddPreis;

    private List<Artikel> artikelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.add_activity);
        Toolbar toolbar = findViewById(R.id.toolbarAddArtikelActivity);
        setSupportActionBar(toolbar);

        setupAddArtikelTyp();
        setupAddArtikel();
        setupAddPreis();

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        Spinner spinnerAddType = findViewById(R.id.spinnerAddType);
        spinnerAddType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.add_activity_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddType.setAdapter(adapter);

        Intent intent = getIntent();
        String addType = intent.getStringExtra(IntentStrings.ADD_TYPE_MESSAGE);
        if (StringUtils.isNotEmpty(addType)) {
            switch (addType) {
                case "ArtikelTyp":
                    spinnerAddType.setSelection(0);
                    break;
                case "Artikel":
                    spinnerAddType.setSelection(1);
                    long artikelTypId = intent.getLongExtra(IntentStrings.ADD_BASE_ID_MESSAGE, -1);
                    setArtikelTypSpinnerFromIntent(artikelTypId);
                    break;
                case "Preis":
                    spinnerAddType.setSelection(2);
                    long artikelId = intent.getLongExtra(IntentStrings.ADD_BASE_ID_MESSAGE, -1);
                    setArtikelSpinnerFromIntent(artikelId);
                    break;
            }
        }
    }



    private void setupAddArtikelTyp() {
        layoutAddArtikelTyp = findViewById(R.id.add_artikel_typ_layout);

        //Add ArtikelTyp
        editTextArtikelTypAddArtikelTyp = findViewById(R.id.editTextArtikelAddArtikelTyp);

    }


    private void setupAddArtikel() {
        layoutAddArtikel = findViewById(R.id.add_artikel_layout);

        //Add Artikel
        spinnerArtikelTypAddArtikel = findViewById(R.id.spinnerArtikelTypAddArtikel);
        editTextArtikelAddArtikel = findViewById(R.id.editTextArtikelAddArtikel);

        artikelTyps = mainViewModel.getAllArtikelTypNormalList();
        if (artikelTyps != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    artikelTyps
                            .stream()
                            .map(ArtikelTyp::getName).collect(Collectors.toList()));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerArtikelTypAddArtikel.setAdapter(adapter);
        }
    }

    private void setupAddPreis() {
        layoutAddPreis = findViewById(R.id.add_preis_layout);

        // Add Preis
        spinnerArtikelAddPreis = findViewById(R.id.spinnerArtikelAddPreis);
        editTextStueckpreisAddPreis = findViewById(R.id.editTextNumberStueckpreisAddPreis);
        editTextKilopreisAddPreis = findViewById(R.id.editTextNumberKilopreisAddPreis);
        switchIsNormalPreisAddPreis = findViewById(R.id.switchNormalpreis);
        editTextDateGueltigBisAddPreis = findViewById(R.id.editTextDateGueltigBisAddPreis);
        spinnerLaden = findViewById(R.id.spinnerLaden);

        ArrayAdapter<CharSequence> ladenAdapter = ArrayAdapter.createFromResource(this,
                R.array.laden_spinner_array, android.R.layout.simple_spinner_item);
        ladenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLaden.setAdapter(ladenAdapter);

        artikelList = mainViewModel.getAllArtikelNormalList();
        if (artikelList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    artikelList
                            .stream()
                            .map(Artikel::getName).collect(Collectors.toList())
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerArtikelAddPreis.setAdapter(adapter);
        }

    }

    private void setArtikelSpinnerFromIntent(long id) {
        if (id != -1 && artikelList != null) {
            int pos = -1;
            for (int i = 0; i < artikelList.size(); i++) {
                if (artikelList.get(i).getArtikelId() == id) {
                    pos = i;
                    break;
                }
            }
            if (pos != -1 && pos < artikelList.size()) {
                spinnerArtikelAddPreis.setSelection(pos);
            }
        }
    }


    private void setArtikelTypSpinnerFromIntent(long id) {
        if (id != -1 && artikelTyps != null) {
            int pos = -1;
            for (int i = 0; i < artikelTyps.size(); i++) {
                if (artikelTyps.get(i).getArtikelTypId() == id) {
                    pos = i;
                    break;
                }
            }
            if (pos != -1 && pos < artikelTyps.size()) {
                spinnerArtikelTypAddArtikel.setSelection(pos);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        if (pos == 0) {
            layoutAddArtikelTyp.setVisibility(View.VISIBLE);
            layoutAddArtikel.setVisibility(View.GONE);
            layoutAddPreis.setVisibility(View.GONE);
        } else if (pos == 1) {
            layoutAddArtikelTyp.setVisibility(View.GONE);
            layoutAddArtikel.setVisibility(View.VISIBLE);
            layoutAddPreis.setVisibility(View.GONE);
        } else if (pos == 2) {
            layoutAddArtikelTyp.setVisibility(View.GONE);
            layoutAddArtikel.setVisibility(View.GONE);
            layoutAddPreis.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void addArtikelTyp() {
        String name = editTextArtikelTypAddArtikelTyp.getText().toString();
        ArtikelTyp newArtikelTyp = new ArtikelTyp(name);
        mainViewModel.saveArtikelTyp(newArtikelTyp);
    }

    private void addArtikel() {
        long artikelTypId = 0L;
        try {
            String artikelTypStr = (String) spinnerArtikelTypAddArtikel.getSelectedItem();
            if (artikelTypStr != null) {
                List<ArtikelTyp> artikelTypByName = mainViewModel.findArtikelTypByName(artikelTypStr);
                if (!artikelTypByName.isEmpty()) {
                    artikelTypId = artikelTypByName.get(0).getArtikelTypId();
                }
            }
        } catch (RuntimeException ignored) {}

        String artikel = editTextArtikelAddArtikel.getText().toString();
        final Artikel newArtikel = new Artikel(artikel, artikelTypId);
        mainViewModel.saveArtikel(newArtikel);

    }

    private void addPreis() {
        long artikelId = 0L;
        try {
            String artikelName = (String) spinnerArtikelAddPreis.getSelectedItem();
            List<Artikel> artikelList = mainViewModel.findArtikelByName(artikelName);
            if (!artikelList.isEmpty()) {
                artikelId = artikelList.get(0).getArtikelId();
            }
        } catch (RuntimeException ignored) {}
//        String laden = editTextLadenAddPreis.getText().toString();
        String laden = spinnerLaden.getSelectedItem().toString();
        boolean isNormalPreis = switchIsNormalPreisAddPreis.isChecked();
        LocalDate gueltigBis = null;
        try {
            gueltigBis = LocalDate.parse(editTextDateGueltigBisAddPreis.getText());
        } catch (RuntimeException ignored) {}
        String stueckpreisString = editTextStueckpreisAddPreis.getText().toString();
        String kilopreisString = editTextKilopreisAddPreis.getText().toString();

        float kilopreis = 0.0f;
        float stueckpreis = 0.0f;
        if (!StringUtils.isEmpty(stueckpreisString)) {
            stueckpreis = Float.parseFloat(stueckpreisString);
        }
        if (!StringUtils.isEmpty(kilopreisString)) {
            kilopreis = Float.parseFloat(kilopreisString);
        }

        Preis newPreis = new Preis(laden, kilopreis, stueckpreis, isNormalPreis, artikelId, gueltigBis);
        mainViewModel.savePreis(newPreis);
    }

    @Override
    public void onClick(View v) {
        if (layoutAddArtikelTyp.getVisibility() == View.VISIBLE) {
            addArtikelTyp();
        }
        if (layoutAddArtikel.getVisibility() == View.VISIBLE) {
            addArtikel();
        }
        if (layoutAddPreis.getVisibility() == View.VISIBLE) {
            addPreis();
        }

        finish();
    }
}