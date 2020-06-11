import hashlib

from sqlalchemy.sql.elements import and_

from helpers.Methods import Methods
from repo.IRepository import IRepository
from repo.model.Models import File
from services.main_service.IFileService import IFileService


class FileService(IFileService):

    def __init__(self, repository: IRepository):
        self.__repository = repository

    def find_file_by_name(self, name) -> []:
        """
            Gets the list of elements that respects a property
            :param name: the name of the file(or the part of the name)
            :return: a list of elements from database that have the name like the :param name
        """
        like_name = "%{name}%".format(name=name)
        return list(map(lambda x: x.path, self.__repository.filter(table=File, filters=File.name.like(like_name))))

    def find_file_by_text(self, text) -> []:
        """
            Gets the list of elements that respects a property
            :param text: the content of the file(or the part of the content)
            :return: a list of elements from database that have in their content something like the :param text
        """
        like_text = "%{text}%".format(text=text)

        # noinspection PyComparisonWithNone
        return list(map(
            lambda x: x.path,
            self.__repository
                .filter(table=File, filters=and_(File.binary_content == None, File.text_content.like(like_text)))))

    def find_file_by_binary(self, hex_binary_sequence: str) -> []:
        """
            Gets the list of elements that respects a property
            :param hex_binary_sequence: the content of the file(or the part of the content)
            :return: a list of elements from database that have in their content something like the :param binary
        """
        # noinspection PyComparisonWithNone
        binary_files: [] = self.__repository.filter(table=File, filters=File.text_content == None)

        # check the bytes
        result = []
        file: File
        for file in binary_files:
            array = [hex(el).replace("0x", "") for el in file.binary_content]
            if not Methods.is_subset(array, hex_binary_sequence.split(" ")):
                continue
            result.append(file.path)

        return result

    def find_duplicated_files(self) -> []:
        """
            :return: a  list of elements that are duplicated
        """

        file: File
        elements = {}
        for file in self.__repository.all(File):
            # calculate the sha3 for the files, depending on the file type
            hash_value = hashlib.sha3_256(file.text_content.encode()
                                          if file.text_content is not None
                                          else file.binary_content).hexdigest()

            # create a dictionary with the sha3 function value as key and as value for the key,
            # a list of elements that have that sha value
            if hash_value not in elements:
                elements[hash_value] = []

            # otherwise the file (for the moment) is not duplicated
            elements[hash_value].append(file.path)

        # create the result
        result = []
        for duplicate in filter(lambda lst: len(lst) > 1, map(lambda x: x[1], elements.items())):
            result.append(duplicate)
        return result
