const {Sequelize, DataTypes, literal} = require('sequelize');
const Constants = require('../constants/constants');
const models = require("../storage/models/models");

const createDatabaseConnectionAndGetModels = () => {
    //create the database connections
    const sequelize = new Sequelize(Constants.databaseConnection.connectionString);

    //create the directory model
    const directoryModel = sequelize.define('Directory', models.Directory,
        {
            tableName: 'directory',
            timestamps: false
        });

    //create the file model
    const fileModel = sequelize.define('File', models.File,
        {
            tableName: 'file',
            timestamps: false
        });

    //create the relationships
    directoryModel.hasMany(fileModel, {
        as: 'files', foreignKey: 'directory_id'
    });
    directoryModel.hasMany(directoryModel, {
        as: 'subdirectories', foreignKey: 'parent_directory_id'
    });
    directoryModel.belongsTo(directoryModel, {
        as: 'parentDirectory',
        foreignKey: 'parent_directory_id',
        foreignKeyConstraint: true
    })
    fileModel.belongsTo(directoryModel, {
        as: 'directory', foreignKey: 'directory_id', foreignKeyConstraint: true
    });

    return {
        Directory: directoryModel,
        File: fileModel,
        SequelizeInstance: sequelize
    }
};

module.exports = {
    createDatabaseConnectionAndGetModels
}