/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LQRelaInfoSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LQRelaInfoSet </p>
 * <p>Description: LQRelaInfoSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 报价关系表
 * @CreateDate：2010-09-04
 */
public class LQRelaInfoSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(LQRelaInfoSet.class);

	// @Method
	public boolean add(LQRelaInfoSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LQRelaInfoSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LQRelaInfoSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LQRelaInfoSchema get(int index)
	{
		LQRelaInfoSchema tSchema = (LQRelaInfoSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LQRelaInfoSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LQRelaInfoSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLQRelaInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LQRelaInfoSchema aSchema = this.get(i);
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
			LQRelaInfoSchema aSchema = new LQRelaInfoSchema();
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
		LQRelaInfoSchema tSchema = new LQRelaInfoSchema();
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
