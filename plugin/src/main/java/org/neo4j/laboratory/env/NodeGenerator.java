package org.neo4j.laboratory.env;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Simple generator that creates a disconnected graph of unadorned (lacking properties) Nodes.
 */
public class NodeGenerator implements GraphGenerator
{

    private GraphDatabaseService graphdb;
    private long count;
    private long flushInterval;

    private NodeGenerator( GraphDatabaseService graphdb, long count, long flushInterval )
    {
        this.graphdb = graphdb;
        this.count = count;
        this.flushInterval = flushInterval;
    }

    public static NodeGenerator createNodeGenerator( GraphDatabaseService graphdb, long count, long flushInterval )
    {
        return new NodeGenerator( graphdb, count, flushInterval );
    }

    /**
     * Generates the requested number of nodes.
     *
     * @param origin the starting node for generation (ignored)
     * @return the last node created
     */
    @Override
    public Iterable<Node> generate( Node origin )
    {
        Transaction tx = graphdb.beginTx();
        Node lastNode = null;
        try
        {
            for ( long i = 0; i < count; i++ )
            {
                lastNode = graphdb.createNode();

                if ( (i % flushInterval) == 0 )
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

        Collection<Node> endNodes = new ArrayList<Node>();
        endNodes.add(lastNode);
        return endNodes;
    }

}