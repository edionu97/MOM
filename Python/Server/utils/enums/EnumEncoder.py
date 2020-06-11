import json

from utils.enums.OperationType import OperationType

PUBLIC_ENUMS = {
    'OperationType': OperationType,
}


class EnumEncoder(json.JSONEncoder):
    def default(self, obj):
        if type(obj) in PUBLIC_ENUMS.values():
            return str(obj).split(".")[-1].strip()
        return json.JSONEncoder.default(self, obj)

