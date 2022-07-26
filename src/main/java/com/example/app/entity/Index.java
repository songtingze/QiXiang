package com.example.app.entity;

public class Index {
    private boolean selected;
    private String indexNum;
    private String indexJudge;
    private String indexName;
    private String indexStatus;
    private String indexCode;
    private String indexData;

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(String indexNum) {
        this.indexNum = indexNum;
    }

    public String getIndexJudge() {
        return indexJudge;
    }

    public void setIndexJudge(String indexJudge) {
        this.indexJudge = indexJudge;
    }

    public String getIndexName(){return indexName;}

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexStatus(){return indexStatus;}

    public void setIndexStatus(String indexStatus) {
        this.indexStatus = indexStatus;
    }

    public String getIndexCode(){return indexCode;}

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getIndexData(){return indexData;}

    public void setIndexData(String indexName) {
        this.indexData = indexData;
    }

    public Index(Boolean selected,String indexCode,String indexData,String indexJudge,String indexName,String indexNum,String indexStatus) {
        this.indexCode = indexCode;
        this.indexData = indexData;
        this.indexJudge = indexJudge;
        this.indexStatus =indexStatus;
        this.indexName = indexName;
        this.indexNum = indexNum;
        this.selected = selected;
    }


}
