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
import java.util.List;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.result.DateAndString;
import org.gedantic.analyzer.result.FamilyRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.parser.DateParser.ImpreciseDatePreference;

/**
 * An analyzer that finds couples that had kids while one of the parents was 60 or older
 * 
 * @author frizbog
 */
public class BirthsToOldParentsAnalyzer extends AAnalyzer {

    /**
     * Number of milliseconds in sixty years
     */
    private static final long MILLIS_IN_SIXTY_YEARS = (long) (60 * 365.25 * 24 * 60 * 60 * 1000);

    /**
     * Number of milliseconds in a year
     */
    private static final long MILLIS_IN_YEAR = (long) (365.25 * 24 * 60 * 60 * 1000);

    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();

        for (Family f : g.getFamilies().values()) {
            // No kids? Not interested
            if (f.getChildren() == null || f.getChildren().isEmpty()) {
                continue;
            }

            Individual husband = f.getHusband();
            DateAndString husbandLatestBirthDate = getBirthDate(husband, ImpreciseDatePreference.FAVOR_LATEST);
            DateAndString husbandEarliestDeathDate = getDeathDate(husband, ImpreciseDatePreference.FAVOR_EARLIEST);

            Individual wife = f.getWife();
            DateAndString wifeLatestBirthDate = getBirthDate(wife, ImpreciseDatePreference.FAVOR_LATEST);
            DateAndString wifeEarliestDeathDate = getDeathDate(wife, ImpreciseDatePreference.FAVOR_EARLIEST);

            // Neither parent has a birth date? Can't calculate, so skip
            if ((husbandLatestBirthDate == null || husbandLatestBirthDate.getDate() == null) && (wifeLatestBirthDate == null
                    || wifeLatestBirthDate.getDate() == null)) {
                continue;
            }

            for (Individual kid : f.getChildren()) {
                DateAndString kidEarliestBirthDate = getBirthDate(kid, ImpreciseDatePreference.FAVOR_EARLIEST);
                if (kidEarliestBirthDate == null || kidEarliestBirthDate.getDate() == null) {
                    continue;
                }

                StringBuilder problem = new StringBuilder();

                if (wifeLatestBirthDate != null && wifeLatestBirthDate.getDate() != null) {
                    long momMillisDiff = kidEarliestBirthDate.getDate().getTime() - wifeLatestBirthDate.getDate().getTime();
                    if (momMillisDiff >= MILLIS_IN_SIXTY_YEARS) {
                        if (wifeEarliestDeathDate != null && wifeEarliestDeathDate.getDate() != null && wifeEarliestDeathDate
                                .getDate().before(kidEarliestBirthDate.getDate())) {
                            problem.append("Mother may have been deceased");
                        } else {
                            long yearsOld = momMillisDiff / MILLIS_IN_YEAR;
                            problem.append("Mother was " + yearsOld + " years old when child was born");
                        }
                    }
                }
                if (husbandLatestBirthDate != null && husbandLatestBirthDate.getDate() != null) {
                    long dadMillisDiff = kidEarliestBirthDate.getDate().getTime() - husbandLatestBirthDate.getDate().getTime();
                    if (dadMillisDiff >= MILLIS_IN_SIXTY_YEARS) {
                        if (husbandEarliestDeathDate != null && husbandEarliestDeathDate.getDate() != null
                                && husbandEarliestDeathDate.getDate().before(kidEarliestBirthDate.getDate())) {
                            if (problem.length() > 0) {
                                problem.append("; ");
                            }
                            problem.append("Father may have been deceased");
                        } else {
                            long yearsOld = dadMillisDiff / MILLIS_IN_YEAR;
                            if (problem.length() > 0) {
                                problem.append("; ");
                            }
                            problem.append("Father was " + yearsOld + " years old when child was born");
                        }
                    }
                }
                if (problem.length() > 0) {
                    result.add(new FamilyRelatedResult(f, null, kid, problem.toString()));
                }
            }

        }

        return result;
    }

    @Override
    public String getDescription() {
        return "Children born to parents who were over the age of 60";
    }

    @Override
    public String getName() {
        return "Children of elderly parents";
    }

    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_COUPLE_RESULTS;
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.PROBLEM, AnalysisTag.FAMILIES };
    }

    @Override
    public boolean isNewish() {
        return true;
    }

}
