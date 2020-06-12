module.exports = {
  connectOptions: {
    host: 'localhost',
    port: 61613,
  },
  sendHeaders: {
    destination: 'client-to-middleware',
    'content-type': 'text/plain',
  },
  subscribeHeaders: {
    destination: 'middleware-to-client',
  },
};
