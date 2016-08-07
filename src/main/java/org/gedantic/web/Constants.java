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
package org.gedantic.web;

/**
 * A bunch of constants used by the web classes for redirects and stuff
 * 
 * @author frizbog
 */
public class Constants {
    /**
     * Request attribute name for alerts
     */
    public static final String ALERT_MESSAGE = "message";

    /**
     * Request attribute name for the styles to use for the alert message type
     */
    public static final String ALERT_MESSAGE_TYPE = "messageType";

    /**
     * Request attribute name for the name of the analysis description
     */
    public static final String ANALYSIS_DESCRIPTION = "analysisDescription";

    /**
     * Form field name for the analyzer ID
     */
    public static final String ANALYSIS_ID = "analyzerId";

    /**
     * Request attribute name for the name of the analysis name
     */
    public static final String ANALYSIS_NAME = "analysisName";

    /**
     * Session attribute name for file load progress
     */
    public static final String FILE_PROGRESS = "fileProgress";

    /**
     * Session attribute name for the loaded gedcom file
     */
    public static final String GEDCOM = "gedcom";

    /**
     * Request attribute name for the name of the loaded gedcom file
     */
    public static final String GEDCOM_NAME = "gedcomName";

    /**
     * Session attribute name for the number of families in the GEDCOM file
     */
    public static final String NUM_FAMILIES = "numFamilies";

    /**
     * Session attribute name for the number of individuals in the GEDCOM file
     */
    public static final String NUM_INDIVIDUALS = "numIndividuals";

    /**
     * Session attribute name for file parse progress
     */
    public static final String PARSE_PROGRESS = "parseProgress";

    /**
     * Request attribute name for the name of the analysis results
     */
    public static final String RESULTS = "results";

    /**
     * Session attribute name for upload file size
     */
    public static final String UPLOAD_FILE_SIZE = "uploadFileSize";

    /**
     * Portion of URL for the "about" page
     */
    public static final CharSequence URL_ABOUT_PAGE = "about.tiles";

    /**
     * The page for displaying analysis results about couples
     */
    public static final String URL_ANALYSIS_COUPLE_RESULTS = "coupleResults.tiles";

    /**
     * The page for displaying analysis results about individuals
     */
    public static final String URL_ANALYSIS_INDIVIDUAL_RESULTS = "individualResults.tiles";

    /**
     * The page for picking an analysis to perform on the loaded file
     */
    public static final String URL_ANALYSIS_MENU = "analyzeMenu.tiles";

    /**
     * The page for displaying analysis results about individuals and families mixed together
     */
    public static final String URL_ANALYSIS_MIXED_RESULTS = "mixedResults.tiles";

    /**
     * The page for displaying analysis results about relationships
     */
    public static final String URL_ANALYSIS_RELATIONSHIP_RESULTS = "relationshipResults.tiles";

    /**
     * The page for displaying analysis results about sources.
     */
    public static final String URL_ANALYSIS_SOURCE_RESULTS = "sourceResults.tiles";

    /**
     * The page for doing an upload of a GEDCOM file
     */
    public static final String URL_UPLOAD_PAGE = "upload.tiles";

}
