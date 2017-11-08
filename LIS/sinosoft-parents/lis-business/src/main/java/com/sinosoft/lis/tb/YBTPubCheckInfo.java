package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.utility.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * <p>Title: lis</p>
 * <p>Description:银、邮保通公共校验JAVA类 </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: sinosoft</p>
 * @author duanyh
 * @version 6.0
 */

public class YBTPubCheckInfo extends BasicCheckInfo {
private static Logger logger = Logger.getLogger(YBTPubCheckInfo.class);
  
	
  public YBTPubCheckInfo() {
  }

  public YBTPubCheckInfo(VData cInputData)
  {
    super(cInputData);
  } 

  /**
   * 投保单号校验
   * @return
   */
  public String checkProposalContNo()
  {
	  String tResult = "";
	  String tProposalContNo = (String)this.mTransferData.getValueByName("LCCont.ProposalContNo");
	  logger.debug("ProposalContNo="+tProposalContNo);
	  
	  if (tProposalContNo==null||tProposalContNo.equals("")||tProposalContNo.equals("null")) 
	  {
		  logger.debug("111");
		  tResult = "投保单号不能为空!";
		  return tResult;
	  }
	  if (tProposalContNo.length() != 14) 
	  {
		  logger.debug("222");
		  tResult = "投保单印刷号必须为14位数字";
		  return tResult;

	  }
	  if (!tProposalContNo.matches("[0-9]{14}"))
	  {
		  logger.debug("444");
		  tResult = "投保单印刷号必须为14位数字";
		  return tResult;
	  }
	  if (!tProposalContNo.substring(0,6).equals("861502")) 
	  {
		  logger.debug("333");
		  tResult = "该投保单印刷号错误";
		  return tResult;
	  }
      String strSQL = "select count(*) from lccont where ProposalContNo='"+ "?ProposalContNo?" + "'";
      SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
      sqlbv1.sql(strSQL);
      sqlbv1.put("ProposalContNo",tProposalContNo);
      ExeSQL tExeSQL1 = new ExeSQL();
      String strCount = tExeSQL1.getOneValue(sqlbv1);
      int SumCount = Integer.parseInt(strCount);
      if (SumCount > 0)
      {
    	  logger.debug("555");
          tResult = "该投保单印刷号已被使用，请更正";
          return tResult;
      }
	  return tResult;
  }   

