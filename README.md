# Resilience Workshop

## Patterns

### CircuitBreaker

1. Run **generate-base-load** test and demonstrate stable RPS
2. Run **call-delayed-error** test and explain why RPS goes down
3. Put `@io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "delayedError")` on top of `delayedError` method and demonstrate how RPS is stabilizing.

#### Cleanup
1. Drop **call-delayed-error** job and restart **service-under-test** deployment

### Retry

1. Run **call-random-error** test and demonstrate 5xx and 2xx responses rate
2. Put `@io.github.resilience4j.retry.annotation.Retry(name = "randomError")` on top of `randomError` method and demonstrate 2xx only responses

#### Cleanup

1. Drop **call-random-error** job and restart **service-under-test** deployment

### Bulkhead
1. Run **call-slow-db-query** test and demonstrate stable RPS and connection pool metrics
2. Run **generate-db-entries** test and explain why connection pool is full
3. Put `@io.github.resilience4j.bulkhead.annotation.Bulkhead(name = "slowDbQuery")` on top of `slowDbQuery` method and demonstrate how connection pool is stabilizing.

#### Cleanup

1. Drop **call-slow-db-query** job and restart **service-under-test** deployment

### Pubsub

1. Run **generate-pubsub-messages** test and explain how default pubsub 10s retry works

## Helpful scripts

```shell script
kubectl port-forward -nresilience-workshop svc/pubsub-emulator 8085
kubectl port-forward -nresilience-workshop svc/mongodb 27017
kubectl port-forward -nresilience-workshop svc/faulty-service 8181:80
curl -H 'content-type: application/json' -X POST --data $'{"messages": [{"data": "abcd"}]}' http://localhost:8085/v1/projects/fake/topics/sample-topic:publish
mongo "mongodb://user:pass@localhost:27017/resilience-workshop" --eval 'db.sample.insert({ _id : ObjectId(), name : "test" })'
mongo "mongodb://user:pass@localhost:27017/resilience-workshop" --eval 'db.sample.find()'
```
