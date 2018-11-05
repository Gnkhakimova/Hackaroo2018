# Hackaroo2018  
# Doctorist  
## HealthAI - Yoddle - Use case  
### Team of 3  
[Source Code](https://github.com/Gnkhakimova/Hackaroo2018)  
[Youtube Video](https://youtu.be/il3nEsPA26M)  

### Objectives  
Nowadays time management is a big thing, people leave with schedule and we do not want to spend our time while waiting on something. Purpose of this application is to create Virtual Queue application for Walk-In Clinics, so users can get into the queue without staying in line. **Doctorist** is Android application which will help us avoid long queues. User logs into application using name and phone number. Application provides us name of clinics located near us and their phone number. Clinics can be filtered by specialists. Also user can check status of their waiting time and get into selected doctor's queue which will be recorded in database. Doctorist also send us messaged every 2 minutes with status update.   
### Steps  
Application is user friendly and easy to use  
### Login Page  
User can login into application using their name and phone number (e.g. 18164421212)  
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/LoginPage.JPG?raw=true)  
### Main Page  
On Main page user can see list of clinics which has name and phone number of clinics. We can filter list by specialist by selecting on of the 3 buttons: "Physician", "Dentist" and "Pediatrician", which will display us list of clinics which has selected specialist. If we are already got into the queue we can check out status by clicking on `Get Status` button. If we want to get more detailed information or get into queue we need to select clinic's name, which will take us to Clinic's page.    
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/MainPage1.JPG?raw=true)  
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/MainPage2.JPG?raw=true)
### Clinic Page  
Following page displays us list of doctors who works at selected clinic and it is location. List of doctors has name and specialty. In order to get into queue we need to select doctor's name, application will prompt us to confirm that we want to get into the queue, if we confirm, estimated wait time will be displayed to us. After that user will get messages to their phone every 2 minutes with updated waiting time.  
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/ClinicPage.JPG?raw=true)    
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/C.JPG?raw=true)  
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/ClinicPage1.JPG?raw=true)  
### Messages  
We get messaged with status update to our phone every 2 minutes:  
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/sms.JPG?raw=true)   
### Database  
MongoDB was used as a database for following application, were we are storing data in two Collections: Clinics and Queue.  
* Clinics - stores information about clinics, such as doctors, location, phone number, address and geo location.  
* Queue - stores information about whole queue, which has patients phone number and doctors name.  
In order to get and post data into database, Node.js was deployed into Heroku with several endpoints which helps application to get and post data into database.     
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/mongodb1.JPG?raw=true)  
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/mongodb2.JPG?raw=true)
### Server  
Node.js was used to create several endpoints in order to get and retrieve data from MongoDB. Endpoints such as: getIntoQueue, GetQueue, GetClinics, SendSMS, SearchDoctor and etc.  Node.js was deployed into Heroku, so our application could make API calls to get needed information from database.  
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/heroku.JPG?raw=true)  
### SMS - Twilio  
In order to send messaged to user Twilio API was used, which was also created in Node.js and deployed to Heroku. User receives messaged every 2 minutes with updated waiting time.  
![](https://github.com/Gnkhakimova/Hackaroo2018/blob/master/Documentation/twilio.JPG?raw=true)   
### UI  
User Interface of our application and simple and user friendly which makes it easier for user to navigate between pages.   
### Future works  
Our application can only send messages, in future work we could let user to get into queue using SMS as well. Also Since we are already storing geo location of clinics we could display location of clinics on map.   
### Technologies  
* Heroku  
* MongoDB  
* Twilio  
* Android Studio  
* Node.js  
* Web Storm  
* Postman (to test API calls)
### References  
1. https://www.twilio.com/  
2. https://dashboard.heroku.com/
3. https://mlab.com/  
4. https://www.w3schools.com/nodejs/    
 
