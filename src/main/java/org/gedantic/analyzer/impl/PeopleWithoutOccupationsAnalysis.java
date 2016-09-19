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
import java.util.Collections;
import java.util.List;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.comparator.IndividualResultSortComparator;
import org.gedantic.analyzer.result.IndividualRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualAttribute;
import org.gedcom4j.model.enumerations.IndividualAttributeType;

/**
 * Analysis to find people with no data about their occupation
 * 
 * @author frizbog
 */
public class PeopleWithoutOccupationsAnalysis extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {

        List<AResult> result = new ArrayList<>();

        for (Individual i : g.getIndividuals().values()) {
            if (i.getNames() == null || i.getNames().isEmpty()) {
                continue;
            }

            List<IndividualAttribute> occupations = i.getAttributesOfType(IndividualAttributeType.OCCUPATION);
            if (occupations.isEmpty()) {
                result.add(new IndividualRelatedResult(i, null, (String) null, null));
            } else {
                for (IndividualAttribute b : occupations) {
                    if (b.getDescription() == null || b.getDescription().getValue() == null || b.getDescription().getValue().trim()
                            .isEmpty()) {
                        result.add(new IndividualRelatedResult(i, null, (String) null, "Occupation recorded with no description"));
                    }
                }
            }
        }

        Collections.sort(result, new IndividualResultSortComparator());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "People who have no occupation facts on file";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "People without occupations";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_INDIVIDUAL_RESULTS;
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.MISSING_DATA, AnalysisTag.INDIVIDUALS };
    }

}
