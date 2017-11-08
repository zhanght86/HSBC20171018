/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.PD_LMRiskPremRateSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMRiskPremRateSet </p>
 * <p>Description: PD_LMRiskPremRateSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2010-09-12
 */
public class PD_LMRiskPremRateSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(PD_LMRiskPremRateSet.class);

	// @Method
	public boolean add(PD_LMRiskPremRateSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(PD_LMRiskPremRateSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(PD_LMRiskPremRateSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public PD_LMRiskPremRateSchema get(int index)
	{
		PD_LMRiskPremRateSchema tSchema = (PD_LMRiskPremRateSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, PD_LMRiskPremRateSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(PD_LMRiskPremRateSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskPremRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			PD_LMRiskPremRateSchema aSchema = this.get(i);
			strReturn.append(aSchema.encode());
			if( i != n ) strReturn.append(SysConst.RECORDSPLITER);
		}

		return strReturn.toString();
	}

	/**
	* 数据解包
	* @param: str String 打包后字符串
	* @return: boolean
	**/
	public boolean decode( String str )
	{
		int nBeginPos = 0;
		int nEndPos = str.indexOf('^');
		this.clear();

		while( nEndPos != -1 )
		{
			PD_LMRiskPremRateSchema aSchema = new PD_LMRiskPremRateSchema();
			if(aSchema.decode(str.substring(nBeginPos, nEndPos)))
			{
			this.add(aSchema);
			nBeginPos = nEndPos + 1;
			nEndPos = str.indexOf('^', nEndPos + 1);
			}
			else
			{
				// @@错误处理
				this.mErrors.copyAllErrors( aSchema.mErrors );
				return false;
			}
		}
		PD_LMRiskPremRateSchema tSchema = new PD_LMRiskPremRateSchema();
		if(tSchema.decode(str.substring(nBeginPos)))
		{
		this.add(tSchema);
		return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors( tSchema.mErrors );
			return false;
		}
	}

}
