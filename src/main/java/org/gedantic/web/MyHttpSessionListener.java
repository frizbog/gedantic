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
package org.gedantic.web;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class MyHttpSessionListener implements javax.servlet.http.HttpSessionListener {

    /** Logger */
    private static final Logger LOG = LoggerFactory.getLogger(MyHttpSessionListener.class.getName());

    private int sessionCount = 0;

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {

        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        long freeMemory = rt.freeMemory();
        sessionCount++;
        LOG.info("sessionCreate - freeMemory:" + Double.toString(freeMemory / (1024 * 1024)) + " maxMemory:" + Double.toString(
                maxMemory / (1024 * 1024)) + " count: " + sessionCount);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {

        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        long freeMemory = rt.freeMemory();
        sessionCount--;
        LOG.info("sessionDestroy - freeMemory:" + Double.toString(freeMemory / (1024 * 1024)) + " maxMemory:" + Double.toString(
                maxMemory / (1024 * 1024)) + " count: " + sessionCount);
    }

}
