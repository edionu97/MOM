import json

import stomp

from resources.manager.IResourcesManager import IResourcesManager
from controllers.IUserInterfaceController import IUserInterfaceController


class UserInterfaceController(stomp.ConnectionListener, IUserInterfaceController):

    def set_message_handler(self, onMessage=print):
        self.__handler = onMessage

    def __init__(self, resource: IResourcesManager):
        self.__constants = resource.getConstants()
        self.__connection = None
        self.__handler = None

    def start(self):
        self.__configure(self.__constants)

    def stop(self):
        if self.__connection is None:
            return
        self.__connection.disconect()

    def send(self, message):
        self.__connection.send(
            destination=self.__constants.serverrequestqueue,
            body=json.dumps(message))

    def on_message(self, headers, body):
        """
            This method handles the pushed message from server to client
            :param headers: the message headers
            :param body: the message itself
        """
        if self.__handler is None:
            return
        self.__handler(json.loads(body))

    def __configure(self, constants):
        """
         This method is used in order to connect to the activeMq service
            :param constants: the constants defined into the constants file
            :return: None
        """
        # create the connection
        self.__connection = stomp.Connection(
            host_and_ports=[(constants.activemqaddress + "", int(constants.activemqport))],
            auto_content_length=False)

        # set the listener and connect to the queue
        self.__connection.set_listener("py_listener", self)
        self.__connection.connect(wait=True)

        # subscribe the client to the queue
        self.__connection.subscribe(destination=constants.serverresponsequeue + "", id="python-client")
