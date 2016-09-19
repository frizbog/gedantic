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
package org.gedantic.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gedantic.web.Constants;
import org.gedcom4j.model.Gedcom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A servlet filter that ensures theres a {@link Gedcom} loaded in session, and if not, redirect to the upload page.
 * Except for certain pages, of course.
 * 
 * @author frizbog
 */
public class CheckIfGedcomInSessionFilter implements Filter {

    /** Logger */
    private static final Logger LOG = LoggerFactory.getLogger(CheckIfGedcomInSessionFilter.class.getName());

    @Override
    public void destroy() {
        // Nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        try {
            if (httpRequest.getRequestURI().contains(Constants.URL_UPLOAD_PAGE) || httpRequest.getRequestURI().equals(httpRequest.getServletContext()
                    .getContextPath() + "/")) {
                LOG.info("Wiping out session because we're on the upload page");
                session.invalidate();
            } else if ((Gedcom) session.getAttribute(Constants.GEDCOM) == null && !httpRequest.getRequestURI().contains(Constants.URL_ABOUT_PAGE)) {
                LOG.info("Redirecting from " + httpRequest.getRequestURI() + " to upload page because there is no gedcom in session");
                httpRequest.setAttribute(Constants.ALERT_MESSAGE, "Please upload a GEDCOM to analyze");
                request.setAttribute(Constants.ALERT_MESSAGE_TYPE, "alert alert-warning");
                request.getRequestDispatcher(Constants.URL_UPLOAD_PAGE).forward(request, response);
            }
        } catch (ClassCastException e) {
            LOG.error("Found gedcom session attribute but it was not a " + Gedcom.class.getCanonicalName(), e);
            httpResponse.sendRedirect(httpRequest.getContextPath() + Constants.URL_UPLOAD_PAGE);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        LOG.debug(this.getClass().getName() + " initialized");
    }

}
