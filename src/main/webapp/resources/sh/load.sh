ORACLE_HOME=/home/oracle/product/11g/db_1
export ORACLE_HOME
PATH=$PATH:$ORACLE_HOME/bin
export PATH
export NLS_LANG=AMERICAN_AMERICA.ZHS16GBK
/home/oracle/upload/test.sh $1
sqlldr userid=ejqd/gzlt_2014@channel control="/home/oracle/upload/"$1".ctl"
