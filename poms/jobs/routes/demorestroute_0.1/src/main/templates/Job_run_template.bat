%~d0
cd %~dp0
java ${talend.job.jvmargs} -jar %~dp0/${talend.job.finalName}.jar --spring.config.additional-location=%~dp0/config/application.properties,%~dp0/config/contexts/${talend.job.context}.properties %* 