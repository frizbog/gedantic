package org.gedantic.analyzer.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class PeopleWithoutParentsAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();

        for (Individual i : g.getIndividuals().values()) {
            if (i.getFamiliesWhereChild() == null || i.getFamiliesWhereChild().isEmpty()) {
                result.add(new IndividualRelatedResult(i, null, null, null));
            } else {
                boolean foundParent = false;
                for (FamilyChild fc : i.getFamiliesWhereChild()) {
                    foundParent = fc.getFamily().getWife() != null || fc.getFamily().getHusband() != null;
                }
                if (!foundParent) {
                    result.add(new IndividualRelatedResult(i, null, null, "Child of at least one family record, but no family with designated parents"));
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
        return "People who have no parents recorded";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "People without parents";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_INDIVIDUAL_RESULTS;
    }

}
