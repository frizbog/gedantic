package org.g_lint.analyzer.impl;

import java.util.ArrayList;
import java.util.List;

import org.g_lint.analyzer.AResult;
import org.g_lint.analyzer.IAnalyzer;
import org.gedcom4j.model.Gedcom;

/**
 * @author frizbog
 *
 */
public class ChildrenWithDifferentSurnamesAnalyzer implements IAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Find children who have different surnames than both their parents";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Children with different surnames";
    }

}
