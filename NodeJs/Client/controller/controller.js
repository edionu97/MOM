fs = require('fs');

Controller = function (stompConnection) {
  this.connection = stompConnection;
  return this;
};

Controller.prototype.filterByName = function (readerLine) {
  return 'da';
};

Controller.prototype.filterByContent = function (reader) {
  return this;
};

Controller.prototype.filterByBinary = function (reader) {
  return this;
};

Controller.prototype.filterByDuplicates = function (reader) {
  return this;
};

Controller.prototype.onMessage = function (message) {
  fs.writeFile('helloworld.txt', message, function (err) {
    if (err) return console.log(err);
    console.log('Hello World > helloworld.txt');
  });
  return this;
};

module.exports = Controller;
