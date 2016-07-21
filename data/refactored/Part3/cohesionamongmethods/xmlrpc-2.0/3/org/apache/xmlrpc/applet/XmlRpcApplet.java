/*
 * Copyright 1999,2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.xmlrpc.applet;

import java.applet.Applet;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Vector;


/**
 * An applet that provides basic XML-RPC client functionality.
 *
 * @version $Id: XmlRpcApplet.java,v 1.3 2005/04/22 10:25:58 hgomez Exp $
 */
public class XmlRpcApplet extends Applet {
    public Object loaded = null;

    public

     SimpleXmlRpcClient client;

    public Hashtable addStructArgToArray(Vector ary)
     {
        Hashtable ht = new Hashtable();
        ary.addElement(ht);
        return ht;
    }
}
