pipeline {
    agent any

    tools {
        maven 'Maven_3.9'
        jdk 'JDK_21'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/sudha677/Project-2---E-commerce.git'
                echo 'Checkout Stage Completed'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests via TestNG suite file...'
                bat 'mvn clean test -DsuiteXmlFile=testng.xml'
            }
        }

        stage('Publish Reports') {
            steps {
                echo 'Publishing TestNG XML results...'
                junit '**/test-output/testng-results.xml'

                echo 'Publishing Extent Report...'
                publishHTML(target: [
                    reportDir: 'test-output',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }
}
