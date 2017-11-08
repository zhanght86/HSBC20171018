/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LDMsgInfo_BOMSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDMsgInfo_BOMSet </p>
 * <p>Description: LDMsgInfo_BOMSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 规则引擎多语言方案
 */
public class LDMsgInfo_BOMSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(LDMsgInfo_BOMSet.class);

	// @Method
	public boolean add(LDMsgInfo_BOMSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LDMsgInfo_BOMSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LDMsgInfo_BOMSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LDMsgInfo_BOMSchema get(int index)
	{
		LDMsgInfo_BOMSchema tSchema = (LDMsgInfo_BOMSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LDMsgInfo_BOMSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LDMsgInfo_BOMSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMsgInfo_BOM描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LDMsgInfo_BOMSchema aSchema = this.get(i);
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
			LDMsgInfo_BOMSchema aSchema = new LDMsgInfo_BOMSchema();
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
		LDMsgInfo_BOMSchema tSchema = new LDMsgInfo_BOMSchema();
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
