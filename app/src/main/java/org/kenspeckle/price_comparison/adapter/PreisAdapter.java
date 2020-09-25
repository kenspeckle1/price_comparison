package org.kenspeckle.price_comparison.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.kenspeckle.price_comparison.R;
import org.kenspeckle.price_comparison.db.preis.Preis;
import org.kenspeckle.price_comparison.listener.DeletePreisButtonClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PreisAdapter extends RecyclerView.Adapter<PreisAdapter.PreisViewHolder> {

    private List<Preis> data;
    private LayoutInflater layoutInflater;
    private DeletePreisButtonClickListener onDeleteDeletePreisButtonClickListener;

    public PreisAdapter(
            Context context,
            DeletePreisButtonClickListener onDeleteDeletePreisButtonClickListener) {
        this.data = new ArrayList<>();
        this.onDeleteDeletePreisButtonClickListener = onDeleteDeletePreisButtonClickListener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    @NonNull
    public PreisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.preis_list_item, parent, false);
        return new PreisViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PreisViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Preis> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            data = newData;
        }
    }

    class PreisViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewKilopreis, textViewStueckpreis, textViewLaden;
        private Button btnDelete;
        private View itemView;

        PreisViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            textViewStueckpreis = itemView.findViewById(R.id.textViewStueckpreis);
            textViewKilopreis = itemView.findViewById(R.id.textViewKilopreis);
            textViewLaden = itemView.findViewById(R.id.textViewLaden);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(final Preis preis) {
            if (preis != null) {
                textViewStueckpreis.setText(new DecimalFormat("#.##").format(preis.getStueckpreis()));
                textViewKilopreis.setText(new DecimalFormat("#.##").format(preis.getKilopreis()));
                textViewLaden.setText(preis.getLaden());
                if (preis.isNormalPreis()) {
                    itemView.setBackgroundColor(Color.argb(255, 255, 192, 203));
                }
            }
            btnDelete.setOnClickListener(v -> {
                if (onDeleteDeletePreisButtonClickListener != null)
                    onDeleteDeletePreisButtonClickListener.onDeletePreisButtonClicked(preis);
            });
        }
    }
}
