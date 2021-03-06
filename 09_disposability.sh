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
export OPENSHIFT_APP=`oc get route my12factorapp -o=jsonpath='{.spec.host}'`
for i in {1..3}; do curl http://$OPENSHIFT_APP/api/hello/$DEMOTEXT ; echo; sleep 1; done
echo "Destroying two processes"
oc delete pod --wait=false `oc get pods -l deployment=my12factorapp --no-headers=true| grep my12factorapp -m 2| awk '{ print $1 }'`
while true; do curl http://$OPENSHIFT_APP/api/hello/$DEMOTEXT ; echo; sleep 1; done
