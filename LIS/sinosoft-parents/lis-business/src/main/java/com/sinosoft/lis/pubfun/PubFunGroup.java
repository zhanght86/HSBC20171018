package com.sinosoft.lis.pubfun;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

//import com.sinosoft.g_easyscan.PubFunEasyScan;
import com.sinosoft.lis.db.LDQuestionDB;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.schema.LDCustomerIDSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDQuestionSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.VData;

/**
 * <p>Title：团险业务处理函</p>
 * 
 * <p>Description：包含团险业务处理中的公共函数</p>
 * 
 * <p>Copyright：Copyright (c) 2012</p>
 * 
 * <p>Company: Sinosoft</p>
 * 
 * @author 杨治纲
 * @version 8.0
 */
public class PubFunGroup {
	
	private static Logger logger = Logger.getLogger(PubFunGroup.class);
	
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	
	/**
	 * 通过总行、省、市获取保户银行编码
	 * @param tHeadBankCode 总行编码
	 * @param ProvinceCode 省编码
	 * @param tCityCode 市编码
	 * @return
	 */
	public static String getBankCode(String tHeadBankCode, String tProvinceCode, String tCityCode) {
		
		String tBankCode = null;
		StringBuffer tStrBuff = new StringBuffer();
		String tFlag = "0";//modify by songsz 增加标识判断,判断是否已传来省市 0-否,1-是
		ExeSQL tExeSQL = new ExeSQL();
		
		if (tHeadBankCode==null || "".equals(tHeadBankCode)) {
			return tBankCode;
		}
		
		//modify by songsz 20150119 增加该处银行的判断,从总行进行配置处取信息
		tStrBuff = new StringBuffer();
		tStrBuff.append(" select count(1) from ldbankconfig a ");
		tStrBuff.append(" where 1=1 ");
		tStrBuff.append(" and a.headflag='1' and a.pcflag='1' and a.bankcode='"+ "?tHeadBankCode?" +"' ");
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tStrBuff.toString());
		sqlbv1.put("tHeadBankCode", tHeadBankCode);
		String tValue = tExeSQL.getOneValue(sqlbv1);
		if (tValue==null || "".equals(tValue)) {
			return tBankCode;
		} else if ("0".equals(tValue)) {//不需要录入省市,如果此时仅有一方有,那么报错
			
			if ((tProvinceCode==null || "".equals(tProvinceCode)) && (tCityCode!=null && !"".equals(tCityCode))) {
				return tBankCode;
			} else if ((tCityCode==null || "".equals(tCityCode)) && (tProvinceCode!=null && !"".equals(tProvinceCode))) {
				return tBankCode;
			} else if (tProvinceCode!=null && !"".equals(tProvinceCode)&& tCityCode!=null && !"".equals(tCityCode)){
				tFlag = "1";
			}
		} else {//需要录入省市,如果此时仅有一方有,那么报错
			
			if (tProvinceCode==null || "".equals(tProvinceCode)) {
				return tBankCode;
			}
			
			if (tCityCode==null || "".equals(tCityCode)) {
				return tBankCode;
			}
			
			tFlag = "1";
		}
		
