package org.gedantic.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.gedantic.web.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet to handle File upload request from Client
 */
public class FileUploadHandler extends HttpServlet {
    /** The upload directory. */
    static final String UPLOAD_DIRECTORY = System.getProperty("java.io.tmpdir");

    /** Logger */
    private static final Logger LOG = LoggerFactory.getLogger(FileUploadHandler.class.getName());

    /**
     * The maximum number of MB allowed to be uploaded
     */
    private static final int MAX_MB = 8;

    /** Serial Version UID */
    private static final long serialVersionUID = 6317204513997696239L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(">doPost");

        // process only if its multipart content
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                long length = 0;
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        if (item.getSize() > MAX_MB * 1024 * 1024) {
                            LOG.info("Upload file exceeds " + MAX_MB + "MB limit - was " + item.getSize());
                            request.setAttribute(Constants.ALERT_MESSAGE, "Upload file exceeds " + MAX_MB + "MB limit");
                            request.setAttribute(Constants.ALERT_MESSAGE_TYPE, "alert alert-danger");
                            request.getRequestDispatcher(Constants.URL_UPLOAD_PAGE).forward(request, response);
                            break;
                        }
                        String name = new File(item.getName()).getName();
                        String fullPathAndName = UPLOAD_DIRECTORY + File.separator + name;
                        File file = new File(fullPathAndName);
                        item.write(file);
                        length = file.length();
                        LOG.info("Wrote " + fullPathAndName + " (" + length + " bytes)");

                        new ParserAndLoader(request, response, file).parseAndLoadIntoSession();
                    }
                }
            } catch (Exception ex) {
                LOG.error("Unable to upload file", ex);
                request.setAttribute(Constants.ALERT_MESSAGE, "File upload failed - " + ex.getMessage());
                request.setAttribute(Constants.ALERT_MESSAGE_TYPE, "alert alert-danger");
                request.getRequestDispatcher(Constants.URL_UPLOAD_PAGE).forward(request, response);
            }
        } else {
            LOG.info("User attempted something other than file upload");
            request.setAttribute(Constants.ALERT_MESSAGE, "Sorry, this form only handles file upload requests");
            request.setAttribute(Constants.ALERT_MESSAGE_TYPE, "alert alert-danger");
            request.getRequestDispatcher(Constants.URL_UPLOAD_PAGE).forward(request, response);
        }
        if (!response.isCommitted()) {
            request.getRequestDispatcher(Constants.URL_ANALYSIS_MENU).forward(request, response);
        }
        LOG.debug("<doPost");
    }

}
