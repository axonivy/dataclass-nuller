pipeline {
  agent {
    dockerfile true
  }

  options {
    buildDiscarder(logRotator(numToKeepStr: '30', artifactNumToKeepStr: '5'))
  }

  triggers {
    cron '@midnight'
  }

  stages {
    stage('build') {
      steps {
          script {
            maven cmd:'-f DataclassNuller/pom.xml clean install'
            archiveArtifacts 'DataclassNuller/build/dataclass-nuller/target/*.jar'
            junit '**/target/surefire-reports/**/*.xml' 
          }
      }
    }
  }
}
