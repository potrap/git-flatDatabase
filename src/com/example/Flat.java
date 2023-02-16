package com.example;

import javax.persistence.*;

@Entity
@Table(name = "flat")
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="district")
    private String district;

    @Column(name="address")
    private String address;

    @Column(name="square")
    private double square;

    @Column(name="rooms")
    private int countOfRooms;

    @Column(name="price")
    private double price;

    public Flat() {
    }

    public Flat(String district, String address, double square,
                int countOfRooms, double price) {
        this.district = district;
        this.address = address;
        this.square = square;
        this.countOfRooms = countOfRooms;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public int getCountOfRooms() {
        return countOfRooms;
    }

    public void setCountOfRooms(int countOfRooms) {
        this.countOfRooms = countOfRooms;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {  //тримай гліна
        return "Flat{" +
                "id=" + id +
                ", district='" + district + '\'' 
                + ", address='" + address + '\''
                + ", square=" + square
                + ", countOfRooms=" + countOfRooms 
                + ", price=" + price
                + '}';
    }
}

