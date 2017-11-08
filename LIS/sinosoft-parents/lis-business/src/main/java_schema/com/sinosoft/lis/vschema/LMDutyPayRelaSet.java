

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LMDutyPayRelaSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMDutyPayRelaSet </p>
 * <p>Description: LMDutyPayRelaSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyPayRelaSet extends SchemaSet
{
	// @Method
	public boolean add(LMDutyPayRelaSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LMDutyPayRelaSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LMDutyPayRelaSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LMDutyPayRelaSchema get(int index)
	{
		LMDutyPayRelaSchema tSchema = (LMDutyPayRelaSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LMDutyPayRelaSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LMDutyPayRelaSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyPayRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LMDutyPayRelaSchema aSchema = this.get(i);
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
			LMDutyPayRelaSchema aSchema = new LMDutyPayRelaSchema();
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
		LMDutyPayRelaSchema tSchema = new LMDutyPayRelaSchema();
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
