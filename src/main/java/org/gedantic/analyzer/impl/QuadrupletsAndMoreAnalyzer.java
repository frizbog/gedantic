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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.result.DateAndString;
import org.gedantic.analyzer.result.FamilyRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualReference;
import org.gedcom4j.parser.DateParser.ImpreciseDatePreference;

/**
 * An analyzer that finds couples without children.
 * 
 * @author frizbog
 */
public class QuadrupletsAndMoreAnalyzer extends AAnalyzer {

    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();
        for (Family f : g.getFamilies().values()) {

            // If there aren't at least 4 children there's nothing to do with this family
            if (f.getChildren() == null || f.getChildren().size() < 4) {
                continue;
            }

            /*
             * Sort out the children into lists of people, by their (approximate) birth date. Treat people born within 48h of each
             * other as on the same date (multiple births)
             */
            Map<DateAndString, Set<Individual>> births = new HashMap<>();
            for (IndividualReference iRef : f.getChildren()) {
                Individual i = iRef.getIndividual();
                DateAndString birthDate = getBirthDate(i, ImpreciseDatePreference.PRECISE);
                if (birthDate == null || birthDate.getDate() == null) {
                    continue;
                }
                boolean added = false;
                for (DateAndString das : births.keySet()) {
                    // Look for any already existing list within 48h of this birth date
                    if (Math.abs(das.getDate().getTime() - birthDate.getDate().getTime()) < 48L * 60 * 60 * 1000) {
                        births.get(das).add(i);
                        added = true;
                    }
                }
                if (!added) {
                    Set<Individual> birthsOnDate = new HashSet<>();
                    birthsOnDate.add(i);
                    births.put(birthDate, birthsOnDate);
                }
            }

            /* Look through the birth dates and see if any have 4 or more individuals born on those dates */
            for (Entry<DateAndString, Set<Individual>> e : births.entrySet()) {
                if (e.getValue().size() >= 4) {
                    // We got a hit, add to results
                    result.add(new FamilyRelatedResult(f, null, e.getValue(), null));
                }
            }

        }
        return result;
    }

    @Override
    public String getDescription() {
        return "Families where there are quadruplets, quintuplets, or more multiple births";
    }

    @Override
    public String getName() {
        return "Quadruplets and more";
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
