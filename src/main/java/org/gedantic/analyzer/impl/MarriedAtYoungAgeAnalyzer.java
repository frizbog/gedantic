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
import java.util.List;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AnalysisResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.DateAndString;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyEvent;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.enumerations.FamilyEventType;
import org.gedcom4j.parser.DateParser;
import org.gedcom4j.parser.DateParser.ImpreciseDatePreference;

/**
 * An analyzer that finds couples where one of the spouses was 16 or younger at time of marriage
 * 
 * @author frizbog
 */
public class MarriedAtYoungAgeAnalyzer extends AAnalyzer {

    /**
     * Number of milliseconds in sixty years
     */
    private static final long MILLIS_IN_SIXTEEN_YEARS = (long) (16 * 365.25 * 24 * 60 * 60 * 1000);

    /**
     * Number of milliseconds in a year
     */
    private static final long MILLIS_IN_YEAR = (long) (365.25 * 24 * 60 * 60 * 1000);

    /**
     * Date parser
     */
    private static final DateParser DP = new DateParser();

    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        List<AnalysisResult> result = new ArrayList<>();

        for (Family f : g.getFamilies().values()) {
            if (f.getHusband() == null || f.getWife() == null || f.getEvents() == null || f.getEvents().isEmpty()) {
                continue;
            }

            // Get the earliest possible marriage date
            FamilyEvent earliestMarriage = null;
            Date earliestMarriageDate = new Date();
            for (FamilyEvent e : f.getEvents()) {
                if (e.getType() == FamilyEventType.MARRIAGE) {
                    if (e.getDate() != null && e.getDate().getValue() != null) {
                        Date d = DP.parse(e.getDate().getValue());
                        if (d != null && d.before(earliestMarriageDate)) {
                            earliestMarriage = e;
                            earliestMarriageDate = d;
                        }
                    }
                }
            }
            if (earliestMarriage == null) {
                continue;
            }

            Individual husband = f.getHusband().getIndividual();
            DateAndString husbandLatestBirthDate = getBirthDate(husband, ImpreciseDatePreference.FAVOR_LATEST);

            Individual wife = f.getWife().getIndividual();
            DateAndString wifeLatestBirthDate = getBirthDate(wife, ImpreciseDatePreference.FAVOR_LATEST);

            // Both spouses need a birth date to proceed
            if ((husbandLatestBirthDate == null || husbandLatestBirthDate.getDate() == null || wifeLatestBirthDate == null
                    || wifeLatestBirthDate.getDate() == null)) {
                continue;
            }

            long hDiff = earliestMarriageDate.getTime() - husbandLatestBirthDate.getDate().getTime();
            long wDiff = earliestMarriageDate.getTime() - wifeLatestBirthDate.getDate().getTime();

            if (hDiff <= MILLIS_IN_SIXTEEN_YEARS || wDiff <= MILLIS_IN_SIXTEEN_YEARS) {
                int hAgeAtMarriage = (int) (hDiff / MILLIS_IN_YEAR);
                int wAgeAtMarriage = (int) (wDiff / MILLIS_IN_YEAR);

                StringBuilder problem = new StringBuilder();
                if (hDiff <= MILLIS_IN_SIXTEEN_YEARS) {
                    problem.append("Husband born ");
                    problem.append(husbandLatestBirthDate.getDateString());
                    problem.append(", aged ");
                    problem.append(hAgeAtMarriage);
                }
                if (wDiff <= MILLIS_IN_SIXTEEN_YEARS) {
                    if (problem.length() > 0) {
                        problem.append("; ");
                    }
                    problem.append("Wife born ");
                    problem.append(wifeLatestBirthDate.getDateString());
                    problem.append(", aged ");
                    problem.append(wAgeAtMarriage);
                }

                result.add(new AnalysisResult("Family", getFamilyDescriptor(f), FamilyEventType.MARRIAGE.getDisplay(),
                        earliestMarriage.getDate().getValue(), problem.toString()));
            }
        }

        return result;
    }

    @Override
    public String getDescription() {
        return "Marriages where one or both spouses were under 16";
    }

    @Override
    public String getName() {
        return "Married at young age";
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
