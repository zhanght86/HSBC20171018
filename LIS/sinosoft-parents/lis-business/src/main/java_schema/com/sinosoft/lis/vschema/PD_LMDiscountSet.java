/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.PD_LMDiscountSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMDiscountSet </p>
 * <p>Description: PD_LMDiscountSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台补充表
 */
public class PD_LMDiscountSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(PD_LMDiscountSet.class);

	// @Method
	public boolean add(PD_LMDiscountSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(PD_LMDiscountSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(PD_LMDiscountSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public PD_LMDiscountSchema get(int index)
	{
		PD_LMDiscountSchema tSchema = (PD_LMDiscountSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, PD_LMDiscountSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(PD_LMDiscountSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDiscount描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			PD_LMDiscountSchema aSchema = this.get(i);
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
			PD_LMDiscountSchema aSchema = new PD_LMDiscountSchema();
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
		PD_LMDiscountSchema tSchema = new PD_LMDiscountSchema();
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
