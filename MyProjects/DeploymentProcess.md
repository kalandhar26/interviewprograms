# Overview of the Process

- We used a modern CI/CD pipeline with
- **Jenkins** for **automation**,
- **Docker** for **containerization**, and
- **Kubernetes** hosted on AWS EKS for **orchestration**.
- This process ensures **consistent, reliable deployments** with zero downtime.
- We follow a bi-weekly release cycle after thorough QA and UAT.
- My role involved **integrating CI/CD pipelines and Dockerizing microservices**, collaborating closely with the DevOps
  team.

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
- The build stage compiled the code and generated the JAR, while the runtime stage used a slim base image for
  deployment.
- Images were tested locally using docker run to verify functionality before pushing to ECR.

### Kubernetes Deployment on AWS EKS

#### **Orchestration:**

- Kubernetes on EKS pulls the image from ECR and deploys it into pods, which run the microservice.
- EKS provided a scalable, managed environment for running Kubernetes workloads.

## 📦 Deployment Artifacts

### 🔹 Helm Charts (Like "Templates" for Kubernetes Deployments)

- Instead of writing the same Kubernetes YAML files repeatedly, we used Helm charts, which are like predefined templates
  for our deployments.

#### Why Helm?

- Reusable Configurations: Each microservice (e.g., order-service, payment-service) had its own Helm chart.
- Different Environments (Dev/QA/Prod): We could tweak settings (like CPU/memory) per environment using values.yaml.
- Version Control: Helm allowed us to track changes and roll back if something went wrong.

#### When We Used Raw kubectl (Without Helm)

- For simpler services (like internal tools), we sometimes just applied YAML files directly with:

```yaml
kubectl apply -f deployment.yaml
```

- But Helm made things much cleaner for complex apps.

### 🔹 ConfigMaps (For Non-Sensitive Configurations)

- Instead of hardcoding settings (like database URLs) inside our Spring Boot app, we stored them in ConfigMaps.
    - Database connection strings (jdbc:mysql://db-host:3306/orders)
    - Feature flags (enable_new_checkout = true)
    - External service URLs (payment-gateway = https://api.pay.com)

#### How It Worked?

- The ConfigMap was created once.
- The app read these values at runtime (either as environment variables or files).
- No need to rebuild Docker images when configs changed!
- Mounted as environment variables or config files in pods.

### 🔹 Secrets (For Passwords, API Keys, Certificates)

- Sensitive data (like passwords, SSL certs) were stored in Kubernetes Secrets instead of ConfigMaps.
    - Database credentials
    - Kafka certificates
    - API keys
    - Database passwords (db_password = "s3cr3t")
    - API keys (stripe_api_key = "sk_test_123")
    - TLS certificates for HTTPS
    - Accessed securely by pods and encrypted at rest.

#### Security Features:

- Encrypted at rest (AWS EKS automatically secures them).
- Never stored in Git (unlike ConfigMaps, which can be versioned).
- Some teams also used AWS Secrets Manager for extra security.

## 📈 Scaling

### 🔸 HPA (Autoscaling – Automatically Adding More Pods Under Load)

- Some services (like payment processing) needed to scale up/down based on traffic.
- Configured based on:
    - CPU utilization
    - Memory usage

#### How HPA Worked?

- If CPU/memory usage went above 70%, Kubernetes automatically added more pods.
- When traffic dropped, it scaled down to save costs.
- We set min/max pod limits to avoid overloading the cluster.
- Automatically scaled pods **up or down** depending on traffic volume.

```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: payment-service-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: payment-service
  minReplicas: 2
  maxReplicas: 10
  metrics:
    - type: Resource
      resource:
        name: cpu
        target:
          type: Utilization
          averageUtilization: 70
```

---

## 🚀 Deployment Strategies (Rolling Updates vs. Canary Releases)

### A) Rolling Updates (Default – Zero Downtime)

- New version slowly replaces old pods, one by one.
- If something fails, Kubernetes stops the rollout automatically.

### B) Canary Deployments (For Critical Services)

- First, deploy the new version to only 5% of users.
- Monitor errors, latency, performance.
- If everything looks good, roll out to 100%.
- If something breaks, roll back instantly.

### 🔸 GitOps with ArgoCD (Automated Deployments from Git)

- Instead of manually running kubectl apply, we used ArgoCD to automatically sync Kubernetes with Git.

#### How It Worked?

- All Kubernetes YAML/Helm charts were stored in a Git repository.
- ArgoCD watched this repo and applied changes to EKS automatically.
- If someone changed a file in Git → ArgoCD updated Kubernetes within minutes.

#### Benefits:

✅ No manual deployments (reduces human errors).
✅ Easy rollbacks (just revert a Git commit).
✅ Full audit log (who changed what and when).

## How We Released New Software Versions:

### Nightly Test Versions (For Developers)

- Every night, we automatically built a new test version of our software
- Developers could try these versions the next morning
- This helped us find and fix problems quickly

### Official Releases (Every 2 Weeks)

- We planned releases to match our 2-week work cycles (called "sprints")
- At the end of each cycle, we combined all the ready features into a release branch
- This was like packing up a box of completed work

### Testing Before Going Live

- First, we tested the release in our QA (Quality Assurance) environment
- Then real users tested it in our UAT (User Acceptance Testing) environment
- Only after both tests passed did we send it to real customers

### Careful Rollout to Customers

- We first released to a small group of customers (called a "canary release")
- We watched carefully to make sure nothing broke
- If everything looked good, we released to everyone

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

## Final Summary (Simple Flow)

- Developers push code → Jenkins builds Docker image → Pushes to AWS ECR.
- Helm charts define how the app runs in Kubernetes.
- ConfigMaps & Secrets store configurations securely.
- HPA scales pods up/down based on traffic.
- ArgoCD automatically applies changes from Git → Kubernetes.
- Rolling updates & canary releases ensure smooth deployments.
- This setup made our deployments faster, more reliable, and fully automated while keeping everything secure and
  scalable.