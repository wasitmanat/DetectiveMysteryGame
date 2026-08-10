package com.detective.factory;

import com.detective.model.Evidence;

public class EvidenceFactory {
    public static Evidence createEvidence(String name, String description) {
        return new Evidence(name, description);
    }
}