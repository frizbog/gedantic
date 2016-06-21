package org.g_lint.analyzer;

import java.util.List;

import org.gedcom4j.model.Gedcom;

/**
 * Base class for all analyzers
 * 
 * @author frizbog
 */
public interface IAnalyzer {

    /**
     * Analyze a {@link Gedcom}, and return some results
     * 
     * @param g
     *            the {@link Gedcom} to analyze
     * @return a {@link List} of results
     */
    public List<AResult> analyze(Gedcom g);

    /**
     * Get a description for this analyzer.
     * 
     * @return the description of this analyzer.
     */
    public String getDescription();

    /**
     * Get the ID (i.e., className) for this analyzer
     * 
     * @return the name of this analyzer
     */
    public String getId();

    /**
     * Get the short name for this analyzer
     * 
     * @return the name of this analyzer
     */
    public String getName();

}
