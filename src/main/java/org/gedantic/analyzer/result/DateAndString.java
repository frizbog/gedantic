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
package org.gedantic.analyzer.result;

import java.util.Date;

/**
 * An object that contains a Date string and its parsed value.
 *
 * @author frizbog
 */
public class DateAndString implements Comparable<DateAndString> {

    /** The date string. */
    private String dateString;

    /** The date. */
    private Date date;

    @Override
    public int compareTo(DateAndString o) {
        if (o == null) {
            return 1;
        }
        if (o.getDate() == null) {
            return 1;
        }
        if (getDate() == null) {
            return -1;
        }

        return (int) Math.signum(getDate().getTime() - o.getDate().getTime());
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        DateAndString other = (DateAndString) obj;
        if (date == null) {
            if (other.date != null) {
                return false;
            }
        } else if (!date.equals(other.date)) {
            return false;
        }
        if (dateString == null) {
            if (other.dateString != null) {
                return false;
            }
        } else if (!dateString.equals(other.dateString)) {
            return false;
        }
        return true;
    }

    /**
     * Get the date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Get the dateString.
     *
     * @return the dateString
     */
    public String getDateString() {
        return dateString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((dateString == null) ? 0 : dateString.hashCode());
        return result;
    }

    /**
     * Set the date.
     *
     * @param date
     *            the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Set the dateString.
     *
     * @param dateString
     *            the dateString to set
     */
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

}
