var fs = require("fs");

fs.readFile("./test_data/input.txt", function (err, data) {
	if (err) {
		return console.error(err);
	};
	console.log("异步读取：" + data.toString());
});

var data = fs.readFileSync("./test_data/input.txt");
console.log("同步读取：" + data.toString());