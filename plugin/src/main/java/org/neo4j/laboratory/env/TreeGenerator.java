package org.neo4j.laboratory.env;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Generates trees of arbitrary depth.
 */
public class TreeGenerator implements GraphGenerator
{

    private GraphDatabaseService graphdb;
    private long depth;
    private long density;
    private RelationshipType relType;
    private long batch;

    private TreeGenerator( GraphDatabaseService graphdb, long density, long depth, RelationshipType relType, long batch )
    {
        this.graphdb = graphdb;
        this.density = density;
        this.depth = depth;
        this.relType = relType;
        this.batch = batch;
    }

    public static TreeGenerator createTreeGenerator( GraphDatabaseService graphdb, long density, long depth, RelationshipType relType, long batch )
    {
        return new TreeGenerator( graphdb, density, depth, relType, batch );
    }

    @Override
    public Iterable<Node> generate( Node origin )
    {
        Transaction tx = graphdb.beginTx();
        Node parent = origin;
        try
        {
            for ( long i = 0; i < depth; i++ )
            {
                // create children
                for ( long j=0; j < Math.pow(i+1, density); j++)
                {
                    Node child = graphdb.createNode();
                }
                // connect to parents


                if ( (i % batch) == 0 )
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
