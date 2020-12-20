package io.fabric8.test;

import io.fabric8.openshift.api.model.SecurityContextConstraints;
import io.fabric8.openshift.api.model.SecurityContextConstraintsList;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplySCC {
    public static final Logger logger = LoggerFactory.getLogger(ApplySCC.class);

    public static void main(String[] args) {
        try (final OpenShiftClient oc = new DefaultOpenShiftClient()) {
            logger.info("Listing current SecurityContextConstraint:");
            SecurityContextConstraintsList sccs = oc.securityContextConstraints().list();
            
            for (SecurityContextConstraints s : sccs.getItems()) {
                logger.info(s.getMetadata().getName());
            }
            logger.info("--------------------------------------");

            logger.info("Loading SecurityContextConstraint from YAML manifest");
            SecurityContextConstraints scc = oc.securityContextConstraints().load(ApplySCC.class.getResourceAsStream("/test-scc.yml")).get();

            logger.info("Applying SecurityContextConstraint to OpenShift Cluster");
            oc.securityContextConstraints().createOrReplace(scc);
            logger.info("Done!");
        }
    }
}
