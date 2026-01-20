# Complete Microservice Folder Structure

## 1. Individual Service Structure (e.g., user-service)

```
user-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── company/
│   │   │           └── userservice/
│   │   │               ├── UserServiceApplication.java
│   │   │               ├── controller/
│   │   │               │   └── UserController.java
│   │   │               ├── service/
│   │   │               │   └── UserService.java
│   │   │               ├── repository/
│   │   │               │   └── UserRepository.java
│   │   │               ├── model/
│   │   │               │   └── User.java
│   │   │               ├── dto/
│   │   │               │   ├── UserRequestDto.java
│   │   │               │   └── UserResponseDto.java
│   │   │               └── config/
│   │   │                   ├── DatabaseConfig.java
│   │   │                   └── SecurityConfig.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       ├── bootstrap.yml
│   │       └── db/
│   │           └── migration/
│   │               └── V1_0_1__create_user_table.sql
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── company/
│       │           └── userservice/
│       │               ├── UserServiceApplicationTests.java
│       │               ├── controller/
│       │               │   └── UserControllerTest.java
│       │               └── service/
│       │                   └── UserServiceTest.java
│       └── resources/
│           └── application-test.yml
├── target/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
├── README.md
├── Jenkinsfile                           # CI/CD Pipeline
├── Dockerfile                            # Container Definition
├── .dockerignore
├── helm/                                 # Helm Charts Directory
│   └── user-service/
│       ├── Chart.yaml
│       ├── values.yaml
│       ├── values-dev.yaml
│       ├── values-staging.yaml
│       ├── values-prod.yaml
│       └── templates/
│           ├── deployment.yaml
│           ├── service.yaml
│           ├── ingress.yaml
│           ├── configmap.yaml
│           ├── secret.yaml
│           ├── hpa.yaml
│           ├── pdb.yaml
│           ├── serviceaccount.yaml
│           ├── networkpolicy.yaml
│           └── _helpers.tpl
└── k8s/                                  # Raw Kubernetes Manifests (Alternative to Helm)
    ├── base/
    │   ├── kustomization.yaml
    │   ├── deployment.yaml
    │   ├── service.yaml
    │   ├── configmap.yaml
    │   └── secret.yaml
    └── overlays/
        ├── dev/
        │   ├── kustomization.yaml
        │   ├── configmap-patch.yaml
        │   └── deployment-patch.yaml
        ├── staging/
        │   ├── kustomization.yaml
        │   ├── configmap-patch.yaml
        │   └── deployment-patch.yaml
        └── prod/
            ├── kustomization.yaml
            ├── configmap-patch.yaml
            ├── deployment-patch.yaml
            └── sealed-secret.yaml
```

## 2. Multi-Service Repository Structure (Monorepo Approach)

