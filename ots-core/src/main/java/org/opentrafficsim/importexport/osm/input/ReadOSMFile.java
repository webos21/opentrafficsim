package org.opentrafficsim.importexport.osm.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.openstreetmap.osmosis.core.task.v0_6.RunnableSource;
import org.openstreetmap.osmosis.xml.common.CompressionMethod;
import org.openstreetmap.osmosis.xml.v0_6.XmlReader;
import org.opentrafficsim.importexport.osm.Network;
import org.opentrafficsim.importexport.osm.Tag;

import crosby.binary.osmosis.OsmosisReader;

/**
 * <p>
 * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights
 * reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version 31 dec. 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/mzhang">Mingxin Zhang </a>
 * @author <a>Moritz Bergmann</a>
 */
public class ReadOSMFile
{

    /**  */
    private NewSink sinkImplementation = null;

    /**  */
    private boolean pbf = false;

    /**  */
    private URL url;

    /**  */
    private File file;

    /**  */
    private CompressionMethod compression;

    /**  */
    private RunnableSource reader = null;

    /**  */
    private Thread readerThread;

    /**  */
    private boolean isReaderThreadDead = false;

    /** */
    private List<Tag> wantedTags;

    /** */
    private List<String> filterKeys;

    /**
     * @param location String; the location of the OSM file
     * @param wt List&lt;Tag&gt;; the list of wanted tags
     * @param ft List&lt;String&gt;; the list of filtered keys
     * @throws URISyntaxException when <cite>location</cite> is not a valid URL
     * @throws FileNotFoundException when the OSM file can not be found
     * @throws MalformedURLException when <cite>location</cite> is not valid
     */
    public ReadOSMFile(final String location, final List<Tag> wt, final List<String> ft) throws URISyntaxException,
            FileNotFoundException, MalformedURLException
    {
        this.wantedTags = wt;
        this.filterKeys = ft;
        this.url = new URL(location);
        if (null == this.url)
        {
            throw new FileNotFoundException("Cannot construct url for location \"" + location + "\"");
        }
        this.file = new File(this.url.toURI());

        this.sinkImplementation = new NewSink();
        this.sinkImplementation.setWantedTags(this.wantedTags);
        this.sinkImplementation.setFilterKeys(this.filterKeys);

        this.compression = CompressionMethod.None;

        if (this.file.getName().endsWith(".pbf"))
        {
            this.pbf = true;
        }
        else if (this.file.getName().endsWith(".gz"))
        {
            this.compression = CompressionMethod.GZip;
        }
        else if (this.file.getName().endsWith(".bz2"))
        {
            this.compression = CompressionMethod.BZip2;
        }

        if (this.pbf)
        {
            try
            {
                this.reader = new OsmosisReader(new FileInputStream(this.file));
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            this.reader = new XmlReader(this.file, false, this.compression);
        }

        this.reader.setSink(this.sinkImplementation);

        this.readerThread = new Thread(this.reader);
        this.readerThread.start();
        while (this.readerThread.isAlive())
        {
            try
            {
                this.readerThread.join();
            }
            catch (InterruptedException e)
            {
                System.err.println("The map reader thread got problem!");
            }
        }

        this.isReaderThreadDead = true;

        // System.out.println("reader thread is dead here/////");
    }

    /**
     * @return is reader thread dead
     */
    public final boolean checkisReaderThreadDead()
    {
        return this.isReaderThreadDead;
    }

    /**
     * @return get the whole Network
     */
    public final Network getNetwork()
    {
        return this.sinkImplementation.getNetwork();
    }
}
