fs = require('fs');

Controller = function (sendCallback, resourcesManager) {
  this.sendCallback = sendCallback;
  this.resourcesManager = resourcesManager;
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
  // read the user request and convert it to server message
  self.sendCallback({
    type: 'FilterByContent',
    payload: reader.question('Enter file text: ').toString().trim(),
  });
  return this;
};

Controller.prototype.filterByBinary = function (reader, self) {
  // read the user request and convert it to server message
  self.sendCallback({
    type: 'FilterByBinary',
    payload: reader.question('Enter hex-content-binary: ').toString().trim(),
  });
  return this;
};

Controller.prototype.filterByDuplicates = function (_, self) {
  // read the user request and convert it to server message
  self.sendCallback({
    type: 'FilterDuplicates',
    payload: undefined,
  });
  return this;
};

Controller.prototype.onMessage = function (message, self) {
  self.resourcesManager.writeToResourcesFile(
    JSON.stringify(message, null, 4),
    true
  );
  return this;
};

module.exports = Controller;
