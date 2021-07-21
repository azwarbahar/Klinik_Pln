package com.example.klinik_pln.histrory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.model.getDataAntrianOneModel;

import java.util.ArrayList;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.MyHolderView> {

    private Context context;
    private ArrayList<getDataAntrianOneModel> oneModels;

    public RiwayatAdapter(Context context, ArrayList<getDataAntrianOneModel> oneModels) {
        this.context = context;
        this.oneModels = oneModels;
    }

    @NonNull
    @Override
    public RiwayatAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_riwayar, parent, false);
        RiwayatAdapter.MyHolderView myHolderView = new RiwayatAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatAdapter.MyHolderView holder, int position) {

        String jam = oneModels.get(position).getJam_antrian();
        String kode_db = oneModels.get(position).getKode_antrian();
        holder.tv_jam.setText("Jam : "+oneModels.get(position).getJam_antrian());

        switch (jam) {
            case "08.00":
                holder.tv_kode.setText("PS-" + "01-" + "00" + kode_db);
                break;
            case "11.00":
                holder.tv_kode.setText("PS-" + "02-" + "00" + kode_db);
                break;
            case "14.00":
                holder.tv_kode.setText("PS-" + "03-" + "00" + kode_db);
                break;
        }

        holder.tv_tanggal.setText(oneModels.get(position).getTanggal_antrian());
        String status = oneModels.get(position).getStatus();
        if (status.equals("Selesai")){
            holder.tv_status.setText(status);
            holder.tv_status.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_status_selesai));
        } else if (status.equals("Batal")){
            holder.tv_status.setText("Dibatalkan");
            holder.tv_status.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_status_batal));
        } else if (status.equals("Tunda")){
            holder.tv_status.setText("Tertunda");
            holder.tv_status.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_status));

        }


    }

    @Override
    public int getItemCount() {
        return oneModels.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_kode;
        private TextView tv_tanggal;
        private TextView tv_jam;
        private TextView tv_status;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_kode = itemView.findViewById(R.id.tv_kode);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_jam = itemView.findViewById(R.id.tv_jam);
            tv_status = itemView.findViewById(R.id.tv_status);

        }
    }
}
