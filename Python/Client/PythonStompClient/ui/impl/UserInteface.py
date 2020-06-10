import json
import sys

from controllers.IUserInterfaceController import IUserInterfaceController
from resources.manager.IResourcesManager import IResourcesManager
from ui.IUserInterface import IUserInterface
from utils.enums.EnumEncoder import EnumEncoder
from utils.enums.OperationType import OperationType


class UserInterface(IUserInterface):

    def __init__(self, controller: IUserInterfaceController, resources_manager: IResourcesManager = None):
        self.__controller = controller
        self.__options = self.__build_menu_options()
        self.__controller.set_message_handler(
            lambda msg: resources_manager.write_to_resource_file(
                message=self.__stringify_response(msg),
                open_file=True
            ))

    def show_ui(self):
        self.__controller.start()

        while True:
            option = self.__show_user_options()

            # check if the entered option is no in available options
            if option not in self.__options:
                print("Wrong option")
                continue

            self.__options[option]()

    def __build_menu_options(self):
        """
            This method constructs the user menu
            :return: a dictionary that contains all the options for the user
        """

        def option_helper(controller: IUserInterfaceController, message):
            """
                Helper method for creating the user options
                :param controller: the controller that sends request to the jms server
                :param message: the message itself
                :return: None
            """
            controller.send(
                message=json.dumps(message, cls=EnumEncoder)
            )

        return {
            "1": lambda: option_helper(self.__controller,
                                       {
                                           "payload": input("Enter the file name: ").strip(),
                                           "type": OperationType.FilterByName
                                       }),
            "2": lambda: option_helper(self.__controller,
                                       {
                                           "payload": input("Enter the file content: ").strip(),
                                           "type": OperationType.FilterByContent
                                       }),
            "3": lambda: option_helper(self.__controller,
                                       {
                                           "payload": input("Enter hex-bytes separated by spaces: ").strip(),
                                           "type": OperationType.FilterByBinary
                                       }),
            "4": lambda: option_helper(self.__controller,
                                       {
                                           "payload": None,
                                           "type": OperationType.FilterDuplicates
                                       }),
            "5": lambda: sys.exit(),
        }

    def __on_message(self, message):
        self.__resources_manager.write_to_resource_file(
            message=self.__stringify_response(message),
            open_file=True
        )

    # region Helpers

    @staticmethod
    def __show_user_options():
        print(
            "\nPress one of the options\n",
            "1. For filtering by filename\n",
            "2. For filtering by text content\n",
            "3. For filtering by binary content\n",
            "4. For filtering by duplicates\n",
            "5. For stopping the application\n"
        )
        return input("Your option is: ").strip()

    @staticmethod
    def __stringify_response(middleware_response: {}):

        # represent the list
        list_representation = UserInterface.__stringify_list(middleware_response['response'])

        # get the max number of characters from a line
        max_number_of_characters_from_line = len(max(list(list_representation.split("\n")), key=len))

        # construct the header line
        format1 = "The result of middleware call '{}' with payload '{}' is displayed below"
        format2 = "The result of middleware call '{}' is displayed below"
        header_line = format1.format(middleware_response['onRequest']['type'],
                                     middleware_response['onRequest']['payload']) \
            if middleware_response['onRequest']['payload'] is not None \
            else format2.format(middleware_response['onRequest']['type'])

        # compute the max line
        max_number_of_characters_from_line = max([max_number_of_characters_from_line, len(header_line)])

        # create the representation
        return "\n" * 2 + \
               "=" * max_number_of_characters_from_line + \
               "\n" * 2 + header_line + \
               "\n" * 2 + \
               list_representation + \
               "=" * max_number_of_characters_from_line + \
               "\n" * 2

    @staticmethod
    def __stringify_list(array, tabs=0):
        """
            Pretty print a list
            :param array: the list that we want to print
            :param tabs: the number of tabs
            :return: the string representation of the list
        """
        result = "    " * tabs + "[\n"
        for list_element in array:
            # if the list element is instance of a list than we process it as list
            # (we add one more tab in front of the elements)
            if isinstance(list_element, list):
                result += UserInterface.__stringify_list(list_element, tabs=tabs + 1)
                continue

            # otherwise add the element to list (with specific number of elements)
            result += "   " * (tabs + 1) + list_element + "\n"

        return result + "    " * tabs + "]\n"

    # endregion
