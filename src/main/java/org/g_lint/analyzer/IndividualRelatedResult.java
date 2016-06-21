package org.g_lint.analyzer;

import org.gedcom4j.model.Individual;

public class IndividualRelatedResult extends AResult {
    private final Individual individual;

    private final String factType;

    private final String value;

    private final String problem;

    public IndividualRelatedResult(Individual individual, String factType, String value, String problem) {
        this.individual = individual;
        this.factType = factType;
        this.value = value;
        this.problem = problem;
    }

    public String getFactType() {
        return factType;
    }

    public Individual getIndividual() {
        return individual;
    }

    public String getProblem() {
        return problem;
    }

    public String getValue() {
        return value;
    }
}
