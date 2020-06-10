from abc import abstractmethod


class IResourcesManager:

    @abstractmethod
    def getConstants(self):
        """
            This method is used in order to parse all the constants, defined into the constants file
            :return: the parsed version of the document (python object)
        """
        pass
