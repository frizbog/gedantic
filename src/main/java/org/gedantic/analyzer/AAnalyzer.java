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

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gedantic.analyzer.result.DateAndString;
import org.gedcom4j.model.*;
import org.gedcom4j.parser.DateParser;
import org.gedcom4j.parser.DateParser.ImpreciseDatePreference;

/**
 * Base class for all {@link IAnalyzer} implementations
 * 
 * @author frizbog
 */
public abstract class AAnalyzer implements IAnalyzer {

    @Override
    public String getId() {
        return this.getClass().getSimpleName();
    }

    /**
     * Get a comma-delimited string of tag ID's
     * 
     * @return a comma-delimited string of tag ID's
     */
    public String getTagIds() {
        StringBuilder result = new StringBuilder();
        AnalysisTag[] tags = getTags();
        for (int i = 0; i < tags.length; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(tags[i].getId());
        }
        return result.toString();
    }

    @Override
    public boolean isNewish() {
        return false;
    }

    /**
     * Get the birth date of preference from the supplied individual
     * 
     * @param i
     *            the individual
     * @param datePreference
     *            the preference for imprecise dates
     * @return the date found, if any - null if no parseable date could be found
     */
    protected DateAndString getBirthDate(Individual i, ImpreciseDatePreference datePreference) {
        DateParser dp = new DateParser();
        DateAndString result = new DateAndString();
        if (i != null) {
            for (IndividualEvent e : i.getEventsOfType(IndividualEventType.BIRTH)) {
                if (isSpecified(e.getDate())) {
                    Date d = dp.parse(e.getDate().getValue());
                    if (d != null && (result.getDate() == null || d.before(result.getDate()))) {
                        result.setDate(d);
                        result.setDateString(e.getDate().getValue());
                    }
                }
            }
        }
        return result;
    }

    /**
     * Get the death date of preference from the supplied individual
     * 
     * @param i
     *            the individual
     * @param datePreference
     *            the preference for imprecise dates
     * @return the date found, if any - null if no parseable date could be found
     */
    protected DateAndString getDeathDate(Individual i, ImpreciseDatePreference datePreference) {
        DateParser dp = new DateParser();
        DateAndString result = new DateAndString();
        for (IndividualEvent e : i.getEventsOfType(IndividualEventType.DEATH)) {
            if (isSpecified(e.getDate())) {
                Date d = dp.parse(e.getDate().getValue());
                if (d != null && (result.getDate() == null || d.after(result.getDate()))) {
                    result.setDate(d);
                    result.setDateString(e.getDate().getValue());
                }
            }
        }
        return result;
    }

    /**
     * Get all the surnames for an individual
     * 
     * @param i
     *            the individual
     * @return a Set of all the surnames (as Strings)
     */
    protected Set<String> getSurnamesFromIndividual(Individual i) {
        TreeSet<String> result = new TreeSet<String>();
        Pattern pattern = Pattern.compile(".*\\/(.*)\\/.*");
        for (PersonalName pn : i.getNames()) {
            if ("<No /name>/".equals(pn.getBasic())) {
                result.add("");
                continue;
            }
            ;
            if (pn.getSurname() != null) {
                result.add(pn.getSurname().getValue());
            }
            if (pn.getBasic() != null) {
                Matcher matcher = pattern.matcher(pn.getBasic());
                while (matcher.find()) {
                    result.add(matcher.group(1));
                }
            }
        }
        return result;
    }

    /**
     * Is the string supplied non-null, and has something other than whitespace in it?
     * 
     * @param s
     *            the strings
     * @return true if the string supplied non-null, and has something other than whitespace in it
     */
    protected boolean isSpecified(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is the supplied string with custom tags non-null, and has something other than whitespace in its value field?
     * 
     * @param swct
     *            the string with custom tags
     * @return true if the string supplied non-null, and has something other than whitespace in it
     */
    protected boolean isSpecified(StringWithCustomTags swct) {
        if (swct == null || swct.getValue() == null || swct.getValue().isEmpty()) {
            return false;
        }
        for (int i = 0; i < swct.getValue().length(); i++) {
            if (!Character.isWhitespace(swct.getValue().charAt(i))) {
                return true;
            }
        }
        return false;
    }

}