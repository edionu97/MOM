from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

from helpers.Singleton import Singleton


class DbHelper(metaclass=Singleton):
    @property
    def modelBase(self):
        """
        Returns the base class of all the models
        :return: an instance of a model's base class
        """
        return self.__modelBase

    @property
    def session(self):
        """
        :return: a session object creator
        """
        return self.__session

    def __init__(self):
        self.__modelBase = declarative_base()
        self.__session = None

    def create_database(self, constants):
        """
        Creates the database(all the tables + the session maker
        :return: nothing
        """

        engine = create_engine(str(constants.connectionstring), echo=bool(constants.showsql))

        if bool(constants.createeverytime):
            self.__modelBase.metadata.drop_all(bind=engine)

        self.modelBase.metadata.create_all(bind=engine)
        self.__session = sessionmaker(bind=engine)
