#!/bin/sh
# Michael Lange
#
# 02.01.2020
#
version="V01.00.00"
logDir="${HOME}/.gossip/log"
varLog="${loghome}/gossipfx.out"

if [ ! -d "$logDir" ];
then
	echo "create log dir: $logDir"
	mkdir $logDir
else
	echo "log dir  ok: $logDir"
fi

echo "script version = ${version}" |tee -a ${varLog}
echo "log file = ${varLog}" |tee -a ${varLog}

echo "*****************************************************"|tee -a $varLog
echo ""|tee -a $varLog
echo "`date` gossip awt version $version start "|tee -a $varLog

applTargetDir='/opt/GossipAwt'
sourceDir='/tmp/GossipAwt'

# copy the application files
#-------------------
echo "copy files"|tee -a $varLog
if [ ! -d $applTargetDir ]; then
	mkdir -p $applTargetDir
fi
cp -r $sourceDir/* $applTargetDir/

exit 0