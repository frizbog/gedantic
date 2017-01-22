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
 * Base class for an analysis result
 * 
 * @author frizbog
 */
public class AnalysisResult {

    /**
     * The type of fact having an issue
     */
    protected final String factType;

    /**
     * The type of item having an issue
     */
    protected final String itemType;

    /**
     * The thing that had the problemDescription
     */
    protected final String item;

    /**
     * The description of the problemDescription
     */
    protected final String problemDescription;

    /**
     * Instantiates a new a result.
     *
     * @param factType
     *            the fact type
     * @param itemType
     *            the type of item with the problem
     * @param item
     *            the item
     * @param problemDescription
     *            the problemDescription
     */
    public AnalysisResult(String factType, String itemType, String item, String problemDescription) {
        this.factType = StringEscapeUtils.escapeHtml(factType);
        this.itemType = StringEscapeUtils.escapeHtml(itemType);
        this.item = StringEscapeUtils.escapeHtml(item);
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
        if (factType == null) {
            if (other.factType != null) {
                return false;
            }
        } else if (!factType.equals(other.factType)) {
            return false;
        }
        if (item == null) {
            if (other.item != null) {
                return false;
            }
        } else if (!item.equals(other.item)) {
            return false;
        }
        if (itemType == null) {
            if (other.itemType != null) {
                return false;
            }
        } else if (!itemType.equals(other.itemType)) {
            return false;
        }
        if (problemDescription == null) {
            if (other.problemDescription != null) {
                return false;
            }
        } else if (!problemDescription.equals(other.problemDescription)) {
            return false;
        }
        return true;
    }

    /**
     * Get the fact type
     * 
     * @return the fact type
     */
    public String getFactType() {
        return factType;
    }

    /**
     * Get the problematic item
     * 
     * @return the item that cause the problemDescription
     */
    public String getItem() {
        return item;
    }

    /**
     * Get the itemType
     * 
     * @return the itemType
     */
    public String getItemType() {
        return itemType;
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
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((factType == null) ? 0 : factType.hashCode());
        result = prime * result + ((item == null) ? 0 : item.hashCode());
        result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
        result = prime * result + ((problemDescription == null) ? 0 : problemDescription.hashCode());
        return result;
    }
}
