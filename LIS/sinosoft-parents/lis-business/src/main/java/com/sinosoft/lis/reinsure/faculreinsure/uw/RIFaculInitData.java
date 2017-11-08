

package com.sinosoft.lis.reinsure.faculreinsure.uw;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @ zhangbin
 * @version 1.0
 */
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

public class RIFaculInitData {
    public CErrors mErrors=new CErrors();
    /**私有静态构造器*/
    private static RIFaculInitData riFaculInitData=null;
    /**累计风险类别*/
    private RIAccumulateDefSet mRIAccumulateDefSet ;
    /**累计险种/责任表*/
    private RIAccumulateRDCodeSet mRIAccumulateRDCodeSet ;
    /**累计给付责任表*/
    private RIAccumulateGetDutySet mRIAccumulateGetDutySet ;
    /**再保方案*/
    private RIPreceptSet mRIPreceptSet ;
    /**核保核赔算法*/
    private RIItemCalSet mRIItemSet ;
    private String mGrpContNo ;
    private String[][] mRiskCode ;
    private String[][] mDutyCode ;

    private String mOpeFlag ;
    private VData mInputData ;

    public RIFaculInitData(){
    }

    public boolean init(VData vData , String aOpeFlag){

        this.mOpeFlag = aOpeFlag;
        //初始化累计风险
        if(!initRIAccumulateDef()){
            return false;
        }
        //初始化累计风险险种/责任
        if(!initRIAccumulateRDCode()){
            return false;
        }
        //初始化累计风险给付责任
        if(!initRIAccumulateGetDuty()){
            return false;
        }
        //初始化再保方案
        if(!initPrecept()){
            return false;
        }
        //初始化核保核赔算法
        if(mOpeFlag.equals("11")){
            if(!initIndRIItemCal()){
                return false;
            }
        }else if(mOpeFlag.equals("13")){
            if(!initIndRIItemCal()){
                return false;
            }
        }else if(mOpeFlag.equals("14")){
            if(!initClmRIItemCal()){
                return false;
            }
        }else if(mOpeFlag.equals("15")){
            if(!initGrpRIItemCal()){
                return false;
            }
            if(!initRiskCode()){
                return false;
            }
            if(!initDutyCode()){

            }
        }else{
            buildError("Init"," 自核初始化类-> 不知名类型 ");
            return false;
        }

        return true;
    }
    /**
     * 获取前台信息
     * @param vInputData VData
     * @return boolean
     */
    private boolean getInputData(VData vInputData){
        this.mInputData = vInputData ;
        TransferData tTransferData = (TransferData) mInputData.getObjectByObjectName( "TransferData", 0);
        this.mGrpContNo = (String)tTransferData.getValueByName("GRPCONTNO");
        return true;
    }

    //初始化累计风险
    private boolean initRIAccumulateDef(){
        RIAccumulateDefDB tRIAccumulateDefDB = new RIAccumulateDefDB();
        String tSQL ;
        if(mOpeFlag.equals("15")){
            tSQL = "select distinct * from AccumulateRDCode where AssociatedCode in (select distinct b.dutycode from lcpol a ,lcduty b where a.polno=b.polno and a.grpcontno='" + mGrpContNo + "') "
                   ;
        }else{
            tSQL = " select * from RIAccumulateDef a where a.state='01' ";
        }
        mRIAccumulateDefSet = tRIAccumulateDefDB.executeQuery(tSQL) ;
        if(tRIAccumulateDefDB.mErrors.needDealError()){
            buildError("InitInfo","初始化累计风险定义时出错");
            return false;
        }
        return true ;
    }

    //初始化累计险种/责任表
    private boolean initRIAccumulateRDCode() {
        RIAccumulateRDCodeDB tRIAccumulateRDCodeDB = new RIAccumulateRDCodeDB();
        String tSQL = " select * from  RIAccumulateRDCode a where exists (select * from RIAccumulateDef b where b.state='01' and b.accumulatedefno=a.accumulatedefno) order by a.accumulatedefno ";
        mRIAccumulateRDCodeSet = tRIAccumulateRDCodeDB.executeQuery(tSQL) ;
        if(tRIAccumulateRDCodeDB.mErrors.needDealError()){
            buildError("InitInfo","初始化险种/责任出错");
            return false;
        }
        return true;
    }

    //初始化给付责任
    private boolean initRIAccumulateGetDuty() {
        RIAccumulateGetDutyDB tRIAccumulateGetDutyDB = new RIAccumulateGetDutyDB();
        String tSQL = " select * from  RIAccumulateGetDuty a where exists (select * from RIAccumulateDef b where b.state='01' and b.accumulatedefno=a.accumulatedefno) order by a.accumulatedefno ";
        mRIAccumulateGetDutySet = tRIAccumulateGetDutyDB.executeQuery(tSQL) ;
        if (tRIAccumulateGetDutyDB.mErrors.needDealError()) {
            buildError("InitInfo", "初始化给付责任时出错");
            return false;
        }
        return true;
    }

