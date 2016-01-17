#!/bin/bash
echo "load data">$1.ctl
echo "infile '/home/oracle/upload/"$1".csv'">>$1.ctl
echo "append into table combo">>$1.ctl
echo "fields terminated by ','">>$1.ctl
echo "trailing nullcols">>$1.ctl
echo "(">>$1.ctl
echo "\"accountTime\",">>$1.ctl
echo "\"userId\",">>$1.ctl
echo "\"code\",">>$1.ctl
echo "\"phone\",">>$1.ctl
echo "\"currentCode\",">>$1.ctl
echo "\"currentName\",">>$1.ctl
echo "\"accessCode\",">>$1.ctl
echo "\"accessName\",">>$1.ctl
echo "\"accessTime\",">>$1.ctl
echo "\"userCode\",">>$1.ctl
echo "\"userName\",">>$1.ctl
echo "\"channelCode\",">>$1.ctl
echo "\"channelName\",">>$1.ctl
echo "\"payCode\",">>$1.ctl
echo "\"payName\",">>$1.ctl
echo "\"policyName\",">>$1.ctl
echo "\"ruleName\",">>$1.ctl
echo "\"acceptTime\",">>$1.ctl
echo "\"brokerage\",">>$1.ctl
echo "\"productCode\",">>$1.ctl
echo "\"productName\"">>$1.ctl
echo ")">>$1.ctl
