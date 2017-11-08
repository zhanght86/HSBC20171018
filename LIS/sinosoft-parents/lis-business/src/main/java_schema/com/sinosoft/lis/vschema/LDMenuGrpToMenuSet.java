/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LDMenuGrpToMenuSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDMenuGrpToMenuSet </p>
 * <p>Description: LDMenuGrpToMenuSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDMenuGrpToMenuSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(LDMenuGrpToMenuSet.class);

	// @Method
	public boolean add(LDMenuGrpToMenuSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LDMenuGrpToMenuSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LDMenuGrpToMenuSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LDMenuGrpToMenuSchema get(int index)
	{
		LDMenuGrpToMenuSchema tSchema = (LDMenuGrpToMenuSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LDMenuGrpToMenuSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LDMenuGrpToMenuSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMenuGrpToMenu描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LDMenuGrpToMenuSchema aSchema = this.get(i);
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
			LDMenuGrpToMenuSchema aSchema = new LDMenuGrpToMenuSchema();
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
		LDMenuGrpToMenuSchema tSchema = new LDMenuGrpToMenuSchema();
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
