import datetime

from sqlalchemy import Column, Integer, ForeignKey, String, DateTime, BLOB, LargeBinary, Binary
from sqlalchemy.orm import relationship

from helpers.DatabaseHelper import DbHelper


class Directory(DbHelper().modelBase):
    """
        This class is the orm model that is used as a template in directory table generation
        The table has two one-to-many relationships (one with the files table and one with the directory server itself)
    """
    __tablename__ = 'directory'

    id = Column('id', Integer, primary_key=True, autoincrement=True)
    path = Column('path', String(200), nullable=False)
    name = Column('name', String(50), index=True)
    creation_date = Column(DateTime, default=datetime.datetime.utcnow)

    parent_directory_id = Column('parent_directory_id', Integer, ForeignKey('directory.id'))

    files = relationship('File', back_populates='directory')
    subdirectories = relationship('Directory', back_populates='parent_directory')
    parent_directory = relationship('Directory')

    def __init__(self, name: String(50), path, object_id: Integer = None, parent_directory_id: Integer = None):
        """
            Creates the object
            :param name: the name
            :param parent_directory_id:  the id of the parent
        """
        self.id = object_id
        self.path = path
        self.name = name
        self.parent_directory_id = parent_directory_id


class File(DbHelper().modelBase):
    """
        This class is the orm model that is used as a template in directory table generation
        The table has two many-to-one relationship (with the directory table)
    """
    __tablename__ = 'file'

    id = Column('id', Integer, primary_key=True, autoincrement=True)
    name = Column('name', String(50), nullable=False)
    path = Column('path', String(200), nullable=False)
    binary_content = Column('binary_content', LargeBinary(length=(2 ** 32) - 1))
    text_content = Column('text_content', String(5000))
    directory_id = Column('directory_id', Integer, ForeignKey('directory.id'))
    directory = relationship('Directory', back_populates='files')

    def __init__(self, name, directory_id, path, binary_content=None, text_content=None):
        """
            Creates the object
            :param name:
            :param directory_id:
            :param binary_content:
            :param text_content:
        """

        self.name = name
        self.binary_content = binary_content
        self.text_content = text_content
        self.directory_id = directory_id
        self.path = path
