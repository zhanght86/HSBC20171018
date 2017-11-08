/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LDUWGrade_PicchSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDUWGrade_PicchSet </p>
 * <p>Description: LDUWGrade_PicchSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDUWGrade_PicchSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(LDUWGrade_PicchSet.class);

	// @Method
	public boolean add(LDUWGrade_PicchSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LDUWGrade_PicchSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LDUWGrade_PicchSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LDUWGrade_PicchSchema get(int index)
	{
		LDUWGrade_PicchSchema tSchema = (LDUWGrade_PicchSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LDUWGrade_PicchSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LDUWGrade_PicchSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWGrade_Picch描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LDUWGrade_PicchSchema aSchema = this.get(i);
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
			LDUWGrade_PicchSchema aSchema = new LDUWGrade_PicchSchema();
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
		LDUWGrade_PicchSchema tSchema = new LDUWGrade_PicchSchema();
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
