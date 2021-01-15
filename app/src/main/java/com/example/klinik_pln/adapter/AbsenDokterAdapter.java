package com.example.klinik_pln.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.model.DokterModel;


import java.util.List;

public class AbsenDokterAdapter extends RecyclerView.Adapter<AbsenDokterAdapter.HolderView> {

    private Context mContext;
    private List<DokterModel> userModels;

    public AbsenDokterAdapter(Context mContext, List<DokterModel> userModels) {
        this.mContext = mContext;
        this.userModels = userModels;
    }

    @NonNull
    @Override
    public AbsenDokterAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dokter,parent,false);
        return new AbsenDokterAdapter.HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsenDokterAdapter.HolderView holder, int position) {
        holder.tv_nama.setText(userModels.get(position).getNama_lengkap());
        holder.tv_spesialis.setText(userModels.get(position).getSpesialis());
        String kehadiran = userModels.get(position).getAbsen();
        if (kehadiran.equals("Hadir")){
            holder.tv_absen.setText("Masuk");
            holder.tv_absen.setTextColor(Color.BLUE);
        } else {
            holder.tv_absen.setText("Keluar");
            holder.tv_absen.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        TextView tv_nama,tv_spesialis,tv_absen;

        public HolderView(@NonNull View itemView) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_spesialis = itemView.findViewById(R.id.tv_spesialis);
            tv_absen = itemView.findViewById(R.id.tv_absen);
        }
    }
}
