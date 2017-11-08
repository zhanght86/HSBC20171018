package com.sinosoft.utility;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLwithBindVariables {

	// public final static String dbType = "MYSQL"; // 应放在系统全局变量里控制
	public final static String DBType = com.sinosoft.utility.SysConst.DBTYPE;

	private final static String bindVariableOpenToken = "?";
	private final static String bindVariableCloseToken = "?";
	private final static String variableOpenToken = "${";
	private final static String variableCloseToken = "}";

	private HashMap<String, BindData> values = new HashMap<String, BindData>();
	private String sql = null;
	private String originalSql = null;
	private ArrayList<String> keys = new ArrayList<String>();
	private boolean parsed = false;

	private static Map<String, ResourceBundle> rbMap = null;

	private class BindData {
		public String key;
		public int jdbcType;

		public String strValue;
		public java.sql.Date dateValue;
		public int intValue;
		public double dblValue;

		public boolean collection;
		public ArrayList<String> strList;
		public ArrayList<Integer> intList;
	}

	public void put(SQLwithBindVariables sqlwithBV) {
		this.sql(sqlwithBV.originalSql);
		for (String key : sqlwithBV.values.keySet()) {
			this.put(key, sqlwithBV.values.get(key));
		}
	}

	@SuppressWarnings("unchecked")
	private void put(String key, BindData value) {
		BindData bv = new BindData();
		bv.collection = value.collection;
		bv.key = value.key;
		bv.jdbcType = value.jdbcType;
		if (value.strValue != null)
			bv.strValue = value.strValue;
		if (value.dateValue != null)
			bv.dateValue = value.dateValue;
		bv.intValue = value.intValue;
		bv.dblValue = value.dblValue;
		if (value.strList != null)
			bv.strList = (ArrayList<String>) value.strList.clone();
		if (value.intList != null)
			bv.intList = (ArrayList<Integer>) value.intList.clone();
		values.put(key, bv);
	}

	public void put(String key, String value, int jdbcType) throws Exception {
		BindData bv = new BindData();
		bv.collection = false;
		bv.key = key;
		bv.jdbcType = jdbcType;
		if (jdbcType == java.sql.Types.CHAR || jdbcType == java.sql.Types.VARCHAR)
			bv.strValue = value;
		else if (jdbcType == java.sql.Types.DATE)
			bv.dateValue = java.sql.Date.valueOf(value);
		else if (jdbcType == java.sql.Types.INTEGER)
			bv.intValue = Integer.parseInt(value);
		else if (jdbcType == java.sql.Types.DOUBLE)
			bv.dblValue = Double.parseDouble(value);
		else {
			throw new Exception("不支持的数据类型");
		}
		values.put(key, bv);
	}

	public void put(String key, String value) {
		BindData bv = new BindData();
		bv.collection = false;
		bv.key = key;
		bv.strValue = value;
		bv.jdbcType = java.sql.Types.VARCHAR;
		values.put(key, bv);
	}

	public void put(String key, int value) {
		BindData bv = new BindData();
		bv.collection = false;
		bv.key = key;
		bv.intValue = value;
		bv.jdbcType = java.sql.Types.INTEGER;
		values.put(key, bv);
	}

	public void put(String key, double value) {
		BindData bv = new BindData();
		bv.collection = false;
		bv.key = key;
		bv.dblValue = value;
		bv.jdbcType = java.sql.Types.DOUBLE;
		values.put(key, bv);
	}

	public void put(String key, Date value) {
		BindData bv = new BindData();
		bv.collection = false;
		bv.key = key;
		bv.dateValue = new java.sql.Date(value.getTime());
		bv.jdbcType = java.sql.Types.DATE;
		values.put(key, bv);
	}

	public void put(String key, ArrayList<String> values) {
		BindData bv = new BindData();
		bv.collection = true;
		bv.key = key;
		bv.strList = values;
		bv.jdbcType = java.sql.Types.VARCHAR;
		this.values.put(key, bv);
	}

	public void putIntList(String key, ArrayList<Integer> values) {
		BindData bv = new BindData();
		bv.collection = true;
		bv.key = key;
		bv.intList = values;
		bv.jdbcType = java.sql.Types.INTEGER;
		this.values.put(key, bv);
	}

	public boolean sql(String sql) {
		if (sql == null)
			return false;
		this.originalSql = sql;// 放在this.sql之前,即使错误也保存
		if (!checkSQL(sql))
			return false;
		this.sql = sql;
		this.parsed = false;
		this.keys.clear();
		return true;
	}

	private boolean checkSQL(String sql) {
		if ("?".equals(bindVariableOpenToken) || "?".equals(bindVariableCloseToken)) {
			Pattern pattern = Pattern.compile("\\?");
			Matcher matcher = pattern.matcher(sql);
			int count = 0;
			while (matcher.find()) {
				count++;
			}
			if (count % 2 == 0)
				return true;
			else {
				// throw new Exception("SQL中“?”数量不匹配");
				return true;
			}
		}
		return true;
	}

	private static Integer LOCK = 0;

	public boolean sql(Object obj, String index) throws Exception {

		if (rbMap == null) {
			synchronized (LOCK) {
				if (rbMap == null)
					rbMap = new Hashtable<String, ResourceBundle>();
			}
		}

		String className = obj.getClass().getName();
		ResourceBundle rb = null;
		if (rbMap.containsKey(className)) {
			rb = rbMap.get(className);
		} else {
			rb = ResourceBundle.getBundle(className);
			rbMap.put(className, rb);
		}

		String sql = null;
		if (rb.containsKey(index + "_" + DBType)) {
			sql = rb.getString(index + "_" + DBType);
		} else {
			// 获取不到，会有java.util.MissingResourceException异常，这里不屏蔽异常
			sql = rb.getString(index);
		}
		this.originalSql = sql;// 放在this.sql之前,即使错误也保存
		if (!checkSQL(sql))
			return false;
		this.sql = sql;
		this.parsed = false;
		this.keys.clear();
		return true;
	}

	public String sql() {
		parse();
		return sql;
	}

	public String originalSql() {
		return originalSql;
	}

	public Set<String> variableKey() {
		return this.values.keySet();
	}

	public String variableValue(String key) {
		BindData value = values.get(key);
		if (value == null)
			return "null(No BindData)";
		else {
			if (value.collection) {
				if (value.jdbcType == java.sql.Types.INTEGER) {
					String intStr = "";
					for (Integer i : value.intList) {
						intStr += (i + ",");
					}
					if (intStr.length() > 0)
						intStr = intStr.substring(0, intStr.length() - 1);
					return intStr;
				} else {
					String strStr = "";
					for (String i : value.strList) {
						strStr += ("'" + i + "',");
					}
					if (strStr.length() > 0)
						strStr = strStr.substring(0, strStr.length() - 1);
					return strStr;
				}
			} else {
				if (value.jdbcType == java.sql.Types.CHAR || value.jdbcType == java.sql.Types.VARCHAR) {
					if ("".equals(value.strValue) || "null".equalsIgnoreCase(value.strValue))
						return "null(" + value.strValue + ")";
					else
						return "'" + value.strValue + "'";
				} else if (value.jdbcType == java.sql.Types.INTEGER) {
					return String.valueOf(value.intValue);
				} else if (value.jdbcType == java.sql.Types.DOUBLE) {
					return String.valueOf(value.dblValue);
				} else if (value.jdbcType == java.sql.Types.DATE) {
					return "date'" + String.valueOf(value.dateValue) + "'";
				} else {
					return "null(undefined)";
				}
			}
		}
	}

	private void parse() {
		if (!parsed) {
			parse(variableOpenToken, variableCloseToken, false);
			parse(bindVariableOpenToken, bindVariableCloseToken, true);
			// 错误sql修正，主要为了避免出现“'?'”的状况
			sql = sql.replaceAll("'\\s*\\?\\s*'", " ? ");
			parsed = true;
		}
	}

	// 下面这个方法实现Copy自myBatis的GenericTokenParser类
	private void parse(String openToken, String closeToken, boolean bv) {
		final StringBuilder builder = new StringBuilder();
		final StringBuilder expression = new StringBuilder();
		if (sql != null && sql.length() > 0) {
			char[] src = sql.toCharArray();
			int offset = 0;
			// search open token
			int start = sql.indexOf(openToken, offset);
			while (start > -1) {
				if (start > 0 && src[start - 1] == '\\') {
					// this open token is escaped. remove the backslash and
					// continue.
					builder.append(src, offset, start - offset - 1).append(openToken);
					offset = start + openToken.length();
				} else {
					// found open token. let's search close token.
					expression.setLength(0);
					builder.append(src, offset, start - offset);
					offset = start + openToken.length();
					int end = sql.indexOf(closeToken, offset);
					while (end > -1) {
						if (end > offset && src[end - 1] == '\\') {
							// this close token is escaped. remove the backslash
							// and continue.
							expression.append(src, offset, end - offset - 1).append(closeToken);
							offset = end + closeToken.length();
							end = sql.indexOf(closeToken, offset);
						} else {
							expression.append(src, offset, end - offset);
							offset = end + closeToken.length();
							break;
						}
					}
					if (end == -1) {
						// close token was not found.
						builder.append(src, start, src.length - start);
						offset = src.length;
					} else {
						builder.append(handleToken(expression.toString(), bv));
						offset = end + closeToken.length();
					}
				}
				start = sql.indexOf(openToken, offset);
			}
			if (offset < src.length) {
				builder.append(src, offset, src.length - offset);
			}
		}
		sql = builder.toString();
	}

	private String handleToken(String content, boolean bv) {
		BindData bd = values.get(content);
		if (bv) {

			if (bd != null) {
				if (bd.collection) {
					String returnStr = "";
					if (bd.jdbcType == java.sql.Types.INTEGER) {
						for (int i = 0; i < bd.intList.size(); i++) {
							keys.add(bd.key + "|" + i);
							returnStr += "?,";
						}
					} else {
						for (int i = 0; i < bd.strList.size(); i++) {
							keys.add(bd.key + "|" + i);
							returnStr += "?,";
						}
					}

					if (returnStr.endsWith(","))
						returnStr = returnStr.substring(0, returnStr.length() - 1);
					return returnStr;
				} else {
					keys.add(content);
					return "?";
				}
			} else {
				keys.add(content);
				return "?";
			}
		} else {
			if (bd != null) {
				if (bd.collection) {
					String returnStr = "";
					if (bd.jdbcType == java.sql.Types.INTEGER) {
						for (Integer v : bd.intList) {
							returnStr += (v + ",");
						}
					} else {
						for (String v : bd.strList) {
							returnStr += ("'" + v + "',");
						}
					}
					if (returnStr.endsWith(","))
						returnStr = returnStr.substring(0, returnStr.length() - 1);
					return returnStr;
				} else {
					switch (bd.jdbcType) {
					case java.sql.Types.INTEGER:
						return String.valueOf(bd.intValue);
					case java.sql.Types.DOUBLE:
						return String.valueOf(bd.dblValue);
					case java.sql.Types.DATE:
						return String.valueOf(bd.dateValue);
					default:
						if ("".equals(bd.strValue) || "null".equalsIgnoreCase(bd.strValue))
							return "";
						else
							return bd.strValue;
					}
				}
			} else {
				return "";
			}
		}
	}

	public void setParameters(PreparedStatement ps) throws SQLException {
		parse();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			int index = 0;
			if (key.indexOf("|") > -1) {
				index = Integer.parseInt(key.substring(key.lastIndexOf("|") + 1, key.length()));
				key = key.substring(0, key.indexOf("|"));
			}
			BindData value = values.get(key);
			if (value == null)
				ps.setNull(i + 1, java.sql.Types.NULL);
			else {
				if (value.collection) {
					if (value.jdbcType == java.sql.Types.INTEGER) {
						ps.setInt(i + 1, value.intList.get(index));
					} else {
						ps.setString(i + 1, value.strList.get(index));
					}
				} else {
					if (value.jdbcType == java.sql.Types.CHAR) {
						if ("".equals(value.strValue) || "null".equalsIgnoreCase(value.strValue))
							ps.setNull(i + 1, java.sql.Types.CHAR);
						else
							ps.setString(i + 1, value.strValue);
					} else if (value.jdbcType == java.sql.Types.VARCHAR) {
						if ("".equals(value.strValue) || "null".equalsIgnoreCase(value.strValue))
							ps.setNull(i + 1, java.sql.Types.VARCHAR);
						else
							ps.setString(i + 1, value.strValue);
					} else if (value.jdbcType == java.sql.Types.INTEGER) {
						ps.setInt(i + 1, value.intValue);
					} else if (value.jdbcType == java.sql.Types.DOUBLE) {
						ps.setDouble(i + 1, value.dblValue);
					} else if (value.jdbcType == java.sql.Types.DATE) {
						ps.setDate(i + 1, value.dateValue);
					} else {
						ps.setNull(i + 1, value.jdbcType);
					}
				}
			}
		}
	}
}
