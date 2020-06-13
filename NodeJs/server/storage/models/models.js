const {DataTypes, literal} = require('sequelize');

const Directory = {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    path: {
        type: DataTypes.STRING(200),
        allowNull: false
    },
    name: {
        type: DataTypes.STRING(50)
    },
    creation_date: {
        type: DataTypes.DATE,
        defaultValue: literal('CURRENT_TIMESTAMP')
    }
};

const File = {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    path: {
        type: DataTypes.STRING(200),
        allowNull: false
    },
    name: {
        type: DataTypes.STRING(50)
    },
    text_content: {
        type: DataTypes.STRING(5000),
    },
    binary_content: {
        type: DataTypes.BLOB("long")
    }
};

module.exports = {
    Directory: Directory,
    File: File
}