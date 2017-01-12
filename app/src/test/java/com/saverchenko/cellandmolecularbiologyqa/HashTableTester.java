package com.saverchenko.cellandmolecularbiologyqa;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class HashTableTester {

    ChainedHashTable table;

    @Before
    public void setUp(){
        table = new ChainedHashTable(6);

    }
    @Test
    public void testHashDate() {

        String[]parts = {"09","01"};
        table.hashDate(parts);
        assertEquals("Testing hashDate on dates other than the current date", "[0]", table.getHashArray(parts).toString() );

        String[]parts1 = {"11","05"};
        table.hashDate(parts1);
        assertEquals("Testing hashDate on dates other than the current date", "[0, 1, 2]", table.getHashArray(parts1).toString() );
    }
}