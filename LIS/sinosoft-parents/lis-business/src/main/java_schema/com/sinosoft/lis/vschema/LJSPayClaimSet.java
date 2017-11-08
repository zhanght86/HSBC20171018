/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LJSPayClaimSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LJSPayClaimSet </p>
 * <p>Description: LJSPayClaimSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LJSPayClaimSet extends SchemaSet
{
	// @Method
	public boolean add(LJSPayClaimSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LJSPayClaimSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LJSPayClaimSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LJSPayClaimSchema get(int index)
	{
		LJSPayClaimSchema tSchema = (LJSPayClaimSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LJSPayClaimSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LJSPayClaimSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJSPayClaim描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LJSPayClaimSchema aSchema = this.get(i);
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
			LJSPayClaimSchema aSchema = new LJSPayClaimSchema();
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
		LJSPayClaimSchema tSchema = new LJSPayClaimSchema();
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
