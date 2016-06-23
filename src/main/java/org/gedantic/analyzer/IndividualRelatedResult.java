package org.gedantic.analyzer;

import org.gedcom4j.model.Individual;

/**
 * An analysis result about a specific individual. Immutable.
 * 
 * @author frizbog
 */
public class IndividualRelatedResult extends AResult {
    /**
     * The individual
     */
    private final Individual individual;

    /**
     * The type of fact having an issue
     */
    private final String factType;

    /**
     * The value that caused the problem
     */
    private final String value;

    /**
     * The description of the problem
     */
    private final String problem;

    /**
     * Constructor
     * 
     * @param individual
     *            the individual with the finding
     * @param factType
     *            the fact that the finding relates to - optional
     * @param value
     *            the value that was problematic - optional
     * @param problem
     *            a description of the problem - optional
     */
    public IndividualRelatedResult(Individual individual, String factType, String value, String problem) {
        this.individual = individual;
        this.factType = factType;
        this.value = value;
        this.problem = problem;
    }

    /**
     * Get the fact type
     * 
     * @return the fact type
     */
    public String getFactType() {
        return factType;
    }

    /**
     * Get the individual
     * 
     * @return the individual
     */
    public Individual getIndividual() {
        return individual;
    }

    /**
     * Get the problem description
     * 
     * @return the problem description
     */
    public String getProblem() {
        return problem;
    }

    /**
     * Get the problematic value
     * 
     * @return the value that cause the problem
     */
    public String getValue() {
        return value;
    }
}
