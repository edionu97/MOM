import os
import pathlib
import subprocess
import webbrowser

from lxml import objectify

from resources.manager.IResourcesManager import IResourcesManager


class ResourcesManager(IResourcesManager):

    def __init__(self):
        """
            Constructs the object and setts the file to
        """
        super().__init__()

        # get the file path (the constants file path)
        self.__file = pathlib.Path(__file__)\
            .parent\
            .parent\
            .parent\
            .absolute()\
            .joinpath("constants.xml")

        self.__constants = None

    def get_constants(self):
        # if the file was not yet parsed, then parse the file
        if self.__constants is None:
            with open(self.__file, 'r') as file:
                self.__constants = objectify.fromstring(file.read())
            # clear the result file
            with open(self.get_constants().resultfilepath + "", 'w'):
                pass

        # get the constants
        return self.__constants

    def write_to_resource_file(self, message, open_file=False):

        # open the file for writing appending into it
        with open(self.get_constants().resultfilepath + "", 'a') as file:
            file.write(message)

        # if the open_file tag is disabled than do not open the file
        if not open_file:
            return

        # open the file with default editor enabled
        webbrowser.open(self.get_constants().resultfilepath + "")
