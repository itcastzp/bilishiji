package com.example.demo.bilibili;


import java.util.List;

public class Data {
    private List<Daum> data;
    private String nextId;

    public Data() {
    }

    public Data(List<Daum> data, String nextId) {
        this.data = data;
        this.nextId = nextId;
    }

    public List<Daum> getData() {
        return this.data;
    }

    public String getNextId() {
        return this.nextId;
    }

    public void setData(List<Daum> data) {
        this.data = data;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }


    @Override
    public String toString() {
        String sb = "Data{" + "data=" + data +
                ", nextId='" + nextId + '\'' +
                '}';
        return sb;
    }
}
