/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCCustomerImpartParamsSet </p>
 * <p>Description: LCCustomerImpartParamsSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCCustomerImpartParamsSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(LCCustomerImpartParamsSet.class);

	// @Method
	public boolean add(LCCustomerImpartParamsSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LCCustomerImpartParamsSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LCCustomerImpartParamsSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LCCustomerImpartParamsSchema get(int index)
	{
		LCCustomerImpartParamsSchema tSchema = (LCCustomerImpartParamsSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LCCustomerImpartParamsSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LCCustomerImpartParamsSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCCustomerImpartParams描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LCCustomerImpartParamsSchema aSchema = this.get(i);
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
			LCCustomerImpartParamsSchema aSchema = new LCCustomerImpartParamsSchema();
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
		LCCustomerImpartParamsSchema tSchema = new LCCustomerImpartParamsSchema();
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
