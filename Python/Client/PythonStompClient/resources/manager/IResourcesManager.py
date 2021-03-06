import subprocess
from abc import abstractmethod


class IResourcesManager:

    @abstractmethod
    def get_constants(self):
        """
            This method is used in order to parse all the constants, defined into the constants file
            :return: the parsed version of the document (python object)
        """
        pass

    @abstractmethod
    def write_to_resource_file(self, message, open_file=False):
        pass
