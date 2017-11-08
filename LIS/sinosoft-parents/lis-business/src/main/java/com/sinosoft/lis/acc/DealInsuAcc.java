package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: </p>
 *
 * <p>Description:投连批处理入口 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 * @Cleaned QianLy 2009-07-16
 */
public class DealInsuAcc implements BusinessService
{
private static Logger logger = Logger.getLogger(DealInsuAcc.class);


    public DealInsuAcc()
    {
    }

    public DealInsuAcc(GlobalInput tGlobalInput)
    {
        _GlobalInput = tGlobalInput;
    }

    private GlobalInput _GlobalInput = new GlobalInput();
    private LCPolSet _LCPolSet = new LCPolSet();
    private LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();
    private LOPolAfterDealSet _LOPolAfterDealSet = new LOPolAfterDealSet();
    private DealInsuAccLog tDealInsuAccLog = new DealInsuAccLog();
    public CErrors mErrors = new CErrors();
    private TransferData _TransferData = new TransferData();
    /*对保单账户进行计价批处理*/
    public boolean dealInsuAcc()
    {
        if (!dealInsuAcc(PubFun.getCurrentDate()))
            return false;
        else
            return true;
    }

    public boolean dealInsuAcc(String _DealDate)
    {

        //////////////////////////////加入判断是否有帐户单位价值\\\\\\\\\\\\\\\\\\\\\\\\\\\
        LOAccUnitPriceDB tLOAccUnitPriceDB = new LOAccUnitPriceDB();
        tLOAccUnitPriceDB.setStartDate(_DealDate);
        tLOAccUnitPriceDB.setState("0");
        LOAccUnitPriceSet tLOAccUnitPriceSet = new LOAccUnitPriceSet();
        tLOAccUnitPriceSet = tLOAccUnitPriceDB.query();

        if (tLOAccUnitPriceSet.size() <= 0)
        {
//    		 @@错误处理
            CError tError = new CError();
            tError.moduleName = "dealInsuAcc";
            tError.functionName = "dealInsuAcc";
            tError.errorMessage = _DealDate + "没有帐户单位价格信息！";
            this.mErrors.addOneError(tError);
            return false;
        }

        //判断已经处理的trace,限制多次批
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        String tSQL = null;
        if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
        	tSQL="select 'X' from lcinsureacctrace where state = '1' and valuedate = '" +"?_DealDate?" + "' and rownum = 1";
        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
        	tSQL="select 'X' from lcinsureacctrace where state = '1' and valuedate = '" +"?_DealDate?" + "' limit 0,1";
        }
        sqlbv.sql(tSQL);
        sqlbv.put("_DealDate", _DealDate);
        ExeSQL exs = new ExeSQL();
        String have = exs.getOneValue(sqlbv);
        if (have !=null && "X".equals(have))
        {
//   		 @@错误处理
            CError tError = new CError();
            tError.moduleName = "dealInsuAcc";
            tError.functionName = "dealInsuAcc";
            tError.errorMessage = _DealDate + "已经做过批处理！";
            this.mErrors.addOneError(tError);
           // return false;
        }

        /*1.账户计价*/
        //1.1查询待计价的账户
        _LCInsureAccSet = PubInsuAccFun.queryCalSet();
        //1.2对待计价的账户进行计价处理
        DealInsuAccPrice _DealInsuAccPrice = new DealInsuAccPrice(_GlobalInput, _DealDate);
        if (_LCInsureAccSet.size() == 0)
        { //没有需要计价的记录；
            tDealInsuAccLog.createLOAccLog(_DealDate, _GlobalInput.ManageCom,"01", _DealDate + "这天没有要计价的记录");
        }
        else
        {
            _DealInsuAccPrice.calPrice(_LCInsureAccSet);
        }
        /*2.管理费收取*/
        //2.1查询待进行管理费处理的保单
        PubInsuAccFun.isTN = "N";//管理费收取时非T+N计价
        _LCPolSet = PubInsuAccFun.queryPolManageSet();

