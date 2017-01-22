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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.comparator.MixedResultSortComparator;
import org.gedantic.analyzer.result.FamilyRelatedResult;
import org.gedantic.analyzer.result.IndividualRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.AbstractEvent;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyEvent;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.enumerations.FamilyEventType;
import org.gedcom4j.model.enumerations.IndividualEventType;
import org.gedcom4j.parser.DateParser;
import org.gedcom4j.parser.DateParser.ImpreciseDatePreference;

/**
 * Analyzer that finds Individual and Family events where dates are not specified
 * 
 * @author frizbog
 */
public class ConflictingDatesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();

        checkIndividualEvents(g, result);
        checkFamilyEvents(g, result);

        Collections.sort(result, new MixedResultSortComparator());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Events that have conflicting, non-overlapping dates";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Conflicting dates";
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
        return new AnalysisTag[] { AnalysisTag.PROBLEM, AnalysisTag.FAMILIES, AnalysisTag.INDIVIDUALS };
    }

    /**
     * Check the family events
     * 
     * @param gedcom
     *            the gedcom
     * @param result
     *            the results
     */
    private void checkFamilyEvents(Gedcom gedcom, List<AResult> result) {
        DateParser dp = new DateParser();
        for (Family i : gedcom.getFamilies().values()) {
            Map<FamilyEventType, List<AbstractEvent>> indEventsByType = new TreeMap<>();
            for (FamilyEvent e : i.getEvents()) {
                List<AbstractEvent> eventsOfThisType = indEventsByType.get(e.getType());
                if (eventsOfThisType == null) {
                    eventsOfThisType = new ArrayList<>();
                    indEventsByType.put(e.getType(), eventsOfThisType);
                }
                eventsOfThisType.add(e);
            }
            for (Entry<FamilyEventType, List<AbstractEvent>> entry : indEventsByType.entrySet()) {
                FamilyEventType type = entry.getKey();
                List<AbstractEvent> eventsOfThisType = entry.getValue();
                if (eventsOfThisType.size() < 2) {
                    continue;
                }
                for (int k = 1; k < eventsOfThisType.size(); k++) {
                    AbstractEvent outerEvent = eventsOfThisType.get(k);
                    String outerDateString = (outerEvent.getDate() == null ? null : outerEvent.getDate().getValue());
                    if (outerDateString == null) {
                        continue;
                    }
                    Date outerDateEarliest = dp.parse(outerDateString, ImpreciseDatePreference.FAVOR_EARLIEST);
                    Date outerDateLatest = dp.parse(outerDateString, ImpreciseDatePreference.FAVOR_LATEST);
                    if (outerDateEarliest == null || outerDateLatest == null) {
                        continue;
                    }
                    for (int j = 0; j < k; j++) {
                        AbstractEvent innerEvent = eventsOfThisType.get(j);
                        String innerDateString = (innerEvent.getDate() == null ? null : innerEvent.getDate().getValue());
                        if (innerDateString == null) {
                            continue;
                        }
                        Date innerDateEarliest = dp.parse(innerDateString, ImpreciseDatePreference.FAVOR_EARLIEST);
                        Date innerDateLatest = dp.parse(innerDateString, ImpreciseDatePreference.FAVOR_LATEST);
                        if (innerDateEarliest == null || innerDateLatest == null) {
                            continue;
                        }

                        // Ok, enough getting to the data - we can finally compare
                        if (innerDateEarliest.after(outerDateLatest) || innerDateLatest.before(outerDateEarliest)) {
                            // Not overlapping so we have a conflict
                            result.add(new FamilyRelatedResult(i, type.getDisplay(), outerDateString + " and " + innerDateString,
                                    null));
                        }
                    }
                }

            }
        }
    }

    /**
     * Check individual events for conflicts
     * 
     * @param gedcom
     *            the gedcom
     * @param result
     *            the results
     */
    private void checkIndividualEvents(Gedcom gedcom, List<AResult> result) {
        DateParser dp = new DateParser();
        for (Individual i : gedcom.getIndividuals().values()) {
            Map<IndividualEventType, List<AbstractEvent>> indEventsByType = new TreeMap<>();
            for (IndividualEvent e : i.getEvents()) {
                List<AbstractEvent> eventsOfThisType = indEventsByType.get(e.getType());
                if (eventsOfThisType == null) {
                    eventsOfThisType = new ArrayList<>();
                    indEventsByType.put(e.getType(), eventsOfThisType);
                }
                eventsOfThisType.add(e);
            }
            for (Entry<IndividualEventType, List<AbstractEvent>> entry : indEventsByType.entrySet()) {
                IndividualEventType type = entry.getKey();
                List<AbstractEvent> eventsOfThisType = entry.getValue();
                if (eventsOfThisType.size() < 2) {
                    continue;
                }
                for (int k = 1; k < eventsOfThisType.size(); k++) {
                    AbstractEvent outerEvent = eventsOfThisType.get(k);
                    String outerDateString = (outerEvent.getDate() == null ? null : outerEvent.getDate().getValue());
                    if (outerDateString == null) {
                        continue;
                    }
                    Date outerDateEarliest = dp.parse(outerDateString, ImpreciseDatePreference.FAVOR_EARLIEST);
                    Date outerDateLatest = dp.parse(outerDateString, ImpreciseDatePreference.FAVOR_LATEST);
                    if (outerDateEarliest == null || outerDateLatest == null) {
                        continue;
                    }
                    for (int j = 0; j < k; j++) {
                        AbstractEvent innerEvent = eventsOfThisType.get(j);
                        String innerDateString = (innerEvent.getDate() == null ? null : innerEvent.getDate().getValue());
                        if (innerDateString == null) {
                            continue;
                        }
                        Date innerDateEarliest = dp.parse(innerDateString, ImpreciseDatePreference.FAVOR_EARLIEST);
                        Date innerDateLatest = dp.parse(innerDateString, ImpreciseDatePreference.FAVOR_LATEST);
                        if (innerDateEarliest == null || innerDateLatest == null) {
                            continue;
                        }

                        // Ok, enough getting to the data - we can finally compare
                        if (innerDateEarliest.after(outerDateLatest) || innerDateLatest.before(outerDateEarliest)) {
                            // Not overlapping so we have a conflict
                            result.add(new IndividualRelatedResult(i, type.getDisplay(), outerDateString + " and "
                                    + innerDateString, null));
                        }
                    }
                }

            }
        }
    }
}
