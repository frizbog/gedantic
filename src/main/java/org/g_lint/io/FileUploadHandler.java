package org.g_lint.io;

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
                        }
                    }

                    // File uploaded successfully
                    request.setAttribute("message", "File uploaded successfully (" + length + " bytes)");
                } else {
                    LOG.info("User did not accept terms of use and privacy statement");
                    request.setAttribute("message", "You must accept the terms of use and privacy statement to upload.");
                }
            } catch (Exception ex) {
                LOG.error("Unable to upload file", ex);
                request.setAttribute("message", "File upload failed - " + ex.getMessage());
            }
        } else {
            LOG.info("User attempted something other than file upload");
            request.setAttribute("message", "Sorry this Servlet only handles file upload request");
        }
        request.getRequestDispatcher("/result.jsp").forward(request, response);
        LOG.debug("<doPost");
    }

}
