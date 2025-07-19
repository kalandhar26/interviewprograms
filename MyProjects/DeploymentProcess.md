# Overview of the Process

- We used a modern CI/CD pipeline with
- **Jenkins** for **automation**, 
- **Docker** for **containerization**, and 
- **Kubernetes** hosted on AWS EKS for **orchestration**.
- The process ensured **consistent, reliable deployments** with zero downtime.
- We follow a bi-weekly release cycle after thorough QA and UAT. 
- My role involved **integrating CI/CD pipelines and Dockerizing microservices**, collaborating closely with the DevOps team.

# Step-by-Step Deployment Flow

## 1. Code Development and Version Control

- Developers wrote code for microservices using Java 17 and Spring Boot.
- Code was pushed to a Git repository on the main branch for ongoing development or a
  release branch for production.
- Pull Requests (PRs) were used to review and merge code, ensuring quality through peer reviews.

## 2. CI/CD Pipeline with Jenkins

### Trigger

- A commit or merge to the main branch triggered the Jenkins pipeline.
- Jenkins was configured with webhooks to detect changes in the Git repository, initiating the pipeline automatically.

### Build and Test

- Jenkins executed mvn clean package to **compile the code and generate a JAR** file using Maven.
- Unit tests, written with JUnit, were run to validate functionality (e.g., mvn test).
- Code quality checks were performed using SonarQube, analyzing metrics like code coverage and technical debt.
- If any test or quality check failed, the pipeline halted, and developers were notified via email or Slack.

### Docker Image Creation

- Upon successful tests, Jenkins built a Docker image for each microservice using a multi-stage Dockerfile.
- The image was tagged with a version or latest for development.
- Images were pushed to Amazon Elastic Container Registry (ECR) using AWS credentials for secure storage.

### Dockerization

- Each microservice had its own Dockerfile for consistent environments across dev, staging, and production.
- We used multi-stage builds to optimize image size, starting with a build stage and copying only the JAR to the runtime
  stage.
- The build stage compiled the code and generated the JAR, while the runtime stage used a slim base image for deployment.
- Images were tested locally using docker run to verify functionality before pushing to ECR.

### Kubernetes Deployment on AWS EKS

#### **Orchestration:**
- We deployed microservices to a Kubernetes cluster managed by AWS EKS. 
- EKS provided a scalable, managed environment for running Kubernetes workloads.

## 📦 Deployment Artifacts

### 🔹 Helm Charts / kubectl Manifests
- **Helm Charts** were primarily used to define:
  - Deployments
  - Services
  - Ingress rules
- Enabled parameterized and reusable configurations.
- For simpler services, **raw YAML manifests** were applied using `kubectl apply`.

### 🔹 ConfigMaps
- Stored **non-sensitive configuration data** such as:
  - Database URLs
  - Broker endpoints
  - Environment flags
- Mounted as environment variables or config files in pods.

### 🔹 Secrets
- Stored **sensitive data** such as:
  - Database credentials
  - Kafka certificates
  - API keys
- Accessed securely by pods and encrypted at rest.

---

## 📈 Scaling

### 🔸 Horizontal Pod Autoscaler (HPA)
- Configured based on:
  - CPU utilization
  - Memory usage
- Automatically scaled pods **up or down** depending on traffic volume.

---

## 🚀 Deployment Strategy

### 🔸 Rolling Updates
- Used Kubernetes `rollingUpdate` strategy.
- Replaced old pods with new ones **gradually**.
- Ensured **zero downtime** during deployments.

### 🔸 Canary Testing
- Used for **critical services** like payment processing.
- Rolled out new versions to a **small subset of users**.
- Monitored for errors or performance issues before full rollout.

### 🔸 GitOps with ArgoCD
- All Helm charts and manifests stored in a **Git repository**.
- **ArgoCD** continuously synced desired state from Git to EKS.
- Provided:
  - **Declarative deployments**
  - **Change traceability**
  - **Audit logging**

### Release Cadence

- Nightly Builds: Development environments were updated nightly for rapid iteration.
- Every 15 days, aligned with Agile sprints, features and fixes were merged into a release branch.
- The release was validated in QA and UAT environments before production deployment.
- Canary testing ensured stability before full rollout.

### Post-Deployment Monitoring

- **Tools:** We used Prometheus and Grafana for monitoring metrics (e.g., latency, error rates) and Splunk for log
  analysis.
- **My Contribution:** I monitored deployment logs in Splunk to verify the stability of the Payment Service
  post-release.

## 3. My Role and Contributions

- **While the DevOps team managed Jenkins and Kubernetes infrastructure, I played a hands-on role in:**
- **Dockerization:** Wrote and tested Dockerfiles for the Payment Service to ensure consistency across environments.
- **CI/CD Integration:** Collaborated with DevOps to integrate unit tests and Docker builds into Jenkins pipelines.
- **Testing:** Ran Docker images locally (docker run) to verify functionality before deployment.
- **Troubleshooting:** Identified a deployment issue where a misconfigured Kubernetes service caused latency in the
  Payment Service. I worked with DevOps to update the service’s port configuration, resolving the issue.
- **Manifest Reviews:** Reviewed Kubernetes YAML files to ensure correct environment variables and resource limits for
  the Payment Service.

## 4. Challenges and Learnings

- **Challenge:** Early on, we faced issues with image bloat in Docker. Switching to multi-stage builds reduced image
  size significantly.
- **Learning:** I gained hands-on experience with containerization and Kubernetes manifests, which deepened my
  understanding of cloud-native deployments.