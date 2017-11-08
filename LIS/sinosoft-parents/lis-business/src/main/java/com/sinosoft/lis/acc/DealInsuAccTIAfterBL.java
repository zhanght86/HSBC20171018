package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.acc.*;

/**
 * <p>Title: </p>
 *
 * <p>Description:投连后续处理账户转换PG实现 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 */
public class DealInsuAccTIAfterBL extends DealInsuAccAfter {
private static Logger logger = Logger.getLogger(DealInsuAccTIAfterBL.class);
    public DealInsuAccTIAfterBL() {
    }

    private VData mResult = new VData();
    /** 传出数据的容器 */
    public LOPolAfterDealSet _LOPolAfterDealSet = new LOPolAfterDealSet();
    DecimalFormat df = new DecimalFormat("0.00"); 
    private LOPolAfterDealSchema _LOPolAfterDealSchema = new
            LOPolAfterDealSchema();
    private LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();
    private LCInsureAccClassSet _LCInsureAccClassSet = new LCInsureAccClassSet();
    private LCInsureAccTraceSet _LCInsureAccTraceSet = new LCInsureAccTraceSet();
    private LPInsuAccOutSet _LPInsuAccOutSet=new LPInsuAccOutSet();
    private LPInsuAccInSet _LPInsuAccInSet=new LPInsuAccInSet();
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    
    
    MMap mmap = new MMap();
    
    String CurrentDate = PubFun.getCurrentDate();
    String CurrentTime = PubFun.getCurrentTime();

