package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
public class BQCheckUI implements BusinessService{
private static Logger logger = Logger.getLogger(BQCheckUI.class);
  public BQCheckUI() {
  }

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors=new CErrors();
  private VData mResult = new VData();
  //private VData mInputData = new VData();
  //业务处理相关变量
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  private String mManageCom="";
  private String mDay[]=null;
  private String mBQCode="";
  private String mDefineCode="";
  private String mOpt="";
  private String mAppOperator="";
  private String mConfOperator="";
  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData, String cOperate){
    try{
      if( !cOperate.equals("PRINTGET") && !cOperate.equals("PRINTPAY")) {
        buildError("submitData", "不支持的操作字符串");
        return false;
      }
      // 得到外部传入的数据，将数据备份到本类中
      if( !getInputData(cInputData) ) {
        return false;
      }
      // 进行业务处理
      if( !dealData() ) {
        return false;
      }
      // 准备传往后台的数据
      VData vData = new VData();
      if( !prepareOutputData(vData) ) {
        return false;
      }
      BQCheckBL tBQCheckBL = new BQCheckBL();
      logger.debug("Start BQCheckBL Submit ...");
      if( !tBQCheckBL.submitData(vData, cOperate) ) {
        if( tBQCheckBL.mErrors.needDealError() ) {
          mErrors.copyAllErrors(tBQCheckBL.mErrors);
          return false;
        } else {
          buildError("submitData", "tBQCheckBL发生错误，但是没有提供详细的出错信息");
          return false;
        }
      } else {
        mResult = tBQCheckBL.getResult();
        return true;
      }
    }catch(Exception e){
      e.printStackTrace();
      CError cError = new CError( );
      cError.moduleName = "tBQCheckUI";
      cError.functionName = "submit";
      cError.errorMessage = e.toString();
      mErrors.addOneError(cError);
      return false;
    }
  }
  /**
   * 准备往后层输出所需要的数据
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean prepareOutputData(VData vData)
  {
    try {
      vData.clear();
      vData.add(mDay);
      vData.add(mManageCom);
      vData.add(mBQCode);
      vData.add(mDefineCode);
      vData.add(mOpt);
      vData.add(mAppOperator);
      vData.add(mConfOperator);
      vData.add(mGlobalInput);

    } catch (Exception ex) {
      ex.printStackTrace();
      buildError("prepareOutputData", "发生异常");
      return false;
    }
    return true;

  }

  /**
   * 根据前面的输入数据，进行UI逻辑处理
   * 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData()
  {
    return true;
  }

  /**
   * 从输入数据中得到所有对象
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData){ //全局变量
    mDay=(String[])cInputData.get(0);
    mManageCom=(String)cInputData.get(1);
    mBQCode=(String)cInputData.get(2);
    mDefineCode=(String)cInputData.get(3);
    mOpt=(String)cInputData.get(4);
    mAppOperator=(String)cInputData.get(5);
    mConfOperator=(String)cInputData.get(6);

    mGlobalInput.setSchema((GlobalInput)cInputData.get(7));

    if( mDay==null ) {
      buildError("getInputData", "没有得到足够的信息！");
      return false;
    }
    if( mGlobalInput==null ) {
      buildError("getInputData", "没有得到足够的信息！");
      return false;
    }
    if(mDefineCode==""){
      buildError("mDefineCode", "没有得到足够的信息！");
      return false;
    }

    return true;
  }

  public VData getResult() {
    return this.mResult;
  }

  private void buildError(String szFunc, String szErrMsg){
    CError cError = new CError( );
    cError.moduleName = "BQCheckUI";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }
  public static void main(String[] args) {
    BQCheckUI sc = new BQCheckUI();
    String ttDay[]=new String[2];
    ttDay[0]="2004-03-03";
    ttDay[1]="2004-03-04";
    String mBQCode="";
    GlobalInput tG = new GlobalInput();
    tG.ManageCom="86";
    tG.Operator="001";
    String code="bq7";
    String mng="86";
    String opt="000035";
    VData tVData = new VData();
    tVData.add(ttDay);
    tVData.add(mng);
    tVData.add(mBQCode);
    tVData.add(code);
    tVData.add(opt);
    tVData.add(tG);
    sc.submitData(tVData,"PRINTPAY");
  }
public CErrors getErrors() {
	// TODO Auto-generated method stub
	return this.mErrors;
}
}
