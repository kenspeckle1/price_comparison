package org.kenspeckle.price_comparison.db.preis;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.kenspeckle.price_comparison.db.BaseDao;

import java.util.List;

@Dao
public interface PreisDao extends BaseDao<Preis> {
    @Query("SELECT * FROM preis")
    LiveData<List<Preis>> findAll();

    @Query("SELECT * FROM preis WHERE artikelId = :artikelId")
    LiveData<List<Preis>> findAllWithArtikelId(long artikelId);
}
