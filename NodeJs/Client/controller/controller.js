Controller = function (stompConnection) {
  this.connection = stompConnection;
  return this;
};

Controller.prototype.filterByName = function (reader) {
  return this;
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
  return this;
};

module.exports = Controller;
