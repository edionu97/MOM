const { Op } = require("sequelize");
const { SHA3 } = require('sha3');

/**
 * This function it's used for getting all the files that have in their name a pattern
 * @param File: the File table
 * @param name: the name pattern
 */
const filterFilesByName = async ({File}, name) => {
    //select f.path from File f where f.name like %name%
    return File
        .findAll({
            where: {
                name: {
                    [Op.like]: `%${name}%`
                }
            }
        })
        .map(file => file.path);
}

/**
 * This function its used in order to get all the files that have in their text_content something like @param text
 * @param File:  the file table
 * @param text:
 * @returns {Promise<Uint8Array|BigInt64Array|*[]|Float64Array|Int8Array|Float32Array|Int32Array|Uint32Array|Uint8ClampedArray|BigUint64Array|Int16Array|Uint16Array>}
 */
const filterFilesByTextContent = async ({File}, text) => {
    //select f.path from File f where f.text like %text% and f.binary_content = null
    return File
        .findAll({
            where: {
                text_content: {
                    [Op.like]: `%${text}%`
                },
                binary_content: {
                    [Op.eq]: null
                }
            }
        })
        .map(file => file.path);
}

/**
 * This function it is used in order to filter binary files by their content
 * @param File: the file table
 * @param binary: the binary hex bytes
 * @returns {Promise<BigUint64Array>}
 */
const filterFilesByBinaryContent = async ({File}, binary) => {
    //split the byte string into byte array and create a js buffer
    const byteBuffer = Buffer.from(
        binary.split(" ").map(x => {
            return parseInt(x, 16);
        }))

    //get binary files
    const binaryFiles = await File
        .findAll({
            where: {
                text_content: {
                    [Op.eq]: null
                }
            }
        });

    //filter the list and get those files that respect the given condition
    return binaryFiles
        .filter(binaryFile => binaryFile.binary_content.includes(byteBuffer))
        .map(file => file.path);
}

const filterByDuplicates = async ({File}, _) => {
    //get the sha3 function
    const sha3 = new SHA3(256);

    //get all the files from database
    const files = await File.findAll();

    //compute the hash value dictionary
    const hashValues = new Map();
    files.forEach(file => {
        // get the file bytes
        const byteBuffer = file.text_content !== null
            ? Buffer.from(file.text_content)
            : file.binary_content;

        //compute the hash value for each file, based on file content
        sha3.reset();
        sha3.update(byteBuffer);
        const hash = sha3.digest('hex');

        // add into hash value dictionary
        if(!hashValues.has(hash)) {
            hashValues.set(hash, []);
        }
        hashValues.get(hash).push(file.path);
    })

    //get duplicates
    const array = [];
    hashValues.forEach((value, _, __) => {
        if(value.length <= 1) {
            return;
        }
        array.push(value);
    });
    return array;
}


module.exports = {
    service: {
        filterFilesByName,
        filterFilesByTextContent,
        filterFilesByBinaryContent,
        filterByDuplicates
    }
}