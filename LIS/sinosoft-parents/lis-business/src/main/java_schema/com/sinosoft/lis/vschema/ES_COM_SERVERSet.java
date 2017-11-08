/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.ES_COM_SERVERSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: ES_COM_SERVERSet </p>
 * <p>Description: ES_COM_SERVERSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 扫描系统
 */
public class ES_COM_SERVERSet extends SchemaSet
{
	// @Method
	public boolean add(ES_COM_SERVERSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(ES_COM_SERVERSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(ES_COM_SERVERSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public ES_COM_SERVERSchema get(int index)
	{
		ES_COM_SERVERSchema tSchema = (ES_COM_SERVERSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, ES_COM_SERVERSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(ES_COM_SERVERSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_COM_SERVER描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			ES_COM_SERVERSchema aSchema = this.get(i);
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
			ES_COM_SERVERSchema aSchema = new ES_COM_SERVERSchema();
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
		ES_COM_SERVERSchema tSchema = new ES_COM_SERVERSchema();
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
