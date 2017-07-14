	var http = require("http");

	http.createServer(function (request, respone) {
		respone.writeHead(200, {'Content-Type': 'text/plain'});

		respone.end('Hello World\n');

	}).listen(8888);