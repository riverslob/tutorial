rootProject.name = "tutorial"
include(
    "redis-queue",
    "merchant",
    "utilities",
    "ddd-demo",
//    "k8s-service",
    "k8s-service:consumer",
    "k8s-service:producer"
)

