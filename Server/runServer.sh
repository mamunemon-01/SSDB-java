#!/bin/bash

#putting the address of postgresql jar file in the CLASSPATH (replace address based on file location)
export CLASSPATH=./postgresql-42.3.6.jar:.
#export CLASSPATH=/media/mamun/Dev/E_Lab/CSE_OPS_Lin/813_CSE_DistributedAndCloudComputing/elephant/rmidb3/Server/postgresql-42.3.6.jar:.
#changing directory
#cd .
#cd /media/mamun/Dev/E_Lab/CSE_OPS_Lin/813_CSE_DistributedAndCloudComputing/elephant/rmidb3/Server/

#starting rmiregistry
rmiregistry 9000 &
echo "rmiregistry running"

java com.callbackcops.MyServer