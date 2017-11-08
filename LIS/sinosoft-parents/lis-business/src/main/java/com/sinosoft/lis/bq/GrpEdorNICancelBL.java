package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 新保加人保全项目</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * ReWrite ZhangRong
 * @version 1.0
 */

import java.util.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class GrpEdorNICancelBL implements EdorCancel {
private static Logger logger = Logger.getLogger(GrpEdorNICancelBL.class);
  /** 传入数据的容器 */
  private VData mInputData = new VData();


  /** 传出数据的容器 */
  private MMap mMap = new MMap();
  private VData mResult = new VData();


  /** 数据操作字符串 */
  private String mOperate;


  /** 错误处理类 */
  public CErrors mErrors = new CErrors();


  /** 全局基础数据 */
  private GlobalInput mGlobalInput = new GlobalInput();
  private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

  public GrpEdorNICancelBL() {
  }


  /**
   * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
   * @param cInputData 传入的数据,VData对象
   * @param cOperate 数据操作字符串，主要包括"INSERT"
   * @return 布尔值（true--提交成功, false--提交失败）
   */
  public boolean submitData(VData cInputData, String cOperate) {
    //将操作数据拷贝到本类中
    this.mInputData = (VData) cInputData.clone();

    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData()) {
      return false;
    }

    //进行业务处理
    if (!dealData()) {
      return false;
    }

    //准备往后台的数据
    if (!prepareOutputData()) {
      return false;
    }

    return true;
  }


  /**
   * 将外部传入的数据分解到本类的属性中
   * @param: 无
   * @return: boolean
   */
  private boolean getInputData() {
    try {
      mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
          "GlobalInput", 0);
      mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.
                             getObjectByObjectName("LPGrpEdorItemSchema", 0);
    } catch (Exception e) {
      // @@错误处理
      e.printStackTrace();
      CError tError = new CError();
      tError.moduleName = "GrpEdorNICancelBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "接收数据失败!!";
      this.mErrors.addOneError(tError);
      return false;
    }

    if (mGlobalInput == null || mLPGrpEdorItemSchema == null) {
      CError tError = new CError();
      tError.moduleName = "GrpEdorNICancelBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "输入数据有误!";
      this.mErrors.addOneError(tError);
      return false;
    }
    return true;
  }


  /**
   * 根据前面的输入数据，进行逻辑处理
   * @return 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData() {
    String tGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();
    LCContDB tLCContDB = new LCContDB();
    tLCContDB.setGrpContNo(tGrpContNo);
    tLCContDB.setAppFlag("2");
    LCContSet tLCContSet = tLCContDB.query();
    for (int i = 1; i <= tLCContSet.size(); i++) {
      String tContNo = tLCContSet.get(i).getContNo();
      mMap.put(
          "delete from LCDuty where PolNo in (select polno from lcpol where contno= '" +
          tContNo + "')", "DELETE");
      mMap.put("delete from LPEdorMain where EDORACCEPTNO = '"
	       + mLPGrpEdorItemSchema.getEdorNo() + "' and EdorNo = '"
	       + mLPGrpEdorItemSchema.getEdorNo() + "' and ContNo = '" + tContNo + "'", "DELETE");
      mMap.put("delete from LCPremToAcc where PolNo in (select PolNo from LCPol where ContNo = '" +
               tContNo + "' and AppFlag = '2')", "DELETE");
      mMap.put("delete from LCGetToAcc where PolNo in (select PolNo from LCPol where ContNo = '" +
               tContNo + "' and AppFlag = '2')", "DELETE");
      mMap.put(
          "delete from LCBnf where PolNo in (select polno from lcpol where contno= '" +
          tContNo + "')", "DELETE");
      mMap.put(
              "delete from LCInsuredRelated where PolNo in (select polno from lcpol where contno= '" +
              tContNo + "')", "DELETE");
      mMap.put(
          "delete from LCPrem where PolNo in (select polno from lcpol where contno= '" +
          tContNo + "')", "DELETE");
      mMap.put(
          "delete from LCGet where PolNo in (select polno from lcpol where contno= '" +
          tContNo + "')", "DELETE");
      mMap.put("delete from LCInsureAccFee where ContNo = '" + tContNo + "'",
               "DELETE");
      mMap.put("delete from LCInsureAccClassFee where ContNo = '" + tContNo +
               "'", "DELETE");
      mMap.put("delete from LCInsureAccClass where ContNo = '" + tContNo + "'",
               "DELETE");
      mMap.put("delete from LCInsureAcc where ContNo = '" + tContNo + "'",
               "DELETE");
      mMap.put("delete from LCInsureAccTrace where PolNo in (select polno from lcpol where contno= '" +
               tContNo + "')", "DELETE");
      mMap.put("delete from LCInsured where ContNo = '" + tContNo + "'",
               "DELETE");
      mMap.put("delete from LCCustomerImpart where ContNo = '" + tContNo + "'",
               "DELETE");
      mMap.put("delete from LCCustomerImpartParams where ContNo = '" + tContNo +
               "'", "DELETE");
      mMap.put("delete from LCNotePad where ContNo = '" + tContNo + "'",
               "DELETE");
      mMap.put("delete from LCRReport where ContNo = '" + tContNo + "'",
               "DELETE");
      mMap.put("delete from LCCSpec where ContNo = '" + tContNo + "'", "DELETE");
      mMap.put("delete from LCSpec where ContNo = '" + tContNo + "'", "DELETE");
      mMap.put("delete from LCPol where ContNo = '" + tContNo + "'", "DELETE");
      mMap.put("delete from LCCont where ContNo = '" + tContNo + "'", "DELETE");
      mMap.put("delete from LJSPayPerson where ContNo='" + tContNo +"' and edorno='"
                 + mLPGrpEdorItemSchema.getEdorNo()
                 + "'","DELETE");
    }
//        }
    return true;
  }


  /**
   * 准备往后层输出所需要的数据
   * @return 如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean prepareOutputData() {
    try {
      mResult.clear();
      mResult.add(mMap);
    } catch (Exception e) {
      // @@错误处理
      e.printStackTrace();
      CError tError = new CError();
      tError.moduleName = "GrpEdorNICancelBL";
      tError.functionName = "prepareOutputData";
      tError.errorMessage = "在准备往后层处理所需要的数据时出错! ";
      this.mErrors.addOneError(tError);
      return false;
    }

    return true;
  }


  /**
   * 数据输出方法，供外界获取数据处理结果
   * @return 包含有数据查询结果字符串的VData对象
   */
  public VData getResult() {
    return mResult;
  }


public CErrors getErrors() {
	return mErrors;
}

}
