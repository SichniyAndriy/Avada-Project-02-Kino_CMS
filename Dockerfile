# Використовуємо офіційний образ Tomcat
FROM tomcat:10.1-jdk17

# Копіюємо .war файл у папку webapps
COPY ./target/KinoCMS-SichniyA.war /usr/local/tomcat/webapps/KinoCMS-SichniyA.war
VOLUME ["/usr/local/tomcat/webapps/resources"]

# Відкриваємо порт 8080
EXPOSE 8080

RUN apt update && apt install -y iputils-ping && apt install -y net-tools

# Запускаємо Tomcat
CMD ["catalina.sh", "run"]
