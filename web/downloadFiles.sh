#!/bin/sh
#1.download tar.gz
#获取参数日期
if [  !  -n "$1"  ];then 
  Exe_Date=`date +%Y-%m-%d`
else
  Exe_Date=$1
fi
#要下载的文件或者批量文件格式
if [  !  -n "$2"  ];then
  fileName="*.tar.gz"
else
  fileName=$2
fi

USER=mysftp
#密码
PASSWORD=apex@2020
#下载文件目录
SRCDIR=/home/ftpdata/remoteData
#FTP目录(待下载文件目录)
DESDIR="/data/sftp/mysftp/upload/${Exe_Date}"
#IP
IP=66.10.61.122
#端口
PORT=22
#需要下载的文件为fileName

echo $fileName

echo "${USER},${PASSWORD} sftp://${IP}:${PORT}"
lftp -u ${USER},${PASSWORD} sftp://${IP}:${PORT}<<EOF
cd ${DESDIR}
lcd ${SRCDIR}

#支持正则表达式的方式
mget $fileName
by
EOF

echo "download done!"


#2. 解压文件并到指定日期文件夹并重命名为指定数据库表对应的del文件
#批量解压tar.gz文件
#dateDir=`date +%Y%m%d`
#按日期创建解压后的del文件存放目录
dateDir=$Exe_Date
if   [ ! -d "$dateDir" ];then 
  mkdir  $dateDir 
fi
#ls   "`date +%Y-%m-%d`*.tar.gz" |xargs -n1 tar zxvf
#解压前一天的数据
cd $SRCDIR
ls   *.tar.gz |grep -E "$Exe_Date"|xargs -n1 tar zxvf
for childFile in `ls *.del|grep  -E "[0-9]{4}[-/][0-9]{1,2}[-/][0-9]{1,2}_FRPT_"`
do 
  echo "$childFile"
  mv -f  "$childFile"  `echo "../$dateDir/$childFile"|sed 's/[0-9]\{4\}-[0-9]\{1,2\}-[0-9]\{1,2\}_FRPT_//g'`
done
pwd
cd /home/sftpdata/ 
#更改权限 让db2用户有权操作del文件
chmod -R 777 *
chown root:root `echo "$dateDir/*"`
echo "rename format *.del success!"


