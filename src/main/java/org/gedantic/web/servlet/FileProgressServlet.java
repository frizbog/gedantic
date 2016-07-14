package org.gedantic.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gedantic.web.Constants;
import org.gedcom4j.io.event.FileProgressEvent;

/**
 * A servlet intended for AJAX calls to get the file upload progress
 * 
 * @author frizbog
 */
public class FileProgressServlet extends HttpServlet {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -3108629716219085316L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        FileProgressEvent fileProgress = (FileProgressEvent) session.getAttribute(Constants.FILE_PROGRESS);
        Long fileSize = (Long) session.getAttribute(Constants.UPLOAD_FILE_SIZE);
        if (fileProgress != null && fileSize != null && fileSize.longValue() != 0) {
            String pctComplete = "" + ((100 * fileProgress.getBytesProcessed()) / fileSize.longValue());
            resp.getWriter().write(pctComplete);
        }
    }

}