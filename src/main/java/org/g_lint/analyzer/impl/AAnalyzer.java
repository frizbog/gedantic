package org.g_lint.analyzer.impl;

import org.g_lint.analyzer.IAnalyzer;

/**
 * Base class for all {@link IAnalyzer} implementations
 * 
 * @author frizbog
 */
public abstract class AAnalyzer implements IAnalyzer {

    @Override
    public String getId() {
        return this.getClass().getName();
    }

}