package com.sinosoft.lis.pubfun;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class SQLIDefense {
	private static Properties ruleproperties = null;
	private static boolean mLoadSuccFlag = false;
	private static Logger logger = Logger.getLogger(SQLIDefense.class);
	private static SQLIDefense sqliDefense = null;
	private final static String SWITCH = "_switch";
	
	
	private static Properties csrfproperties = null;
	
	private static Properties keywordproperties = null;
	
	
	/**
	 * 校验参数是否含有CSRF
	 * @param sqlParam
	 * @return
	 */
	public boolean checkCSRFParam(String sqlParam){
		String checkSqlParam = "";
		checkSqlParam = decode(sqlParam);
		if(!checkCSRF(checkSqlParam)){
				logger.info("CSRF不合格："+checkSqlParam);
				return false;
		}
				
		return true;
	}
	
	
	/**
	 * 检验SQL中是否有违规字符
	 * @param sql
	 * @return
	 */
	public boolean checkSQLOnlySelect(String sql){
		String patternStr = "\\s(delete|update|create|truncate|drop)\\s";
		Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
		Matcher match= pattern.matcher(sql.toLowerCase());
		if(match.find()){
			logger.debug("违规SQL："+sql);
			return false;
		}
		return true;
	}
	
	/**
	 * SQL参数校验入口
	 * @param sqlParam
	 * @return
	 */
	public boolean checkSQLParam(String sqlParam){
		String checkSqlParam = "";
		checkSqlParam = decode(sqlParam);
		//SQL参数长度校验
		if(!checkLength(checkSqlParam)){
			logger.info("type1 长度不合格："+checkSqlParam);
			return false;
		}
		
		//SQL关键字校验
		if(!checkKeyWord(checkSqlParam)){
			logger.info("type2 关键字不合格："+checkSqlParam);
			return false;
		}
		
		//正则表达式校验
		if(!checkPattern(checkSqlParam)){
			logger.info("type3 正则表达式不合格："+checkSqlParam);
			return false;
		}
		
		//CSRF规则校验
		if(!checkCSRF(checkSqlParam)){
			logger.info("type4 CSRF不合格："+checkSqlParam);
			return false;
		}
		
		return true;
	}

	private String decode(String sqlParam) {
		//先做转码
		String checkSqlParam = "";
		try {
			checkSqlParam =  URLDecoder.decode(sqlParam.replaceAll("%", "%25"),   "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//checkSqlParam = "";
			checkSqlParam = sqlParam;
			//return true;
		}
		return checkSqlParam;
	}
	
	
	private boolean checkKeyWord(String sqlParam){
		//SQL_PARAM_KEYWORD
		if(!ifCheckON(SQLIDefenseConst.SQL_PARAM_KEYWORD)){
			return true;
		}
		
	
		if(keywordproperties==null){
			String keywordpropertiesFile = this.getPropertiesValue(SQLIDefenseConst.SQL_PARAM_KEYWORD);
			keywordproperties = this.getProperties(keywordpropertiesFile);
		}
		for(Object value:keywordproperties.values()){
			String currentVale = ((String)value).toLowerCase() ;
			if(sqlParam.toLowerCase().indexOf(currentVale)!=-1){
				return false;
			}
		}
		
		return true;
	}
	
	
	//长度校验
	private boolean checkLength(String sqlParam){
		if(!ifCheckON(SQLIDefenseConst.SQL_PARAM_LENGTH)){
			return true;
		}
		String length = this.getPropertiesValue(SQLIDefenseConst.SQL_PARAM_LENGTH);
		if(!("").equals(length)){
			//logger.debug("length+"+length);
			if(sqlParam.length()>Integer.parseInt(length)){
				return false;
			}
		}
		return true;
		
	}
	
	
	//正则表达式规则校验
	private boolean checkPattern(String sqlParam){
		if(!ifCheckON(SQLIDefenseConst.SQL_PARAM_PATTERN)){
			return true;
		}
		
		String patternStr = this.getPropertiesValue(SQLIDefenseConst.SQL_PARAM_PATTERN);
		Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
		Matcher match= pattern.matcher(sqlParam.toLowerCase());
		if(match.find()){
			return false;
		}
		
		return true;
	}
	
	
	
	/**
	 * CSRF关键字校验
	 * @param sqlParam
	 * @return
	 */
	private boolean checkCSRF(String sqlParam){
		if(!ifCheckON(SQLIDefenseConst.SQL_PARAM_CSRF)){
			return true;
		}
		
	
		if(csrfproperties==null){
			String csrfPropertiesFile = this.getPropertiesValue(SQLIDefenseConst.SQL_PARAM_CSRF);
			csrfproperties = this.getProperties(csrfPropertiesFile);
		}
		for(Object value:csrfproperties.values()){
			String currentVale = ((String)value).toLowerCase() ;
			if(sqlParam.toLowerCase().indexOf(currentVale)!=-1){
				return false;
			}
		}
		
		
		return true;
	}
	
	
	
	
	
	
	
	
	private String getPropertiesValue(String key){
		if(ruleproperties!=null){
			return ruleproperties.getProperty(key);
		}
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	private boolean ifCheckON(String checkName){
		String checkOn = this.getPropertiesValue(checkName+SWITCH);
		if(!("").equals(checkOn)&&checkOn.toUpperCase().equals("ON")){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	public static SQLIDefense getInstance() {
		if (sqliDefense == null) {
			sqliDefense = new SQLIDefense();
			initProperties();
		}
		

		return sqliDefense;
	}

	private synchronized Properties getProperties(String fileName){
		
			Properties prop = new Properties();

			InputStream is = SQLIDefense.class
					.getResourceAsStream("/"+fileName);
			try {
				if (is == null) {
					logger.debug("读取配置文件失败");
				}
				if (is != null) {
					prop.load(is);
				}
				

			} catch (Exception e) {
				e.printStackTrace();
				prop = null;
			} finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return prop;
	}
	
	/**
	 * 初始化Properties
	 */
	public synchronized static void initProperties() {
		// 如果为空才重新加载.
		// 防止自动运行通过界面重启对ruleproperties有影响.
		if (ruleproperties == null) {

			Date start = new Date();
			Properties prop = new Properties();

			InputStream is = SQLIDefense.class
					.getResourceAsStream("/sqldefense.properties");

			ruleproperties = prop;
			try {
				if (is == null) {
					logger.debug("读取配置文件失败");
				}
				if (is != null) {
					logger.debug("begin load sqldefense.properties..");
					prop.load(is);
					logger.debug("end load sqldefense.properties..");
					mLoadSuccFlag = true;
				}
				Date end = new Date();
				logger.debug("load properties:\t"
						+ (end.getTime() - start.getTime()) + "\t:" + " used ");

			} catch (Exception e) {
				e.printStackTrace();
				ruleproperties = null;
				mLoadSuccFlag = false;
			} finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SQLIDefense sqlDefense = SQLIDefense.getInstance();
		logger.debug("########:"+sqlDefense.checkSQLParam("and 1=1 (alert( <iframE "));
		
	}

}
