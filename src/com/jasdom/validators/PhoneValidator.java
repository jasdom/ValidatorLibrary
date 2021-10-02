package com.jasdom.validators;

import java.util.HashMap;
import java.util.Map;

public class PhoneValidator {

    private Map<String, CountryPrefix> prefixes = new HashMap<>();

    public PhoneValidator(){
        addCountryPrefix("LT", "+370", 8, "8");
        addCountryPrefix("LS", "+266", 8);
        addCountryPrefix("MV", "+960", 7);
    }

    public boolean validatePhoneNumber(String number) {
        if(number == null) return false;
        if(number.length() == 0) return false;

        String convertedNumber = convertNumber(number);
        if(convertedNumber.charAt(0) != '+') return false;
        if(!checkDigits(convertedNumber.substring(1))) return false;

        return checkCountryNumber(convertedNumber);
    }

    public boolean addCountryPrefix(String code, String prefix, int length){
        return addPrefix(code, prefix, length, null);
    }

    public boolean addCountryPrefix(String code, String prefix, int length, String trunk){
        return addPrefix(code, prefix, length, trunk);
    }

    private boolean checkCountryNumber(String number){
        for (Map.Entry<String, CountryPrefix> entry : prefixes.entrySet()) {
            CountryPrefix countryPrefix = entry.getValue();

            if(!number.startsWith(countryPrefix.prefix)) continue;
            if(number.length() != countryPrefix.length + countryPrefix.prefix.length()) continue;

            return true;
        }

        return false;
    }

    private String convertNumber(String number){
        String converted = number;

        for (Map.Entry<String, CountryPrefix> entry : prefixes.entrySet()) {
            CountryPrefix countryPrefix = entry.getValue();

            if(countryPrefix.trunk == null) continue;
            if(!number.startsWith(countryPrefix.trunk)) continue;

            converted = countryPrefix.prefix + number.substring(countryPrefix.trunk.length());

            break;
        }

        return converted;
    }

    private boolean checkDigits(String number){
        for(int i = 0; i < number.length(); i++){
            char c = number.charAt(i);

            if(!isDigit(c)) return false;
        }

        return true;
    }

    private boolean addPrefix(String code, String prefix, int length, String trunk){
        if(code == null || code.length() == 0) return false;
        if(prefix == null || prefix.length() == 0 || prefix.charAt(0) != '+') return false;
        if(trunk != null && trunk.length() == 0) return false;

        CountryPrefix countryPrefix = new CountryPrefix(prefix, length, trunk);
        prefixes.put(code, countryPrefix);

        return true;
    }

    private boolean isDigit(char c){
        return (c >= 48 && c <= 57);
    }
}
