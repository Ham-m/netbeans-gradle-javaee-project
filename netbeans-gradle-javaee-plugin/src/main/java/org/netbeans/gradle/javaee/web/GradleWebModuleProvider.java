/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.netbeans.modules.web.spi.webmodule.WebModuleFactory;
import org.netbeans.modules.web.spi.webmodule.WebModuleProvider;
import org.openide.filesystems.FileObject;

import java.util.logging.Logger;

/**
 *
 * @author Ed
 */
public class GradleWebModuleProvider implements WebModuleProvider {

    private static final Logger LOGGER = Logger.getLogger(GradleWebModuleProvider.class.getName());

    private final Project project;
    private WebModule webModule;

    public GradleWebModuleProvider(Project project) {
        this.project = project;
    }

    @Override
    public WebModule findWebModule(FileObject fo) {
        LOGGER.entering(GradleWebModuleProvider.class.getName(), "findWebModule");
        WebModule returnValue = null;
        if (project != null) {
            Project fileOwner = FileOwnerQuery.getOwner(fo);
            if (project.equals(fileOwner)) {
                returnValue = getWebModule();
            }
        }
        LOGGER.exiting(GradleWebModuleProvider.class.getName(), "findWebModule", returnValue);
        return returnValue;
    }

    private WebModule getWebModule() {
        if (webModule == null) {
            initWebModule();
        }
        return webModule;
    }

    private synchronized void initWebModule() {
        if (webModule == null) {
            webModule = WebModuleFactory.createWebModule(new GradleWebModuleImpl(project));
        }
    }

}
