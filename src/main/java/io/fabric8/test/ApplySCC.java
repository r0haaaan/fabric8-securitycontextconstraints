package io.fabric8.test;

import io.fabric8.openshift.api.model.SecurityContextConstraints;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

import java.util.logging.Logger;

public class ApplySCC {
    public static final Logger logger = Logger.getLogger(ApplySCC.class.getName());

    public static void main(String[] args) {
        try (final OpenShiftClient oc = new DefaultOpenShiftClient()) {
            logger.info("Loading SecurityContextConstraint from YAML manifest");
            SecurityContextConstraints scc = oc.securityContextConstraints().load(ApplySCC.class.getResourceAsStream("/test-scc.yml")).get();

            logger.info("Applying SecurityContextConstraint to OpenShift Cluster");
            oc.securityContextConstraints().createOrReplace(scc);
        }
    }
}
