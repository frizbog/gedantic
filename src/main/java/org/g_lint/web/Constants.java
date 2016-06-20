package org.g_lint.web;

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

}
