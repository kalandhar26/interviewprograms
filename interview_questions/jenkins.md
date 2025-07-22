# Step-by-Step Guide to Building a CI/CD Pipeline with Jenkins

## Step 1: Set Up Jenkins

### Install Jenkins

- Launch an EC2 instance, install Jenkins, and expose port 8080.
- Secure Jenkins with a reverse proxy (e.g., Nginx) and SSL if public.
- Download and run Jenkins: java -jar jenkins.war or use a Docker container: docker run -p 8080:8080 jenkins/jenkins:
  lts.

### Access Jenkins

- Open http://<your-server>:8080, follow the setup wizard, and install recommended plugins

### Install Required Plugins:

- Go to Manage Jenkins > Manage Plugins and install:
- Docker Pipeline: For building and pushing Docker images.
- Kubernetes: For deploying to Kubernetes clusters.
- AWS Credentials: For AWS integration.
- Git: For source control integration.
- Pipeline: For defining pipelines in Jenkins files.

## Step 2: Configure Jenkins for Your Tools

### Set Up Credentials:

- Go to Manage Jenkins > Manage Credentials.
- Add credentials for:
- Git: SSH key or username/password for your Git repo.
- Docker Hub/ECR: Username and password or AWS credentials for ECR.
- AWS: Access Key ID and Secret Access Key for AWS services.
- Kubernetes: Kubeconfig file or token for your EKS cluster.

### Install Tools:

- Install Maven or Gradle (for building Spring Boot apps) on the Jenkins server or use a Docker agent.
- Install Docker on the Jenkins server if building Docker images.
- Install kubectl for Kubernetes deployments (optional if using a plugin).

## Step 3: Create a Jenkinsfile

- Create a **Jenkinsfile** in the root of your Spring Boot microservice’s Git repository. This file defines your CI/CD
  pipeline using Groovy.

```groovy
pipeline {
    agent {
        docker {
            image 'maven:3.8.6-openjdk-17' // Use a Maven Docker image for building
            args '-v $HOME/.m2:/root/.m2' // Mount Maven cache
        }
    }
    environment {
        DOCKER_REGISTRY = 'your-docker-registry' // e.g., Docker Hub username or AWS ECR URL
        AWS_REGION = 'us-east-1' // Your AWS region
        KUBE_CLUSTER = 'your-eks-cluster-name' // Your EKS cluster name
        APP_NAME = 'your-springboot-app-name'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-repo.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests' // Build Spring Boot JAR
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test' // Run unit tests
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    def dockerImage = docker.build("${DOCKER_REGISTRY}/${APP_NAME}:${env.BUILD_ID}")
                    docker.withRegistry('https://' + DOCKER_REGISTRY, 'docker-credentials-id') {
                        dockerImage.push()
                        dockerImage.push('latest')
                    }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'kube-credentials-id', clusterName: KUBE_CLUSTER]) {
                    sh """
                    kubectl apply -f k8s/deployment.yaml
                    kubectl apply -f k8s/service.yaml
                    """
                }
            }
        }
        stage('Verify Deployment') {
            steps {
                withKubeConfig([credentialsId: 'kube-credentials-id', clusterName: KUBE_CLUSTER]) {
                    sh 'kubectl rollout status deployment/${APP_NAME}'
                }
            }
        }
    }
    post {
        always {
            cleanWs() // Clean workspace after pipeline execution
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
```

#### Explanation of the Jenkinsfile:

- Agent: Uses a Maven Docker image to build the Spring Boot app, ensuring consistent environments.
- Environment: Defines variables like Docker registry, AWS region, and app name.
- Stages:
- Checkout: Pulls code from your Git repository.
- Build: Compiles the Spring Boot app using Maven.
- Test: Runs unit tests.
- Build Docker Image: Builds and pushes a Docker image to your registry.
- Deploy to Kubernetes: Applies Kubernetes manifests to deploy the app to EKS.
- Verify Deployment: Checks if the deployment is successful.
- Post: Cleans up and logs the pipeline status.

## Step 4: Create Kubernetes Manifests

### In your repository, create a k8s/ directory with the following files:

#### k8s/deployment.yaml:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: your-springboot-app-name
  namespace: default
spec:
  replicas: 2
  selector:
    matchLabels:
      app: your-springboot-app-name
  template:
    metadata:
      labels:
        app: your-springboot-app-name
    spec:
      containers:
        - name: your-springboot-app-name
          image: your-docker-registry/your-springboot-app-name:latest
          ports:
            - containerPort: 8080
          env:
            - name: SPRING_KAFKA_BOOTSTRAP_SERVERS
              value: "your-kafka-broker:9092" # Kafka broker address
