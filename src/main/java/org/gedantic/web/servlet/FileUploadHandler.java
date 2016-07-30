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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
 * Ajax Servlet to handle File upload request from Client
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
                        if (!item.getName().toLowerCase().endsWith(".ged") && !item.getName().toLowerCase().endsWith(".gedcom")) {
                            LOG.info("Upload file " + item.getName() + " doesn't end in .ged or .gedcom");
                            throw new RuntimeException("Upload file " + item.getName() + " doesn't end in .ged or .gedcom");
                        }
                        if (item.getSize() > MAX_MB * 1024 * 1024) {
                            LOG.info("Upload file " + item.getName() + " exceeds " + MAX_MB + "MB limit - was " + item.getSize());
                            throw new RuntimeException("Upload file " + item.getName() + " exceeds " + MAX_MB + "MB limit - was "
                                    + item.getSize());
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
            } catch (RuntimeException ex) {
                LOG.error("Unable to upload file: " + ex.getMessage());
                response.setStatus(403);
            } catch (Exception ex) {
                LOG.error("Unable to upload file: ", ex);
                response.setStatus(403);
            }
        } else {
            if (request.getParameter("useSample") != null) {
                URL resource = this.getClass().getClassLoader().getResource("gedantic sample.ged");
                File file;
                try {
                    file = new File(resource.toURI());
                } catch (URISyntaxException e) {
                    throw new ServletException("Could not get resource URI: " + resource, e);
                }
                // Make sure to pass in false in last param to prevent deleting the file from classpath
                new ParserAndLoader(request, response, file, false).parseAndLoadIntoSession();
                request.getRequestDispatcher(Constants.URL_ANALYSIS_MENU).forward(request, response);
            } else {
                response.setStatus(403);
            }
        }
        LOG.debug("<doPost");
    }

}
