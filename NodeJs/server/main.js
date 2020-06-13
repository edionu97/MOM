const {repository} = require('./storage/repos/repository');
const {service} = require('./services/service');


const main = async () => {
    const databaseConnection = await repository.createDatabaseConnectionAndGetModels();
    const result = await service.filterByDuplicates(databaseConnection, "ff d8 ff");
    console.log(result);
}


(async () => {
    try {
        await main();
    } catch (e) {
        // Deal with the fact the chain failed
    }
})();
