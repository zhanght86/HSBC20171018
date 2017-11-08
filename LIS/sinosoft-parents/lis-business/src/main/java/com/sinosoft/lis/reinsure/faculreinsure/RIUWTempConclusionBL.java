

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;

public class RIUWTempConclusionBL
{

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 前台传入的公共变量 */
    private GlobalInput globalInput = new GlobalInput();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    private TransferData mTransferData = new TransferData();
    private RIDutyStateSet mRIDutyStateSet ;

    /** 数据操作字符串 */
    private String strOperate;
    private String mTempUWConclusion;
    private MMap mMap = new MMap();

    private PubSubmit tPubSubmit = new PubSubmit();


    //业务处理相关变量
    /** 全局数据 */

    public RIUWTempConclusionBL()
    {
    }

    /**
    * 提交数据处理方法
    * @param cInputData 传入的数据,VData对象
    * @param cOperate 数据操作字符串
    * @return 布尔值（true--提交成功, false--提交失败）
    */
   public boolean submitData(VData cInputData, String cOperate){
       strOperate=cOperate;
       if (!getInputData(cInputData)){
           return false;
       }
       if (!dealData()){
           return false;
       }
       return true;
   }

   public static void main(String[] args){
          GlobalInput globalInput = new GlobalInput();
          globalInput.ComCode = "8611";
          globalInput.Operator = "001";
          TransferData tTransferData = new TransferData();
          tTransferData.setNameAndValue("GrpContNo","1300111588");
          tTransferData.setNameAndValue("ContPlanCode","");
          tTransferData.setNameAndValue("RiskCode","");
          tTransferData.setNameAndValue("DutyCode","");
          tTransferData.setNameAndValue("InsuredNo","");
          tTransferData.setNameAndValue("InsuredName","");
          tTransferData.setNameAndValue("TempUWConclusion","");
          tTransferData.setNameAndValue("TempUWConclusionConf","");
          tTransferData.setNameAndValue("UWFlag","2");
          tTransferData.setNameAndValue("DeleteType","02");
          //
          RIDutyStateSchema tRIDutyStateSchema = new RIDutyStateSchema();
          RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
          tRIDutyStateDB.setState("04");

          RIDutyStateSet mRIDutyStateSet = tRIDutyStateDB.query();


          // 准备传输数据 VData
          VData tVData = new VData();
          tVData.add(tTransferData);
          tVData.add(globalInput);
          tVData.add(mRIDutyStateSet);

          // 数据传输
          RIUWTempConclusionBL tRIUWTempConclusionBL = new RIUWTempConclusionBL();
          tRIUWTempConclusionBL.submitData(tVData, "03");

          try {
          } catch (Exception ex) {
              ex.printStackTrace();
          }
    }

