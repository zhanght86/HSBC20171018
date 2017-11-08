/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCPrem_1Schema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCPrem_1Set </p>
 * <p>Description: LCPrem_1SchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCPrem_1Set extends SchemaSet
{
private static Logger logger = Logger.getLogger(LCPrem_1Set.class);

	// @Method
	public boolean add(LCPrem_1Schema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LCPrem_1Set aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LCPrem_1Schema aSchema)
	{
		return super.remove(aSchema);
	}

	public LCPrem_1Schema get(int index)
	{
		LCPrem_1Schema tSchema = (LCPrem_1Schema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LCPrem_1Schema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LCPrem_1Set aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPrem_1描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LCPrem_1Schema aSchema = this.get(i);
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
			LCPrem_1Schema aSchema = new LCPrem_1Schema();
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
		LCPrem_1Schema tSchema = new LCPrem_1Schema();
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
