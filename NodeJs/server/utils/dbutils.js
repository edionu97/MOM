const {Sequelize, DataTypes, literal} = require('sequelize');
const Constants = require('../constants/constants');
const {Directory, File} = require("../storage/models/models");

const createDatabaseConnectionAndGetModels = async () => {
    const {connectionString, createEveryTime, logging} = Constants.databaseConnection;
    //create the database connections
    const sequelize = new Sequelize(connectionString, {logging: logging});

    //create the directory model
    const directoryModel = sequelize.define('Directory', Directory,
        {
            tableName: 'directory',
            timestamps: false
        });

    //create the file model
    const fileModel = sequelize.define('File', File,
        {
            tableName: 'file',
            timestamps: false
        });

    //create the relationships
    directoryModel.hasMany(fileModel, {
        as: 'files',
        foreignKey: 'directory_id'
    });
    directoryModel.hasMany(directoryModel, {
        as: 'subdirectories',
        foreignKey: 'parent_directory_id'
    });
    directoryModel.belongsTo(directoryModel, {
        as: 'parentDirectory',
        foreignKey: 'parent_directory_id',
        foreignKeyConstraint: true
    })
    fileModel.belongsTo(directoryModel, {
        as: 'directory',
        foreignKey: 'directory_id',
        foreignKeyConstraint: true
    });

    //await database syncing
    await sequelize.sync({force: createEveryTime});

    return {
        Directory: directoryModel,
        File: fileModel,
        SequelizeInstance: sequelize
    }
};

module.exports = {
    createDatabaseConnectionAndGetModels
}