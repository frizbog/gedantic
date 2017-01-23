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
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.PersonalName;
import org.gedcom4j.model.StringWithCustomFacts;

/**
 * @author frizbog
 */
public class PeopleWithoutNamesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalysisResult> analyze(Gedcom g) {

        List<AnalysisResult> result = new ArrayList<>();

        for (Individual i : g.getIndividuals().values()) {
            if (i.getNames() == null || i.getNames().isEmpty()) {
                AnalysisResult r = new AnalysisResult("Individual", i.toString(), null, (String) null, "Individual has no names");
                result.add(r);
            } else {
                for (PersonalName pn : i.getNames()) {
                    boolean componentsUnspecified = notSpecified(pn.getPrefix()) && notSpecified(pn.getGivenName()) && notSpecified(
                            pn.getNickname()) && notSpecified(pn.getSurnamePrefix()) && notSpecified(pn.getSurname())
                            && notSpecified(pn.getSuffix());
                    boolean noNameIndicated = ("//".equals(pn.getBasic()) || pn.getBasic() == null || "".equals(pn.getBasic()
                            .trim()) || "<No /name>/".equals(pn.getBasic()));
                    if (noNameIndicated && componentsUnspecified) {
                        AnalysisResult r = new AnalysisResult("Individual", i.toString(), null, (String) null,
                                "One of this individual's names is blank");
                        result.add(r);
                    }
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
        return "People who have no names (or only blank ones)";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "People without names";
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.MISSING_DATA, AnalysisTag.INDIVIDUALS };
    }

    /**
     * Return true if the supplied string problematicValue is null or empty when trimmed
     * 
     * @param s
     *            teh string
     * @return true if the supplied string problematicValue is null or empty when trimmed
     */
    private boolean notSpecified(StringWithCustomFacts s) {
        if (s == null || s.getValue() == null || s.getValue().trim().isEmpty()) {
            return true;
        }
        return false;
    }

}
