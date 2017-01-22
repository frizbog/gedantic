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
import org.gedantic.analyzer.DateAndString;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.parser.DateParser.ImpreciseDatePreference;

/**
 * An analyzer to find people who are ancestors of themselves due to illegal cyclical relationshops
 * 
 * @author frizbog
 */
public class DescendantsBornBeforeAncestorsAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        List<AnalysisResult> result = new ArrayList<>();
        for (Individual i : g.getIndividuals().values()) {
            DateAndString ibd = getBirthDate(i, ImpreciseDatePreference.FAVOR_EARLIEST);
            if (ibd == null || ibd.getDate() == null) {
                continue;
            }
            for (Individual a : i.getAncestors()) {
                DateAndString abd = getBirthDate(a, ImpreciseDatePreference.FAVOR_EARLIEST);
                if (abd != null && abd.getDate() != null && abd.getDate().after(ibd.getDate())) {
                    // Ancestor born after individual

                    result.add(new AnalysisResult("Individual", i.getFormattedName(), "Birth", ibd.getDateString(), "Ancestor " + a
                            .getFormattedName() + " born " + abd.getDateString()));
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "People who have a birth date that precedes that of any of their ancestors";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Descendants born before ancestors";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_INDIVIDUAL_RESULTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.PROBLEM, AnalysisTag.INDIVIDUALS };
    }
}
