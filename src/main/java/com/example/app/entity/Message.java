package com.example.app.entity;

import java.util.ArrayList;

public class Message {
    private String seq;
    private String time;
    private String status;
    private ArrayList<Phone> phone;

    public Message(String seq,String time,String status,ArrayList<Phone> phone){
        this.seq=seq;
        this.time=time;
        this.status=status;
        this.phone = phone;
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

    public ArrayList<Phone> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<Phone> phone) {
        this.phone = phone;
    }

}
