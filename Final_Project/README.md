<<<<<<< HEAD
# Software Requirements

(SR-01) As a student, I want to quickly add assignments so that I can capture tasks without wasting time. <br/>
<br/>
(SR-02) As a student, I want to organize my assignments in a simple checklist so that I can easily track what is done and what is not. <br/>
<br/>
(SR-03) As a student, I want reminders for assignment deadlines so that I don’t forget to complete them on time. <br/>
<br/>
(SR-04) As a student, I want to mark assignments as completed so that I can clearly see my progress. <br/>
<br/>
(SR-05) As a student, I want a reward or points system for completing assignments so that I feel motivated to finish my work.  <br/>
<br/>
(SR-06) As a student, I want to redeem rewards or benefits from earned points so that staying productive feels worthwhile. <br/>
<br/>
(SR-07) As a student, I want the system to be simple and not distracting so that I will actually use it.  <br/>
<br/>
(SR-08) As a student, I want to be able to add notes to each assignment so that I can keep all relevant information together. <br/>
<br/>
(SR-09) As a student, I want to view completed vs incomplete assignments separately so that I can focus on pending work. <br/>
<br/>
(SR-10) As a student, I want to prioritize assignments based on due date and priority(low/medium/high).
 
# Software Functional Requirements

(SF-01) When the application runs, the system shall allow users to add a new assignment. <br/>
<br/>
(SF-02) When the "add assignment" button is pressed, the system shall allow users to enter the assignment title, course name, due date, priority(low/medium/high), and description/notes. <br/>
<br/>
(SF-03) When an assignment is added, the system shall display all assignments in an incomplete list. <br/>
<br/>
(SF-04) When an assignment is selected, the system shall allow users to mark assignments as completed. <br/>
<br/>
(SF-05) When an assignment is marked as complete, the system shall move it to the completed list. <br/>
<br/>
(SF-06) When an assignment is selected, the system shall allow users to delete or edit assignments.  <br/>
<br/>
(SF-07) When assignments are added, the system shall sort assignments by due date or priority(low/medium/high).  <br/>
<br/>
(SF-08) When an assignment is marked as complete, the system shall give users points/credits.  <br/>
<br/>
(SF-09) When an assignment is past due, the system shall remove points/credits once per assignment. <br/>
<br/>
(SF-10) When the application runs, the system shall display the user’s total points.  <br/>
<br/>
(SF-11) When an assignment is added, the system shall notify users before the assignment due date.  <br/>
<br/>
(SF-12) When an assignment is selected, the system shall allow the user to view the assignment title, course name, due date, and description/notes. 

Architecture Requirements
(AR-01) The system shall use a three-tier architecture consisting of presentation layer, application layer, and database layer.

(AR-02) The presentation layer shall use JSP, HTML, and CSS to provide the user interface.

(AR-03) The application layer shall use Java Servlets running on Apache Tomcat to process user requests and business logic.

(AR-04) The backend MYSQL database shall store assignment information, deadlines, completion status, notes, priority, and reward points.

(AR-05) The application shall allow configurations for MYSQL connection using local or remote database credentials.

(AR-06) The system shall use JDBC for communication between the Java backend and MYSQL database.

(AR-07) The system shall support CRUD operations for assignment management.

(AR-08) The architecture shall separate frontend, backend, and database components to improve maintainability and scalability.

(AR-09) The system shall support multiple assignment records stored in the database.

(AR-10) The application shall be accessible through a web browser connected to the Apache Tomcat server.

(AR-11) The system shall support reward point calculations after assignment completion.

(AR-12) The system shall support assignment status tracking including not started, in progress, and completed states.

(AR-13) The system shall support assignment sorting by due date and priority.

(AR-14) The system shall support notification and reminder functionality for upcoming deadlines.

(AR-15) The system architecture shall allow future expansion such as mobile support, email notifications, and calendar integration.

# Project Setup
This project is implemented as a Java Servlet web application for Apache Tomcat.

## Files generated
- `pom.xml`
- `src/main/java/edu/cs/Assignment.java`
- `src/main/java/edu/cs/AssignmentDAO.java`
- `src/main/java/edu/cs/AssignmentServlet.java`
- `src/main/webapp/index.jsp`
- `src/main/webapp/list.jsp`
- `src/main/webapp/assignment-form.jsp`
- `src/main/webapp/WEB-INF/web.xml`
- `db-schema.sql`

## How to run
1. Create the database with `db-schema.sql`.
2. Update MySQL credentials in `src/main/java/edu/cs/AssignmentDAO.java`.
3. Build with Maven: `mvn clean package`.
4. Deploy the generated `target/assignment-planner.war` to Tomcat.
5. Open `http://localhost:8080/assignment-planner/`.

## UI Enhancements
- Bootstrap styling for forms, tables, and cards.
- Dedicated assignment detail page.
- Clear separate views for incomplete and completed assignments.
- Reminder status and reward/points summary on the checklist page.
=======
# Software Requirements

