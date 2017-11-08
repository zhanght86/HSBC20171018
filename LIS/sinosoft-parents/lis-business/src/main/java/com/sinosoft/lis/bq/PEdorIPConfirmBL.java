package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.acc.*;
import com.sinosoft.lis.f1print.BqNameFun;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全账户赎回确认处理类</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */
public class PEdorIPConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorIPConfirmBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;


    /** 往界面传输数据的容器 */
    private VData mResult = new VData();


    /** 数据操作字符串 */
    private String mOperate;
    String CurrentDate = PubFun.getCurrentDate();
    String CurrentTime = PubFun.getCurrentTime();

    /**  */
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new
            LCInsureAccTraceSet();
    private LCInsureAccFeeSet mLCInsureAccFeeSet = new LCInsureAccFeeSet();
    private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
    private LCInsureAccClassFeeSet mLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
//    private LJAGetEndorseSchema mLJAGetEndorseSchema = new LJAGetEndorseSchema();
//    private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();


    private LPPerInvestPlanSet mLPPerInvestPlanSet=new LPPerInvestPlanSet();
    private LCPerInvestPlanSet mLCPerInvestPlanSet=new LCPerInvestPlanSet();
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private double douAddMoney = 0;
    private MMap map = new MMap();
    boolean newaddrFlag = false;
    private boolean isTL = true;

    public PEdorIPConfirmBL() {}


    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
          return false;
        }

        if(isTL){
          //数据准备操作
          if (!dealData()) {
            return false;
          }
        }else{
          PEdorUPConfirmBL tPEdorUPConfirmBL = new PEdorUPConfirmBL();
          if(!tPEdorUPConfirmBL.submitData(mInputData,mOperate)){
            return false;
          }
          mResult = tPEdorUPConfirmBL.getResult();
          map.add((MMap) mResult.getObjectByObjectName("MMap", 0));
        }

        if (!prepareOutputData()) {
            return false;
        }
        return true;
    }

    public VData getResult() {
        return mResult;
    }

    private boolean prepareOutputData() {

        mResult.clear();
        mResult.add(map);

        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * @return
     */
    private boolean getInputData() {
        try {
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.
                                   getObjectByObjectName("LPEdorItemSchema",
                    0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);
        } catch (Exception e) {
            CError.buildErr(this, "接收数据失败");
            return false;
        }
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
        String flag = BqNameFun.getAnother(sqlbv,"LMRiskApp","riskcode in (select riskcode from lcpol where polno = mainpolno and contno = '?ContNo?') and risktype3","3","'X'");
        if(flag != null && "X".equals(flag)){
            isTL = true;
        }else{
            isTL = false;
        }
        return true;
    }


    /**
     * 准备需要保存的数据
     */
    private boolean dealData() {

        if (!dealInsuAccTraceData()) {
            return false;
        }

        if(!dealInvestPlan())
        {
        	return false;
        }
        return true;
    }

    //处理比例表
    private boolean dealInvestPlan()
    {

  	  String SQL="select * from  LPPerInvestPlan where EdorNo='?EdorNo?' order by PolNo ";
  	  SQLwithBindVariables sqlbv=new SQLwithBindVariables();
  	  sqlbv.sql(SQL);
  	  sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
  	  LPPerInvestPlanDB tLPPerInvestPlanDB=new LPPerInvestPlanDB();
  	  LPPerInvestPlanSet lzLPPerInvestPlanSet = new LPPerInvestPlanSet();
  	  lzLPPerInvestPlanSet = tLPPerInvestPlanDB.executeQuery(sqlbv);
  	  if(lzLPPerInvestPlanSet.size()>0)
  	  {
  		  for(int i = 1;i<=lzLPPerInvestPlanSet.size();i++){
  			  LCPerInvestPlanDB lzLCPerInvestPlanDB = new LCPerInvestPlanDB();
  			  lzLCPerInvestPlanDB.setPolNo(lzLPPerInvestPlanSet.get(i).getPolNo());
  			  lzLCPerInvestPlanDB.setPayPlanCode(lzLPPerInvestPlanSet.get(i).getPayPlanCode());
  			  lzLCPerInvestPlanDB.setInsuAccNo(lzLPPerInvestPlanSet.get(i).getInsuAccNo());
  			  LCPerInvestPlanSchema lzLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
  			  lzLCPerInvestPlanSchema.setSchema(lzLCPerInvestPlanDB.query().get(1));
  			  double tempbili = lzLCPerInvestPlanSchema.getInvestRate();
  			  double tempmoney = lzLCPerInvestPlanSchema.getInvestMoney();
  			  lzLCPerInvestPlanSchema.setInvestRate(lzLPPerInvestPlanSet.get(i).getInvestRate());
  			  lzLCPerInvestPlanSchema.setInvestMoney(tempmoney);
  			  lzLCPerInvestPlanSchema.setModifyDate(CurrentDate);
  			  lzLCPerInvestPlanSchema.setModifyTime(CurrentTime);
  			  mLCPerInvestPlanSet.add(lzLCPerInvestPlanSchema);
  			  LPPerInvestPlanSchema lzLPPerInvestPlanSchema = new LPPerInvestPlanSchema();
  			  lzLPPerInvestPlanSchema.setSchema(lzLPPerInvestPlanSet.get(i));
  			  lzLPPerInvestPlanSchema.setInvestRate(tempbili);
  			  lzLPPerInvestPlanSchema.setModifyDate(CurrentDate);
  			  lzLPPerInvestPlanSchema.setModifyTime(CurrentTime);
  			  mLPPerInvestPlanSet.add(lzLPPerInvestPlanSchema);
  		  }

  		  map.put(mLCPerInvestPlanSet, "DELETE&INSERT");
  		  map.put(mLPPerInvestPlanSet, "DELETE&INSERT");

  	  }
  		return true ;
    }



    private boolean dealInsuAccTraceData() {

        LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
        tLPInsureAccTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPInsureAccTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
        LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
        if (tLPInsureAccTraceSet.size() > 0) {
            LCInsureAccTraceSchema tLCInsureAccTraceSchema;
            for (int i = 1; i <= tLPInsureAccTraceSet.size(); i++) {
                tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
                Reflections ref = new Reflections();
                ref.transFields(tLCInsureAccTraceSchema,tLPInsureAccTraceSet.get(i));
                tLCInsureAccTraceSchema.setModifyDate(CurrentDate);
                tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
                //////判断首期是否已经进帐户
                LCPolDB tLCPolDB = new LCPolDB();
                String SQL = "select * from LCPol where contno = '?contno?' and riskcode in (select riskcode from lmriskapp where risktype3 = '3')";
                SQLwithBindVariables sqlbv=new SQLwithBindVariables();
                sqlbv.sql(SQL);
                sqlbv.put("contno", mLPEdorItemSchema.getContNo());
                LCPolSchema tLCPolSchema = new LCPolSchema();
                tLCPolSchema = tLCPolDB.executeQuery(sqlbv).get(1);
                LCInsureAccFeeTraceSchema lzLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
                lzLCInsureAccFeeTraceSchema = this.dealLCInsureAccFeeTraceData(tLCInsureAccTraceSchema);
                if(tLCPolSchema.getUintLinkValiFlag()==null||tLCPolSchema.getUintLinkValiFlag().equals("")){
                	
                } else {
                	if(tLCPolSchema.getUintLinkValiFlag().equals("4")){
                		String tSQL = "select * from lcinsureacctrace where contno = '?contno?' and moneytype = 'BF' and paydate >= '?paydate?'";
                		SQLwithBindVariables sbv=new SQLwithBindVariables();
                		sbv.sql(tSQL);
                		sbv.put("contno", mLPEdorItemSchema.getContNo());
                		sbv.put("paydate", mLPEdorItemSchema.getEdorValiDate());
                		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
                		LCInsureAccTraceSet lzLCInsureAccTraceSet = new LCInsureAccTraceSet();
                		lzLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sbv);
                		if(lzLCInsureAccTraceSet.size()!=0){
                			tLCInsureAccTraceSchema.setPayDate(lzLCInsureAccTraceSet.get(1).getPayDate());
                		}
                	}
                }

                ///判断并处理完毕
                mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
                //营改增 add zhangyingfeng 2016-07-14
                //价税分离 计算器
                TaxCalculator.calBySchema(lzLCInsureAccFeeTraceSchema);
                //end zhangyingfeng 2016-07-14
                mLCInsureAccFeeTraceSet.add(lzLCInsureAccFeeTraceSchema);

            }
        }

        map.put(mLCInsureAccTraceSet, "DELETE&INSERT");
        map.put(mLCInsureAccFeeTraceSet, "DELETE&INSERT");

        String upACCClassFeeSql = "Update LCInsureAccClassFee a set a.fee = (select (case when sum(fee) is not null then sum(fee) else 0 end) from lcinsureaccfeetrace where polno = a.polno and insuaccno = a.insuaccno and payplancode = a.payplancode and feecode = a.feecode) where a.ContNo = '?ContNo?'";
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql(upACCClassFeeSql);
        sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
        map.put(sbv1,"UPDATE");
        String upAccFeeSql = "Update LCInsureAccFee a set a.fee = (select (case when sum(fee) is not null then sum(fee) else 0 end) from lcinsureaccfeetrace where polno = a.polno and insuaccno = a.insuaccno ) where a.ContNo = '?ContNo?'";
        SQLwithBindVariables sbv2=new SQLwithBindVariables();
        sbv2.sql(upAccFeeSql);
        sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
        map.put(sbv2,"UPDATE");
        SQLwithBindVariables sbv3=new SQLwithBindVariables();
        sbv3.sql("delete from LPInsureAccFeeTrace where edorno = '?edorno?'");
        sbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
        map.put(sbv3,"DELETE");
        return true;
    }

    public TransferData getReturnTransferData() {
        return null;
    }

    public LCInsureAccFeeTraceSchema dealLCInsureAccFeeTraceData(LCInsureAccTraceSchema teLCInsureAccTraceSchema) {
    	LCInsureAccFeeTraceSchema ttLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
    	LPInsureAccFeeTraceDB tLPInsureAccFeeTraceDB = new LPInsureAccFeeTraceDB();
    	tLPInsureAccFeeTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
    	tLPInsureAccFeeTraceDB.setInsuAccNo(teLCInsureAccTraceSchema.getInsuAccNo());
    	tLPInsureAccFeeTraceDB.setPayPlanCode(teLCInsureAccTraceSchema.getPayPlanCode());
    	Reflections ref = new Reflections();
    	ref.transFields(ttLCInsureAccFeeTraceSchema, tLPInsureAccFeeTraceDB.query().get(1));

		ttLCInsureAccFeeTraceSchema.setState("1");
		ttLCInsureAccFeeTraceSchema.setMakeDate(CurrentDate);
		ttLCInsureAccFeeTraceSchema.setMakeTime(CurrentTime);
		ttLCInsureAccFeeTraceSchema.setModifyDate(CurrentDate);
		ttLCInsureAccFeeTraceSchema.setModifyTime(CurrentTime);

    	return ttLCInsureAccFeeTraceSchema;
    }

    public CErrors getErrors() {
        return mErrors;
    }

  public static void main(String[] args)
  {

      try
      {
          LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
          tLPEdorItemSchema.setEdorNo("6020071026000011");
          LPEdorItemDB tt = tLPEdorItemSchema.getDB();
          tt.query().get(1); //不tt.query()可能得到null或则size为0的*Set,此风格代码只能用于调试
          tLPEdorItemSchema.setSchema(tt.query().get(1).getSchema());
          VData tVData = new VData();
          tVData.add(tLPEdorItemSchema);
          PEdorIPConfirmBL tBl = new PEdorIPConfirmBL();
          tBl.submitData(tVData, "");


//          VData rVDta = tBl.getResult();
//          XmlExport xmlexport = (XmlExport) rVDta.getObjectByObjectName(
//                  "XmlExport", 0);
//          /**暂时用于调试把信息暂存在文件中，应该放在表lpedorprint的edorinfo字段，日后该之*/
//          xmlexport.outputDocumentToFile("E:\\work", "myPG.xml", false);

      }
      catch (Exception ex)
      {
          ex.printStackTrace();
      }

  }


}
