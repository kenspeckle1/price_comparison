package org.kenspeckle.price_comparison.listener;

import org.kenspeckle.price_comparison.db.dtos.ArtikelListViewDto;

public interface DeleteArtikelButtonClickListener {
    void onDeleteArtikelButtonClicked(ArtikelListViewDto artikel);
}
