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
import org.gedantic.analyzer.comparator.MixedResultSortComparator;
import org.gedantic.analyzer.result.FamilyRelatedResult;
import org.gedantic.analyzer.result.IndividualRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.*;

/**
 * Analyzer that finds Events with dates, but no places
 * 
 * @author frizbog
 */
public class DatesButNoPlacesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();
        for (Individual i : g.getIndividuals().values()) {
            for (IndividualEvent e : i.getEvents()) {
                boolean hasDate = e.getDate() != null && e.getDate().getValue() != null;
                boolean hasPlace = e.getPlace() != null;
                if (hasDate & !hasPlace) {
                    result.add(new IndividualRelatedResult(i, e.getType().getDisplay(), e.getDate().getValue(), null));
                }
            }
        }
        for (Family f : g.getFamilies().values()) {
            for (FamilyEvent e : f.getEvents()) {
                boolean hasDate = e.getDate() != null && e.getDate().getValue() != null;
                boolean hasPlace = e.getPlace() != null;
                if (hasDate & !hasPlace) {
                    result.add(new FamilyRelatedResult(f, e.getType().getDisplay(), e.getDate().getValue(), null));
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
        return "Events that have dates, but no location information";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Dates but no places";
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
        return new AnalysisTag[] { AnalysisTag.MISSING_DATA, AnalysisTag.FAMILIES, AnalysisTag.INDIVIDUALS };
    }

}
