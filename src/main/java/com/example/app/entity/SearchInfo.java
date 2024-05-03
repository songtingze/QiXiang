package com.example.app.entity;

import java.util.ArrayList;

public class SearchInfo {
    private String seq;
    private String time;
    private String status;
    private String library;
    private String info;

    public SearchInfo(String seq,String time,String status,String library,String info){
        this.seq=seq;
        this.time=time;
        this.status=status;
        this.library = library;
        this.info = info;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }
}
