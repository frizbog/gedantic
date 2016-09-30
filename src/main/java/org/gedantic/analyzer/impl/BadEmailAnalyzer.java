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
import java.util.regex.Pattern;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.AnalysisTag;
import org.gedantic.analyzer.result.FamilyRelatedResult;
import org.gedantic.analyzer.result.IndividualRelatedResult;
import org.gedantic.analyzer.result.RepositoryRelatedResult;
import org.gedantic.analyzer.result.SubmitterRelatedResult;
import org.gedantic.analyzer.result.UnrelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyEvent;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualAttribute;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.Repository;
import org.gedcom4j.model.StringWithCustomFacts;
import org.gedcom4j.model.Submitter;

/**
 * Analyzer that finds bad emails, which could be found on Corporations, FamilyEvents, IndividualAttributes, IndividualEvents,
 * Individuals, Repositories, and Submitters.
 * 
 * @author frizbog
 */
public class BadEmailAnalyzer extends AAnalyzer {

    /**
     * Regex pattern for checking the syntax of an email address
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("(?i)\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b");

    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();

        checkHeaderCorporation(g, result);
        checkIndividuals(g, result);
        checkFamilies(g, result);
        checkRepositories(g, result);
        checkSubmitters(g, result);
        return result;
    }

    @Override
    public String getDescription() {
        return "Find malformed emails throughout the GEDCOM";
    }

    @Override
    public String getName() {
        return "Bad emails";
    }

    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_MIXED_RESULTS;
    }

    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.PROBLEM, AnalysisTag.FAMILIES, AnalysisTag.INDIVIDUALS, AnalysisTag.SOURCES };
    }

    /**
     * Check the family events
     * 
     * @param g
     *            the gedcom
     * @param result
     *            the results we're collecting
     */
    protected void checkFamilies(Gedcom g, List<AResult> result) {
        for (Family f : g.getFamilies().values()) {
            if (f.getEvents() != null) {
                for (FamilyEvent fe : f.getEvents()) {
                    if (fe.getEmails() != null) {
                        for (StringWithCustomFacts e : fe.getEmails()) {
                            if (e.getValue() != null && !EMAIL_PATTERN.matcher(e.getValue()).matches()) {
                                result.add(new FamilyRelatedResult(f, "Email for " + fe.getType().getDisplay() + " event", e
                                        .getValue(), null));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check the corporation in the header
     * 
     * @param g
     *            the gedcom
     * @param result
     *            the results we're collecting
     */
    protected void checkHeaderCorporation(Gedcom g, List<AResult> result) {
        if (g.getHeader().getSourceSystem() != null && g.getHeader().getSourceSystem().getCorporation() != null && g.getHeader()
                .getSourceSystem().getCorporation().getEmails() != null) {
            for (StringWithCustomFacts e : g.getHeader().getSourceSystem().getCorporation().getEmails()) {
                if (e.getValue() != null && !EMAIL_PATTERN.matcher(e.getValue()).matches()) {
                    result.add(new UnrelatedResult("Header - Source System", "Email for Corporation", e.getValue(), null));
                }
            }
        }
    }

    /**
     * Check the individual attributes and events
     * 
     * @param g
     *            the gedcom
     * @param result
     *            the results we're collecting
     */
    protected void checkIndividuals(Gedcom g, List<AResult> result) {
        for (Individual i : g.getIndividuals().values()) {
            if (i.getAttributes() != null) {
                for (IndividualAttribute ia : i.getAttributes()) {
                    if (ia.getEmails() != null) {
                        for (StringWithCustomFacts e : ia.getEmails()) {
                            if (e.getValue() != null && !EMAIL_PATTERN.matcher(e.getValue()).matches()) {
                                result.add(new IndividualRelatedResult(i, "Email for " + ia.getType().getDisplay() + " attribute", e
                                        .getValue(), null));
                            }
                        }
                    }
                }
            }
            if (i.getEvents() != null) {
                for (IndividualEvent ie : i.getEvents()) {
                    if (ie.getEmails() != null) {
                        for (StringWithCustomFacts e : ie.getEmails()) {
                            if (e.getValue() != null && !EMAIL_PATTERN.matcher(e.getValue()).matches()) {
                                result.add(new IndividualRelatedResult(i, "Email for " + ie.getType().getDisplay() + " event", e
                                        .getValue(), null));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check the repositories
     * 
     * @param g
     *            the gedcom
     * @param result
     *            the results we're collecting
     */
    protected void checkRepositories(Gedcom g, List<AResult> result) {
        for (Repository r : g.getRepositories().values()) {
            if (r.getEmails() != null) {
                for (StringWithCustomFacts e : r.getEmails()) {
                    if (e.getValue() != null && !EMAIL_PATTERN.matcher(e.getValue()).matches()) {
                        result.add(new RepositoryRelatedResult(r, "Email for Repository", e.getValue(), null));
                    }
                }
            }
        }
    }

    /**
     * Check the submitters
     * 
     * @param g
     *            the gedcom
     * @param result
     *            the results we're collecting
     */
    protected void checkSubmitters(Gedcom g, List<AResult> result) {
        for (Submitter s : g.getSubmitters().values()) {
            if (s.getEmails() != null) {
                for (StringWithCustomFacts e : s.getEmails()) {
                    if (e.getValue() != null && !EMAIL_PATTERN.matcher(e.getValue()).matches()) {
                        result.add(new SubmitterRelatedResult(s, "Email for Submitter", e.getValue(), null));
                    }
                }
            }
        }
    }
}
