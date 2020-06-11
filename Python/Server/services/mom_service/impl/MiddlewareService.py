import json
import sys
import time

import stomp

from resources.manager.IResourcesManager import IResourcesManager
from services.main_service.IFileService import IFileService
from services.mom_service.IMiddlewareService import IMiddlewareService


class MiddlewareService(stomp.ConnectionListener, IMiddlewareService):

    def __init__(self, resource_manager: IResourcesManager, file_service: IFileService):
        self.__constants = resource_manager.get_constants()
        self.__connection = None

        # create a method for mapping the request types with the proper method from the service
        self.__method_mapping = {
            "FilterByName": lambda name: file_service.find_file_by_name(name=name),
            "FilterByContent": lambda text: file_service.find_file_by_text(text=text),
            "FilterByBinary": lambda sequence: file_service.find_file_by_binary(hex_binary_sequence=sequence),
            "FilterDuplicates": lambda _: file_service.find_duplicated_files(),
        }

    def start(self):
        print("Server started...")
        self.__configure(self.__constants)

        # keep de process running forever
        while True:
            time.sleep(1)

    def stop(self):
        if self.__connection is None:
            return
        self.__connection.disconect()
        print("Server stopped...")
        sys.exit(0)

    def on_message(self, headers, request):
        """
            This method handles the pushed request from client to server
            :param headers: the message headers
            :param request: the message itself
        """

        # transform the request from string to JSON object
        client_request: {} = json.loads(request)

        # process the request, and prepare the response
        response = {
            "response": self.__method_mapping[client_request["type"]](client_request["payload"]),
            "onRequest": client_request
        }

        # send the response to client
        self.__send(message=json.dumps(response))

    def __send(self, message):
        self.__connection.send(
            destination=self.__constants.serverresponsequeue, body=message)

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
        self.__connection.set_listener("middleware_listener", self)
        self.__connection.connect(wait=True)

        # subscribe the client to the queue
        self.__connection.subscribe(destination=constants.serverrequestqueue + "", id="python-server")
