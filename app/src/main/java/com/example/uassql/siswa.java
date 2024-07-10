package com.example.uassql;

public class siswa {
    private long id;
    private String name;
    private String nim;
    private double ipk;
    private String subject;

    public siswa(long id, String name, String nim, double ipk, String subject) {
        this.id = id;
        this.name = name;
        this.nim = nim;
        this.ipk = ipk;
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNim() {
        return nim;
    }

    public double getIpk() {
        return ipk;
    }

    public String getSubject() {
        return subject;
    }
}
