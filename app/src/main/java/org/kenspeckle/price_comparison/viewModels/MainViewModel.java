package org.kenspeckle.price_comparison.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.kenspeckle.price_comparison.db.artikel.Artikel;
import org.kenspeckle.price_comparison.db.artikel.ArtikelDao;
import org.kenspeckle.price_comparison.db.ArtikelsDatabase;
import org.kenspeckle.price_comparison.db.artikelTyp.ArtikelTyp;
import org.kenspeckle.price_comparison.db.artikelTyp.ArtikelTypDao;
import org.kenspeckle.price_comparison.db.dtos.ArtikelListViewDto;
import org.kenspeckle.price_comparison.db.preis.Preis;
import org.kenspeckle.price_comparison.db.preis.PreisDao;

public class MainViewModel extends AndroidViewModel {

    private ArtikelTypDao artikelTypDao;
    private ArtikelDao artikelDao;
    private PreisDao preisDao;
    private ExecutorService executorService;

    public MainViewModel(@NonNull Application application) {
        super(application);
        artikelTypDao = ArtikelsDatabase.getInstance(application).artikelTypDao();
        artikelDao = ArtikelsDatabase.getInstance(application).artikelDao();
        preisDao = ArtikelsDatabase.getInstance(application).preisDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ArtikelTyp>> getAllArtikelTypList() {
        return artikelTypDao.findAll();
    }
    public List<ArtikelTyp> getAllArtikelTypNormalList() {
        return artikelTypDao.findAllNormalList();
    }

    public void saveArtikelTyp(ArtikelTyp artikelTyp) {
        executorService.execute(() -> artikelTypDao.insert(artikelTyp));
    }

    public void saveArtikel(Artikel artikel) {
        executorService.execute(() -> artikelDao.insert(artikel));
    }

    public void savePreis(Preis preis) {
        executorService.execute(() -> preisDao.insert(preis));
    }

    public void deleteArtikelTyp(ArtikelTyp artikelTyp) {
        executorService.execute(() -> artikelTypDao.delete(artikelTyp));
    }

    public LiveData<List<ArtikelTyp>> searchArtikel(String query) {
        return artikelTypDao.findSearchValue(query);
    }

    public List<Artikel> getAllArtikelNormalList() {
        return artikelDao.findAllNormalList();
    }

    public List<ArtikelTyp> findArtikelTypByName(String artikelTypStr) {
        return artikelTypDao.findAllByName(artikelTypStr);
    }

    public LiveData<List<ArtikelListViewDto>> getAllArtikelWithMinKilopreis() {
        return artikelDao.findAllWithMinKilopreis();
    }

    public LiveData<List<ArtikelListViewDto>> getAllArtikelListWithArtikelTypId(long artikelTypId) {
        return artikelDao.findAllWithArtikelTypId(artikelTypId);
    }

    public LiveData<List<Preis>> getAllPreisListWithArtikelId(long artikelId) {
        return preisDao.findAllWithArtikelId(artikelId);
    }

    public LiveData<List<Preis>> getAllPreisList() {
        return preisDao.findAll();
    }

    public List<Artikel> findArtikelByName(String artikelName) {
        return artikelDao.findAllByName(artikelName);
    }

    public void deleteArtikel(ArtikelListViewDto artikelDto) {
        Artikel artikel = artikelDao.findById(artikelDto.getArtikelId());
        artikelDao.delete(artikel);
    }

    public void deletePreis(Preis preis) {
        preisDao.delete(preis);
    }
}
