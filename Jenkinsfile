def projectName = 'fundmanager'
def version = "0.0.${currentBuild.number}"
def dockerImageTag = "emp/app"

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
    
    stage('Docker Build') {
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
        sh "docker build -f Dockerfile-mysql -t emps/mysql ."
        sh "docker build -f Dockerfile-app -t emps/app ."
        sh "docker run --name mysql -d -p 3306:3306 emps/mysql"
        sh "sleep 10"
        sh "docker run --name app -d --link mysql:mysql emps/app"
      }
    } 

    stage('Deploy Container To Openshift') {
      agent any
      steps {
        sh "oc login https://devopsapac34.conygre.com:8443 --username admin --password admin --insecure-skip-tls-verify=true"
        sh "oc project ${projectName} || oc new-project ${projectName}"
        sh "oc delete all --selector app=${projectName} || echo 'Unable to delete all previous openshift resources'"
        sh "oc new-app ${dockerImageTag}"
        sh "oc expose svc/${projectName}"
      }
    }
  }
}
