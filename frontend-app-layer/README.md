### frontend-app-layer

   This is the UI or front layer of the <b>customer-monthly-balance</b> application. This module shows the customer balance details in a nicer UI. This module talks with rest module to fetch customer balances and shows in UI.

### Setup Development Environment

- ##### Requirement
  - Node.js v16.10.0, install it with [NVM](https://github.com/creationix/nvm).

- ##### Compile and build it
  - npm install && npm start
  - Wait for message `webpack: Compiled successfully`

- ##### IDE
  - Import the [frontend-app-layer](https://github.com/prashantapal/customer-monthly-balance/tree/master/frontend-app-layer) directory.

### Run it in local environment
  - In the project directory, run the command `npm install && npm start`.
  - The UI application runs on port 9355.
  - Open [http://localhost:9355](http://localhost:9355)
  - To work this UI application correctly, you need to run rest layer in local. Please check rest layer deployment step from [here](https://github.com/prashantapal/customer-monthly-balance/tree/master/rest-app-layer#run-it-in-local-environment).

### Run it in remote environment

  - In remote server, the rest application should run in http://localhost:9357. Please check rest layer deployment step from [here](https://github.com/prashantapal/customer-monthly-balance/blob/master/rest-app-layer/README.md#run-it-in-remote-environment).
  - In remote server, we need web server, so you need to install either nginx or apache httpd.
  - Here steps are given for httpd server. Install apache httpd in remote server from [here](https://httpd.apache.org/docs/2.4/install.html).
  - In local system, build the project using `npm install && npm run build`.
  - After local build, the new directory `build` is created inside frontend-app-layer directory.
  - Transfer the content the of build directory to remote location directory `/var/www/html`.
  - Configure the domain name in conf directory `/etc/httpd/conf.d`.
  - Start the httpd server using `systemctl start httpd`.
  - Stop the httpd server using `systemctl stop httpd`.
  - The UI application can be opened using the domain name configured in httpd conf file.
