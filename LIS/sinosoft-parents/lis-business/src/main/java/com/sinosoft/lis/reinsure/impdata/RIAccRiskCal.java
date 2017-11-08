

package com.sinosoft.lis.reinsure.impdata;


import com.sinosoft.lis.db.RIPolRecordBakeDB;
import com.sinosoft.lis.db.RIUnionAccumulateDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.RIPolRecordBakeSet;
import com.sinosoft.lis.vschema.RIUnionAccumulateSet;
import com.sinosoft.utility.CError;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RIAccRiskCal {
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 前台传入的公共变量 */
    private GlobalInput globalInput = new GlobalInput();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    private TransferData mTransferData;
    private MMap mMap ;
    private int rowNum=1000;
    private boolean mEndFlag = false;
    private boolean unionAccFlag=false;//风险累计是否有联合风险定义
    private String mAccumulateDefNo;
    private RIUnionAccumulateSet mRIUnionAccumulateSet;
    private PubSubmit tPubSubmit = new PubSubmit();
    
    public RIAccRiskCal() {
    }

    /**
     * 提交数据处理方法
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate) {
        if (!getInputData(cInputData)){
            return false;
        }
        if (!dealData()) {
            return false;
        }

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        this.globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        this.mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
    	this.mAccumulateDefNo = (String)mTransferData.getValueByName("AccumulateDefNo");
        if(this.globalInput==null){
            buildError("getInputData","前台传输用户信息数据失败");
            return false;
        }
        if(mInputData==null){
            buildError("getInputData","前台传输全局公共数据失败");
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行UI逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
    	
    	RIUnionAccumulateDB tRIUnionAccumulateDB = new RIUnionAccumulateDB();
//      取得该累计风险下的所有联合累计定义    	
    	String sql = "select * from RIUnionAccumulate a where a.AccumulateDefNO='"+mAccumulateDefNo+"'";
    	mRIUnionAccumulateSet = tRIUnionAccumulateDB.executeQuery(sql);
    	
        if(mRIUnionAccumulateSet!=null&&mRIUnionAccumulateSet.size()>0){//有联合风险累计
        	this.unionAccFlag=true;
        }
        while (true){
            if(!calAccAmnt()){
                return false;
            }
            if (mEndFlag) {
                break;
            }
        }
        return true;
    }

    public boolean calAccAmnt(){
    	mMap = new MMap();
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("select * from (select * from ripolrecordbake a ,(select distinct insuredno A1 from ripolrecordbake s where s.AccumulateDefNO='");
        strBuffer.append(mAccumulateDefNo);
        strBuffer.append("' and s.batchno='0000000000'");
        strBuffer.append(" and s.NodeState <> '08'");
        strBuffer.append(" and rownum <= ");
        strBuffer.append(rowNum);
        strBuffer.append(" ) x where a.insuredno=x.A1 and a.AccumulateDefNO='");
        strBuffer.append(mAccumulateDefNo + "'  and a.NodeState <> '08' and a.batchno='0000000000'");
        strBuffer.append(" ) x order by x.InsuredNo,x.GetDate,x.ContNo,x.EventType,x.PolNo ");

        System.out.println(" 累计风险计算 sql: "+strBuffer.toString());
        
        RIPolRecordBakeDB tRIPolRecordBakeDB = new RIPolRecordBakeDB();
        RIPolRecordBakeSet newRIPolRecordBakeSet = tRIPolRecordBakeDB.executeQuery(strBuffer.toString());
        if (tRIPolRecordBakeDB.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tRIPolRecordBakeDB.mErrors);
            return false;
        }
        if(newRIPolRecordBakeSet.size()==0){
            mEndFlag = true;
            return true;
        }
        String insuredNo = ""; //被保险人号
        double accAmnt=0;
        for(int i=1;i<=newRIPolRecordBakeSet.size();i++){
        	boolean calflag=true;//计算风险保额标记
        	String unionaccno="";//联合累计编号，如果险种不属于联合累计风险中的险种unionaccno=""
            //需要累计
            if (!newRIPolRecordBakeSet.get(i).getInsuredNo().equals(insuredNo)) {
                insuredNo = newRIPolRecordBakeSet.get(i).getInsuredNo();
//　　　　　　　　如果是新的被保险人需要　重新累计
                accAmnt = 0;
            }
//          有联合累计风险定义
            if(this.unionAccFlag==true){
                for(int j=1;j<=this.mRIUnionAccumulateSet.size();j++){
//                  如果当前险种属于联合累计风险定义中的险种，取得联合累计编号
                	if(mRIUnionAccumulateSet.get(j).getAssociatedCode().equals(newRIPolRecordBakeSet.get(i).getRiskCode())){
                        unionaccno=mRIUnionAccumulateSet.get(j).getUnionAccNo();
                        //循环取出该联合累计所有的险种编码
                        for(int k=1;k<=this.mRIUnionAccumulateSet.size();k++){
                            if(mRIUnionAccumulateSet.get(k).getUnionAccNo().equals(unionaccno)&&!mRIUnionAccumulateSet.get(k).getAssociatedCode().equals(newRIPolRecordBakeSet.get(i).getRiskCode())){
                                //用contno+联合累计风险定义中不等于当前险种的险种编码+insuredno到容器中取得累计风险保额
                                String unionkey=newRIPolRecordBakeSet.get(i).getContNo()+mRIUnionAccumulateSet.get(k).getAssociatedCode()+newRIPolRecordBakeSet.get(i).getInsuredNo();
                                if(this.mTransferData.getValueByName(unionkey)!=null){
                                    newRIPolRecordBakeSet.get(i).setAccAmnt(((Double)this.mTransferData.getValueByName(unionkey)).doubleValue());
                                    calflag=false;//不需要计算风险保额
                                    break;
                                }
                            }
                        }
                        break;
                	}
                }                   
            }
            if (calflag) {
                accAmnt = accAmnt + newRIPolRecordBakeSet.get(i).getRiskAmnt();
                newRIPolRecordBakeSet.get(i).setAccAmnt(accAmnt);
                if(!"".equals(unionaccno)){//如果当前险种属于联合累计定义中的险种且需要计算累计风险保额，需要把计算结果保存到transferdate容器中
                	this.mTransferData.setNameAndValue(newRIPolRecordBakeSet.get(i).getContNo()+newRIPolRecordBakeSet.get(i).getRiskCode()+newRIPolRecordBakeSet.get(i).getInsuredNo(), accAmnt);
                }             
            }                                      
           newRIPolRecordBakeSet.get(i).setNodeState("08");
        }
        mMap.put(newRIPolRecordBakeSet,"UPDATE");
        if(!saveData()){
            return false;
        }
    	return true;
    }
    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean saveData() {
        try {
            this.mInputData.clear();
            this.mInputData.add(mMap);
            if (!tPubSubmit.submitData(this.mInputData, "")) {
                if (tPubSubmit.mErrors.needDealError()) {
                    buildError("update", "计算累计风险，更新接口表数据时出错!");
                    return false;
                }
            }
            mMap = null;
        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "RIAccRiskCal";
            tError.functionName = "save";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();

        cError.moduleName = "RIAccRiskCal";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
    
    public static void main(String[] args){
    	RIAccRiskCal tRIAccRiskCal = new RIAccRiskCal();
    	VData cInputData = new VData();
    	GlobalInput globalInput = new GlobalInput();
        globalInput.ManageCom = "8611";
        globalInput.Operator = "001";
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("EndDate", "2008-11-21");
        tTransferData.setNameAndValue("AccumulateDefNo", "L000000009");
        cInputData.add(globalInput);
        cInputData.add(tTransferData);
    	tRIAccRiskCal.submitData(cInputData, "");
    }
}
