FROM openjdk:11
COPY . /opt/clinic
EXPOSE 8080
CMD java -Dspring.profiles.active=docker -Dapp.home=/opt/clinic-home -jar /opt/clinic/clinic.jar