



/**
 * <p>Title: PDRiskAppDefi</p>
 * <p>Description: 险种承保定义</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-12
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.ArrayList;


public class PDRiskSortDefiBL implements BusinessService{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    private VData tResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    /** 数据操作字符串 */
    private String mOperate;
    /** 业务处理相关变量 */
    private MMap map = new MMap();
    private String mTableName = "";
    private String mRiskcode = "";
    private TransferData mTransferData = new TransferData();
    private ArrayList mList = new ArrayList();
    private ArrayList mList2 = new ArrayList();
    private ArrayList mList3 = new ArrayList();

    public PDRiskSortDefiBL() {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate) {
        
        if (!getInputData(cInputData)) {
            return false;
        }
        if (!check()) {
            return false;
        }
        //进行业务处理
        if (!dealData(cOperate)) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "$tableName$BL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败$tableName$BL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //准备给后台的数据
//        if (!prepareOutputData(cOperate)) {
//            return false;
//        }
        tResult.add(map);
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(tResult, "")) {
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "ProdDefWorkFlowBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        
        RiskState.setState((String)((TransferData)cInputData.getObjectByObjectName("TransferData", 0)).getValueByName("RiskCode"), "契约业务控制->险种分类定义", "1");

        return true;
    }

    private boolean check() {
    	 for(int i=0;i<mList.size();i++){
    		if("".equals(mList.get(i))||mList.get(i)==null){
    			CError.buildErr(this,"第"+(i+1)+"行请选择！");
    	            return false;
    		}
    		for(int j=(i+1);j<mList.size();j++){
    			if(mList.get(i).equals(mList.get(j))&&mList2.get(i).equals(mList2.get(j))){
    				CError.buildErr(this,"exists same data");
    	            return false;
    			}
    		}
    	}
       for(int i=0;i<mList2.size();i++){
    		
    		if("".equals(mList2.get(i))||mList2.get(i)==null){
    			CError.buildErr(this,""+mList.get(i)+"行请赋值");
    	            return false;
    		}
    	}
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData", 0);
        mList = (ArrayList) mTransferData.getValueByName("list");
        mList2 = (ArrayList) mTransferData.getValueByName("list2");
        mList3 = (ArrayList) mTransferData.getValueByName("list3");
        mRiskcode = (String) mTransferData.getValueByName("RiskCode");

        this.mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0);

        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData(String cOperate) {
        System.out.println(" operator : " + cOperate);

        //下面处理险种账户关联表
        PD_LMRiskSortDB tPD_LMRiskSortDB = new
                                           PD_LMRiskSortDB();
        tPD_LMRiskSortDB.setRiskCode(this.mRiskcode);
        PD_LMRiskSortSet tPD_LMRiskSortSet = tPD_LMRiskSortDB.query();
        this.map.put(tPD_LMRiskSortSet, "DELETE");
        if ("save".equals(cOperate)) {
            for (int i = 0; i < mList.size(); i++) {
                PD_LMRiskSortSchema tPD_LMRiskSortSchema = new
                        PD_LMRiskSortSchema();
                tPD_LMRiskSortSchema.setRiskCode(mRiskcode);
                tPD_LMRiskSortSchema.setRiskSortType(String.valueOf(mList.get(i)));
                tPD_LMRiskSortSchema.setRiskSortValue(String.valueOf(mList2.get(
                        i)));
                tPD_LMRiskSortSchema.setRemark(String.valueOf(mList3.get(i)));
                tPD_LMRiskSortSchema.setOperator(this.mGlobalInput.Operator);
                tPD_LMRiskSortSchema.setMakeDate(PubFun.getCurrentDate());
                tPD_LMRiskSortSchema.setMakeTime(PubFun.getCurrentTime());
                tPD_LMRiskSortSchema.setModifyDate(PubFun.getCurrentDate());
                tPD_LMRiskSortSchema.setModifyTime(PubFun.getCurrentTime());
                this.map.put(tPD_LMRiskSortSchema.getSchema(), "INSERT");
            }
        }
        return true;
    }


    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData(String cOperate) {
        try {
            VData tData = new VData();
            tData = (VData) mResult.get(0);
            MMap tmap = (MMap) tData.getObjectByObjectName("MMap", 0);
            if ("save".equalsIgnoreCase(cOperate)) {
                //考虑到外键因素，添加新的账户是需要新执行tmap，再执行map
                tmap.add(map);
                tResult.add(tmap);
            } else {
                map.add(tmap);

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public VData getResult() {
		return mResult;
		}
	
	 public CErrors getErrors() {
			// TODO Auto-generated method stub
			return mErrors;
		}

    public static void main(String[] args) {
    }
}