```

#### k8s/service.yaml:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: your-springboot-app-name
  namespace: default
spec:
  selector:
    app: your-springboot-app-name
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8080
  type: LoadBalancer
```

**Notes:**

- Replace your-docker-registry, your-springboot-app-name, and your-kafka-broker with your actual values.
- The env section sets Kafka bootstrap servers for your Spring Boot app to connect to Kafka.
- The LoadBalancer service type exposes the app externally via an AWS ELB.

## Step 5: Configure Your Spring Boot App

### Ensure your Spring Boot microservice is configured for:

- Kafka: Use spring-kafka dependency and configure application.yml

```yaml
spring:
  kafka:
    bootstrap-servers: your-kafka-broker:9092
    consumer:
      group-id: your-group-id
```

- Docker: Create a Dockerfile in the project root:

```yaml
FROM openjdk:17-jdk-slim
COPY target/your-app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

- Kubernetes: Ensure the app listens on port 8080 and is containerized properly.

## Step 6: Set Up the Pipeline in Jenkins

### Create a New Pipeline:

- Go to Jenkins Dashboard > New Item.
- Select Pipeline, name it (e.g., SpringBoot-CICD), and click OK.
- In the pipeline configuration, select Pipeline script from SCM.
- Set SCM to Git, provide your repository URL, and specify the branch (e.g., main).
- Set the Jenkinsfile path (default: Jenkinsfile).

### Trigger the Pipeline:

- Save and click Build Now to run the pipeline manually.
- Configure webhooks in your Git repository (e.g., GitHub) to trigger builds on code pushes.

## Step 7: Integrate with AWS

### AWS ECR for Docker Images:

- Create an ECR repository in AWS.
- Update the DOCKER_REGISTRY in the Jenkinsfile to your ECR URL (e.g., <account-id>.dkr.ecr.<region>.amazonaws.com).
- Ensure Jenkins has AWS credentials to push to ECR (use the AWS Credentials plugin).

### Amazon EKS for Deployment:

- Set up an EKS cluster using the AWS CLI or console.
- Configure kubeconfig for Jenkins to access the cluster (store in Jenkins credentials).
- Ensure the Kubernetes plugin is installed and configured.

## Step 8: Monitor and Debug

- View Pipeline Logs: Check the Console Output in Jenkins for each build to debug issues.
- Test Kafka Integration: Verify that your Spring Boot app connects to Kafka by checking logs in Kubernetes (kubectl
  logs).
- Monitor Kubernetes: Use kubectl get pods and kubectl describe service to ensure the app is running and accessible.
- AWS Monitoring: Use CloudWatch to monitor logs and metrics for your EKS cluster and Spring Boot app.

## Step 9: Enhance the Pipeline

### Add Code Quality Checks:

- Integrate SonarQube for static code analysis:

```groovy
stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('SonarQube') {
            sh 'mvn sonar:sonar'
        }
    }
}
```

### Add Integration Tests:

- Run integration tests in a separate stage using a test Kafka broker or Testcontainers.

### Blue-Green Deployment:

- Modify the Kubernetes deployment stage to support blue-green or canary deployments for zero-downtime updates.

### Notifications:

- Add Slack or email notifications in the post section:

```groovy
post {
    success {
        slackSend(channel: '#ci-cd', message: "Build ${env.BUILD_ID} succeeded!")
    }
}
```

## Step 10: Learn Groovy for Advanced Pipelines

**Groovy is central to Jenkins pipelines. Here’s a quick primer:**

- **Syntax:** Groovy is similar to Java but with concise syntax (e.g., no semicolons, optional types).
- **Pipeline DSL:** Jenkins provides a Domain-Specific Language (DSL) in Groovy for pipelines. Common constructs:
- pipeline {}: Defines the pipeline structure.
- stage {}: Groups steps (e.g., build, test).
- steps {}: Contains executable commands (e.g., sh for shell commands).
- script {}: Allows complex Groovy logic.

#### Examples:

##### Conditional logic:

```groovy
if (env.BRANCH_NAME == 'main') {
    echo 'Deploying to production'
} else {
    echo 'Deploying to staging'
}
```

##### Loops:

```groovy
for (int i = 0; i < 3; i++) {
    echo "Attempt ${i}"
}
```

# Pipeline as Code Organization:

your-microservice/
├── Jenkinsfile
├── docker/
│   ├── Dockerfile
│   └── docker-compose.test.yml
├── k8s/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── configmap.yaml
├── helm-chart/
│   ├── Chart.yaml
│   ├── values.yaml
│   └── templates/
└── jenkins/
├── jenkins-pod.yaml
└── shared-library/


## What is Jenkins?
Jenkins is an open-source automation server that enables continuous integration and continuous deployment (CI/CD). For microservices, it's particularly valuable because it can orchestrate complex deployment workflows across multiple services, environments, and cloud platforms.
### Key Components:
- **Master Node:** Controls the pipeline execution and UI
- **Agent Nodes:** Execute the actual build/deployment tasks
- **Pipelines:** Define your CI/CD workflow as code
- **Plugins:** Extend functionality (Docker, Kubernetes, AWS, SonarQube, etc.)

### Jenkins pipelines use Groovy DSL (Domain Specific Language). Here's what you need to know:

```groovy
// Variables
def serviceName = 'user-service'
def dockerImage = ''

