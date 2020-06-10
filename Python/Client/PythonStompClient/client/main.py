from controllers.impl.UserInterfaceController import UserInterfaceController
from resources.manager.impl.ResourcesManager import ResourcesManager
from ui.impl.UserInteface import UserInterface

try:
    resources_manager = ResourcesManager()
    controller = UserInterfaceController(resourceManager=resources_manager)
    userInterface = UserInterface(controller=controller, resources_manager=resources_manager)
    userInterface.show_ui()
except Exception as ex:
    print(ex)
