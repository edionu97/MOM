from enum import Enum


class OperationType(Enum):
    FilterByName = 1,
    FilterByContent = 2,
    FilterByBinary = 3,
    FilterDuplicates = 4
