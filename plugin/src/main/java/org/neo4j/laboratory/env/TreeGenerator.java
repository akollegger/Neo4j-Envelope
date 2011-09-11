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
    private long branchFactor;
    private RelationshipType relType;
    private long batch;

    private TreeGenerator( GraphDatabaseService graphdb, long branchFactor, long depth, RelationshipType relType, long batch )
    {
        this.graphdb = graphdb;
        this.branchFactor = branchFactor;
        this.depth = depth;
        this.relType = relType;
        this.batch = batch;
    }

    public static TreeGenerator createTreeGenerator( GraphDatabaseService graphdb, long branchFactor, long depth, RelationshipType relType, long batch )
    {
        return new TreeGenerator( graphdb, branchFactor, depth, relType, batch );
    }

    @Override
    public Iterable<Node> generate( Node origin )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            for ( long d = 1; d <= depth; d++ )
            {
                // create children
                long parentRowSize = (long)Math.pow(branchFactor, d-2);
                for ( long j=1; j <= (long)Math.pow(branchFactor, d-1 ); j++)
                {
                    Node child = graphdb.createNode();
                    long parentPosition = (j/branchFactor)+1;
                    long parentNodeId = (child.getId() - j) - parentRowSize + parentPosition;
                    Node parent = graphdb.getNodeById( parentNodeId );
                    parent.createRelationshipTo( child, relType );
                }

                if ( (d % batch) == 0 )
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
        return returnNodes;
    }
}
