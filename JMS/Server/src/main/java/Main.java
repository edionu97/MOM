import utils.persistence.HibernateUtils;

public class Main {

    public static void main(String ...args){
        var session = HibernateUtils.getInstance().getSessionFactory();


    }
}
