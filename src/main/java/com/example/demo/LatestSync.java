package com.example.demo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Table(name = "GENERIC_LATEST_SYNC")
@Entity
public class LatestSync  implements Serializable {
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

    public static void main(String[] args) {
        String[] a = {"1,2,3", "4,5,6"};
        //['1', '2', '3', '4', '5', '6']//
        System.out.println(Arrays.stream(a).flatMap(s -> Stream.of(s.split(","))).collect(Collectors.toList()));


    }
}
