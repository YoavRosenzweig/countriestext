package com.project.yoavr.countries;

public class Item {
    private String Ename;
    private String EnativeName;
    private Double Size;

    public String getEname() {
        return Ename;
    }

    public String getEnativeName() {
        return EnativeName;
    }

    public Item(String name, String nativeName){
        Ename=name;
        EnativeName=nativeName;

    }

    public Double getSize() {
        return Size;
    }

    public Item(String name, String nativeName, Double area){
        Ename=name;
        EnativeName=nativeName;
        Size=area;


    }
}
