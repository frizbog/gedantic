package org.g_lint.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet to handle File upload request from Client
 * 
 * @author Javin Paul
 */

public class FileUploadHandler extends HttpServlet {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 6317204513997696239L;

    /** Logger */
    private static final Logger LOG = LoggerFactory.getLogger(FileUploadHandler.class.getName());

    /**
     * Where the files get uploaded
     */
    private final String UPLOAD_DIRECTORY = System.getProperty("java.io.tmpdir");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(">doPost");

        // process only if its multipart content
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                boolean acceptedTermsOfUseAndPrivacy = false;
                for (FileItem item : multiparts) {
                    if (item.isFormField() && "touandprivacy".equals(item.getFieldName())) {
                        acceptedTermsOfUseAndPrivacy = true;
                    }
                }
                if (acceptedTermsOfUseAndPrivacy) {

                    long length = 0;
                    for (FileItem item : multiparts) {
                        if (!item.isFormField()) {
                            String name = new File(item.getName()).getName();
                            String fullPathAndName = UPLOAD_DIRECTORY + File.separator + name;
                            File file = new File(fullPathAndName);
                            item.write(file);
                            length = file.length();
                            LOG.info("Wrote " + fullPathAndName + " (" + length + " bytes)");

                            parseAndLoadIntoSession(request, file);
                        }
                    }
                } else {
                    LOG.info("User did not accept terms of use and privacy statement");
                    request.setAttribute("message", "You must accept the terms of use and privacy statement to upload.");
                    request.setAttribute("messageType", "alert alert-danger");
                }
            } catch (Exception ex) {
                LOG.error("Unable to upload file", ex);
                request.setAttribute("message", "File upload failed - " + ex.getMessage());
                request.setAttribute("messageType", "alert alert-danger");
            }
        } else {
            LOG.info("User attempted something other than file upload");
            request.setAttribute("message", "Sorry, this form only handles file upload requests");
            request.setAttribute("messageType", "alert alert-danger");
        }
        request.getRequestDispatcher("/result.jsp").forward(request, response);
        LOG.debug("<doPost");
    }

    /**
     * Parses the uploaded GEDCOM file, loads the {@link Gedcom} parse result object into the HTTP session, and deletes
     * the temp file.
     * 
     * @param request
     *            the HTTP request
     * @param file
     *            the uploaded GEDCOM file
     */
    private void parseAndLoadIntoSession(HttpServletRequest request, File file) {
        GedcomParser gp = new GedcomParser();
        gp.strictCustomTags = false;
        gp.strictLineBreaks = false;

        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file))) {
            gp.load(stream);
            if (gp.errors.isEmpty()) {
                request.setAttribute("parseErrors", gp.warnings);
            }
            if (gp.warnings.isEmpty()) {
                request.setAttribute("parseWarnings", gp.warnings);
            }
            Gedcom g = gp.gedcom;
            request.getSession().setAttribute("gedcom", g);
            StringBuilder parseResults = new StringBuilder("File uploaded. ");
            parseResults.append(g.individuals.size() + " individuals in ");
            parseResults.append(g.families.size() + " families loaded.");
            request.setAttribute("parseResults", parseResults);

        } catch (IOException | GedcomParserException e) {
            LOG.error("Unable to load GEDCOM file " + file, e);
        } finally {
            try {
                file.delete();
                LOG.info("Deleted temp GEDCOM file " + file.getName());
            } catch (Exception e) {
                LOG.error("Unable to delete temp GEDCOM file " + UPLOAD_DIRECTORY + File.separator + file.getName());
            }
        }
    }

}
