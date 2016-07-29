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
 * Analyzer that finds Individual and Family events where dates are not specified
 * 
 * @author frizbog
 */
public class EventsWithoutDatesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();
        for (Individual i : g.getIndividuals().values()) {
            for (IndividualEvent e : i.getEvents()) {
                if (e.getDate() == null || e.getDate().getValue() == null || e.getDate().getValue().trim().isEmpty()) {
                    result.add(new IndividualRelatedResult(i, getFactTypeWithDescription(e), null, null));
                }
            }
        }
        for (Family f : g.getFamilies().values()) {
            for (FamilyEvent e : f.getEvents()) {
                if (e.getDate() == null || e.getDate().getValue() == null || e.getDate().getValue().trim().isEmpty()) {
                    result.add(new FamilyRelatedResult(f, getFactTypeWithDescription(e), null, null));
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
        return "Events that are recorded but have no date specified";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Events without dates";
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

    /**
     * Get the fact type, with description if applicable
     * 
     * @param e
     *            the event
     * @return the fact type, with description if applicable
     */
    private String getFactTypeWithDescription(FamilyEvent e) {
        String pn = "";
        if (e.getPlace() != null && e.getPlace().getPlaceName() != null) {
            pn = " (at " + e.getPlace().getPlaceName() + ")";
        }
        String eventType;
        if (FamilyEventType.EVENT == e.getType()) {
            eventType = "Custom event: " + e.getSubType();
        } else {
            eventType = e.getType().getDisplay();
        }
        String factType = eventType + pn;
        return factType;
    }

    /**
     * Get the fact type, with description if applicable
     * 
     * @param e
     *            the event
     * @return the fact type, with description if applicable
     */
    private String getFactTypeWithDescription(IndividualEvent e) {
        String pn = "";
        if (e.getPlace() != null && e.getPlace().getPlaceName() != null) {
            pn = " (at " + e.getPlace().getPlaceName() + ")";
        }
        String eventType;
        if (IndividualEventType.EVENT == e.getType()) {
            eventType = "Custom event: " + e.getSubType();
        } else {
            eventType = e.getType().getDisplay();
        }
        String factType = eventType + pn;
        return factType;
    }

}
