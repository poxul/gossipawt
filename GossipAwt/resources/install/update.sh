#!/bin/bash
#######################################################################
# Title      :    Update Gossip AWT
# Author     :    Michael Lange	
# Date       :    02.01.2020
####################################################################### 
# $1 source dir
# $2 target dir
sourceDir=$1
targetDir=$2

version="01.00.00"
log="${HOME}/.gossip/log/update.log"

echo "-----------------------------------------------------------------------"
echo "(II) update gossip awt" |tee -a $log
echo "(II) date  : `date`" |tee -a $log
echo "(II) script V$version" |tee -a $log
echo "-----------------------------------------------------------------------"

echo "(++) source dir : $sourceDir" |tee -a $log
echo "(++) target dir : $targetDir" |tee -a $log

echo "(II) update done"|tee -a $log
exit 0