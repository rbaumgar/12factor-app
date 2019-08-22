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
oc scale dc/my12factorapp --replicas=1
oc patch svc/mysql -p '{"spec":{"ports":[{"name": "5000-tcp", "port": 5000, "targetPort": 3306}]}}'
oc set env dc/my12factorapp port=5000
# open http://12factorappdemo.$OPENSHIFT_IP.nip.io/api/db
echo "Configuration updated. Please check again http://12factorappdemo.$OPENSHIFT_IP.nip.io/api/db"
echo "Database port 3306 was bound to port 5000"
