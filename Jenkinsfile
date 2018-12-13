pipeline {
  agent {
    dockerfile true
  }

  options {
    buildDiscarder(logRotator(artifactNumToKeepStr: '10', artifactNumToKeepStr: '5'))
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
          }
      }
    }
  }
}
