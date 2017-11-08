

package com.sinosoft.lis.reinsure.calculate.calitem;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.schema.RIItemCalSchema;
import com.sinosoft.lis.schema.RIRecordTraceSchema;
import com.sinosoft.lis.schema.RICalParamSchema;
import com.sinosoft.utility.CError;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @zhangbin
 * @version 1.0
 */
public class RIItemCalClass {
    public CErrors mErrors = new CErrors();
    private double mReslut = 0;
    public RIItemCalClass() {
    }
    public boolean calItem(RIRecordTraceSchema riRecordTraceSchema,RICalParamSchema riCalParamSchema,RIItemCalSchema riItemCalSchema){
        try{
            if(riItemCalSchema.getArithmeticID().equals("i001000001")){
                if(riItemCalSchema.getCalItemOrder()==1){//新单分保保费
                    mReslut=riRecordTraceSchema.getCessionAmount()/1000*riCalParamSchema.getParamDouble1();
                    return true;
                }
                if(riItemCalSchema.getCalItemOrder()==2){//新单分保佣金
                    mReslut=riRecordTraceSchema.getPremChang()*riCalParamSchema.getParamDouble2();
                    return true;
                }
                if(riItemCalSchema.getCalItemOrder()==3){//续期分保保费
                    mReslut=riRecordTraceSchema.getCurentAmnt()*riCalParamSchema.getParamDouble1()/1000*riCalParamSchema.getParamDouble2();
                    return true;
                }
                if(riItemCalSchema.getCalItemOrder()==4){//续期分保佣金
                    mReslut=riRecordTraceSchema.getPremChang()/1000*riCalParamSchema.getParamDouble3();
                    return true;
                }
                if(riItemCalSchema.getCalItemOrder()==5){//保全分保保费
                    if(riRecordTraceSchema.getAddSubFlag().equals("1")){
                        mReslut=riCalParamSchema.getParamDouble4()/1000*riCalParamSchema.getParamDouble2();
                        return true;
                    }else{
                        mReslut=riRecordTraceSchema.getCurentAmnt()*riCalParamSchema.getParamDouble1()/1000*riCalParamSchema.getParamDouble2();
                        return true;
                    }
                }
                if(riItemCalSchema.getCalItemOrder()==6){//保全分保佣金
                    mReslut=riRecordTraceSchema.getPremChang()/1000*riCalParamSchema.getParamDouble3();
                    return true;
                }
                if(riItemCalSchema.getCalItemOrder()==7){//理赔摊回
                    mReslut=riCalParamSchema.getParamDouble2()*riCalParamSchema.getParamDouble1();
                    return true;
                }
            }
        }catch(Exception ex){
            buildError("RIItemCalClass",ex.getMessage());
            return false;
        }
        return true;
    }

    public double getValue(){
        return mReslut;
    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "RIItemCalClass";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }
}
