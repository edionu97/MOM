package services.impl;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import repo.models.File;
import repo.repositories.IRepository;
import services.IService;
import utils.search.ISearcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ComponentScan(basePackages = "config")
public class Service implements IService {

    private final IRepository<File> repository;
    private final ISearcher searcher;

    @Autowired
    public Service(final IRepository<File> repository, ISearcher searcher) {
        this.repository = repository;
        this.searcher = searcher;
    }

    @Override
    public List<String> findFilesByName(final String name) {
        //get those files that, in their name, contain @param name
        return repository
                .filter(x -> x.getName().contains(name))
                .stream()
                .map(File::getPath)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findFilesByText(final String text) {
        //get those files that, in their content, contains a given text
        return repository
                .filter(x -> !x.isBinary() && x.getTextContent().contains(text))
                .stream()
                .map(File::getPath)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findFilesByBinary(final int[] binary) {

        //convert the binary pattern into string pattern
        var pattern = Arrays
                .toString(Arrays.stream(binary).map(x -> (byte) x).toArray())
                .replaceAll("[\\[\\]]", "");

        //keep only those files that have the given binary sequence
        return repository
                .filter(File::isBinary)
                .stream()
                .filter(file -> {
                    try {
                        //transform the binary content into string list of bytes
                        var binaryToString = Arrays
                                .toString(file.getBinaryContent().getBinaryStream().readAllBytes())
                                .replaceAll("[\\[\\]]", "");

                        //using string search algorithm, search if te pattern is in binary content
                        return searcher.isIn(pattern, binaryToString);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    return false;
                })
                .map(File::getPath)
                .collect(Collectors.toList());
    }


    @Override
    public List<List<String>> findDuplicateFiles() {
        //declare the distinct map
        var map = new HashMap<String, List<File>>();

        //construct the hash map (key is the hash value and as value a list of files that have that hash value)
        repository.getAll().forEach(file -> {
            var sha3 = new SHA3.Digest512();

            try {
                //get the hash byte value
                var hashByteValue = file.isBinary()
                        ? sha3.digest(
                            file.getBinaryContent().getBinaryStream().readAllBytes())
                        : sha3.digest(file.getTextContent().getBytes());

                //convert the key from byte array to hex array
                var key = Hex.toHexString(hashByteValue);

                //if the key is not in the dictionary than add an empty list
                //add the file into the sha list
                map.computeIfAbsent(key, x -> new ArrayList<>());
                map.get(key).add(file);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //get only the duplicates files as bulks
        return map
                .keySet()
                .stream()
                .filter(key -> map.get(key).size() > 1)
                .map(map::get)
                .map(files -> files
                        .stream()
                        .map(File::getPath)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

}
