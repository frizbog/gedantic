/*
 * Copyright (c) 2016 Matthew R. Harrah
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.gedantic.analyzer.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.result.FamilyRelatedResult;
import org.gedantic.analyzer.result.IndividualRelatedResult;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.PersonalName;

/**
 * Comparator for sorting results about individuals by last name (surname) first, then first (given) name
 * 
 * @author frizbog1
 */
public class MixedResultSortComparator implements Serializable, Comparator<AResult> {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -8121061183483337581L;

    /**
     * Compare two results, based on the individuals in them.
     * 
     * @param ar1
     *            result 1, which is assumed to be an {@link IndividualRelatedResult} or a {@link FamilyRelatedResult}
     * @param ar2
     *            result 2, which is assumed to be an {@link IndividualRelatedResult} or a {@link FamilyRelatedResult}
     * @return -1 if i1 &lt; i2, 0 if i1 == i2, 1 if i1 &gt; i2
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(AResult ar1, AResult ar2) {

        Individual i1 = getIndividualReferenced(ar1);
        Individual i2 = getIndividualReferenced(ar2);

        return compareIndividuals(i1, i2);
    }

    /**
     * Compare two individuals
     * 
     * @param i1
     *            the first individual
     * @param i2
     *            the second individual
     * @return -1 if i1 &lt; i2, 0 if i1 == i2, 1 if i1 &gt; i2
     */
    private int compareIndividuals(Individual i1, Individual i2) {
        String s1 = "-unknown-";
        String s2 = "-unknown-";
        PersonalName n1 = null;
        PersonalName n2 = null;

        if (i1 != null && i1.getNames() != null && !i1.getNames().isEmpty()) {
            n1 = i1.getNames().get(0);
        }
        if (i2 != null && i2.getNames() != null && !i2.getNames().isEmpty()) {
            n2 = i2.getNames().get(0);
        }

        if (n1 != null) {
            if (n1.getSurname() == null && n1.getGivenName() == null) {
                if (n1.getBasic().contains("/")) {
                    String sn = n1.getBasic().substring(n1.getBasic().indexOf("/"));
                    String gn = n1.getBasic().substring(0, n1.getBasic().indexOf("/"));
                    s1 = sn + ", " + gn;
                }
            } else {
                s1 = n1.getSurname() + ", " + n1.getGivenName();
            }
        }

        if (n2 != null) {
            if (n2.getSurname() == null && n2.getGivenName() == null) {
                if (n2.getBasic().contains("/")) {
                    String sn = n2.getBasic().substring(n2.getBasic().indexOf("/"));
                    String gn = n2.getBasic().substring(0, n2.getBasic().indexOf("/"));
                    s2 = sn + ", " + gn;
                }
            } else {
                s2 = n2.getSurname() + ", " + n2.getGivenName();
            }
        }

        return s1.compareTo(s2);
    }

    /**
     * Get the individual referenced in the result supplied. For families, go with the husband unless there isn't one, in which case
     * go with the wife...a sexist strategy to be sure, but one most likely to work with most genealogical records.
     * 
     * @param analysisResult
     *            the analysis result
     * @return the individual referenced.
     */
    private Individual getIndividualReferenced(AResult analysisResult) {
        if (analysisResult instanceof FamilyRelatedResult) {
            return getIndividualReferenced((FamilyRelatedResult) analysisResult);
        }
        return getIndividualReferenced((IndividualRelatedResult) analysisResult);
    }

    /**
     * Get the individual referenced in the result supplied. For families, go with the husband unless there isn't one, in which case
     * go with the wife...a sexist strategy to be sure, but one most likely to work with most genealogical records.
     * 
     * @param analysisResult
     *            the analysis result
     * @return the individual referenced.
     */
    private Individual getIndividualReferenced(FamilyRelatedResult analysisResult) {
        Family f = analysisResult.getFamily();
        if (f == null) {
            return null;
        }
        if (f.getHusband() != null) {
            return f.getHusband();
        }
        return f.getWife();

    }

    /**
     * Get the individual referenced in the result supplied.
     * 
     * @param analysisResult
     *            the analysis result
     * @return the individual referenced.
     */
    private Individual getIndividualReferenced(IndividualRelatedResult analysisResult) {
        return analysisResult.getIndividual();
    }

}
