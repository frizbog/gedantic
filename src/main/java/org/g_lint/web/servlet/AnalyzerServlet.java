package org.g_lint.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.g_lint.analyzer.AResult;
import org.g_lint.analyzer.AnalyzerList;
import org.g_lint.analyzer.IAnalyzer;
import org.g_lint.web.Constants;
import org.gedcom4j.model.Gedcom;

/**
 * Servlet that does analysis and displays results
 * 
 * @author frizbog
 */
public class AnalyzerServlet extends HttpServlet {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -62757248978505969L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Gedcom g = (Gedcom) session.getAttribute(Constants.GEDCOM);

        IAnalyzer a = AnalyzerList.getInstance().getAnalyzers().get(req.getParameter("analyzerId"));
        List<AResult> results = a.analyze(g);

        req.setAttribute(Constants.RESULTS, results);
        req.getRequestDispatcher(Constants.URL_ANALYSIS_RESULTS).forward(req, resp);
    }
}
