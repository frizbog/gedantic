package org.gedantic.analyzer.impl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.IndividualRelatedResult;
import org.gedantic.analyzer.comparator.IndividualResultSortComparator;
import org.gedantic.web.Constants;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.PersonalName;

/**
 * @author frizbog
 */
public class ChildrenWithDifferentSurnamesAnalyzer extends AAnalyzer {

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
            Set<String> personSurnames = getSurnamesFromIndividual(i);
            Set<String> allParentSurnames = new TreeSet<String>();
            for (FamilyChild fc : i.familiesWhereChild) {
                if (fc.family.husband != null) {
                    allParentSurnames.addAll(getSurnamesFromIndividual(fc.family.husband));
                }
                if (fc.family.wife != null) {
                    allParentSurnames.addAll(getSurnamesFromIndividual(fc.family.wife));
                }
            }
            if (allParentSurnames.isEmpty()) {
                continue;
            }

            Set<String> commonSurnames = new TreeSet<>(allParentSurnames);
            commonSurnames.retainAll(personSurnames);
            if (commonSurnames.isEmpty()) {
                // Found a problem
                IndividualRelatedResult r = new IndividualRelatedResult(i, null, null,
                        "Individual has surnames " + personSurnames + " and parents have surnames " + allParentSurnames);
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
        return "Children who have no matching surnames with their parents";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Children with different surnames";
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
