package com.jasdom.user_module.user_module.service;

import com.jasdom.user_module.user_module.model.User;
import main.java.validators.EmailValidator;
import main.java.validators.PasswordChecker;
import main.java.validators.PhoneValidator;
import org.springframework.stereotype.Component;

//@Component
public class UserValidator {

    private final EmailValidator emailValidator;
    private final PasswordChecker passwordChecker;
    private final PhoneValidator phoneValidator;

    private final int minPasswordLength = 6;

    public UserValidator(){
        emailValidator = new EmailValidator();
        passwordChecker = new PasswordChecker();
        phoneValidator = new PhoneValidator();

        phoneValidator.checkNewRule("+370", 12, "LT");
    }

    public ServiceResponse<User> validateUser(User user){

        String errorMessage = "";
        boolean success = false;

        if(user.getName() == null || user.getName().length() == 0){
            errorMessage += "Name can't be empty. ";
        }

        if(user.getSurname() == null || user.getSurname().length() == 0){
            errorMessage += "Surname can't be empty. ";
        }

        if(!validateEmail(user.getEmailAddress())){
            errorMessage += "Incorrect email address. ";
        }

        if(!validatePhoneNumber(user.getPhoneNumber())){
            errorMessage += "Incorrect phone number. ";
        }

        if(user.getAddress() == null || user.getAddress().length() == 0){
            errorMessage += "Address can't be empty.";
        }

        if(!validatePassword(user.getPassword())){
            errorMessage += "Password must be atleast " + minPasswordLength + " characters long. Password must contain uppercase characters. Password must contain a special symbol. ";
        }

        if(errorMessage == "") success = true;
        return new ServiceResponse<>(success, user, errorMessage);
    }

    public boolean validateEmail(String email){
        return emailValidator.validateEmail(email);
    }

    public boolean validatePassword(String password){
        return passwordChecker.validatePassword(password, minPasswordLength);
    }

    public boolean validatePhoneNumber(String phoneNumber){
        return phoneValidator.validatePhoneNumber(phoneNumber, "LT") || phoneValidator.validatePhoneNumber(phoneNumber);
    }

}
