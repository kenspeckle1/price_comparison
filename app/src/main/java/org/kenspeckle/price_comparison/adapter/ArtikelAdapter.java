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

import org.kenspeckle.price_comparison.IntentStrings;
import org.kenspeckle.price_comparison.PreisListActivity;
import org.kenspeckle.price_comparison.R;
import org.kenspeckle.price_comparison.db.dtos.ArtikelListViewDto;
import org.kenspeckle.price_comparison.listener.DeleteArtikelButtonClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder> {


    private List<ArtikelListViewDto> data;
    private LayoutInflater layoutInflater;
    private DeleteArtikelButtonClickListener onDeleteDeleteArtikelButtonClickListener;

    public ArtikelAdapter(
            Context context,
            DeleteArtikelButtonClickListener deleteArtikelButtonClickListener) {
        this.data = new ArrayList<>();
        this.onDeleteDeleteArtikelButtonClickListener = deleteArtikelButtonClickListener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    @NonNull
    public ArtikelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.artikel_list_item, parent, false);
        return new ArtikelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArtikelViewHolder holder, int position) {
        holder.bind(data.get(position));
        holder.itemView.setOnClickListener(o -> {
            Intent i = new Intent(holder.itemView.getContext(), PreisListActivity.class);
            i.putExtra(IntentStrings.ARTIKEL_ID_MESSAGE, data.get(position).getArtikelId());
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ArtikelListViewDto> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            // first initialization
            data = newData;
        }
    }

    class ArtikelViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewArtikelname, textViewMinKilopreis;
        private Button btnDelete;
        private View itemView;

        ArtikelViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            textViewArtikelname = itemView.findViewById(R.id.textViewArtikelListArtikelname);
            textViewMinKilopreis = itemView.findViewById(R.id.textViewArtikelListMinKilopreis);
            btnDelete = itemView.findViewById(R.id.btnArtikelListDelete);
        }

        void bind(final ArtikelListViewDto dto) {
            if (dto != null) {
                textViewArtikelname.setText(dto.getName());
                textViewMinKilopreis.setText(new DecimalFormat("#.##").format(dto.getMinKilopreis()));
            }

            btnDelete.setOnClickListener(v -> {
                if (onDeleteDeleteArtikelButtonClickListener != null)
                    onDeleteDeleteArtikelButtonClickListener.onDeleteArtikelButtonClicked(dto);
            });
        }
    }
}
