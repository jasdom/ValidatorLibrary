package com.jasdom.validators;

public class PasswordChecker {

    private int minLength;
    private boolean needsUppercase;
    private boolean needsSpecialSymbols;
    private String specialSymbols;

    public PasswordChecker(){
        minLength(6);
        needsUppercase();
        specialSymbols("!@#$%^&*()_-.");
    }

    public boolean validatePassword(String password) {
        boolean hasUppercase = false;
        boolean hasSpecialSymbols = false;

        if(password == null) return false;
        if(password.length() < minLength) return false;

        for(int i = 0; i < password.length(); i++){
            char c = password.charAt(i);

            if(isUppercase(c)) hasUppercase = true;
            if(isSpecialCharacter(c)) hasSpecialSymbols = true;
        }

        if(needsUppercase && !hasUppercase) return false;
        return !needsSpecialSymbols || hasSpecialSymbols;
    }

    public void specialSymbols(String symbols){
        specialSymbols = symbols;
        needsSpecialSymbols = true;
    }

    public void minLength(int length){
        minLength = length;
    }

    public void needsUppercase(){
        needsUppercase = true;
    }

    private boolean isUppercase(char c){
        return (c >= 65 && c <= 90);
    }

    private boolean isSpecialCharacter(char c){
        return specialSymbols.indexOf(c) >= 0;
    }
}
