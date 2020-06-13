/**
 * Method for sending message to destination
 * @param destination: message's destination
 * @param message: the message itself
 * @param client: the client
 */
const sendCallback = (destination, message, client) => {
    //get the client frame
    const frame = client.send(destination);
    //write into frame
    frame.write(JSON.stringify(message));
    //close the file
    frame.end();
}

/**
 * Method for calling a function when a message is pushed from a client into a destination
 * @param destination: message's destination
 * @param messageReceivedCallback: the message handler
 * @param client: the client
 */
const setReceiverCallback = (destination, client, messageReceivedCallback)  => {

    client.subscribe(destination, function (error, message) {
        //check if subscription error
        if (error) {
            console.log('subscribe error ' + error.message);
            return;
        }

        //read the string
        message.readString('utf-8', function (error, body) {
            //check if error
            if (error) {
                console.log('read message error ' + error.message);
                return;
            }

            messageReceivedCallback(JSON.parse(body));
        });
    });
}

module.exports = {
    sendCallback,
    setReceiverCallback
}