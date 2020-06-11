from abc import abstractmethod


class IService:

    @abstractmethod
    def find_file_by_name(self, name) -> []:
        pass

    @abstractmethod
    def find_file_by_text(self, text) -> []:
        pass

    @abstractmethod
    def find_file_by_binary(self, binary) -> []:
        pass

    @abstractmethod
    def find_duplicated_files(self) -> []:
        pass
