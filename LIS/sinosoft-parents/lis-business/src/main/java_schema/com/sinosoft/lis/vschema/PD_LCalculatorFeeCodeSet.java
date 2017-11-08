/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.PD_LCalculatorFeeCodeSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LCalculatorFeeCodeSet </p>
 * <p>Description: PD_LCalculatorFeeCodeSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class PD_LCalculatorFeeCodeSet extends SchemaSet
{
	// @Method
	public boolean add(PD_LCalculatorFeeCodeSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(PD_LCalculatorFeeCodeSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(PD_LCalculatorFeeCodeSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public PD_LCalculatorFeeCodeSchema get(int index)
	{
		PD_LCalculatorFeeCodeSchema tSchema = (PD_LCalculatorFeeCodeSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, PD_LCalculatorFeeCodeSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(PD_LCalculatorFeeCodeSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCalculatorFeeCode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			PD_LCalculatorFeeCodeSchema aSchema = this.get(i);
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
			PD_LCalculatorFeeCodeSchema aSchema = new PD_LCalculatorFeeCodeSchema();
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
		PD_LCalculatorFeeCodeSchema tSchema = new PD_LCalculatorFeeCodeSchema();
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
