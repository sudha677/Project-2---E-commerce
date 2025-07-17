
pipeline {
    agent any

    tools {
        maven 'Maven_3.9' // replace with your actual Maven tool name in Jenkins
        jdk 'JDK_21'      // replace with your actual JDK tool name in Jenkins
    }

    environment {
        REPORT_DIR = 'ECommerceSystemOriginal/test-output'
    }

    stages {
        stage('Checkout') {
            steps {
                // If using Git:
                git url: 'https://github.com/sudha677/Project-2---E-commerce/tree/master.git'
                // For local Jenkinsfile, this assumes code is already in workspace
                echo 'Using existing workspace'
            }
        }

        stage('Test') {
            steps {
                dir('ECommerceSystemOriginal') {
                    echo 'Running tests via TestNG suite file...'
                    sh 'mvn clean test -DsuiteXmlFile=testng.xml'
                }
            }
        }
    }

    post {
        always {
            echo 'Publishing TestNG XML results...'
            junit '**/testng-results.xml'

            echo 'Publishing Extent Report...'
            publishHTML(target: [
                reportDir: "${env.REPORT_DIR}",
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
        }
    }
}