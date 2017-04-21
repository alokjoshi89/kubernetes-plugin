package org.csanchez.jenkins.plugins.kubernetes;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.*;
import jenkins.model.Jenkins;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


/**
 * Workspace browser for Jenkins Kubernetes Plugin
 *
 * Inspired from https://github.com/jenkinsci/mesos-plugin/blob/master/src/main/java/org/jenkinsci/plugins/mesos/MesosWorkspaceBrowser.java
 */
@Extension
public class KubernetesWorkspaceBrowser extends WorkspaceBrowser{

    private static final Logger LOGGER = Logger.getLogger(KubernetesWorkspaceBrowser.class.getName());

    @Override
    public FilePath getWorkspace(Job job) {
        String remoteWorkSpacePath = null;
        String absoluteJobUrl = null;
        FilePath filePath = null;
        Jenkins jenkinsInstance = Hudson.getInstanceOrNull();
        LOGGER.info("Nodes went offline. Hence fetching workspace through master");
        if (job instanceof AbstractProject) {
            if(jenkinsInstance != null) {
                absoluteJobUrl = job.getAbsoluteUrl();
                filePath = jenkinsInstance.getWorkspaceFor((TopLevelItem) job);
                if(filePath!=null){
                    remoteWorkSpacePath = filePath.getRemote();
                    if(StringUtils.isNotBlank(remoteWorkSpacePath) &&
                            StringUtils.isNotBlank(absoluteJobUrl)){
                        LOGGER.fine("Workspace Path of job:\t"+ absoluteJobUrl +" is:\t"+ remoteWorkSpacePath);
                    }
                }
            }
        }
        return filePath;
    }
}

