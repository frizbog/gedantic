package org.g_lint.analyzer.impl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.g_lint.analyzer.AAnalyzer;
import org.g_lint.analyzer.AResult;
import org.g_lint.analyzer.IndividualRelatedResult;
import org.g_lint.analyzer.comparator.IndividualResultSortComparator;
import org.g_lint.web.Constants;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.PersonalName;

/**
 * @author frizbog
 */
public class PeopleWithoutSurnamesAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {

        List<AResult> result = new ArrayList<>();

        for (Individual i : g.individuals.values()) {
            if (i.names == null || i.names.isEmpty()) {
                continue;
            }
            Set<String> personSurnames = getSurnamesFromIndividual(i);
            if (personSurnames.isEmpty() || (personSurnames.size() == 1 && personSurnames.contains(""))) {
                // Found a problem
                IndividualRelatedResult r = new IndividualRelatedResult(i, null, null, "Individual has no surnames");
                result.add(r);
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
        return "People who have names but no surnames";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "People without surnames";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_INDIVIDUAL_RESULTS;
    }

    /**
     * Get all the surnames for an individual
     * 
     * @param i
     *            the individual
     * @return a Set of all the surnames (as Strings)
     */
    private Set<String> getSurnamesFromIndividual(Individual i) {
        TreeSet<String> result = new TreeSet<String>();
        Pattern pattern = Pattern.compile(".*\\/(.*)\\/.*");
        for (PersonalName pn : i.names) {
            if (pn.surname != null) {
                result.add(pn.surname.value);
            }
            if (pn.basic != null) {
                Matcher matcher = pattern.matcher(pn.basic);
                while (matcher.find()) {
                    result.add(matcher.group(1));
                }
            }
        }
        return result;
    }

}
