package org.neo4j.laboratory.env;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Generates a singly-linked list.
 */
public class LinkedListGenerator implements GraphGenerator
{

    private GraphDatabaseService graphdb;
    private long length;
    private RelationshipType relType;
    private long flush;

    private LinkedListGenerator( GraphDatabaseService graphdb, long length, RelationshipType relType, long flush )
    {
        this.graphdb = graphdb;
        this.length = length;
        this.relType = relType;
        this.flush = flush;
    }

    public static LinkedListGenerator createLinkedListGenerator( GraphDatabaseService graphdb, long length, RelationshipType relType, long flush )
    {
        return new LinkedListGenerator( graphdb, length, relType, flush );
    }

    @Override
    public Iterable<Node> generate( Node origin )
    {
        Transaction tx = graphdb.beginTx();
        Node previous = origin;
        try
        {
            for ( long i = 0; i < length; i++ )
            {
                Node next = graphdb.createNode();
                previous.createRelationshipTo( next, relType );
                previous = next;

                if ( (i % flush) == 0 )
                {
                    tx.success();
                    tx.finish();
                    tx = graphdb.beginTx();
                }
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        Collection<Node> returnNodes = new ArrayList<Node>();
        returnNodes.add(previous);
        return returnNodes;
    }
}
