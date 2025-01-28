docker exec -it sec-keycloak bash -c ^
"/opt/keycloak/bin/kc.sh export --dir ./tmp/import --users different_files --realm Test-Realm"