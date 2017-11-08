package com.sinosoft.easyscan5.util;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class NewUploadCommconFun {
	private Document tempDoc;
	public String getImageSavePath(String strXML) {
		String filePath = "";
	    SAXBuilder builder = new SAXBuilder();
		try {
		    Document doc = builder.build(new CharArrayReader(strXML.toCharArray()));
		    tempDoc = doc;
			Element dataE = doc.getRootElement();
			Element bodyE = dataE.getChild("body");
			List listFiles = bodyE.getChildren("files");
			for (int m = 0; m < listFiles.size(); m++) {
				Element files = (Element) listFiles.get(m);
				filePath = files.getAttributeValue("filepath");
			}
			if(!"".equals(filePath)){
				filePath = getServerBasePath() + "xerox/EasyScan/" + filePath;
				File dir = new File(filePath);
				if(!dir.exists()){
					dir.mkdirs();
				}
			}
			return filePath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}
	/**
	 * 获取服务器路径
	 *  如/D:/workspace2/ImageNew/WebRoot/
	 * @return
	 */
	public String getServerBasePath(){
		URL url = this.getClass().getResource(""); 
		String path = url.getPath();  
		path = path.substring(0, path.lastIndexOf("WEB-INF"));  
		String serverBasePath = path;
		return serverBasePath;
	}
	public String getOutErrorStr(String str){
		StringBuffer tbufXML = new StringBuffer(1024);
		tbufXML.append("<IndexXML><?xml version=\"1.0\" encoding=\"UTF-8\"?><data><head></head><result code=\"");
		tbufXML.append("-500");
		tbufXML.append("\">");
		tbufXML.append("<message>");
	    tbufXML.append(str);
		tbufXML.append("</message></result><body></body></data></IndexXML>");
		return tbufXML.toString();
	}
	public String createReturnXml() throws Exception{
		Element dataE = tempDoc.getRootElement();
		Element bodyE = dataE.getChild("body");
		List listFiles = bodyE.getChildren("files");
		Element result = new Element("result");
		result.addAttribute("code","0");
		Element message = new Element("message");
		message.setText("");
		result.addContent(message);
		dataE.addContent(result);
		for (int m = 0; m < listFiles.size(); m++) {
			Element files = (Element) listFiles.get(m);
			List fileList = files.getChildren();
			for(int j = 0;j < fileList.size();j++){
				Element fileE = (Element) fileList.get(j);
				fileE.addAttribute("filekey", "");
			}
		}
		XMLOutputter out = new XMLOutputter();
		OutputStream outputStream = new ByteArrayOutputStream();
		out.output(tempDoc,outputStream);
		outputStream.flush();
		String strXML = "<IndexXML>" + out.outputString(tempDoc) + "</IndexXML>";
		return strXML;
	}
}