```
microservices-platform/
├── services/
│   ├── user-service/
│   │   ├── src/
│   │   ├── target/
│   │   ├── pom.xml
│   │   ├── mvnw
│   │   ├── mvnw.cmd
│   │   ├── Dockerfile
│   │   └── Jenkinsfile
│   ├── payment-service/
│   │   ├── src/
│   │   ├── target/
│   │   ├── pom.xml
│   │   ├── mvnw
│   │   ├── mvnw.cmd
│   │   ├── Dockerfile
│   │   └── Jenkinsfile
│   ├── order-service/
│   │   ├── src/
│   │   ├── target/
│   │   ├── pom.xml
│   │   ├── mvnw
│   │   ├── mvnw.cmd
│   │   ├── Dockerfile
│   │   └── Jenkinsfile
│   ├── gateway-service/
│   │   ├── src/
│   │   ├── target/
│   │   ├── pom.xml
│   │   ├── mvnw
│   │   ├── mvnw.cmd
│   │   ├── Dockerfile
│   │   └── Jenkinsfile
│   ├── discovery-service/
│   │   ├── src/
│   │   ├── target/
│   │   ├── pom.xml
│   │   ├── mvnw
│   │   ├── mvnw.cmd
│   │   ├── Dockerfile
│   │   └── Jenkinsfile
│   └── auth-service/
│       ├── src/
│       ├── target/
│       ├── pom.xml
│       ├── mvnw
│       ├── mvnw.cmd
│       ├── Dockerfile
│       └── Jenkinsfile
├── helm-charts/
│   ├── user-service/
│   │   ├── Chart.yaml
│   │   ├── values.yaml
│   │   ├── values-dev.yaml
│   │   ├── values-staging.yaml
│   │   ├── values-prod.yaml
│   │   └── templates/
│   │       ├── deployment.yaml
│   │       ├── service.yaml
│   │       ├── ingress.yaml
│   │       ├── configmap.yaml
│   │       ├── secret.yaml
│   │       ├── hpa.yaml
│   │       ├── pdb.yaml
│   │       ├── serviceaccount.yaml
│   │       └── _helpers.tpl
│   ├── payment-service/
│   │   ├── Chart.yaml
│   │   ├── values.yaml
│   │   ├── values-dev.yaml
│   │   ├── values-staging.yaml
│   │   ├── values-prod.yaml
│   │   └── templates/
│   │       ├── deployment.yaml
│   │       ├── service.yaml
│   │       ├── ingress.yaml
│   │       ├── configmap.yaml
│   │       ├── secret.yaml
│   │       ├── hpa.yaml
│   │       ├── pdb.yaml
│   │       └── serviceaccount.yaml
│   └── umbrella-chart/                   # Parent chart for all services
│       ├── Chart.yaml
│       ├── values.yaml
│       ├── values-dev.yaml
│       ├── values-staging.yaml
│       ├── values-prod.yaml
│       └── templates/
│           └── namespace.yaml
├── k8s-manifests/
│   ├── base/
│   │   ├── user-service/
│   │   │   ├── kustomization.yaml
│   │   │   ├── deployment.yaml
│   │   │   ├── service.yaml
│   │   │   └── configmap.yaml
│   │   ├── payment-service/
│   │   │   ├── kustomization.yaml
│   │   │   ├── deployment.yaml
│   │   │   ├── service.yaml
│   │   │   └── configmap.yaml
│   │   └── gateway-service/
│   │       ├── kustomization.yaml
│   │       ├── deployment.yaml
│   │       ├── service.yaml
│   │       └── configmap.yaml
│   └── overlays/
│       ├── dev/
│       │   ├── kustomization.yaml
│       │   ├── user-service/
│       │   │   ├── kustomization.yaml
│       │   │   ├── configmap-patch.yaml
│       │   │   └── deployment-patch.yaml
│       │   ├── payment-service/
│       │   │   ├── kustomization.yaml
│       │   │   ├── configmap-patch.yaml
│       │   │   └── deployment-patch.yaml
│       │   └── namespace.yaml
│       ├── staging/
│       │   ├── kustomization.yaml
│       │   ├── user-service/
│       │   │   ├── kustomization.yaml
│       │   │   ├── configmap-patch.yaml
│       │   │   └── deployment-patch.yaml
│       │   ├── payment-service/
│       │   │   ├── kustomization.yaml
│       │   │   ├── configmap-patch.yaml
│       │   │   └── deployment-patch.yaml
│       │   └── namespace.yaml
│       └── prod/
│           ├── kustomization.yaml
│           ├── user-service/
│           │   ├── kustomization.yaml
│           │   ├── configmap-patch.yaml
│           │   ├── deployment-patch.yaml
│           │   └── sealed-secret.yaml
│           ├── payment-service/
│           │   ├── kustomization.yaml
│           │   ├── configmap-patch.yaml
│           │   ├── deployment-patch.yaml
│           │   └── sealed-secret.yaml
│           └── namespace.yaml
├── infrastructure/
│   ├── terraform/
│   │   ├── environments/
│   │   │   ├── dev/
│   │   │   │   ├── main.tf
│   │   │   │   ├── variables.tf
│   │   │   │   └── terraform.tfvars
│   │   │   ├── staging/
│   │   │   │   ├── main.tf
│   │   │   │   ├── variables.tf
│   │   │   │   └── terraform.tfvars
│   │   │   └── prod/
│   │   │       ├── main.tf
│   │   │       ├── variables.tf
│   │   │       └── terraform.tfvars
│   │   └── modules/
│   │       ├── eks/
│   │       ├── rds/
│   │       └── vpc/
│   └── ansible/
│       ├── playbooks/
│       ├── roles/
│       └── inventory/
├── docs/                                 # Documentation
│   ├── api/
│   │   ├── user-service.md
│   │   ├── payment-service.md
│   │   └── openapi/
│   │       ├── user-service.yaml
│   │       └── payment-service.yaml
│   ├── deployment/
│   │   ├── local-setup.md
│   │   ├── ci-cd-pipeline.md
│   │   └── monitoring.md
│   └── architecture/
│       ├── system-overview.md
│       ├── microservices-design.md
│       └── diagrams/
├── scripts/
│   ├── build/
│   │   ├── build-all.sh
│   │   ├── build-service.sh
│   │   └── docker-build.sh
│   ├── deploy/
│   │   ├── deploy-dev.sh
│   │   ├── deploy-staging.sh
│   │   ├── deploy-prod.sh
│   │   └── rollback.sh
│   ├── setup/
│   │   ├── local-env-setup.sh
│   │   ├── install-dependencies.sh
│   │   └── create-namespaces.sh
│   └── monitoring/
│       ├── check-health.sh
│       └── get-logs.sh
├── config/                               # Shared configurations
│   ├── prometheus/
│   │   ├── prometheus.yml
│   │   └── alert-rules.yml
│   ├── grafana/
│   │   ├── dashboards/
│   │   └── datasources/
│   ├── jenkins/
│   │   ├── jenkins.yml
│   │   └── pipelines/
│   └── argocd/
│       ├── applications/
│       │   ├── user-service.yaml
│       │   ├── payment-service.yaml
│       │   └── gateway-service.yaml
│       └── projects/
│           └── microservices-project.yaml
├── docker-compose/                       # Local Development
│   ├── docker-compose.yml
│   ├── docker-compose.dev.yml
│   ├── docker-compose.monitoring.yml
│   └── .env
├── .github/                              # GitHub Actions (if using GitHub)
│   └── workflows/
│       ├── ci.yml
│       ├── cd-dev.yml
│       ├── cd-staging.yml
│       └── cd-prod.yml
├── .gitignore
├── README.md
├── pom.xml                               # Parent POM (if using Maven multi-module)
└── Jenkinsfile                           # Multi-service pipeline
```

