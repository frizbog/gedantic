package org.gedantic.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.gedantic.analyzer.AnalyzerList;

/**
 * A listener that ensures application scope items are set up
 * 
 * @author frizbog
 */
public class ApplicationSetupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ; // Nothing
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("analyzers", AnalyzerList.getInstance().getAnalyzers());
    }

}
