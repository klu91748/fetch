# Receipt Process App
This application is built using Spring Boot and packaged into a JAR file for containerization.
## Running Application
1. Build the app: `docker build -t fetch .`
2. Run the app: `docker run -p 8080:8080 fetch`
## Notes
1. ID is a string, but is handled in UUID format
2. Port is 8080
