package org.g_lint.analyzer.impl;

import java.util.ArrayList;
import java.util.List;

import org.g_lint.analyzer.AResult;
import org.g_lint.analyzer.IAnalyzer;
import org.g_lint.analyzer.IndividualRelatedResult;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.PersonalName;

/**
 * @author frizbog
 *
 */
public class FactsWithoutSourcesAnalyzer implements IAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();
        for (Individual i : g.individuals.values()) {
            for (PersonalName n : i.names) {
                if (n.citations.isEmpty()) {
                    result.add(new IndividualRelatedResult(i, "Name", n.toString(), "No source citations"));
                }
            }
            for (IndividualEvent e : i.events) {
                if (e.citations.isEmpty()) {
                    result.add(new IndividualRelatedResult(i, e.type.display, e.toString(), "No source citations"));
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
        return "Find facts (events, names, etc.) without source citations.";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Facts without sources";
    }

}
