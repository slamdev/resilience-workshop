FROM google/cloud-sdk:alpine

RUN apk --update add openjdk11-jre \
 && gcloud components install --quiet beta pubsub-emulator \
 && mkdir -p /var/pubsub

VOLUME /var/pubsub

EXPOSE 8085

ENTRYPOINT [ "gcloud", "beta", "emulators", "pubsub", "start", "--data-dir=/var/pubsub", "--host-port=0.0.0.0:8085", "--project=fake", "--log-http", "--verbosity=debug", "--user-output-enabled" ]
