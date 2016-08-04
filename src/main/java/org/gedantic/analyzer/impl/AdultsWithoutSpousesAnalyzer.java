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
import org.gedantic.analyzer.result.DateAndString;
import org.gedantic.analyzer.result.IndividualRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.parser.DateParser.ImpreciseDatePreference;

/**
 * Analyzer that finds people who lived to at least 18 and for which we do not have a specific spouse recorded
 * 
 * @author frizbog
 */
public class AdultsWithoutSpousesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {

        List<AResult> result = new ArrayList<>();

        for (Individual i : g.getIndividuals().values()) {
            boolean foundSpouse = false;
            if (i.getFamiliesWhereSpouse() != null) {
                // Having FAMS records is not proof of actually having a spouse - there could have just been a note saying "did not
                // marry" or something like that
                for (FamilySpouse fs : i.getFamiliesWhereSpouse()) {
                    Family f = fs.getFamily();
                    Individual w = f.getWife();
                    Individual h = f.getHusband();
                    boolean someoneElseIsTheWife = w != null && !i.equals(w);
                    boolean someoneElseIsTheHusband = h != null && !i.equals(h);
                    if (someoneElseIsTheWife || someoneElseIsTheHusband) {
                        foundSpouse = true;
                        break;
                    }
                }
            }
            if (foundSpouse) {
                continue;
            }

            DateAndString birthDate = getBirthDate(i, ImpreciseDatePreference.FAVOR_EARLIEST);
            if (birthDate == null || birthDate.getDate() == null) {
                // can't tell how old they are/were without a parseable birth date
                continue;
            }

            /*
             * Make sure they survived into adulthood by comparing their birth date to their latest death date (if they have one) or
             * the current date
             */
            DateAndString deathDate = getDeathDate(i, ImpreciseDatePreference.FAVOR_LATEST);
            Date endDate = (deathDate == null || deathDate.getDate() == null ? new Date() : deathDate.getDate());

            long difference = endDate.getTime() - birthDate.getDate().getTime();
            long yearsOld = difference / (365L * 24 * 60 * 60 * 1000); // approximate
            if (yearsOld >= 18) {
                result.add(new IndividualRelatedResult(i, null, (String) null, (deathDate == null ? "Born " + yearsOld
                        + " years ago with no death date available" : "Lived to " + yearsOld) + ", but no spouses"));
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
        return "Adults (lived to at least 18) who have no spouse recorded";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Adults without spouses";
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
