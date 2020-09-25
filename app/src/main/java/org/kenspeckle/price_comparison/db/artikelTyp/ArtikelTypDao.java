package org.kenspeckle.price_comparison.db.artikelTyp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.kenspeckle.price_comparison.db.BaseDao;

import java.util.List;

@Dao
public interface ArtikelTypDao extends BaseDao<ArtikelTyp> {

    @Query("SELECT * FROM artikel_typ ORDER BY name ASC")
    LiveData<List<ArtikelTyp>> findAll();

    @Query("SELECT * FROM artikel_typ ORDER BY name ASC")
    List<ArtikelTyp> findAllNormalList();

    @Query("SELECT * FROM artikel_typ WHERE :query || '%' OR name LIKE '%' || :query || '%' ORDER BY name ASC")
    LiveData<List<ArtikelTyp>> findSearchValue(String query);

    @Query("SELECT * FROM artikel_typ WHERE name = :artikelTypStr ORDER BY name ASC")
    List<ArtikelTyp> findAllByName(String artikelTypStr);

}
