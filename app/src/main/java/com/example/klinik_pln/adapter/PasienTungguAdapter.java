package com.example.klinik_pln.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.model.getDataAntrianOneModel;

import java.util.List;

public class PasienTungguAdapter extends RecyclerView.Adapter<PasienTungguAdapter.myHolderView> {

    ProgressDialog pd;

    private Context mContext;
    private List<getDataAntrianOneModel> modelList;

    public PasienTungguAdapter(Context mContext, List<getDataAntrianOneModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }
    @NonNull
    @Override
    public PasienTungguAdapter.myHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_antrian, parent,false);
        PasienTungguAdapter.myHolderView myholderView = new PasienTungguAdapter.myHolderView(view);
        return myholderView;
    }

    @Override
    public void onBindViewHolder(@NonNull PasienTungguAdapter.myHolderView holder, int position) {
        String kode_db = modelList.get(position).getKode_antrian();
        String jam = modelList.get(position).getJam_antrian();
        holder.tv_nama.setText(modelList.get(position).getNama_pasien());
        holder.tv_panggil.setVisibility(View.GONE);

        switch (jam) {
            case "08.00":
                holder.tv_nomor.setText("PS-" + "01-" + "00" + kode_db);
                break;
            case "11.00":
                holder.tv_nomor.setText("PS-" + "02-" + "00" + kode_db);
                break;
            case "14.00":
                holder.tv_nomor.setText("PS-" + "03-" + "00" + kode_db);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class myHolderView extends RecyclerView.ViewHolder {
        private TextView tv_nomor, tv_nama, tv_panggil;
        public myHolderView(@NonNull View itemView) {
            super(itemView);

            pd = new  ProgressDialog(mContext);
            tv_nomor = itemView.findViewById(R.id.tv_nomor);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_panggil = itemView.findViewById(R.id.tv_panggil);
        }
    }
}
