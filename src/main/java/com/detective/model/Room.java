package com.detective.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private String description;
    private List<Evidence> evidenceList;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.evidenceList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Evidence> getEvidenceList() {
        return evidenceList;
    }

    public void addEvidence(Evidence evidence) {
        evidenceList.add(evidence);
    }
}