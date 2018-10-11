package net.tsystems;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class EMFactory {
    private static EntityManagerFactory emf = null;

    static {
        try {
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
            properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/sbb_db");
            properties.put("javax.persistence.jdbc.user", "root");
            properties.put("javax.persistence.jdbc.password", "root");

            emf = Persistence.createEntityManagerFactory("sbb", properties);
        } catch (Throwable e) {
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
