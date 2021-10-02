package com.jasdom.validators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailValidator {

    private final String allowedCharacters = "!#$%&'*+-/=?^_`{|}~";
    private List<String> knownTLDs = new ArrayList<>();

    public EmailValidator(){
        loadTLDList();
    }

    public boolean validateEmail(String email) {

        if(email == null) return false;
        if(!email.contains("@")) return false;

        String[] split = email.split("@");
        if(split.length > 2) return false;

        String localPart = split[0];
        String domain = split[1];

        return validateLocalPart(localPart) && validateDomain(domain);
    }

    private boolean validateLocalPart(String localPart){
        if(localPart == null) return false;

        int length = localPart.length();

        if(length == 0 || length > 64) return false;
        if(localPart.charAt(0) == '.' || localPart.charAt(length - 1) == '.') return false;
        if(localPart.contains("..")) return false;

        for(int i = 0; i < length; i++){
            char c = localPart.charAt(i);

            if(!isLetter(c) && !isDigit(c) && !isAllowedCharacter(c)) return false;
        }

        return true;
    }

    private boolean validateDomain(String domain){
        if(domain == null) return false;
        if(domain.length() == 0 || domain.length() > 255) return false;
        if(!domain.contains(".")) return false;

        String[] labels = domain.split("\\.");

        for(int i = 0; i < labels.length; i++){
            String label = labels[i];
            if(i == labels.length - 1 && !validateTLD(label)) return false;
            if(!validateLabel(label)) return false;
        }

        return true;
    }

    private boolean validateLabel(String label){
        if(label == null) return false;
        int length = label.length();

        if(length == 0 || length > 63) return false;
        if(label.charAt(0) == '-' || label.charAt(length - 1) == '-') return false;

        for(int i = 0; i < length; i++){
            char c = label.charAt(i);

            if(!isLetter(c) && !isDigit(c) && c != '-') return false;
        }

        return true;
    }

    private boolean validateTLD(String domain){
        return knownTLDs.contains(domain);
    }

    private boolean isLetter(char c){
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }

    private boolean isDigit(char c){
        return (c >= 48 && c <= 57);
    }

    private boolean isAllowedCharacter(char c){
        return allowedCharacters.indexOf(c) >= 0;
    }

    private void loadTLDList(){
        try{
            loadTLD();
        }catch(IOException e){
            knownTLDs = new ArrayList<>();
        }
    }

    private void loadTLD() throws IOException {
        String file = "resources/tld-list.csv";
        knownTLDs = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {
            knownTLDs.add(line);
        }
    }
}
