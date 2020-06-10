from lxml import objectify

from resources.manager.IResourcesManager import IResourcesManager


class ResourcesManager(IResourcesManager):

    def __init__(self):
        """
            Constructs the object and setts the file to
        """
        super().__init__()
        self.__file = "../resources/constants.xml"

    def getConstants(self):
        with open(self.__file, 'r') as file:
            return objectify.fromstring(file.read())
