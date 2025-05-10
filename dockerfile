FROM openjdk:23-jdk
ARG JAR_FILE=target/*.jar
# ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
# COPY ssl ssl
COPY secrets.properties secrets.properties
COPY portfolio_db_username.txt portfolio_db_username.txt
COPY portfolio_db_password.txt portfolio_db_password.txt
COPY portfolio_admin_username.txt portfolio_admin_username.txt
COPY portfolio_admin_password.txt portfolio_admin_password.txt
EXPOSE 443
ENTRYPOINT ["java","-jar","/app.jar"]