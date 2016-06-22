package org.g_lint.analyzer.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.g_lint.analyzer.AAnalyzer;
import org.g_lint.analyzer.AResult;
import org.g_lint.analyzer.IndividualRelatedResult;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.PersonalName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author frizbog
 */
public class ChildrenWithDifferentSurnamesAnalyzer extends AAnalyzer {

    /** Logger */
    private static final Logger LOG = LoggerFactory.getLogger(ChildrenWithDifferentSurnamesAnalyzer.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();

        for (Individual i : g.individuals.values()) {
            if (i.familiesWhereChild == null) {
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
                IndividualRelatedResult r = new IndividualRelatedResult(i, null, "",
                        "Individual has surnames " + personSurnames + " and parents have surnames " + allParentSurnames);
                result.add(r);
            }
        }

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
