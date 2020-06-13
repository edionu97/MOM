const stompit = require('stompit');
const {serverConstants} = require('../constants/constants');
const {setReceiverCallback, sendCallback} = require('../utils/helpers');

/**
 * This method is use for processing the client request to the server
 * @param request: the json request
 * @param receiver: the message receiver
 * @param service: the service which contains all the application logic
 * @param dbConnection: the connection to the database
 * @param sendInfo: the additional information mandatory for sending the information
 * @returns {Promise<void>}
 */
const processRequest = async (request, receiver, service, dbConnection, sendInfo) => {
    //create the request handlers
    const requestMethods = {
        'FilterByName': async (payload) => await service.filterFilesByName(dbConnection, payload),
        'FilterByContent': async (payload) => await service.filterFilesByTextContent(dbConnection, payload),
        'FilterByBinary': async (payload) => await service.filterFilesByBinaryContent(dbConnection, payload),
        'FilterDuplicates': async (payload) => await service.filterByDuplicates(dbConnection, payload),
    }

    //compute the result and send it to the client
    sendCallback(sendInfo,
        {
            "response": await requestMethods[request['type']](request.payload),
            "onRequest": request
        },
        receiver);
};

const startStompService = (service, databaseConnection) => {
    //get the server constants
    const {connectOptions, sendHeaders, subscribeHeaders} = serverConstants;

    //connect tho the activeMq broker
    stompit.connect(connectOptions, function (error, client) {
        //check if there is anny connection error
        if (error) {
            console.log('connect error ' + error.message);
            return;
        }
        // set message receiver callback
        setReceiverCallback(subscribeHeaders, client,
            (message) => processRequest(message, client, service, databaseConnection, sendHeaders));
    });
};

module.exports = {
    startStompService
}