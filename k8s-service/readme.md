# docker build

## build
### build producer
`docker build -t j-producer:v3 .`
### build consumer
`docker build -t j-consumer:v3 .`
## start 

不用yaml直接创建pod
`nerdctl -n k8s.io build -t j-consumer:v11 . --build-arg APP_NAME=producer`

`k run j-consumer --image=j-consumer:v7 --port=8080`

使用文件创建

`kubectl create -f deploy.yaml`

查看日志

`kubectl logs j-producer`

pod端口转发

`kubectl port-forward j-producer 8888:8080`

docker 升级

`k set image deployment j-producer-d j-producer=j-producer:v5`
# install by helm

helm dependency update .

## producer
helm upgrade --install --dry-run --debug --namespace=default --values values.yaml j-producer . --set container_image_digests=j-producer:v8

helm upgrade --install --cleanup-on-fail --timeout 20m --namespace=default --values values.yaml j-producer . --set container_image_digests=j-producer:v8

helm uninstall j-producer


## consumer
helm upgrade --install --dry-run --debug --namespace=default --values values.yaml j-consumer . --set container_image_digests=j-consumer:v1

helm upgrade --install --cleanup-on-fail --timeout 20m --namespace=default --values values.yaml j-consumer . --set container_image_digests=j-consumer:v1

helm uninstall j-consumer


## ingress
helm upgrade --install --cleanup-on-fail --timeout 20m --namespace=default ingress-controller . --values values.yaml

helm uninstall ingress-controller

## node application
kubectl run j-consumer --image=n-app:v1 --port=8080 




```shell
cat /etc/resolv.conf
search default.svc.cluster.local svc.cluster.local cluster.local
nameserver 10.43.0.10
options ndots:5
```

