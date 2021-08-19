pipeline {
  agent any
  stages {
    stage('Maven Install') {
      agent {
        docker {
          image 'maven:3.8.1'
        }
      }
      steps {
        sh 'mvn clean install -D skipTests'
      }
    }
    stage('Docker Build') {
      agent any
      steps {
        sh "docker build -f Dockerfile-mysql -t emps/mysql ."
        sh "docker build -f Dockerfile-app -t emps/app ."
        sh "docker run --name mysql -d -p 3306:3306 emps/mysql"
        sh "sleep 10"
        sh "docker run --name app -d --link mysql:mysql emps/app"
      }
    } 
    stage('Test') { 
      steps {
        sh 'mvn test' 
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml' 
        }
      }
    }
    stage('Deploy Container To Openshift') {
      steps {
        sh "oc login https://localhost:8443 --username admin --password admin --insecure-skip-tls-verify=true"
        sh "oc project ${projectName} || oc new-project ${projectName}"
        sh "oc delete all --selector app=${projectName} || echo 'Unable to delete all previous openshift resources'"
        sh "oc new-app ${dockerImageTag} -l version=${version}"
        sh "oc expose svc/${projectName}"
      }
    }
  }
}
