from helpers.DatabaseHelper import DbHelper
from repo.impl.Repository import Repository
from resources.manager.impl.ResourcesManager import ResourcesManager
from services.main_service.impl.Service import FileService

try:
    # create the resource manager
    resources_manager = ResourcesManager()

    # create the logic service
    service = FileService(
        repository=Repository(helper=DbHelper(), resources_manager=resources_manager))

    print(len(service.find_file_by_binary(hex_binary_sequence="ff d8 ff")))
except Exception as e:
    print(e)
