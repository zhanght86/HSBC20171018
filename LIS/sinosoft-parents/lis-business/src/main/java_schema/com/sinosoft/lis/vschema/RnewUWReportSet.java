/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.RnewUWReportSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RnewUWReportSet </p>
 * <p>Description: RnewUWReportSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 续期续保二核
 */
public class RnewUWReportSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(RnewUWReportSet.class);

	// @Method
	public boolean add(RnewUWReportSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(RnewUWReportSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(RnewUWReportSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public RnewUWReportSchema get(int index)
	{
		RnewUWReportSchema tSchema = (RnewUWReportSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, RnewUWReportSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(RnewUWReportSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRnewUWReport描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			RnewUWReportSchema aSchema = this.get(i);
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
			RnewUWReportSchema aSchema = new RnewUWReportSchema();
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
		RnewUWReportSchema tSchema = new RnewUWReportSchema();
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
