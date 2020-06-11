from abc import abstractmethod


class IMiddlewareService:

    @abstractmethod
    def start(self):
        """
            This method starts the connection to the activeMq queue
            :return: None
        """
        pass

    @abstractmethod
    def stop(self):
        """
            This method disconnects from the queue
            :return: None
        """
        pass
