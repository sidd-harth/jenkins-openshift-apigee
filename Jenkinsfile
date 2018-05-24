def mvn = "mvn -s nexusconfigurations/nexus.xml"

pipeline {
 agent any

 stages {
  stage('Check Parameters') {
   steps {
    echo "Application Name - ${APP_NAME}"
    echo "Project Name - ${PROJECT_NAME}"
    echo "Master Host - ${MASTER_URL}"
   }
  }

  // Using Maven build the war file
  // Do not run tests in this step 
  stage('Build Artifact') {
   steps {
    withMaven(maven: 'apache-maven-3.3.9') {
     bat "${mvn} clean install -DskipTests=true"

     archive 'target/*.jar'
    }
   }
  }

  // Using Maven run the unit tests
  stage('Unit Tests') {
   steps {
    withMaven(maven: 'apache-maven-3.3.9') {
     bat "${mvn} test"
    }
   }
  }

  // Using Maven call SonarQube for Code Analysis
  stage('Sonar Code Analysis') {
   steps {
    withMaven(maven: 'apache-maven-3.3.9') {
     bat "${mvn} sonar:sonar -Dsonar.host.url=http://localhost:9000   -Dsonar.login=aab02659e091858dfd99ddace56d44c604390a52"
    }
   }
  }

  // Publish the latest war file to Nexus. This needs to go into <nexusurl>/repository/releases.
  stage('Publish to Nexus Repository') {
   steps {
    withMaven(maven: 'apache-maven-3.3.9') {
     bat "${mvn} deploy -DskipTests=true"
    }
   }
  }

  stage('Deploy on Openshift?') {
   steps {
    timeout(time: 2, unit: 'DAYS') {
     input message: 'Do you want to Approve?'
    }
   }
  }
  stage('Openshift New Build') {
   steps {
    sh 'oc login ${MASTER_URL} --token=${OAUTH_TOKEN} --insecure-skip-tls-verify'

    sh 'oc project ${PROJECT_NAME}'

   sh 'oc delete all --all'
     // clean up. keep the imagestream
     //sh 'oc delete bc,dc,svc,route -l app=${APP_NAME} -n ${DEV_NAME}'

    // create build. override the exit code since it complains about exising imagestream
   // sh "oc new-build --name=${APP_NAME} --image-stream=redhat-openjdk18-openshift --binary=true --labels=app=${APP_NAME} -n ${DEV_NAME} || true"
	              

   //************ below cmd works perfectly fine for Openshift AWS(office) & Openshift Origin(personal) insatnces 
   //sh 'oc new-build --name=${APP_NAME} redhat-openjdk18-openshift --binary=true'
   
   //below cmd is specially for Openshift online which has multiple image streams & hence need to use the tag
	sh 'oc new-build --name=${APP_NAME} --image-stream="openshift/redhat-openjdk18-openshift:1.1" --binary=true'   
	   
   }
  }

  stage('Openshift Start Build') {
   steps {
    //sh "pwd" 
    //sh " curl -O -X GET -u admin:admin123 http://localhost:8081/repository/snapshot/com/openshift/test/openshift-jenkins/0.0.1-SNAPSHOT/openshift-jenkins-0.0.1-20180214.210246-15.jar "
    sh "rm -rf oc-build && mkdir -p oc-build/deployments"
   // sh "cp ./openshift-jenkins-0.0.1-20180214.210246-15.jar oc-build/deployments/ROOT.jar"
	sh "cp target/openshift-jenkins-0.0.1-SNAPSHOT.jar oc-build/deployments/ROOT.jar"

    sh 'oc start-build ${APP_NAME} --from-dir=oc-build --wait=true  --follow'
   }
  }
  stage('Deploy in Development') {
   steps {
    sh 'oc new-app ${APP_NAME}'
    sh 'oc expose svc/${APP_NAME}'
    //sh 'sleep 60s'
   }
  }
  
  /*
  stage('Integration Tests') {
   steps {
    parallel(
     "Status Code": {
      // sh 'sleep 20s'
      sh "curl -I -s -L http://${APP_NAME}-${DEV_NAME}.192.168.99.100.nip.io/check | grep 200"
     },
     "Content String": {
      // sh 'sleep 20s'
      sh "curl -s http://${APP_NAME}-${DEV_NAME}.192.168.99.100.nip.io/check | grep 'Yeah, This service is deployed & it is running!!!!!!!!!!!!!!'"
     }
    )
   }
  }
  stage('Promote to Production?') {
   steps {
    timeout(time: 2, unit: 'DAYS') {
     input message: "Promote to Production?", ok: "Promote"
    }
   }
  }

  stage('Deploy in Prodiuction') {
   steps {
    // tag for stage
    sh "oc tag ${DEV_NAME}/${APP_NAME}:latest ${PROD_NAME}/${APP_NAME}:${env.BUILD_ID}"
     // clean up. keep the imagestream
    sh 'oc delete bc,dc,svc,route -l app=${APP_NAME} -n ${PROD_NAME}'
     // deploy stage image
    sh "oc new-app ${APP_NAME}:${env.BUILD_ID} -n ${PROD_NAME}"
    sh 'oc expose svc/${APP_NAME} -n ${PROD_NAME}'
   }
  }

  stage('Scaling Application') {
   steps {
    sh ' oc scale --replicas=${SCALE_APP} dc ${APP_NAME} -n ${DEV_NAME}'
   }
  }
*/
 }
}
