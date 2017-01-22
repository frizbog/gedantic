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

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AnalysisResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.comparator.IndividualResultSortComparator;
import org.gedantic.analyzer.result.IndividualRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.enumerations.IndividualEventType;
import org.gedcom4j.parser.DateParser;

/**
 * Analyzer that finds birth and death dates in the future
 * 
 * @author frizbog
 */
public class FutureBirthDeathDatesAnalyzer extends AAnalyzer {

    /**
     * Date parser
     */
    private static final DateParser DP = new DateParser();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        Date now = new Date();

        List<AnalysisResult> result = new ArrayList<>();

        for (Individual i : g.getIndividuals().values()) {
            List<IndividualEvent> births = i.getEventsOfType(IndividualEventType.BIRTH);
            for (IndividualEvent b : births) {
                if (b.getDate() != null && b.getDate().getValue() != null && !b.getDate().getValue().isEmpty()) {
                    String dateString = b.getDate().getValue();
                    Date bd = DP.parse(dateString);
                    if (bd != null && now.before(bd)) {
                        result.add(new IndividualRelatedResult(i, IndividualEventType.BIRTH.getDisplay(), dateString, null));
                    }
                }
            }
            List<IndividualEvent> deaths = i.getEventsOfType(IndividualEventType.DEATH);
            for (IndividualEvent d : deaths) {
                if (d.getDate() != null && d.getDate().getValue() != null && !d.getDate().getValue().isEmpty()) {
                    String dateString = d.getDate().getValue();
                    Date dd = DP.parse(dateString);
                    if (dd != null && now.before(dd)) {
                        result.add(new IndividualRelatedResult(i, IndividualEventType.DEATH.getDisplay(), dateString, null));
                    }
                }
            }
        }

        Collections.sort(result, new IndividualResultSortComparator());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "People with birth or death dates in the future";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Future births and deaths";
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
        return new AnalysisTag[] { AnalysisTag.PROBLEM, AnalysisTag.INDIVIDUALS };
    }

}
