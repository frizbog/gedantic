package org.gedantic.web;

/**
 * A bunch of constants used by the web classes for redirects and stuff
 * 
 * @author frizbog
 */
public class Constants {
    /**
     * The page for doing an upload of a GEDCOM file
     */
    public static final String URL_UPLOAD_PAGE = "/upload.tiles";

    /**
     * The page for picking an analysis to perform on the loaded file
     */
    public static final String URL_ANALYSIS_MENU = "/analyzeMenu.tiles";

    /**
     * The page for displaying analysis results
     */
    public static final String URL_ANALYSIS_INDIVIDUAL_RESULTS = "/individualResults.tiles";

    /**
     * Request attribute name for alerts
     */
    public static final String ALERT_MESSAGE = "message";

    /**
     * Request attribute name for the styles to use for the alert message type
     */
    public static final String ALERT_MESSAGE_TYPE = "messageType";

    /**
     * Request attribute name for the name of the loaded gedcom file
     */
    public static final String GEDCOM_NAME = "gedcomName";

    /**
     * Session attribute name for the loaded gedcom file
     */
    public static final String GEDCOM = "gedcom";

    /**
     * Session attribute name for the number of individuals in the GEDCOM file
     */
    public static final String NUM_INDIVIDUALS = "numIndividuals";

    /**
     * Session attribute name for the number of families in the GEDCOM file
     */
    public static final String NUM_FAMILIES = "numFamilies";

    /**
     * Request attribute name for the name of the analysis results
     */
    public static final String RESULTS = "results";

    /**
     * Request attribute name for the name of the analysis name
     */
    public static final String ANALYSIS_NAME = "analysisName";

    /**
     * Request attribute name for the name of the analysis description
     */
    public static final String ANALYSIS_DESCRIPTION = "analysisDescription";

    /**
     * Session attribute name for file load progress
     */
    public static final String FILE_PROGRESS = "fileProgress";

    /**
     * Session attribute name for file parse progress
     */
    public static final String PARSE_PROGRESS = "parseProgress";

    /**
     * Session attribute name for upload file size
     */
    public static final String UPLOAD_FILE_SIZE = "uploadFileSize";
}
