package org.kenspeckle.price_comparison.db.artikel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.kenspeckle.price_comparison.db.BaseDao;
import org.kenspeckle.price_comparison.db.dtos.ArtikelListViewDto;

import java.util.List;

@Dao
public interface ArtikelDao extends BaseDao<Artikel> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Artikel> artikels);

    @Query("SELECT * FROM artikel ORDER BY name ASC")
    LiveData<List<Artikel>> findAll();

    @Query("SELECT * FROM artikel ORDER BY name ASC")
    List<Artikel> findAllNormalList();

    @Query("SELECT a.artikelId, a.artikelTypId, a.name, p.minKilopreis FROM Artikel AS a LEFT JOIN \n" +
        "(SELECT artikelId, max(kilopreis) AS minKilopreis FROM preis GROUP BY artikelId) AS p ON a.artikelId = p.artikelId " +
        "WHERE a.artikelTypId = :artikelTypId")
    LiveData<List<ArtikelListViewDto>> findAllWithArtikelTypId(long artikelTypId);

    @Query("SELECT * FROM artikel WHERE name = :artikelTypStr ORDER BY name ASC")
    List<Artikel> findAllByName(String artikelTypStr);

    @Query("SELECT a.artikelId, a.artikelTypId, a.name, p.minKilopreis FROM Artikel AS a LEFT JOIN \n" +
            "(SELECT artikelId, max(kilopreis) AS minKilopreis FROM preis GROUP BY artikelId) AS p ON a.artikelId = p.artikelId")
    LiveData<List<ArtikelListViewDto>> findAllWithMinKilopreis();

    @Query("SELECT * from artikel WHERE artikelId = :artikelId")
    Artikel findById(long artikelId);
}
