/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.TAB_PARSE_CONFIGSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: TAB_PARSE_CONFIGSet </p>
 * <p>Description: TAB_PARSE_CONFIGSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银保通
 */
public class TAB_PARSE_CONFIGSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(TAB_PARSE_CONFIGSet.class);

	// @Method
	public boolean add(TAB_PARSE_CONFIGSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(TAB_PARSE_CONFIGSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(TAB_PARSE_CONFIGSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public TAB_PARSE_CONFIGSchema get(int index)
	{
		TAB_PARSE_CONFIGSchema tSchema = (TAB_PARSE_CONFIGSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, TAB_PARSE_CONFIGSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(TAB_PARSE_CONFIGSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpTAB_PARSE_CONFIG描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			TAB_PARSE_CONFIGSchema aSchema = this.get(i);
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
			TAB_PARSE_CONFIGSchema aSchema = new TAB_PARSE_CONFIGSchema();
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
		TAB_PARSE_CONFIGSchema tSchema = new TAB_PARSE_CONFIGSchema();
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
