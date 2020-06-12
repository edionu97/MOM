const UserInterface = require('./ui/userInterface');
const Constants = require('./constants/constants');
const Controller = require('./controller/controller');
const { Worker, isMainThread } = require('worker_threads');
const stompit = require('stompit');
const {
  stompSendCallback,
  stompReceiveCallback,
} = require('./utils/callbacks');

function main(client, userInterface, controller) {
  // if is in main thread
  if (isMainThread) {
    new Worker(__filename);
    userInterface.showUI();
    return;
  }
  //execute in worker thread
  stompReceiveCallback(client, controller.onMessage);
}

function startClient() {
  //connect to activemq broker
  stompit.connect(Constants.connectOptions, function (error, client) {
    //check if there is anny connection error
    if (error) {
      console.log('connect error ' + error.message);
      return;
    }

    //create the controler
    const controller = new Controller((message) =>
      stompSendCallback(message, client)
    );

    //create the user interface
    const userInterface = new UserInterface(controller);

    //start the main method
    main(client, userInterface, controller);
  });
}

startClient();
