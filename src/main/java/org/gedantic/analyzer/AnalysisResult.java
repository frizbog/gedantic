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
package org.gedantic.analyzer;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * An analysis result
 * 
 * @author frizbog
 */
public class AnalysisResult {

    /**
     * The thing *about* the object that had an issue (Attribute, Source, etc)
     */
    protected final String aspectOfItemWithIssue;

    /**
     * The type of object that had an issue (Individual, Family, etc)
     */
    protected final String typeOfItemWithIssue;

    /**
     * The value that was problematic
     */
    protected final String problematicValue;

    /**
     * The description of the problem
     */
    protected final String problemDescription;

    /**
     * Which item had the problem - some identifying information
     */
    protected final String whichItem;

    /**
     * Instantiates a new a result.
     *
     * @param typeOfItemWithIssue
     *            The type of object that had an issue (Individual, Family, etc)
     * @param whichItem
     *            Which item had the problem - some identifying information
     * @param aspectOfItemWithIssue
     *            The thing *about* the object that had an issue (Attribute, Source, etc)
     * @param problematicValue
     *            The value that was problematic
     * @param problemDescription
     *            The description of the problem
     */
    public AnalysisResult(String typeOfItemWithIssue, String whichItem, String aspectOfItemWithIssue, String problematicValue,
            String problemDescription) {
        this.typeOfItemWithIssue = StringEscapeUtils.escapeHtml(typeOfItemWithIssue);
        this.whichItem = StringEscapeUtils.escapeHtml(whichItem);
        this.aspectOfItemWithIssue = StringEscapeUtils.escapeHtml(aspectOfItemWithIssue);
        this.problematicValue = StringEscapeUtils.escapeHtml(problematicValue);
        this.problemDescription = StringEscapeUtils.escapeHtml(problemDescription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AnalysisResult)) {
            return false;
        }
        AnalysisResult other = (AnalysisResult) obj;
        if (aspectOfItemWithIssue == null) {
            if (other.aspectOfItemWithIssue != null) {
                return false;
            }
        } else if (!aspectOfItemWithIssue.equals(other.aspectOfItemWithIssue)) {
            return false;
        }
        if (problemDescription == null) {
            if (other.problemDescription != null) {
                return false;
            }
        } else if (!problemDescription.equals(other.problemDescription)) {
            return false;
        }
        if (problematicValue == null) {
            if (other.problematicValue != null) {
                return false;
            }
        } else if (!problematicValue.equals(other.problematicValue)) {
            return false;
        }
        if (typeOfItemWithIssue == null) {
            if (other.typeOfItemWithIssue != null) {
                return false;
            }
        } else if (!typeOfItemWithIssue.equals(other.typeOfItemWithIssue)) {
            return false;
        }
        if (whichItem == null) {
            if (other.whichItem != null) {
                return false;
            }
        } else if (!whichItem.equals(other.whichItem)) {
            return false;
        }
        return true;
    }

    /**
     * Get the fact type
     * 
     * @return the fact type
     */
    public String getAspectOfItemWithIssue() {
        return aspectOfItemWithIssue;
    }

    /**
     * Get the problematic problematicValue
     * 
     * @return the problematicValue that cause the problemDescription
     */
    public String getProblematicValue() {
        return problematicValue;
    }

    /**
     * Get the problemDescription description
     * 
     * @return the problemDescription description
     */
    public String getProblemDescription() {
        return problemDescription;
    }

    /**
     * Get the typeOfItemWithIssue
     * 
     * @return the typeOfItemWithIssue
     */
    public String getTypeOfItemWithIssue() {
        return typeOfItemWithIssue;
    }

    /**
     * Get the whichItem
     * 
     * @return the whichItem
     */
    public String getWhichItem() {
        return whichItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aspectOfItemWithIssue == null) ? 0 : aspectOfItemWithIssue.hashCode());
        result = prime * result + ((problemDescription == null) ? 0 : problemDescription.hashCode());
        result = prime * result + ((problematicValue == null) ? 0 : problematicValue.hashCode());
        result = prime * result + ((typeOfItemWithIssue == null) ? 0 : typeOfItemWithIssue.hashCode());
        result = prime * result + ((whichItem == null) ? 0 : whichItem.hashCode());
        return result;
    }
}
