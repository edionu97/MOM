from abc import abstractmethod


class IFileService:

    @abstractmethod
    def find_file_by_name(self, name) -> []:
        pass

    @abstractmethod
    def find_file_by_text(self, text) -> []:
        pass

    @abstractmethod
    def find_file_by_binary(self, hex_binary_sequence: str) -> []:
        pass

    @abstractmethod
    def find_duplicated_files(self) -> []:
        pass
