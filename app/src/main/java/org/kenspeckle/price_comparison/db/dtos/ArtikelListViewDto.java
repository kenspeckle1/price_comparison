package org.kenspeckle.price_comparison.db.dtos;

public class ArtikelListViewDto {

    private long artikelId;


    private String name;

    private float minKilopreis;

    public ArtikelListViewDto(long artikelId, String name, float minKilopreis) {
        this.artikelId = artikelId;
        this.name = name;
        this.minKilopreis = minKilopreis;
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

    public float getMinKilopreis() {
        return minKilopreis;
    }
}
