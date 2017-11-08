/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LPrtTempleteSchema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * ClassName: LPrtTempleteSet
 * </p>
 * <p>
 * Description: LPrtTempleteSchemaSet类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-01-24
 */
public class LPrtTempleteSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(LPrtTempleteSet.class);

	// @Method
	public boolean add(LPrtTempleteSchema cSchema)
	{
		return super.add(cSchema);
	}

	public boolean add(LPrtTempleteSet cSet)
	{
		return super.add(cSet);
	}

	public boolean remove(LPrtTempleteSchema cSchema)
	{
		return super.remove(cSchema);
	}

	public LPrtTempleteSchema get(int index)
	{
		LPrtTempleteSchema tSchema = (LPrtTempleteSchema) super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LPrtTempleteSchema cSchema)
	{
		return super.set(index, cSchema);
	}

	public boolean set(LPrtTempleteSet cSet)
	{
		return super.set(cSet);
	}

	/**
	 * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}
	 * /dataStructure/tb.html#PrpLPrtTemplete描述/A>表字段
	 * @return: String 返回打包后字符串
	 */
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LPrtTempleteSchema aSchema = this.get(i);
			strReturn.append(aSchema.encode());
			if (i != n)
				strReturn.append(SysConst.RECORDSPLITER);
		}
		return strReturn.toString();
	}

	/**
	 * 数据解包
	 * @param: str String 打包后字符串
	 * @return: boolean
	 */
	public boolean decode(String str)
	{
		int nBeginPos = 0;
		int nEndPos = str.indexOf('^');
		this.clear();
		while (nEndPos != -1)
		{
			LPrtTempleteSchema aSchema = new LPrtTempleteSchema();
			if (aSchema.decode(str.substring(nBeginPos, nEndPos)))
			{
				this.add(aSchema);
				nBeginPos = nEndPos + 1;
				nEndPos = str.indexOf('^', nEndPos + 1);
			}
			else
			{
				// @@错误处理
				this.mErrors.copyAllErrors(aSchema.mErrors);
				return false;
			}
		}
		LPrtTempleteSchema tSchema = new LPrtTempleteSchema();
		if (tSchema.decode(str.substring(nBeginPos)))
		{
			this.add(tSchema);
			return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tSchema.mErrors);
			return false;
		}
	}
}
