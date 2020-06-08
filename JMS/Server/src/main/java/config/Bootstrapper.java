package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repo.models.File;
import repo.repositories.IRepository;
import repo.repositories.impl.Repository;
import utils.search.ISearcher;
import utils.search.impl.KmpSearcher;

@Configuration
public class Bootstrapper {

    @Bean
    public IRepository<File> directoryRepository(){
        return new Repository<>(File.class);
    }

    @Bean
    public ISearcher searcher(){
        return new KmpSearcher();
    }

}
