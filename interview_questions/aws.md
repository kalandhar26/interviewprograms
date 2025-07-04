# Key AWS Tech

| AWS Service                                   | Key Functionality                                     | Use Case in Java/Spring Boot                                                                               |
|-----------------------------------------------|-------------------------------------------------------|------------------------------------------------------------------------------------------------------------|
| **IAM** (Identity & Access Management)        | Manages users, roles, and permissions securely.       | - Securing AWS API access (e.g., S3, DynamoDB)<br>- Assigning roles to EC2 instances for secure SDK access |
| **VPC** (Virtual Private Cloud)               | Isolated networking environment for AWS resources     | - Deploying Spring Boot apps in private subnets<br>- Configuring security groups (firewall rules)          |
| **EC2** (Elastic Compute Cloud)               | Virtual servers in the cloud (Linux/Windows)          | - Hosting Spring Boot apps on virtual machines<br>- Auto Scaling for high availability                     |
| **S3** (Simple Storage Service)               | Object storage for files (unstructured data)          | - Storing app logs, images, or static content<br>- Backup & restore for Spring Boot apps                   |
| **Lambda** (Serverless Compute)               | Runs event-driven functions without servers           | - Processing S3 uploads, API Gateway requests<br>- Async task execution (e.g., PDF generation)             |
| **ECS** (Elastic Container Service)           | Docker container orchestration (managed by AWS)       | - Running Spring Boot apps in Docker containers<br>- Integrates with Fargate (serverless containers)       |
| **ELB** (Elastic Load Balancer)               | Distributes traffic across multiple EC2/ECS instances | - Load balancing Spring Boot microservices<br>- Supports HTTP(S), TCP, and WebSockets                      |
| **RDS** (Relational Database Service - MySQL) | Managed relational database (MySQL, PostgreSQL, etc.) | - Spring Boot application.yml connects to RDS MySQL<br>- Automated backups & failover                      |
| **CDK** (Cloud Development Kit)               | Infrastructure-as-Code (IaC) using Java/Python        | - Programmatically defining AWS resources (VPC, ECS, etc.)<br>- Deploying stacks via AWS CloudFormation    |
| **EKS** (Elastic Kubernetes Service)          | Managed Kubernetes for container orchestration        | - Deploying Spring Boot microservices in K8s<br>- Auto-scaling with Horizontal Pod Autoscaler (HPA)        |

# AWS ECS vs EKS Cheat Sheet for Java Microservices

## 🔄 Common Workflow

| Step          | ECS Command                       | EKS Command                        |
|---------------|-----------------------------------|------------------------------------|
| **Deploy**    | `aws ecs update-service`          | `kubectl apply -f deployment.yaml` |
| **Rollback**  | `aws ecs deploy --rollback`       | `kubectl rollout undo deployment`  |
| **View Logs** | `aws logs tail /ecs/service-name` | `kubectl logs -f <pod>`            |

## 1️⃣ ECS Essentials (Fargate/EC2)

### Cluster & Tasks

```bash
# List clusters
aws ecs list-clusters

# Describe services
aws ecs describe-services --cluster my-cluster --services my-service

# Run one-off task (e.g., DB migration)
aws ecs run-task --cluster my-cluster \
  --task-definition my-task:1 \
  --network-configuration "awsvpcConfiguration={subnets=[subnet-123],securityGroups=[sg-123]}"

# Update service (zero-downtime deploy)
aws ecs update-service --cluster my-cluster \
  --service my-service \
  --force-new-deployment
```