// Collections
def environments = ['dev', 'staging', 'prod']
def services = [
        'user-service': '8080',
        'order-service': '8081',
        'notification-service': '8082'
]

// Control structures
if (env.BRANCH_NAME == 'main') {
  // Deploy to production
}

environments.each { environment ->
  // Deploy to each environment
}
```

## Step 1: Jenkins Setup and Configuration
### Install Required Plugins:
- Pipeline
- Docker Pipeline
- Kubernetes
- AWS Steps
- SonarQube Scanner
- JUnit
- Jacoco

### Configure Global Tools:
- JDK 17/21
- Maven/Gradle
- Docker
- kubectl

## Step 2: Basic Pipeline Structure
- Create a Jenkinsfile in your Spring Boot microservice root:
```groovy
pipeline {
    agent any
    
    tools {
        jdk 'JDK-17'
        maven 'Maven-3.8'
    }
    
    environment {
        DOCKER_REGISTRY = 'your-ecr-registry'
        SERVICE_NAME = 'user-service'
        KUBECONFIG = credentials('kubeconfig')
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build & Test') {
            steps {
                sh 'mvn clean compile test'
            }
        }
        
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        
        stage('Docker Build') {
            steps {
                script {
                    dockerImage = docker.build("${DOCKER_REGISTRY}/${SERVICE_NAME}:${BUILD_NUMBER}")
                }
            }
        }
        
        stage('Deploy') {
            steps {
                // Deployment steps
            }
        }
    }
}
```

## Step 3: Advanced Microservices Pipeline
- Here's a comprehensive pipeline for Spring Boot microservices:

```groovy
pipeline {
    agent {
        kubernetes {
            yamlFile 'jenkins-pod.yaml'
        }
    }
    
    environment {
        DOCKER_REGISTRY = credentials('docker-registry-url')
        DOCKER_CREDENTIALS = credentials('docker-registry-credentials')
        AWS_DEFAULT_REGION = 'us-west-2'
        KUBECONFIG = credentials('kubernetes-config')
        SONAR_TOKEN = credentials('sonar-token')
        KAFKA_BOOTSTRAP_SERVERS = 'kafka-cluster:9092'
    }
    
    stages {
        stage('Initialize') {
            steps {
                script {
                    env.SERVICE_NAME = sh(script: "echo '${JOB_NAME}' | cut -d'/' -f1", returnStdout: true).trim()
                    env.GIT_COMMIT_SHORT = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                    env.IMAGE_TAG = "${BUILD_NUMBER}-${GIT_COMMIT_SHORT}"
                }
            }
        }
        
        stage('Code Quality & Security') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        container('maven') {
                            sh '''
                                mvn clean test jacoco:report
                            '''
                        }
                    }
                    post {
                        always {
                            publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                            publishCoverage adapters: [jacoco('target/site/jacoco/jacoco.xml')]
                        }
                    }
                }
                
                stage('SonarQube Analysis') {
                    steps {
                        container('maven') {
                            withSonarQubeEnv('SonarQube') {
                                sh '''
                                    mvn sonar:sonar \
                                    -Dsonar.projectKey=${SERVICE_NAME} \
                                    -Dsonar.host.url=${SONAR_HOST_URL} \
                                    -Dsonar.login=${SONAR_TOKEN}
                                '''
                            }
                        }
                    }
                }
            }
        }
        
        stage('Build Application') {
            steps {
                container('maven') {
                    sh '''
                        mvn clean package -DskipTests \
                        -Dspring.profiles.active=docker
                    '''
                }
            }
        }
        
        stage('Integration Tests') {
            steps {
                container('maven') {
                    sh '''
                        # Start test containers (Kafka, Database, etc.)
                        mvn verify -P integration-tests \
                        -Dkafka.bootstrap-servers=${KAFKA_BOOTSTRAP_SERVERS}
                    '''
                }
            }
        }
        
        stage('Docker Build & Push') {
            steps {
                container('docker') {
                    script {
                        docker.withRegistry("https://${DOCKER_REGISTRY}", 'docker-registry-credentials') {
                            def image = docker.build(
                                "${DOCKER_REGISTRY}/${SERVICE_NAME}:${IMAGE_TAG}",
                                "--build-arg JAR_FILE=target/${SERVICE_NAME}-*.jar ."
                            )
                            image.push()
                            image.push('latest')
                        }
                    }
                }
            }
        }
        
        stage('Deploy to Environments') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                    branch 'release/*'
                }
            }
            steps {
                script {
                    def deploymentEnvironment = getBranchEnvironment(env.BRANCH_NAME)
                    deployToKubernetes(deploymentEnvironment)
                }
            }
        }
        
        stage('Post-Deployment Tests') {
            steps {
                script {
                    runHealthChecks()
                    runSmokeTests()
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        failure {
            slackSend(
                channel: '#deployments',
                color: 'danger',
                message: "❌ Pipeline failed: ${SERVICE_NAME} - ${BUILD_URL}"
            )
        }
        success {
            slackSend(
                channel: '#deployments',
                color: 'good',
                message: "✅ Pipeline succeeded: ${SERVICE_NAME} deployed to ${deploymentEnvironment}"
            )
        }
    }
}

// Helper functions
def getBranchEnvironment(branchName) {
    switch(branchName) {
        case 'main': return 'production'
        case 'develop': return 'staging'
        case ~/release\/.*/ : return 'pre-production'
        default: return 'development'
    }
}

def deployToKubernetes(environment) {
    sh """
        helm upgrade --install ${SERVICE_NAME} ./helm-chart \
        --namespace ${environment} \
        --set image.repository=${DOCKER_REGISTRY}/${SERVICE_NAME} \
        --set image.tag=${IMAGE_TAG} \
        --set environment=${environment} \
        --values ./helm-chart/values-${environment}.yaml
    """
}

def runHealthChecks() {
    sh """
        kubectl wait --for=condition=ready pod \
        -l app=${SERVICE_NAME} \
        --timeout=300s \
        -n ${deploymentEnvironment}
    """
}

def runSmokeTests() {
    sh """
        mvn test -Dtest=SmokeTestSuite \
        -Dservice.url=https://${SERVICE_NAME}-${deploymentEnvironment}.yourcompany.com
    """
}
```

## Step 4: Microservices-Specific Configurations
- Multi-Branch Pipeline for Multiple Services:

```groovy
// In your organization's shared library
@Library('your-shared-library') _

microservicePipeline {
    serviceName = 'user-service'
    javaVersion = '17'
    springProfiles = ['docker', 'kafka']
    dependencies = [
        'postgres:13',
        'redis:6',
        'kafka:latest'
    ]
    environments = [
        dev: [
            replicas: 1,
            resources: [cpu: '100m', memory: '256Mi']
        ],
        staging: [
            replicas: 2,
            resources: [cpu: '200m', memory: '512Mi']
        ],
        production: [
            replicas: 3,
            resources: [cpu: '500m', memory: '1Gi']
        ]
    ]
}
```
## Step 5: AWS Integration
### ECR Integration:

```groovy
stage('Push to ECR') {
    steps {
        script {
            sh """
                aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | \
                docker login --username AWS --password-stdin ${DOCKER_REGISTRY}
                
                docker push ${DOCKER_REGISTRY}/${SERVICE_NAME}:${IMAGE_TAG}
            """
        }
    }
}
```
### EKS Deployment

```groovy 
stage('Deploy to EKS') {
    steps {
        script {
            sh """
                aws eks update-kubeconfig --region ${AWS_DEFAULT_REGION} --name ${EKS_CLUSTER_NAME}
                kubectl apply -f k8s/deployment.yaml
                kubectl set image deployment/${SERVICE_NAME} \
                ${SERVICE_NAME}=${DOCKER_REGISTRY}/${SERVICE_NAME}:${IMAGE_TAG}
            """
        }
    }
}
```

##  Shared Pipeline Library:
- Create reusable components for common microservice patterns:
```groovy
// vars/microserviceBuild.groovy
def call(Map config) {
  pipeline {
    agent {
      kubernetes {
        yamlFile 'jenkins/jenkins-pod.yaml'
      }
    }

    stages {
      stage('Build') {
        steps {
          buildSpringBootApp(config)
        }
      }

      stage('Test') {
        parallel {
          stage('Unit Tests') {
            steps {
              runUnitTests(config)
            }
          }
          stage('Integration Tests') {
            steps {
              runIntegrationTests(config)
            }
          }
        }
      }

      stage('Deploy') {
        steps {
          deployToKubernetes(config)
        }
      }
    }
  }
}
```