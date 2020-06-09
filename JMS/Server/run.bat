@echo off

start cmd.exe /k C:\PROGRA~1\Java\jdk-11\bin\java.exe -jar C:\Users\Eduard\Desktop\Master\WSMT\MOM\JMS\Server\build\libs\JmsServer.jar

cd ../../Apachemq/apache-activemq-5.15.2/bin && activemq start

@pause 