  /**
   * 被保人姓名、性别校验,证件类型以及证件号码校验
   * @return
   */
  public String checkInsuredNameAndIDNo()
  {
	  String tResult = "";
	  String tName = (String)this.mTransferData.getValueByName("LCInsured.Name");
	  String sex = (String)this.mTransferData.getValueByName("LCInsured.Sex");	  
	  String idno = (String)this.mTransferData.getValueByName("LCInsured.IDNo");
	  String idtype = (String)this.mTransferData.getValueByName("LCInsured.IDType");	  
	  String birthday = (String)this.mTransferData.getValueByName("LCInsured.Birthday");
	  String idexpdate = (String)this.mTransferData.getValueByName("LCInsured.IDExpDate");
	  String operator = (String)this.mTransferData.getValueByName("LCInsured.Operator");
	  logger.debug("IDExpDate="+idexpdate+"------operator="+operator);	  
	  logger.debug("tName="+tName+"-----sex="+sex+"-----idno="+idno+"-----idtype="+idtype+"-----birthday="+birthday);
	  
	  if (tName == null || tName.equals("null")) 
	  {
		  tResult = "被保人姓名不能为空";
		  return tResult;
	  }		
		//必须包含汉字或英文字母
	  if (CheckNUM(tName)==false) 
	  {
		  tResult = "姓名应为汉字或英文字母";
		  return tResult;
      }
	  logger.debug("tName.length()"+tName.length());
	  if (tName.length()>20)
	  {
		  tResult = "姓名最长不能超过20个字符";
		  return tResult;
	  }
	  if(sex==null||sex.equals("null")||sex.trim().equals("2"))
	  {
		  tResult = "性别录入错误或不能为空。";
		  return tResult;
	  }
	  
	  //证件类型必须为：身份证 、护照 、军官证 、士兵证、户口本、出生证。 0 1 2 4 7 A
	  if (!idtype.equals("null") && !(idtype.trim().equals("0")||idtype.trim().equals("1")||idtype.trim().equals("2")
			  ||idtype.trim().equals("4")||idtype.trim().equals("7")||idtype.trim().equals("A")))
	  {
		  tResult = "被保人证件类型不符合要求";
		  return tResult;
	  }
	  
	  if(operator!=null&&operator.equals("03"))
	  {
		  if(idtype != null&&(idtype.equals("0")||idtype.equals("1")||idtype.equals("2")||idtype.equals("D")))
		  {
			  if (idexpdate == null || idexpdate.equals("null"))
			  {
				  tResult = "证件类型为“身份证、军人证、警官证、护照”时，证件有效期未录入";
				  return tResult;
			  }
		  }
		  if (idexpdate != null && !idexpdate.equals("null"))
		  {
			  if(PubFun.calInterval(idexpdate, PubFun.getCurrentDate(), "D")>=0)
			  {
				  tResult = "证件有效期不能等于或早于投保申请日期";
				  return tResult;
			  }
		  }
	  }
		
	  // 如果证件类型是身份证则需要进行生日、性别、证件联合校验
	  if (!idtype.equals("null") && idtype.trim().equals("0")) 
	  {
		  String tmpbirthday = "";
		  String tmpsex = "";
		  FDate tFDate = new FDate();
		  if (idno.trim().length() == 18) 
		  {
			  tmpbirthday = idno.substring(6, 14);
			  logger.debug("被保人身份证号中的出生日期: "+idno.substring(6, 14));
			  tmpsex = idno.substring(16, 17);
			  logger.debug("被保人身份证号中的性别: "+idno.substring(6, 14));
		  } 
		  else if (idno.trim().length() == 15) 
		  {
			  tmpbirthday = idno.substring(6, 12);
			  tmpbirthday = "19" + tmpbirthday;
			  logger.debug("被保人身份证号中的出生日期: "+idno.substring(6, 14));
			  tmpsex = idno.substring(14, 15);
			  logger.debug("被保人身份证号中的性别: "+idno.substring(6, 14));
		  } 
		  else 
		  {
			  tResult = "被保人身份证号码位数错误,不能柜面出单";
			  return tResult;
		  }

		  if (birthday.equals("null") || birthday.equals("")) 
		  {
			  tResult = "被保人出生日期格式为空,不能柜面出单";
			  return tResult;
		  }
		  if (tFDate.getDate(birthday).compareTo(tFDate.getDate(tmpbirthday)) != 0) 
		  {
			  tResult = "被保人出生日期与证件号码校验有误,不能柜面出单";
			  return tResult;
		  }
		  if (tmpsex.equals("0") || tmpsex.equals("2") || tmpsex.equals("4")
					|| tmpsex.equals("6") || tmpsex.equals("8")) 
		  {
			  if (sex.equals("null") || sex.trim().equals("0")) 
			  {
				  tResult = "被保人性别与证件号码校验有误,不能柜面出单";
				  return tResult;
			  }
		  } 
		  else 
		  {
			  logger.debug("sex:" + sex);
			  if (sex.equals("null") || sex.trim().equals("1")) 
			  {
				  tResult = "被保人性别与证件号码校验有误,不能柜面出单";
				  return tResult;
			  }
		  }
		}
		
		//证件类型是户口本，在此增加校验
		if (!idtype.equals("null") && idtype.trim().equals("4"))
		{
			logger.debug("开始校验被保人验户口本：idno="+idno);
			String tmpbirthday = "";
			String tmpsex = "";
			FDate tFDate = new FDate();
			if (idno.trim().length() == 18) 
			{
				tmpbirthday = idno.substring(6, 14);
				logger.debug("被保人户口本中的出生日期: "+idno.substring(6, 14));
				tmpsex = idno.substring(16, 17);
				logger.debug("被保人户口本中的性别: "+idno.substring(6, 14));
			}
			else if (idno.trim().length() == 15) 
			{
				tmpbirthday = idno.substring(6, 12);
				tmpbirthday = "19" + tmpbirthday;
				logger.debug("被保人户口本中的出生日期: "+idno.substring(6, 14));
				tmpsex = idno.substring(14, 15);
				logger.debug("被保人户口本中的性别: "+idno.substring(6, 14));				
				if (tFDate.getDate(birthday).compareTo(tFDate.getDate(tmpbirthday)) != 0) 
				{
					tResult = "被保人出生日期与证件号码校验有误,不能柜面出单";
					return tResult;
				}

				if (tmpsex.equals("0") || tmpsex.equals("2") || tmpsex.equals("4")
						|| tmpsex.equals("6") || tmpsex.equals("8")) 
				{
					if (sex.equals("null") || sex.trim().equals("0")) 
					{					
						tResult = "被保人性别与证件号码校验有误,不能柜面出单";
						return tResult;
					}
				} 
				else 
				{
					logger.debug("sex:" + sex);
					if (sex.equals("null") || sex.trim().equals("1")) 
					{						
						tResult = "被保人性别与证件号码校验有误,不能柜面出单";						
						return tResult;
					}
				}				
			} 
			else
			{
				SimpleDateFormat format = null;
				if(idno.length()==8)
				{
					format = new SimpleDateFormat("yyyyMMdd");
				}
				else if(idno.length()==10)
				{
					format = new SimpleDateFormat("yyyy-MM-dd");
				}							    
				java.util.Date date = null;
				try 
				{
					date = format.parse(idno);
					logger.debug("@@@"+date);
				} catch (ParseException e) 
				{					
					tResult = "证件号码必须为身份证号或出生日期";
					return tResult;
				}   
			} 
		}
	  return tResult;
  }
  
