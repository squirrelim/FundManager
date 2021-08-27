def dockerHubName = "newbieesun"
def projectName = "manager"
def appName = "${projectName}app"
def version = "latest"
def dockerImageTag_app = "${dockerHubName}/${appName}:${version}"
def mysqlName = "${projectName}mysql"
def dockerImageTag_mysql = "${dockerHubName}/${mysqlName}:${version}"

pipeline {
  agent any
  stages {
    stage('Build') {
      agent any
      steps {
        sh "docker build -f Dockerfile-mysql -t ${dockerImageTag_mysql} ."
        sh "docker build -f Dockerfile-app-local -t ${dockerImageTag_app} ."
      }
    }

    stage('Test') {
      agent {
        docker {
          image 'maven:3.8.1'
        }
      }
      steps {
        sh "echo \"start testing\""
        //sh 'mvn test'
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
        sh "docker-compose up"
      }
    }
  }
}
