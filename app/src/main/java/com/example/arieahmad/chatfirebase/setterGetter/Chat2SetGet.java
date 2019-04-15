package com.example.arieahmad.chatfirebase.setterGetter;

import java.io.Serializable;

/**
 * Created by Arie Ahmad on 7/15/2017.
 */

public class Chat2SetGet implements Serializable {
    String nama, pesan, status, waktu;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
