# TRMS
TRMS, or Tuition Reimbursement Management System is a full-stack web application that allows employees to submit requests for reimbursements for courses, events, and certifications. These requests can then be approved or rejected by the employee's direct supervisor, department head, and a benefits coordinator while the employee is able to track the status of their requests.

## Technologies used
<ul>
  <li>JavaScript</li>
  <li>Java</li>
  <li>Hibernate</li>
  <li>AWS RDS</li>
</ul>

## Features
Current features ready:<br>
<ul>
  <li>Users can register, login, and change profile information</li>
  <li>Forms can be submitted from the front end</li>
  <li>Default form information is handled by the server</li>
</ul>
TODO:<br>
<ul>
  <li>Implement manager and department head functionality</li>
  <li>Smooth out the interface by ensuring the old page disappears when switching pages</li>
  <li>Fix connection between profile page and back end</li>
</ul>

## Getting started
Run `git clone https://github.com/krwerber/TRMS.git` in a terminal to clone the repository<br>
Import the project as a Maven project in prefered IDE<br>
Launch the project on a Tomcat server, it can be accessed via http://localhost:8080/static<br>

## Usage
Click register to make an account, you will be automatically logged in.<br>
Navigate to the 'My Forms' page to submit a form.
