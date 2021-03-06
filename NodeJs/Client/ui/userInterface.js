const reader = require('readline-sync');

// create the user options
const createOptions = () => {
  return`Press one of the options
1. For filtering by filename
2. For filtering by text content
3. For filtering by binary content
4. For filtering by duplicates
5. For stopping the application\n
Your option is: `;
};

//create the method dictionary
const createDictionary = (methodHolder) => {
  //create the dictionary
  const methodMap = new Map();

  //add dictionary options
  methodMap.set('1', methodHolder.filterByName);
  methodMap.set('2', methodHolder.filterByContent);
  methodMap.set('3', methodHolder.filterByBinary);
  methodMap.set('4', methodHolder.filterByDuplicates);
  methodMap.set('5', (_, __) => process.exit(0));

  //return the result
  return methodMap;
};

//main user interface object
let UserInterface = function (controller) {
  this.methods = createDictionary(controller);
  this.controller = controller;
  this.userOptions = createOptions();
  return this;
};

UserInterface.prototype.showUI = function () {
  console.clear();

  // read the user option from the keyboard
  const answer = reader.question(this.userOptions).toString().trim();

  // if the user enters a wrong option
  if (!this.methods.has(answer)) {
    console.log('Wrong option');

    // set the timer
    setTimeout(() => this.showUI(), 600);
    return this;
  }

  //call the option
  this.methods.get(answer)(reader, this.controller);

  // set the timer
  setTimeout(() => this.showUI(), 0);

  return this;
};

module.exports = UserInterface;
