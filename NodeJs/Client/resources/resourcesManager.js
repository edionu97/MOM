const Constants = require('../constants/constants');
const open = require('open');

ResourcesManager = function () {
  return this;
};

ResourcesManager.prototype.writeToResourcesFile = function (content, openFile) {
  //write the content into file
  fs.appendFile(Constants.fileLocation, content, (err) => console.log(err));

  //check if we should open the file
  if (openFile !== true) {
    return this;
  }

  //open the file
  open(Constants.fileLocation, { wait: true });
  return this;
};

ResourcesManager.prototype.clearFile = function () {
  fs.writeFileSync(Constants.fileLocation, '', (_) => {});
};

module.exports = ResourcesManager;
