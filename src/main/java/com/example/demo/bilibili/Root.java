package com.example.demo.bilibili;


import java.util.BitSet;

public class Root {
    private Long code;
    private String message;
    private Data data;
    private Long errtag;

    public Root() {
    }

    public static void main(String[] args) {
        BitSet set = new BitSet();

        int groupNumber = 50;
        int index = 0;
        for (int i = 0; i < 1341; i++) {
            set.set(i);
            index++;
            if (index % groupNumber == 0) {
                System.out.println("批处理提交" + index);
            }
            if (index % groupNumber != 0) {
                System.out.println("ceil"+Math.ceil(index / groupNumber));
                System.out.println("-----" + Math.floorMod(index, groupNumber));
            }
        }
        System.out.println(set);

    }

    public Root(Long code, String message, Data data, Long errtag) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.errtag = errtag;
    }

    public Long getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Data getData() {
        return this.data;
    }

    public Long getErrtag() {
        return this.errtag;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setErrtag(Long errtag) {
        this.errtag = errtag;
    }

    @Override
    public String toString() {
        String sb = "Root{" + "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", errtag=" + errtag +
                '}';
        return sb;
    }
}