  /**
   * 地址、邮编等信息校验
   * @return
   */
  public String checkAddress()
  {
	  String tResult = "";
	  String tPostalAddress = (String)this.mTransferData.getValueByName("LCAddress.PostalAddress");
	  String tZipCode = (String)this.mTransferData.getValueByName("LCAddress.ZipCode");
	  //String tHomeZipCode = (String)this.mTransferData.getValueByName("LCAddress.HomeZipCode");
	  logger.debug("tPostalAddress="+tPostalAddress+"-----tZipCode="+tZipCode);
	  
	  if (tPostalAddress == null || tPostalAddress.equals("null"))
	  {
		  tResult = "通讯地址必须包含汉字或英文字母";
		  return tResult;
	  }
	  if (isNumeric(tPostalAddress)==true)
	  {
		  tResult = "通讯地址必须包含汉字或英文字母";
		  return tResult;
	  }
	  if (tZipCode.equals("null") ) 
	  {
		  tResult = "邮编应为6位阿拉伯数字";
		  return tResult;
	  }
	  if (tZipCode.length() != 6 ) 
	  {
		  tResult = "邮编应为6位阿拉伯数字";
		  return tResult;
	  }
	  if (isNumeric(tZipCode)==false)
	  {
		  tResult = "邮编应为6位阿拉伯数字";
		  return tResult;
	  }	 
	  return tResult;
  }
  
