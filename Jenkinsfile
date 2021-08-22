def projectName = 'app'
def version = "latest"
def dockerImageTag_app = "${projectName}:${version}"
def mysqlName = 'mysql'
def dockerImageTag_mysql = "${mysqlName}:${version}"

pipeline {
  agent any
  stages {


    stage('Test') {
      agent {
        docker {
          image 'maven:3.8.1'
        }
      }
      steps {
        sh 'mvn test' 
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml' 
        }
      }
    }
    
    stage('Deploy') {
      agent any
      steps {
        sh "docker-compose down || echo \"application not running\""
        sh "docker build -f Dockerfile-mysql -t ${dockerImageTag_mysql} ."
        sh "docker build -f Dockerfile-app -t ${dockerImageTag_app} ."
        sh "docker-compose up -d"
      }
    } 

    stage('Deploy Container to OpenShift') {
      agent any
      steps {
        sh "oc login https://devopsapac34.conygre.com:8443 --username admin --password admin --insecure-skip-tls-verify=true"
        sh "oc project ${projectName} || oc new-project ${projectName}"
        sh "oc delete all --selector app=${projectName} || echo 'Unable to delete all previous openshift resources'"
        sh "oc delete all --selector app=${mysqlName} || echo 'Unable to delete all previous openshift resources'"
        sh "oc new-app ${dockerImageTag_mysql}"
        sh "oc new-app ${dockerImageTag_app}"
        sh "oc expose svc/${projectName}"
        sh "oc status"
      }
    }
  }
}
