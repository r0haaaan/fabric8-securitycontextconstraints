# Applying SecurityContextConstraint from within a Pod

This project tries to apply an OpenShift `SecurityContextConstraint` from within a Pod. You would need to apply `ServiceAccount`, `ClusterRole` and `ClusterRoleBinding` manifests present in this repository used by the application from an admin user:
```bash
fabric8-securitycontextconstraints : $ oc create -f serviceaccount.yml 
serviceaccount/build-robot created
fabric8-securitycontextconstraints : $ oc create -f clusterrole.yml 
clusterrole.rbac.authorization.k8s.io/scc-reader-writer created
fabric8-securitycontextconstraints : $ oc create -f clusterrolebinding.yml 
clusterrolebinding.rbac.authorization.k8s.io/read-secrets-global created
```

Log out as admin user and log in again with some non-privileged user:
```bash
fabric8-securitycontextconstraints : $ oc login https://192.168.42.83:8443 --token=secret
Logged into "https://192.168.42.83:8443" as "developer" using the token provided.

You have one project on this server: "myproject"

Using project "myproject".
```

Build project
```
mvn clean install
```

Deploy your project to OpenShift using [Eclipse JKube](https://github.com/eclipse/jkube):
```
mvn oc:deploy -POpenshift
```
This would start an S2I build, generate OpenShift manifests and apply them to currently logged in OpenShift cluster.
```
fabric8-securitycontextconstraints : $ mvn oc:deploy -POpenshift
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------< org.example:fabric8-securitycontextconstraints >-----------
[INFO] Building fabric8-securitycontextconstraints 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] >>> openshift-maven-plugin:1.0.2:deploy (default-cli) > install @ fabric8-securitycontextconstraints >>>
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ fabric8-securitycontextconstraints ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 2 resources
[INFO] 
[INFO] --- openshift-maven-plugin:1.0.2:resource (jkube) @ fabric8-securitycontextconstraints ---
[INFO] oc: Using docker image name of namespace: myproject
[INFO] oc: Running generator java-exec
[INFO] oc: java-exec: Using Docker image quay.io/jkube/jkube-java-binary-s2i:0.0.8 as base / builder
[INFO] oc: Using resource templates from /home/rohaan/work/repos/fabric8-securitycontextconstraints/src/main/jkube
[INFO] oc: jkube-service: Adding a default service 'fabric8-securitycontextconstraints' with ports [8080]
[INFO] oc: jkube-revision-history: Adding revision history limit to 2
[INFO] oc: validating /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/classes/META-INF/jkube/openshift/build-robot-serviceaccount.yml resource                                                                            
[INFO] oc: validating /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/classes/META-INF/jkube/openshift/fabric8-securitycontextconstraints-service.yml resource                                                            
[INFO] oc: validating /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/classes/META-INF/jkube/openshift/fabric8-securitycontextconstraints-deploymentconfig.yml resource                                                   
[INFO] oc: validating /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/classes/META-INF/jkube/openshift/fabric8-securitycontextconstraints-route.yml resource                                                              
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ fabric8-securitycontextconstraints ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ fabric8-securitycontextconstraints ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /home/rohaan/work/repos/fabric8-securitycontextconstraints/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ fabric8-securitycontextconstraints ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ fabric8-securitycontextconstraints ---
[INFO] No tests to run.
[INFO] 
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ fabric8-securitycontextconstraints ---
[INFO] Building jar: /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/fabric8-securitycontextconstraints-1.0-SNAPSHOT.jar
[INFO] 
[INFO] --- maven-assembly-plugin:3.3.0:single (default) @ fabric8-securitycontextconstraints ---
[INFO] Building jar: /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/fabric8-securitycontextconstraints-1.0-SNAPSHOT-jar-with-dependencies.jar
[INFO] 
[INFO] --- openshift-maven-plugin:1.0.2:build (jkube) @ fabric8-securitycontextconstraints ---
[INFO] oc: Using OpenShift build with strategy S2I
[INFO] oc: Running in OpenShift mode
[INFO] oc: Running generator java-exec
[INFO] oc: java-exec: Using Docker image quay.io/jkube/jkube-java-binary-s2i:0.0.8 as base / builder
[INFO] oc: [fabric8-securitycontextconstraints:latest] "java-exec": Created docker source tar /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/docker/fabric8-securitycontextconstraints/latest/tmp/docker-build.tar       
[INFO] oc: Updating BuildServiceConfig fabric8-securitycontextconstraints-s2i for Source strategy
[INFO] oc: Adding to ImageStream fabric8-securitycontextconstraints
[INFO] oc: Starting Build fabric8-securitycontextconstraints-s2i
[INFO] oc: Waiting for build fabric8-securitycontextconstraints-s2i-2 to complete...
[INFO] oc: Using quay.io/jkube/jkube-java-binary-s2i:0.0.8 as the s2i builder image
[INFO] oc: INFO S2I source build with plain binaries detected
[INFO] oc: INFO S2I binary build from fabric8-maven-plugin detected
[INFO] oc: INFO Copying binaries from /tmp/src/deployments to /deployments ...
[INFO] oc: fabric8-securitycontextconstraints-1.0-SNAPSHOT-jar-with-dependencies.jar
[INFO] oc: INFO Copying deployments from deployments to /deployments...
[INFO] oc: '/tmp/src/deployments/fabric8-securitycontextconstraints-1.0-SNAPSHOT-jar-with-dependencies.jar' -> '/deployments/fabric8-securitycontextconstraints-1.0-SNAPSHOT-jar-with-dependencies.jar'                                     
[INFO] oc: INFO Cleaning up source directory (/tmp/src)
[INFO] oc: Pushing image 172.30.1.1:5000/myproject/fabric8-securitycontextconstraints:latest ...
[INFO] oc: Pushed 3/4 layers, 86% complete
[INFO] oc: Pushed 4/4 layers, 100% complete
[INFO] oc: Push successful
[INFO] oc: Build fabric8-securitycontextconstraints-s2i-2 in status Complete
[INFO] oc: Found tag on ImageStream fabric8-securitycontextconstraints tag: sha256:5f158078184499a382b0f89f30200097c0a2bd812ebe693b0a9237c408351a67                                                                                         
[INFO] oc: ImageStream fabric8-securitycontextconstraints written to /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/fabric8-securitycontextconstraints-is.yml                                                            
[INFO] 
[INFO] --- maven-install-plugin:2.4:install (default-install) @ fabric8-securitycontextconstraints ---
[INFO] Installing /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/fabric8-securitycontextconstraints-1.0-SNAPSHOT.jar to /home/rohaan/.m2/repository/org/example/fabric8-securitycontextconstraints/1.0-SNAPSHOT/fabric8-securitycontextconstraints-1.0-SNAPSHOT.jar
[INFO] Installing /home/rohaan/work/repos/fabric8-securitycontextconstraints/pom.xml to /home/rohaan/.m2/repository/org/example/fabric8-securitycontextconstraints/1.0-SNAPSHOT/fabric8-securitycontextconstraints-1.0-SNAPSHOT.pom
[INFO] Installing /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/classes/META-INF/jkube/openshift.yml to /home/rohaan/.m2/repository/org/example/fabric8-securitycontextconstraints/1.0-SNAPSHOT/fabric8-securitycontextconstraints-1.0-SNAPSHOT-openshift.yml
[INFO] Installing /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/fabric8-securitycontextconstraints-1.0-SNAPSHOT-jar-with-dependencies.jar to /home/rohaan/.m2/repository/org/example/fabric8-securitycontextconstraints/1.0-SNAPSHOT/fabric8-securitycontextconstraints-1.0-SNAPSHOT-jar-with-dependencies.jar
[INFO] Installing /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/fabric8-securitycontextconstraints-is.yml to /home/rohaan/.m2/repository/org/example/fabric8-securitycontextconstraints/1.0-SNAPSHOT/fabric8-securitycontextconstraints-1.0-SNAPSHOT-is.yml
[INFO] 
[INFO] <<< openshift-maven-plugin:1.0.2:deploy (default-cli) < install @ fabric8-securitycontextconstraints <<<
[INFO] 
[INFO] 
[INFO] --- openshift-maven-plugin:1.0.2:deploy (default-cli) @ fabric8-securitycontextconstraints ---
[INFO] oc: Using OpenShift at https://192.168.42.83:8443/ in namespace myproject with manifest /home/rohaan/work/repos/fabric8-securitycontextconstraints/target/classes/META-INF/jkube/openshift.yml                                       
[INFO] oc: OpenShift platform detected
[INFO] oc: Using project: myproject
[INFO] oc: Creating a ServiceAccount from openshift.yml namespace myproject name build-robot
[INFO] oc: Created ServiceAccount: target/jkube/applyJson/myproject/serviceaccount-build-robot.json
[INFO] oc: Creating a Service from openshift.yml namespace myproject name fabric8-securitycontextconstraints
[INFO] oc: Created Service: target/jkube/applyJson/myproject/service-fabric8-securitycontextconstraints.json
[INFO] oc: Creating a DeploymentConfig from openshift.yml namespace myproject name fabric8-securitycontextconstraints
[INFO] oc: Created DeploymentConfig: target/jkube/applyJson/myproject/deploymentconfig-fabric8-securitycontextconstraints.json                                                                                                              
[INFO] oc: Creating Route myproject:fabric8-securitycontextconstraints host: null
[INFO] oc: HINT: Use the command `oc get pods -w` to watch your pods start up
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  29.430 s
[INFO] Finished at: 2020-12-20T21:04:59+05:30
[INFO] ------------------------------------------------------------------------
```

Check created Pods:
```
fabric8-securitycontextconstraints : $ oc get pods
NAME                                             READY     STATUS      RESTARTS   AGE
fabric8-securitycontextconstraints-1-xxjsv       0/1       Completed   1          21s
fabric8-securitycontextconstraints-s2i-2-build   0/1       Completed   0          35s
```

Check logs for application pod:
```
fabric8-securitycontextconstraints : $ oc logs pod/fabric8-securitycontextconstraints-1-xxjsv
Starting the Java application using /opt/jboss/container/java/run/run-java.sh ...
INFO exec  java -javaagent:/usr/share/java/jolokia-jvm-agent/jolokia-jvm.jar=config=/opt/jboss/container/jolokia/etc/jolokia.properties -javaagent:/usr/share/java/prometheus-jmx-exporter/jmx_prometheus_javaagent.jar=9779:/opt/jboss/container/prometheus/etc/jmx-exporter-config.yaml -XX:+UseParallelOldGC -XX:MinHeapFreeRatio=10 -XX:MaxHeapFreeRatio=20 -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -XX:MaxMetaspaceSize=100m -XX:+ExitOnOutOfMemoryError -cp "." -jar /deployments/fabric8-securitycontextconstraints-1.0-SNAPSHOT-jar-with-dependencies.jar  
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.jolokia.util.ClassUtil (file:/usr/share/java/jolokia-jvm-agent/jolokia-jvm.jar) to constructor sun.security.x509.X500Name(java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String)
WARNING: Please consider reporting this to the maintainers of org.jolokia.util.ClassUtil
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
[main] DEBUG io.fabric8.kubernetes.client.Config - Trying to configure client from Kubernetes config...
[main] DEBUG io.fabric8.kubernetes.client.Config - Did not find Kubernetes config at: [/home/jboss/.kube/config]. Ignoring.
[main] DEBUG io.fabric8.kubernetes.client.Config - Trying to configure client from service account...
[main] DEBUG io.fabric8.kubernetes.client.Config - Found service account host and port: 172.30.0.1:443
[main] DEBUG io.fabric8.kubernetes.client.Config - Found service account ca cert at: [/var/run/secrets/kubernetes.io/serviceaccount/ca.crt].
[main] DEBUG io.fabric8.kubernetes.client.Config - Found service account token at: [/var/run/secrets/kubernetes.io/serviceaccount/token].
[main] DEBUG io.fabric8.kubernetes.client.Config - Trying to configure client namespace from Kubernetes service account namespace path...
[main] DEBUG io.fabric8.kubernetes.client.Config - Found service account namespace at: [/var/run/secrets/kubernetes.io/serviceaccount/namespace].
[main] INFO io.fabric8.test.ApplySCC - Listing current SecurityContextConstraint:
I> No access restrictor found, access to any MBean is allowed
Jolokia: Agent started with URL https://172.17.0.10:8778/jolokia/
[main] INFO io.fabric8.test.ApplySCC - anyuid
[main] INFO io.fabric8.test.ApplySCC - hostaccess
[main] INFO io.fabric8.test.ApplySCC - hostmount-anyuid
[main] INFO io.fabric8.test.ApplySCC - hostnetwork
[main] INFO io.fabric8.test.ApplySCC - nonroot
[main] INFO io.fabric8.test.ApplySCC - privileged
[main] INFO io.fabric8.test.ApplySCC - restricted
[main] INFO io.fabric8.test.ApplySCC - scc-test2
[main] INFO io.fabric8.test.ApplySCC - --------------------------------------
[main] INFO io.fabric8.test.ApplySCC - Loading SecurityContextConstraint from YAML manifest
[main] INFO io.fabric8.test.ApplySCC - Applying SecurityContextConstraint to OpenShift Cluster
[main] INFO io.fabric8.test.ApplySCC - Done!
```
