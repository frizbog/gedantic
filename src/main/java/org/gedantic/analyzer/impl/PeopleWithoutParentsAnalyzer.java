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
import org.gedantic.analyzer.AnalysisResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.comparator.IndividualResultSortComparator;
import org.gedantic.analyzer.result.IndividualRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;

/**
 * @author frizbog
 */
public class PeopleWithoutParentsAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        List<AnalysisResult> result = new ArrayList<>();

        for (Individual i : g.getIndividuals().values()) {
            if (i.getFamiliesWhereChild() == null || i.getFamiliesWhereChild().isEmpty()) {
                result.add(new IndividualRelatedResult(i, null, (String) null, null));
            } else {
                boolean foundParent = false;
                for (FamilyChild fc : i.getFamiliesWhereChild()) {
                    foundParent = fc.getFamily().getWife() != null || fc.getFamily().getHusband() != null;
                }
                if (!foundParent) {
                    result.add(new IndividualRelatedResult(i, null, (String) null,
                            "Child of at least one family record, but no family with designated parents"));
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
        return "People who have no parents recorded";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "People without parents";
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
        return new AnalysisTag[] { AnalysisTag.MISSING_DATA, AnalysisTag.FAMILIES, AnalysisTag.INDIVIDUALS };
    }

}
