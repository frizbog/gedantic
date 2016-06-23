package org.gedantic.analyzer.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.IndividualRelatedResult;
import org.gedantic.analyzer.comparator.IndividualResultSortComparator;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.PersonalName;

/**
 * Analyzer that finds facts without any source citations
 * 
 * @author frizbog
 */
public class FactsWithoutSourcesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();
        for (Individual i : g.individuals.values()) {
            for (PersonalName n : i.names) {
                if (n.citations.isEmpty()) {
                    result.add(new IndividualRelatedResult(i, "Name", n.toString(), null));
                }
            }
            for (IndividualEvent e : i.events) {
                if (e.citations.isEmpty()) {
                    result.add(new IndividualRelatedResult(i, e.type.display, e.toString(), null));
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
        return "Facts (events, names, etc.) without source citations";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Facts without sources";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_INDIVIDUAL_RESULTS;
    }

}
