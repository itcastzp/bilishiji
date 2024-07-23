package com.example.demo;

import javax.persistence.*;

@Table(name = "GENERIC_LATEST_SYNC")
@Entity
public class LatestSync {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String latestSync;


    private int syncNum;


    private String syncElapsedTime;

    public String getSyncElapsedTime() {
        return syncElapsedTime;
    }

    public void setSyncElapsedTime(String syncElapsedTime) {
        this.syncElapsedTime = syncElapsedTime;
    }

    public int getSyncNum() {
        return syncNum;
    }

    public void setSyncNum(int syncNum) {
        this.syncNum = syncNum;
    }

    public String getLatestSync() {
        return latestSync;
    }

    public void setLatestSync(String latestSync) {
        this.latestSync = latestSync;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
