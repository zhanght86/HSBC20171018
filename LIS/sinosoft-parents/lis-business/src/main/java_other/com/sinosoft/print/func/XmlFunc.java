package com.sinosoft.print.func;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XmlFunc {
	private static Logger logger = Logger.getLogger(XmlFunc.class);

	/**
	 * 解析xml之后的Document对象
	 */
	private Document mDocument;

	/**
	 * xml文件名
	 */
	private String mFileName;

	/**
	 * 读取值，由于大数据将反复读取，设为全局变量
	 */
	private String strReturn = "";

	/**
	 * 元素控制类，防止对象过大
	 * 
	 */
	private MyElementHandler mElementHandler = new MyElementHandler();

	/**
	 * 输入流长度，用于设置元素控制类
	 */
	private int inputLength;

	/**
	 * 当前访问节点（行节点）
	 * 
	 */
	private Node m_nodeMultiRow = null;

	private boolean m_bBOF = false;

	public XmlFunc() {
	}

	/**
	 * 从一个StringReader对象中产生一个Document
	 * 
	 * @param in
	 *            InputStream 输入流
	 */
	public XmlFunc(InputStream in) {
		this(in, false);
	}

	/**
	 * 从一个StringReader对象中产生一个Document，如果flag为真，则设置元素控制类
	 * 
	 * @param in
	 * @param printControlFlag
	 */
	public XmlFunc(InputStream in, boolean printControlFlag) {
		this.mFileName = "";
		try {
			SAXReader reader = new SAXReader();
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"));
			BufferedInputStream bins = new BufferedInputStream(in);
			this.inputLength = bins.available();
			this.setElementHandler(reader, printControlFlag);
			mDocument = reader.read(br);
			mElementHandler.setEndOfbyte();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public XmlFunc(String cFileName) {
		this.mFileName = cFileName;
		try {
			if (mDocument == null) {
				SAXReader reader = new SAXReader();
				reader.setDefaultHandler(mElementHandler);
				mDocument = reader.read(new File(mFileName));
				mElementHandler.setEndOfbyte();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分析xpath串。由于dom4j自带方法访问大数据时返回为结果集，而此处仅需要访问找到的第一条数据，因此在可能存在大数据时，使用简单方法访问
	 * 
	 * @param xpathString
	 *            要进行分析的xpath表达式。
	 * @return
	 */
	public Node parseX(String xpathString) {
		try {
			if (mDocument == null) {
				throw new Exception("Document对象为空！");
			}
			String[] pathArr = xpathString.split("/");
			pathArr = XMLPrintTagName.changeROWToR(pathArr);
			Element element = mDocument.getRootElement();
			Node node = null;
			for (String path : pathArr) {
				if (path.length() == 0 || "DATASET".equals(path)) {
					continue;
				}
				if (element.nodeCount() > 100) {// 超过100个节点，就只访问第一个符合节点
					for (Iterator i = element.elementIterator(); i.hasNext();) {
						node = (Node) i.next();
						if (node.getNodeType() == Node.ELEMENT_NODE
								&& node.getName().equals(path)) {
							element = (Element) node;
							break;
						}
					}
				} else {// 否则使用自带方法
					element = (Element) element.selectSingleNode("./" + path);
				}
				if (element == null)
					return null;
			}
			return (Node) element;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String getNodeValue(Node node) {
		if (node == null) {
			return "";
		}
		return node.getText();
	}

	public String getTemplate() {
		Node node = parseX("/DATASET/CONTROL/TEMPLATE");
		return getNodeValue(node);
	}

	public String getTemplateType() {
		Node node = parseX("/DATASET/CONTROL/TEMPLATETYPE");
		return getNodeValue(node);
	}

	public String getPrintName() {
		Node node = parseX("/DATASET/CONTROL/PrintName");
		return getNodeValue(node);
	}

	public String getPrintType() {
		Node node = parseX("/DATASET/CONTROL/PrintType");
		return getNodeValue(node);
	}

	public String getUserLanguage() {
		Node node = parseX("/DATASET/CONTROL/DISPLAY/UserLanguage");
		return getNodeValue(node);
	}

	public String getSysLanguage() {
		Node node = parseX("/DATASET/CONTROL/DISPLAY/SysLanguage");
		return getNodeValue(node);
	}

	public String getOutputType() {
		Node node = parseX("/DATASET/CONTROL/DISPLAY/OutputType");
		return getNodeValue(node);
	}

	public String getCreateType(){
		Node node = parseX("/DATASET/CONTROL/DISPLAY/CreateType");
		return getNodeValue(node);
	}
	
	public String getNodeValue(String argXPath) {
		Node node = parseX(argXPath);
		return getNodeValue(node);
	}

	public String getDisplayControl(String strName) {
		Node node = parseX("/DATASET/CONTROL/DISPLAY" + strName);
		return getNodeValue(node);
	}

	/**
	 * Kevin 2003-06-06
	 * 
	 * @param argXPath
	 *            String
	 */
	public void query(String argXPath) {
		m_nodeMultiRow = parseX(argXPath);
		m_bBOF = true;
	}

	/**
	 * 获取下一个兄弟节点，如果找不到，将判断大数据标志是否为真
	 * 
	 * @return
	 */
	public boolean next() {
		boolean bReturn = true;

		if (m_nodeMultiRow == null) {
			bReturn = false;
		} else {
			if (m_bBOF) { // 如果是第一条记录
				bReturn = true;
				m_bBOF = false;
			} else {
				bReturn = false;
				boolean flag = false;// 是否遍历到原节点
				Element parent = m_nodeMultiRow.getParent();
				for (int i = parent.indexOf(m_nodeMultiRow), size = parent
						.nodeCount(); i < size; i++) {// 本节点位置，开始查询
					Node aNode = (Node) parent.node(i);
					if (!flag) {
						if (m_nodeMultiRow.equals(aNode)) {
							flag = true;// 找到原节点位置
						}
					} else {
						if (aNode.getNodeType() == Node.ELEMENT_NODE) {
							m_nodeMultiRow = aNode;// 获得下一个兄弟节点
							bReturn = true;
							break;
						}
					}
				}
				if (!bReturn && mElementHandler.hugeSizeFlag) {// 如果没有找到下一个节点，且大数据标志为真
					if (mElementHandler.xPathString.substring(
							mElementHandler.xPathString.lastIndexOf("/") + 1)
							.equals(m_nodeMultiRow.getName())) {// 节点名称要相同
						if (mElementHandler.next()) {
							SAXReader reader = new SAXReader();
							reader.setDefaultHandler(mElementHandler);
							try {
								String aPath = m_nodeMultiRow.getPath();// 获取路径
								parent.clearContent();// 父节点清空子节点
								Element root = reader
										.read(
												new ByteArrayInputStream(
														mElementHandler
																.getObjectbyte()))
										.getRootElement();
								parent.appendContent(root);// 加入下一段数据集
								m_nodeMultiRow = parseX(aPath);// 获取新的兄弟节点
								bReturn = true;
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		}

		return bReturn;
	}

	/**
	 * 按照列的名字取得一个值
	 * 
	 * @param strChildPath
	 *            String
	 * @return String
	 */
	public String getString(String strChildPath) {
		if (m_nodeMultiRow == null) {
			return "";
		}
		Node node = null;
		List aList = m_nodeMultiRow.selectNodes("");
		for (int i = 0, size = aList.size(); i < size; i++) {
			node = (Node) aList.get(i);
			if (node.getName().equals(strChildPath)) {
				strReturn = node.getText();
				break;
			}
		}
		return strReturn;
	}

	/**
	 * 按照列索引取得某一个值
	 * 
	 * @param nIndex
	 *            int
	 * @return String
	 */
	public String getString(int nIndex) {

		if (nIndex < 0 || m_nodeMultiRow == null) {
			return "";
		}
		Node node = null;
		Element a_nodeMultiRow = null;
		if (m_nodeMultiRow.getNodeType() == Node.ELEMENT_NODE) {
			a_nodeMultiRow = (Element) m_nodeMultiRow;
		} else {
			return "";
		}
		for (int nCount = 0, size = a_nodeMultiRow.nodeCount(); nCount < size; nCount++) {
			node = (Node) a_nodeMultiRow.node(nCount);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				if (nCount == nIndex) {
					break;
				}
			}
		}
		strReturn = node.getText();
		return strReturn;
	}

	/**
	 * 得到当前行的列数
	 * 
	 * @return int
	 */
	public int getColCount() {
		if (m_nodeMultiRow == null) {
			return -1;
		}
		Node node = null;
		Element a_nodeMultiRow = null;
		if (m_nodeMultiRow.getNodeType() == Node.ELEMENT_NODE) {
			a_nodeMultiRow = (Element) m_nodeMultiRow;
		} else {
			return -1;
		}
		if (a_nodeMultiRow == null) {
			return -1;
		}
		int nCount = 0;
		for (int nIndex = 0, size = a_nodeMultiRow.nodeCount(); nIndex < size; nIndex++) {
			node = (Node) a_nodeMultiRow.node(nIndex);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				nCount++;
			}
		}
		return nCount;
	}

	/**
	 * 获得某一个列的索引值
	 * 
	 * @param strChildPath
	 *            String
	 * @return int
	 */
	public int getColIndex(String strChildPath) {
		strChildPath = XMLPrintTagName.changeCOLTOC(strChildPath);
		if (m_nodeMultiRow == null) {
			return -1;
		}
		Node node = null;
		Element a_nodeMultiRow = null;
		if (m_nodeMultiRow.getNodeType() == Node.ELEMENT_NODE) {
			a_nodeMultiRow = (Element) m_nodeMultiRow;
		} else {
			return -1;
		}
		int nIndex = -1;
		for (int i = 0, size = a_nodeMultiRow.nodeCount(); i < size; i++) {
			node = (Node) a_nodeMultiRow.node(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			if (node.getName().equals(strChildPath)) {
				nIndex = i;
				break;
			}
		}
		return nIndex;
	}

	/**
	 * 根据数据的大小决定xml元素解析控制类的设置，根据打印控制标志设置元素控制类的设置。
	 * 
	 * @param reader
	 * @param printControlFlag
	 */
	private void setElementHandler(SAXReader reader, boolean printControlFlag) {
		int length = this.inputLength;
		if (length <= 26000000) {// 小于25m的文件直接读取
			return;
		}
		if (length > 26000000 && length < 36000000) {
			this.mElementHandler.hugeSize = 30000;// 一次读取3w条
		}
		if (length >= 36000000) {
			this.mElementHandler.hugeSize = 10000;
		}
		if (printControlFlag) {
			this.mElementHandler.printControlFlag = true;
			this.mElementHandler.hugeSize = 1000;
		}
		reader.setDefaultHandler(mElementHandler);
	}

	public static void main(String[] args) throws FileNotFoundException {
		File tFile = new File("c:/1.xml");
		FileInputStream tFIS = new FileInputStream(tFile);
		XmlFunc tXmlFunc = new XmlFunc(tFIS);
		String tNodeValue = tXmlFunc.getNodeValue("/DATASET/CONTROL/PrintName");
		logger.debug(tNodeValue);

		String strXPath = "/DATASET/CONTROL";
		String strNodeName = "PrintName";

		tXmlFunc.query(strXPath);
		int nColIndex = tXmlFunc.getColIndex(strNodeName);

		while (tXmlFunc.next()) {
			logger.debug("nColIndex is " + nColIndex);
			logger.debug(tXmlFunc.getString(nColIndex));
		}

		strXPath = "/DATASET/INFO/ROW";
		strNodeName = "COL1";

		tXmlFunc.query(strXPath);
		nColIndex = tXmlFunc.getColIndex(strNodeName);

		while (tXmlFunc.next()) {
			logger.debug("nColIndex is " + nColIndex);
			logger.debug(tXmlFunc.getString(nColIndex));
		}

		strXPath = "/DATASET/BarCodeInfo/BarCode";
		strNodeName = "Code";

		tXmlFunc.query(strXPath);
		nColIndex = tXmlFunc.getColIndex(strNodeName);
		int tColIndex = tXmlFunc.getColIndex("Param");

		while (tXmlFunc.next()) {
			logger.debug("nColIndex is " + nColIndex);
			logger.debug(tXmlFunc.getString(nColIndex));
			logger.debug(tXmlFunc.getString(tColIndex));
		}
	}
}
