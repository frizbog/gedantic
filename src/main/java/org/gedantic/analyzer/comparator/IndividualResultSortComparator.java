/*
 * Copyright (c) 2009-2016 Matthew R. Harrah
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.gedantic.analyzer.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.IndividualRelatedResult;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.PersonalName;

/**
 * Comparator for sorting results about individuals by last name (surname) first, then first (given) name
 * 
 * @author frizbog1
 * 
 */
public class IndividualResultSortComparator implements Serializable, Comparator<AResult> {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -8121061183483337581L;

    /**
     * Compare two individuals
     * 
     * @param ar1
     *            result 1, which is assumed to be an {@link IndividualRelatedResult}
     * @param ar2
     *            result 2, which is assumed to be an {@link IndividualRelatedResult}
     * @return -1 if i1 &lt; i2, 0 if i1 == i2, 1 if i1 &gt; i2
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(AResult ar1, AResult ar2) {

        IndividualRelatedResult r1 = (IndividualRelatedResult) ar1;
        IndividualRelatedResult r2 = (IndividualRelatedResult) ar2;

        Individual i1 = r1.getIndividual();
        Individual i2 = r2.getIndividual();

        String s1 = "-unknown-";
        String s2 = "-unknown-";
        PersonalName n1 = null;
        PersonalName n2 = null;
        if (!i1.names.isEmpty()) {
            n1 = i1.names.get(0);
        }
        if (!i2.names.isEmpty()) {
            n2 = i2.names.get(0);
        }

        if (n1 != null) {
            if (n1.surname == null && n1.givenName == null) {
                if (n1.basic.contains("/")) {
                    String sn = n1.basic.substring(n1.basic.indexOf("/"));
                    String gn = n1.basic.substring(0, n1.basic.indexOf("/"));
                    s1 = sn + ", " + gn;
                }
            } else {
                s1 = n1.surname + ", " + n1.givenName;
            }
        }

        if (n2 != null) {
            if (n2.surname == null && n2.givenName == null) {
                if (n2.basic.contains("/")) {
                    String sn = n2.basic.substring(n2.basic.indexOf("/"));
                    String gn = n2.basic.substring(0, n2.basic.indexOf("/"));
                    s2 = sn + ", " + gn;
                }
            } else {
                s2 = n2.surname + ", " + n2.givenName;
            }
        }

        return s1.compareTo(s2);
    }
}
