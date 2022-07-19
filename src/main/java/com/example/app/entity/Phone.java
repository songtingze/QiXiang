package com.example.app.entity;

public class Phone {
    private boolean selected;
    private String seq;
    private String phone;
    private String status;

    public Phone(boolean selected,String seq,String phone,String status){
        this.seq=seq;
        this.phone=phone;
        this.status=status;
        this.selected=selected;
    }


    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
