pipeline {
    agent any

    tools {
        maven 'Maven_3.9'
        jdk 'JDK_21'
    }

    environment {
        REPORT_DIR = 'ECommerceSystemOriginal/test-output'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/sudha677/Project-2---E-commerce.git', branch: 'master'
                echo 'Code checked out from GitHub repository.'
            }
        }

        stage('Test') {
            steps {
                dir('ECommerceSystemOriginal') {
                    echo 'Running tests via TestNG suite file...'
                    bat 'mvn clean test -DsuiteXmlFile=testng.xml'
                }
            }
        }
    }

    post {
        always {
            echo 'Publishing TestNG Results...'
            junit 'ECommerceSystemOriginal/target/surefire-reports/testng-results.xml'

            echo 'Publishing Extent Report...'
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: "${env.REPORT_DIR}",
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
        }
    }
}
