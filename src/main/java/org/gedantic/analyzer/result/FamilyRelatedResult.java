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
package org.gedantic.analyzer.result;

import java.util.Set;

import org.gedantic.analyzer.AResult;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;

/**
 * An analysis result about a specific family. Immutable.
 * 
 * @author frizbog
 */
public class FamilyRelatedResult extends AResult {
    /**
     * The Family
     */
    private final Family family;

    /**
     * Constructor
     * 
     * @param family
     *            the family with the finding
     * @param factType
     *            the fact that the finding relates to - optional
     * @param value
     *            the individual that was problematic - optional
     * @param problem
     *            a description of the problem - optional
     */
    public FamilyRelatedResult(Family family, String factType, Individual value, String problem) {
        super(factType, value, problem);
        this.family = family;
    }

    /**
     * Constructor
     * 
     * @param family
     *            the family with the finding
     * @param factType
     *            the fact that the finding relates to - optional
     * @param value
     *            the individuals that were problematic - optional, usually a list of children
     * @param problem
     *            a description of the problem - optional
     */
    public FamilyRelatedResult(Family family, String factType, Set<Individual> value, String problem) {
        super(factType, value, problem);
        this.family = family;
    }

    /**
     * Constructor
     * 
     * @param family
     *            the family with the finding
     * @param factType
     *            the fact that the finding relates to - optional
     * @param value
     *            the value that was problematic - optional
     * @param problem
     *            a description of the problem - optional
     */
    public FamilyRelatedResult(Family family, String factType, String value, String problem) {
        super(factType, value, problem);
        this.family = family;
    }

    /**
     * Get the family
     * 
     * @return the family
     */
    public Family getFamily() {
        return family;
    }
}
