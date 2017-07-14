var events = require('events');

var eventEmitter = new events.EventEmitter();

var connHandler = function () {

	console.log('连接成功');

	eventEmitter.emit('data_received');
}

eventEmitter.on('connection', connHandler);

eventEmitter.on('data_received', function() {
	console.log('数据接收成功');
});

eventEmitter.emit('connection');

console.log('程序执行完毕');