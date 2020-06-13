const {createDatabaseConnectionAndGetModels} = require('./utils/dbutils');


const main = async () => {
    const {SequelizeInstance} = await createDatabaseConnectionAndGetModels();
}


(async () => {
    try {
        await main();
    } catch (e) {
        // Deal with the fact the chain failed
    }
})();
