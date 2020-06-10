from abc import abstractmethod


class IUserInterfaceController:

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

    @abstractmethod
    def set_message_handler(self, onMessage):
        """
            This method sets the function that will be called when a message occurs
            :param onMessage: the callback
            :return: None
        """
        pass

    @abstractmethod
    def send(self, message):
        """
            Send the request to server
            :param message: the message that will be send
            :return: None
        """
        pass
