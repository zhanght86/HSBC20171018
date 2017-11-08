/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LPrtCodeSchema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * ClassName: LPrtCodeSet
 * </p>
 * <p>
 * Description: LPrtCodeSchemaSet类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-03-01
 */
public class LPrtCodeSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(LPrtCodeSet.class);

	// @Method
	public boolean add(LPrtCodeSchema cSchema)
	{
		return super.add(cSchema);
	}

	public boolean add(LPrtCodeSet cSet)
	{
		return super.add(cSet);
	}

	public boolean remove(LPrtCodeSchema cSchema)
	{
		return super.remove(cSchema);
	}

	public LPrtCodeSchema get(int index)
	{
		LPrtCodeSchema tSchema = (LPrtCodeSchema) super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LPrtCodeSchema cSchema)
	{
		return super.set(index, cSchema);
	}

	public boolean set(LPrtCodeSet cSet)
	{
		return super.set(cSet);
	}

	/**
	 * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}
	 * /dataStructure/tb.html#PrpLPrtCode描述/A>表字段
	 * @return: String 返回打包后字符串
	 */
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LPrtCodeSchema aSchema = this.get(i);
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
			LPrtCodeSchema aSchema = new LPrtCodeSchema();
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
		LPrtCodeSchema tSchema = new LPrtCodeSchema();
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
