fs = require('fs');

Controller = function (sendCallback) {
  this.sendCallback = sendCallback;
  return this;
};

// callback for filtering the files by name
Controller.prototype.filterByName = function (reader, self) {
  // read the user request and convert it to server message
  self.sendCallback({
    type: 'FilterByName',
    payload: reader.question('Enter file name: ').toString().trim(),
  });
  return this;
};

Controller.prototype.filterByContent = function (reader, self) {
  return this;
};

Controller.prototype.filterByBinary = function (reader, self) {
  return this;
};

Controller.prototype.filterByDuplicates = function (reader, self) {
  return this;
};

Controller.prototype.onMessage = function (message) {
  fs.appendFile('helloworld.txt', JSON.stringify(message, null, 4), function (
    err
  ) {
    if (err) return console.log(err);
  });
  return this;
};

module.exports = Controller;
