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

#oc start-build my12factorapp --from-dir=. --follow
#oc new-build java --binary=true --name=my12factorapp
#oc new-build --binary=true --name=my12factorapp --image-stream="openshift/ubi8-openjdk-21:1.18" --strategy docker

oc new-build --binary=true --name=my12factorapp --image="registry.redhat.io/ubi9/openjdk-21" --strategy source

oc start-build my12factorapp --from-file=target/12factor-app-2.0.0-runner.jar -F
