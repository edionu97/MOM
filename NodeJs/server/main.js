const { repository } = require('./storage/repos/repository');
const { service } = require('./services/fileService');
const { startStompService } = require('./services/stompService');


const main = async () => {
    const databaseConnection = await repository.createDatabaseConnectionAndGetModels();
    console.log("Service started...")
    startStompService(service, databaseConnection);
}

(async () => {
    try {
        await main();
    } catch (e) {
        console.error(e);
    }
})();
