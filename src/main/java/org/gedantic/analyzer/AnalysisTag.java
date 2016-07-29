package org.gedantic.analyzer;

/**
 * The types (categories) of analyses available. Any given analysis may have one or more of these tag values. Used to produce the
 * filter bar so users can filter based on these tags to find the analysis they want.
 * 
 * @author frizbog
 */
public enum AnalysisTag {
    /**
     * A likely problem with the data
     */
    PROBLEM("1", "Problem", "A likely problem, inconsistency, or item to be verified/corrected"),

    /**
     * Analysis about or includes families
     */
    FAMILIES("2", "Families", "Analysis is about or includes families"),

    /**
     * Data that might be expected but isn't there - potential area for more research
     */
    MISSING_DATA("3", "Missing data", "Expected data not found - potential area for more research"),

    /**
     * Analysis about or includes individual
     */
    INDIVIDUALS("4", "Individuals", "Analysis is about or includes individuals"),

    /**
     * Analysis about source
     */
    SOURCES("5", "Sources", "Analysis is about sources, repositories, or citations");

    /**
     * The description of this tag - will be displayed on the site as a tooltip
     */
    private final String description;

    /**
     * The name of this tag/category for displaying in the button
     */
    private final String name;

    /**
     * The id of this tag/category - this will be a very short id of the category used by the filtering script
     */
    private final String id;

    /**
     * Constructor
     * 
     * @param id
     *            the id of this tag/category
     * 
     * @param name
     *            The name of this tag/category
     * 
     * @param description
     *            The description of this tag - will be displayed on the site as well
     */
    private AnalysisTag(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Get the description
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the id
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Get the name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

}
