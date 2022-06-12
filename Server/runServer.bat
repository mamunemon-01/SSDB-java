::cd Z:\E_Lab\CSE_OPS_Lin\813_CSE_DistributedAndCloudComputing\elephant\rmidb3\Server
::z:
set CLASSPATH=.;.\postgresql-42.3.6.jar;.
::starting rmiregistry
start rmiregistry 9000
echo "rmiregistry running"

java com.callbackcops.MyServer