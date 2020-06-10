from abc import abstractmethod


class IUserInterface:

    @abstractmethod
    def show_ui(self):
        """
            This method starts the user interface
            :return: 
        """
        pass

