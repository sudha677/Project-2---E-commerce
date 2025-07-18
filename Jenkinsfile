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
                echo 'Git checkout completed'
            }
        }

        stage('Test') {
            steps {
                echo 'Running TestNG suite...'
                // Avoid breaking pipeline on test failure
                bat 'mvn clean test -DsuiteXmlFile=testng.xml || exit 0'
            }
        }
    }

    post {
        always {
            echo 'Publishing TestNG XML Results'
            junit 'test-output/testng-results.xml'

            echo 'Publishing Extent Report'
            publishHTML(target: [
            reportDir: 'test-output',
            reportFiles: 'ExtentReport.html',
            reportName: 'Extent Report',
            keepAll: true,
            alwaysLinkToLastBuild: true,
            allowMissing: true
            ])

            echo 'Sending email with Extent Report link...'
            emailext (
                subject: "Project2 - ECommerce Test Report - ${currentBuild.currentResult}",
                body: """
                <p>Build Result: <b>${currentBuild.currentResult}</b></p>
                <p><a href="${BUILD_URL}">Build Console Output</a></p>
                <p><a href="${BUILD_URL}Extent_20Report">View Extent Report</a></p>
                """,
                mimeType: 'text/html',
                to: 'automationsudha@gmail.com'
            )
        }
    }
}
