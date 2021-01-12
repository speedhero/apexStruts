#!/bin/sh
fileRootDir=/root/ftp/daydata
cd  $fileRootDir
echo `pwd`
for file in `ls  *.tar.gz|grep -E "[0-9]{4}[-/][0-9]{1,2}[-/][0-9]{1,2}"` 
d
     echo $file
     echo $fileRootDir
     echo " tar zxvf $file -C  $fileRootDir"  
       #解压缩文件
      tar zxvf $file -C  $fileRootDir
       #echo mv $file `echo  $file|sed 's/-/_/g'`
      #echo  $file|sed 's/-/_/g'|sed 's/.tar.gz//g' 
     fileDir=` echo $file|sed 's/.tar.gz//g' ` 
     echo $fileDir
     chmod -R  777 $fileDir
     #切换到解压缩后的目录 批量替换 格式化del文件
     cd $fileDir
     for childFile in `ls  *.del|grep -E "[0-9]{4}[-/][0-9]{1,2}[-/][0-9]{1,2}_FRPT_"` 
     do 
          echo  "$childFile" 
          echo  "$childFile" | sed 's/[0-9]\{4\}-[0-9]\{1,2\}-[0-9]\{1,2\}_FRPT_//g'
          echo  $childFile
          mv   "$childFile"   `echo  "$childFile"|sed 's/[0-9]\{4\}-[0-9]\{1,2\}-[0-9]\{1,2\}_FRPT_//g'` 
     done
    
     echo   "tar zcvf  ` echo $fileDir|sed 's/-//g' `.tar.gz" 
     echo   $fileRootDir"/"$fileDir"/*.del" 
    #获取压缩包名称 2020-10-31 改为 20201031
    trgFileName="` echo $fileDir|sed 's/-//g' `"".tar.gz"
    echo $trgFileName
    #压缩所有下面del文件 
    tar zcvf  $trgFileName   *.del
    #将压缩文件移动到上级 并切换到上级目录后 删除解压缩文件 以及原压缩文件
    mv $trgFileName ..
    cd .. 
    rm -rf $fileDir
    rm -rf $file
    touch $trgFileName".ok"
    #授权
    chmod -R 777 .
done 



