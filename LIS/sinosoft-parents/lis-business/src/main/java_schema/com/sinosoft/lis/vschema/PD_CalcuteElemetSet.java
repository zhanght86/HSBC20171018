/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.PD_CalcuteElemetSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_CalcuteElemetSet </p>
 * <p>Description: PD_CalcuteElemetSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 中银产品定义平台
 * @CreateDate：2011-11-11
 */
public class PD_CalcuteElemetSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(PD_CalcuteElemetSet.class);

	// @Method
	public boolean add(PD_CalcuteElemetSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(PD_CalcuteElemetSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(PD_CalcuteElemetSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public PD_CalcuteElemetSchema get(int index)
	{
		PD_CalcuteElemetSchema tSchema = (PD_CalcuteElemetSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, PD_CalcuteElemetSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(PD_CalcuteElemetSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_CalcuteElemet描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			PD_CalcuteElemetSchema aSchema = this.get(i);
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
			PD_CalcuteElemetSchema aSchema = new PD_CalcuteElemetSchema();
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
		PD_CalcuteElemetSchema tSchema = new PD_CalcuteElemetSchema();
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
