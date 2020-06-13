@echo off

cd ../../NodeJs/Client

node --experimental-worker %cd%/main.js

@pause 