/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.PD_LMDutyGetAlive_LibSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMDutyGetAlive_LibSet </p>
 * <p>Description: PD_LMDutyGetAlive_LibSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMDutyGetAlive_LibSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(PD_LMDutyGetAlive_LibSet.class);

	// @Method
	public boolean add(PD_LMDutyGetAlive_LibSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(PD_LMDutyGetAlive_LibSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(PD_LMDutyGetAlive_LibSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public PD_LMDutyGetAlive_LibSchema get(int index)
	{
		PD_LMDutyGetAlive_LibSchema tSchema = (PD_LMDutyGetAlive_LibSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, PD_LMDutyGetAlive_LibSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(PD_LMDutyGetAlive_LibSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGetAlive_Lib描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			PD_LMDutyGetAlive_LibSchema aSchema = this.get(i);
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
			PD_LMDutyGetAlive_LibSchema aSchema = new PD_LMDutyGetAlive_LibSchema();
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
		PD_LMDutyGetAlive_LibSchema tSchema = new PD_LMDutyGetAlive_LibSchema();
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
