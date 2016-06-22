package org.g_lint.analyzer;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.g_lint.analyzer.impl.ChildrenWithDifferentSurnamesAnalyzer;
import org.g_lint.analyzer.impl.FactsWithoutSourcesAnalyzer;

/**
 * List of available Analyzers.
 * 
 * @author frizbog
 */
public final class AnalyzerList {

    /**
     * The singleton instance
     */
    private static final AnalyzerList INSTANCE = new AnalyzerList();

    /**
     * Get the singleton instance
     * 
     * @return the singleton instance
     */
    public static AnalyzerList getInstance() {
        return INSTANCE;
    }

    /**
     * A map of all available analyzers
     */
    private final Map<String, IAnalyzer> analyzers = new TreeMap<>();

    /**
     * Constructor
     */
    public AnalyzerList() {
        IAnalyzer a;
        a = new FactsWithoutSourcesAnalyzer();
        addAnalyzer(a);
        a = new ChildrenWithDifferentSurnamesAnalyzer();
        addAnalyzer(a);
    }

    /**
     * Get a map of all available analyzers
     * 
     * @return a map of all available analyzers
     */
    public Map<String, IAnalyzer> getAnalyzers() {
        return Collections.unmodifiableMap(analyzers);
    }

    /**
     * Add an analyzer to the Analyzers map
     * 
     * @param a
     *            the analyzer to add
     */
    private void addAnalyzer(IAnalyzer a) {
        analyzers.put(a.getId(), a);
    }

}
