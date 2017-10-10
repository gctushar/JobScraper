/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.datastore;

import com.mongodb.MongoClient;
import jobscraper.common.Configuration;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author tushar
 */
public class DatabaseMongo {

    private volatile static Datastore datastore = null;

    public static Datastore getDatastore() {
        if (datastore == null) {
            Morphia morphia = new Morphia();
            morphia.mapPackage("jobscraper.job.entity");
            datastore = morphia.createDatastore(new MongoClient(), Configuration.DATABASE_NAME);
            datastore.ensureIndexes();

        }
        return DatabaseMongo.datastore;

    }

}