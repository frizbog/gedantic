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

import java.util.Comparator;

import org.gedantic.analyzer.AnalysisResult;

/**
 * Comparator for sorting results
 * 
 * @author frizbog
 */
public class AnalysisResultComparator implements Comparator<AnalysisResult> {

    @Override
    public int compare(AnalysisResult o1, AnalysisResult o2) {
        int result = 0;
        if (o1 == null) {
            if (o2 == null) {
                return result;
            }
            return 1;
        }
        if (o2 == null) {
            return -1;
        }

        if (o1.getItemType() == null) {
            if (o2.getItemType() != null) {
                return 1;
            }
        } else {
            result = o1.getItemType().compareTo(o2.getItemType());
            if (result != 0) {
                return result;
            }
        }

        if (o1.getItem() == null) {
            if (o2.getItem() != null) {
                return 1;
            }
        } else {
            result = o1.getItem().compareTo(o2.getItem());
            if (result != 0) {
                return result;
            }
        }

        if (o1.getFactType() == null) {
            if (o2.getFactType() != null) {
                return 1;
            }
        } else {
            result = o1.getFactType().compareTo(o2.getFactType());
            if (result != 0) {
                return result;
            }
        }

        if (o1.getProblemDescription() == null) {
            if (o2.getProblemDescription() != null) {
                return 1;
            }
        } else {
            result = o1.getProblemDescription().compareTo(o2.getProblemDescription());
            if (result != 0) {
                return result;
            }
        }

        return 0;

    }

}
