/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.PDT_RiskTypeTemplateSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PDT_RiskTypeTemplateSet </p>
 * <p>Description: PDT_RiskTypeTemplateSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class PDT_RiskTypeTemplateSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(PDT_RiskTypeTemplateSet.class);

	// @Method
	public boolean add(PDT_RiskTypeTemplateSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(PDT_RiskTypeTemplateSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(PDT_RiskTypeTemplateSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public PDT_RiskTypeTemplateSchema get(int index)
	{
		PDT_RiskTypeTemplateSchema tSchema = (PDT_RiskTypeTemplateSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, PDT_RiskTypeTemplateSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(PDT_RiskTypeTemplateSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPDT_RiskTypeTemplate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			PDT_RiskTypeTemplateSchema aSchema = this.get(i);
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
			PDT_RiskTypeTemplateSchema aSchema = new PDT_RiskTypeTemplateSchema();
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
		PDT_RiskTypeTemplateSchema tSchema = new PDT_RiskTypeTemplateSchema();
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
