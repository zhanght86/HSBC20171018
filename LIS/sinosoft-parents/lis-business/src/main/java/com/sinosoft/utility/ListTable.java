/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;
import org.apache.log4j.Logger;

import java.util.Vector;

public class ListTable {
private static Logger logger = Logger.getLogger(ListTable.class);
	private Vector myVector = new Vector();
	private int col; // 表的行
	private String name; // 表名

	// @Constructor
	public ListTable() {
	}
	// @Method
	// 新建一个ListTable，参数为一个字符串数组，包含所需的值
	public void add(String[] temparray) {
		myVector.addElement(temparray);
	}

	// 得到指定行列的值
	public String getValue(int column, int row) {
		String[] temparray = new String[column];
		temparray = (String[]) myVector.get(row);
		return temparray[column];
	}

	// 取得第i行的列表的所有值
	public String[] get(int i) {
		return (String[]) myVector.get(i);
	}

	// 得到整个列表的大小
	public int size() {
		return myVector.size();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getCol() {
		return this.col;
	}
}
