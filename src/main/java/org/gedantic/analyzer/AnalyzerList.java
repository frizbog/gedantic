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

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.gedantic.analyzer.impl.*;

/**
 * List of available Analyzers.
 * 
 * @author frizbog
 */
public final class AnalyzerList {

    /**
     * The singleton instance
     */
    private static final AnalyzerList INSTANCE = new AnalyzerList();

    /**
     * Get the singleton instance
     * 
     * @return the singleton instance
     */
    public static AnalyzerList getInstance() {
        return INSTANCE;
    }

    /**
     * A map of all available analyzers
     */
    private final Map<String, IAnalyzer> analyzers = new TreeMap<>();

    /**
     * Constructor
     */
    public AnalyzerList() {
        addAnalyzer(new FactsWithoutSourcesAnalyzer());
        addAnalyzer(new ChildrenWithDifferentSurnamesAnalyzer());
        addAnalyzer(new PeopleWithoutParentsAnalyzer());
        addAnalyzer(new OnlyChildrenAnalyzer());
        addAnalyzer(new PeopleWithoutSurnamesAnalyzer());
        addAnalyzer(new PeopleWithOneMissingParentAnalyzer());
        addAnalyzer(new PeopleWithoutBirthDatesAnalyzer());
        addAnalyzer(new PeopleWithoutDeathEventsAnalyzer());
        addAnalyzer(new PeopleWithoutNamesAnalyzer());
        addAnalyzer(new CouplesWithoutChildrenAnalyzer());
        addAnalyzer(new MarriagesWithoutDatesAnalyzer());
        addAnalyzer(new DatesButNoPlacesAnalyzer());
        addAnalyzer(new PlacesButNoDatesAnalyzer());
        addAnalyzer(new SourcesWithoutRepositoryCitationsOrMediaAnalyzer());
        addAnalyzer(new UnparsableDatesAnalyzer());
        addAnalyzer(new UnspecifiedSexAnalyzer());
        addAnalyzer(new EventsWithoutDatesAnalyzer());
        addAnalyzer(new EventsWithoutPlacesOrDatesAnalyzer());
        addAnalyzer(new PeopleWithoutOccupationsAnalysis());
    }

    /**
     * Get a map of all available analyzers
     * 
     * @return a map of all available analyzers
     */
    public Map<String, IAnalyzer> getAnalyzers() {
        return Collections.unmodifiableMap(analyzers);
    }

    /**
     * Add an analyzer to the Analyzers map
     * 
     * @param a
     *            the analyzer to add
     */
    private void addAnalyzer(IAnalyzer a) {
        analyzers.put(a.getId(), a);
    }

}
