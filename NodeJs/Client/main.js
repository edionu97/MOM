const UserInterface = require('./ui/userInterface');
const Constants = require('./constants/constants');
const Controller = require('./controller/controller');
const {Worker, isMainThread} = require('worker_threads');
const Stompit = require('stompit');
const {
    stompSendCallback,
    stompReceiveCallback,
} = require('./utils/callbacks');

const ResourcesManager = require('./resources/resourcesManager');

function startClient(client, userInterface, controller) {
    // if is in main thread
    if (isMainThread) {
        new Worker(__filename);
        userInterface.showUI();
        return;
    }
    //execute in worker thread
    stompReceiveCallback(client, (message) =>
        controller.onMessage(message, controller)
    );
}

function main() {
    //connect to activeMq broker
    Stompit.connect(Constants.connectOptions, function (error, client) {

        //check if there is anny connection error
        if (error) {
            console.log('connect error ' + error.message);
            return;
        }

        // create the resource manger
        const resourcesManager = new ResourcesManager();

        //clear the file
        resourcesManager.clearFile();

        //create the controller
        const controller = new Controller(
            (message) => stompSendCallback(message, client),
            resourcesManager
        );

        //create the user interface
        const userInterface = new UserInterface(controller);

        //start the main method
        startClient(client, userInterface, controller);
    });
}

main();