  /**
   * 投保人姓名、性别以及证件类型、证件号码与生日、性别的联合校验
   * @return
   */
  public String checkAppntNameAndIDNo()
  {
	  logger.debug("^^^^^^checkAppntNameAndIDNo^^^^");
	  String tResult = "";
	  String tAppntName = (String)this.mTransferData.getValueByName("LCAppnt.AppntName");	  
	  String idno = (String)this.mTransferData.getValueByName("LCAppnt.IDNo");
	  String idtype = (String)this.mTransferData.getValueByName("LCAppnt.IDType");
	  String sex = (String)this.mTransferData.getValueByName("LCAppnt.AppntSex");
	  String birthday = (String)this.mTransferData.getValueByName("LCAppnt.AppntBirthday");
	  String idexpdate = (String)this.mTransferData.getValueByName("LCAppnt.IDExpDate");
	  String operator = (String)this.mTransferData.getValueByName("LCAppnt.Operator");
	  logger.debug("tAppntName="+tAppntName+"-----idno="+idno+"-----idtype="+idtype+"-----sex="+sex+"-----birthday="+birthday);
	  logger.debug("IDExpDate="+idexpdate+"------operator="+operator);
	  if (tAppntName == null || tAppntName.equals("null")) 
	  {
		  tResult = "投保人姓名不能为空";
		  return tResult;
	  }		
		//必须包含汉字或英文字母
	  if (CheckNUM(tAppntName)==false) 
	  {
		  tResult = "姓名应为汉字或英文字母";
		  return tResult;
      }
	  logger.debug("tAppntName.length()"+tAppntName.length());
	  if (tAppntName.length()>20)
	  {
		  tResult = "姓名最长不能超过20个字符";
		  return tResult;
	  }
	  if(sex==null||sex.equals("null")||sex.trim().equals("2"))
	  {
		  tResult = "性别录入错误或不能为空。";
		  return tResult;
	  }
	  
	  //证件类型必须为：身份证 、护照 、军官证 、士兵证、户口本、出生证。 0 1 2 4 7 A
	  if (!idtype.equals("null") && !(idtype.trim().equals("0")||idtype.trim().equals("1")||idtype.trim().equals("2")
			  ||idtype.trim().equals("4")||idtype.trim().equals("7")||idtype.trim().equals("A")))
	  {
		  tResult = "投保人证件类型不符合要求";
		  return tResult;
	  }
	  
	  if(operator!=null&&operator.equals("03"))
	  {
		  if(idtype!=null&&(idtype.equals("0")||idtype.equals("1")||idtype.equals("2")||idtype.equals("D")))
		  {
			  if (idexpdate == null || idexpdate.equals("null"))
			  {
				  tResult = "证件类型为“身份证、军人证、警官证、护照”时，证件有效期未录入";
				  return tResult;
			  }
		  }
		  if (idexpdate != null && !idexpdate.equals("null"))
		  {
			  if(PubFun.calInterval(idexpdate, PubFun.getCurrentDate(), "D")>=0)
			  {
				  tResult = "证件有效期不能等于或早于投保申请日期";
				  return tResult;
			  }
		  }
	  }	  
		
	  // 如果证件类型是身份证则需要进行生日、性别、证件联合校验
	  if (!idtype.equals("null") && idtype.trim().equals("0")) 
	  {
		  String tmpbirthday = "";
		  String tmpsex = "";
		  FDate tFDate = new FDate();
		  if (idno.trim().length() == 18) 
		  {
			  tmpbirthday = idno.substring(6, 14);
			  logger.debug("投保人身份证号中的出生日期: "+idno.substring(6, 14));
			  tmpsex = idno.substring(16, 17);
			  logger.debug("投保人身份证号中的性别: "+idno.substring(6, 14));
		  } 
		  else if (idno.trim().length() == 15) 
		  {
			  tmpbirthday = idno.substring(6, 12);
			  tmpbirthday = "19" + tmpbirthday;
			  logger.debug("投保人身份证号中的出生日期: "+idno.substring(6, 14));
			  tmpsex = idno.substring(14, 15);
			  logger.debug("投保人身份证号中的性别: "+idno.substring(6, 14));
		  } 
		  else 
		  {
			  tResult = "投保人身份证号码位数错误,不能柜面出单";
			  return tResult;
		  }

		  if (birthday.equals("null") || birthday.equals("")) 
		  {
			  tResult = "投保人出生日期格式为空,不能柜面出单";
			  return tResult;
		  }
		  if (tFDate.getDate(birthday).compareTo(tFDate.getDate(tmpbirthday)) != 0) 
		  {
			  tResult = "投保人出生日期与证件号码校验有误,不能柜面出单";
			  return tResult;
		  }
		  if (tmpsex.equals("0") || tmpsex.equals("2") || tmpsex.equals("4")
					|| tmpsex.equals("6") || tmpsex.equals("8")) 
		  {
			  if (sex.equals("null") || sex.trim().equals("0")) 
			  {
				  tResult = "投保人性别与证件号码校验有误,不能柜面出单";
				  return tResult;
			  }
		  } 
		  else 
		  {
			  logger.debug("sex:" + sex);
			  if (sex.equals("null") || sex.trim().equals("1")) 
			  {
				  tResult = "投保人性别与证件号码校验有误,不能柜面出单";
				  return tResult;
			  }
		  }
		}
		
		//证件类型是户口本，在此增加校验
		if (!idtype.equals("null") && idtype.trim().equals("4"))
		{
			logger.debug("开始校投保人验户口本：idno="+idno);
			String tmpbirthday = "";
			String tmpsex = "";
			FDate tFDate = new FDate();
			if (idno.trim().length() == 18) 
			{
				tmpbirthday = idno.substring(6, 14);
				logger.debug("投保人户口本中的出生日期: "+idno.substring(6, 14));
				tmpsex = idno.substring(16, 17);
				logger.debug("投保人户口本中的性别: "+idno.substring(6, 14));
			}
			else if (idno.trim().length() == 15) 
			{
				tmpbirthday = idno.substring(6, 12);
				tmpbirthday = "19" + tmpbirthday;
				logger.debug("投保人户口本中的出生日期: "+idno.substring(6, 14));
				tmpsex = idno.substring(14, 15);
				logger.debug("投保人户口本中的性别: "+idno.substring(6, 14));				
				if (tFDate.getDate(birthday).compareTo(tFDate.getDate(tmpbirthday)) != 0) 
				{
					tResult = "投保人出生日期与证件号码校验有误,不能柜面出单";
					return tResult;
				}

				if (tmpsex.equals("0") || tmpsex.equals("2") || tmpsex.equals("4")
						|| tmpsex.equals("6") || tmpsex.equals("8")) 
				{
					if (sex.equals("null") || sex.trim().equals("0")) 
					{					
						tResult = "投保人性别与证件号码校验有误,不能柜面出单";
						return tResult;
					}
				} 
				else 
				{
					logger.debug("sex:" + sex);
					if (sex.equals("null") || sex.trim().equals("1")) 
					{						
						tResult = "投保人性别与证件号码校验有误,不能柜面出单";						
						return tResult;
					}
				}				
			} 
			else
			{
				SimpleDateFormat format = null;
				if(idno.length()==8)
				{
					format = new SimpleDateFormat("yyyyMMdd");
				}
				else if(idno.length()==10)
				{
					format = new SimpleDateFormat("yyyy-MM-dd");
				}				 				    
				java.util.Date date = null;
				try 
				{
					date = format.parse(idno);
					logger.debug("@@@"+date);
				} catch (ParseException e) 
				{					
					tResult = "证件号码必须为身份证号或出生日期";
					return tResult;
				}   
			} 
		}
	    return tResult;
  }
  
