#!/bin/bash
mvn clean package tomcat7:undeploy tomcat7:deploy-only
