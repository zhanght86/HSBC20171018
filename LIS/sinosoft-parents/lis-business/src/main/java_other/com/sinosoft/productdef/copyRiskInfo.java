

package com.sinosoft.productdef;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

public class copyRiskInfo {

	public copyRiskInfo() {
		// TODO Auto-generated constructor stub
	}

	private Reflections  mReflections = new Reflections();
	private SSRS getRiskTables()
	{
		SSRS tSSRS = new SSRS();
		String tSQL_RiskTables = "select TableName,TableOrder,SQL from pd_riskinfo where 1=1 "
			                   + " and state='0' "
							   + " order by TableOrder ";
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSQL_RiskTables);
		return tSSRS;
	}
	
	public boolean dealData(String tRiskCode)
	{
		boolean resultFlag = true;
		ExeSQL tempExeSQL  = new ExeSQL();
		MMap tMMap = new MMap();
		SSRS tSSRS = this.getRiskTables();
		for(int i=1;i<=tSSRS.getMaxRow();i++)
		{
			String tTableName = tSSRS.GetText(i, 1);
			String tTableOrder = tSSRS.GetText(i, 2);
			String tSQL = tSSRS.GetText(i, 3);
			tSQL = StrTool.replace(tSQL,"@RISKCODE@", tRiskCode);
			System.out.println("tTableName:"+tTableName+"---------------------------------------");
			System.out.println("tSQL:"+tSQL);
			
			String tSQL_DutyCode = "select dutycode from lmriskduty where riskcode='"+tRiskCode+"'";
			String tDutyCode = tempExeSQL.getOneValue(tSQL_DutyCode);
			 try {
				Class tClass  = Class.forName("com.sinosoft.lis.vschema."+ "PD_"+tTableName + "Set");
				 SchemaSet tDesSet = (SchemaSet) tClass.newInstance();
				 
				 Class tClass2 = Class.forName("com.sinosoft.lis.schema."+ tTableName + "Schema");
				 Schema tResSchems = (Schema) tClass2.newInstance();
				 
				 Class tClass1 = Class.forName("com.sinosoft.lis.schema."+ "PD_"+tTableName + "Schema");
				 Schema tDesSchems = (Schema) tClass1.newInstance();
				 
				 ExeSQL tExeSQL = new ExeSQL();
				 
				 SSRS tResSSRS = new SSRS();
				 tResSSRS = tExeSQL.execSQL(tSQL);
				 for(int m=1;m<=tResSSRS.getMaxRow();m++)
				 {
					 tResSchems = (Schema) tClass2.newInstance();
					 tDesSchems = (Schema) tClass1.newInstance();
					 //行 
					 String tValue = "";
					 String tFieldName = "";
					 for(int n=1;n<=tResSSRS.getMaxCol();n++)
					 {
						 try {
							 tValue = tResSSRS.GetText(m,n);
							 tFieldName = tResSchems.getFieldName(n-1);
							 if(tValue==null||tValue.equals("null"))
							 {
								 tValue = "";
							 }
							 
							 System.out.println("tFieldName, tValue:"+tFieldName+":"+tValue);
							 tResSchems.setV(tFieldName, tValue);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							 System.out.println("tTableName,tFieldName, tValue:"+tTableName+":"+tFieldName+":"+tValue);
							break;
						}
					 }
					 this.mReflections.transFields(tDesSchems, tResSchems);
					 try {
						 tDesSchems.setV("Operator", "Auto");
						 tDesSchems.setV("MakeDate", PubFun.getCurrentDate());
						 tDesSchems.setV("MakeTime", PubFun.getCurrentTime());
						 tDesSchems.setV("ModifyDate", PubFun.getCurrentDate());
						 tDesSchems.setV("ModifyTime", PubFun.getCurrentTime());
						 if(tTableName.toUpperCase().equals("LMDUTYPAY")
								 ||tTableName.toUpperCase().equals("LMDUTYGET"))
						 {
							 tDesSchems.setV("DutyCode", tDutyCode);
						 }
						 
						 if(tTableName.toUpperCase().equals("LMRISKFEE")
								 ||tTableName.toUpperCase().equals("LMRISKFEE"))
						 {
							 tDesSchems.setV("FeeStartDate", PubFun.getCurrentDate());
						 }
						 
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						 System.out.println("$$$$$$$$$$$$$$$$"+e.toString());
					}
					for(int t=1;t<=tDesSchems.getFieldCount();t++)
					{
						System.out.println(tTableName+":"+tDesSchems.getFieldName(t-1)+":"+tDesSchems.getV(t-1));
					}
					
					if(tTableName.equals("LDCode1"))
					{
						System.out.println("222");
					}
					tDesSet.add(tDesSchems);
					// tResSet.add(tResSchems);
				 }
				 
				 tMMap.put(tDesSet, "DELETE&INSERT");
				
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			System.out.println("end tTableName:"+tTableName+"---------------------------------------");    
		}
		
		
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
		tLWMissionSchema.setMissionID(tMissionID);
		tLWMissionSchema.setMainMissionID(tMissionID);
		tLWMissionSchema.setSubMissionID("1");
		tLWMissionSchema.setProcessID("pd00000011");
		tLWMissionSchema.setActivityID("pd00000004");
		tLWMissionSchema.setActivityStatus("1"); // 0 --
		tLWMissionSchema.setDefaultOperator("Auto");
		tLWMissionSchema.setCreateOperator("Auto");
		tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
		tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
		tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
		tLWMissionSchema.setInDate(PubFun.getCurrentDate());
		tLWMissionSchema.setInTime(PubFun.getCurrentTime());
		

		tLWMissionSchema.setMissionProp2(tRiskCode);
		tLWMissionSchema.setMissionProp1(PubFun.getCurrentDate());
		tLWMissionSchema.setMissionProp3("Auto");
		tLWMissionSchema.setMissionProp4(PubFun.getCurrentDate());
		tLWMissionSchema.setMissionProp24("86");
		tLWMissionSchema.setMissionProp25(tMissionID);
		tMMap.put(tLWMissionSchema, "DELETE&INSERT");
		
		VData mInputData = new VData();
		mInputData.add(tMMap);
		// 数据提交、保存
		PubSubmit tPubSubmit = new PubSubmit();
		System.out.println("Start  Submit...");
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
		
			return false;
		}
		
			
		return resultFlag;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		copyRiskInfo tcopyRiskInfo = new copyRiskInfo();
/*		ExeSQL tExeSQL = new ExeSQL();
		String tSQL = "select riskcode from lmrisk where riskcode not in (select riskcode from pd_lmrisk) ";
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(tSQL);
		for(int i=1;i<=tSSRS.getMaxRow();i++)
		{
			String tRiskCode = tSSRS.GetText(i, 1);
			tcopyRiskInfo.dealData(tRiskCode);
		}
		*/
		tcopyRiskInfo.dealData("IBC00");
	}

}
