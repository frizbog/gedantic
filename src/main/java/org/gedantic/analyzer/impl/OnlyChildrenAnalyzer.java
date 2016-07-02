package org.gedantic.analyzer.impl;

import java.util.*;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.IndividualRelatedResult;
import org.gedantic.analyzer.comparator.IndividualResultSortComparator;
import org.gedantic.web.Constants;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;

/**
 * @author frizbog
 */
public class OnlyChildrenAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();

        for (Individual i : g.individuals.values()) {
            if (i.familiesWhereChild == null || i.familiesWhereChild.isEmpty()) {
                continue;
            }
            Set<Individual> kids = new HashSet<>();
            for (FamilyChild fc : i.familiesWhereChild) {
                kids.addAll(fc.family.children);
            }
            if (kids.size() == 1) {
                result.add(new IndividualRelatedResult(i, null, null, null));
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
        return "People who have no siblings";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Only children";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_INDIVIDUAL_RESULTS;
    }

}
