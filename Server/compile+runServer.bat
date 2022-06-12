::putting the address of postgresql jar file in the CLASSPATH (replace address based on file location)

::changing directory
::cd /media/mamun/Dev/E_Lab/CSE_OPS_Lin/813_CSE_DistributedAndCloudComputing/elephant/rmidb3/Server/
::cd Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server
::z:

javac -d . Student.java
javac -d . SDB.java
javac -d . SDBGo.java
rmic com.callbackcops.SDBGo
::cd Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server\com\callbackcops
::cd C:\ProgramData\Oracle\Java\javapath\
::c:
::set CLASSPATH=Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server\;Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server\postgresql-42.3.6.jar;.
set CLASSPATH=.;.\postgresql-42.3.6.jar;.

start rmiregistry 9000
echo "rmiregistry running"

::cd Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server
::z:

::set CLASSPATH=Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server\postgresql-42.3.6.jar

javac -d . MyServer.java

::set CLASSPATH=Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server\
::set CLASSPATH=Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server\com\callbackcops;
::\SDBGo_Stub.class;Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server\com\callbackcops\MyServer.class;
::javap -c -classpath Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server\com\callbackcops;. MyServer
::start java -classpath Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server\com\callbackcops;. -Djava.rmi.server.codebase=file:Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server\com\callbackcops/ com.callbackcops.MyServer
java com.callbackcops.MyServer

cmd \k