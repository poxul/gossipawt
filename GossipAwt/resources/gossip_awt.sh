#!/bin/bash
#
# 
version="V01.05"
#   
# 29.11.2019 
#
#  Markers: (--) probed, (**) from config file, (==) default setting, 
# (++) from command line, (!!) notice, (II) informational, (WW) warning, 
# (EE) error, (NI) not implemented, (??) unknown.
#
# Script logging output file
application_home_dir="${HOME}/.gossip"
application_base_dir="/opt/GossipAwt"
log_dir="${application_home_dir}/log"
log_file="${log_dir}/out.txt"

on_exit()
{
	echo "(II) Exiting..."  | tee -a $log_file
	
	# define Variables
	#-----------------
	sigFile='.gossip_awt_signal'

	# remove files
	#-----------------
	if [ -f $sigFile ] 
	then
		echo "(II) Removing signal file"  | tee -a $log_file
		rm $sigFile  | tee -a $log_file
	fi
	# Need to exit the script explicitly when done.
	# Otherwise the script would live on, until system
	# realy goes down, and KILL signals are send.
	#
	exit 0
}

# check home folder
if [ -d "${application_home_dir}" ]
then
	if [ -d "${log_dir}" ]
	then
		echo "[--] home folder ok!"
	else 
		echo "[--] create log folder"
		mkdir -p "${log_dir}"
	fi
else
	echo "[--] create home folder"
	mkdir -p "${log_dir}"
fi

# HOME directory
cd "${application_home_dir}"

# Handle log file
if [ -f "${log_dir}/out.txt.1" ]
then
	cp "${log_dir}/out.txt.1" "${log_dir}/out.txt.2"
	echo "backup out1"
fi

if [ -f $log_file ]
then
	cp "$log_file" "${log_dir}/out.txt.1"
fi

# create new out file
echo "(II) Start gossip script ${version}" | tee $log_file 
echo "(II) `date`" | tee -a $log_file 

# list out files 
outs=$(find ${log_dir}/out.txt* -type f 2>/dev/null)
for out in $outs 
do
	echo "(II) Log: $out -- `stat -c%s $out` byte-- " | tee -a $log_file
done

numLogs=$(find ${log_dir}/* |wc -l);
sizeLogs=$(du -h ${log_dir} 2>|/dev/null | awk '{print $1}')

echo "(II) Log dir size: $sizeLogs ($numLogs)" | tee -a $log_file

# Command line options Java VM
java_flags="-Xmx1000m \
	-XX:+HeapDumpOnOutOfMemoryError \
	-XX:-AllowUserSignalHandlers"
		
# application class
application_class='gossip.run.GossipAwt'

tcp_port='45049'
tcp_host='localhost'

# Key-File to start debugging
debugFile="/opt/gossip/data/debug"

# Key-File to start remote debugging
remoteDebugFile="/opt/gossip/data/remotedebug"

# Key-File to start remote profiling
profilingFile="/opt/gossip/data/remoteprofile"

#
# Execute function on trap
#
trap "on_exit" 0 2 15 

#Clean up dumps
cntHp=$(find *.hprof -type f 2>/dev/null| wc -l )  
echo "(II) HP dumps $cntHp"  | tee -a $log_file
if [ $cntHp -gt 2 ]
then
	echo "(WW) CLEAN HP dumps $cntHp"  | tee -a $log_file
	ls -t *.hprof | tail -n +3 | xargs rm -rf
fi

cntHs=$(find hs_err_pid*.log -type f 2>/dev/null| wc -l)
echo "(II) HS dumps $cntHs"  | tee -a $log_file
if [ $cntHs -gt 2 ]
then
	echo "(WW) CLEAN HS dumps $cntHs"  | tee -a $log_file
	ls -t hs_err_pid*.log | tail -n +3 | xargs rm -rf
fi

# Test debug and create log
if [ -e "$debugFile" ] 
then
	# Enable remote debugging	
	host="$(hostname)"
	port=45041
	echo "(WW) Debug application host = $host:$port"  | tee -a $log_file
	# Visula VM
	debug_flags="-Dcom.sun.management.jmxremote.port=${port} \
	-Dcom.sun.management.jmxremote.ssl=false \
	-Dcom.sun.management.jmxremote.authenticate=false \
	-Djava.rmi.server.hostname=${host}"

	# Prepare command line options of the JavaVM
	flags="${debug_flags} ${java_flags}"
else
	# Usual start of HMI
	echo "(II) Standard flags"  | tee -a $log_file
	# Prepare command line options of the JavaVM
	flags="${java_flags}"
fi

if [ -e "$remoteDebugFile" ] 
then
	# Enable remote debugging	
	echo "(WW) Remote debug application port 45223"
	# Remote debug eclipse
	remotedebug_flags="-Djava.compiler=NONE \
-Xdebug \
-Xnoagent \
-Xrunjdwp:transport=dt_socket,address=0.0.0.0:45223,server=y,suspend=y"
	# Prepare command line options of the JavaVM
	flags="${remotedebug_flags} ${flags}"
else
	if [ -e "$profilingFile" ] 
	then
		# Enable remote profiling 
		echo "(WW) Profiling enabled on port 45223"
		# Profiling flags
		profiling="-javaagent:/fwa/hmi/lib/agent.jar=waitconn=false,verbosity=1,javaagent"
		# Prepare command line options of the JavaVM
		flags="${profiling} ${flags}"
	fi
fi

# Java Environment
JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64/"
#JAVA_HOME="/usr/lib/jvm/java-7-openjdk-amd64/"
PATH=$JAVA_HOME/bin:$PATH

# Test java
javaVersion=$(java -version 2>&1 >/dev/null | grep ' version' | awk '{print $3}')
echo "(II) Java $javaVersion"  | tee -a $log_file 

# Start application
echo "(II) Start application >${application_class}< ${tcp_host} ${tcp_port}"  | tee -a $log_file
java ${flags} -cp "${application_base_dir}/lib/*" \
-Dlog4j.configurationFile=${application_base_dir}/log4j2.xml \
${application_class} -b ${application_base_dir} -h ${tcp_host} -p ${tcp_port} &>> $log_file 

# END
echo "(II) `date`" | tee -a $log_file 
echo "(!!) Application done..."  | tee -a $log_file
exit 0