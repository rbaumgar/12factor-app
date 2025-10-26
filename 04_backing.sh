#!/bin/bash
# JBoss, Home of Professional Open Source
# Copyright 2016, Red Hat, Inc. and/or its affiliates, and individual
# contributors by the @authors tag. See the copyright.txt in the 
# distribution for a full listing of individual contributors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# http://www.apache.org/licenses/LICENSE-2.0
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,  
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
echo "Deploying a database"
oc new-app --name mysql -e MYSQL_USER=myuser -e MYSQL_PASSWORD=mypassword -e MYSQL_DATABASE=mydatabase openshift/mysql 
echo "Attach it to the app"
oc set env deployment/my12factorapp host=mysql username=myuser password=mypassword database=mydatabase 
oc annotate deployment/my12factorapp app.openshift.io/connects-to=mysql --overwrite
export OPENSHIFT_APP=`oc get route my12factorapp -o=jsonpath='{.spec.host}'`
echo "Open the URL: http://$OPENSHIFT_APP/api/db"