    //初始化再保方案。包括:合同分保方案，临时分保方案
    private boolean initPrecept(){
        RIPreceptDB tRIPreceptDB = new RIPreceptDB();
        String tSQL = " select * from RIPrecept a where a.state='01' ";
        mRIPreceptSet = tRIPreceptDB.executeQuery(tSQL) ;
        if(tRIPreceptDB.mErrors.needDealError()){
            buildError("InitInfo","初始化再保方案出错");
            return false;
        }
        return true ;
    }

    /**
     * 11-临分自核规则
     */
    private boolean initIndRIItemCal() {
        RIItemCalDB riItemCalDB = new RIItemCalDB();
        String tSQL = " select * from RIItemCal a where a.ArithmeticType='11' " ;
        mRIItemSet = riItemCalDB.executeQuery(tSQL);
        if (riItemCalDB.mErrors.needDealError()) {
            buildError("InitInfo", "初始化给付责任时出错");
            return false;
        }
        return true;
    }

    /**
     * 15-临分团单自核
     */
    private boolean initGrpRIItemCal() {
        RIItemCalDB riItemCalDB = new RIItemCalDB();
        String tSQL = " select * from RIItemCal a where a.ArithmeticType='15' " ;
        mRIItemSet = riItemCalDB.executeQuery(tSQL);
        if (riItemCalDB.mErrors.needDealError()) {
            buildError("InitInfo", "初始化给付责任时出错");
            return false;
        }
        return true;
    }

    /**
     * 14-核赔规则
     */
    private boolean initClmRIItemCal(){
        RIItemCalDB riItemCalDB = new RIItemCalDB();
        String tSQL = " select * from RIItemCal a where a.ArithmeticType='14' " ;
        mRIItemSet = riItemCalDB.executeQuery(tSQL);
        if (riItemCalDB.mErrors.needDealError()) {
            buildError("InitInfo", "初始化给付责任时出错");
            return false;
        }
        return true;
    }

    /**
     * 初始化团单险种编码
     * @return boolean
     */
    private boolean initRiskCode(){
        String tSQL = " select distinct a.RiskCode from lcpol a where a.grpcontno='" + mGrpContNo + "' " ;

        ExeSQL tExeSQL = new ExeSQL();
        SSRS temp = tExeSQL.execSQL(tSQL);
        if(tExeSQL.mErrors.needDealError()){
            buildError("InitInfo","初始化团单险种编码时出错");
            return false;
        }
        this.mRiskCode = temp.getAllData();
        return true;
    }

    /**
     * 初始化团单责任编码
     * @return boolean
     */
    private boolean initDutyCode(){
        String tSQL = " select distinct a.dutycode from lcduty a where exists (select * from lcpol b where b.polno=a.polno and b.grpcontno='" + mGrpContNo + "') " ;
        ExeSQL tExeSQL = new ExeSQL();
        SSRS temp = tExeSQL.execSQL(tSQL);
        if(tExeSQL.mErrors.needDealError()){
            buildError("InitInfo","初始化团单责任时出错");
            return false;
        }
        this.mDutyCode = temp.getAllData();
        return true;
    }

    /**
     * 获取累计风险定义
     * @return RIAccumulateDefSet
     */
    public RIAccumulateDefSet getRIAccumulateDefSet() {
        return mRIAccumulateDefSet;
    }

    /**
     * 获取险种/责任编码定义
     * @return RIAccumulateDefSet
     */
    public RIAccumulateRDCodeSet getRIAccumulateRDCodeSet() {
        return mRIAccumulateRDCodeSet;
    }

    /**
     * 获取给付责任定义
     * @return RIAccumulateDefSet
     */
    public RIAccumulateGetDutySet getRIAccumulateGetDutySet() {
        return mRIAccumulateGetDutySet;
    }

    /**
     * 获取再保方案定义
     * @return RIAccumulateDefSet
     */
    public RIPreceptSet getRIPreceptSet() {
        return mRIPreceptSet;
    }

    /**
     * 得到个单核保算法
     * 11-临分自核规则
     * @return RIItemCalSet
     */
    public RIItemCalSet getRIItemCalSet() {
        return mRIItemSet;
    }

    /**
     * 获取团单险种编码
     * @return String[][]
     */
    public String[][] getRiskCode(){
        return mRiskCode;
    }

    /**
     * 获取团单责任编码
     * @return String[][]
     */
    public String[][] getDutyCode(){
        return mDutyCode;
    }

    public static void main(String[] args) {
        try{
            RIFaculInitData t = new RIFaculInitData();
            VData tData = new VData();
            t.init(tData,"14");
            RIAccumulateDefSet tRIAccumulateDefSet = t.getRIAccumulateDefSet();
            RIAccumulateRDCodeSet tRIAccumulateRDCodeSet = t.getRIAccumulateRDCodeSet();
            RIAccumulateGetDutySet tRIAccumulateGetDutySet = t.getRIAccumulateGetDutySet();
            RIPreceptSet tRIPreceptSet = t.getRIPreceptSet();
            RIItemCalSet tRIItemSet = t.getRIItemCalSet();
            String tGrpContNo = "";
            String[][] tRiskCode ;
            String[][] tDutyCode;

            for(int i=1;i<=tRIItemSet.size();i++){
                System.out.println(" aaaa :"+tRIItemSet.get(i).getArithmeticDefID());
            }



        }catch(Exception ex){
            System.out.println(" "+ex);
        }
    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "InitInfo";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }
}
