package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.io.CharArrayReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.common.XmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.ParentXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewHeadDataConvertService;

public class ClientHeadDataConvertUtil implements INewHeadDataConvertService {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 利用STAX解析报文头部信息
	 * @param strXML
	 * @param map
	 * @throws Exception
	 */
	public void xmlToHeadMap(String strXML, Map<String, String> map) throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader r = factory.createXMLStreamReader(new CharArrayReader(strXML.toCharArray()));
		try {
			int event = r.getEventType();
			boolean flag = false;
			while (true) {
				switch (event) {
				case XMLStreamConstants.START_DOCUMENT:
					logger.info("Start Document.");
					break;
				case XMLStreamConstants.START_ELEMENT:
					logger.info("Start Element: " + r.getName());
					if(ParentXmlConstants.XML_HEAD.equals(r.getLocalName())){
						for (int i = 0, n = r.getAttributeCount(); i < n; ++i){
							logger.info("Attribute: " + r.getAttributeName(i) + "="
									+ r.getAttributeValue(i));
							
							map.put(XmlConstants.CON_XML_NAME, r.getAttributeValue(null, ParentXmlConstants.XML_HEAD_NAME));
							map.put(XmlConstants.CON_XML_VERSION, r.getAttributeValue(null, ParentXmlConstants.XML_HEAD_VERSION));
							
						}
						
					}	
					if(ParentXmlConstants.XML_HEAD_ACTION.equals(r.getLocalName())){
						map.put(XmlConstants.CON_XML_ACTIONCODE, r.getElementText());
						flag = true;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					if (r.isWhiteSpace())
						break;

					logger.info("Text: " + r.getText());
					break;
				case XMLStreamConstants.END_ELEMENT:
					logger.info("End Element:" + r.getName());
					break;
				case XMLStreamConstants.END_DOCUMENT:
					logger.info("End Document.");
					break;
				}

				if (!r.hasNext()||flag)
				break;

				event = r.next();
			}
		} finally {
			r.close();
		}
	}



}