  /**
   * 受益人信息校验：
   * @return
   */
  public String checkLCBnfNameAndIDNo()
  {
	  String tResult = "";
	  String tName = (String)this.mTransferData.getValueByName("LCBnf.Name");
	  String tBnfGrade = (String)this.mTransferData.getValueByName("LCBnf.BnfGrade");
	  String tIDType = (String)this.mTransferData.getValueByName("LCBnf.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LCBnf.IDNo");
	  String tBnfType = (String)this.mTransferData.getValueByName("LCBnf.BnfType");
	  String tRelationToInsured = (String)this.mTransferData.getValueByName("LCBnf.RelationToInsured");
	  
	  String tInsuredName = (String)this.mTransferData.getValueByName("LCInsured.Name");  
	  String tInsuredidno = (String)this.mTransferData.getValueByName("LCInsured.IDNo");
	  String tInsuredidtype = (String)this.mTransferData.getValueByName("LCInsured.IDType");
	  
	  String tRelationToInsured_App = (String)this.mTransferData.getValueByName("LCAppnt.RelationToInsured");
	  String tAppntName = (String)this.mTransferData.getValueByName("LCAppnt.AppntName");
	  String tAppntIDType = (String)this.mTransferData.getValueByName("LCAppnt.IDType");
	  String tAppntIDNo = (String)this.mTransferData.getValueByName("LCAppnt.IDNo");
	  logger.debug("tName="+tName+"-----tBnfGrade="+tBnfGrade+"-----tIDType="+tIDType+"-----tIDNo="+tIDNo+"-----tBnfType="+tBnfType+"-----tInsuredName="+tInsuredName);
	  logger.debug("tRelationToInsured_App="+tRelationToInsured_App);
	  if (tName == null || tName.equals("null"))
	  {
		  tResult = "受益人姓名不能为空";
		  return tResult;
	  }
	  if (CheckNUM(tName)==false)
	  {
		  tResult = "受益人姓名必须包含汉字或英文字母";
		  return tResult;
	  }
	  if (tBnfGrade == null || tBnfGrade.equals("null") ) 
	  {
		  tResult = "受益人级别不能为空！";
		  return tResult;
	  }
	  //受益人证件类型必须为：身份证 、护照 、军官证 、出生证、士兵证、户口本。
	  if (tIDType != null && !(tIDType.trim().equals("0")||tIDType.trim().equals("1")||tIDType.trim().equals("2")
			  ||tIDType.trim().equals("4")||tIDType.trim().equals("7")||tIDType.trim().equals("A")))
	  {
		  tResult = "受益人证件类型不符合要求";
		  return tResult;
	  }
	  if (!tIDType.equals("null") && tIDType.equals("0")) 
	  {
		  if (tIDNo.length() != 18 && tIDNo.length() != 15)
		  {
			  tResult = "受益人身份证号位数错误,不能柜面出单";
			  return tResult;
		  }
	  }
	  //当身故受益人姓名处不为空时，证件类型、证件号码、与被保险人关系以及受益比例均不能为空。
	  logger.debug("tBnfType="+tBnfType);
	  if(tBnfType.equals("1"))
	  {		 
		  String tBnfLot = (String)this.mTransferData.getValueByName("LCBnf.BnfLot");
		  if(tIDType.equals("null")||tIDNo.equals("null")||tRelationToInsured.equals("null")||tBnfLot.equals("null"))
		  {
			  tResult = "身故受益人信息录入不完整。";
			  return tResult;
		  }
		  if(tName.equals(tInsuredName))
		  {
			  tResult = "身故受益人不能与被保险人为同一人";
			  return tResult;
		  }
	  }
	  
	  //满期受益人必须为被保险人本人或为空
	  if(tBnfType.equals("0"))
	  {
		  if(!tRelationToInsured.equals("00")&&!tRelationToInsured.equals("null"))
		  {
			  tResult = "满期受益人必须为被保险人本人。";
			  return tResult;
		  }
		  if(!tName.equals(tInsuredName))
		  {
			  tResult = "满期受益人与被保人为同一人，但姓名不一致。";
			  return tResult;
		  }
		  if(!tIDType.equals(tInsuredidtype))
		  {
			  tResult = "满期受益人与被保人为同一人，但证件类型不一致。";
			  return tResult;
		  }
		  if(!tIDNo.equals(tInsuredidno))
		  {
			  tResult = "满期受益人与被保人为同一人，但证件号码不一致。";
			  return tResult;
		  }
	  }
	  
      //当投保人和受益人是同一人的时候,要保证姓名,证件类型,证件号要一致
		logger.debug("开始校验投保人和受益人是否为同一人");
        logger.debug("投保人与被保人关系:"+tRelationToInsured_App);
        logger.debug("受益人与被保人关系:"+tRelationToInsured);
        if(tRelationToInsured_App.equals(tRelationToInsured))
        {
        	if(!tAppntName.equals(tName))
        	{
        		tResult = "受益人与投保人为同一人，但姓名与投保人信息不一致。";
        		return tResult;
        	}
        	if(!tAppntIDType.equals(tIDType))
        	{
        		tResult = "受益人与投保人为同一人，但证件类型与投保人信息不一致。";
        		return tResult;
        	}
        	if(!tAppntIDNo.equals(tIDNo))
        	{
        		tResult = "受益人与投保人为同一人，但证件号码与投保人信息不一致。";
        		return tResult;
        	}
        }
	  return tResult;
  }
  
