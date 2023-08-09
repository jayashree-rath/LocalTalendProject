#!/bin/sh
cd `dirname $0`
 ROOT_PATH=`pwd`
java ${talend.job.jvmargs} -jar $ROOT_PATH/${talend.job.finalName}.jar --spring.config.additional-location=$ROOT_PATH/config/application.properties,$ROOT_PATH/config/contexts/${talend.job.context}.properties "$@"