package org.g_lint.analyzer;

import org.gedcom4j.model.Individual;

public class IndividualRelatedResult extends AResult {
    public Individual individual;

    public String factType;

    public String value;

    public String problem;

    public IndividualRelatedResult(Individual individual, String factType, String value, String problem) {
        this.individual = individual;
        this.factType = factType;
        this.value = value;
        this.problem = problem;
    }
}
