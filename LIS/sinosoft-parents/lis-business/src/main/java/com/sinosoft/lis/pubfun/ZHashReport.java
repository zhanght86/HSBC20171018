package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.F1PrintParser;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * 此类旨为增加F1Print的效率，将行的复杂度转换为列的复杂度
 * 
 * @author zhangzm
 */
public class ZHashReport {
private static Logger logger = Logger.getLogger(ZHashReport.class);
	public final DataType DoubleType = new DataType("double", true);
	public final DataType IntType = new DataType("int", true);
	public final DataType StringType = new DataType("string", false);

	public CErrors mErrors = new CErrors();
	private HashMap mResultHash = new HashMap();
	private int mRowCount = 0;
	private int mColCount = 0;
	private int mKeyCol = 0;
	private VData mDataTypes = new VData();
	private boolean mSumAll = false;
	private String[] mSumCols = null;
	private SQLwithBindVariables mKeySql = null;
	private ArrayList msqls = new ArrayList();
	private HashMap mValues = new HashMap();
	private HashMap mSortMap = new HashMap();
	private String[][] mResultStr = null;
	private String mTemplatePath = null;
	private boolean mDoformat = true;
	private String compareStr = null;
	private MultiComparator comparator = null;

	/**
	 * 构造函数
	 * 
	 * @param keySql
	 *            用于指定关键字列的SQL,可以为多列用于多关键字 查询。此列信息也会记入显示列中，因此对于简单查询可以进录入
	 *            此一SQL即可。支持按顺序显示。
	 */
	public ZHashReport(SQLwithBindVariables keySql) {
		mKeySql = keySql;
	}

	/**
	 * 构造函数
	 * 
	 * @param keySql
	 *            keySql用于指定关键字列的SQL,可以为多列用于多关键字 查询。此列信息也会记入显示列中，因此对于简单查询可以进录入
	 *            此一SQL即可。支持按顺序显示。
	 * @param sqls
	 *            指标SQL
	 */
	public ZHashReport(SQLwithBindVariables keySql, VData sqls) {
		this(keySql);
		for (int i = 0; i < sqls.size(); i++) {
			this.addSql((SQLwithBindVariables) sqls.get(i));
		}
	}

	/**
	 * 计算关键字列
	 * 
	 * @param keySql
	 *            关键字列SQL
	 */
	private void calKeySql(SQLwithBindVariables keySql) {
		ExeSQL calExeSQL = new ExeSQL();
		SSRS ssrs = calExeSQL.execSQL(keySql);
		if (calExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "sql error:" + keySql);
			return;
		}
		int keyRow = ssrs.getMaxRow();
		logger.debug("MaxRow:" + keyRow);

		int keyCol = ssrs.getMaxCol();
		mColCount = mKeyCol = keyCol;
		if (keyRow == 0) {
			logger.debug("没有符合条件的统计项！");
			return;
		}