	/**
	 * 校验字符串必须全部是数字
	 * 
	 * @param: String
	 * @return: boolean
	 */
	public  boolean isNumeric(String str)
	{
		for(int i=str.length();--i>=0;)
		{
			int chr=str.charAt(i);
		    if(chr<48 || chr>57)
		        return false;
		}
		return true;
	}
	
	//包含数字，返回false
	public  boolean CheckNUM(String NUM)
	{
	    String strTemp="0123456789";
	    for (int i=0;i<NUM.length();i++)
	    {
	    	int j=strTemp.indexOf(NUM.charAt(i));
	    	if(j!=-1)
	    	{
	    		return false;
	    	}
	    }
	    return true;	
	}
  

  public static void main(String[] args) 
  {	  
	  TransferData tTransferData = new TransferData();
	  LCAppntSchema tLCAppntSchema = new LCAppntSchema();
	  tLCAppntSchema.setAppntName("11");
	  tTransferData.setNameAndValue("LCCont.ProposalContNo", null);
	  VData tVData = new VData();
	  tVData.add(tTransferData);
	  tVData.add(tLCAppntSchema);
	  YBTPubCheckInfo tYBTPubCheckInfo = new YBTPubCheckInfo(tVData);
//	  if (tYBTPubCheckInfo.CheckNUM("ww1")==false) 
//	  {
//		  String tResult = "姓名应为汉字或英文字母";
//		  logger.debug(tResult);
//      }
	  if (tYBTPubCheckInfo.isNumeric("ww1")==false)
	  {
		  String tResult = "邮编应为6位阿拉伯数字";
		  logger.debug(tResult);
	  }
	  else
	  {
		  logger.debug("ww");
	  }
	  
  }
}