    /*根据LOPolAfterDeal表中的账户信息进行处理*/
    public boolean dealAfter(GlobalInput tGlobalInput,
                             LOPolAfterDealSchema tLOPolAfterDealSchema) {
    	
    	
    	LPEdorItemSet tLPEdorItemSet=new LPEdorItemSet();
        LPEdorItemDB tLPEdorItemDB=new LPEdorItemDB();
        LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema ();
        tLPEdorItemDB.setEdorNo(tLOPolAfterDealSchema.getAccAlterNo());
        tLPEdorItemDB.setEdorType(tLOPolAfterDealSchema.getBusyType());
        tLPEdorItemSet=tLPEdorItemDB.query();
        if (tLPEdorItemSet.size() == 0) {
            CError.buildErr(this,
                            "批改项目信息查询失败!");
            return false;
        }
        mLPEdorItemSchema=tLPEdorItemSet.get(1);
        
    	
        /*账户转换PG后续处理逻辑*/
        String _DealDate = tLOPolAfterDealSchema.getDealDate();
        LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
        LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
        tLCInsureAccTraceDB.setAccAlterNo(tLOPolAfterDealSchema.getAccAlterNo());
        tLCInsureAccTraceDB.setAccAlterType(tLOPolAfterDealSchema.
                                            getAccAlterType());
        tLCInsureAccTraceDB.setBusyType(tLOPolAfterDealSchema.getBusyType());
        tLCInsureAccTraceDB.setState("0");
        tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
        if (tLCInsureAccTraceSet.size() > 0) {
            CError.buildErr(this, "存在待计价的记录!");
            return false;
        }
        tLCInsureAccTraceDB.setState("1");
        tLCInsureAccTraceSet.clear();
        tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();

        LPInsuAccOutSet tLPInsuAccOutSet = new LPInsuAccOutSet();
        LPInsuAccOutDB tLPInsuAccOutDB = new LPInsuAccOutDB();
        tLPInsuAccOutDB.setEdorNo(tLOPolAfterDealSchema.getAccAlterNo());
        tLPInsuAccOutDB.setEdorType(tLOPolAfterDealSchema.getBusyType());
        tLPInsuAccOutSet = tLPInsuAccOutDB.query();
        if (tLPInsuAccOutSet.size() != tLCInsureAccTraceSet.size()) {
            CError.buildErr(this,
                            "保全账户转出信息LPInsuAccOut与轨迹表LCInsureAccTrace存在不一致!");
            return false;
        }
//zhelikaishide  
        double OutMainMoney = 0.00;
        LCInsureAccTraceSet cLCInsureAccTraceSet = new LCInsureAccTraceSet();
        
        String strOperator = "";
        
        
        /*得到卖出的*/
        for (int i = 1; i <= tLPInsuAccOutSet.size(); i++) {
            LPInsuAccOutSchema tLPInsuAccOutSchema = new LPInsuAccOutSchema();
            tLPInsuAccOutSchema = tLPInsuAccOutSet.get(i);
            tLPInsuAccOutSchema.setState("0");
            _LPInsuAccOutSet.add(tLPInsuAccOutSchema);
            LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
            LCInsureAccTraceDB mLCInsureAccTraceDB = new LCInsureAccTraceDB();
            mLCInsureAccTraceDB.setSerialNo(tLPInsuAccOutSchema.getSerialNo());
            mLCInsureAccTraceSet = mLCInsureAccTraceDB.query();
            if (mLCInsureAccTraceSet.size() == 0) {
                CError.buildErr(this,
                                "未找到账户转出交易轨迹!");
                return false;
            }
            strOperator = mLCInsureAccTraceSet.get(1).getOperator();
            OutMainMoney = OutMainMoney+mLCInsureAccTraceSet.get(1).getMoney();
        }
        
        
        //算和减手续费
        
//      查询险种保单详细信息
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(tLCInsureAccTraceSet.get(1).getPolNo());
        LCPolSet tLCPolSet = tLCPolDB.query();
        if (tLCPolDB.mErrors.needDealError()) {
            CError.buildErr(this, "险种信息查询失败!");
            return false;
        }
        if (tLCPolSet == null || tLCPolSet.size() != 1) {
            CError.buildErr(this, "没有查到险种信息!");
            return false;
        }

        TLbqForFee lzTLbqForFee = new TLbqForFee();
        double lzCalFee = 0;
        try {
        	lzCalFee = lzTLbqForFee.GetCalFee( 0,mLPEdorItemSchema.getEdorNo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("手续费是:::"+lzCalFee); 
		lzCalFee = Double.parseDouble(df.format(lzCalFee));
		
		//手续费计算完毕
		//判断和扣减手续费
		if(lzCalFee>0)
		{
			OutMainMoney = OutMainMoney+lzCalFee;
			LCInsureAccTraceSchema lzLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			String tLimit = PubFun.getNoLimit(tLCInsureAccTraceSet.get(1).getManageCom());
			String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			lzLCInsureAccTraceSchema.setSchema(tLCInsureAccTraceSet.get(1));
			lzLCInsureAccTraceSchema.setSerialNo(SerialNo);
			lzLCInsureAccTraceSchema.setMoneyType("WF");
			lzLCInsureAccTraceSchema.setMoney(-lzCalFee);
			lzLCInsureAccTraceSchema.setUnitCount(null);
			lzLCInsureAccTraceSchema.setState("1");
			lzLCInsureAccTraceSchema.setBusyType("TI");
			lzLCInsureAccTraceSchema.setFeeCode("000000");
			//_LCInsureAccTraceSet.add(lzLCInsureAccTraceSchema);
			
			
			//////////////////////////这里要将手续费写入feetrace表\\\\\\\\\\\\\\\
			
			LCInsureAccFeeTraceSchema ttLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();        
	    	Reflections ref = new Reflections();
	    	ref.transFields(ttLCInsureAccFeeTraceSchema, lzLCInsureAccTraceSchema);
	    	
	    	LMRiskFeeDB lzLMRiskFeeDB = new LMRiskFeeDB();
			LMRiskFeeSet lzLMRiskFeeSet = new LMRiskFeeSet();
	    	lzLMRiskFeeDB.setInsuAccNo(lzLCInsureAccTraceSchema.getInsuAccNo());
			lzLMRiskFeeDB.setPayPlanCode(lzLCInsureAccTraceSchema.getPayPlanCode());
			lzLMRiskFeeDB.setFeeTakePlace("01");
			lzLMRiskFeeSet = lzLMRiskFeeDB.query();
			String Feecode = lzLMRiskFeeSet.get(1).getFeeCode();
			ttLCInsureAccFeeTraceSchema.setFeeCode(Feecode);	
			ttLCInsureAccFeeTraceSchema.setPayPlanCode("000000");
			ttLCInsureAccFeeTraceSchema.setInsuAccNo("000000");
			ttLCInsureAccFeeTraceSchema.setFee(lzLCInsureAccTraceSchema.getMoney());
			ttLCInsureAccFeeTraceSchema.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
			ttLCInsureAccFeeTraceSchema.setOtherType("2");
			logger.debug("ttLCInsureAccFeeTraceSchema.encode()*-*..$_$"+ttLCInsureAccFeeTraceSchema.encode());
			mmap.put(ttLCInsureAccFeeTraceSchema, "DELETE&INSERT");
			
			//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\完毕///////////////////////////
			
		}
		
		//扣减完毕
        
        //循环IN记录
        LPInsuAccInSet tLPInsuAccInSet = new LPInsuAccInSet();
        LPInsuAccInDB tLPInsuAccInDB = new LPInsuAccInDB();
        tLPInsuAccInDB.setEdorNo(tLOPolAfterDealSchema.getAccAlterNo());
        tLPInsuAccInDB.setEdorType(tLOPolAfterDealSchema.getBusyType());
        tLPInsuAccInSet = tLPInsuAccInDB.query();
        double inmoney = 0;
        double maininmoney = 0;
        for (int j = 1; j <= tLPInsuAccInSet.size(); j++) {
        	
        	LPInsuAccInSchema tLPInsuAccInSchema = new LPInsuAccInSchema();
            tLPInsuAccInSchema = tLPInsuAccInSet.get(j);
            tLPInsuAccInSchema.setState("0");
             _LPInsuAccInSet.add(tLPInsuAccInSchema);
            LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
                    LCInsureAccTraceSchema();

            LCInsureAccClassSchema tLCInsureAccClassSchema = new
                    LCInsureAccClassSchema();
            LCInsureAccClassSet tLCInsureAccClassSet = new
                    LCInsureAccClassSet();
            LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
            tLCInsureAccClassDB.setPolNo(tLPInsuAccInSchema.getInPolNo());
            tLCInsureAccClassDB.setInsuAccNo(tLPInsuAccInSchema.
                                             getInInsuAccNo());
            tLCInsureAccClassDB.setPayPlanCode(tLPInsuAccInSchema.
                    getInPayPlanCode());
            tLCInsureAccClassSet = tLCInsureAccClassDB.query();
            if (tLCInsureAccClassSet.size() == 0) {
                CError.buildErr(this,
                                "未找到转入账户信息!");
                return false;
            }
            tLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);

            LOAccUnitPriceDB tLOAccUnitPriceDB = new LOAccUnitPriceDB();
//            tLOAccUnitPriceDB.setRiskCode(tLCInsureAccClassSchema.getRiskCode());
            tLOAccUnitPriceDB.setInsuAccNo(tLCInsureAccClassSchema.
                                           getInsuAccNo());
            tLOAccUnitPriceDB.setStartDate(PubInsuAccFun.getNextStartDate(
            		mLPEdorItemSchema.getEdorValiDate(),
                    tLCInsureAccClassSchema.getInsuAccNo())
                    );
            if (!tLOAccUnitPriceDB.getInfo()) {
                CError.buildErr(this,
                                "未找到转入账户计价信息!");
                return false;
            }
        	
            //生成LCInsureAccTrace表信息
            Reflections tReflections = new Reflections();

            tReflections.transFields(tLCInsureAccTraceSchema,
                                     tLCInsureAccClassSchema);
            String tLimit = PubFun.getNoLimit(tLCInsureAccTraceSchema.
                                              getManageCom());
            String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
            tLCInsureAccTraceSchema.setSerialNo(SerialNo);


            tLCInsureAccTraceSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
            tLCInsureAccTraceSchema.setShouldValueDate(PubInsuAccFun.
                    getNextStartDate(
                    		mLPEdorItemSchema.getEdorValiDate(),
                            tLCInsureAccClassSchema.getInsuAccNo()));
            tLCInsureAccTraceSchema.setValueDate(tLOPolAfterDealSchema.getDealDate());
            tLCInsureAccTraceSchema.setState("1");
            inmoney = Double.parseDouble(df.format(-OutMainMoney*tLPInsuAccInSchema.getAccInRate()));
            if(j == tLPInsuAccInSet.size())
            {
            	inmoney = Double.parseDouble(df.format(-maininmoney - OutMainMoney));
            }
            else
            {
            	maininmoney = maininmoney + inmoney;
            }
            tLCInsureAccTraceSchema.setMoney(inmoney);
            tLCInsureAccTraceSchema.setMoneyType("TI");
            tLCInsureAccTraceSchema.setUnitCount(PubInsuAccFun.calUnit(tLCInsureAccTraceSchema.getMoney(),tLCInsureAccTraceSchema));
            
            if(tLCInsureAccTraceSchema.getUnitCount()==0&&tLCInsureAccTraceSchema.getMoney()!=0)
            {
            	CError.buildErr(this, "保全TI买入时出现错误!");
                return false;
            }
            
            tLCInsureAccTraceSchema.setMoneyType("IN");
            tLCInsureAccTraceSchema.setFeeCode("000000");
            tLCInsureAccTraceSchema.setBusyType("TI");
            tLCInsureAccTraceSchema.setAccAlterNo(tLOPolAfterDealSchema.
                    getAccAlterNo());
            tLCInsureAccTraceSchema.setAccAlterType(tLOPolAfterDealSchema.
                    getAccAlterType());
            tLCInsureAccTraceSchema.setOperator(strOperator);
            tLCInsureAccTraceSchema.setMakeDate(PubFun.getCurrentDate());
            tLCInsureAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
            tLCInsureAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
            tLCInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
            
            logger.debug("tLCInsureAccTraceSchema:'(...>_<很郁闷"+tLCInsureAccTraceSchema.getPayDate());
            
            cLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);     	
        }
        
        _LCInsureAccTraceSet.add(cLCInsureAccTraceSet);
        _LCInsureAccClassSet.add(PubInsuAccFun.createAccClassByTrace(
                cLCInsureAccTraceSet));
        _LCInsureAccSet.add(PubInsuAccFun.createAccByTrace(
                cLCInsureAccTraceSet));

        _LOPolAfterDealSchema = tLOPolAfterDealSchema.getSchema();
        //_LOPolAfterDealSchema.setDealDate(PubFun.getCurrentDate());
        _LOPolAfterDealSchema.setState("2");
        if (!updateAccInfo()) {
            CError.buildErr(this, "账户信息更新出错!");
            return false;
        }
        //这里要加入收取风险保费的逻辑
        //1.调用PubInsuAccFun的queryPolManageSet来判断是否要进入收取风险保费的类
        LCContDB manageLCContDB = new LCContDB();
        manageLCContDB.setContNo(_LOPolAfterDealSchema.getContNo());
        LCPolSet manageLCPolSet = new LCPolSet();
        LCContSet manageLCContSet = new LCContSet();
        manageLCContSet = manageLCContDB.query();
        if(manageLCContSet!=null&&manageLCContSet.size()>0)
        {
        	manageLCPolSet = PubInsuAccFun.queryPolManageSet(manageLCContSet);
        	if(manageLCPolSet!=null&&manageLCPolSet.size()>0)
        	{
        		//2.进入收取风险保费类
        		DealPolManageFee _DealPolManageFee = new
        		DealPolManageFee(tGlobalInput, _LOPolAfterDealSchema.getDealDate());
        		_DealPolManageFee.calPolManageFee(manageLCPolSet);
        	}
        }
        else
        {
        	logger.debug("保单号为"+_LOPolAfterDealSchema.getContNo()+"的TI后续处理中查询LCCont失败!");
        }
        //风险保费处理完毕
        return true;
    }
    
    
    
    
    /*更新账户信息*/
    public boolean updateAccInfo() {
        VData tVData = new VData();
        
        //准备公共提交数据
        mmap.put(_LCInsureAccTraceSet, "DELETE&INSERT");
        mmap.put(_LCInsureAccClassSet, "UPDATE");
        mmap.put(_LCInsureAccSet, "UPDATE");
        mmap.put(_LOPolAfterDealSchema, "UPDATE");
        mmap.put(_LPInsuAccOutSet, "UPDATE");
        mmap.put(_LPInsuAccInSet, "UPDATE");

        if (mmap != null && mmap.keySet().size() > 0)
            tVData.add(mmap);
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, "")) {
            return false;
        }
        return true;
    }

    public VData getResult() {
        return mResult;
    }

    public CErrors getErrors() {
        return null;
    }
}