(SR-01) As a student, I want to quickly add assignments so that I can capture tasks without wasting time. <br/>
<br/>
(SR-02) As a student, I want to organize my assignments in a simple checklist so that I can easily track what is done and what is not. <br/>
<br/>
(SR-03) As a student, I want reminders for assignment deadlines so that I don’t forget to complete them on time. <br/>
<br/>
(SR-04) As a student, I want to mark assignments as completed so that I can clearly see my progress. <br/>
<br/>
(SR-05) As a student, I want a reward or points system for completing assignments so that I feel motivated to finish my work.  <br/>
<br/>
(SR-06) As a student, I want to redeem rewards or benefits from earned points so that staying productive feels worthwhile. <br/>
<br/>
(SR-07) As a student, I want the system to be simple and not distracting so that I will actually use it.  <br/>
<br/>
(SR-08) As a student, I want to be able to add notes to each assignment so that I can keep all relevant information together. <br/>
<br/>
(SR-09) As a student, I want to view completed vs incomplete assignments separately so that I can focus on pending work. <br/>
<br/>
(SR-10) As a student, I want to prioritize assignments based on due date and priority(low/medium/high).
 
# Software Functional Requirements

(SF-01) When the application runs, the system shall allow users to add a new assignment. <br/>
<br/>
(SF-02) When the "add assignment" button is pressed, the system shall allow users to enter the assignment title, course name, due date, priority(low/medium/high), and description/notes. <br/>
<br/>
(SF-03) When an assignment is added, the system shall display all assignments in an incomplete list. <br/>
<br/>
(SF-04) When an assignment is selected, the system shall allow users to mark assignments as completed. <br/>
<br/>
(SF-05) When an assignment is marked as complete, the system shall move it to the completed list. <br/>
<br/>
(SF-06) When an assignment is selected, the system shall allow users to delete or edit assignments.  <br/>
<br/>
(SF-07) When assignments are added, the system shall sort assignments by due date or priority(low/medium/high).  <br/>
<br/>
(SF-08) When an assignment is marked as complete, the system shall give users points/credits.  <br/>
<br/>
(SF-09) When an assignment is past due, the system shall remove points/credits once per assignment. <br/>
<br/>
(SF-10) When the application runs, the system shall display the user’s total points.  <br/>
<br/>
(SF-11) When an assignment is added, the system shall notify users before the assignment due date.  <br/>
<br/>
(SF-12) When an assignment is selected, the system shall allow the user to view the assignment title, course name, due date, and description/notes. 

Architecture Requirements
(AR-01) The system shall use a three-tier architecture consisting of presentation layer, application layer, and database layer.

(AR-02) The presentation layer shall use JSP, HTML, and CSS to provide the user interface.

(AR-03) The application layer shall use Java Servlets running on Apache Tomcat to process user requests and business logic.

(AR-04) The backend MYSQL database shall store assignment information, deadlines, completion status, notes, priority, and reward points.

(AR-05) The application shall allow configurations for MYSQL connection using local or remote database credentials.

(AR-06) The system shall use JDBC for communication between the Java backend and MYSQL database.

(AR-07) The system shall support CRUD operations for assignment management.

(AR-08) The architecture shall separate frontend, backend, and database components to improve maintainability and scalability.

(AR-09) The system shall support multiple assignment records stored in the database.

(AR-10) The application shall be accessible through a web browser connected to the Apache Tomcat server.

(AR-11) The system shall support reward point calculations after assignment completion.

(AR-12) The system shall support assignment status tracking including not started, in progress, and completed states.

(AR-13) The system shall support assignment sorting by due date and priority.

(AR-14) The system shall support notification and reminder functionality for upcoming deadlines.

(AR-15) The system architecture shall allow future expansion such as mobile support, email notifications, and calendar integration.

# Project Setup
This project is implemented as a Java Servlet web application for Apache Tomcat.

## Files generated
src/main/java/edu/cs/
- Assignment.java
- AssignmentDAO.java
- AssignmentServlet.java
- RegisterServlet.java

src/main/webapp/
- index.jsp
- list.jsp
- login.jsp
- register.jsp
- assignment-form.jsp
- assignment-detail.jsp

src/main/webapp/WEB-INF/
- web.xml

Database:
- db-schema.sql

Build File:
- pom.xml

## How to run
1. Create the database with `db-schema.sql`.
2. Update MySQL credentials in `src/main/java/edu/cs/AssignmentDAO.java`.
3. Build with Maven: `mvn clean package`.
4. Deploy the generated `target/assignment-planner.war` to Tomcat.
5. Open `http://localhost:8080/assignment-planner/`.

## UI Enhancements
- Bootstrap styling for forms, tables, and cards.
- Dedicated assignment detail page.
- Clear separate views for incomplete and completed assignments.
- Reminder status and reward/points summary on the checklist page.
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
