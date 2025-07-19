pipeline {
    agent any

    tools {
        maven 'Maven_3.9.9'
        jdk 'JDK_21.0.7'
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
                echo 'Running tests via TestNG suite file...'
                // Runs testng.xml inside ECommerceSystemOriginal (Windows-friendly)
                bat 'mvn clean test -DsuiteXmlFile=ECommerceSystemOriginal/testng.xml'
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
                reportDir: 'ECommerceSystemOriginal/test-output',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
        }
    }
}
