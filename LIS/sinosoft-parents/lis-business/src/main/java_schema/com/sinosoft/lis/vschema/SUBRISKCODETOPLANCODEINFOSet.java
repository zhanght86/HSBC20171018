

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.SUBRISKCODETOPLANCODEINFOSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: SUBRISKCODETOPLANCODEINFOSet </p>
 * <p>Description: SUBRISKCODETOPLANCODEINFOSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class SUBRISKCODETOPLANCODEINFOSet extends SchemaSet
{
	// @Method
	public boolean add(SUBRISKCODETOPLANCODEINFOSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(SUBRISKCODETOPLANCODEINFOSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(SUBRISKCODETOPLANCODEINFOSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public SUBRISKCODETOPLANCODEINFOSchema get(int index)
	{
		SUBRISKCODETOPLANCODEINFOSchema tSchema = (SUBRISKCODETOPLANCODEINFOSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, SUBRISKCODETOPLANCODEINFOSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(SUBRISKCODETOPLANCODEINFOSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpSUBRISKCODETOPLANCODEINFO描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			SUBRISKCODETOPLANCODEINFOSchema aSchema = this.get(i);
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
			SUBRISKCODETOPLANCODEINFOSchema aSchema = new SUBRISKCODETOPLANCODEINFOSchema();
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
		SUBRISKCODETOPLANCODEINFOSchema tSchema = new SUBRISKCODETOPLANCODEINFOSchema();
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

