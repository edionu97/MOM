import repo.models.Directory;
import repo.repositories.impl.Repository;
import utils.persistence.HibernateUtils;

public class Main {

    public static void main(String ...args){
        var session = HibernateUtils.getInstance().getSessionFactory();

        var a = new Repository<>(Directory.class);

        var elements = a.filter(x-> x.getName().startsWith("a"));


    }
}
