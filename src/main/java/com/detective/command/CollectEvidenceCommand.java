package com.detective.command;

import com.detective.manager.GameManager;
import com.detective.model.Evidence;

public class CollectEvidenceCommand implements Command {
    private Evidence evidence;

    public CollectEvidenceCommand(Evidence evidence) {
        this.evidence = evidence;
    }

    @Override
    public void execute() {
        GameManager.getInstance().collectEvidence(evidence);
    }
}