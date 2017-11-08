/*
 * <p>ClassName: InitElementBL </p>
 * <p>Description: InitElementBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company:  </p>
 * @Database:
 * @CreateDate：2005-01-24 18:15:01
 */
package com.sinosoft.lis.config;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDMaxNoConfigDB;
import com.sinosoft.lis.pubfun.CachedLDMaxNo;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDMaxNoRuleLimitPropSchema;
import com.sinosoft.lis.schema.LDMaxNoRuleLimitSchema;
import com.sinosoft.lis.schema.LDMaxNoRulePropSchema;
import com.sinosoft.lis.schema.LDMaxNoRuleSchema;
import com.sinosoft.lis.vschema.LDMaxNoConfigSet;
import com.sinosoft.lis.vschema.LDMaxNoRuleLimitPropSet;
import com.sinosoft.lis.vschema.LDMaxNoRuleLimitSet;
import com.sinosoft.lis.vschema.LDMaxNoRulePropSet;
import com.sinosoft.lis.vschema.LDMaxNoRuleSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

public class LDMaxNoRuleBL {
private static Logger logger = Logger.getLogger(LDMaxNoRuleBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	
	public LDMaxNoRuleBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
	
		String tNoCode = (String)cInputData.get(2);
		String tJsonStr = (String)cInputData.get(1);
		String tShowRule = (String)cInputData.get(3);
		String tLimitType = (String)cInputData.get(4);
		JSONArray jsonArray =JSONArray.fromObject(tJsonStr);   
		ExeSQL tExeSQL = new ExeSQL();
		map = new MMap();
		
		LDMaxNoConfigDB tLDMaxNoConfigDB = new LDMaxNoConfigDB();
		LDMaxNoConfigSet tLDMaxNoConfigSet = new LDMaxNoConfigSet();
		tLDMaxNoConfigDB.setNoCode(tNoCode);
		
		tLDMaxNoConfigSet =  tLDMaxNoConfigDB.query();
		if(tLDMaxNoConfigSet.size()<=0)
		{
			CError.buildErr(this,"请先配置号段信息.");
			return false;
		}
		else
		{
			tLDMaxNoConfigSet.get(1).setShowRule(tShowRule);
		}
		
		if(tLimitType!=null&&tLimitType.equals("0"))
		{
			LDMaxNoRuleSet tLDMaxNoRuleSet = new LDMaxNoRuleSet();
			LDMaxNoRulePropSet tLDMaxNoRulePropSet = new LDMaxNoRulePropSet();

			for (int i = 0; i < jsonArray.size(); i++) {
				LDMaxNoRuleSchema tempLDMaxNoRuleSchema = new LDMaxNoRuleSchema();
				JSONObject jobj = (JSONObject) jsonArray.get(i);
				String tKey = (String) jobj.get("key");
				String elementKey = (String) jobj.get("elementKey");
				JSONArray tArray = (JSONArray) jobj.get("prop");

				tempLDMaxNoRuleSchema.setNoCode(tNoCode);
				tempLDMaxNoRuleSchema.setCode(elementKey);
				tempLDMaxNoRuleSchema.setIDX(i);
				tempLDMaxNoRuleSchema.setRuleCode(tKey);

				tLDMaxNoRuleSet.add(tempLDMaxNoRuleSchema);

				for (int n = 0; n < tArray.size(); n++) {
					LDMaxNoRulePropSchema tempLDMaxNoRulePropSchema = new LDMaxNoRulePropSchema();
					JSONObject tjobj = (JSONObject) tArray.get(n);
					String propcode = (String) tjobj.get("propcode");
					String propname = (String) tjobj.get("propname");
					String propvalue = (String) tjobj.get("propvalue");
					String modifyflag = (String) tjobj.get("modifyflag");
					String showflag = (String) tjobj.get("showflag");

					tempLDMaxNoRulePropSchema.setRuleCode(tKey);
					tempLDMaxNoRulePropSchema.setPropCode(propcode);
					tempLDMaxNoRulePropSchema.setPropName(propname);
					tempLDMaxNoRulePropSchema.setPropValue(propvalue);
					tempLDMaxNoRulePropSchema.setModifyFlag(modifyflag);
					tempLDMaxNoRulePropSchema.setShowFlag(showflag);
					tempLDMaxNoRulePropSchema.setNoCode(tNoCode);

					tLDMaxNoRulePropSet.add(tempLDMaxNoRulePropSchema);
					
					//如果是流水号类型,并且在序列号和最大号之间做切换时,需要同步更新序列或者最大号表
					if(elementKey.toUpperCase().equals("SERIALNO")
							&&propcode.toUpperCase().equals("SEQFLAG"))
					{
						if(propvalue.equals("1"))
						{
							//当前是序列,判断之前是不是非序列,如果非序列,则自动创建序列.
							String tSQL_SEQ = "select propvalue from ldmaxnoruleprop where nocode='"+"?tNoCode?"+"'"
							                + " and rulecode='"+"?tKey?"+"' and upper(propcode)='SEQFLAG' ";
							SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
							sqlbv1.sql(tSQL_SEQ);
							sqlbv1.put("tNoCode",tNoCode);
							sqlbv1.put("tKey",tKey);
							String tSEQ_Value = tExeSQL.getOneValue(sqlbv1);
							if(tSEQ_Value==null||tSEQ_Value.equals("")||tSEQ_Value.equals("0"))
							{
								//需要自动创建序列
								String tSQL = "select notype,nolimit,maxno from ldmaxno where upper(notype) = '"+"?notype?"+"'";
								SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
								sqlbv2.sql(tSQL);
								sqlbv2.put("notype",tNoCode.toUpperCase());
								SSRS tSSRS_MaxNo = new SSRS();
								tSSRS_MaxNo = tExeSQL.execSQL(sqlbv2);
								for(int m=1;m<=tSSRS_MaxNo.getMaxRow();m++)
								{
									String tNoType = tSSRS_MaxNo.GetText(m, 1);
									String tNoLimit = tSSRS_MaxNo.GetText(m, 2);
									String tMaxNo = tSSRS_MaxNo.GetText(m, 3);
							
									String tSQL_Insert = "insert into ldmaxnob  select * from ldmaxno where notype='"+"?tNoType?"+"' "
									                   + " and nolimit='"+"?tNoLimit?"+"'";
									SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
									sqlbv3.sql(tSQL_Insert);
									sqlbv3.put("tNoType",tNoType);
									sqlbv3.put("tNoLimit",tNoLimit);
									map.put(sqlbv3, "INSERT");
									
									String tSQL_Delete = "delete from ldmaxno where notype='"+"?tNoType?"+"' "
					                   				   + " and nolimit='"+"?tNoLimit?"+"'";
									SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
									sqlbv4.sql(tSQL_Delete);
									sqlbv4.put("tNoType",tNoType);
									sqlbv4.put("tNoLimit",tNoLimit);
									map.put(sqlbv4, "DELETE");
									
									String tSQL_MaxNO = "create sequence SEQ_"
							              + tNoType.toUpperCase() + "_" + tNoLimit
							              + " minvalue 1 maxvalue 999999999999 start with "+tMaxNo+" increment by 1 nocache ";
									logger.debug("tSQL_MaxNO:"+tSQL_MaxNO);
									map.put(tSQL_MaxNO, "CREATE");
								}
								if(tSSRS_MaxNo.getMaxRow()<=0)
								{
									//新建的序列,原来号段里面也没有
									String tSQL_MaxNO = "create sequence SEQ_"
							              + tNoCode.toUpperCase() + "_" + "SN"
							              + " minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache ";
									logger.debug("tSQL_MaxNO:"+tSQL_MaxNO);
									map.put(tSQL_MaxNO, "CREATE");
								}
								
							}
							
						}
						else
						{
							//当前不是序列,判断之前是不是序列,如果是序列,则需要反写ldmaxno表.
							String tSQL_SEQ = "select propvalue from ldmaxnoruleprop where nocode='"+"?tNoCode?"+"'"
							                + " and rulecode='"+"?tKey?"+"' and upper(propcode)='SEQFLAG' ";
							SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
							sqlbv5.sql(tSQL_SEQ);
							sqlbv5.put("tNoCode",tNoCode);
							sqlbv5.put("tKey",tKey);
							String tSEQ_Value = tExeSQL.getOneValue(sqlbv5);
							if(tSEQ_Value!=null&&tSEQ_Value.equals("1"))
							{
								String tSQL = "";
								if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
									tSQL = " select a.sequence_name,a.last_number "
									        + " from user_sequences a where a.sequence_name like concat(?sequence_name?,'%')";
								}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
									//修改错误 先注掉   zhangyingfeng 2016-04-20
//									tSQL = " select a.sequence_name,a.last_number "
//									        + " from information_schema.sequences a where a.sequence_name like concat(?sequence_name?,'%')";
									//
								}
								SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
								sqlbv6.sql(tSQL);
								sqlbv6.put("sequence_name","SEQ_"+tNoCode.toUpperCase());
								SSRS tSSRS_SEQ = new SSRS();
								tSSRS_SEQ = tExeSQL.execSQL(sqlbv6);
								for(int m=1;m<=tSSRS_SEQ.getMaxRow();m++)
								{
									String tSQL_Name = tSSRS_SEQ.GetText(m, 1);
									String tMaxNo = tSSRS_SEQ.GetText(m, 2);
									String[] tSEQArray = tSQL_Name.split("_");
									if(tSEQArray.length!=3)
									{
										CError.buildErr(this,"序列:"+tSQL_Name+"命名规则有误,无法拆分!");
										return false;
									}
									String tNoType = tSEQArray[1];
									String tNoLimit = tSEQArray[2];
									String tSQL_Insert = "insert into ldmaxno(notype, nolimit, maxno) values('"
									                   + "?tNoType?"+"','"+"?tNoLimit?"+"',"+"?tMaxNo?"+")";

									SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
									sqlbv7.sql(tSQL_Insert);
									sqlbv7.put("tNoType",tNoType);
									sqlbv7.put("tNoLimit",tNoLimit);
									sqlbv7.put("tMaxNo",tMaxNo);
									map.put(sqlbv7, "INSERT");
									
									String tSQL_Delete = "delete from ldmaxnob where notype='"+"?tNoType?"+"' "
					                   				   + " and nolimit='"+"?tNoLimit?"+"'";
									SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
									sqlbv8.sql(tSQL_Delete);
									sqlbv8.put("tNoType",tNoType);
									sqlbv8.put("tNoLimit",tNoLimit);
									map.put(sqlbv8, "DELETE");
									
									String tDrop = "drop sequence "+tSQL_Name;
									map.put(tDrop, "DROP");
								}
							}
						}
					}
				}

			}