## 3. Separate GitOps Repository Structure

```
k8s-gitops-repo/
├── applications/
│   ├── user-service/
│   │   ├── base/
│   │   │   ├── kustomization.yaml
│   │   │   ├── deployment.yaml
│   │   │   ├── service.yaml
│   │   │   └── configmap.yaml
│   │   └── overlays/
│   │       ├── dev/
│   │       │   ├── kustomization.yaml
│   │       │   ├── configmap-patch.yaml
│   │       │   └── deployment-patch.yaml
│   │       ├── staging/
│   │       │   ├── kustomization.yaml
│   │       │   ├── configmap-patch.yaml
│   │       │   └── deployment-patch.yaml
│   │       └── prod/
│   │           ├── kustomization.yaml
│   │           ├── configmap-patch.yaml
│   │           ├── deployment-patch.yaml
│   │           └── sealed-secret.yaml
│   ├── payment-service/
│   │   ├── base/
│   │   │   ├── kustomization.yaml
│   │   │   ├── deployment.yaml
│   │   │   ├── service.yaml
│   │   │   └── configmap.yaml
│   │   └── overlays/
│   │       ├── dev/
│   │       │   ├── kustomization.yaml
│   │       │   ├── configmap-patch.yaml
│   │       │   └── deployment-patch.yaml
│   │       ├── staging/
│   │       │   ├── kustomization.yaml
│   │       │   ├── configmap-patch.yaml
│   │       │   └── deployment-patch.yaml
│   │       └── prod/
│   │           ├── kustomization.yaml
│   │           ├── configmap-patch.yaml
│   │           ├── deployment-patch.yaml
│   │           └── sealed-secret.yaml
│   ├── gateway-service/
│   │   ├── base/
│   │   │   ├── kustomization.yaml
│   │   │   ├── deployment.yaml
│   │   │   ├── service.yaml
│   │   │   └── configmap.yaml
│   │   └── overlays/
│   │       ├── dev/
│   │       │   ├── kustomization.yaml
│   │       │   ├── configmap-patch.yaml
│   │       │   └── deployment-patch.yaml
│   │       ├── staging/
│   │       │   ├── kustomization.yaml
│   │       │   ├── configmap-patch.yaml
│   │       │   └── deployment-patch.yaml
│   │       └── prod/
│   │           ├── kustomization.yaml
│   │           ├── configmap-patch.yaml
│   │           ├── deployment-patch.yaml
│   │           └── sealed-secret.yaml
├── infrastructure/
│   ├── namespaces/
│   │   ├── dev-namespace.yaml
│   │   ├── staging-namespace.yaml
│   │   └── prod-namespace.yaml
│   ├── ingress-controllers/
│   │   ├── nginx-ingress/
│   │   └── traefik/
│   ├── monitoring/
│   │   ├── prometheus/
│   │   ├── grafana/
│   │   └── jaeger/
│   └── security/
│       ├── network-policies/
│       ├── pod-security-policies/
│       └── rbac/
├── argocd/
│   ├── applications/
│   │   ├── user-service-dev.yaml
│   │   ├── user-service-staging.yaml
│   │   ├── user-service-prod.yaml
│   │   ├── payment-service-dev.yaml
│   │   ├── payment-service-staging.yaml
│   │   └── payment-service-prod.yaml
│   ├── projects/
│   │   └── microservices.yaml
│   └── app-of-apps/
│       ├── dev-apps.yaml
│       ├── staging-apps.yaml
│       └── prod-apps.yaml
└── environments/
    ├── dev/
    │   ├── kustomization.yaml
    │   └── patches/
    ├── staging/
    │   ├── kustomization.yaml
    │   └── patches/
    └── prod/
        ├── kustomization.yaml
        └── patches/
```

