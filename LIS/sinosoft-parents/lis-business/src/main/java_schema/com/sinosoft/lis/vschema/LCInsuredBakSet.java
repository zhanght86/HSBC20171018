/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCInsuredBakSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCInsuredBakSet </p>
 * <p>Description: LCInsuredBakSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LCInsuredBakSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(LCInsuredBakSet.class);

	// @Method
	public boolean add(LCInsuredBakSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LCInsuredBakSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LCInsuredBakSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LCInsuredBakSchema get(int index)
	{
		LCInsuredBakSchema tSchema = (LCInsuredBakSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LCInsuredBakSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LCInsuredBakSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsuredBak描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LCInsuredBakSchema aSchema = this.get(i);
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
			LCInsuredBakSchema aSchema = new LCInsuredBakSchema();
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
		LCInsuredBakSchema tSchema = new LCInsuredBakSchema();
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
