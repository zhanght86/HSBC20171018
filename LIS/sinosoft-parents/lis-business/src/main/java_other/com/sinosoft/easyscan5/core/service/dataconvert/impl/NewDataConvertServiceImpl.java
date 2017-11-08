package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sinosoft.easyscan5.common.easyscanxml.GetCenterSettingReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.ParentXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.easyscan.ProductVo;
import com.sinosoft.utility.VData;

public class NewDataConvertServiceImpl implements INewDataConvertService {
	private Document reqDocument;
	private Document resDocument;
	private ClientHeadXmlBean clientHead;
	private Logger logger = Logger.getLogger(this.getClass());

	public Document getReqDocument() {
		return reqDocument;
	}

	public void setDocument(String xml) {
		try {
			// SAXReader sr = new SAXReader();
			// DocumentHelper.parseText(xml);
			reqDocument = DocumentHelper.parseText(xml);// sr.read(new
														// CharArrayReader(xml.toCharArray()));
			resDocument = DocumentHelper.createDocument();
			setHead();
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error("加载报文出错，请检查报文格式", e);
			reqDocument = null;
		}
	}

	/**
	 *  解析头部信息
	 */
	private void setHead() {
		if (reqDocument != null) {
			List headList = reqDocument.selectNodes(ParentXmlConstants.XML_ROOT + "/" + ParentXmlConstants.XML_HEAD);
			if (headList != null && headList.size() > 0) {
				for (int i = 0; i < headList.size(); i++) {
					Element head = (Element) headList.get(i);
					clientHead = new ClientHeadXmlBean();

					clientHead.setName(head.attributeValue(ParentXmlConstants.XML_HEAD_NAME));
					clientHead.setVersion(head.attributeValue(ParentXmlConstants.XML_HEAD_VERSION));
					List paramList = head.selectNodes(ParentXmlConstants.XML_HEAD_PARAM);
					if (paramList != null && paramList.size() > 0) {
						Element param = (Element) paramList.get(0);
						clientHead.setParam(param.getText());
					}
					List actionList = head.selectNodes(ParentXmlConstants.XML_HEAD_ACTION);
					if (actionList != null && actionList.size() > 0) {
						Element action = (Element) actionList.get(0);
						clientHead.setAction(action.getText());
					}
					List mngList = head.selectNodes(ParentXmlConstants.XML_HEAD_MANAGECOM);
					if (mngList != null && mngList.size() > 0) {
						Element mng = (Element) mngList.get(0);
						clientHead.setManageCom(mng.getText());
					}
					List operList = head.selectNodes(ParentXmlConstants.XML_HEAD_USERCODE);
					if (operList != null && operList.size() > 0) {
						Element oper = (Element) operList.get(0);
						clientHead.setUserCode(oper.getText());
					}
					List sessList = head.selectNodes(ParentXmlConstants.XML_HEAD_SESSIONID);
					if (sessList != null && sessList.size() > 0) {
						Element sess = (Element) sessList.get(0);
						clientHead.setSessionId(sess.getText());
					}
				}
			}
		}
	}

	/**
	 * 拼接报文头
	 * 
	 * @return
	 */
	public Element getHeadXml() {
		Element root = this.getResDocument().addElement(ParentXmlConstants.XML_ROOT);
		Element head = root.addElement(ParentXmlConstants.XML_HEAD);
		ClientHeadXmlBean headBean = this.getClientHead();
		head.addAttribute(ParentXmlConstants.XML_HEAD_NAME, headBean.getName());
		head.addAttribute(ParentXmlConstants.XML_HEAD_VERSION, headBean.getVersion());
		head.addElement(ParentXmlConstants.XML_HEAD_PARAM, headBean.getParam() == null ? "" : headBean.getParam());
		Element action = head.addElement(ParentXmlConstants.XML_HEAD_ACTION);
		action.setText(headBean.getAction());
		Element sessionId = head.addElement(ParentXmlConstants.XML_HEAD_SESSIONID);
		sessionId.setText(headBean.getSessionId());
		return root;
	}

	/**
	 * 增加result节点
	 * @param element
	 * @param code
	 * @param strMessage
	 */
	public void addResult(Element element, String code, String strMessage) {
		Element resultEle = element.addElement(ParentXmlConstants.XML_RESULT);
		resultEle.addAttribute(ParentXmlConstants.XML_RESULT_CODE, code);
		Element codeEle = resultEle.addElement(ParentXmlConstants.XML_RESULT_MESSAGE);
		codeEle.setText(strMessage);
	}

	/**
	 * 增加result节点
	 * @param element
	 * @param code
	 * @param strMessage
	 */
	public void addResult(Element element){
		addResult(element,"0","");
	}
	/**
	 * 保存返回xml
	 * 
	 * @param bufXml
	 */
	public void getOutXml(StringBuffer bufXml) {
		try {
			// 格式化输出xml文件
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			StringWriter out = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(out, format);
			xmlWriter.write(this.getResDocument());
			bufXml.append(out.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getChannel(Document doc) {
		List channelList = doc.selectNodes(GetCenterSettingReqXmlConstants.XML_ROOT + "/"
				+ GetCenterSettingReqXmlConstants.XML_HEAD + "/" + GetCenterSettingReqXmlConstants.XML_HEAD_CHANNEL);
		if (channelList != null && channelList.size() > 0) {
			Element paramNode = (Element) channelList.get(0);
			return paramNode.getText();
		}
		return null;
	}

	public Element getRootElement() throws Exception {
		return reqDocument.getRootElement();
	}

	public Document getResDocument() {
		return resDocument;
	}

	public ClientHeadXmlBean getClientHead() {
		return clientHead;
	}

	public String mapToXml(Map map, StringBuffer xml) {
		// TODO Auto-generated method stub
		return null;
	}

	public String mapToXml(Map map, Map map1, StringBuffer xml) {
		// TODO Auto-generated method stub
		return null;
	}

	public String mapToXml(Map map, Map map1, StringBuffer xml, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public String xmlToMap(String xml, Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public String listToXml(List esQcMainsList, StringBuffer xmlBuffer) {
		// TODO Auto-generated method stub
		return null;
	}

	public String xmlToList(String indexStr, List queryList) {
		// TODO Auto-generated method stub
		return null;
	}

	public String StringToXml(String dataStr, StringBuffer bufXML) {
		// TODO Auto-generated method stub
		return null;
	}

	public String xmlToProductVo(String indexXML, ProductVo productVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public String listToXml(List list1, Map map, StringBuffer xmlBuffer) {
		// TODO Auto-generated method stub
		return null;
	}

	public String stringToXml(String dataStr, StringBuffer bufXML) {
		// TODO Auto-generated method stub
		return null;
	}

	public String stringToXml(String[] dataStr, StringBuffer bufXML) {
		// TODO Auto-generated method stub
		return null;
	}

	public String xmlToVData(String xml, VData vData) {
		// TODO Auto-generated method stub
		return null;
	}

	public String vdataToXml(VData data, StringBuffer bufXML) {
		// TODO Auto-generated method stub
		return null;
	}


}
