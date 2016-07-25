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
package org.gedantic.analyzer.impl;

import java.util.ArrayList;
import java.util.List;

import org.gedantic.analyzer.AAnalyzer;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.result.SourceRelatedResult;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Multimedia;
import org.gedcom4j.model.RepositoryCitation;
import org.gedcom4j.model.Source;

/**
 * Analyzer that finds {@link Source} items without {@link RepositoryCitation} or {@link Multimedia} records attached. Not a
 * problem, but a good potential area for more research so the sources can be validated.
 * 
 * @author frizbog
 */
public class SourcesWithoutRepositoryCitationsOrMediaAnalyzer extends AAnalyzer {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AResult> analyze(Gedcom g) {
        List<AResult> result = new ArrayList<>();
        if (g == null || g.getSources() == null) {
            return result;
        }
        for (Source s : g.getSources().values()) {
            if (s != null && (s.getMultimedia() == null || s.getMultimedia().isEmpty()) && (s.getRepositoryCitation() == null)) {
                result.add(new SourceRelatedResult(s, null, null, null));
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Source records that do not have citations to repositories and do not have media attached";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Sources without repository citations or media";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultsTileName() {
        return Constants.URL_ANALYSIS_SOURCE_RESULTS;
    }

}
