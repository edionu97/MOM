from lxml import objectify

from resources.manager.IResourcesManager import IResourcesManager


class ResourcesManager(IResourcesManager):

    def __init__(self):
        """
            Constructs the object and setts the file to
        """
        super().__init__()
        self.__file = "../resources/constants.xml"
        self.__constants = None

    def get_constants(self):
        # if the file was not yet parsed, then parse the file
        if self.__constants is None:
            with open(self.__file, 'r') as file:
                self.__constants = objectify.fromstring(file.read())

        # get the constants
        return self.__constants

    def write_to_resource_file(self, message, open_file=False):
        print(message)
        pass
