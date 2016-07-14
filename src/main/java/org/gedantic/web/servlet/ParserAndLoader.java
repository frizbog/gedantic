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
package org.gedantic.web.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gedantic.web.Constants;
import org.gedcom4j.Options;
import org.gedcom4j.exception.GedcomParserException;
import org.gedcom4j.io.event.FileProgressEvent;
import org.gedcom4j.io.event.FileProgressListener;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.parser.GedcomParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class to parse and load a file from a given HTTP request
 */
class ParserAndLoader implements FileProgressListener {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ParserAndLoader.class.getName());

    /** The request. */
    private final HttpServletRequest request;

    /** The file. */
    private final File file;

    /** The response. */
    private final HttpServletResponse response;

    /** The http session */
    private final HttpSession session;

    /**
     * Instantiates a new parser and loader.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @param file
     *            the file
     */
    ParserAndLoader(HttpServletRequest request, HttpServletResponse response, File file) {
        this.request = request;
        this.response = response;
        this.file = file;
        session = request.getSession();
    }

    @Override
    public void progressNotification(FileProgressEvent e) {
        session.setAttribute(Constants.FILE_PROGRESS, e);
    }

    /**
     * Parses the uploaded GEDCOM file, loads the {@link Gedcom} parse result object into the HTTP session, and deletes
     * the temp file.
     */
    void parseAndLoadIntoSession() {
        session.removeAttribute(Constants.GEDCOM);
        session.removeAttribute(Constants.GEDCOM_NAME);
        session.removeAttribute(Constants.FILE_PROGRESS);
        session.removeAttribute(Constants.PARSE_PROGRESS);
        session.removeAttribute(Constants.UPLOAD_FILE_SIZE);
        Options.setCollectionInitializationEnabled(true);
        session.setAttribute(Constants.UPLOAD_FILE_SIZE, Long.valueOf(file.length()));
        GedcomParser gp = new GedcomParser();
        gp.registerFileObserver(this);
        gp.setStrictCustomTags(false);
        gp.setStrictLineBreaks(false);

        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file))) {
            gp.load(stream);
            if (gp.getErrors().isEmpty()) {
                request.setAttribute("errors", gp.getWarnings());
            }
            if (gp.getWarnings().isEmpty()) {
                request.setAttribute("warnings", gp.getWarnings());
            }
            Gedcom g = gp.getGedcom();
            session.setAttribute(Constants.GEDCOM, g);
            session.setAttribute(Constants.GEDCOM_NAME, file.getName());
            session.setAttribute(Constants.NUM_INDIVIDUALS, g.getIndividuals().size());
            session.setAttribute(Constants.NUM_FAMILIES, g.getFamilies().size());
            StringBuilder parseResults = new StringBuilder("File uploaded. ");
            parseResults.append(g.getIndividuals().size() + " individuals in ");
            parseResults.append(g.getFamilies().size() + " families loaded.");
            request.setAttribute("parseResults", parseResults);
        } catch (IOException | GedcomParserException e) {
            LOG.error("Unable to load GEDCOM file " + file, e);
            request.setAttribute(Constants.ALERT_MESSAGE, e.getMessage());
            request.setAttribute(Constants.ALERT_MESSAGE_TYPE, "alert alert-danger");
            try {
                request.getRequestDispatcher(Constants.URL_UPLOAD_PAGE).forward(request, response);
            } catch (ServletException | IOException e1) {
                LOG.error("Unable to forward to the upload page", e1);
            }
        } finally {
            try {
                file.delete();
                LOG.info("Deleted temp GEDCOM file " + file.getName());
            } catch (Exception e) {
                LOG.error("Unable to delete temp GEDCOM file " + FileUploadHandler.UPLOAD_DIRECTORY + File.separator + file.getName(), e);
            }
        }
        gp = null;
    }

}
