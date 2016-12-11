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
import org.gedcom4j.model.PersonalName;

/**
 * Analysis to find people with only a surname (no given name)
 * 
 * @author frizbog
 */
public class PeopleWithOnlySurnamesAnalyzer extends AAnalyzer {

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
            boolean hadSurname = false;
            boolean somethingOtherThanSurname = false;
            for (PersonalName pn : i.getNames()) {
                if (isSpecified(pn.getSurname())) {
                    // Don't count empty surnames
                    hadSurname = true;
                }

                if (pn.getBasic() != null) {

                    if (pn.getBasic().contains("/") && !pn.getBasic().contains("//")) {
                        hadSurname = true;
                    }

                    // Characters before a slash would be something other than a surname
                    int firstSlash = pn.getBasic().indexOf('/');
                    if (firstSlash > 0) {
                        somethingOtherThanSurname = true;
                        break; // Don't need to check any more names
                    }
                }

                // Check the name components too
                if (isSpecified(pn.getGivenName()) || isSpecified(pn.getNickname())) {
                    somethingOtherThanSurname = true;
                    break; // Don't need to check any more names
                }
            }
            if (somethingOtherThanSurname || !hadSurname) {
                // Next person
                continue;
            }
            result.add(new IndividualRelatedResult(i, null, (String) null, null));
        }

        Collections.sort(result, new IndividualResultSortComparator());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "People who have no given (first) names or nicknames, just surnames";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "People with only surnames";
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