    private boolean prepareOutputData(){
        try{
            this.mInputData.clear();
            this.mInputData.add(mMap);
        }
        catch (Exception ex){
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDComBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    private boolean dealData() {
        mTempUWConclusion = (String)mTransferData.getValueByName("TempUWConclusion");
        String tGrpContNo = (String)mTransferData.getValueByName("GrpContNo");
        String tContPlanCode = (String) mTransferData.getValueByName("ContPlanCode");
        String tRiskCode = (String) mTransferData.getValueByName("RiskCode");
        String tDutyCode = (String) mTransferData.getValueByName("DutyCode");
        String tInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
        String tInsuredName = (String) mTransferData.getValueByName("InsuredName");
        String tUWFlag = (String) mTransferData.getValueByName("UWFlag");
        String tTempUWConclusionConf = (String) mTransferData.getValueByName("TempUWConclusionConf");
        String tDeleteType = (String) mTransferData.getValueByName("DeleteType");
        //strOperate：01－全部查询结果；02－选中查询结果
        if(strOperate.equals("01")){ //全部查询结果
            StringBuffer sqlStrBuffer = new StringBuffer();
            if(tUWFlag.equals("1")){ //核保临分结论
                sqlStrBuffer.append(" select '"+tGrpContNo+"' ProposalGrpContNo,a.proposalno ProposalNo,b.dutycode DutyCode,'" +mTempUWConclusion + "' State,'"+mTempUWConclusion+"' UWConclusion,null CalFeeTerm,null CalFeeType,null StandbyString1,null StandbyString2 from lcpol a,lcduty b where a.polno=b.polno ");
                sqlStrBuffer.append(" and exists (select * from lcgrpcont where trim(grpcontno)=trim(a.grpcontno) and trim(proposalgrpcontno)='"+tGrpContNo+"') ");
                sqlStrBuffer.append(" and trim(b.dutycode) in (select distinct trim(associatedcode) from RIAccumulateRDCode) ");
            }else{ //再保临分结论
                sqlStrBuffer.append(" select '"+tGrpContNo+"' ProposalGrpContNo,a.proposalno ProposalNo,b.dutycode DutyCode,'" +mTempUWConclusion + "' State,(select UWConclusion from RIDutyState where trim(ProposalNo)=trim(a.ProposalNo) and trim(dutycode)=trim(b.Dutycode)) UWConclusion,null CalFeeTerm,null CalFeeType,null StandbyString1,null StandbyString2 from lcpol a,lcduty b where a.polno=b.polno ");
                sqlStrBuffer.append(" and exists (select * from RIDutyState where trim(ProposalNo)=trim(a.ProposalNo) and trim(DutyCode)=trim(b.DutyCode) and State='02') ");
                sqlStrBuffer.append(" and exists (select * from lcgrpcont where trim(grpcontno)=trim(a.grpcontno) and trim(proposalgrpcontno)='"+tGrpContNo+"') ");
                sqlStrBuffer.append(" and trim(b.dutycode) in (select distinct trim(associatedcode) from RIAccumulateRDCode) ");
            }
            if(tRiskCode!=null&&!tRiskCode.equals("")){
                sqlStrBuffer.append(" and trim(a.RiskCode)='"+tRiskCode+"'");
            }
            if(tDutyCode!=null&&!tDutyCode.equals("")){
                sqlStrBuffer.append(" and trim(b.DutyCode)='"+tDutyCode+"'");
            }
            if(tInsuredNo!=null&&!tInsuredNo.equals("")){
                sqlStrBuffer.append(" and trim(a.InsuredNo) = '"+tInsuredNo+"'");
            }
            if(tInsuredName!=null&&!tInsuredName.equals("")){
                sqlStrBuffer.append(" and a.InsuredName like '%"+tInsuredName+"%'");
            }
            if(tContPlanCode!=null&&!tContPlanCode.equals("")){
                sqlStrBuffer.append(" and exists (select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and trim(contno)=trim(a.contno) and trim(contplancode)='"+tContPlanCode+"')");
            }
            if(tTempUWConclusionConf!=null&&!tTempUWConclusionConf.equals("")){
                sqlStrBuffer.append(" and exists (select state from RIDutyState where trim(proposalno)=trim(a.proposalno) and trim(dutycode)=trim(b.dutycode) and trim(state)='"+tTempUWConclusionConf+"') ");
            }
            System.out.println(" 全部查询结果 : "+sqlStrBuffer.toString());
            RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
            mRIDutyStateSet = tRIDutyStateDB.executeQuery(sqlStrBuffer.toString());
            if(tRIDutyStateDB.mErrors.needDealError()){
                buildError("update", "查询责任保单记录失败");
                return false;
            }
            //核保临分结论需判断是临分保单
            if(tUWFlag.equals("1")){
                for (int i = 1; i <= mRIDutyStateSet.size(); i++) {

                    if (mRIDutyStateSet.get(i).getUWConclusion() != null && mRIDutyStateSet.get(i).getState().equals("03")) {
                        buildError("dealData", "不能对已临分的保单下再保临分结论");
                        return false;
                    } else {
                        mRIDutyStateSet.get(i).setUWConclusion(null);
                    }
                }
            }
            System.out.println(" 下结论记录数量： "+mRIDutyStateSet.size());
        }else if(strOperate.equals("02")){//对选中结果进行操作

        }else if(strOperate.equals("03")){ //取消临分结论
            if(tDeleteType.equals("02")){
                RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
                tRIDutyStateDB.setProposalGrpContNo(tGrpContNo);
                tRIDutyStateDB.setUWConclusion("02");
                tRIDutyStateDB.setState("00");
                mRIDutyStateSet = tRIDutyStateDB.query();
                if(tRIDutyStateDB.mErrors.needDealError()){
                    buildError("update", "保存临分核保结论时出现错误!");
                    return false;
                }
                for(int i=1;i<=mRIDutyStateSet.size();i++){
                    mRIDutyStateSet.get(i).setState("02");
                }
            }else if(tDeleteType.equals("03")){
                RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
                tRIDutyStateDB.setProposalGrpContNo(tGrpContNo);
                tRIDutyStateDB.setUWConclusion("02");
                tRIDutyStateDB.setState("04");
                mRIDutyStateSet = tRIDutyStateDB.query();
                if(tRIDutyStateDB.mErrors.needDealError()){
                    buildError("update", "保存临分核保结论时出现错误!");
                    return false;
                }
                for(int i=1;i<=mRIDutyStateSet.size();i++){
                    mRIDutyStateSet.get(i).setState("02");
                }
            }
        }
        if (mTempUWConclusion.trim().equals("99")) { //如果是取消
            if(tUWFlag.equals("1")){ //核保临分意见
                mMap.put(mRIDutyStateSet, "DELETE");
            }else{ //再保临分结论
                for(int i=1;i<=mRIDutyStateSet.size();i++){
                    mRIDutyStateSet.get(i).setState("02");
                    mRIDutyStateSet.get(i).setUWConclusion("02");
                }
            }
        } else {
            mMap.put(mRIDutyStateSet, "DELETE&INSERT");
        }

        if(!prepareOutputData()){
            return false;
        }
        if (!tPubSubmit.submitData(this.mInputData, "")) {
            if (tPubSubmit.mErrors.needDealError()) {
                buildError("update", "保存临分核保结论时出现错误!");
                return false;
            }
        }
        mMap = null;
        tPubSubmit = null;

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData){
        globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                    "GlobalInput", 0));
        this.mInputData=cInputData;
        this.mTransferData=(TransferData) cInputData.getObjectByObjectName("TransferData", 0);
        if(!strOperate.equals("01")){
            this.mRIDutyStateSet = (RIDutyStateSet) cInputData.getObjectByObjectName("RIDutyStateSet", 0);
        }
        return true;
    }

    /*
     * add by kevin, 2002-10-14
     */
    private void buildError(String szFunc, String szErrMsg){
        CError cError = new CError();
        cError.moduleName = "ReComManageBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
}
