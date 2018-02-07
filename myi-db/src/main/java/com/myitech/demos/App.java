package com.myitech.demos;

import com.myitech.demos.cassandra.Client;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Client client = new Client();
//        client.skinnyTableTest();
//        client.skinnyBatchInsertTest();

        client.wideBatchInsertTest();
        client.close();
    }
}