        //2.2对待进行管理费处理的账户进行逻辑处理
        DealPolManageFee _DealPolManageFee = new DealPolManageFee(_GlobalInput,_DealDate);
        _DealPolManageFee.calPolManageFee(_LCPolSet);

        /*3.后续处理*/
        //3.1查询需后续处理的集合
        PubInsuAccFun.isTN = "Y";//后续业务逻辑处理采用T+N计价处理方式
        _LOPolAfterDealSet = PubInsuAccFun.queryAfterSet();
        //3.2根据处理类型进行后续处理
        for (int i = 1; i <= _LOPolAfterDealSet.size(); i++)
        {
            LOPolAfterDealSchema _LOPolAfterDealSchema = new LOPolAfterDealSchema();
            _LOPolAfterDealSchema = _LOPolAfterDealSet.get(i);
            _LOPolAfterDealSchema.setDealDate(_DealDate);
            try
            {
                Class _Class = Class.forName("com.sinosoft.lis.acc.DealInsuAcc"+ _LOPolAfterDealSchema.getBusyType() + "AfterBL");
                DealInsuAccAfter _DealInsuAccAfter = (DealInsuAccAfter)_Class.newInstance();
                _DealInsuAccAfter.dealAfter(_GlobalInput, _LOPolAfterDealSchema);
            }
            catch (ClassNotFoundException ex)
            {
                continue;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                tDealInsuAccLog.createLOAccLog(_DealDate,_GlobalInput.ManageCom, "03",_LOPolAfterDealSchema.getBusyType() + "后续处理出现问题");
                continue;
            }
        }
        return true;
    }
    public boolean dealInsuAcc(String aDealDate,String aNextPriceDate)
    {
        if (!dealInsuAcc(aDealDate))
        {
        	return false;
        }
        if (aNextPriceDate==null || "".equals(aNextPriceDate))
        {
			// @@错误处理
            CError tError = new CError();
            tError.moduleName = "dealInsuAcc";
            tError.functionName = "dealInsuAcc";
            tError.errorMessage = aDealDate + "下个计价日为空";
            this.mErrors.addOneError(tError);
            return false;
        }
        
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql("update loaccunitprice set nextpricedate =to_date('"+"?aNextPriceDate?"+"','yyyy-mm-dd') where state ='0' and StartDate=to_date('"+"?aDealDate?"+"','yyyy-mm-dd')");
        sqlbv.put("aNextPriceDate", aNextPriceDate);
        sqlbv.put("aDealDate", aDealDate);
        MMap tMap = new MMap();
        tMap.put(sqlbv, "UPDATE");
        //数据提交
        VData tVData = new VData();
        tVData.add(tMap);
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, ""))
        {
            // @@错误处理
            mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.errorMessage = "数据提交失败!";
            mErrors.addOneError(tError);
            return false;
        }
        
        return true;
    }
    public static void main(String[] args)
    {
        DealInsuAcc tDealInsuAcc = new DealInsuAcc();
        tDealInsuAcc._GlobalInput.ComCode = "86";
        tDealInsuAcc._GlobalInput.ManageCom = "86";
        tDealInsuAcc._GlobalInput.Operator = "001";

        tDealInsuAcc.dealInsuAcc("2009-07-03");
    }

	public CErrors getCErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean submitData(VData data, String Operater) {
		_GlobalInput = (GlobalInput) data.getObjectByObjectName("GlobalInput", 0);
		_TransferData = (TransferData)data.getObjectByObjectName("TransferData", 0);
		String tDealDate = (String)_TransferData.getValueByName("DealDate");
		String tNextPriceDate = (String)_TransferData.getValueByName("NextPriceDate");
		if("DoBatch".equals(Operater)){
			DealInsuAcc tDealInsuAcc = new DealInsuAcc(_GlobalInput);
			if(!tDealInsuAcc.dealInsuAcc(tDealDate,tNextPriceDate)){
				this.mErrors.copyAllErrors(tDealInsuAcc.mErrors);
				return false;
			}
		}
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		logger.debug("//////////////"+mErrors.getFirstError());
		return mErrors;
	}

}
