from time import sleep

from resources.manager.impl.ResourcesManager import ResourcesManager
from services.impl.Service import Service

el = {
    "payload": ".txt",
    "type": "FilterByName"
}

service = Service(resource=ResourcesManager())

service.set_message_handler(print)

service.start()



# a.message_handler = lambda x: print(x["onResponse"])
#
# a.send(dict)


# conn.subscribe(destination='middleware-to-client', id="python-client", ack='auto')
# conn.send(destination="client-to-middleware", body=json.dumps(dict))

while True:
    sleep(1)
