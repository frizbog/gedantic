/*
 * Copyright (c) 2016 Matthew R. Harrah
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.gedantic.analyzer;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * The types (categories) of analyses available. Any given analysis may have one or more of these tag values. Used to produce the
 * filter bar so users can filter based on these tags to find the analysis they want.
 * 
 * @author frizbog
 */
public enum AnalysisTag {
    /**
     * A likely problemDescription with the data
     */
    PROBLEM("1", "Problem", "A likely problemDescription, inconsistency, or item to be verified/corrected"),

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
        this.id = StringEscapeUtils.escapeHtml(id);
        this.name = StringEscapeUtils.escapeHtml(name);
        this.description = StringEscapeUtils.escapeHtml(description);
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
