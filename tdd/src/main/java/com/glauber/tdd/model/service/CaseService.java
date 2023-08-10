package com.glauber.tdd.model.service;

public class CaseService {
    public String lowerPlusFirstCapital(String text){
        if (text.isBlank()){
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}
