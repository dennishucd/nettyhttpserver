# nettyhttpserver
a simple http server based on netty

Usage:
[1] start the httpserver
mvn assembly:assembly
java -jar nettyhttpserver-0.1.0-SNAPSHOT.jar 

[2] Test with postman
POST http://192.168.0.101:9001/rest/push/token
Content-Type:application/json
{"userId":"crtb9527","token":"e10adc3949ba59abbe56e057f20f883e"}

