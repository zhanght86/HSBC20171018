package com.sinosoft.print.func;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.Node;

/**
 * <p>
 * Title: 元素控制类
 * </p>
 * <p>
 * ClassName: MyElementHandler
 * </p>
 * <p>
 * Description: 用于控制XmlFunc中对大数据（超过25m的xml文件）解析的缓存。
 * </p>
 * 
 * @version 0.1
 * @modify 2012-6-20
 * @author HuangLiang
 * 
 */
public class MyElementHandler implements ElementHandler {

	/**
	 * 大数据标记
	 */
	public boolean hugeSizeFlag = false;

	/**
	 * 读取数据流
	 * 
	 */
	public ByteArrayOutputStream baos;

	/**
	 * 以xml文本暂存的大数据
	 * 
	 */
	private ArrayList<byte[]> byteArray = new ArrayList<byte[]>();

	/**
	 * 一次读取的数据量
	 * 
	 */
	public int hugeSize = 40000;

	/**
	 * 大数据所在绝对路径名
	 * 
	 */
	public String xPathString;

	/**
	 * 读取大数据的起止位置[a,b)，即读取区间
	 * 
	 */
	private int limitA = 0;

	private int limitB = hugeSize;

	/**
	 * 当前读取个数，用于判断是否超出读取范围
	 * 
	 */
	private int currentNum = 0;

	public boolean printControlFlag = false;
	
	public void onEnd(ElementPath elementPath) {
		// TODO Auto-generated method stub
		if (hugeSizeFlag) {
			if (xPathString.equals(elementPath.getPath())) {
				currentNum++;// 只计算大数据节点的数量
				if (currentNum >= limitB || currentNum < limitA) {
					Element e = elementPath.getCurrent();
					e.detach();
					writeElement(e);
				}
			}
		}
		if (elementPath.getCurrent().getParent() != null) {// 为生成路径和设置大数据标志
			if (elementPath.getCurrent().getParent().nodeCount() >= hugeSize
					&& currentNum == 0) {
				currentNum = hugeSize;
				limitB = hugeSize;
				hugeSizeFlag = true;
				xPathString = elementPath.getPath();
				initBAOS();
			}
		}
	}

	public void onStart(ElementPath elementPath) {
	}

	/**
	 * 获取下一个数据集，如果没有则返回false
	 * 
	 * @return
	 */
	public boolean next() {
		if (byteArray.size() == 0) {
			return false;
		}
		currentNum = -1;
		return true;
	}

	/**
	 * 初始化数据输出流
	 * 
	 */
	private void initBAOS() {
		try {
			baos = new ByteArrayOutputStream(hugeSize);
			baos.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes());
			baos.write("<ROOT>".getBytes());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 将传入xml元素转化成xml文本，写入数据流。如果当前数量刚好整除大数据量，则调用setEndOfbyte和initBAOS方法
	 * 
	 * @param element
	 */
	private void writeElement(Element element) {
		if(this.printControlFlag)return;//如果为打印控制，大数据不缓存
		try {
			if (element.getNodeType() != Node.ELEMENT_NODE) {
				return;
			}
			baos.write(element.asXML().getBytes());
			if ((currentNum + 1) % hugeSize == 0) {
				setEndOfbyte();
				initBAOS();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 为数据流写入终止符，将数据流内容存入数组，供调用
	 * 
	 */
	public void setEndOfbyte() {
		try {
			if (baos != null) {
				baos.write("</ROOT>".getBytes());
				baos.flush();
				baos.close();
				byteArray.add(baos.toByteArray());
				baos = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获得下一组数据的字节流，如果输出流没有关闭，则关闭输出流
	 * 
	 * @return
	 */
	public byte[] getObjectbyte() {
		return byteArray.remove(0);
	}
}
