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
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyEvent;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.enumerations.FamilyEventType;
import org.gedcom4j.model.enumerations.IndividualEventType;

/**
 * Analyzer that finds Individual and Family events where neither a place nor a date is specified
 * 
 * @author frizbog
 */
public class EventsWithoutPlacesOrDatesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        List<AnalysisResult> result = new ArrayList<>();
        for (Individual i : g.getIndividuals().values()) {
            for (IndividualEvent e : i.getEvents()) {
                if (e.getDate() != null && e.getDate().getValue() != null && !e.getDate().getValue().trim().isEmpty()) {
                    continue;
                }
                if (e.getPlace() == null) {
                    result.add(new AnalysisResult("Individual", i.getFormattedName(), getFactTypeWithDescription(e), null,
                            "No place or date information"));
                }
            }
        }
        for (Family f : g.getFamilies().values()) {
            for (FamilyEvent e : f.getEvents()) {
                if (e.getDate() != null && e.getDate().getValue() != null && !e.getDate().getValue().trim().isEmpty()) {
                    continue;
                }
                if (e.getPlace() == null) {
                    result.add(new AnalysisResult("Family", getFamilyDescriptor(f), getFactTypeWithDescription(e), null,
                            "No place or date information"));
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
        return "Events that are recorded but have no place or date specified";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Events without places or dates";
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
        String eventType;
        if (FamilyEventType.EVENT == e.getType()) {
            eventType = "Custom event: " + e.getSubType();
        } else {
            eventType = e.getType().getDisplay();
        }
        return eventType;
    }

    /**
     * Get the fact type, with description if applicable
     * 
     * @param e
     *            the event
     * @return the fact type, with description if applicable
     */
    private String getFactTypeWithDescription(IndividualEvent e) {
        String eventType;
        if (IndividualEventType.EVENT == e.getType()) {
            eventType = "Custom event: " + e.getSubType();
        } else {
            eventType = e.getType().getDisplay();
        }
        return eventType;
    }

}
