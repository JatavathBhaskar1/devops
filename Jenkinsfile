pipeline {
    agent any
    tools {
        maven 'maven'  // Make sure this matches the name in Jenkins tool config
    }
    stages {
        stage('Checkout and Build') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://github.com/JatavathBhaskar1/devops']]
                )
                bat 'mvn clean install'
            }
        }
        stage('Build Docker Image') {
            steps {
                bat 'docker build -t musifyapi:v1 .'
            }
        }
        stage('Docker push Image to DockerHub') {
            steps {
                 withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'DOCKER_PWD')]) {
                 bat """
                   docker login -u jatavathbhaskar139 -p %DOCKER_PWD%
                   docker tag musifyapi:v1 jatavathbhaskar139/musifyapi:v1
                   docker push jatavathbhaskar139/musifyapi:v1
                   """
        }
    }
}

    }
}
