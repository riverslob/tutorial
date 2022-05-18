const http=require("http");
const os=require("os");

console.log("kubia server starting");

var handler=function (request,response){
    console.log("receive request from "+request.connection.remoteAddress);
    response.writeHeader(200);
    response.end("you hit "+ os.hostname+"\n");
}

var www = http.createServer(handler);
www.listen(8080);
