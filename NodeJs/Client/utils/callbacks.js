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

const stringifyList = (list, tabs) => {
  let string = '    '.repeat(tabs).concat('[\n');

  for (const listElement of list) {
    // check if element is array
    if (Array.isArray(listElement)) {
      string = string.concat(stringifyList(listElement, tabs + 1));
      continue;
    }
    //concat the list element
    string = string.concat('    '.repeat(tabs + 1)).concat(listElement).concat("\n");
  }

  // finish the representation as string
  return string.concat('    '.repeat(tabs).concat(']\n'));
};

const stringifyResponse = (request, responseList) => {

  // get the list representation
  const representation = stringifyList(responseList, 0);

  // get the length of the longest line from list representation
  let maxLength = Math.max(...representation.split('\n').map(x => x.length));

  // create the header line
  const representationHeader = request['payload'] !== null
      ? `The result of middleware call ${request['type']} with payload ${request['payload']} is displayed below`
      : `The result of middleware call ${request['type']} is displayed below`;

  // recompute the maxlength
  maxLength = Math.max(maxLength, representationHeader.length);

  //create the format
  return '\n'.repeat(2)
      .concat('='.repeat(maxLength))
      .concat("\n".repeat(2))
      .concat(representationHeader)
      .concat("\n".repeat(2))
      .concat(representation)
      .concat("=".repeat(maxLength))
      .concat("\n".repeat(2));
}


module.exports = {
  stompSendCallback: sendCallback,
  stompReceiveCallback: receiveCallback,
  stringifyList: stringifyList,
  stringifyResponse: stringifyResponse
};
