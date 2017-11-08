package com.sinosoft.lis.xb;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;

public class RnewCommonDataPut {
public CErrors mErrors = new CErrors();
	
	private TransferData mTransferData = new TransferData();
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;

	private String mPrtNo;// 流水号保留，在这里基本还没用到
	private String mContNo;
	 
	public  RnewCommonDataPut(){
		
	}
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData( TransferData  tTransferData) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
//		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		mTransferData=tTransferData;
		// 获得业务数据
		if (mTransferData == null) {
			CError.buildErr(this, "前台传输业务数据失败!");
			return false;
		}
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!");
			return false;
		}
		mActivityID = (String) mTransferData.getValueByName("ActivityID");
		if (mActivityID == null) {
			CError.buildErr(this, "前台传输业务数据中ActivityIDD失败!");
			return false;
		}
		return true;
	}

	public boolean  submitData(TransferData tTransferData){
		
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(tTransferData))
			return false;
		// 进行业务处理		
		if (!dealData())
			return false;

		
		return true;
	}
	private boolean dealData()
	{
		LWMissionDB tLWMissionDB=new LWMissionDB();
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		tLWMissionDB.setActivityID(mActivityID);
		
		if(!tLWMissionDB.getInfo())
		{
			CError.buildErr(this, "查询工作流失败!");
			return false;
		}
        LWMissionSchema tLWMissionSchema=new LWMissionSchema();
        tLWMissionSchema=tLWMissionDB.getSchema();
        
        if("0000007013".equals(mActivityID))
        {
        	  mPrtNo=tLWMissionSchema.getMissionProp1();
        	  mContNo=tLWMissionSchema.getMissionProp2();
        }
        if("0000007012".equals(mActivityID))
        {
        	  mPrtNo=tLWMissionSchema.getMissionProp1();
        	  mContNo=tLWMissionSchema.getMissionProp2();
        }
        if("0000007015".equals(mActivityID))
        {
        	  mPrtNo=tLWMissionSchema.getMissionProp1();
        	  mContNo=tLWMissionSchema.getMissionProp2();
        }
        if("0000007006".equals(mActivityID))
        {
        	  mPrtNo=tLWMissionSchema.getMissionProp1();
        	  mContNo=tLWMissionSchema.getMissionProp2();
        }
        if(mPrtNo==null||mPrtNo=="")
        {
        	CError.buildErr(this, "查询mPrtNo失败!");
			return false;
        }
        if(mContNo==null||mContNo=="")
        {
        	CError.buildErr(this, "查询mContNo失败!");
			return false;
        }
//        PrtNo	印刷号MissionProp1
//        ContNo	合同号MissionProp2
//        PolNo	投保单号 MissionProp3
//        RiskCode	险种MissionProp4
//        RiskName	险种名称 MissionProp5
//        ManageCom	管理机构MissionProp6
//        AppntNo	投保人编码MissionProp7
//        AppntName	投保人名称MissionProp8
//        InsuredNo	被保人编码MissionProp9
//        InsuredName	被保人名称MissionProp10
//        ApplyDate	自核提交日期MissionProp11
//        Uw_State	核保状态  MissionProp12
//        Content	核保员说明MissionProp13
//        BackDate	最后回复日期MissionProp14
//        BackTime	最后回复时间MissionProp15
        ExeSQL tExeSQL = new ExeSQL();
        String tSql0 = "select c.Appntno,c.Appntname,c.Insuredno,c.Insuredname,c.Managecom from Lccont c where c.Contno = '?mContNo?'";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSql0);
        sqlbv.put("mContNo", mContNo);
        SSRS tSSRS0=tExeSQL.execSQL(sqlbv);
        if(tSSRS0.MaxRow>0){
        	mTransferData.setNameAndValue("Appntno", tSSRS0.GetText(1, 1));
        	mTransferData.setNameAndValue("Appntname", tSSRS0.GetText(1, 2));
        	mTransferData.setNameAndValue("InsuredNo", tSSRS0.GetText(1, 3));
        	mTransferData.setNameAndValue("InsuredName", tSSRS0.GetText(1, 4));
        	mTransferData.setNameAndValue("ManageCom", tSSRS0.GetText(1, 5));
        }else
		{
			CError.buildErr(this, "查询Lccont表数据失败!");
			return false;
		}
		String tSql1 = "select distinct c.Riskcode,m.Riskname from Lcpol c,lmrisk m where c.Contno = '?mContNo?' and m.Riskcode = c.Riskcode";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql1);
		sqlbv1.put("mContNo", mContNo);
		SSRS tSSRS1=tExeSQL.execSQL(sqlbv1);
		if(tSSRS1.MaxRow>0)
		{
			mTransferData.setNameAndValue("RiskCode", tSSRS1.GetText(1, 1));
			mTransferData.setNameAndValue("RiskName",tSSRS1.GetText(1, 2) );
		}
		else
		{
			CError.buildErr(this, "查询Lcpol表数据失败!");
			return false;
		}
		String tSql2 = "select a.Polno,a.Makedate from lcpol a where a.Contno = '?mContNo?' and a.Polno = a.Proposalno";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql2);
		sqlbv2.put("mContNo", mContNo);
		SSRS tSSRS2=tExeSQL.execSQL(sqlbv2);
		if(tSSRS2.MaxRow>0)
		{
			mTransferData.setNameAndValue("PolNo", tSSRS2.GetText(1, 1));
			mTransferData.setNameAndValue("ApplyDate",tSSRS2.GetText(1, 2) );
		}
		else
		{
			CError.buildErr(this, "查询Lcpol表数据失败!");
			return false;
		}
		//核保状态先这么写死，随后再做修改
		mTransferData.setNameAndValue("Uw_State","1" );
		return true;
	} 
	public TransferData getTransferData()
	{
		return this.mTransferData;
	}
	public CErrors getErrors()
	{
		return this.mErrors;
	}
}
