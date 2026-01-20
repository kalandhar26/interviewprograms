# Helm Chart Files and Folders

The following table outlines the purpose, functionality, and common use cases for each file and folder in a Helm chart
used for deploying microservices in a Kubernetes environment, such as AWS EKS.

| File/Folder             | Purpose & What it Does                                                                                                    | Common Use Cases & Notes                                                                               |
|-------------------------|---------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|
| **Chart.yaml**          | Contains metadata about the Helm chart, including name, version, description, and maintainers.                            | Required by Helm for versioning and identification. Used in Helm repositories for deployment tracking. |
| **values.yaml**         | Default configuration values for the chart, defining image, environment variables, resources, etc.                        | Serves as the base configuration. Can be overridden at deployment with `--values` flag or via ArgoCD.  |
| **values-dev.yaml**     | Override values for the development environment, e.g., lower resources, different DB endpoints.                           | Enables environment-specific configs for development, optimizing resource usage and settings.          |
| **values-staging.yaml** | Override values for staging/pre-prod, mimicking production with testing-specific tweaks.                                  | Ensures staging closely resembles production for reliable testing while allowing flexibility.          |
| **values-prod.yaml**    | Override values for production, specifying production-grade replicas, resources, and endpoints.                           | Enforces best practices for production, ensuring stability and performance.                            |
| **templates/**          | Directory containing Helm YAML templates rendered into Kubernetes manifests using values files.                           | Central to templating and deployment logic for microservices in Kubernetes.                            |
| **deployment.yaml**     | Templated Kubernetes Deployment specifying pods, images, replicas, env vars, resources, and ConfigMap/Secret mounts.      | Main mechanism for deploying and updating microservices with scalable, rolling updates.                |
| **service.yaml**        | Defines a Kubernetes Service to map traffic to pods for stable networking and service discovery.                          | Facilitates communication between microservices or exposure via ingress.                               |
| **ingress.yaml**        | Defines Ingress resource to route external HTTP/HTTPS traffic to the service, often with load balancers (e.g., AWS ALB).  | Provides URL, path, TLS, and routing for external clients and APIs.                                    |
| **configmap.yaml**      | Templated Kubernetes ConfigMap holding non-sensitive configs (e.g., URLs, feature flags) as env vars or files.            | Allows configuration changes without rebuilding images, used for application settings.                 |
| **secret.yaml**         | Templated Kubernetes Secret for sensitive data (e.g., passwords, API keys, certificates), securely mounted/injected.      | Prevents sensitive data leaks, integrates with AWS Secrets Manager for enhanced security.              |
| **hpa.yaml**            | Templated HorizontalPodAutoscaler manifest for scaling pod replicas based on CPU/memory or custom metrics.                | Ensures reliability and cost-efficiency by autoscaling during traffic spikes.                          |
| **pdb.yaml**            | PodDisruptionBudget ensures a minimum number of pods remain available during voluntary disruptions (e.g., node upgrades). | Prevents downtime during cluster maintenance or node draining operations.                              |
| **serviceaccount.yaml** | Defines a Kubernetes ServiceAccount for associating pods with RBAC permissions and cloud IAM (e.g., AWS IRSA).            | Enables secure access to Kubernetes API, ECR, S3, etc., with least privilege.                          |
| **networkpolicy.yaml**  | Defines Kubernetes NetworkPolicy to control ingress/egress traffic for pods (e.g., allowed namespaces/IPs).               | Enforces zero-trust networking, enhances security, and prevents unauthorized access.                   |
| **_helpers.tpl**        | Template helpers and reusable functions for the Helm chart (e.g., labels, names, selectors).                              | Keeps charts DRY, maintainable, and reduces errors by centralizing common template logic.              |

## Notes

- This table reflects a typical Helm chart structure for microservices deployed on Kubernetes, tailored to the context
  of an AWS EKS environment.
- Helm charts enable modular, scalable, and secure deployments by providing reusable and environment-specific
  configurations.
- For specific file examples (e.g., `networkpolicy.yaml`, `_helpers.tpl`), refer to detailed artifacts or request
  additional samples.