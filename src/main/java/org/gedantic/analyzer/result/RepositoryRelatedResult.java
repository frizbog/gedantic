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

import org.gedantic.analyzer.AResult;
import org.gedcom4j.model.Repository;

/**
 * An analysis result about a {@link Repository}
 * 
 * @author frizbog
 */
public class RepositoryRelatedResult extends AResult {

    /**
     * The repository this result is about
     */
    private final Repository repo;

    /**
     * Constructor
     * 
     * @param repo
     *            the repo with the finding
     * @param factType
     *            the fact that the finding relates to - optional
     * @param value
     *            the value that was problematic - optional
     * @param problem
     *            a description of the problem - optional
     */
    public RepositoryRelatedResult(Repository repo, String factType, String value, String problem) {
        super(factType, value, problem);
        this.repo = repo;
    }

    /**
     * Get the source
     * 
     * @return the source
     */
    public Repository getRepository() {
        return repo;
    }

}
