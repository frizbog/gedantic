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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.gedantic.analyzer.impl.AdultsWithoutSpousesAnalyzer;
import org.gedantic.analyzer.impl.BadEmailAnalyzer;
import org.gedantic.analyzer.impl.BirthsToOldParentsAnalyzer;
import org.gedantic.analyzer.impl.BirthsToYoungParentsAnalyzer;
import org.gedantic.analyzer.impl.ChildrenWithDifferentSurnamesAnalyzer;
import org.gedantic.analyzer.impl.ChildrenWithSameFirstNamesAnalyzer;
import org.gedantic.analyzer.impl.CircularAncestryAnalyzer;
import org.gedantic.analyzer.impl.ConflictingDatesAnalyzer;
import org.gedantic.analyzer.impl.CouplesWithCommonAncestorsAnalyzer;
import org.gedantic.analyzer.impl.CouplesWithLargeAgeDifferenceAnalyzer;
import org.gedantic.analyzer.impl.CouplesWithSameBirthSurnames;
import org.gedantic.analyzer.impl.CouplesWithoutChildrenAnalyzer;
import org.gedantic.analyzer.impl.DatesButNoPlacesAnalyzer;
import org.gedantic.analyzer.impl.DescendantsBornBeforeAncestorsAnalyzer;
import org.gedantic.analyzer.impl.EventsWithoutDatesAnalyzer;
import org.gedantic.analyzer.impl.EventsWithoutPlacesOrDatesAnalyzer;
import org.gedantic.analyzer.impl.FactsWithoutSourcesAnalyzer;
import org.gedantic.analyzer.impl.FutureBirthDeathDatesAnalyzer;
import org.gedantic.analyzer.impl.MaleWivesFemaleHusbandsAnalyzer;
import org.gedantic.analyzer.impl.MarriagesWithoutDatesAnalyzer;
import org.gedantic.analyzer.impl.MarriedAtYoungAgeAnalyzer;
import org.gedantic.analyzer.impl.OnlyChildrenAnalyzer;
import org.gedantic.analyzer.impl.PeopleWhoLivedPast100Analyzer;
import org.gedantic.analyzer.impl.PeopleWithOneMissingParentAnalyzer;
import org.gedantic.analyzer.impl.PeopleWithOnlySurnamesAnalyzer;
import org.gedantic.analyzer.impl.PeopleWithoutBirthDatesAnalyzer;
import org.gedantic.analyzer.impl.PeopleWithoutDeathEventsAnalyzer;
import org.gedantic.analyzer.impl.PeopleWithoutNamesAnalyzer;
import org.gedantic.analyzer.impl.PeopleWithoutOccupationsAnalysis;
import org.gedantic.analyzer.impl.PeopleWithoutParentsAnalyzer;
import org.gedantic.analyzer.impl.PeopleWithoutSurnamesAnalyzer;
import org.gedantic.analyzer.impl.PlacesButNoDatesAnalyzer;
import org.gedantic.analyzer.impl.QuadrupletsAndMoreAnalyzer;
import org.gedantic.analyzer.impl.SimultaneousBirthsInMultipleLocationsAnalyzer;
import org.gedantic.analyzer.impl.SourcesWithoutRepositoryCitationsOrMediaAnalyzer;
import org.gedantic.analyzer.impl.UnparsableDatesAnalyzer;
import org.gedantic.analyzer.impl.UnspecifiedSexAnalyzer;

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
     * A List (yes a List) of unique analysis tags, for putting into the filter selector bar.
     */
    private final List<AnalysisTag> tags;

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
        addAnalyzer(new FutureBirthDeathDatesAnalyzer());
        addAnalyzer(new PeopleWhoLivedPast100Analyzer());
        addAnalyzer(new PeopleWithOnlySurnamesAnalyzer());
        addAnalyzer(new CircularAncestryAnalyzer());
        addAnalyzer(new CouplesWithCommonAncestorsAnalyzer());
        addAnalyzer(new CouplesWithSameBirthSurnames());
        addAnalyzer(new AdultsWithoutSpousesAnalyzer());
        addAnalyzer(new MaleWivesFemaleHusbandsAnalyzer());
        addAnalyzer(new DescendantsBornBeforeAncestorsAnalyzer());
        addAnalyzer(new ConflictingDatesAnalyzer());
        addAnalyzer(new QuadrupletsAndMoreAnalyzer());
        addAnalyzer(new SimultaneousBirthsInMultipleLocationsAnalyzer());
        addAnalyzer(new ChildrenWithSameFirstNamesAnalyzer());
        addAnalyzer(new BirthsToYoungParentsAnalyzer());
        addAnalyzer(new BirthsToOldParentsAnalyzer());
        addAnalyzer(new CouplesWithLargeAgeDifferenceAnalyzer());
        addAnalyzer(new MarriedAtYoungAgeAnalyzer());
        addAnalyzer(new BadEmailAnalyzer());

        Set<AnalysisTag> unique = new TreeSet<>();
        for (IAnalyzer a : analyzers.values()) {
            for (AnalysisTag t : a.getTags()) {
                unique.add(t);
            }
        }
        tags = new ArrayList<>(unique);
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
     * Get the tags
     * 
     * @return the tags
     */
    public List<AnalysisTag> getTags() {
        return tags;
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