## 4. File Organization Best Practices

### Choice 1: Service-Centric (Recommended for small teams)

- Each service has its own repository
- DevOps files are co-located with the service code
- Easier for service teams to manage their own deployments

### Choice 2: Monorepo (Recommended for medium teams)

- All services in one repository
- Shared DevOps configurations
- Centralized CI/CD pipeline management

### Choice 3: Separate GitOps Repository (Recommended for large teams)

- Service code and Kubernetes manifests are separated
- Better security and access control
- Follows GitOps principles strictly

## 5. Key File Locations Summary

| File Type             | Location Options                           | Recommended          |
|-----------------------|--------------------------------------------|----------------------|
| `Jenkinsfile`         | Service root OR separate `ci/` folder      | Service root         |
| `Dockerfile`          | Service root                               | Service root         |
| `Helm Charts`         | `helm/` folder in service OR separate repo | `helm/` in service   |
| `K8s Manifests`       | `k8s/` folder in service OR separate repo  | Separate GitOps repo |
| `ArgoCD Apps`         | GitOps repo OR separate config repo        | GitOps repo          |
| `Environment Configs` | With manifests OR separate config repo     | With manifests       |

## 6. Additional Configuration Files

### .gitignore (Service Level)

```
target/
*.jar
*.war
*.ear
*.class
.DS_Store
.idea/
*.iml
.vscode/
.settings/
.project
.classpath
log/
logs/
*.log
application-local.yml
```

### .dockerignore

```
target/
*.md
.git/
.gitignore
.dockerignore
Dockerfile
.DS_Store
.idea/
*.iml
.vscode/
src/test/
```

This structure provides flexibility for different team sizes and organizational preferences while maintaining clear
separation of concerns and following DevOps best practices.