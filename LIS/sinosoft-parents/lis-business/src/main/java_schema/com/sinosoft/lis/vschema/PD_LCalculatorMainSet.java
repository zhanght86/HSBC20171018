/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.PD_LCalculatorMainSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LCalculatorMainSet </p>
 * <p>Description: PD_LCalculatorMainSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class PD_LCalculatorMainSet extends SchemaSet
{
	// @Method
	public boolean add(PD_LCalculatorMainSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(PD_LCalculatorMainSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(PD_LCalculatorMainSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public PD_LCalculatorMainSchema get(int index)
	{
		PD_LCalculatorMainSchema tSchema = (PD_LCalculatorMainSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, PD_LCalculatorMainSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(PD_LCalculatorMainSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCalculatorMain描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			PD_LCalculatorMainSchema aSchema = this.get(i);
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
			PD_LCalculatorMainSchema aSchema = new PD_LCalculatorMainSchema();
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
		PD_LCalculatorMainSchema tSchema = new PD_LCalculatorMainSchema();
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
