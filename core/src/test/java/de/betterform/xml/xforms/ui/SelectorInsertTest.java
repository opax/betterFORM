/*
 * Copyright (c) 2012. betterFORM Project - http://www.betterform.de
 * Licensed under the terms of BSD License
 */
package de.betterform.xml.xforms.ui;

import de.betterform.xml.dom.DOMUtil;
import de.betterform.xml.events.DOMEventNames;
import de.betterform.xml.xforms.XFormsProcessorImpl;
import de.betterform.xml.xforms.exception.XFormsException;
import de.betterform.xml.xpath.impl.saxon.XPathUtil;
import junit.framework.TestCase;
import net.sf.saxon.om.NodeInfo;

import java.util.List;

/**
 * Tests the selector elements.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: SelectorTest.java 3251 2008-07-08 09:26:03Z lasse $
 */
public class SelectorInsertTest extends TestCase {
//    static {
//        org.apache.log4j.BasicConfigurator.configure();
//    }

    private XFormsProcessorImpl xformsProcesssorImpl;

    /**
     * Tests Select1 with dynamic Itemset.
     *
     * @throws Exception if any error occurred during the test.
     */
    public void testSelect1Dynamic() throws Exception {
        Selector selector = (Selector) this.xformsProcesssorImpl.getContainer().lookup("select1-1");
        assertEquals("1", selector.getValue());

        verifyItem(selector, 3, 0, "C5",  "1", "First");
        verifyItem(selector, 3, 1, "C10", "2", "Second");
        verifyItem(selector, 3, 2, "C15", "3", "Third");



        // register(item1, item2, item3);
        this.xformsProcesssorImpl.dispatch("insert-item-at-pos-1", DOMEventNames.ACTIVATE);
        DOMUtil.prettyPrintDOM(this.xformsProcesssorImpl.getContainer().getDocument());

        /**
        verifyItem(selector, 4, 0, "C5", "4", "Fourth");
        verifyItem(selector, 4, 1, "C10",  "1", "First");
        verifyItem(selector, 4, 2, "C15", "2", "Second");
        verifyItem(selector, 4, 3, "C22", "3", "Third");
        **/

        verifyItem(selector, 4, 0, "C22", "4", "Fourth");
        verifyItem(selector, 4, 1, "C5",  "1", "First");
        verifyItem(selector, 4, 2, "C10", "2", "Second");
        verifyItem(selector, 4, 3, "C15", "3", "Third");



        // deregister(item1, item2, item3);

    }

    private void verifyItem(Selector selector, int size, int itemPosition, String itemId, String itemValue, String itemLabel) throws XFormsException {
        List resultIds= XPathUtil.evaluate(selector.getElement(), "xf:itemset/xf:item/@id");
        List resultValues= XPathUtil.evaluate(selector.getElement(), "xf:itemset/xf:item/xf:value/text()");
        List resultLabels = XPathUtil.evaluate(selector.getElement(), "xf:itemset/xf:item/xf:label/text()");
        assertEquals(size, resultIds.size());
        String id = ((NodeInfo) resultIds.get(itemPosition)).getStringValue();
        assertEquals(itemId, id);
        String value =  ((NodeInfo) resultValues.get(itemPosition)).getStringValue();
        assertEquals(itemValue, value);
        String label =  ((NodeInfo) resultLabels.get(itemPosition)).getStringValue();
        assertEquals(itemLabel, label);
    }


    /**
     * Sets up the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void setUp() throws Exception {
        this.xformsProcesssorImpl = new XFormsProcessorImpl();
        this.xformsProcesssorImpl.setXForms(getClass().getResourceAsStream("SelectorInsertTest.xhtml"));
        this.xformsProcesssorImpl.init();
    }

    /**
     * Tears down the test.
     *
     * @throws Exception in any error occurred during setup.
     */
    protected void tearDown() throws Exception {
        this.xformsProcesssorImpl.shutdown();
        this.xformsProcesssorImpl = null;
    }
}

// end of class
