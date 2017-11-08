/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.Es_Doc_LogSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: Es_Doc_LogSet </p>
 * <p>Description: Es_Doc_LogSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: bug4
 */
public class Es_Doc_LogSet extends SchemaSet
{
	// @Method
	public boolean add(Es_Doc_LogSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(Es_Doc_LogSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(Es_Doc_LogSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public Es_Doc_LogSchema get(int index)
	{
		Es_Doc_LogSchema tSchema = (Es_Doc_LogSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, Es_Doc_LogSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(Es_Doc_LogSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpEs_Doc_Log描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			Es_Doc_LogSchema aSchema = this.get(i);
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
			Es_Doc_LogSchema aSchema = new Es_Doc_LogSchema();
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
		Es_Doc_LogSchema tSchema = new Es_Doc_LogSchema();
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
