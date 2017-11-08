package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCInsureAccSet </p>
 * <p>Description: LCInsureAccSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造业务表
 */
public class LCInsureAccSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(LCInsureAccSet.class);

	// @Method
	public boolean add(LCInsureAccSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LCInsureAccSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LCInsureAccSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LCInsureAccSchema get(int index)
	{
		LCInsureAccSchema tSchema = (LCInsureAccSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LCInsureAccSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LCInsureAccSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsureAcc描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LCInsureAccSchema aSchema = this.get(i);
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
			LCInsureAccSchema aSchema = new LCInsureAccSchema();
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
		LCInsureAccSchema tSchema = new LCInsureAccSchema();
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
