#!/usr/bin/env bash
echo "$DOCKER_PASSWORD" | docker login --username "$DOCKER_USERNAME" --password-stdin
docker build -t "$DOCKER_USERNAME"/fulib_scenarios:latest .
docker push "$DOCKER_USERNAME"/fulib_scenarios:latest

# update on rancher
rancherUrl="http://avocado.uniks.de:8080/v2-beta/projects/1a5/services/1s173"

curl -u "${RANCHER_ACCESS}:${RANCHER_KEY}" -X POST -H 'Content-Type: application/json' -d '{"inServiceStrategy": { "startFirst": true, "secondaryLaunchConfigs": []}}' ${rancherUrl}?action=upgrade

echo "Waiting for upgrade ..."
retry=60
state=$(curl -u "$RANCHER_ACCESS:$RANCHER_KEY" "${rancherUrl}" | jq -r '.state')
while [[ "$state" !=  "upgraded"  ]] && [[ ${retry} -ge 0 ]]
    do
        state=$(curl -u "$RANCHER_ACCESS:$RANCHER_KEY" "${rancherUrl}" | jq -r '.state')
        retry=$((retry-1))
        sleep 1
done

echo "Finishing upgrade ..."
curl -u "$RANCHER_ACCESS:$RANCHER_KEY" "${rancherUrl}?action=finishupgrade"

retry=60
state=$(curl -u "$RANCHER_ACCESS:$RANCHER_KEY" "${rancherUrl}" | jq -r '.state')
while [[ "$state" !=  "active"  ]] && [[ ${retry} -ge 0 ]]
    do
        state=$(curl -u "$RANCHER_ACCESS:$RANCHER_KEY" "${rancherUrl}" | jq -r '.state')
        retry=$((retry-1))
        sleep 1
done

if [[ "$retry" -gt 0 ]]
    then
        echo "Successfully upgraded!"
    else
        echo "Error while upgrading, please check on rancher!"
fi
