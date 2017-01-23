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
import org.gedcom4j.model.AbstractEvent;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyEvent;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.PersonalName;

/**
 * Analyzer that finds facts without any source citations. Currently limited to names and events.
 * 
 * @author frizbog
 */
public class FactsWithoutSourcesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        List<AnalysisResult> result = new ArrayList<>();
        for (Individual i : g.getIndividuals().values()) {
            for (PersonalName n : i.getNames()) {
                if (n.getCitations().isEmpty()) {
                    result.add(new AnalysisResult("Individual", i.getFormattedName(), "Name", n.toString(), "No source citations"));
                }
            }
            for (IndividualEvent e : i.getEvents()) {
                if (e.getCitations().isEmpty()) {
                    result.add(new AnalysisResult("Individial", i.getFormattedName(), e.getType().getDisplay(),
                            getEventShortDescription(e), "No source citations"));
                }
            }
        }
        for (Family f : g.getFamilies().values()) {
            for (FamilyEvent e : f.getEvents()) {
                if (e.getCitations().isEmpty()) {
                    result.add(new AnalysisResult("Family", getFamilyDescriptor(f), e.getType().getDisplay(),
                            getEventShortDescription(e), "No source citations"));
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
        return "Facts without source citations. Currently limited to names and events.";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Facts without sources";
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.MISSING_DATA, AnalysisTag.FAMILIES, AnalysisTag.INDIVIDUALS, AnalysisTag.SOURCES };
    }

    /**
     * Get a short description of an event, consisting of date and place name.
     * 
     * @param e
     *            the event
     * @return a short description of an event, consisting of date and place name.
     */
    private String getEventShortDescription(AbstractEvent e) {
        StringBuilder sb = new StringBuilder("Date: ");
        if (e.getDate() != null) {
            sb.append(e.getDate());
        } else {
            sb.append("(no problematicValue)");
        }
        sb.append(", Place: ");
        if (e.getPlace() == null || e.getPlace().getPlaceName() == null || e.getPlace().getPlaceName().trim().isEmpty()) {
            sb.append("(no problematicValue)");
        } else {
            sb.append(e.getPlace().getPlaceName());
        }
        return sb.toString();
    }

}
