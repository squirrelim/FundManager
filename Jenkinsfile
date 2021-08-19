pipeline {
  agent none
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
         sh "docker run --name app --link mysql:mysql emps/app"
      }
    }
  }
}
