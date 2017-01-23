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
import java.util.Set;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AnalysisResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.relationship.AncestryCalculator;

/**
 * An analyzer that finds couples with common ancestors (cousins marrying, etc)
 * 
 * @author frizbog
 */
public class CouplesWithCommonAncestorsAnalyzer extends AAnalyzer {

    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        AncestryCalculator ac = new AncestryCalculator();
        List<AnalysisResult> result = new ArrayList<>();
        for (Family f : g.getFamilies().values()) {
            if (f.getWife() != null && f.getHusband() != null) {

                Set<Individual> lowestCommonAncestors = ac.getLowestCommonAncestors(f.getWife().getIndividual(), f.getHusband()
                        .getIndividual());

                for (Individual commonAncestor : lowestCommonAncestors) {
                    result.add(new AnalysisResult("Family", getFamilyDescriptor(f), null, commonAncestor.getFormattedName(), null));
                }

            }
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "Couples that share a common ancestor (such as cousins marrying)";
    }

    @Override
    public String getName() {
        return "Couples with common ancestors";
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.FAMILIES };
    }

}
