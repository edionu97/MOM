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
        self.__resources_manger = resources_manager
        self.__controller.set_message_handler(lambda msg: self.__on_message(msg))

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
        pass

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

    # endregion
