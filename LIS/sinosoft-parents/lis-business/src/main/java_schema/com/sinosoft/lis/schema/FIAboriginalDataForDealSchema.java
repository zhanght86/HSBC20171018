/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.FIAboriginalDataForDealDB;

/**
 * <p>ClassName: FIAboriginalDataForDealSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIAboriginalDataForDealSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIAboriginalDataForDealSchema.class);

	// @Field
	/** 数据流水号 */
	private String SerialNo;
	/** 数据采集编号 */
	private String AcquisitionID;
	/** 数据源编号 */
	private String DataBaseID;
	/** 数据采集批次号 */
	private String BatchNo;
	/** 数据类型 */
	private String DataType;
	/** 提取数据状态 */
	private String DataState;
	/** 业务类型编号 */
	private String BusinessCode;
	/** 明细费用类型 */
	private String FeeCode;
	/** 费用金额 */
	private double SumMoney;
	/** 业务日期 */
	private Date BusinessDate;
	/** 业务号码 */
	private String BusinessNo;
	/** 字符数据信息01 */
	private String StringInfo01;
	/** 字符数据信息02 */
	private String StringInfo02;
	/** 字符数据信息03 */
	private String StringInfo03;
	/** 字符数据信息04 */
	private String StringInfo04;
	/** 字符数据信息05 */
	private String StringInfo05;
	/** 字符数据信息06 */
	private String StringInfo06;
	/** 字符数据信息07 */
	private String StringInfo07;
	/** 字符数据信息08 */
	private String StringInfo08;
	/** 字符数据信息09 */
	private String StringInfo09;
	/** 字符数据信息10 */
	private String StringInfo10;
	/** 字符数据信息11 */
	private String StringInfo11;
	/** 字符数据信息12 */
	private String StringInfo12;
	/** 字符数据信息13 */
	private String StringInfo13;
	/** 字符数据信息14 */
	private String StringInfo14;
	/** 字符数据信息15 */
	private String StringInfo15;
	/** 日期数据信息01 */
	private Date DateInfo01;
	/** 日期数据信息02 */
	private Date DateInfo02;
	/** 日期数据信息03 */
	private Date DateInfo03;
	/** 日期数据信息04 */
	private Date DateInfo04;
	/** 日期数据信息05 */
	private Date DateInfo05;
	/** 日期数据信息06 */
	private Date DateInfo06;
	/** 日期数据信息07 */
	private Date DateInfo07;
	/** 日期数据信息08 */
	private Date DateInfo08;
	/** 日期数据信息09 */
	private Date DateInfo09;
	/** 日期数据信息10 */
	private Date DateInfo10;
	/** 数字数据信息01 */
	private double NumInfo01;
	/** 数字数据信息02 */
	private double NumInfo02;
	/** 数字数据信息03 */
	private int NumInfo03;
	/** 数字数据信息04 */
	private int NumInfo04;
	/** 数字数据信息05 */
	private int NumInfo05;
	/** 业务关联号码01 */
	private String BusinessNo01;
	/** 业务关联号码02 */
	private String BusinessNo02;
	/** 业务关联号码03 */
	private String BusinessNo03;
	/** 业务关联号码04 */
	private String BusinessNo04;
	/** 业务关联号码05 */
	private String BusinessNo05;
	/** 业务关联号码06 */
	private String BusinessNo06;
	/** 业务关联号码07 */
	private String BusinessNo07;
	/** 业务关联号码08 */
	private String BusinessNo08;
	/** 业务关联号码09 */
	private String BusinessNo09;
	/** 业务关联号码10 */
	private String BusinessNo10;
	/** 信息类型标志01 */
	private String TypeFlag01;
	/** 信息类型标志02 */
	private String TypeFlag02;
	/** 信息类型标志03 */
	private String TypeFlag03;
	/** 信息类型标志04 */
	private String TypeFlag04;
	/** 信息类型标志05 */
	private String TypeFlag05;
	/** 信息类型标志06 */
	private String TypeFlag06;
	/** 信息类型标志07 */
	private String TypeFlag07;
	/** 信息类型标志08 */
	private String TypeFlag08;
	/** 信息类型标志09 */
	private String TypeFlag09;
	/** 信息类型标志10 */
	private String TypeFlag10;
	/** 关联标记号码 */
	private String RelationCode;

	public static final int FIELDNUM = 62;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIAboriginalDataForDealSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

		PK = pk;
	}

            /**
             * Schema克隆
             * @return Object
             * @throws CloneNotSupportedException
             */
            public Object clone()
                    throws CloneNotSupportedException
            {
                FIAboriginalDataForDealSchema cloned = (FIAboriginalDataForDealSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getAcquisitionID()
	{
		return AcquisitionID;
	}
	public void setAcquisitionID(String aAcquisitionID)
	{
		AcquisitionID = aAcquisitionID;
	}
	public String getDataBaseID()
	{
		return DataBaseID;
	}
	public void setDataBaseID(String aDataBaseID)
	{
		DataBaseID = aDataBaseID;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getDataType()
	{
		return DataType;
	}
	public void setDataType(String aDataType)
	{
		DataType = aDataType;
	}
	public String getDataState()
	{
		return DataState;
	}
	public void setDataState(String aDataState)
	{
		DataState = aDataState;
	}
	public String getBusinessCode()
	{
		return BusinessCode;
	}
	public void setBusinessCode(String aBusinessCode)
	{
		BusinessCode = aBusinessCode;
	}
	public String getFeeCode()
	{
		return FeeCode;
	}
	public void setFeeCode(String aFeeCode)
	{
		FeeCode = aFeeCode;
	}
	public double getSumMoney()
	{
		return SumMoney;
	}
	public void setSumMoney(double aSumMoney)
	{
		SumMoney = aSumMoney;
	}
	public void setSumMoney(String aSumMoney)
	{
		if (aSumMoney != null && !aSumMoney.equals(""))
		{
			Double tDouble = new Double(aSumMoney);
			double d = tDouble.doubleValue();
			SumMoney = d;
		}
	}

	public String getBusinessDate()
	{
		if( BusinessDate != null )
			return fDate.getString(BusinessDate);
		else
			return null;
	}
	public void setBusinessDate(Date aBusinessDate)
	{
		BusinessDate = aBusinessDate;
	}
	public void setBusinessDate(String aBusinessDate)
	{
		if (aBusinessDate != null && !aBusinessDate.equals("") )
		{
			BusinessDate = fDate.getDate( aBusinessDate );
		}
		else
			BusinessDate = null;
	}

	public String getBusinessNo()
	{
		return BusinessNo;
	}
	public void setBusinessNo(String aBusinessNo)
	{
		BusinessNo = aBusinessNo;
	}
	public String getStringInfo01()
	{
		return StringInfo01;
	}
	public void setStringInfo01(String aStringInfo01)
	{
		StringInfo01 = aStringInfo01;
	}
	public String getStringInfo02()
	{
		return StringInfo02;
	}
	public void setStringInfo02(String aStringInfo02)
	{
		StringInfo02 = aStringInfo02;
	}
	public String getStringInfo03()
	{
		return StringInfo03;
	}
	public void setStringInfo03(String aStringInfo03)
	{
		StringInfo03 = aStringInfo03;
	}
	public String getStringInfo04()
	{
		return StringInfo04;
	}
	public void setStringInfo04(String aStringInfo04)
	{
		StringInfo04 = aStringInfo04;
	}
	public String getStringInfo05()
	{
		return StringInfo05;
	}
	public void setStringInfo05(String aStringInfo05)
	{
		StringInfo05 = aStringInfo05;
	}
	public String getStringInfo06()
	{
		return StringInfo06;
	}
	public void setStringInfo06(String aStringInfo06)
	{
		StringInfo06 = aStringInfo06;
	}
	public String getStringInfo07()
	{
		return StringInfo07;
	}
	public void setStringInfo07(String aStringInfo07)
	{
		StringInfo07 = aStringInfo07;
	}
	public String getStringInfo08()
	{
		return StringInfo08;
	}
	public void setStringInfo08(String aStringInfo08)
	{
		StringInfo08 = aStringInfo08;
	}
	public String getStringInfo09()
	{
		return StringInfo09;
	}
	public void setStringInfo09(String aStringInfo09)
	{
		StringInfo09 = aStringInfo09;
	}
	public String getStringInfo10()
	{
		return StringInfo10;
	}
	public void setStringInfo10(String aStringInfo10)
	{
		StringInfo10 = aStringInfo10;
	}
	public String getStringInfo11()
	{
		return StringInfo11;
	}
	public void setStringInfo11(String aStringInfo11)
	{
		StringInfo11 = aStringInfo11;
	}
	public String getStringInfo12()
	{
		return StringInfo12;
	}
	public void setStringInfo12(String aStringInfo12)
	{
		StringInfo12 = aStringInfo12;
	}
	public String getStringInfo13()
	{
		return StringInfo13;
	}
	public void setStringInfo13(String aStringInfo13)
	{
		StringInfo13 = aStringInfo13;
	}
	public String getStringInfo14()
	{
		return StringInfo14;
	}
	public void setStringInfo14(String aStringInfo14)
	{
		StringInfo14 = aStringInfo14;
	}
	public String getStringInfo15()
	{
		return StringInfo15;
	}
	public void setStringInfo15(String aStringInfo15)
	{
		StringInfo15 = aStringInfo15;
	}
	public String getDateInfo01()
	{
		if( DateInfo01 != null )
			return fDate.getString(DateInfo01);
		else
			return null;
	}
	public void setDateInfo01(Date aDateInfo01)
	{
		DateInfo01 = aDateInfo01;
	}
	public void setDateInfo01(String aDateInfo01)
	{
		if (aDateInfo01 != null && !aDateInfo01.equals("") )
		{
			DateInfo01 = fDate.getDate( aDateInfo01 );
		}
		else
			DateInfo01 = null;
	}

	public String getDateInfo02()
	{
		if( DateInfo02 != null )
			return fDate.getString(DateInfo02);
		else
			return null;
	}
	public void setDateInfo02(Date aDateInfo02)
	{
		DateInfo02 = aDateInfo02;
	}
	public void setDateInfo02(String aDateInfo02)
	{
		if (aDateInfo02 != null && !aDateInfo02.equals("") )
		{
			DateInfo02 = fDate.getDate( aDateInfo02 );
		}
		else
			DateInfo02 = null;
	}

	public String getDateInfo03()
	{
		if( DateInfo03 != null )
			return fDate.getString(DateInfo03);
		else
			return null;
	}
	public void setDateInfo03(Date aDateInfo03)
	{
		DateInfo03 = aDateInfo03;
	}
	public void setDateInfo03(String aDateInfo03)
	{
		if (aDateInfo03 != null && !aDateInfo03.equals("") )
		{
			DateInfo03 = fDate.getDate( aDateInfo03 );
		}
		else
			DateInfo03 = null;
	}

	public String getDateInfo04()
	{
		if( DateInfo04 != null )
			return fDate.getString(DateInfo04);
		else
			return null;
	}
	public void setDateInfo04(Date aDateInfo04)
	{
		DateInfo04 = aDateInfo04;
	}
	public void setDateInfo04(String aDateInfo04)
	{
		if (aDateInfo04 != null && !aDateInfo04.equals("") )
		{
			DateInfo04 = fDate.getDate( aDateInfo04 );
		}
		else
			DateInfo04 = null;
	}

	public String getDateInfo05()
	{
		if( DateInfo05 != null )
			return fDate.getString(DateInfo05);
		else
			return null;
	}
	public void setDateInfo05(Date aDateInfo05)
	{
		DateInfo05 = aDateInfo05;
	}
	public void setDateInfo05(String aDateInfo05)
	{
		if (aDateInfo05 != null && !aDateInfo05.equals("") )
		{
			DateInfo05 = fDate.getDate( aDateInfo05 );
		}
		else
			DateInfo05 = null;
	}

	public String getDateInfo06()
	{
		if( DateInfo06 != null )
			return fDate.getString(DateInfo06);
		else
			return null;
	}
	public void setDateInfo06(Date aDateInfo06)
	{
		DateInfo06 = aDateInfo06;
	}
	public void setDateInfo06(String aDateInfo06)
	{
		if (aDateInfo06 != null && !aDateInfo06.equals("") )
		{
			DateInfo06 = fDate.getDate( aDateInfo06 );
		}
		else
			DateInfo06 = null;
	}

	public String getDateInfo07()
	{
		if( DateInfo07 != null )
			return fDate.getString(DateInfo07);
		else
			return null;
	}
	public void setDateInfo07(Date aDateInfo07)
	{
		DateInfo07 = aDateInfo07;
	}
	public void setDateInfo07(String aDateInfo07)
	{
		if (aDateInfo07 != null && !aDateInfo07.equals("") )
		{
			DateInfo07 = fDate.getDate( aDateInfo07 );
		}
		else
			DateInfo07 = null;
	}

	public String getDateInfo08()
	{
		if( DateInfo08 != null )
			return fDate.getString(DateInfo08);
		else
			return null;
	}
	public void setDateInfo08(Date aDateInfo08)
	{
		DateInfo08 = aDateInfo08;
	}
	public void setDateInfo08(String aDateInfo08)
	{
		if (aDateInfo08 != null && !aDateInfo08.equals("") )
		{
			DateInfo08 = fDate.getDate( aDateInfo08 );
		}
		else
			DateInfo08 = null;
	}

	public String getDateInfo09()
	{
		if( DateInfo09 != null )
			return fDate.getString(DateInfo09);
		else
			return null;
	}
	public void setDateInfo09(Date aDateInfo09)
	{
		DateInfo09 = aDateInfo09;
	}
	public void setDateInfo09(String aDateInfo09)
	{
		if (aDateInfo09 != null && !aDateInfo09.equals("") )
		{
			DateInfo09 = fDate.getDate( aDateInfo09 );
		}
		else
			DateInfo09 = null;
	}

	public String getDateInfo10()
	{
		if( DateInfo10 != null )
			return fDate.getString(DateInfo10);
		else
			return null;
	}
	public void setDateInfo10(Date aDateInfo10)
	{
		DateInfo10 = aDateInfo10;
	}
	public void setDateInfo10(String aDateInfo10)
	{
		if (aDateInfo10 != null && !aDateInfo10.equals("") )
		{
			DateInfo10 = fDate.getDate( aDateInfo10 );
		}
		else
			DateInfo10 = null;
	}

	public double getNumInfo01()
	{
		return NumInfo01;
	}
	public void setNumInfo01(double aNumInfo01)
	{
		NumInfo01 = aNumInfo01;
	}
	public void setNumInfo01(String aNumInfo01)
	{
		if (aNumInfo01 != null && !aNumInfo01.equals(""))
		{
			Double tDouble = new Double(aNumInfo01);
			double d = tDouble.doubleValue();
			NumInfo01 = d;
		}
	}

	public double getNumInfo02()
	{
		return NumInfo02;
	}
	public void setNumInfo02(double aNumInfo02)
	{
		NumInfo02 = aNumInfo02;
	}
	public void setNumInfo02(String aNumInfo02)
	{
		if (aNumInfo02 != null && !aNumInfo02.equals(""))
		{
			Double tDouble = new Double(aNumInfo02);
			double d = tDouble.doubleValue();
			NumInfo02 = d;
		}
	}

	public int getNumInfo03()
	{
		return NumInfo03;
	}
	public void setNumInfo03(int aNumInfo03)
	{
		NumInfo03 = aNumInfo03;
	}
	public void setNumInfo03(String aNumInfo03)
	{
		if (aNumInfo03 != null && !aNumInfo03.equals(""))
		{
			Integer tInteger = new Integer(aNumInfo03);
			int i = tInteger.intValue();
			NumInfo03 = i;
		}
	}

	public int getNumInfo04()
	{
		return NumInfo04;
	}
	public void setNumInfo04(int aNumInfo04)
	{
		NumInfo04 = aNumInfo04;
	}
	public void setNumInfo04(String aNumInfo04)
	{
		if (aNumInfo04 != null && !aNumInfo04.equals(""))
		{
			Integer tInteger = new Integer(aNumInfo04);
			int i = tInteger.intValue();
			NumInfo04 = i;
		}
	}

	public int getNumInfo05()
	{
		return NumInfo05;
	}
	public void setNumInfo05(int aNumInfo05)
	{
		NumInfo05 = aNumInfo05;
	}
	public void setNumInfo05(String aNumInfo05)
	{
		if (aNumInfo05 != null && !aNumInfo05.equals(""))
		{
			Integer tInteger = new Integer(aNumInfo05);
			int i = tInteger.intValue();
			NumInfo05 = i;
		}
	}

	public String getBusinessNo01()
	{
		return BusinessNo01;
	}
	public void setBusinessNo01(String aBusinessNo01)
	{
		BusinessNo01 = aBusinessNo01;
	}
	public String getBusinessNo02()
	{
		return BusinessNo02;
	}
	public void setBusinessNo02(String aBusinessNo02)
	{
		BusinessNo02 = aBusinessNo02;
	}
	public String getBusinessNo03()
	{
		return BusinessNo03;
	}
	public void setBusinessNo03(String aBusinessNo03)
	{
		BusinessNo03 = aBusinessNo03;
	}
	public String getBusinessNo04()
	{
		return BusinessNo04;
	}
	public void setBusinessNo04(String aBusinessNo04)
	{
		BusinessNo04 = aBusinessNo04;
	}
	public String getBusinessNo05()
	{
		return BusinessNo05;
	}
	public void setBusinessNo05(String aBusinessNo05)
	{
		BusinessNo05 = aBusinessNo05;
	}
	public String getBusinessNo06()
	{
		return BusinessNo06;
	}
	public void setBusinessNo06(String aBusinessNo06)
	{
		BusinessNo06 = aBusinessNo06;
	}
	public String getBusinessNo07()
	{
		return BusinessNo07;
	}
	public void setBusinessNo07(String aBusinessNo07)
	{
		BusinessNo07 = aBusinessNo07;
	}
	public String getBusinessNo08()
	{
		return BusinessNo08;
	}
	public void setBusinessNo08(String aBusinessNo08)
	{
		BusinessNo08 = aBusinessNo08;
	}
	public String getBusinessNo09()
	{
		return BusinessNo09;
	}
	public void setBusinessNo09(String aBusinessNo09)
	{
		BusinessNo09 = aBusinessNo09;
	}
	public String getBusinessNo10()
	{
		return BusinessNo10;
	}
	public void setBusinessNo10(String aBusinessNo10)
	{
		BusinessNo10 = aBusinessNo10;
	}
	public String getTypeFlag01()
	{
		return TypeFlag01;
	}
	public void setTypeFlag01(String aTypeFlag01)
	{
		TypeFlag01 = aTypeFlag01;
	}
	public String getTypeFlag02()
	{
		return TypeFlag02;
	}
	public void setTypeFlag02(String aTypeFlag02)
	{
		TypeFlag02 = aTypeFlag02;
	}
	public String getTypeFlag03()
	{
		return TypeFlag03;
	}
	public void setTypeFlag03(String aTypeFlag03)
	{
		TypeFlag03 = aTypeFlag03;
	}
	public String getTypeFlag04()
	{
		return TypeFlag04;
	}
	public void setTypeFlag04(String aTypeFlag04)
	{
		TypeFlag04 = aTypeFlag04;
	}
	public String getTypeFlag05()
	{
		return TypeFlag05;
	}
	public void setTypeFlag05(String aTypeFlag05)
	{
		TypeFlag05 = aTypeFlag05;
	}
	public String getTypeFlag06()
	{
		return TypeFlag06;
	}
	public void setTypeFlag06(String aTypeFlag06)
	{
		TypeFlag06 = aTypeFlag06;
	}
	public String getTypeFlag07()
	{
		return TypeFlag07;
	}
	public void setTypeFlag07(String aTypeFlag07)
	{
		TypeFlag07 = aTypeFlag07;
	}
	public String getTypeFlag08()
	{
		return TypeFlag08;
	}
	public void setTypeFlag08(String aTypeFlag08)
	{
		TypeFlag08 = aTypeFlag08;
	}
	public String getTypeFlag09()
	{
		return TypeFlag09;
	}
	public void setTypeFlag09(String aTypeFlag09)
	{
		TypeFlag09 = aTypeFlag09;
	}
	public String getTypeFlag10()
	{
		return TypeFlag10;
	}
	public void setTypeFlag10(String aTypeFlag10)
	{
		TypeFlag10 = aTypeFlag10;
	}
	public String getRelationCode()
	{
		return RelationCode;
	}
	public void setRelationCode(String aRelationCode)
	{
		RelationCode = aRelationCode;
	}

	/**
	* 使用另外一个 FIAboriginalDataForDealSchema 对象给 Schema 赋值
	* @param: aFIAboriginalDataForDealSchema FIAboriginalDataForDealSchema
	**/
	public void setSchema(FIAboriginalDataForDealSchema aFIAboriginalDataForDealSchema)
	{
		this.SerialNo = aFIAboriginalDataForDealSchema.getSerialNo();
		this.AcquisitionID = aFIAboriginalDataForDealSchema.getAcquisitionID();
		this.DataBaseID = aFIAboriginalDataForDealSchema.getDataBaseID();
		this.BatchNo = aFIAboriginalDataForDealSchema.getBatchNo();
		this.DataType = aFIAboriginalDataForDealSchema.getDataType();
		this.DataState = aFIAboriginalDataForDealSchema.getDataState();
		this.BusinessCode = aFIAboriginalDataForDealSchema.getBusinessCode();
		this.FeeCode = aFIAboriginalDataForDealSchema.getFeeCode();
		this.SumMoney = aFIAboriginalDataForDealSchema.getSumMoney();
		this.BusinessDate = fDate.getDate( aFIAboriginalDataForDealSchema.getBusinessDate());
		this.BusinessNo = aFIAboriginalDataForDealSchema.getBusinessNo();
		this.StringInfo01 = aFIAboriginalDataForDealSchema.getStringInfo01();
		this.StringInfo02 = aFIAboriginalDataForDealSchema.getStringInfo02();
		this.StringInfo03 = aFIAboriginalDataForDealSchema.getStringInfo03();
		this.StringInfo04 = aFIAboriginalDataForDealSchema.getStringInfo04();
		this.StringInfo05 = aFIAboriginalDataForDealSchema.getStringInfo05();
		this.StringInfo06 = aFIAboriginalDataForDealSchema.getStringInfo06();
		this.StringInfo07 = aFIAboriginalDataForDealSchema.getStringInfo07();
		this.StringInfo08 = aFIAboriginalDataForDealSchema.getStringInfo08();
		this.StringInfo09 = aFIAboriginalDataForDealSchema.getStringInfo09();
		this.StringInfo10 = aFIAboriginalDataForDealSchema.getStringInfo10();
		this.StringInfo11 = aFIAboriginalDataForDealSchema.getStringInfo11();
		this.StringInfo12 = aFIAboriginalDataForDealSchema.getStringInfo12();
		this.StringInfo13 = aFIAboriginalDataForDealSchema.getStringInfo13();
		this.StringInfo14 = aFIAboriginalDataForDealSchema.getStringInfo14();
		this.StringInfo15 = aFIAboriginalDataForDealSchema.getStringInfo15();
		this.DateInfo01 = fDate.getDate( aFIAboriginalDataForDealSchema.getDateInfo01());
		this.DateInfo02 = fDate.getDate( aFIAboriginalDataForDealSchema.getDateInfo02());
		this.DateInfo03 = fDate.getDate( aFIAboriginalDataForDealSchema.getDateInfo03());
		this.DateInfo04 = fDate.getDate( aFIAboriginalDataForDealSchema.getDateInfo04());
		this.DateInfo05 = fDate.getDate( aFIAboriginalDataForDealSchema.getDateInfo05());
		this.DateInfo06 = fDate.getDate( aFIAboriginalDataForDealSchema.getDateInfo06());
		this.DateInfo07 = fDate.getDate( aFIAboriginalDataForDealSchema.getDateInfo07());
		this.DateInfo08 = fDate.getDate( aFIAboriginalDataForDealSchema.getDateInfo08());
		this.DateInfo09 = fDate.getDate( aFIAboriginalDataForDealSchema.getDateInfo09());
		this.DateInfo10 = fDate.getDate( aFIAboriginalDataForDealSchema.getDateInfo10());
		this.NumInfo01 = aFIAboriginalDataForDealSchema.getNumInfo01();
		this.NumInfo02 = aFIAboriginalDataForDealSchema.getNumInfo02();
		this.NumInfo03 = aFIAboriginalDataForDealSchema.getNumInfo03();
		this.NumInfo04 = aFIAboriginalDataForDealSchema.getNumInfo04();
		this.NumInfo05 = aFIAboriginalDataForDealSchema.getNumInfo05();
		this.BusinessNo01 = aFIAboriginalDataForDealSchema.getBusinessNo01();
		this.BusinessNo02 = aFIAboriginalDataForDealSchema.getBusinessNo02();
		this.BusinessNo03 = aFIAboriginalDataForDealSchema.getBusinessNo03();
		this.BusinessNo04 = aFIAboriginalDataForDealSchema.getBusinessNo04();
		this.BusinessNo05 = aFIAboriginalDataForDealSchema.getBusinessNo05();
		this.BusinessNo06 = aFIAboriginalDataForDealSchema.getBusinessNo06();
		this.BusinessNo07 = aFIAboriginalDataForDealSchema.getBusinessNo07();
		this.BusinessNo08 = aFIAboriginalDataForDealSchema.getBusinessNo08();
		this.BusinessNo09 = aFIAboriginalDataForDealSchema.getBusinessNo09();
		this.BusinessNo10 = aFIAboriginalDataForDealSchema.getBusinessNo10();
		this.TypeFlag01 = aFIAboriginalDataForDealSchema.getTypeFlag01();
		this.TypeFlag02 = aFIAboriginalDataForDealSchema.getTypeFlag02();
		this.TypeFlag03 = aFIAboriginalDataForDealSchema.getTypeFlag03();
		this.TypeFlag04 = aFIAboriginalDataForDealSchema.getTypeFlag04();
		this.TypeFlag05 = aFIAboriginalDataForDealSchema.getTypeFlag05();
		this.TypeFlag06 = aFIAboriginalDataForDealSchema.getTypeFlag06();
		this.TypeFlag07 = aFIAboriginalDataForDealSchema.getTypeFlag07();
		this.TypeFlag08 = aFIAboriginalDataForDealSchema.getTypeFlag08();
		this.TypeFlag09 = aFIAboriginalDataForDealSchema.getTypeFlag09();
		this.TypeFlag10 = aFIAboriginalDataForDealSchema.getTypeFlag10();
		this.RelationCode = aFIAboriginalDataForDealSchema.getRelationCode();
	}

	/**
	* 使用 ResultSet 中的第 i 行给 Schema 赋值
	* @param: rs ResultSet
	* @param: i int
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// 非滚动游标
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("AcquisitionID") == null )
				this.AcquisitionID = null;
			else
				this.AcquisitionID = rs.getString("AcquisitionID").trim();

			if( rs.getString("DataBaseID") == null )
				this.DataBaseID = null;
			else
				this.DataBaseID = rs.getString("DataBaseID").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("DataType") == null )
				this.DataType = null;
			else
				this.DataType = rs.getString("DataType").trim();

			if( rs.getString("DataState") == null )
				this.DataState = null;
			else
				this.DataState = rs.getString("DataState").trim();

			if( rs.getString("BusinessCode") == null )
				this.BusinessCode = null;
			else
				this.BusinessCode = rs.getString("BusinessCode").trim();

			if( rs.getString("FeeCode") == null )
				this.FeeCode = null;
			else
				this.FeeCode = rs.getString("FeeCode").trim();

			this.SumMoney = rs.getDouble("SumMoney");
			this.BusinessDate = rs.getDate("BusinessDate");
			if( rs.getString("BusinessNo") == null )
				this.BusinessNo = null;
			else
				this.BusinessNo = rs.getString("BusinessNo").trim();

			if( rs.getString("StringInfo01") == null )
				this.StringInfo01 = null;
			else
				this.StringInfo01 = rs.getString("StringInfo01").trim();

			if( rs.getString("StringInfo02") == null )
				this.StringInfo02 = null;
			else
				this.StringInfo02 = rs.getString("StringInfo02").trim();

			if( rs.getString("StringInfo03") == null )
				this.StringInfo03 = null;
			else
				this.StringInfo03 = rs.getString("StringInfo03").trim();

			if( rs.getString("StringInfo04") == null )
				this.StringInfo04 = null;
			else
				this.StringInfo04 = rs.getString("StringInfo04").trim();

			if( rs.getString("StringInfo05") == null )
				this.StringInfo05 = null;
			else
				this.StringInfo05 = rs.getString("StringInfo05").trim();

			if( rs.getString("StringInfo06") == null )
				this.StringInfo06 = null;
			else
				this.StringInfo06 = rs.getString("StringInfo06").trim();

			if( rs.getString("StringInfo07") == null )
				this.StringInfo07 = null;
			else
				this.StringInfo07 = rs.getString("StringInfo07").trim();

			if( rs.getString("StringInfo08") == null )
				this.StringInfo08 = null;
			else
				this.StringInfo08 = rs.getString("StringInfo08").trim();

			if( rs.getString("StringInfo09") == null )
				this.StringInfo09 = null;
			else
				this.StringInfo09 = rs.getString("StringInfo09").trim();

			if( rs.getString("StringInfo10") == null )
				this.StringInfo10 = null;
			else
				this.StringInfo10 = rs.getString("StringInfo10").trim();

			if( rs.getString("StringInfo11") == null )
				this.StringInfo11 = null;
			else
				this.StringInfo11 = rs.getString("StringInfo11").trim();

			if( rs.getString("StringInfo12") == null )
				this.StringInfo12 = null;
			else
				this.StringInfo12 = rs.getString("StringInfo12").trim();

			if( rs.getString("StringInfo13") == null )
				this.StringInfo13 = null;
			else
				this.StringInfo13 = rs.getString("StringInfo13").trim();

			if( rs.getString("StringInfo14") == null )
				this.StringInfo14 = null;
			else
				this.StringInfo14 = rs.getString("StringInfo14").trim();

			if( rs.getString("StringInfo15") == null )
				this.StringInfo15 = null;
			else
				this.StringInfo15 = rs.getString("StringInfo15").trim();

			this.DateInfo01 = rs.getDate("DateInfo01");
			this.DateInfo02 = rs.getDate("DateInfo02");
			this.DateInfo03 = rs.getDate("DateInfo03");
			this.DateInfo04 = rs.getDate("DateInfo04");
			this.DateInfo05 = rs.getDate("DateInfo05");
			this.DateInfo06 = rs.getDate("DateInfo06");
			this.DateInfo07 = rs.getDate("DateInfo07");
			this.DateInfo08 = rs.getDate("DateInfo08");
			this.DateInfo09 = rs.getDate("DateInfo09");
			this.DateInfo10 = rs.getDate("DateInfo10");
			this.NumInfo01 = rs.getDouble("NumInfo01");
			this.NumInfo02 = rs.getDouble("NumInfo02");
			this.NumInfo03 = rs.getInt("NumInfo03");
			this.NumInfo04 = rs.getInt("NumInfo04");
			this.NumInfo05 = rs.getInt("NumInfo05");
			if( rs.getString("BusinessNo01") == null )
				this.BusinessNo01 = null;
			else
				this.BusinessNo01 = rs.getString("BusinessNo01").trim();

			if( rs.getString("BusinessNo02") == null )
				this.BusinessNo02 = null;
			else
				this.BusinessNo02 = rs.getString("BusinessNo02").trim();

			if( rs.getString("BusinessNo03") == null )
				this.BusinessNo03 = null;
			else
				this.BusinessNo03 = rs.getString("BusinessNo03").trim();

			if( rs.getString("BusinessNo04") == null )
				this.BusinessNo04 = null;
			else
				this.BusinessNo04 = rs.getString("BusinessNo04").trim();

			if( rs.getString("BusinessNo05") == null )
				this.BusinessNo05 = null;
			else
				this.BusinessNo05 = rs.getString("BusinessNo05").trim();

			if( rs.getString("BusinessNo06") == null )
				this.BusinessNo06 = null;
			else
				this.BusinessNo06 = rs.getString("BusinessNo06").trim();

			if( rs.getString("BusinessNo07") == null )
				this.BusinessNo07 = null;
			else
				this.BusinessNo07 = rs.getString("BusinessNo07").trim();

			if( rs.getString("BusinessNo08") == null )
				this.BusinessNo08 = null;
			else
				this.BusinessNo08 = rs.getString("BusinessNo08").trim();

			if( rs.getString("BusinessNo09") == null )
				this.BusinessNo09 = null;
			else
				this.BusinessNo09 = rs.getString("BusinessNo09").trim();

			if( rs.getString("BusinessNo10") == null )
				this.BusinessNo10 = null;
			else
				this.BusinessNo10 = rs.getString("BusinessNo10").trim();

			if( rs.getString("TypeFlag01") == null )
				this.TypeFlag01 = null;
			else
				this.TypeFlag01 = rs.getString("TypeFlag01").trim();

			if( rs.getString("TypeFlag02") == null )
				this.TypeFlag02 = null;
			else
				this.TypeFlag02 = rs.getString("TypeFlag02").trim();

			if( rs.getString("TypeFlag03") == null )
				this.TypeFlag03 = null;
			else
				this.TypeFlag03 = rs.getString("TypeFlag03").trim();

			if( rs.getString("TypeFlag04") == null )
				this.TypeFlag04 = null;
			else
				this.TypeFlag04 = rs.getString("TypeFlag04").trim();

			if( rs.getString("TypeFlag05") == null )
				this.TypeFlag05 = null;
			else
				this.TypeFlag05 = rs.getString("TypeFlag05").trim();

			if( rs.getString("TypeFlag06") == null )
				this.TypeFlag06 = null;
			else
				this.TypeFlag06 = rs.getString("TypeFlag06").trim();

			if( rs.getString("TypeFlag07") == null )
				this.TypeFlag07 = null;
			else
				this.TypeFlag07 = rs.getString("TypeFlag07").trim();

			if( rs.getString("TypeFlag08") == null )
				this.TypeFlag08 = null;
			else
				this.TypeFlag08 = rs.getString("TypeFlag08").trim();

			if( rs.getString("TypeFlag09") == null )
				this.TypeFlag09 = null;
			else
				this.TypeFlag09 = rs.getString("TypeFlag09").trim();

			if( rs.getString("TypeFlag10") == null )
				this.TypeFlag10 = null;
			else
				this.TypeFlag10 = rs.getString("TypeFlag10").trim();

			if( rs.getString("RelationCode") == null )
				this.RelationCode = null;
			else
				this.RelationCode = rs.getString("RelationCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIAboriginalDataForDeal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataForDealSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIAboriginalDataForDealSchema getSchema()
	{
		FIAboriginalDataForDealSchema aFIAboriginalDataForDealSchema = new FIAboriginalDataForDealSchema();
		aFIAboriginalDataForDealSchema.setSchema(this);
		return aFIAboriginalDataForDealSchema;
	}

	public FIAboriginalDataForDealDB getDB()
	{
		FIAboriginalDataForDealDB aDBOper = new FIAboriginalDataForDealDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIAboriginalDataForDeal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AcquisitionID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DataBaseID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DataType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DataState)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(SumMoney));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( BusinessDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo01)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo02)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo03)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo04)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo05)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo06)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo07)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo08)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo09)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo10)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo11)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo12)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo13)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo14)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StringInfo15)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( DateInfo01 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( DateInfo02 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( DateInfo03 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( DateInfo04 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( DateInfo05 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( DateInfo06 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( DateInfo07 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( DateInfo08 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( DateInfo09 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( DateInfo10 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(NumInfo01));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(NumInfo02));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(NumInfo03));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(NumInfo04));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(NumInfo05));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo01)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo02)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo03)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo04)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo05)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo06)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo07)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo08)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo09)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo10)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TypeFlag01)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TypeFlag02)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TypeFlag03)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TypeFlag04)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TypeFlag05)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TypeFlag06)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TypeFlag07)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TypeFlag08)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TypeFlag09)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TypeFlag10)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RelationCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIAboriginalDataForDeal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AcquisitionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DataBaseID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DataType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DataState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BusinessCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SumMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			BusinessDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			BusinessNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StringInfo01 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StringInfo02 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StringInfo03 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StringInfo04 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StringInfo05 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StringInfo06 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StringInfo07 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			StringInfo08 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			StringInfo09 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			StringInfo10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			StringInfo11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			StringInfo12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			StringInfo13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			StringInfo14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			StringInfo15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			DateInfo01 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			DateInfo02 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			DateInfo03 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			DateInfo04 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			DateInfo05 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			DateInfo06 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			DateInfo07 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			DateInfo08 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34,SysConst.PACKAGESPILTER));
			DateInfo09 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			DateInfo10 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			NumInfo01 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			NumInfo02 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			NumInfo03= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).intValue();
			NumInfo04= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).intValue();
			NumInfo05= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).intValue();
			BusinessNo01 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			BusinessNo02 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			BusinessNo03 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			BusinessNo04 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			BusinessNo05 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			BusinessNo06 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			BusinessNo07 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			BusinessNo08 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			BusinessNo09 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			BusinessNo10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			TypeFlag01 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			TypeFlag02 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			TypeFlag03 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			TypeFlag04 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			TypeFlag05 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			TypeFlag06 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			TypeFlag07 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			TypeFlag08 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			TypeFlag09 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			TypeFlag10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			RelationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAboriginalDataForDealSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			return false;
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("AcquisitionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcquisitionID));
		}
		if (FCode.equalsIgnoreCase("DataBaseID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataBaseID));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("DataType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataType));
		}
		if (FCode.equalsIgnoreCase("DataState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataState));
		}
		if (FCode.equalsIgnoreCase("BusinessCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessCode));
		}
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumMoney));
		}
		if (FCode.equalsIgnoreCase("BusinessDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBusinessDate()));
		}
		if (FCode.equalsIgnoreCase("BusinessNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo));
		}
		if (FCode.equalsIgnoreCase("StringInfo01"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo01));
		}
		if (FCode.equalsIgnoreCase("StringInfo02"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo02));
		}
		if (FCode.equalsIgnoreCase("StringInfo03"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo03));
		}
		if (FCode.equalsIgnoreCase("StringInfo04"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo04));
		}
		if (FCode.equalsIgnoreCase("StringInfo05"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo05));
		}
		if (FCode.equalsIgnoreCase("StringInfo06"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo06));
		}
		if (FCode.equalsIgnoreCase("StringInfo07"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo07));
		}
		if (FCode.equalsIgnoreCase("StringInfo08"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo08));
		}
		if (FCode.equalsIgnoreCase("StringInfo09"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo09));
		}
		if (FCode.equalsIgnoreCase("StringInfo10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo10));
		}
		if (FCode.equalsIgnoreCase("StringInfo11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo11));
		}
		if (FCode.equalsIgnoreCase("StringInfo12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo12));
		}
		if (FCode.equalsIgnoreCase("StringInfo13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo13));
		}
		if (FCode.equalsIgnoreCase("StringInfo14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo14));
		}
		if (FCode.equalsIgnoreCase("StringInfo15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StringInfo15));
		}
		if (FCode.equalsIgnoreCase("DateInfo01"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo01()));
		}
		if (FCode.equalsIgnoreCase("DateInfo02"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo02()));
		}
		if (FCode.equalsIgnoreCase("DateInfo03"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo03()));
		}
		if (FCode.equalsIgnoreCase("DateInfo04"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo04()));
		}
		if (FCode.equalsIgnoreCase("DateInfo05"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo05()));
		}
		if (FCode.equalsIgnoreCase("DateInfo06"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo06()));
		}
		if (FCode.equalsIgnoreCase("DateInfo07"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo07()));
		}
		if (FCode.equalsIgnoreCase("DateInfo08"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo08()));
		}
		if (FCode.equalsIgnoreCase("DateInfo09"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo09()));
		}
		if (FCode.equalsIgnoreCase("DateInfo10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo10()));
		}
		if (FCode.equalsIgnoreCase("NumInfo01"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumInfo01));
		}
		if (FCode.equalsIgnoreCase("NumInfo02"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumInfo02));
		}
		if (FCode.equalsIgnoreCase("NumInfo03"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumInfo03));
		}
		if (FCode.equalsIgnoreCase("NumInfo04"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumInfo04));
		}
		if (FCode.equalsIgnoreCase("NumInfo05"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumInfo05));
		}
		if (FCode.equalsIgnoreCase("BusinessNo01"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo01));
		}
		if (FCode.equalsIgnoreCase("BusinessNo02"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo02));
		}
		if (FCode.equalsIgnoreCase("BusinessNo03"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo03));
		}
		if (FCode.equalsIgnoreCase("BusinessNo04"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo04));
		}
		if (FCode.equalsIgnoreCase("BusinessNo05"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo05));
		}
		if (FCode.equalsIgnoreCase("BusinessNo06"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo06));
		}
		if (FCode.equalsIgnoreCase("BusinessNo07"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo07));
		}
		if (FCode.equalsIgnoreCase("BusinessNo08"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo08));
		}
		if (FCode.equalsIgnoreCase("BusinessNo09"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo09));
		}
		if (FCode.equalsIgnoreCase("BusinessNo10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo10));
		}
		if (FCode.equalsIgnoreCase("TypeFlag01"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeFlag01));
		}
		if (FCode.equalsIgnoreCase("TypeFlag02"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeFlag02));
		}
		if (FCode.equalsIgnoreCase("TypeFlag03"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeFlag03));
		}
		if (FCode.equalsIgnoreCase("TypeFlag04"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeFlag04));
		}
		if (FCode.equalsIgnoreCase("TypeFlag05"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeFlag05));
		}
		if (FCode.equalsIgnoreCase("TypeFlag06"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeFlag06));
		}
		if (FCode.equalsIgnoreCase("TypeFlag07"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeFlag07));
		}
		if (FCode.equalsIgnoreCase("TypeFlag08"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeFlag08));
		}
		if (FCode.equalsIgnoreCase("TypeFlag09"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeFlag09));
		}
		if (FCode.equalsIgnoreCase("TypeFlag10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeFlag10));
		}
		if (FCode.equalsIgnoreCase("RelationCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationCode));
		}
		if (strReturn.equals(""))
		{
			strReturn = "null";
		}

		return strReturn;
	}


	/**
	* 取得Schema中指定索引值所对应的字段值
	* @param: nFieldIndex int 指定的字段索引值
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch(nFieldIndex) {
			case 0:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AcquisitionID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DataBaseID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DataType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DataState);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BusinessCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FeeCode);
				break;
			case 8:
				strFieldValue = String.valueOf(SumMoney);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBusinessDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StringInfo01);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StringInfo02);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StringInfo03);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(StringInfo04);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(StringInfo05);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(StringInfo06);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(StringInfo07);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(StringInfo08);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(StringInfo09);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(StringInfo10);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(StringInfo11);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(StringInfo12);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(StringInfo13);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(StringInfo14);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(StringInfo15);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo01()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo02()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo03()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo04()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo05()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo06()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo07()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo08()));
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo09()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDateInfo10()));
				break;
			case 36:
				strFieldValue = String.valueOf(NumInfo01);
				break;
			case 37:
				strFieldValue = String.valueOf(NumInfo02);
				break;
			case 38:
				strFieldValue = String.valueOf(NumInfo03);
				break;
			case 39:
				strFieldValue = String.valueOf(NumInfo04);
				break;
			case 40:
				strFieldValue = String.valueOf(NumInfo05);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo01);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo02);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo03);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo04);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo05);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo06);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo07);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo08);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo09);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo10);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(TypeFlag01);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(TypeFlag02);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(TypeFlag03);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(TypeFlag04);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(TypeFlag05);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(TypeFlag06);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(TypeFlag07);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(TypeFlag08);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(TypeFlag09);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(TypeFlag10);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(RelationCode);
				break;
			default:
				strFieldValue = "";
		};
		if( strFieldValue.equals("") ) {
			strFieldValue = "null";
		}
		return strFieldValue;
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("AcquisitionID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcquisitionID = FValue.trim();
			}
			else
				AcquisitionID = null;
		}
		if (FCode.equalsIgnoreCase("DataBaseID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataBaseID = FValue.trim();
			}
			else
				DataBaseID = null;
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("DataType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataType = FValue.trim();
			}
			else
				DataType = null;
		}
		if (FCode.equalsIgnoreCase("DataState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataState = FValue.trim();
			}
			else
				DataState = null;
		}
		if (FCode.equalsIgnoreCase("BusinessCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessCode = FValue.trim();
			}
			else
				BusinessCode = null;
		}
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCode = FValue.trim();
			}
			else
				FeeCode = null;
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("BusinessDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BusinessDate = fDate.getDate( FValue );
			}
			else
				BusinessDate = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo = FValue.trim();
			}
			else
				BusinessNo = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo01"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo01 = FValue.trim();
			}
			else
				StringInfo01 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo02"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo02 = FValue.trim();
			}
			else
				StringInfo02 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo03"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo03 = FValue.trim();
			}
			else
				StringInfo03 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo04"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo04 = FValue.trim();
			}
			else
				StringInfo04 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo05"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo05 = FValue.trim();
			}
			else
				StringInfo05 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo06"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo06 = FValue.trim();
			}
			else
				StringInfo06 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo07"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo07 = FValue.trim();
			}
			else
				StringInfo07 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo08"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo08 = FValue.trim();
			}
			else
				StringInfo08 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo09"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo09 = FValue.trim();
			}
			else
				StringInfo09 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo10 = FValue.trim();
			}
			else
				StringInfo10 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo11 = FValue.trim();
			}
			else
				StringInfo11 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo12 = FValue.trim();
			}
			else
				StringInfo12 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo13 = FValue.trim();
			}
			else
				StringInfo13 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo14 = FValue.trim();
			}
			else
				StringInfo14 = null;
		}
		if (FCode.equalsIgnoreCase("StringInfo15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StringInfo15 = FValue.trim();
			}
			else
				StringInfo15 = null;
		}
		if (FCode.equalsIgnoreCase("DateInfo01"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DateInfo01 = fDate.getDate( FValue );
			}
			else
				DateInfo01 = null;
		}
		if (FCode.equalsIgnoreCase("DateInfo02"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DateInfo02 = fDate.getDate( FValue );
			}
			else
				DateInfo02 = null;
		}
		if (FCode.equalsIgnoreCase("DateInfo03"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DateInfo03 = fDate.getDate( FValue );
			}
			else
				DateInfo03 = null;
		}
		if (FCode.equalsIgnoreCase("DateInfo04"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DateInfo04 = fDate.getDate( FValue );
			}
			else
				DateInfo04 = null;
		}
		if (FCode.equalsIgnoreCase("DateInfo05"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DateInfo05 = fDate.getDate( FValue );
			}
			else
				DateInfo05 = null;
		}
		if (FCode.equalsIgnoreCase("DateInfo06"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DateInfo06 = fDate.getDate( FValue );
			}
			else
				DateInfo06 = null;
		}
		if (FCode.equalsIgnoreCase("DateInfo07"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DateInfo07 = fDate.getDate( FValue );
			}
			else
				DateInfo07 = null;
		}
		if (FCode.equalsIgnoreCase("DateInfo08"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DateInfo08 = fDate.getDate( FValue );
			}
			else
				DateInfo08 = null;
		}
		if (FCode.equalsIgnoreCase("DateInfo09"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DateInfo09 = fDate.getDate( FValue );
			}
			else
				DateInfo09 = null;
		}
		if (FCode.equalsIgnoreCase("DateInfo10"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DateInfo10 = fDate.getDate( FValue );
			}
			else
				DateInfo10 = null;
		}
		if (FCode.equalsIgnoreCase("NumInfo01"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumInfo01 = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumInfo02"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumInfo02 = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumInfo03"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				NumInfo03 = i;
			}
		}
		if (FCode.equalsIgnoreCase("NumInfo04"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				NumInfo04 = i;
			}
		}
		if (FCode.equalsIgnoreCase("NumInfo05"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				NumInfo05 = i;
			}
		}
		if (FCode.equalsIgnoreCase("BusinessNo01"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo01 = FValue.trim();
			}
			else
				BusinessNo01 = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo02"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo02 = FValue.trim();
			}
			else
				BusinessNo02 = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo03"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo03 = FValue.trim();
			}
			else
				BusinessNo03 = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo04"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo04 = FValue.trim();
			}
			else
				BusinessNo04 = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo05"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo05 = FValue.trim();
			}
			else
				BusinessNo05 = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo06"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo06 = FValue.trim();
			}
			else
				BusinessNo06 = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo07"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo07 = FValue.trim();
			}
			else
				BusinessNo07 = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo08"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo08 = FValue.trim();
			}
			else
				BusinessNo08 = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo09"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo09 = FValue.trim();
			}
			else
				BusinessNo09 = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo10 = FValue.trim();
			}
			else
				BusinessNo10 = null;
		}
		if (FCode.equalsIgnoreCase("TypeFlag01"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeFlag01 = FValue.trim();
			}
			else
				TypeFlag01 = null;
		}
		if (FCode.equalsIgnoreCase("TypeFlag02"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeFlag02 = FValue.trim();
			}
			else
				TypeFlag02 = null;
		}
		if (FCode.equalsIgnoreCase("TypeFlag03"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeFlag03 = FValue.trim();
			}
			else
				TypeFlag03 = null;
		}
		if (FCode.equalsIgnoreCase("TypeFlag04"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeFlag04 = FValue.trim();
			}
			else
				TypeFlag04 = null;
		}
		if (FCode.equalsIgnoreCase("TypeFlag05"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeFlag05 = FValue.trim();
			}
			else
				TypeFlag05 = null;
		}
		if (FCode.equalsIgnoreCase("TypeFlag06"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeFlag06 = FValue.trim();
			}
			else
				TypeFlag06 = null;
		}
		if (FCode.equalsIgnoreCase("TypeFlag07"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeFlag07 = FValue.trim();
			}
			else
				TypeFlag07 = null;
		}
		if (FCode.equalsIgnoreCase("TypeFlag08"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeFlag08 = FValue.trim();
			}
			else
				TypeFlag08 = null;
		}
		if (FCode.equalsIgnoreCase("TypeFlag09"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeFlag09 = FValue.trim();
			}
			else
				TypeFlag09 = null;
		}
		if (FCode.equalsIgnoreCase("TypeFlag10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeFlag10 = FValue.trim();
			}
			else
				TypeFlag10 = null;
		}
		if (FCode.equalsIgnoreCase("RelationCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationCode = FValue.trim();
			}
			else
				RelationCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIAboriginalDataForDealSchema other = (FIAboriginalDataForDealSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& AcquisitionID.equals(other.getAcquisitionID())
			&& DataBaseID.equals(other.getDataBaseID())
			&& BatchNo.equals(other.getBatchNo())
			&& DataType.equals(other.getDataType())
			&& DataState.equals(other.getDataState())
			&& BusinessCode.equals(other.getBusinessCode())
			&& FeeCode.equals(other.getFeeCode())
			&& SumMoney == other.getSumMoney()
			&& fDate.getString(BusinessDate).equals(other.getBusinessDate())
			&& BusinessNo.equals(other.getBusinessNo())
			&& StringInfo01.equals(other.getStringInfo01())
			&& StringInfo02.equals(other.getStringInfo02())
			&& StringInfo03.equals(other.getStringInfo03())
			&& StringInfo04.equals(other.getStringInfo04())
			&& StringInfo05.equals(other.getStringInfo05())
			&& StringInfo06.equals(other.getStringInfo06())
			&& StringInfo07.equals(other.getStringInfo07())
			&& StringInfo08.equals(other.getStringInfo08())
			&& StringInfo09.equals(other.getStringInfo09())
			&& StringInfo10.equals(other.getStringInfo10())
			&& StringInfo11.equals(other.getStringInfo11())
			&& StringInfo12.equals(other.getStringInfo12())
			&& StringInfo13.equals(other.getStringInfo13())
			&& StringInfo14.equals(other.getStringInfo14())
			&& StringInfo15.equals(other.getStringInfo15())
			&& fDate.getString(DateInfo01).equals(other.getDateInfo01())
			&& fDate.getString(DateInfo02).equals(other.getDateInfo02())
			&& fDate.getString(DateInfo03).equals(other.getDateInfo03())
			&& fDate.getString(DateInfo04).equals(other.getDateInfo04())
			&& fDate.getString(DateInfo05).equals(other.getDateInfo05())
			&& fDate.getString(DateInfo06).equals(other.getDateInfo06())
			&& fDate.getString(DateInfo07).equals(other.getDateInfo07())
			&& fDate.getString(DateInfo08).equals(other.getDateInfo08())
			&& fDate.getString(DateInfo09).equals(other.getDateInfo09())
			&& fDate.getString(DateInfo10).equals(other.getDateInfo10())
			&& NumInfo01 == other.getNumInfo01()
			&& NumInfo02 == other.getNumInfo02()
			&& NumInfo03 == other.getNumInfo03()
			&& NumInfo04 == other.getNumInfo04()
			&& NumInfo05 == other.getNumInfo05()
			&& BusinessNo01.equals(other.getBusinessNo01())
			&& BusinessNo02.equals(other.getBusinessNo02())
			&& BusinessNo03.equals(other.getBusinessNo03())
			&& BusinessNo04.equals(other.getBusinessNo04())
			&& BusinessNo05.equals(other.getBusinessNo05())
			&& BusinessNo06.equals(other.getBusinessNo06())
			&& BusinessNo07.equals(other.getBusinessNo07())
			&& BusinessNo08.equals(other.getBusinessNo08())
			&& BusinessNo09.equals(other.getBusinessNo09())
			&& BusinessNo10.equals(other.getBusinessNo10())
			&& TypeFlag01.equals(other.getTypeFlag01())
			&& TypeFlag02.equals(other.getTypeFlag02())
			&& TypeFlag03.equals(other.getTypeFlag03())
			&& TypeFlag04.equals(other.getTypeFlag04())
			&& TypeFlag05.equals(other.getTypeFlag05())
			&& TypeFlag06.equals(other.getTypeFlag06())
			&& TypeFlag07.equals(other.getTypeFlag07())
			&& TypeFlag08.equals(other.getTypeFlag08())
			&& TypeFlag09.equals(other.getTypeFlag09())
			&& TypeFlag10.equals(other.getTypeFlag10())
			&& RelationCode.equals(other.getRelationCode());
	}

	/**
	* 取得Schema拥有字段的数量
       * @return: int
	**/
	public int getFieldCount()
	{
 		return FIELDNUM;
	}

	/**
	* 取得Schema中指定字段名所对应的索引值
	* 如果没有对应的字段，返回-1
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldIndex(String strFieldName)
	{
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("AcquisitionID") ) {
			return 1;
		}
		if( strFieldName.equals("DataBaseID") ) {
			return 2;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 3;
		}
		if( strFieldName.equals("DataType") ) {
			return 4;
		}
		if( strFieldName.equals("DataState") ) {
			return 5;
		}
		if( strFieldName.equals("BusinessCode") ) {
			return 6;
		}
		if( strFieldName.equals("FeeCode") ) {
			return 7;
		}
		if( strFieldName.equals("SumMoney") ) {
			return 8;
		}
		if( strFieldName.equals("BusinessDate") ) {
			return 9;
		}
		if( strFieldName.equals("BusinessNo") ) {
			return 10;
		}
		if( strFieldName.equals("StringInfo01") ) {
			return 11;
		}
		if( strFieldName.equals("StringInfo02") ) {
			return 12;
		}
		if( strFieldName.equals("StringInfo03") ) {
			return 13;
		}
		if( strFieldName.equals("StringInfo04") ) {
			return 14;
		}
		if( strFieldName.equals("StringInfo05") ) {
			return 15;
		}
		if( strFieldName.equals("StringInfo06") ) {
			return 16;
		}
		if( strFieldName.equals("StringInfo07") ) {
			return 17;
		}
		if( strFieldName.equals("StringInfo08") ) {
			return 18;
		}
		if( strFieldName.equals("StringInfo09") ) {
			return 19;
		}
		if( strFieldName.equals("StringInfo10") ) {
			return 20;
		}
		if( strFieldName.equals("StringInfo11") ) {
			return 21;
		}
		if( strFieldName.equals("StringInfo12") ) {
			return 22;
		}
		if( strFieldName.equals("StringInfo13") ) {
			return 23;
		}
		if( strFieldName.equals("StringInfo14") ) {
			return 24;
		}
		if( strFieldName.equals("StringInfo15") ) {
			return 25;
		}
		if( strFieldName.equals("DateInfo01") ) {
			return 26;
		}
		if( strFieldName.equals("DateInfo02") ) {
			return 27;
		}
		if( strFieldName.equals("DateInfo03") ) {
			return 28;
		}
		if( strFieldName.equals("DateInfo04") ) {
			return 29;
		}
		if( strFieldName.equals("DateInfo05") ) {
			return 30;
		}
		if( strFieldName.equals("DateInfo06") ) {
			return 31;
		}
		if( strFieldName.equals("DateInfo07") ) {
			return 32;
		}
		if( strFieldName.equals("DateInfo08") ) {
			return 33;
		}
		if( strFieldName.equals("DateInfo09") ) {
			return 34;
		}
		if( strFieldName.equals("DateInfo10") ) {
			return 35;
		}
		if( strFieldName.equals("NumInfo01") ) {
			return 36;
		}
		if( strFieldName.equals("NumInfo02") ) {
			return 37;
		}
		if( strFieldName.equals("NumInfo03") ) {
			return 38;
		}
		if( strFieldName.equals("NumInfo04") ) {
			return 39;
		}
		if( strFieldName.equals("NumInfo05") ) {
			return 40;
		}
		if( strFieldName.equals("BusinessNo01") ) {
			return 41;
		}
		if( strFieldName.equals("BusinessNo02") ) {
			return 42;
		}
		if( strFieldName.equals("BusinessNo03") ) {
			return 43;
		}
		if( strFieldName.equals("BusinessNo04") ) {
			return 44;
		}
		if( strFieldName.equals("BusinessNo05") ) {
			return 45;
		}
		if( strFieldName.equals("BusinessNo06") ) {
			return 46;
		}
		if( strFieldName.equals("BusinessNo07") ) {
			return 47;
		}
		if( strFieldName.equals("BusinessNo08") ) {
			return 48;
		}
		if( strFieldName.equals("BusinessNo09") ) {
			return 49;
		}
		if( strFieldName.equals("BusinessNo10") ) {
			return 50;
		}
		if( strFieldName.equals("TypeFlag01") ) {
			return 51;
		}
		if( strFieldName.equals("TypeFlag02") ) {
			return 52;
		}
		if( strFieldName.equals("TypeFlag03") ) {
			return 53;
		}
		if( strFieldName.equals("TypeFlag04") ) {
			return 54;
		}
		if( strFieldName.equals("TypeFlag05") ) {
			return 55;
		}
		if( strFieldName.equals("TypeFlag06") ) {
			return 56;
		}
		if( strFieldName.equals("TypeFlag07") ) {
			return 57;
		}
		if( strFieldName.equals("TypeFlag08") ) {
			return 58;
		}
		if( strFieldName.equals("TypeFlag09") ) {
			return 59;
		}
		if( strFieldName.equals("TypeFlag10") ) {
			return 60;
		}
		if( strFieldName.equals("RelationCode") ) {
			return 61;
		}
		return -1;
	}

	/**
	* 取得Schema中指定索引值所对应的字段名
	* 如果没有对应的字段，返回""
       * @param: nFieldIndex int
       * @return: String
	**/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch(nFieldIndex) {
			case 0:
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "AcquisitionID";
				break;
			case 2:
				strFieldName = "DataBaseID";
				break;
			case 3:
				strFieldName = "BatchNo";
				break;
			case 4:
				strFieldName = "DataType";
				break;
			case 5:
				strFieldName = "DataState";
				break;
			case 6:
				strFieldName = "BusinessCode";
				break;
			case 7:
				strFieldName = "FeeCode";
				break;
			case 8:
				strFieldName = "SumMoney";
				break;
			case 9:
				strFieldName = "BusinessDate";
				break;
			case 10:
				strFieldName = "BusinessNo";
				break;
			case 11:
				strFieldName = "StringInfo01";
				break;
			case 12:
				strFieldName = "StringInfo02";
				break;
			case 13:
				strFieldName = "StringInfo03";
				break;
			case 14:
				strFieldName = "StringInfo04";
				break;
			case 15:
				strFieldName = "StringInfo05";
				break;
			case 16:
				strFieldName = "StringInfo06";
				break;
			case 17:
				strFieldName = "StringInfo07";
				break;
			case 18:
				strFieldName = "StringInfo08";
				break;
			case 19:
				strFieldName = "StringInfo09";
				break;
			case 20:
				strFieldName = "StringInfo10";
				break;
			case 21:
				strFieldName = "StringInfo11";
				break;
			case 22:
				strFieldName = "StringInfo12";
				break;
			case 23:
				strFieldName = "StringInfo13";
				break;
			case 24:
				strFieldName = "StringInfo14";
				break;
			case 25:
				strFieldName = "StringInfo15";
				break;
			case 26:
				strFieldName = "DateInfo01";
				break;
			case 27:
				strFieldName = "DateInfo02";
				break;
			case 28:
				strFieldName = "DateInfo03";
				break;
			case 29:
				strFieldName = "DateInfo04";
				break;
			case 30:
				strFieldName = "DateInfo05";
				break;
			case 31:
				strFieldName = "DateInfo06";
				break;
			case 32:
				strFieldName = "DateInfo07";
				break;
			case 33:
				strFieldName = "DateInfo08";
				break;
			case 34:
				strFieldName = "DateInfo09";
				break;
			case 35:
				strFieldName = "DateInfo10";
				break;
			case 36:
				strFieldName = "NumInfo01";
				break;
			case 37:
				strFieldName = "NumInfo02";
				break;
			case 38:
				strFieldName = "NumInfo03";
				break;
			case 39:
				strFieldName = "NumInfo04";
				break;
			case 40:
				strFieldName = "NumInfo05";
				break;
			case 41:
				strFieldName = "BusinessNo01";
				break;
			case 42:
				strFieldName = "BusinessNo02";
				break;
			case 43:
				strFieldName = "BusinessNo03";
				break;
			case 44:
				strFieldName = "BusinessNo04";
				break;
			case 45:
				strFieldName = "BusinessNo05";
				break;
			case 46:
				strFieldName = "BusinessNo06";
				break;
			case 47:
				strFieldName = "BusinessNo07";
				break;
			case 48:
				strFieldName = "BusinessNo08";
				break;
			case 49:
				strFieldName = "BusinessNo09";
				break;
			case 50:
				strFieldName = "BusinessNo10";
				break;
			case 51:
				strFieldName = "TypeFlag01";
				break;
			case 52:
				strFieldName = "TypeFlag02";
				break;
			case 53:
				strFieldName = "TypeFlag03";
				break;
			case 54:
				strFieldName = "TypeFlag04";
				break;
			case 55:
				strFieldName = "TypeFlag05";
				break;
			case 56:
				strFieldName = "TypeFlag06";
				break;
			case 57:
				strFieldName = "TypeFlag07";
				break;
			case 58:
				strFieldName = "TypeFlag08";
				break;
			case 59:
				strFieldName = "TypeFlag09";
				break;
			case 60:
				strFieldName = "TypeFlag10";
				break;
			case 61:
				strFieldName = "RelationCode";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	* 取得Schema中指定字段名所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldType(String strFieldName)
	{
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcquisitionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataBaseID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BusinessDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BusinessNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo01") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo02") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo03") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo04") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo05") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo06") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo07") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo08") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo09") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StringInfo15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DateInfo01") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateInfo02") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateInfo03") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateInfo04") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateInfo05") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateInfo06") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateInfo07") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateInfo08") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateInfo09") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateInfo10") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("NumInfo01") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumInfo02") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumInfo03") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("NumInfo04") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("NumInfo05") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BusinessNo01") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo02") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo03") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo04") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo05") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo06") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo07") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo08") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo09") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeFlag01") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeFlag02") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeFlag03") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeFlag04") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeFlag05") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeFlag06") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeFlag07") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeFlag08") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeFlag09") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeFlag10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationCode") ) {
			return Schema.TYPE_STRING;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	* 取得Schema中指定索引值所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
       * @param: nFieldIndex int
       * @return: int
	**/
	public int getFieldType(int nFieldIndex)
	{
		int nFieldType = Schema.TYPE_NOFOUND;
		switch(nFieldIndex) {
			case 0:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 38:
				nFieldType = Schema.TYPE_INT;
				break;
			case 39:
				nFieldType = Schema.TYPE_INT;
				break;
			case 40:
				nFieldType = Schema.TYPE_INT;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 58:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
