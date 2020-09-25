package org.kenspeckle.price_comparison.db.artikelTyp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "artikel_typ")
public class ArtikelTyp {

    @PrimaryKey(autoGenerate = true)
    private long artikelTypId;

    @ColumnInfo(name = "name")
    private String name;

    public ArtikelTyp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getArtikelTypId() {
        return artikelTypId;
    }

    public void setArtikelTypId(long artikelTypId) {
        this.artikelTypId = artikelTypId;
    }
}
