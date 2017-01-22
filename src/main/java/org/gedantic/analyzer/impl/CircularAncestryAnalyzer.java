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
import org.gedantic.analyzer.result.RelationshipRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.relationship.Relationship;
import org.gedcom4j.relationship.RelationshipCalculator;
import org.gedcom4j.relationship.SimpleRelationship;

/**
 * An analyzer to find people who are ancestors of themselves due to illegal cyclical relationshops
 * 
 * @author frizbog
 */
public class CircularAncestryAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalysisResult> analyze(Gedcom g) {
        List<AnalysisResult> result = new ArrayList<>();
        for (Individual i : g.getIndividuals().values()) {
            List<Individual> ancestors = new ArrayList<>(i.getAncestors());
            if (ancestors.contains(i)) {
                List<List<SimpleRelationship>> path = getCycle(i);
                result.add(new RelationshipRelatedResult(i, null, path, null));
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "People who are recorded as ancestors of themselves due to cyclical relationship errors";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Cyclical Ancestral Relationships";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_RELATIONSHIP_RESULTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnalysisTag[] getTags() {
        return new AnalysisTag[] { AnalysisTag.PROBLEM, AnalysisTag.INDIVIDUALS, AnalysisTag.FAMILIES };
    }

    /**
     * Gets the cyclical ancestry relationship(s) for this individual
     *
     * @param i
     *            the individual we are finding the relationship cycle(s) for
     * @return the cyclical ancestry relationship(s) for this individual
     */
    private List<List<SimpleRelationship>> getCycle(Individual i) {
        List<List<SimpleRelationship>> result = new ArrayList<>();
        RelationshipCalculator rc = new RelationshipCalculator();
        for (FamilyChild fc : i.getFamiliesWhereChild()) {
            Family f = fc.getFamily();
            Individual father = f.getHusband() == null ? null : f.getHusband().getIndividual();
            Individual mother = f.getWife() == null ? null : f.getWife().getIndividual();
            if (father != null && father.getAncestors().contains(i)) {
                rc.calculateRelationships(father, i, false);
                for (Relationship r : rc.getRelationshipsFound()) {
                    List<SimpleRelationship> cycle = new ArrayList<>();
                    SimpleRelationship sr = new SimpleRelationship();
                    sr.setIndividual1(i);
                    sr.setIndividual2(father);
                    cycle.add(sr);
                    cycle.addAll(r.getChain());
                    result.add(cycle);
                }
            }
            if (mother != null && mother.getAncestors().contains(i)) {
                rc.calculateRelationships(mother, i, false);
                for (Relationship r : rc.getRelationshipsFound()) {
                    List<SimpleRelationship> cycle = new ArrayList<>();
                    SimpleRelationship sr = new SimpleRelationship();
                    sr.setIndividual1(i);
                    sr.setIndividual2(mother);
                    cycle.add(sr);
                    cycle.addAll(r.getChain());
                    result.add(cycle);
                }
            }
        }
        return result;
    }

}
