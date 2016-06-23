package org.gedantic.analyzer;

/**
 * A simple string result
 * 
 * @author frizbog
 */
public class SimpleResult extends AResult {
    /**
     * The description of the finding
     */
    private final String description;

    /**
     * Constructor
     * 
     * @param description
     *            the description of the finding
     */
    public SimpleResult(String description) {
        this.description = description;
    }

    /**
     * Get the description of the finding
     * 
     * @return the description of the finding
     */
    public String getDescription() {
        return description;
    }
}
