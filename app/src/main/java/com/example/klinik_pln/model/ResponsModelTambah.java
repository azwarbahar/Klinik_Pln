package com.example.klinik_pln.model;

import com.example.klinik_pln.klinik.KlinikModel;

import java.util.List;

public class ResponsModelTambah {
    String kode, pesan;
    List<KlinikModel> result;
    List<GetDataUserModel> resultpasien;
    List<AntrianLastDoneModel> resultLastDone;
    List<getDataAntrianOneModel> resultstatusAntrian;
    List<getDataAntrianOneModel> resultAllAntrian;
    List<DokterModel> resultAbsenDokter;

    public List<DokterModel> getResultAbsenDokter() {
        return resultAbsenDokter;
    }

    public void setResultAbsenDokter(List<DokterModel> resultAbsenDokter) {
        this.resultAbsenDokter = resultAbsenDokter;
    }

    public List<getDataAntrianOneModel> getResultAllAntrian() {
        return resultAllAntrian;
    }

    public void setResultAllAntrian(List<getDataAntrianOneModel> resultAllAntrian) {
        this.resultAllAntrian = resultAllAntrian;
    }

    public List<getDataAntrianOneModel> getResultstatusAntrian() {
        return resultstatusAntrian;
    }

    public void setResultstatusAntrian(List<getDataAntrianOneModel> resultstatusAntrian) {
        this.resultstatusAntrian = resultstatusAntrian;
    }

    public List<AntrianLastDoneModel> getResultLastDone() {
        return resultLastDone;
    }

    public void setResultLastDone(List<AntrianLastDoneModel> resultLastDone) {
        this.resultLastDone = resultLastDone;
    }

    public List<GetDataUserModel> getResultpasien() {
        return resultpasien;
    }

    public void setResultpasien(List<GetDataUserModel> resultpasien) {
        this.resultpasien = resultpasien;
    }

    public List<KlinikModel> getResult() {
        return result;
    }


    public void setResult(List<KlinikModel> result) {
        this.result = result;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
