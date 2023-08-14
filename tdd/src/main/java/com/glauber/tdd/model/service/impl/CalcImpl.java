package com.glauber.tdd.model.service.impl;

import com.glauber.tdd.model.repository.Calc;

public class CalcImpl implements Calc {
    @Override
    public int somar(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }
}