package org.kenspeckle.price_comparison.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import org.kenspeckle.price_comparison.ArtikelListActivity;
import org.kenspeckle.price_comparison.IntentStrings;
import org.kenspeckle.price_comparison.R;
import org.kenspeckle.price_comparison.db.artikelTyp.ArtikelTyp;
import org.kenspeckle.price_comparison.listener.DeleteArtikelTypButtonClickListener;

public class ArtikelTypAdapter extends RecyclerView.Adapter<ArtikelTypAdapter.ArtikelTypViewHolder> {


    private List<ArtikelTyp> data;
    private LayoutInflater layoutInflater;
    private DeleteArtikelTypButtonClickListener onDeleteDeleteArtikelTypButtonClickListener;

    public ArtikelTypAdapter(
            Context context,
            DeleteArtikelTypButtonClickListener deleteArtikelTypButtonClickListener) {
        this.data = new ArrayList<>();
        this.onDeleteDeleteArtikelTypButtonClickListener = deleteArtikelTypButtonClickListener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    @NonNull
    public ArtikelTypViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_artikel_item, parent, false);
        return new ArtikelTypViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArtikelTypViewHolder holder, int position) {
        holder.bind(data.get(position));
        holder.itemView.setOnClickListener(o -> {
            Intent i = new Intent(holder.itemView.getContext(), ArtikelListActivity.class);
            i.putExtra(IntentStrings.ARTIKEL_TYP_ID_MESSAGE, data.get(position).getArtikelTypId());
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ArtikelTyp> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            // first initialization
            data = newData;
        }
    }

    class ArtikelTypViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewArtikelname;
        private Button btnDelete;
        private View itemView;

        ArtikelTypViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            textViewArtikelname = itemView.findViewById(R.id.textViewArtikelname);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(final ArtikelTyp artikelTyp) {
            if (artikelTyp != null) {
                textViewArtikelname.setText(artikelTyp.getName());
                btnDelete.setOnClickListener(v -> {
                    if (onDeleteDeleteArtikelTypButtonClickListener != null)
                        onDeleteDeleteArtikelTypButtonClickListener.onDeleteArtikelTypButtonClicked(artikelTyp);
                });
            }
        }
    }
}
