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

import java.util.List;

import org.gedcom4j.model.Gedcom;

/**
 * Base class for all analyzers
 * 
 * @author frizbog
 */
public interface IAnalyzer {

    /**
     * Analyze a {@link Gedcom}, and return some results
     * 
     * @param g
     *            the {@link Gedcom} to analyze
     * @return a {@link List} of results
     */
    List<AResult> analyze(Gedcom g);

    /**
     * Get a description for this analyzer.
     * 
     * @return the description of this analyzer.
     */
    String getDescription();

    /**
     * Get the ID (i.e., className) for this analyzer
     * 
     * @return the name of this analyzer
     */
    String getId();

    /**
     * Get the short name for this analyzer
     * 
     * @return the name of this analyzer
     */
    String getName();

    /**
     * Get the name of the tile to use for displaying the results
     * 
     * @return the name of the tile to use for displaying the results
     */
    String getResultsTileName();

    /**
     * Get an array of tags/categories for this analyzer
     * 
     * @return an array of tags/categories for this analyzer
     */
    AnalysisTag[] getTags();
}
