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
 * Base class for an analysis result
 * 
 * @author frizbog
 */
public abstract class AResult {

    /**
     * The type of fact having an issue
     */
    protected final String factType;

    /**
     * The value that caused the problem
     */
    protected final String value;

    /**
     * The description of the problem
     */
    protected final String problem;

    /**
     * @param factType
     * @param value
     * @param problem
     */
    public AResult(String factType, String value, String problem) {
        this.factType = StringEscapeUtils.escapeHtml(factType);
        this.value = StringEscapeUtils.escapeHtml(value);
        this.problem = StringEscapeUtils.escapeHtml(problem);
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
