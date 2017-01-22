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
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.comparator.IndividualResultSortComparator;
import org.gedantic.analyzer.result.IndividualRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.enumerations.IndividualEventType;
import org.gedcom4j.parser.DateParser;
import org.gedcom4j.parser.DateParser.ImpreciseDatePreference;

/**
 * Analysis - find people who do not have death events, particularly those for people who have birth events more than 80 years ago
 * 
 * @author frizbog
 */
public class PeopleWhoLivedPast100Analyzer extends AAnalyzer {

    /**
     * Date parser
     */
    private static final DateParser DP = new DateParser();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {

        List<AResult> result = new ArrayList<>();

        for (Individual i : g.getIndividuals().values()) {
            List<IndividualEvent> deaths = i.getEventsOfType(IndividualEventType.DEATH);
            if (deaths.isEmpty()) {
                continue;
            }

            List<IndividualEvent> births = i.getEventsOfType(IndividualEventType.BIRTH);
            if (births.isEmpty()) {
                continue;
            }
            Date earliestBirthDate = null;
            String earliestBirthDateString = null;
            for (IndividualEvent b : births) {
                if (b.getDate() != null && b.getDate().getValue() != null) {
                    Date bd = DP.parse(b.getDate().getValue(), ImpreciseDatePreference.FAVOR_EARLIEST);
                    if (bd != null && (earliestBirthDate == null || bd.before(earliestBirthDate))) {
                        earliestBirthDate = bd;
                        earliestBirthDateString = b.getDate().getValue();
                    }
                }
            }
            if (earliestBirthDate == null) {
                continue;
            }

            Date latestDeathDate = null;
            String latestDeathDateString = null;
            for (IndividualEvent d : deaths) {
                if (d.getDate() != null && d.getDate().getValue() != null) {
                    Date dd = DP.parse(d.getDate().getValue(), ImpreciseDatePreference.FAVOR_LATEST);
                    if (dd != null && (latestDeathDate == null || dd.after(latestDeathDate))) {
                        latestDeathDate = dd;
                        latestDeathDateString = d.getDate().getValue();
                    }
                }
            }
            if (latestDeathDate == null) {
                continue;
            }

            long difference = latestDeathDate.getTime() - earliestBirthDate.getTime();
            long yearsOld = difference / (365L * 24 * 60 * 60 * 1000); // approximate
            if (yearsOld >= 130) {
                result.add(new IndividualRelatedResult(i, null, (String) null, "Lived to about " + yearsOld + " (b."
                        + earliestBirthDateString + ", d." + latestDeathDateString + ").  Probably incorrect dates."));
            } else if (yearsOld >= 100) {
                result.add(new IndividualRelatedResult(i, null, (String) null, "Lived to about " + yearsOld + " (b."
                        + earliestBirthDateString + ", d." + latestDeathDateString + ") - should verify."));
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
        return "People who have lived to at least 100 years in age";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "People who lived to 100";
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
        return new AnalysisTag[] { AnalysisTag.INDIVIDUALS };
    }

}