		for (int i = 1; i <= keyRow; i++) {
			VData tVData = new VData();
			String key = "";

			for (int j = 1; j <= keyCol; j++) {
				key += ssrs.GetText(i, j);
				setValue(tVData, j - 1, ssrs.GetText(i, j));
				// tVData.add(ssrs.GetText(i, j));
			}

			// mVData存储了主键相关的字符串，tVData存储了主键的指标的默认字符串
			if (!mResultHash.containsKey(key)) {
				mSortMap.put(String.valueOf(mResultHash.size()), key);
				mResultHash.put(key, tVData);
				mRowCount++;
			}
		}
	}

	/**
	 * 添加指标列，其中前n列（n为关键字所指定的列数）应与关键字列的 查询相对应，否则不会被计算
	 * 
	 * @param sql
	 *            指标列SQL
	 */
	public void addSql(SQLwithBindVariables sql) {
		msqls.add(sql);
	}

	/**
	 * 计算指标列
	 * 
	 * @param sql
	 *            指标列SQL
	 */
	private void calSql(SQLwithBindVariables sql) {
		ExeSQL calExeSQL = new ExeSQL();
		SSRS ssrs = calExeSQL.execSQL(sql);
		if (calExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "sql error:" + sql);
			return;
		}
		int row = ssrs.getMaxRow();
		logger.debug("MaxRow:" + row);

		int col = ssrs.getMaxCol();
		if (col <= mKeyCol) {
			CError.buildErr(this, "无效");
		}

		for (int _Row = 1; _Row <= row; _Row++) {
			String key = "";

			for (int _Col = 1; _Col <= mKeyCol; _Col++) {
				key += ssrs.GetText(_Row, _Col);
			}

			if (mResultHash.containsKey(key)) {
				VData mVData = (VData) mResultHash.get(key);

				// ssrs.MaxCol-MaxCol为该条sql的指标数，可能为多条
				for (int num = 1, tempitemcount = mColCount; num <= col
						- mKeyCol; num++, tempitemcount++) {
					setValue(mVData, tempitemcount, ssrs.GetText(_Row, mKeyCol
							+ num));
				}

			}
		}
		mColCount += col - mKeyCol;

	}

	/**
	 * 计算所有项
	 * 
	 * @return 结果集，此集合包换关键字列和指标列，但不包括累计和
	 */
	public String[][] calItem() {
		calKeySql(mKeySql);
		for (Iterator iter = msqls.iterator(); iter.hasNext();) {
			SQLwithBindVariables sql = (SQLwithBindVariables) iter.next();
			calSql(sql);
		}
		Object[][] res = new Object[mRowCount][mColCount];
		for (int i = 0; i < mRowCount; i++) {
			VData tempVData = (VData) mResultHash.get(mSortMap.get(String
					.valueOf(i)));

			for (int j = 0; j < mColCount; j++) {
				if (j == tempVData.size()) {
					tempVData.add(getColumnType(j).getDefaultvalue());
				}
				res[i][j] = tempVData.get(j);
			}
		}

		if (comparator != null) {
			Vector list = new Vector();
			for (int i = 0; i < res.length; i++) {
				Vector v = new Vector();
				Object[] rs = res[i];
				for (int j = 0; j < rs.length; j++) {
					v.add(rs[j]);
				}
				list.add(v);
			}
			Collections.sort(list, comparator);
			for (int i = 0; i < list.size(); i++) {
				Vector v = (Vector) list.get(i);
				for (int j = 0; j < v.size(); j++) {
					res[i][j] = v.get(j);
				}
			}
		}

		if (mSumAll) {
			mSumCols = new String[mColCount];
			for (int i = 0; i < mColCount; i++) {
				DataType type = getColumnType(i);
				Object tsum = type.getDefaultvalue();
				for (int j = 0; j < mRowCount; j++) {
					tsum = type.sum(tsum, res[j][i]);
				}
				if (mDoformat)
					mSumCols[i] = type.format(tsum);
				else
					mSumCols[i] = String.valueOf(tsum);
			}
		}

		mResultStr = new String[mRowCount][mColCount];
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[i].length; j++) {
				DataType type = getColumnType(j);
				if (mDoformat)
					mResultStr[i][j] = type.format(res[i][j]);
				else
					mResultStr[i][j] = String.valueOf(res[i][j]);
			}
		}
		return mResultStr;
	}

	/**
	 * 生成XML文档，可传给F1Print解析 其中包含普通值，多行解析值，累计和值 普通值：手工指定变量名，格式为 $=/name$
	 * 多行解析值：格式为$*\/DATA/ROW/COLn（n为从1开始） 累计和值：变量名为 $=/SUMn$（n为从1开始，对应COL）
	 * 
	 * @param vtsName
	 *            模版文件
	 * @return
	 */
	public XmlExport createXml(String vtsName) {
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument(vtsName, "Printer");
		addToXML(xmlexport, "DATA");
		return xmlexport;

	}

	public void addToXML(XmlExport xmlexport, String rowName) {
		if (mResultStr == null)
			calItem();
		ListTable tListTable = new ListTable();
		tListTable.setName(rowName);
		for (int i = 0; i < mResultStr.length; i++) {
			tListTable.add(mResultStr[i]);
		}
		if (mResultStr.length > 0)
			xmlexport
					.addListTable(tListTable, new String[mResultStr[0].length]);
		TextTag tTextTag = new TextTag();
		if (mSumAll) {
			for (int i = 0; i < mColCount; i++) {
				tTextTag.add(rowName + "_SUM" + (i + 1), mSumCols[i]);
			}
		}
		for (Iterator iter = mValues.entrySet().iterator(); iter.hasNext();) {
			Map.Entry element = (Map.Entry) iter.next();
			tTextTag
					.add((String) element.getKey(), (String) element.getValue());
		}
		xmlexport.addTextTag(tTextTag);
	}

	/**
	 * 获取列累计和数组，索引从0开始
	 * 
	 * @return
	 */
	public String[] getSumCols() {
		if (mSumAll && mSumCols != null)
			return mSumCols;
		else {
			CError.buildErr(this, "没有统计值");
			return null;
		}
	}

	public String getCompareStr() {
		return compareStr;
	}

	/**
	 * 设置列排序规则
	 * 
	 * @param compareStr
	 *            可以为多列，可以设置是否升序。格式为 colindex1:true;colindex2:false
	 *            其中升降序可以不设，默认为true，也可以用asc和desc
	 * @throws Exception
	 */
	public void setCompareStr(String compareStr)
			throws IllegalArgumentException {
		if (compareStr != null && !compareStr.equals("")) {
			MultiComparator comp = null;
			String[] cs = compareStr.split(";");
			for (int i = 0; i < cs.length; i++) {
				if (cs[i].trim().equals(""))
					continue;
				String[] ss = cs[i].split(":");
				int colindex;
				try {
					colindex = Integer.parseInt(ss[0]);
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException("非法操作" + ex.toString());
				}
				boolean isasc = true;
				if (ss.length > 1) {
					if (ss[1].equals("true") || ss[1].equals("asc"))
						isasc = true;
					else if (ss[1].equals("false") || ss[1].equals("desc"))
						isasc = false;
					else
						throw new IllegalArgumentException("非法操作定义");
				}
				if (i == 0)
					comp = new MultiComparator(colindex, isasc);
				else
					comp.addComparator(colindex, isasc);
			}
			this.comparator = comp;
			this.compareStr = compareStr;
		}
	}

	/**
	 * 根据模版输出到指定流中
	 * 
	 * @param os
	 *            输出流
	 * @param vtsname
	 *            模版名，请先设置模版路径setTemplatePath()
	 */
	public XmlExport outputToStream(OutputStream os, String vtsname) {
		XmlExport export = createXml(vtsname);
		outputToStream(export, os, vtsname);
		return export;
	}

	public void outputToStream(XmlExport export, OutputStream os, String vtsname) {
		F1PrintParser parser;
		try {
			parser = new F1PrintParser(export.getInputStream(),
					getTemplatePath());
		} catch (FileNotFoundException e) {
			CError.buildErr(this, e.getMessage());
			e.printStackTrace();
			return;
		} catch (IOException e) {
			CError.buildErr(this, e.getMessage());
			e.printStackTrace();
			return;
		}
		if (!parser.output(os))
			CError.buildErr(this, "没有解析成功");
	}

	public boolean isDoformat() {
		return mDoformat;
	}

	public void setDoformat(boolean doformat) {
		mDoformat = doformat;
	}

	/**
	 * 获取模版路径。
	 * 
	 * @return
	 */
	public String getTemplatePath() {
		if (mTemplatePath == null) {
			mTemplatePath = "/f1print/template/";
		}
		return mTemplatePath;
	}

	/**
	 * 设置模版路径，如果不设置会找不到模版
	 * 
	 * @param templatepath
	 */
	public void setTemplatePath(String templatepath) {
		mTemplatePath = templatepath;
	}

	/**
	 * 设置列类型。其中默认第一列为<tt>DataType.StringType</tt> 其余列为<tt>DataType.DoubleType</tt>。可以只设置需要列的类型
	 * 
	 * @param index
	 *            从0开始
	 * @param type
	 *            列类型。可为<tt>DataType.StringType</tt>，<tt>DataType.IntType</tt>，<tt>DataType.DoubleType</tt>
	 */
	public void setColumnType(int index, DataType type) {
		getColumnType(index);
		mDataTypes.set(index, type);
	}

	/**
	 * 设置是否计算累计和
	 * 
	 * @param op
	 */
	public void setSumColumn(boolean op) {
		mSumAll = op;
	}

	/**
	 * 增加变量值
	 * 
	 * @param name
	 *            vts模版中$=/name/所用的name
	 * @param value
	 *            所要替换的值
	 */
	public void addValue(String name, String value) {
		mValues.put(name, value);
	}

	/**
	 * 获取变量值
	 * 
	 * @param name
	 *            变量名
	 * @return
	 */
	public String getValue(String name) {
		return (String) mValues.get(name);
	}

	/**
	 * 设置指定列位置的值
	 * 
	 * @param aVData
	 *            一行数据的容器
	 * @param index
	 *            列位置，从0开始
	 * @param value
	 *            列值
	 */
	private void setValue(VData aVData, int index, String value) {
		DataType type = getColumnType(index);
		if (index >= aVData.size()) {
			for (int i = aVData.size(); i <= index; i++) {
				aVData.add(getColumnType(i).getDefaultvalue());
			}
		}
		aVData.set(index, type.parser(value, aVData.get(index)));
	}

	/**
	 * 获取指定列的类型。若未设置过末列类型，则默认第一列为StringType,其余列为DoubleType
	 * 
	 * @param index
	 *            指定索引，从0开始
	 * @param type
	 *            指定的类型
	 */
	public DataType getColumnType(int index) {
		int count = mDataTypes.size();
		if (index >= count) {
			for (int n = index - count; n >= 0; n--)
				mDataTypes.add(DoubleType);
			if (count == 0)
				mDataTypes.set(0, StringType);
		}
		return (DataType) mDataTypes.get(index);
	}

	public int getColCount() {
		return mColCount;
	}

	public int getKeyCol() {
		return mKeyCol;
	}

	public int getRowCount() {
		return mRowCount;
	}

	public DataType createDataType(String type) {
		return new DataType(type);
	}

	public DataType createDataType(String type, boolean doadd) {
		return new DataType(type, doadd);

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// String kSql = "select comcode from ldcom where
		// length(trim(comcode))=4 and comcode!='8699' order by comcode";
		// String fSql = "select comcode,substr(shortname,1,2) from ldcom where
		// length(trim(comcode))=4 and comcode!='8699'";
		// String sql1 = "select substr(managecom,1,4),count(distinct prtno),
		// sum(prem) from lcpol where makedate>='2005-1-1' and
		// makedate<='2005-5-1' /*and lcpol.salechnl='03'*/ and exists(select 1
		// from es_doc_main doc where doc.doccode=trim(lcpol.prtno) )and
		// exists(select 1 from ljtempfee fee where EnterAccDate is not null
		// /*and tempfeetype in ('1','5','6')*/ and(lcpol.polno=fee.otherno or
		// (fee.othernotype='4' and lcpol.prtno=fee.otherno)))group by
		// substr(managecom,1,4)";

//		String strStartDate = "2005-1-1";
//		String strEndDate = "2005-2-1";
//		String kSql = "select Operator, managecom from("
//				+ " select distinct Operator,managecom from LCPol where MakeDate >='"
//				+ strStartDate.trim()
//				+ "' and MakeDate <='"
//				+ strEndDate.trim()
//				+ "' union "
//				+ " select distinct Operator, managecom from LBPol where MakeDate >='"
//				+ strStartDate.trim() + "' and MakeDate <='"
//				+ strEndDate.trim() + "'" + ")order by Operator, managecom";
//
//		String sql1 = "select operator, managecom,nvl(count(distinct(PrtNo)),0),sum(Prem) from LCPol where SaleChnl = '01'  and MakeDate >='"
//				+ strStartDate.trim()
//				+ "' and MakeDate <='"
//				+ strEndDate.trim()
//				+ "' and mainpolno = polno  and RenewCount = '0' group by operator, managecom union "
//				+ "select operator, managecom,nvl(count(distinct(PrtNo)),0),sum(Prem) from LbPol where SaleChnl = '01'  and MakeDate >='"
//				+ strStartDate.trim()
//				+ "' and MakeDate <='"
//				+ strEndDate.trim()
//				+ "'and mainpolno = polno and RenewCount = '0' group by operator, managecom ";
		//ZHashReport rep = new ZHashReport(kSql);
		// rep.addSql(fSql);
		//rep.addSql(sql1);
		//rep.setColumnType(1, rep.StringType);
		//rep.setColumnType(2, rep.IntType);
		//rep.setSumColumn(true);
		// rep.addValue("Start","2005-1-1");
		// rep.addValue("End","2005-5-1");
		//FileOutputStream fos = new FileOutputStream("e:/out.vts");
		//rep.outputToStream(fos, "BankChannelTempFee.vts");
		// String[][]result=rep.calItem();
		// for(int i=0;i<result.length;i++){
		// for(int j=0;j<result[i].length;j++){
		// logger.debug(result[i][j]+"\t");
		// }
		// 
		// }
		// XMLExport export=rep.createXml("somevts.vts");
	}

	protected class DataType {
		String mType = "";
		boolean mDoadd = false;
		private Object defaultvalue = null;
		DecimalFormat formatter = null;

		public String format(Object v) {
			if (formatter == null)
				return String.valueOf(v);
			if (mType.equals("double"))
				return formatter.format(((Double) v).doubleValue());
			else if (mType.equals("int"))
				return formatter.format(((Long) v).longValue());
			else
				return String.valueOf(v);

		}

		protected Object clone() throws CloneNotSupportedException {
			DataType d = new DataType(mType, mDoadd);
			d.setFormatter(formatter);
			d.setDefaultvalue(defaultvalue);
			return d;
		}

		public Object getDefaultvalue() {
			if (defaultvalue == null) {
				if (mType.equals("double")) {
					defaultvalue = new Double(0);
				} else if (mType.equals("int")) {
					defaultvalue = new Long(0);
				} else {
					defaultvalue = "";
				}
			}

			return defaultvalue;
		}

		private void setDefaultvalue(Object defaultvalue) {
			this.defaultvalue = defaultvalue;
		}

		public DecimalFormat getFormatter() {
			return formatter;
		}

		public void setFormatter(DecimalFormat formatter) {
			this.formatter = formatter;
		}

		public DataType(String type) {
			this.mType = type.toLowerCase();
			if (mType.equals("double"))
				formatter = new DecimalFormat("0.00");
		}

		public DataType(String type, boolean doadd) {
			this(type);
			this.mDoadd = doadd;
		}

		public Object sum(Object v1, Object v2) {
			if (mType.equals("double")) {
				return new Double(((Double) v1).doubleValue()
						+ ((Double) v2).doubleValue());
			} else if (mType.equals("int")) {
				return new Long(((Long) v1).longValue()
						+ ((Long) v2).longValue());
			}
			return "合计";
		}

		public Object parser(String value, Object oldvalue) {
			if (mType.equals("double")) {
				double v = Double.parseDouble(value);
				if (mDoadd && oldvalue != null && oldvalue != defaultvalue)
					v += ((Double) oldvalue).doubleValue();
				return new Double(v);
			} else if (mType.equals("int")) {
				long v = Long.parseLong(value);
				if (mDoadd && oldvalue != null && oldvalue != defaultvalue)
					v += ((Long) oldvalue).longValue();
				return new Long(v);
			} else
				return value;
		}
	}

}
