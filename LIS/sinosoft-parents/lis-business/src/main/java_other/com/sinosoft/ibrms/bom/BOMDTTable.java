/**
 * Copyright (c) 2008 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.ibrms.bom;
import org.apache.log4j.Logger;

import java.util.*;
import java.text.SimpleDateFormat;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.ibrms.BOMPreCheck;

/**
 * <p>ClassName: BOMDTTable </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2010-12-15
 */

public class BOMDTTable extends AbstractBOM
{
private static Logger logger = Logger.getLogger(BOMDTTable.class);


	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";


	/** 比率 */
	private Double DTRate;



	// @Constructor
	public BOMDTTable()
	{  }

	public void setDTRate( Double DTRate )
	{
	  this.DTRate = DTRate;
 	}

	public Double getDTRate()
	{
	  return DTRate;
	}


	/**
	* 设置对应传入参数的String形式的字段值
	* @param: FCode String 需要赋值的对象
	* @param: FValue String 要赋的值
	* @return: boolean
	**/
	public boolean setV(String FCode ,String FValue)
	{

	  if( StrTool.cTrim( FCode ).equals( "" ))
	      return false;

	if("DTRate".equals(FCode))
		{
			setDTRate(Double.valueOf(FValue));
		}

	    return true;
	}

	/**
	* 取得对应传入参数的String形式的字段值
	* @param: FCode String 希望取得的字段名
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(String FCode)
	{

	  String strReturn = "";
	  if (FCode.equalsIgnoreCase("DTRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDTRate()));
	  }
	  if (strReturn.equals(""))
	  {
	     strReturn = "null";
	  }
	  return strReturn;
	}

	// @CErrors
	public CErrors getErrors(){
		return new BOMPreCheck().mErrors;
	}
}
