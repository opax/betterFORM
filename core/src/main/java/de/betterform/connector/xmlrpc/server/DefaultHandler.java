/*
 * Copyright (c) 2012. betterFORM Project - http://www.betterform.de
 * Licensed under the terms of BSD License
 */

package de.betterform.connector.xmlrpc.server;


import de.betterform.connector.xmlrpc.DocTransformer;
import de.betterform.connector.xmlrpc.DocTransformerException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileWriter;
import java.util.Hashtable;

public class DefaultHandler {

    private static Log LOGGER = LogFactory.getLog(DefaultHandler.class);

    String FORMDIR = "../../forms";

    public DefaultHandler() {
        LOGGER.debug("DefaultHandler: initialising");
    }

    public Hashtable loadInstance(Hashtable params) {
        LOGGER.debug("DefaultHandler: running loadInstance");

        if (!params.containsKey("file")) {
            LOGGER.debug("loadInstance: no parameter 'file'");
            Hashtable ret = new Hashtable();
            ret.put("status", "error");
            ret.put("error", "Parameter 'file' not passed to function");
            return ret;
        }

        String filename = (String) params.get("file");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Looking for file: " + FORMDIR + "/" + filename);
        }
        File f = new File(FORMDIR + "/" + filename);
        try {
            DocTransformer dt = new DocTransformer(f);
            LOGGER.debug("Returning OK hash");
            return dt.getHash();
        } catch (DocTransformerException e) {
            e.printStackTrace();
            Hashtable ret = new Hashtable();
            ret.put("status", "error");
            ret.put("error", "Exception: " + e.getMessage());
            return ret;
        }
    }

    public Hashtable saveInstance(Hashtable params) {
        LOGGER.debug("DefaultHandler: running saveInstance");

        if (!params.containsKey("file")) {
            LOGGER.debug("saveInstance: no parameter 'file'");
            Hashtable ret = new Hashtable();
            ret.put("status", "error");
            ret.put("error", "Parameter 'file' not passed to function");
            return ret;
        }
        if (!params.containsKey("doc")) {
            LOGGER.debug("saveInstance: no parameter 'doc'");
            Hashtable ret = new Hashtable();
            ret.put("status", "error");
            ret.put("error", "Parameter 'doc' not passed to function");
            return ret;
        }

        try {
            String filename = (String) params.get("file");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Saving to file: " + FORMDIR + "/" + filename);
            }
            File file = new File(FORMDIR + "/" + filename);
            FileWriter writer = new FileWriter(file);

            byte[] docBytes = (byte[]) params.get("doc");

            DocTransformer dt = new DocTransformer(docBytes);
            writer.write(dt.getString());

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Doc = " + dt.getString());
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            Hashtable ret = new Hashtable();
            ret.put("status", "error");
            ret.put("error", "Exception: " + e.getMessage());
            return ret;
        }

        Hashtable ret = new Hashtable();
        ret.put("status", "ok");
        return ret;
    }
}
    
    
