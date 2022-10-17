## How to run tradebid application on your local machine

### 1. Bring up a maria db docker container
docker run --detach --name tradebid-db --env MARIADB_DATABASE=tradebid_db --env MARIADB_USER=app-user --env MARIADB_PASSWORD=app_user_pass --env MARIADB_ROOT_PASSWORD=root-user -p 3306:3306 mariadb:latest

### 2. Run the spring boot project
./gradlew bootRun