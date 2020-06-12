const UserInterface = require('./ui/userInterface');
const Constants = require('./constants/constants');
const Controller = require('./controller/controller');
const { Worker, isMainThread } = require('worker_threads');

const Stomp = require('stomp-client');

function main(controller, stompConnection) {

  if (isMainThread) {
    // create the interface
    const interface = new UserInterface(controller);

    // create a new thread and run the 
    interface.showUI();
    return;
  } 

  //start the connection
  stompConnection.connect(function (_) {
    stompConnection.subscribe(`/queue/${Constants.serverResponseQueue}`, (message, _) => controller.onMessage(message))
  });
}

//create the stomp connection
const stompConnection = new Stomp(
  Constants.serverAddress,
  Constants.serverPort
);

// create the controller
const controller = new Controller(stompConnection);

main(controller, stompConnection);