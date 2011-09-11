package org.neo4j.laboratory.env;

import org.neo4j.graphdb.Node;

/**
 * Functions for generating a graph.
 */
public interface GraphGenerator
{
    Iterable<Node> generate( Node origin );
}
