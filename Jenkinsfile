pipeline {
    agent any

    tools {
        maven 'Maven_3.9'
        jdk 'JDK_21'
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
        
      	stage('Debug Report Generation') {
        steps {
            	echo 'Listing all files to locate ExtentReport.html...'
            	bat 'dir /s /b'
        	}
    	}

    }

    post {
        always {
            echo 'Publishing Extent Report...'
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
        }
    }
}
