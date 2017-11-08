/***********************************************************************
 * Module:  DimensionofNatureTime.java
 * Author:  
 * Purpose: Defines the Class DimensionofNatureTime
 ***********************************************************************/

package com.sinosoft.claimcalculator.dimension;
import java.util.*;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;

import com.sinosoft.claimcalculator.ClaimCalculatorDimension;
import com.sinosoft.claimcalculator.pubfun.CalculatorPubFun;

/** @pdOid 52cc125c-0b63-46b7-a163-ff82ede403ac */
public class DimensionofNatureTime extends ClaimCalculatorDimension {
	private static Logger logger = Logger.getLogger(DimensionofNatureTime.class);
   /** @pdOid 67dae560-3e7c-420b-8fe1-8d05e4496d7b */
   private String ValidDateStart;
   /** @pdOid 1520a70e-b627-4e49-8429-4f553e5d9d51 */
   private String ValidDateEnd;
   
// @Constructor
   public DimensionofNatureTime(){
	   setDimensionCode("1");
	   setDimensionName("自然时间维度");
   }
   /** 对每一个Dimension判断当前理赔是否需要计算累加器，返回true需要计算，返回false不需要计算。
    * 
    * @pdOid 2e62fe47-9895-428a-b0b1-a3df10d24830 */
   public boolean ifNeedCalculating() {
      // TODO: implement
	  String tAccidentDate = this.mLLRegisterSchema.getAccidentDate();
	  if (tAccidentDate == null || "".equals(tAccidentDate)){
		  logger.info("出险日期为空！");
		  return false;
	  }
	  if (this.ValidDateStart == null || "".equals(this.ValidDateStart)
			||  this.ValidDateEnd == null || "".equals(this.ValidDateEnd)){
		  logger.info("累加器生效时期设定错误！");
		  return false;
	  }
	  if (CalculatorPubFun.isBetweenDate(tAccidentDate, this.ValidDateStart, this.ValidDateEnd)){
		  logger.info("满足自然时间维度，将受本累加器控制！");
		  return true;
	  }else{
		  logger.info("不满足自然时间维度，不受本累加器控制！");
	      return false;
	  }
   }
   
   /** @param t_Date
    * @pdOid b1d9c65f-62bc-4975-b6ac-fa59d2a79c8a */
   public void setValidDateStart(String t_Date) {
      // TODO: implement
	   this.ValidDateStart = t_Date;
   }
   
   /** @pdOid f8383895-4496-4c25-8fab-84a8ecb8aa83 */
   public String getValidDateStart() {
      // TODO: implement
      return this.ValidDateStart;
   }
   
   /** @param t_Date
    * @pdOid b5f304c9-c1cc-4b9a-adc9-70f5d8397cfa */
   public void setValidDateEnd(String t_Date) {
      // TODO: implement
	   this.ValidDateEnd = t_Date;
   }
   
   /** @pdOid 2a0957ee-c0f0-4fcb-9ce7-6dc7469f181c */
   public String getValidDateEnd() {
      // TODO: implement
      return this.ValidDateEnd;
   }

}