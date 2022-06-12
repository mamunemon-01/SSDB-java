#!/bin/bash

#changing directory
#cd /media/mamun/Dev/E_Lab/CSE_OPS_Lin/813_CSE_DistributedAndCloudComputing/elephant/rmidb3/Client/

javac -d . Student.java
javac -d . SDB.java
javac -d . SDBGo.java
javac -d . MyClientFrame.java
rmic com.callbackcops.SDBGo

javac -d . MyClient.java
java com.callbackcops.MyClient

#uncomment following line to analyze missed errors
#eog pokemon.png