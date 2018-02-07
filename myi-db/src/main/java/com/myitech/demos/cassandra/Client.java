package com.myitech.demos.cassandra;

import com.datastax.driver.core.*;

import java.util.concurrent.*;

/**
 * Description:
 * <p>
 * Created by A.T on 2018/02/07
 */
public class Client {
    private Cluster.Builder builder;
    private Cluster m_cluster;
    private Session session;


    String SKINNY_INSERT = "INSERT INTO skinny (id, time, value) values (?, ?, 5);";

    PreparedStatement skinnyStatement;
    PreparedStatement wideStatement;

    public Client() {
        builder = new Cluster.Builder().addContactPoint("127.0.0.1");
        m_cluster = builder.build();
        session = m_cluster.connect("assess");
    }

    // 数据库连接的初始化工作，
    public void init() {
        createSkinnyRowTable();
        createWideRowTable();
    }

    public void close() {
        session.close();
        m_cluster.close();
    }

    public void skinnyTableTest() {
        dropSkinnyRowTable();
        createSkinnyRowTable();

        skinnyStatement = session.prepare(SKINNY_INSERT);
        System.out.println("Start to skinny table ... ");
        long start = System.currentTimeMillis();

        for (int i = 0; i < 300000; i++) {
            session.execute(skinnyStatement.bind(i, i));
        }

        long end = System.currentTimeMillis();

        System.out.println(String.format("\n-------------------Skinny Test : {}-----------------------\n", (end - start)/1000 ));
    }

    public void wideBatchInsertTest() {
        dropWideRowTable();
        createWideRowTable();

        wideStatement = prepareWideInsertStatement();

        for (int i = 0; i < 60; i++) {
            runExecute();
        }


        System.out.println("Start to wide table ... ");
        try {
            Thread.sleep(100*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runExecute() {
        long start = System.currentTimeMillis();

        BlockingQueue< BatchStatement> queryQueue = new ArrayBlockingQueue(400,true);
        ExecutorService pool = Executors.newFixedThreadPool(30);

        for (int i = 0; i < 300; i++) {
            // BatchStatement maximum size is 65535
            queryQueue.add(new BatchStatement().add(wideStatement.bind(i, i)));
        }


        System.out.println(String.format("\n-------------------Skinny Test - prepare : %d -----------------------\n", (System.currentTimeMillis() - start)));

        while (!queryQueue.isEmpty()) {
            pool.execute(() ->
                    execute(queryQueue)
            );
        }

        System.out.println(String.format("\n-------------------Skinny Test - total : %d -----------------------\n", (System.currentTimeMillis() - start) ));

    }

    public void skinnyBatchInsertTest() {
        dropSkinnyRowTable();
        createSkinnyRowTable();

        BatchStatement batchStatement = new BatchStatement();
        skinnyStatement = session.prepare(SKINNY_INSERT);
        System.out.println("Start to skinny table ... ");

        long start = System.currentTimeMillis();

        BlockingQueue< BatchStatement> queryQueue = new ArrayBlockingQueue(4000,true);
        ExecutorService pool = Executors.newFixedThreadPool(20); //将池大小也更改为油门插入


        for (int i = 0; i < 300000; i++) {
            batchStatement.add(skinnyStatement.bind(i, i));
            // BatchStatement maximum size is 65535
            if (batchStatement.size() >= 200){
                queryQueue.add(batchStatement);
                batchStatement = new BatchStatement();
            }
        }

        System.out.println(String.format("\n-------------------Skinny Test - prepare : %d -----------------------\n", (System.currentTimeMillis() - start)/1000));

        while (!queryQueue.isEmpty()) {
            pool.execute(() -> {
                execute(queryQueue);
            });
        }

        System.out.println(String.format("\n-------------------Skinny Test - total : %d -----------------------\n", (System.currentTimeMillis() - start)/1000 ));

        close();
        pool.shutdown();
        try {
            pool.awaitTermination(120, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement prepareWideInsertStatement() {
        StringBuilder insert = new StringBuilder();
        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();

        String prefix = "INSERT INTO wide (id, time, ";
        String middle = ") values (?, ?, ";
        String suffix = ");";

        for (int i=0; i<999; i++) {
            names.append("c");
            names.append(i);
            names.append(", ");

            values.append(5.5);
            values.append(", ");
        }

        names.append("c");
        names.append(999);
        values.append(5.5);

        insert.append(prefix)
                .append(names)
                .append(middle)
                .append(values)
                .append(suffix);


        return session.prepare(insert.toString());
    }

    private void createWideRowTable() {
        String prefix = "CREATE TABLE IF NOT EXISTS wide (id int, time int, ";
        String suffix = " primary key(id, time));";


        StringBuilder wide = new StringBuilder(prefix);

        for (int i=0; i < 1000; i++) {
            wide.append(" c");
            wide.append(i);
            wide.append(" float,");
        }

        wide.append(suffix);

        session.execute(wide.toString());
    }

    private void dropWideRowTable () {
        String wide = "DROP TABLE IF EXISTS wide;";
        session.execute(wide);
    }

    private void createSkinnyRowTable() {
        String skinny = "CREATE TABLE IF NOT EXISTS skinny (id int, time int, value int,  primary key(id, time));";
        session.execute(skinny);
    }

    private void dropSkinnyRowTable() {
        String skinny = "DROP TABLE IF EXISTS skinny;";
        session.execute(skinny);
    }

    private void execute(BlockingQueue< BatchStatement> queryQueue) {
        try {
            BatchStatement statement = queryQueue.take();
            session.execute(statement);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
