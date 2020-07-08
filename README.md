Helpful scripts:

```shell script
kubectl port-forward -nresilience-workshop svc/pubsub-emulator 8085
kubectl port-forward -nresilience-workshop svc/mongodb 27017
kubectl port-forward -nresilience-workshop svc/faulty-service 8181:80
curl -H 'content-type: application/json' -X POST --data $'{"messages": [{"data": "abcd"}]}' http://localhost:8085/v1/projects/fake/topics/sample-topic:publish
mongo "mongodb://user:pass@localhost:27017/resilience-workshop" --eval 'db.sample.insert({ _id : ObjectId(), name : "test" })'
mongo "mongodb://user:pass@localhost:27017/resilience-workshop" --eval 'db.sample.find()'
```
