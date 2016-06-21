package org.g_lint.analyzer;

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
     * A map of all available analyzers
     */
    private static final Map<String, IAnalyzer> ANALYZERS = new TreeMap<>();

    /**
     * Get a map of all available analyzers
     * 
     * @return a map of all available analyzers
     */
    public static Map<String, IAnalyzer> getAnalyzers() {
        return ANALYZERS;
    }

    /**
     * Get the singleton instance
     * 
     * @return the singleton instance
     */
    public static AnalyzerList getInstance() {
        return INSTANCE;
    }

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
     * Add an analyzer to the Analyzers map
     * 
     * @param a
     *            the analyzer to add
     */
    private void addAnalyzer(IAnalyzer a) {
        getAnalyzers().put(a.getName(), a);
    }

}
