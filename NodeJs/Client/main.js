const UserInterface = require('./ui/userInterface');
const Constants = require('./constants/constants');
const Controller = require('./controller/controller');

const Stomp = require('stomp-client');

//create the stomp connection
const stompConnection = new Stomp(
  Constants.serverAddress,
  Constants.serverPort
);

//create the controller
const controller = new Controller(stompConnection);

//create the interface
const interface = new UserInterface(controller);

//start the connection
stompConnection.connect(function (_) {
  stompConnection.subscribe(
    `/queue/${Constants.serverResponseQueue}`, (message, _) => controller.onMessage(message)
  );

  //show the interface
  interface.showUI();
});
