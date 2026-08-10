package com.detective.state;

public class InvestigatingState implements GameState {
    @Override
    public String getStateName() {
        return "Investigating";
    }
}