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
import org.gedantic.analyzer.AnalysisResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyEvent;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.enumerations.FamilyEventType;

/**
 * An analyzer that finds couples without any date information about their marriage.
 * 
 * @author frizbog
 */
public class MarriagesWithoutDatesAnalyzer extends AAnalyzer {

    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        List<AnalysisResult> result = new ArrayList<>();
        for (Family f : g.getFamilies().values()) {
            if (f.getWife() != null && f.getHusband() != null) {
                // We have a couple
                boolean foundMarriageWithDate = false;
                for (FamilyEvent e : f.getEvents()) {
                    if (e.getType() == FamilyEventType.MARRIAGE && e.getDate() != null && e.getDate().getValue().trim()
                            .length() >= 0) {
                        // We got a marriage date
                        foundMarriageWithDate = true;
                    }
                }
                if (!foundMarriageWithDate) {
                    result.add(new AnalysisResult("Family", getFamilyDescriptor(f), null, null,
                            "No marriage event with date found"));
                }
            }
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "Marriages where the husband and wife are known but there is no date for their marriage";
    }

    @Override
    public String getName() {
        return "Marriages without dates";
    }

    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_COUPLE_RESULTS;
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.MISSING_DATA, AnalysisTag.FAMILIES };
    }

}
