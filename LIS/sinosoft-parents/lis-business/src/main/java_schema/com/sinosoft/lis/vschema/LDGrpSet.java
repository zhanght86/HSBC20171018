/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDGrpSet </p>
 * <p>Description: LDGrpSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LDGrpSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(LDGrpSet.class);

	// @Method
	public boolean add(LDGrpSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LDGrpSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LDGrpSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LDGrpSchema get(int index)
	{
		LDGrpSchema tSchema = (LDGrpSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LDGrpSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LDGrpSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDGrp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LDGrpSchema aSchema = this.get(i);
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
			LDGrpSchema aSchema = new LDGrpSchema();
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
		LDGrpSchema tSchema = new LDGrpSchema();
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
