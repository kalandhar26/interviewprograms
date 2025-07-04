# Kubernetes Cheat Sheet for Java/Spring Boot

## 🌐 Cluster Management

| Command                                | Description            |
|----------------------------------------|------------------------|
| `kubectl cluster-info`                 | Show cluster details   |
| `kubectl config use-context <context>` | Switch clusters        |
| `kubectl get nodes`                    | List nodes with status |

## 📦 Pods & Deployments

| Command                                    | Description          | Java-Specific Use            |
|--------------------------------------------|----------------------|------------------------------|
| `kubectl get pods`                         | List pods            | Check Spring Boot app status |
| `kubectl describe pod <pod>`               | Inspect pod details  | Debug startup crashes        |
| `kubectl logs -f <pod>`                    | Stream logs          | View Spring Boot logs        |
| `kubectl exec -it <pod> -- sh`             | Enter container      | Debug running app            |
| `kubectl apply -f deployment.yaml`         | Apply deployment     | Deploy Spring Boot app       |
| `kubectl rollout status deployment/<name>` | Check rollout        | Verify zero-downtime deploy  |
| `kubectl rollout undo deployment/<name>`   | Rollback             | Revert broken release        |
| `kubectl delete deploy <name>`             | Remove deployment    |                              |
| `kubectl delete svc <name>`                | Delete service       |                              |
| `kubectl delete -f .`                      | Delete all in folder |                              |

## 🔌 Services & Networking

| Command                                     | Description        | Microservices Use           |
|---------------------------------------------|--------------------|-----------------------------|
| `kubectl get svc`                           | List services      | Check service exposure      |
| `kubectl port-forward svc/<name> 8080:8080` | Port forwarding    | Local access to Spring Boot |
| `kubectl get ingress`                       | List ingress rules | Check API gateway routes    |

## 📊 Scaling & Health

| Command                                                            | Description        |
|--------------------------------------------------------------------|--------------------|
| `kubectl scale deploy <name> --replicas=3`                         | Scale horizontally |
| `kubectl autoscale deploy <name> --min=2 --max=5 --cpu-percent=80` | Auto-scale         |
| `kubectl get hpa`                                                  | Check autoscaling  |

## 🛠️ ConfigMaps & Secrets (for Spring Boot)

| Command                                                               | Description      |
|-----------------------------------------------------------------------|------------------|
| `kubectl create configmap app-config --from-file=application.yml`     | Create ConfigMap |
| `kubectl create secret generic db-secret --from-literal=DB_PASS=1234` | Create Secret    |
| `kubectl get configmap,secret`                                        | View configs     |

## 💾 Persistent Volumes (For Databases)

| Command                           | Description   |
|-----------------------------------|---------------|
| `kubectl get pv,pvc`              | Check storage |
| `kubectl apply -f mysql-pvc.yaml` | Claim storage |

## 🚨 Debugging Java Apps

```bash
# Get OOMKilled reasons
kubectl describe pod <pod> | grep -A 10 "State"

# Enable remote debugging
kubectl port-forward <pod> 5005:5005
```