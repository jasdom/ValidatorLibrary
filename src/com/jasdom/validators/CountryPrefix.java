package com.jasdom.validators;

public class CountryPrefix {
    public String prefix;
    public int length;
    public String trunk;

    public CountryPrefix(String prefix, int length, String trunk){
        this.prefix = prefix;
        this.length = length;
        this.trunk = trunk;
    }
}
