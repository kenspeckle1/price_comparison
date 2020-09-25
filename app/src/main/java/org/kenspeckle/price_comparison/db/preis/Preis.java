package org.kenspeckle.price_comparison.db.preis;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.kenspeckle.price_comparison.db.LocalDateConverter;
import org.kenspeckle.price_comparison.db.artikel.Artikel;

import java.time.LocalDate;

@Entity(
        tableName = "preis",
        indices = @Index(value = "preisId", name = "idx_preise_preis_id"),
        foreignKeys = @ForeignKey(
                entity = Artikel.class,
                childColumns = "artikelId",
                parentColumns = "artikelId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
)
public class Preis {

    @PrimaryKey(autoGenerate = true)
    private long preisId;

    @ColumnInfo(name = "laden")
    private String laden;

    @ColumnInfo(name = "kilopreis")
    private float kilopreis;

    @ColumnInfo(name = "stueckpreis")
    private float stueckpreis;

    @ColumnInfo(name = "normalPreis")
    private boolean normalPreis;

    @ColumnInfo(name = "artikelId")
    public long artikelId;

    @ColumnInfo(name = "gueltigBis")
    @TypeConverters(LocalDateConverter.class)
    public LocalDate gueltigBis;

    public Preis(String laden, float kilopreis, float stueckpreis, boolean normalPreis, long artikelId, LocalDate gueltigBis) {
        this.laden = laden;
        this.kilopreis = kilopreis;
        this.stueckpreis = stueckpreis;
        this.normalPreis = normalPreis;
        this.artikelId = artikelId;
        this.gueltigBis = gueltigBis;
    }

    public long getPreisId() {
        return preisId;
    }

    public void setPreisId(long preisId) {
        this.preisId = preisId;
    }

    public String getLaden() {
        return laden;
    }

    public float getKilopreis() {
        return kilopreis;
    }

    public void setKilopreis(float kilopreis) {
        this.kilopreis = kilopreis;
    }

    public float getStueckpreis() {
        return stueckpreis;
    }

    public boolean isNormalPreis() {
        return normalPreis;
    }

    public long getArtikelId() {
        return artikelId;
    }

    public void setArtikelId(long artikelId) {
        this.artikelId = artikelId;
    }

}