			String tDelete_LDMaxNoRuleProp = "delete from ldmaxnoruleprop where rulecode in "
					+ "("
					+ " select rulecode from ldmaxnorule where nocode='"
					+ "?tNoCode?" + "' " + " ) and nocode='" + "?tNoCode?" + "'";
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tDelete_LDMaxNoRuleProp);
			sqlbv9.put("tNoCode",tNoCode);
			map.put(sqlbv9, "DELETE");

			String tDelete_LDMaxNoRule = "delete from ldmaxnorule where nocode='"
					+ "?tNoCode?" + "' ";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tDelete_LDMaxNoRule);
			sqlbv10.put("tNoCode",tNoCode);
			map.put(sqlbv10, "DELETE");

			if (tLDMaxNoRuleSet.size() > 0) {
				map.put(tLDMaxNoRuleSet, "DELETE&INSERT");
			}
			if (tLDMaxNoRulePropSet.size() > 0) {
				map.put(tLDMaxNoRulePropSet, "DELETE&INSERT");
			}
			map.put(tLDMaxNoConfigSet, "DELETE&INSERT");
		}
		else
		{
			LDMaxNoRuleLimitSet tLDMaxNoRuleLimitSet = new LDMaxNoRuleLimitSet();
			LDMaxNoRuleLimitPropSet tLDMaxNoRuleLimitPropSet = new LDMaxNoRuleLimitPropSet();

			for (int i = 0; i < jsonArray.size(); i++) {
				LDMaxNoRuleLimitSchema tempLDMaxNoRuleLimitSchema = new LDMaxNoRuleLimitSchema();
				JSONObject jobj = (JSONObject) jsonArray.get(i);
				String tKey = (String) jobj.get("key");
				String elementKey = (String) jobj.get("elementKey");
				JSONArray tArray = (JSONArray) jobj.get("prop");

				tempLDMaxNoRuleLimitSchema.setNoCode(tNoCode);
				tempLDMaxNoRuleLimitSchema.setCode(elementKey);
				tempLDMaxNoRuleLimitSchema.setIDX(i);
				tempLDMaxNoRuleLimitSchema.setRuleCode(tKey);

				tLDMaxNoRuleLimitSet.add(tempLDMaxNoRuleLimitSchema);

				for (int n = 0; n < tArray.size(); n++) {
					LDMaxNoRuleLimitPropSchema tempLDMaxNoRuleLimitPropSchema = new LDMaxNoRuleLimitPropSchema();
					JSONObject tjobj = (JSONObject) tArray.get(n);
					String propcode = (String) tjobj.get("propcode");
					String propname = (String) tjobj.get("propname");
					String propvalue = (String) tjobj.get("propvalue");
					String modifyflag = (String) tjobj.get("modifyflag");
					String showflag = (String) tjobj.get("showflag");

					tempLDMaxNoRuleLimitPropSchema.setRuleCode(tKey);
					tempLDMaxNoRuleLimitPropSchema.setPropCode(propcode);
					tempLDMaxNoRuleLimitPropSchema.setPropName(propname);
					tempLDMaxNoRuleLimitPropSchema.setPropValue(propvalue);
					tempLDMaxNoRuleLimitPropSchema.setModifyFlag(modifyflag);
					tempLDMaxNoRuleLimitPropSchema.setShowFlag(showflag);
					tempLDMaxNoRuleLimitPropSchema.setNoCode(tNoCode);

					tLDMaxNoRuleLimitPropSet.add(tempLDMaxNoRuleLimitPropSchema);
				}

			}

			

			String tDelete_LDMaxNoRuleProp = "delete from LDMaxNoRuleLimitProp where rulecode in "
					+ "("
					+ " select rulecode from LDMaxNoRuleLimit where nocode='"
					+ "?tNoCode?" + "' " + " ) and nocode='" + "?tNoCode?" + "'";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tDelete_LDMaxNoRuleProp);
			sqlbv11.put("tNoCode",tNoCode);
			map.put(sqlbv11, "DELETE");

			String tDelete_LDMaxNoRule = "delete from LDMaxNoRuleLimit where nocode='"
					+ "?tNoCode?" + "' ";
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tDelete_LDMaxNoRule);
			sqlbv12.put("tNoCode",tNoCode);
			map.put(sqlbv12, "DELETE");

			if (tLDMaxNoRuleLimitSet.size() > 0) {
				map.put(tLDMaxNoRuleLimitSet, "DELETE&INSERT");
			}
			if (tLDMaxNoRuleLimitPropSet.size() > 0) {
				map.put(tLDMaxNoRuleLimitPropSet, "DELETE&INSERT");
			}
		}
		mResult.clear();
		mResult.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError.buildErr(this,"数据提交失败");
			return false;
		}
		//保存成功后,刷新一下缓存
		CachedLDMaxNo mCached = CachedLDMaxNo.getInstance();
		mCached.refresh(tNoCode);
		
		return true;
	}


	public VData getResult() {
		return this.mResult;
	}
}
