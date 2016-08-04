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
import org.gedantic.analyzer.result.FamilyRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;

/**
 * An analyzer that finds couples with common ancestors (cousins marrying, etc)
 * 
 * @author frizbog
 */
public class MaleWivesFemaleHusbandsAnalyzer extends AAnalyzer {

    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();
        for (Family f : g.getFamilies().values()) {
            boolean wifeMale = false;
            boolean husbandFemale = false;
            Individual w = f.getWife();
            Individual h = f.getHusband();
            if (w != null && w.getSex() != null && "M".equals(w.getSex().getValue())) {
                wifeMale = true;
            }
            if (h != null && h.getSex() != null && "F".equals(h.getSex().getValue())) {
                husbandFemale = true;
            }
            if (husbandFemale && wifeMale) {
                result.add(new FamilyRelatedResult(f, null, (String) null, "Husband and wife positions are transposed"));
            } else if (wifeMale) {
                result.add(new FamilyRelatedResult(f, null, (String) null, "Person in wife position " + (w == null ? ""
                        : "(" + w.getFormattedName() + ") ") + "is male"));
            } else if (husbandFemale) {
                result.add(new FamilyRelatedResult(f, null, (String) null, "Person in husband position " + (h == null ? ""
                        : "(" + h.getFormattedName() + ") ") + "is female"));
            }
        }
        return result;
    }

    @Override
    public String getDescription() {
        return "Couples where the wife is listed as a male or the husband is listed as a female - likely a transposition error.";
    }

    @Override
    public String getName() {
        return "Male Wives and Female Husbands";
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
