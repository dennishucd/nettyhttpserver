# nettyhttpserver
a simple http server based on netty

Usage:
[1] compile and start the httpserver
mvn assembly:assembly
java -jar nettyhttpserver-0.2.0-SNAPSHOT.jar host port
example:
java -jar nettyhttpserver-0.2.0-SNAPSHOT.jar 192.168.2.12 9001

[2] Test with postman
POST http://192.168.0.101:9001/rest/push/token
Content-Type:application/json
{"userId":"test","token":"e10adc3949ba59abbe56e057f20f883e"}

