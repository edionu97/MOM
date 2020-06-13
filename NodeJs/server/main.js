const Constants = require('./constants/constants')
const {createDatabaseConnectionAndGetModels} = require('./utils/dbutils');


const main = async () => {

    const {SequelizeInstance} = createDatabaseConnectionAndGetModels();
    await SequelizeInstance.sync({force: Constants.databaseConnection.createEveryTime});
}


(async () => {
    try {
        await main();
    } catch (e) {
        // Deal with the fact the chain failed
    }
})();
