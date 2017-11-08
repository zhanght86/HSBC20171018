package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: lis</p>
 * <p>Description: 指标校验基础类</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 6.0
 */

public class BasicCheckInfo {
private static Logger logger = Logger.getLogger(BasicCheckInfo.class);

  protected TransferData mTransferData = new TransferData();
  
  public BasicCheckInfo() {
  }

  public BasicCheckInfo(VData cInputData) {
    setBasicIndexInfo(cInputData);
  }

  private boolean setBasicIndexInfo(VData bInputData) {
    try {
      this.mTransferData = (TransferData)bInputData.getObjectByObjectName("TransferData", 0);
      return true;
    }
    catch (Exception ex) {
      return false;
    }
  }

  /*--------------------------------------------------------*/
  /**
   * 校验身份证号和性别的关系
   */
  public String checkIDNoAndSex(String tSex,String tIDType,String tIDNo)
  {
	String tResult = "";
	if(((tSex==null||tSex.equals(""))&&tIDType.equals("0"))||tSex.equals("")||(tSex!=null&&!tSex.equals("0")&&!tSex.equals("1")))
	{
		 if(tSex==null||tSex.equals("null"))
		 {
			 tSex = "";
		 }
		 tResult = "输入性别"+tSex+"不明确";
		 return tResult;
	}
	  
	  if(tIDType.equals("0"))
	  {
		  if(tIDNo.length()!=15&&tIDNo.length()!=18)
		  {
			  tResult = "输入的身份证号的信息错误";
			  return tResult;
		  }
//		  if(tBirthday==null||tBirthday.equals(""))
//		  {
//			  tResult = "输入的生日的信息错误";
//			  return tResult;
//		  }
		  //如果长度是15位
		  if(tIDNo.length()==15)
		  {
			  String tLast =  tIDNo.substring(14);
			  if(tSex.equals("0"))
			  {
				  if(Integer.parseInt(tLast)%2==0)
				  {
					  tResult = "输入的性别与身份证号的信息不一致";
					  return tResult;
				  }	  
			  }
			  else if(tSex.equals("1"))
			  {
				  if(Integer.parseInt(tLast)%2!=0)
				  {
					  tResult = "输入的性别与身份证号的信息不一致";
					  return tResult;
				  }
			  }
		  }
		  else if(tIDNo.length()==18)
		  {
			  String tLast =  tIDNo.substring(16,17);
			  if(tSex.equals("0"))
			  {
				  
				  if(Integer.parseInt(tLast)%2==0)
				  {
					  tResult = "输入的性别与身份证号的信息不一致";
					  return tResult;
				  }
			  }
			  else if(tSex.equals("1"))
			  {
				  if(Integer.parseInt(tLast)%2!=0)
				  {
					  tResult = "输入的性别与身份证号的信息不一致";
					  return tResult;
				  }
			  }
		  }
	  }
	return tResult;
  }
  /**
   * 校验出生日期和证件号的关系
   * @param tSex
   * @param tIDType
   * @param tIDNo
   * @param tBirthday
   * @return
   */
  public String checkIDNoAndBirth(String tSex,String tIDType,String tIDNo,String tBirthday)
  {
		String tResult = "";
		if(!tIDType.equals("0"))
		{
			return "";
		}
		if(((tBirthday==null||tBirthday.equals(""))&&tIDType.equals("0")))
		{
			 tResult = "输入生日为空";
			 return tResult;
		}
		  
		  if(tIDType.equals("0"))
		  {
			  if(tIDNo.length()!=15&&tIDNo.length()!=18)
			  {
				  tResult = "输入的身份证号的信息错误";
				  return tResult;
			  }

			  //如果长度是15位
			  if(tIDNo.length()==15)
			  {
				  ExeSQL tExeSQL = new ExeSQL();
				  String tBirthSQL = "select to_char(to_date('"+"?tBirthday?"+"','yyyy-mm-dd'),'yymmdd') from dual";
				  SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				  sqlbv.sql(tBirthSQL);
				  sqlbv.put("tBirthday", tBirthday);
				  String tBirth = tExeSQL.getOneValue(sqlbv);
				  if(tBirth==null||tBirth.equals(""))
				  {
					  tResult = "输入的生日错误";
					  return tResult;
				  }
				  String tLast =  tIDNo.substring(6,12);
				  if(!tLast.equals(tBirth))
				  {
					 tResult = "输入的生日与身份证号的信息不一致";
					 return tResult; 
				  }
			  }
			  else if(tIDNo.length()==18)
			  {
				  ExeSQL tExeSQL = new ExeSQL();
				  String tBirthSQL = "select to_char(to_date('"+"?tBirthday?"+"','yyyy-mm-dd'),'yyyymmdd') from dual";
				  SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				  sqlbv1.sql(tBirthSQL);
				  sqlbv1.put("tBirthday", tBirthday);
				  String tBirth = tExeSQL.getOneValue(sqlbv1);
				  if(tBirth==null||tBirth.equals(""))
				  {
					  tResult = "输入的生日错误";
					  return tResult;
				  }
				  String tLast =  tIDNo.substring(6,14);
				  if(!tLast.equals(tBirth))
				  {
					 tResult = "输入的生日与身份证号的信息不一致";
					 return tResult; 
				  }
			  }
		  }
		return tResult;
  }
  /*--------------------------------------------------------*/
}
