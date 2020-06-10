from time import sleep

from resources.manager.impl.ResourcesManager import ResourcesManager
from controllers.impl.UserInterfaceController import UserInterfaceController

el = {
    "payload": ".txt",
    "type": "FilterByName"
}

controller = UserInterfaceController(resource=ResourcesManager())

controller.set_message_handler(print)

controller.start()



# a.message_handler = lambda x: print(x["onResponse"])
#
# a.send(dict)


# conn.subscribe(destination='middleware-to-client', id="python-client", ack='auto')
# conn.send(destination="client-to-middleware", body=json.dumps(dict))

while True:
    sleep(1)
