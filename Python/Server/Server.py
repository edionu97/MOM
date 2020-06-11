from helpers.DatabaseHelper import DbHelper
from repo.impl.Repository import Repository
from resources.manager.impl.ResourcesManager import ResourcesManager
from services.main_service.impl.FileService import FileService
from services.mom_service.impl.MiddlewareService import MiddlewareService

try:
    # create the resource manager
    resources_manager = ResourcesManager()

    # create the logic service
    service = FileService(
        repository=Repository(helper=DbHelper(), resources_manager=resources_manager))

    # start the service
    middleware_service = MiddlewareService(resource_manager=resources_manager, file_service=service)
    middleware_service.start()
except Exception as e:
    print(e)
