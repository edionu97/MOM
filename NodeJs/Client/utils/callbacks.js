const Constants = require('../constants/constants');

function sendCallback(message, client) {
  //get the client frame
  const frame = client.send(Constants.sendHeaders);
  //write into frame
  frame.write(JSON.stringify(message));
  //close the file
  frame.end();
}

function receiveCallback(client, messageReceivedCallback) {
  client.subscribe(Constants.subscribeHeaders, function (error, message) {
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
  stompSendCallback: sendCallback,
  stompReceiveCallback: receiveCallback,
};
