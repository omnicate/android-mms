/*
 * Copyright (C) 2007 Esmertec AG.
 * Copyright (C) 2007 The Android Open Source Project
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

package com.android.mms.dom.smil.parser;

import org.w3c.dom.smil.SMILDocument;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;

public class SmilXmlParser {
  private XMLReader mXmlReader;
  private SmilContentHandler mContentHandler;

  public SmilXmlParser() throws SAXException {
    mXmlReader = XMLReaderFactory.createXMLReader();

    // Disallow external DTD (Document Type Definition)
    mXmlReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

    // This may not be strictly required as DTDs shouldn't be allowed at all, per previous line.
    mXmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    mXmlReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
    mXmlReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

    mContentHandler = new SmilContentHandler();
    mXmlReader.setContentHandler(mContentHandler);
  }

  public SMILDocument parse(InputStream in) throws IOException, SAXException {
    mContentHandler.reset();

    mXmlReader.parse(new InputSource(in));

    SMILDocument doc = mContentHandler.getSmilDocument();
    validateDocument(doc);

    return doc;
  }

  private void validateDocument(SMILDocument doc) {
    /*
     * Calling getBody() will create "smil", "head", and "body" elements if they are not present. It will also
     * initialize the SequentialTimeElementContainer member of SMILDocument, which could not be set on creation of the
     * document.
     * 
     * @see com.android.mms.dom.smil.SmilDocumentImpl#getBody()
     */
    doc.getBody();

    /*
     * Calling getLayout() will create "layout" element if it is not present.
     * 
     * @see com.android.mms.dom.smil.SmilDocumentImpl#getLayout()
     */
    doc.getLayout();
  }
}
