package com.glauber.tdd.model.service;

public class StringService {
    private final WhiteSpaceService whiteSpaceService = new WhiteSpaceService();
    private final CaseService caseService = new CaseService();

    public String beautify(String ugly) {
        String text = whiteSpaceService.remove(ugly);
        return caseService.lowerPlusFirstCapital(text);
    }
}
