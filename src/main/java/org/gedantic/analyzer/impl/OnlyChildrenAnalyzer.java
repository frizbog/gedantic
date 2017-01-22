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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AnalysisResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.web.Constants;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualReference;

/**
 * @author frizbog
 */
public class OnlyChildrenAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        List<AnalysisResult> result = new ArrayList<>();

        for (Individual i : g.getIndividuals().values()) {
            if (i.getFamiliesWhereChild() == null || i.getFamiliesWhereChild().isEmpty()) {
                continue;
            }
            Set<Individual> kids = new HashSet<>();
            for (FamilyChild fc : i.getFamiliesWhereChild()) {
                for (IndividualReference ir : fc.getFamily().getChildren()) {
                    kids.add(ir.getIndividual());
                }
            }
            if (kids.size() == 1) {
                for (FamilyChild fc : i.getFamiliesWhereChild()) {
                    StringBuilder parents = new StringBuilder("Child of ");
                    if (fc.getFamily().getWife() != null) {
                        parents.append(fc.getFamily().getWife().getIndividual().getFormattedName());
                        parents.append(" and ");
                    }
                    if (fc.getFamily().getHusband() == null) {
                        parents.append("unknown");
                    } else {
                        parents.append(fc.getFamily().getHusband().getIndividual().getFormattedName());
                    }
                    result.add(new AnalysisResult("Individual", i.getFormattedName(), null, null, "Only child of " + parents
                            .toString()));
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
        return "People who have no siblings";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Only children";
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
        return new AnalysisTag[] { AnalysisTag.INDIVIDUALS };
    }

}
