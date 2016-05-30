package org.g_lint.analyzer.impl;

import java.util.List;

import org.g_lint.analyzer.AResult;
import org.g_lint.analyzer.IAnalyzer;
import org.gedcom4j.model.Gedcom;

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
        throw new UnsupportedOperationException("This analysis has not been implemented yet");
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
