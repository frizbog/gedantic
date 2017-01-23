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

/**
 * An analyzer that finds couples with common ancestors (cousins marrying, etc)
 * 
 * @author frizbog
 */
public class CouplesWithSameBirthSurnames extends AAnalyzer {

    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        List<AnalysisResult> result = new ArrayList<>();
        for (Family f : g.getFamilies().values()) {
            if (f.getWife() != null && f.getHusband() != null) {

                Set<String> wifeSurnames = getSurnamesFromIndividual(f.getWife().getIndividual());
                Set<String> husbandSurnames = getSurnamesFromIndividual(f.getHusband().getIndividual());
                if (wifeSurnames.isEmpty() || husbandSurnames.isEmpty()) {
                    continue;
                }

                if (wifeSurnames.containsAll(husbandSurnames) || husbandSurnames.containsAll(wifeSurnames)) {
                    wifeSurnames.addAll(husbandSurnames);
                    result.add(new AnalysisResult("Family", getFamilyDescriptor(f), "Name", wifeSurnames.toString(),
                            "Same birth surnames"));
                }

            }
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "Couples with same surnames at time of birth (probable error)";
    }

    @Override
    public String getName() {
        return "Couples with same birth surnames";
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.PROBLEM, AnalysisTag.FAMILIES };
    }

}
