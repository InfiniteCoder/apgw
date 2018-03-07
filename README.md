[![Build Status](https://travis-ci.org/InfiniteCoder/apgw.svg?branch=testing)](https://travis-ci.org/InfiniteCoder/apgw)     [![Known Vulnerabilities](https://snyk.io/test/github/InfiniteCoder/apgw/badge.svg)](https://snyk.io/test/github/InfiniteCoder/apgw) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/d205284a8c554870afe668bd387efbdb)](https://www.codacy.com/app/InfiniteCoder/apgw?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=InfiniteCoder/apgw&amp;utm_campaign=Badge_Grade) [![Maintainability](https://api.codeclimate.com/v1/badges/953923a0414a683bfbf7/maintainability)](https://codeclimate.com/github/InfiniteCoder/apgw/maintainability) [![codecov](https://codecov.io/gh/InfiniteCoder/apgw/branch/testing/graph/badge.svg)](https://codecov.io/gh/InfiniteCoder/apgw) 



# APGW
Web application for automatically grading programs.

APGW provides a single platform for students and teachers to automate the process of grading programming assignments. Teachers can create assignments and generate grading reports, while students can upload programs. APGW automatically calculates grades based on the test cases provided by the teacher.
> **Note:** This is pre-release software. Docker builds are planned.

## Setup

### Creating Google OAuth2 project
1. You will need a Google OAuth2 project so that users can login using their google accounts. Create one by following [this tutorial](https://support.google.com/cloud/answer/6158849?hl=en)
2. Allow Google+ API access to the project. This is required to read basic details such as name and email-id of the user.

### Create a database
1. A MariaDB/MySQL database is required to store data.
2. Setup a database with whatever name you like. Note down the name, password and URL of the database.

### Installation
1. A Linux based operating system is required. A stable operating system such as Debian is recommended.
2. Install `jdk 8` and `maven`. Other versions of java are unsupported.
3. Install `docker` and start docker service. We recommend you get the latest version from docker website, as the packages in official repositories might be outdated.
  To start docker service on OS with systemd, you can use the command,
  
       sudo systemctl start docker
      
4. Get the `gcc:7.3` image.

       docker pull gcc:7.3
 
5. Get a copy of this code using git, or by downloading the zip

       git clone https://github.com/InfiniteCoder/apgw.git
    
6. Compile the code using maven

       cd apgw && mvn package
   this will generate a fat jar in target directory.
       
7. Set Environmental variables
   - `apgw_client_id`      :   Client id for the google OAuth2 project
   - `apgw_client_secret`  :   Client secret for the google OAuth2 project
   - `apgw_db_password`    :   Database user password
   - `apgw_db_url`         :   Database url
   - `apgw_db_username`    :   Database user
   - `file-path`(optional) :   Path where files(eg: Assignments, programs) should be stored. Defaults to user home. A directory named `apgw` will be created at this path.
   
8. Execute

       java -jar /target/*.jar
      
9. The application will listen on port 8080 on all IP addresses.

## License
APGW is licensed under GNU GPLv3. For more, see LICENSE.
