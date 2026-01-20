# GitHub Actions – Sample CI/CD YAML

## 2. CI/CD Pipeline with GitHub Actions

- A push or merge to the main or release branch triggered the GitHub Actions workflow.
- Workflows were defined using YAML files inside .github/workflows/.
- GitHub automatically detected repository changes and started the pipeline.

```yaml
name: CI-CD-Pipeline

on:
  push:
    branches:
      - main
      - release/*
  pull_request:
    branches:
      - main

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest

    steps:
      # 1. Checkout code
      - name: Checkout Repository
        uses: actions/checkout@v4

      # 2. Set up Java
      - name: Set up Java 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      # 3. Build & Test
      - name: Build with Maven
        run: mvn clean package

      - name: Run Unit Tests
        run: mvn test

      # 4. Login to Amazon ECR
      - name: Configure AWS Credentials
        uses: aws-actions/configure-aws-credentials@v4
        with:
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
          aws-region: ap-south-1

      - name: Login to Amazon ECR
        uses: aws-actions/amazon-ecr-login@v2

      # 5. Build & Push Docker Image
      - name: Build and Push Docker Image
        env:
          ECR_REGISTRY: ${{ secrets.ECR_REGISTRY }}
          ECR_REPOSITORY: payment-service
          IMAGE_TAG: latest
        run: |
          docker build -t $ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG .
          docker push $ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG
```

- Another Example

```yaml
name: CI-CD-Pipeline

on:
  push:
    branches:
      - main
  pull_request:
    branches:
      - main

env:
  APP_NAME: your-springboot-app-name
  AWS_REGION: us-east-1
  ECR_REPOSITORY: your-ecr-repository-name
  CLUSTER_NAME: your-eks-cluster-name

jobs:
  build-test-deploy:
    runs-on: ubuntu-latest

    steps:
      # -----------------------------
      # Checkout
      # -----------------------------
      - name: Checkout Source Code
        uses: actions/checkout@v4

      # -----------------------------
      # Setup Java 17
      # -----------------------------
      - name: Set up Java 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: maven

      # -----------------------------
      # Build
      # -----------------------------
      - name: Build Application
        run: mvn clean package -DskipTests

      # -----------------------------
      # Test
      # -----------------------------
      - name: Run Unit Tests
        run: mvn test

      # -----------------------------
      # AWS Credentials
      # -----------------------------
      - name: Configure AWS Credentials
        uses: aws-actions/configure-aws-credentials@v4
        with:
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
          aws-region: ${{ env.AWS_REGION }}

      # -----------------------------
      # Login to Amazon ECR
      # -----------------------------
      - name: Login to Amazon ECR
        id: login-ecr
        uses: aws-actions/amazon-ecr-login@v2

      # -----------------------------
      # Build & Push Docker Image
      # -----------------------------
      - name: Build and Push Docker Image
        env:
          ECR_REGISTRY: ${{ steps.login-ecr.outputs.registry }}
          IMAGE_TAG: ${{ github.run_number }}
        run: |
          docker build -t $ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG .
          docker tag $ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG $ECR_REGISTRY/$ECR_REPOSITORY:latest
          docker push $ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG
          docker push $ECR_REGISTRY/$ECR_REPOSITORY:latest

      # -----------------------------
      # Update kubeconfig for EKS
      # -----------------------------
      - name: Update kubeconfig
        run: |
          aws eks update-kubeconfig \
            --region $AWS_REGION \
            --name $CLUSTER_NAME

      # -----------------------------
      # Deploy to Kubernetes
      # -----------------------------
      - name: Deploy to Kubernetes
        run: |
          kubectl apply -f k8s/deployment.yaml
          kubectl apply -f k8s/service.yaml

      # -----------------------------
      # Verify Deployment
      # -----------------------------
      - name: Verify Rollout
        run: |
          kubectl rollout status deployment/${APP_NAME}
```