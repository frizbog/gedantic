package org.gedantic.web.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gedantic.web.Constants;
import org.gedcom4j.model.Gedcom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A servlet filter that ensures theres a {@link Gedcom} loaded in session, and if not, redirect to the upload page.
 * 
 * @author frizbog
 */
public class CheckIfGedcomInSessionFilter implements Filter {

    /** Logger */
    private static final Logger LOG = LoggerFactory.getLogger(CheckIfGedcomInSessionFilter.class.getName());

    @Override
    public void destroy() {
        ; // Nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        LOG.debug(">" + this.getClass().getName() + ".doFilter() - requestURI = " + httpRequest.getRequestURI());

        try {
            if (httpRequest.getRequestURI().contains(Constants.URL_UPLOAD_PAGE)
                    || httpRequest.getRequestURI().equals(httpRequest.getServletContext().getContextPath() + "/")) {
                LOG.info("Wiping out session because we're on the uplaod page");
                session.invalidate();
            } else {
                if ((Gedcom) session.getAttribute(Constants.GEDCOM) == null) {
                    LOG.info("Redirecting from " + httpRequest.getRequestURI() + " to upload page because there is no gedcom in session");
                    httpRequest.setAttribute(Constants.ALERT_MESSAGE, "Please upload a GEDCOM to analyze");
                    request.setAttribute(Constants.ALERT_MESSAGE_TYPE, "alert alert-warning");
                    request.getRequestDispatcher(Constants.URL_UPLOAD_PAGE).forward(request, response);
                }
            }
        } catch (ClassCastException e) {
            LOG.error("Found gedcom session attribute but it was not a " + Gedcom.class.getCanonicalName());
            httpResponse.sendRedirect(httpRequest.getContextPath() + Constants.URL_UPLOAD_PAGE);
        }
        LOG.debug("<" + this.getClass().getName() + ".doFilter()");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        LOG.debug(this.getClass().getName() + " initialized");
    }

}
