//package com.myretail;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.*;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import com.mongodb.Mongo;
//import com.myretail.models.Product;
//import com.myretail.repositories.ProductCatalogDBRepository;
//
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodProcess;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.MongodConfig;
//import de.flapdoodle.embed.mongo.config.RuntimeConfig;
//import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.process.extract.UserTempNaming;
//
//@Configuration
//@EnableMongoRepositories
//public class  MyRetailApplicationTests {
//
//    private static final String LOCALHOST = "127.0.0.1";
//    private static final String DB_NAME = "retail";
//    private static final int MONGO_TEST_PORT = 27028;
//
//    private ProductCatalogDBRepository repoImpl;
//
//    private static MongodProcess mongoProcess;
//    private static Mongo mongo;
//
//    private MongoTemplate template;
//
//
//    @BeforeClass
//    public static void initializeDB() throws IOException {
//
//        RuntimeConfig config = new RuntimeConfig();
//        config.setExecutableNaming(new UserTempNaming());
//
//        MongodStarter starter = MongodStarter.getInstance(config);
//
//        MongodExecutable mongoExecutable = starter.prepare(new MongodConfig(Version.V2_2_0, MONGO_TEST_PORT, false));
//        mongoProcess = mongoExecutable.start();
//
//        mongo = new Mongo(LOCALHOST, MONGO_TEST_PORT);
//        mongo.getDB(DB_NAME);
//    }
//
//    @AfterClass
//    public static void shutdownDB() throws InterruptedException {
//        mongo.close();
//        mongoProcess.stop();
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        repoImpl = new ProductCatalogDBRepository();
//        template = new MongoTemplate(mongo, DB_NAME);
//        repoImpl.setMongoOps(template);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        template.dropCollection(Product.class);
//    }
//
//    
//
//}