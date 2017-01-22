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
package org.gedantic.analyzer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AnalysisResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.result.FamilyRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.IndividualReference;
import org.gedcom4j.model.Place;
import org.gedcom4j.model.enumerations.IndividualEventType;
import org.gedcom4j.parser.DateParser;

/**
 * An analyzer that finds couples without children.
 * 
 * @author frizbog
 */
public class SimultaneousBirthsInMultipleLocationsAnalyzer extends AAnalyzer {

    /**
     * A birth of a specific person
     */
    private class Birth {

        /** The person. */
        Individual person;

        /** The birth. */
        IndividualEvent birth;
    }

    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        List<AnalysisResult> result = new ArrayList<>();
        DateParser dp = new DateParser();
        for (Family f : g.getFamilies().values()) {

            // If there aren't at least 2 children there's nothing to do with this family
            if (f.getChildren() == null || f.getChildren().size() < 2) {
                continue;
            }

            /*
             * Sort out the children into lists of people, grouping by their (approximate) birth date. Treat people born within 48h
             * of each other as on the same date (twins, triplets, etc.)
             */
            Map<Date, Set<Birth>> birthsByDate = new HashMap<>();

            for (IndividualReference iRef : f.getChildren()) {
                Individual i = iRef.getIndividual();
                List<IndividualEvent> birthEvents = i.getEventsOfType(IndividualEventType.BIRTH);
                for (IndividualEvent birthEvent : birthEvents) {
                    if (birthEvent.getDate() == null) {
                        continue;
                    }
                    Date birthDate = dp.parse(birthEvent.getDate().getValue());
                    if (birthDate == null) {
                        continue;
                    }

                    boolean added = false;
                    for (Date das : birthsByDate.keySet()) {
                        // Look for any already existing set or people born within 48h of this birth date
                        if (Math.abs(das.getTime() - birthDate.getTime()) < 48L * 60 * 60 * 1000) {
                            /*
                             * The existing date in the Map is roughly the same as this birth date, so add this birth to that map
                             * entry
                             */
                            Birth b = new Birth();
                            b.birth = birthEvent;
                            b.person = i;
                            birthsByDate.get(das).add(b);
                            added = true;
                        }
                    }
                    // If we didn't add the birth to any pre-existing map entry, do it now.
                    if (!added) {
                        Set<Birth> birthsOnDate = new HashSet<>();
                        Birth b = new Birth();
                        b.birth = birthEvent;
                        b.person = i;
                        birthsOnDate.add(b);
                        birthsByDate.put(birthDate, birthsOnDate);
                    }
                }
            }

            // Go through all the consolidated dates of birth
            for (Entry<Date, Set<Birth>> e : birthsByDate.entrySet()) {
                // See how many places were listed for the births on this date
                Set<Place> places = new HashSet<>();
                Set<Birth> birthsOnDate = e.getValue();
                Set<Individual> kids = new HashSet<>();
                String birthDateString = null;
                for (Birth b : birthsOnDate) {
                    if (b.birth.getPlace() != null) {
                        places.add(b.birth.getPlace());
                        kids.add(b.person);
                        birthDateString = b.birth.getDate().getValue();
                    }
                }
                if (places.size() > 1) {
                    // Found multiple places on this date
                    StringBuilder problem = new StringBuilder("Date: " + birthDateString + " - Places: ");
                    boolean first = true;
                    for (Place p : places) {
                        if (!first) {
                            problem.append("; ");
                        }
                        first = false;
                        problem.append(p.getPlaceName());
                    }
                    result.add(new FamilyRelatedResult(f, null, kids, problem.toString()));
                }
            }

        }
        return result;
    }

    @Override
    public String getDescription() {
        return "Families where there multiple births at the same time but different locations";
    }

    @Override
    public String getName() {
        return "Simultaneous births in multiple locations";
    }

    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_COUPLE_RESULTS;
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.PROBLEM, AnalysisTag.FAMILIES };
    }

}
