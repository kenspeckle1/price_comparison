package org.kenspeckle.price_comparison.db.artikel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.kenspeckle.price_comparison.db.artikelTyp.ArtikelTyp;

@Entity(
        tableName = "artikel",
        indices = @Index(value = "artikelId", name = "idx_artikels_artikel_id"),
        foreignKeys = @ForeignKey(
                entity = ArtikelTyp.class,
                childColumns = "artikelTypId",
                parentColumns = "artikelTypId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
)
public class Artikel {

    @PrimaryKey(autoGenerate = true)
    private long artikelId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "artikelTypId")
    private long artikelTypId;

    public Artikel() { }

    public Artikel(String name, long artikelTypId) {
        this.name = name;
        this.artikelTypId = artikelTypId;
    }

    public long getArtikelId() {
        return artikelId;
    }

    public void setArtikelId(long artikelId) {
        this.artikelId = artikelId;
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
