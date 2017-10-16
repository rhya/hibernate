package com.nf.emp_ms.entity;

import javax.persistence.*;

/**
 * Created by admin on 2017/10/16.
 */
@Entity
public class Company {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String address;

    @Version
    private long version;

    public Company() {
    }

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
