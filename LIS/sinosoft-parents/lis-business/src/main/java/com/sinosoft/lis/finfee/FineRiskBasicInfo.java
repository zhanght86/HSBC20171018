package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;
import com.sinosoft.utility.*;

/**
 * <p>Title: RiskBasicInfo</p>
 * <p>Description: 险种基本信息</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SinoSoft</p>
 * @author ln
 * @version 1.0
 */

public class FineRiskBasicInfo
{
private static Logger logger = Logger.getLogger(FineRiskBasicInfo.class);

  /**险种代码*/
  private String mRiskCode ;
  /**险种名称*/
  private String mRiskName ;
  /**交费金额*/
  private double mMoney ;
  /**缴费年期*/
  private int mYears ;
  /**缴费间隔*/
  private int mIntv ;
  
  /**交费金额(字符型)*/
  private String mPayMoney ;
  /**缴费年期(字符型)*/
  private String mPayYears ;
  /**缴费间隔(字符型)*/
  private String mPayIntv ;
  
  public FineRiskBasicInfo()
  {
  }

  public void setRiskCode(String tRiskCode)
  {
    mRiskCode = tRiskCode;
  }
  public String getRiskCode()
  {
    return mRiskCode;
  }
  public void setRiskName(String tRiskName)
  {
    mRiskName = tRiskName;
  }
  public String getRiskName()
  {
    return mRiskName;
  }
  
