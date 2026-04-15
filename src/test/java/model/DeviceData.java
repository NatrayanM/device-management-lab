package model;

import java.math.BigDecimal;

public class DeviceData {
    private Integer year;
    private Double price;

    public Integer getYear(){
        return year;
    }

    public DeviceData setYear(Integer year){
        this.year = year;
        return this;
    }
    public Double getPrice(){
        return price;
    }

    public DeviceData setPrice(Double price){
        this.price = price;
        return this;
    }
}
