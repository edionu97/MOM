from helpers.DatabaseHelper import DbHelper
from repo.IRepository import IRepository
from resources.manager.IResourcesManager import IResourcesManager


class Repository(IRepository):

    def __init__(self, helper: DbHelper, resources_manager: IResourcesManager):
        """
            Construct the repository
            :param helper: the db helper (create all the models + session maker)
        """
        super().__init__()
        self.__helper = helper
        self.__helper.create_database(resources_manager.get_constants())

    def add(self, value):
        session = self.__helper.session()
        session.add(value)
        session.commit()
        session.close()

    def all(self, table):
        return self.__helper.session().query(table).all()

    def filter(self, table, filters):
        return self.__helper.session().query(table).filter(filters).all()
