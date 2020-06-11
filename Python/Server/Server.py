from lxml import objectify

from helpers.DatabaseHelper import DbHelper
from repo.impl.Repository import Repository
from services.impl.Service import Service

try:
    # read the constants folder
    with open('./resources/constants.xml', 'r') as file:
        constants = objectify.fromstring(file.read())

    # create the logic service
    service = Service(Repository(helper=DbHelper(), constants=constants))

    print(len(service.find_file_by_text(text="Lorem")))
except Exception as e:
    print(e)
