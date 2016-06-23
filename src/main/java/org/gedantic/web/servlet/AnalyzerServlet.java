package org.gedantic.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.AnalyzerList;
import org.gedantic.analyzer.IAnalyzer;
import org.gedantic.web.Constants;
import org.gedcom4j.model.Gedcom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet that does analysis and displays results
 * 
 * @author frizbog
 */
public class AnalyzerServlet extends HttpServlet {

    /** Logger */
    private static final Logger LOG = LoggerFactory.getLogger(AnalyzerServlet.class.getName());

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -62757248978505969L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Gedcom g = (Gedcom) session.getAttribute(Constants.GEDCOM);

        if (g == null) {
            LOG.info("Redirecting from " + req.getRequestURI() + " to upload page because there is no gedcom in session");
            req.setAttribute(Constants.ALERT_MESSAGE, "Please upload a GEDCOM to analyze");
            req.setAttribute(Constants.ALERT_MESSAGE_TYPE, "alert alert-warning");
            req.getRequestDispatcher(Constants.URL_UPLOAD_PAGE).forward(req, resp);
        }

        String analyzerId = req.getParameter("analyzerId");
        LOG.debug("Requested analysis: " + analyzerId);
        IAnalyzer a = AnalyzerList.getInstance().getAnalyzers().get(analyzerId);
        LOG.debug("Analyzer " + a + " found");

        List<AResult> results = a.analyze(g);

        LOG.debug("Analysis complete. " + results.size() + " findings.");

        req.setAttribute(Constants.ANALYSIS_NAME, a.getName());
        req.setAttribute(Constants.ANALYSIS_DESCRIPTION, a.getDescription());
        req.setAttribute(Constants.RESULTS, results);
        req.getRequestDispatcher(a.getResultsTileName()).forward(req, resp);
    }
}