		tStrBuff = new StringBuffer();
		tStrBuff.append(" select bankcode from ldbank ");
		tStrBuff.append(" where headbankcode='"+ "?headbankcode?" +"' and state='1' ");
		if ("1".equals(tFlag)) {
			tStrBuff.append(" and province='"+ "?tProvinceCode?" +"' and city='"+ "?tCityCode?" +"' ");
		} else {
			tStrBuff.append(" and province is null and city is null ");
		}
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tStrBuff.toString());
		sqlbv2.put("headbankcode", tHeadBankCode);
		sqlbv2.put("tProvinceCode", tProvinceCode);
		sqlbv2.put("tCityCode", tCityCode);
		tBankCode = tExeSQL.getOneValue(sqlbv2);
		if("".equals(tBankCode)){
			
			String mCurrentDate = PubFun.getCurrentDate();
			String mCurrentTime = PubFun.getCurrentTime();
			tBankCode = PubFun1.CreateMaxNo("BANKCODE", "SN");
			LDBankSchema mLDBankSchema = new LDBankSchema();
			mLDBankSchema.setBankCode(tBankCode);
			mLDBankSchema.setBankName("");
			mLDBankSchema.setHeadBankCode(tHeadBankCode);
			mLDBankSchema.setProvince(tProvinceCode);
			mLDBankSchema.setCity(tCityCode);
			mLDBankSchema.setCounty("");
			mLDBankSchema.setState("1");
			mLDBankSchema.setComCode("00");
			  
			MMap mMMap = new MMap();
			mMMap.put(mLDBankSchema, "INSERT");
			VData mInputData = new VData();
			mInputData.clear();
			mInputData.add(mMMap);
			
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, null)) {
				return "";
			}
		}
		return tBankCode;
	}
	
	/**
	 * 通过主证件类型和证件号
	 * 获取团体客户号
	 * @param tLDCustomerIDSchema
	 * @param GrpName
	 * @return
	 */
	public static String getAppntNo(LDCustomerIDSchema tLDCustomerIDSchema,String GrpName) {
		
		String tAppntNo = null;
		MMap mMMap = new MMap();
		StringBuffer tStrBuff = new StringBuffer();
		tStrBuff.append("select customerno from LDCustomerID ");
		tStrBuff.append("where idflag='00' ");
		tStrBuff.append("and idtype='"+"?idtype?"+"' and idno='"+"?idno?"+"'");
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tStrBuff.toString());
		sqlbv3.put("idtype", tLDCustomerIDSchema.getIDType());
		sqlbv3.put("idno", tLDCustomerIDSchema.getIDNo());
		ExeSQL tExeSQL = new ExeSQL();
		tAppntNo = tExeSQL.getOneValue(sqlbv3);
		if("".equals(tAppntNo)){
			String mCurrentDate = PubFun.getCurrentDate();
			String mCurrentTime = PubFun.getCurrentTime();
			tAppntNo = PubFun1.CreateMaxNo("AppntNo", "SN");
			LDGrpSchema mLDGrpSchema = new LDGrpSchema();
			mLDGrpSchema.setCustomerNo(tAppntNo);
			mLDGrpSchema.setGrpName(GrpName);
			mLDGrpSchema.setState("1");
			mLDGrpSchema.setManageCom("86");
			mLDGrpSchema.setComCode("00");
			mLDGrpSchema.setOperator("001");
			mLDGrpSchema.setMakeDate(mCurrentDate);
			mLDGrpSchema.setMakeTime(mCurrentTime);
			mLDGrpSchema.setModifyOperator("001");
			mLDGrpSchema.setModifyDate(mCurrentDate);
			mLDGrpSchema.setModifyTime(mCurrentTime);
			
			LDCustomerIDSchema mLDCustomerIDSchema = new LDCustomerIDSchema();
			Reflections ref = new Reflections();
			ref.transFields(mLDCustomerIDSchema, tLDCustomerIDSchema);
			mLDCustomerIDSchema.setCustomerNo(tAppntNo);
			mLDCustomerIDSchema.setState("1");
			mLDCustomerIDSchema.setIDFlag("00");
			mLDCustomerIDSchema.setManageCom("86");
			mLDCustomerIDSchema.setComCode("00");
			mLDCustomerIDSchema.setMakeOperator("001");
			mLDCustomerIDSchema.setMakeDate(mCurrentDate);
			mLDCustomerIDSchema.setMakeTime(mCurrentTime);
			mLDCustomerIDSchema.setModifyOperator("001");
			mLDCustomerIDSchema.setModifyDate(mCurrentDate);
			mLDCustomerIDSchema.setModifyTime(mCurrentTime);
			
			mMMap.put(mLDGrpSchema, "INSERT");
			mMMap.put(mLDCustomerIDSchema, "INSERT");
			VData mInputData = new VData();
			mInputData.clear();
			mInputData.add(mMMap);
			
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, null)) {
				return "";
			}
		}
		return tAppntNo;
	}
	/**
	 * 备份团体客户信息
	 * @param customerno
	 * @return
	 */
	public static MMap backGrpInfo(String customerno){
		//记录轨迹
		MMap mMMap = new MMap();
		String tTraceID = PubFun1.CreateMaxNo("GrpTraceID", 10);
		
		StringBuffer mStrBuff = new StringBuffer();
		mStrBuff.append("insert into LDGrpTrace (traceid, customerno, servicepwd, customername, grpnamepy, searchkeyword, businesscategory, busicategory, grpnature, sumnumpeople, mainbusiness, corporation, coridtype, corlid, idexpirydate, onjobnumber, retirenumber, othernumber, rgtcapital, totalassets, netprofitrate, satrap, actuctrl, license, socialinsucode, organizationcode, taxcode, phone, fax, email, founddate, state, blacklistflag, vipvalue, levelcode, upcustoemrno, remark, managecom, comcode, makeoperator, makedate, maketime, modifyoperator, modifydate, modifytime)");
		mStrBuff.append("select '"+"?tTraceID?"+"', customerno, Password, GrpName, grpnamepy, searchkeyword, BusinessBigType, BusinessType, grpnature, Peoples, MainBussiness, corporation, coridtype, corid, coridexpirydate, OnWorkPeoples, OffWorkPeoples, OtherPeoples, RgtMoney, Asset, netprofitrate, satrap, actuctrl, license, SocialInsuNo, organizationcode, taxcode, phone, fax, email, founddate, state, blacklistflag, vipvalue, levelcode, SupCustoemrNo, remark, managecom, comcode, operator, makedate, maketime, modifyoperator, modifydate, modifytime from LDGrp a ");
		mStrBuff.append(" where  customerno='"+"?customerno?"+"' ");
		
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(mStrBuff.toString());
		sqlbv4.put("tTraceID", tTraceID);
		sqlbv4.put("customerno", customerno);
		
		mMMap.put(sqlbv4, "INSERT");
		
		mStrBuff = new StringBuffer();
		mStrBuff.append("insert into LDGrpContactInfoTrace (TraceID,"+"?d1?"+")");
		mStrBuff.append("select '"+"?tTraceID?"+"',"+"?a1?"+" from LDGrpContactInfo a ");
		mStrBuff.append(" where customerno='"+"?customerno?"+"' ");
		
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(mStrBuff.toString());
		sqlbv5.put("d1", PubFunGroup.getFields("LDGrpContactInfo"));
		sqlbv5.put("tTraceID", tTraceID);
		sqlbv5.put("a1", PubFunGroup.getFields("LDGrpContactInfo"));
		sqlbv5.put("customerno", customerno);
		mMMap.put(sqlbv5, "INSERT");
		
		mStrBuff = new StringBuffer();
		mStrBuff.append("insert into LDCustomerAccountTrace (TraceID,"+"?a3?"+")");
		mStrBuff.append("select '"+"?tTraceID?"+"',"+"?a2?"+" from LDCustomerAccount a ");
		mStrBuff.append(" where customerno='"+"?customerno?"+"' ");
		
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(mStrBuff.toString());
		sqlbv6.put("a3", PubFunGroup.getFields("LDCustomerAccount"));
		sqlbv6.put("tTraceID", tTraceID);
		sqlbv6.put("a2", PubFunGroup.getFields("LDCustomerAccount"));
		sqlbv6.put("customerno", customerno);
		mMMap.put(sqlbv6, "INSERT");
		
		mStrBuff = new StringBuffer();
		mStrBuff.append("insert into LDCustomerLinkmanTrace (TraceID,"+"?a4?"+")");
		mStrBuff.append("select '"+"?tTraceID?"+"',"+"?a5?"+" from LDCustomerLinkman a ");
		mStrBuff.append(" where customerno='"+"?customerno?"+"' ");
		
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(mStrBuff.toString());
		sqlbv7.put("a4", PubFunGroup.getFields("LDCustomerLinkman"));
		sqlbv7.put("tTraceID", tTraceID);
		sqlbv7.put("a5", PubFunGroup.getFields("LDCustomerLinkman"));
		sqlbv7.put("customerno", customerno);
		
		mMMap.put(sqlbv7, "INSERT");
		
		mStrBuff = new StringBuffer();
		mStrBuff.append("insert into LDCustomerIDTrace (TraceID,"+"?a8?"+")");
		mStrBuff.append("select '"+"?tTraceID?"+"',"+"?a9?"+" from LDCustomerID a ");
		mStrBuff.append(" where customerno='"+"?customerno?"+"' ");
		
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(mStrBuff.toString());
		sqlbv8.put("a8", PubFunGroup.getFields("LDCustomerID"));
		sqlbv8.put("tTraceID", tTraceID);
		sqlbv8.put("a9", PubFunGroup.getFields("LDCustomerID"));
		sqlbv8.put("customerno", customerno);
		
		mMMap.put(sqlbv8, "INSERT");
		
		return mMMap;
	}
	
	
	/**
	 * 通过表名获取所有字段
	 * @param tPrefix
	 * @param tTableName
	 * @return
	 */
	public static String getFields(String tAlias, String tTableName) {
		
		StringBuffer tStrBuff = new StringBuffer();
		
		try {
			
			Class tClass = Class.forName("com.sinosoft.lis.schema."+ tTableName +"Schema");
			Schema tSchema = (Schema)tClass.newInstance();
			
			for (int i=0;i<tSchema.getFieldCount();i++) {
				
				if (i!=0) {
					tStrBuff.append(", ");
				}
				
				if (tAlias!=null && !"".equals(tAlias)) {
					tStrBuff.append(tAlias + ".");
				}
				
				tStrBuff.append(tSchema.getFieldName(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tStrBuff.toString();
	}
	
	/**
	 * 通过表名获取所有字段
	 * @param tTableName
	 * @return
	 */
	public static String getFields(String tTableName) {
		
		return getFields("", tTableName);
	}
	
	/**
	 * 发送影像问题件
	 * @param mQuesID
	 * @return
	 */
	public static VData sendScanQuest(String mQuesID){
		VData mResult = new VData();
//		MMap mMMap = new MMap();
//		StringBuffer mStrBuff = new StringBuffer();
//		
//		LDQuestionDB tLDQuestionDB = new LDQuestionDB();
//		tLDQuestionDB.setQuesID(mQuesID);
//		if(!tLDQuestionDB.getInfo()){
//			return null;
//		}
//		LDQuestionSchema tLDQuestionSchema = tLDQuestionDB.getSchema();
//		
//		mStrBuff = new StringBuffer();
//		mStrBuff.append("select c.docid from es_doc_main c where c.doccode='"+tLDQuestionSchema.getOtherNo()+"' and c.subtype='"+tLDQuestionSchema.getSubOtherNo()+"'");
//		mStrBuff.append(" union select b.docid from lcgrpcont a,es_doc_main b where a.prtno=b.doccode and a.grpcontno='"+tLDQuestionSchema.getOtherNo()+"' and b.subtype='"+tLDQuestionSchema.getSubOtherNo()+"'");
//		
//		ExeSQL tExeSQL = new ExeSQL();
//		String mDocID = tExeSQL.getOneValue(mStrBuff.toString());
//		//发送到影像
//		String sendInfo[] = PubFunEasyScan.sendIssue(mDocID, tLDQuestionSchema.getMakeOperator(), tLDQuestionSchema.getSendContent());
//		if("0".equals(sendInfo[0])){
//			mStrBuff = new StringBuffer();
//			mStrBuff.append(" update ldquestion set state='3' where quesid='"+mQuesID+"' ");
//		}
//		mMMap.put(mStrBuff.toString(), "UPDATE");
//		mResult.add(mMMap);
//		mResult.add(sendInfo);
		
		return mResult;
	}
	
	/**
	 * 获取承保机构
	 * @param tGrpContNo
	 * @return
	 */
	public static String getManageCom(String tGrpContNo){
		StringBuffer mStrBuff = new StringBuffer();
		mStrBuff.append("select managecom from lcgrpcont ");
		mStrBuff.append("where grpcontno='"+"?tGrpContNo?"+"'");
		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		sqlbv11.sql(mStrBuff.toString());
		sqlbv11.put("tGrpContNo", tGrpContNo);
		ExeSQL tExeSQL = new ExeSQL();
		return tExeSQL.getOneValue(sqlbv11);
	}
	
	/**
	 * 获取系统文件存储路径
	 * @param cSysvar
	 * @return
	 */
	public static String getSysFilePath(String cSysvar) {
		
		ExeSQL tExeSQL = new ExeSQL();
		
		StringBuffer tStrBuff = new StringBuffer();
		tStrBuff.append(" select a.sysvarvalue from ldsysvar a ");
		tStrBuff.append(" where 1=1 ");
		tStrBuff.append(" and a.sysvar='"+ "?cSysvar?" +"' ");
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(tStrBuff.toString());
		sqlbv10.put("cSysvar", cSysvar);
		String tPath = tExeSQL.getOneValue(sqlbv10);
		if (tPath==null || "".equals(tPath)) {
			return null;
		}
		String tCurrentDate = PubFun.getCurrentDate();
		tPath = tPath+tCurrentDate.replace("-","/")+"/";
		
		File tFile = new File(tPath);
		if (!tFile.exists()) {
			tFile.mkdirs();
		}
		
		return tPath;
	}
	
	/**
	 * 获取系统存储文件名
	 * @return
	 */
	public static String getSysFileName() {
		
		return PubFun1.CreateMaxNo("SYSFILENAME", 10);
	}
	
	/**
	 * 获取应用路径
	 * @return
	 */
	public static String getAppPath() {
		
		String tPath = PubFunGroup.class.getResource("").getPath();
		
		String tOS = System.getProperty("os.name").toLowerCase();
		if (tOS!=null && tOS.indexOf("win")!=-1) {
			int tBeginIndex = tPath.indexOf("/") + 1;
			tPath = tPath.substring(tBeginIndex);
		}
		
		tPath = tPath.substring(0, tPath.lastIndexOf("WEB-INF"));
		tPath = tPath.replace("\\", "/");
		
		logger.debug("获取路径：" + tPath);
		
		return tPath;
	}
	
	/**
	 * 人民币大小写转换
	 * @param value
	 * @return
	 */
	public String changeToBig(double value){
		if(value==0){
			return "零";
		}
		DecimalFormat tFormart0= new DecimalFormat("0.00");
		char[] hunit={'拾','佰','仟'};										//段内位置表示
		char[] vunit={'万','亿'};                                        	//段名表示
		char[] digit={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'}; 		//数字表示
		//BigDecimal midVal = new BigDecimal(Math.round(value*100));    	//转化成整形,替换上句
		//String valStr=String.valueOf(midVal);                             //转化成字符串
		
		String vs = tFormart0.format(value);
		String head=vs.substring(0,vs.indexOf("."));						//取整数部分
		String rail=vs.substring(vs.indexOf(".")+1);                  		//取小数部分

		String prefix="";                                                 	//整数部分转化的结果
		String suffix="";                                                	//小数部分转化的结果
		//处理小数点后面的数
		if(rail.equals("00")){                                           	//如果小数部分为0
			suffix="整";
		} else{
			suffix=digit[rail.charAt(0)-'0']+"角"+digit[rail.charAt(1)-'0']+"分"; //否则把角分转化出来
		}
		//处理小数点前面的数
		char[] chDig=head.toCharArray();                   //把整数部分转化成字符数组
		boolean preZero=false;                             //标志当前位的上一位是否为有效0位（如万位的0对千位无效）
		byte zeroSerNum = 0;                               //连续出现0的次数
		for(int i=0;i<chDig.length;i++){                   //循环处理每个数字
			int idx=(chDig.length-i-1)%4;                  //取段内位置
			int vidx=(chDig.length-i-1)/4;                 //取段位置
			if(chDig[i]=='0'){                             //如果当前字符是0
				preZero=true;
				zeroSerNum++;                               //连续0次数递增
				if(idx==0 && vidx >0 &&zeroSerNum < 4){
					prefix += vunit[vidx-1];
					preZero=false;                          //不管上一位是否为0，置为无效0位
				}
			}else{
				zeroSerNum = 0;                             //连续0次数清零
				if(preZero) {                                   //上一位为有效0位
					prefix+=digit[0];                                //只有在这地方用到'零'
					preZero=false;
				}
				prefix+=digit[chDig[i]-'0'];                    //转化该数字表示
				if(idx > 0){
					prefix += hunit[idx-1];                  
				}
				if(idx==0 && vidx>0){
					prefix+=vunit[vidx-1];                      //段结束位置应该加上段名如万,亿
				}
			}
		}

		if(prefix.length() > 0) {
			prefix += '元';                               //如果整数部分存在,则有圆的字样
		}
		return prefix+suffix;                                                            //返回正确表示
	}
	/**
	 * 日期格式转换
	 * @param mDate 2013-03-03
	 * @return 2013年03月03日
	 */
	public String getDateCN(String mDate){
		if("".equals(mDate)){
			return "";
		}
		String[] tt;
		if(mDate.contains("-")){
			tt = mDate.split("-");
		}else if(mDate.contains("/")){
			tt = mDate.split("/");
		}else {
			return "";
		}
		
		return tt[0]+"年"+tt[1]+"月"+tt[2]+"日";
	}
}