  //---------------------------------------
  public void setPayYears(String tPayYears)
  {
    mPayYears = tPayYears;
  }
  public String getPayYears()
  {
    return mPayYears;
  }
  public void setYears(int tYears)
  {
    mYears = tYears;
  }
  public void setYears(String tYears)
  {
    try
    {
      if(tYears != null && !"".equals(tYears))
      {
    	  Integer tInteger = new Integer(tYears);
    	  int i = tInteger.intValue();
    	  mYears = i;
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
  public int getYears()
  {
    return mYears;
  }
  
  //---------------------------------------
  public void setPayIntv(String tPayIntv)
  {
	  mPayIntv = tPayIntv;
  }
  public String getPayIntv()
  {
    return mPayIntv;
  }
  public void setIntv(int tIntv)
  {
    mIntv = tIntv;
  }
  public void setIntv(String tIntv)
  {
    if (tIntv != null && !tIntv.equals(""))
    {
      Integer tInteger = new Integer(tIntv);
      int i = tInteger.intValue();
      mIntv = i;
    }
	}
  public int getIntv()
  {
    return mIntv;
  }

  //---------------------------------------
  public void setPayMoney(String tPayMoney)
  {
	  mPayMoney = tPayMoney;
  }
  public String getPayMoney()
  {
    return mPayMoney;
  }
  public void setMoney(double tMoney)
  {
	  mMoney = tMoney;
  }

  public void setMoney(String tMoney)
  {
    try
    {
      if(tMoney != null && !"".equals(tMoney))
      {
        mMoney = Double.parseDouble(tMoney);
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
  public double getMoney()
  {
    return mMoney;
  }
 
  /**
   * 自动转换成数字
   * @return  如果发生异常返回false,否则，返回true;
   */
  private boolean convertMoney()
  {
	
    try
    {     
    	logger.debug("***RiskCode: "+mRiskCode+"  PayMoney: "+mPayMoney);
        //转换缴费金额
    	if( mPayMoney!=null && !"".equals(mPayMoney))
        { 
	    	int index = mPayMoney.indexOf("元");
	    	int index1 = mPayMoney.indexOf("万元");
	         if(index!=-1)
	         {
	        	 try
	             {
	        		 mMoney = Double.parseDouble(mPayMoney.substring(0,index));
	             }
	             catch(Exception ex1){} //防止转换因格式不对出现异常
	         }
	         else if(index1!=-1)
	         {
	        	 try
	             {
	        		 mMoney = 10000 * Double.parseDouble(mPayMoney.substring(0,index));
	             }
	             catch(Exception ex1){} //防止转换因格式不对出现异常
	         }
	         
	         try
	         {
	        	 mMoney = Double.parseDouble(mPayMoney);
	         }
	         catch(Exception ex1){} //防止转换因格式不对出现异常
        }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * 自动转换成数字
   * @return  如果发生异常返回false,否则，返回true;
   */
  private boolean convertYears()
  {
    try
    {     
    	logger.debug("***RiskCode: "+mRiskCode+"  PayYears: "+mPayYears);
        //转换缴费金额
    	if( mPayYears!=null && !"".equals(mPayYears))
        { 
	    	int index = mPayYears.indexOf("年");
	         if(index!=-1)
	         {
	        	 try
	             {
	        		 mYears = Integer.parseInt(mPayYears.substring(0,index));
	             }
	             catch(Exception ex1){} //防止转换因格式不对出现异常
	         }
	         
	         try
	         {
	        	 mYears = Integer.parseInt(mPayYears);
	         }
	         catch(Exception ex1){} //防止转换因格式不对出现异常
        }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return false;
    }
    return true;
  }
  
  /**
   * 转换缴费间隔的值，如保险期间初始值为“趸缴”，分解为PayIntv = 0
   * @return 转换出现异常返回false,否则，返回true;
   */
  private boolean convertIntv()
  {
    try
    {   
      if( mPayIntv!=null && !"".equals(mPayIntv))
      {	
        int index1 = mPayIntv.indexOf("趸");
        int index2 = mPayIntv.indexOf("月");
        int index3 = mPayIntv.indexOf("季");
        int index4 = mPayIntv.indexOf("半年");
        int index5 = mPayIntv.indexOf("年");
        int index6 = mPayIntv.indexOf("不定");
        if( index1 !=-1)  
        {
        	mIntv = 0;
        }
        else if( index2 !=-1)
        {
        	mIntv = 1;
        }
        else if( index3 !=-1)
        {
        	mIntv = 3;
        }
        else if( index4 !=-1)
        {
        	mIntv = 6;
        }
        else if( index5 !=-1)
        {
        	mIntv = 12;
        }
        else if( index6 !=-1)
        {
        	mIntv = -1;
        }  
        else 
        {
        	try
            {
        	  mIntv = Integer.parseInt(mPayIntv);
            }
            catch(Exception ex1){} //防止转换因格式不对出现异常
        }
          
      }//end if
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * 转换传入险种信息的档次，以及保险期间、交费期间等信息
   * @return 转换出现异常返回false,否则，返回true;
   */
  public boolean convertRiskinfo()
  {
    try
    {
       //转换保险期间等值
       if(!convertMoney())
       {
         return false;
       }

       //转换档次
       if(!convertYears())
       {
         return false;
       }
       //转换份数
       if(!convertIntv())
       {
         return false;
       }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  public String encode()
  {
    String strReturn = "";
        strReturn = StrTool.cTrim( StrTool.unicodeToGBK(mRiskCode) ) + SysConst.PACKAGESPILTER
              + StrTool.cTrim( StrTool.unicodeToGBK(mRiskName) ) + SysConst.PACKAGESPILTER
              + mMoney + SysConst.PACKAGESPILTER
              + mYears + SysConst.PACKAGESPILTER
              + mIntv + SysConst.PACKAGESPILTER  ;
		return strReturn;
  }
  
  public static void main(String[] args)
  {
    FineRiskBasicInfo riskBasicInfo1 = new FineRiskBasicInfo();
//    riskBasicInfo1.setRiskCode("111602");
//    riskBasicInfo1.setLevel("计划3");
//    riskBasicInfo1.convertRiskinfo();
    logger.debug("riskBasicInfo1.encode(): "+riskBasicInfo1.encode());
//    String t = "至88岁";
//    for(int i=0;i<t.length();i++)
//    {
//      logger.debug(t.charAt(i));
//    }

  }
}
