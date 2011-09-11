package org.neo4j.laboratory.env;

import org.neo4j.graphdb.*;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/env")
public class EnvelopeResource
{
    volatile long x;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String ops( @Context UriInfo urii )
    {

        return "{" +
                "\t" + "\"no-op\" : \"" + urii.getBaseUri().toASCIIString() + "env/no-op\"" + ",\n" +
                "\t" + "\"max-read\" : \"" + urii.getBaseUri().toASCIIString() + "env/max-read\",\n" +
                "\t" + "\"max-write\" : \"" + urii.getBaseUri().toASCIIString() + "env/max-write\",\n" +
                "\t" + "\"read-all\" : \"" + urii.getBaseUri().toASCIIString() + "env/read-all\",\n" +
                "\t" + "\"linked-list\" : \"" + urii.getBaseUri().toASCIIString() + "env/linked-list\",\n" +
                "}";
    }

    @GET
    @Path("/no-op")
    @Produces(MediaType.APPLICATION_JSON)
    public String noop()
    {
        return "{}";
    }

    @GET
    @Path("/no-op/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String noop( @PathParam("count") long count )
    {
        for ( long i = 0; i < count; i++ )
        {
            x = i;
        }
        return "{}";
    }

    @GET
    @Path("/max-read/{node}/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String readNode( @Context GraphDatabaseService graphdb, @PathParam("node") long node, @PathParam("count") long count )
    {
        for ( long i = 0; i < count; i++ )
        {
            graphdb.getNodeById( node );
        }
        return "{}";
    }

    @POST
    @Path("/max-write/{count}/{batch}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNode( @Context GraphDatabaseService graphdb, @PathParam("count") long count, @PathParam("batch") long batch )
    {
        GraphGenerator nodeGenerator = NodeGenerator.createNodeGenerator( graphdb, count, batch );
        nodeGenerator.generate( null );

        return "{}";
    }

    @GET
    @Path("/read-all/{maxid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String readAll( @Context GraphDatabaseService graphdb, @PathParam("maxid") long maxid )
    {
        for ( long nodeid = 0; nodeid <= maxid; nodeid++ )
        {
            graphdb.getNodeById( nodeid );
        }
        return "{}";
    }

    @POST
    @Path("/boolean-prop/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodesWithBooleanProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                n.setProperty( "b", (i % 2 == 0) );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @POST
    @Path("/byte-prop/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodesWithByteProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            byte v = 0;
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                n.setProperty( "byte", v++ );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @POST
    @Path("/short-prop/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodesWithShortProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            short v = 0;
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                n.setProperty( "short", v++ );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @POST
    @Path("/int-prop/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodesWithIntProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                n.setProperty( "int", (int) i );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @POST
    @Path("/long-prop/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodesWithLongProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                n.setProperty( "long", i );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @POST
    @Path("/float-prop/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodesWithFloatProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            float v = 0.0f;
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                n.setProperty( "float", v++ );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @POST
    @Path("/double-prop/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodesWithDoubleProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            double v = 0.0;
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                n.setProperty( "double", v++ );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @POST
    @Path("/char-prop/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodesWithCharProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            char v = 'a';
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                n.setProperty( "char", v++ );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @POST
    @Path("/long-array-prop/{count}/{length}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodesWithLongArrayProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count, @PathParam("length") int length )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            long[] a = new long[length];
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                a[(int) i % length] = i;
                n.setProperty( "la", a );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @POST
    @Path("/string-prop/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodeWithStringProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                n.setProperty( "string", "Not a short string value #" + Long.toString( i ) );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @POST
    @Path("/short-string-prop/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String writeNodeWithShortStringProp( @Context GraphDatabaseService graphdb, @PathParam("count") long count )
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            for ( long i = 0; i < count; i++ )
            {
                Node n = graphdb.createNode();
                //n.setProperty( "ss", Long.toString(i%999999));
                n.setProperty( "ss", Long.toString( i ).substring( 0, 6 ) );
            }
            tx.success();
        } finally
        {
            tx.finish();
        }
        return "{}";
    }

    @GET
    @Path("/read-all-props/{maxid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String readAllProps( @Context GraphDatabaseService graphdb, @PathParam("maxid") long maxid )
    {
        for ( long nodeid = 0; nodeid <= maxid; nodeid++ )
        {
            Node n = graphdb.getNodeById( nodeid );
            for ( String propKey : n.getPropertyKeys() )
            {
                n.getProperty( propKey );
            }
        }
        return "{}";
    }

    @POST
    @Path("/linked-list/{count}/{batch}")
    @Produces(MediaType.APPLICATION_JSON)
    public String makeLinkedList( @Context GraphDatabaseService graphdb, @PathParam("count") long count, @PathParam("batch") long batch )
    {
        RelationshipType relType = DynamicRelationshipType.withName( "link" );

        Transaction tx = graphdb.beginTx();
        Node head = null;
        try
        {
            head = graphdb.createNode();
            tx.success();
        } finally
        {
            tx.finish();
        }

        GraphGenerator listGenerator = LinkedListGenerator.createLinkedListGenerator( graphdb, count, relType, batch );
        listGenerator.generate( head );

        return "{}";
    }

    @POST
    @Path("/star/{arms}/{length}/{batch}")
    @Produces(MediaType.APPLICATION_JSON)
    public String makeStar( @Context GraphDatabaseService graphdb,
                            @PathParam("arms") long arms, @PathParam("length") long length, @PathParam("batch") long batch )
    {
        RelationshipType relType = DynamicRelationshipType.withName( "ray" );

        Transaction tx = graphdb.beginTx();
        Node center = null;
        try
        {
            center = graphdb.createNode();
            tx.success();
        } finally
        {
            tx.finish();
        }

        GraphGenerator armGenerator = LinkedListGenerator.createLinkedListGenerator( graphdb, length, relType, batch );
        for ( long i = 0; i < arms; i++ )
        {
            armGenerator.generate( center );
        }

        return "{}";
    }

    @POST
    @Path("/rmat/{scale}")
    @Produces(MediaType.APPLICATION_JSON)
    public String makeRmat( @Context GraphDatabaseService graphdb, @PathParam("scale") long scale )
    {
        RelationshipType relType = DynamicRelationshipType.withName( "rmat" );
        RmatGenerator.createRmatGenerator( graphdb, relType, scale ).generate( null );
        return "{}";
    }
}
