def projectName = 'javaapp'
def version = "0.0.99"
def dockerImageTag = "${projectName}:${version}"
def mysqlName = 'mysql'
def dockerImageTag2 = "${mysqlName}:${version}"

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
    
    stage('Build') {
      agent any
      steps {
        script {
          try {
              sh "docker container kill mysql"
          } catch (err) {
              echo err.getMessage()
          }
        }
        script {
          try {
              sh "docker container rm mysql"
          } catch (err) {
              echo err.getMessage()
          }
        }
        script {
          try {
              sh "docker container kill app"
          } catch (err) {
              echo err.getMessage()
          }
        }
        script {
          try {
              sh "docker container rm app"
          } catch (err) {
              echo err.getMessage()
          }
        }
        sh "docker build -f Dockerfile-mysql -t ${dockerImageTag2} ."
        sh "docker build -f Dockerfile-app -t ${dockerImageTag} ."
        sh "docker run --name mysql -d -p 3306:3306 ${dockerImageTag2}"
        sh "sleep 10"
        sh "docker run --name app -d -p 8888:8080 --link mysql:mysql ${dockerImageTag}"
      }
    } 

    stage('Deploy') {
      agent any
      steps {
        sh "oc login https://devopsapac34.conygre.com:8443 --username admin --password admin --insecure-skip-tls-verify=true"
        sh "oc project ${projectName} || oc new-project ${projectName}"
        sh "oc delete all --selector app=${projectName} || echo 'Unable to delete all previous openshift resources'"
        sh "oc delete all --selector app=${mysqlName} || echo 'Unable to delete all previous openshift resources'"
        sh "oc new-app ${dockerImageTag2}"
        sh "oc new-app ${dockerImageTag}"
        sh "oc expose svc/${projectName}"
        sh "oc status"
      }
    }
  }
}
