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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.comparator.MixedResultSortComparator;
import org.gedantic.analyzer.result.FamilyRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualReference;
import org.gedcom4j.model.PersonalName;

/**
 * @author frizbog
 */
public class ChildrenWithSameFirstNamesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {

        List<AResult> result = new ArrayList<>();

        for (Family f : g.getFamilies().values()) {
            if (f.getChildren() == null) {
                continue;
            }

            // Build a map of each first name and all the kids who have that first name
            Map<String, Set<Individual>> kidsByFirstName = new HashMap<>();
            for (IndividualReference iRef : f.getChildren()) {
                Individual kid = iRef.getIndividual();
                for (PersonalName pn : kid.getNames(true)) {
                    String gn = null;
                    if (pn.getGivenName() != null && isSpecified(pn.getGivenName().getValue())) {
                        gn = pn.getGivenName().getValue();
                    } else if (isSpecified(pn.getBasic())) {
                        gn = pn.getBasic().trim();
                        int firstSlash = gn.indexOf('/');
                        if (firstSlash > 0) {
                            gn = gn.substring(0, firstSlash).trim();
                        } else {
                            gn = null;
                        }
                    }
                    if (isSpecified(gn)) {
                        Set<Individual> kidsWithThisFirstName = kidsByFirstName.get(gn);
                        if (kidsWithThisFirstName == null) {
                            kidsWithThisFirstName = new HashSet<>();
                            kidsByFirstName.put(gn, kidsWithThisFirstName);
                        }
                        kidsWithThisFirstName.add(kid);
                    }
                }
            }

            // Check the map for any names with more than one kid in the family who has it
            for (Entry<String, Set<Individual>> e : kidsByFirstName.entrySet()) {
                if (e.getValue().size() > 1 && isSpecified(e.getKey())) {
                    result.add(new FamilyRelatedResult(f, null, e.getValue(), null));
                }
            }
        }

        Collections.sort(result, new MixedResultSortComparator());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Children in the same family with the same first name(s) as a sibling";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Children with same first names";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_MIXED_RESULTS;
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.PROBLEM, AnalysisTag.FAMILIES };
    }

}