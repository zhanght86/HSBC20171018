package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.FICustomerAccSchema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * ClassName: FICustomerAccSet
 * </p>
 * <p>
 * Description: 客户账户总表（FICustomerAcc）的Set文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-12-30
 */
public class FICustomerAccSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(FICustomerAccSet.class);

	// @Method
	public boolean add(FICustomerAccSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(FICustomerAccSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(FICustomerAccSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public FICustomerAccSchema get(int index)
	{
		FICustomerAccSchema tSchema = (FICustomerAccSchema) super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, FICustomerAccSchema aSchema)
	{
		return super.set(index, aSchema);
	}

	public boolean set(FICustomerAccSet aSet)
	{
		return super.set(aSet);
	}

	/**
	 * 数据打包，按 XML 格式打包
	 * @return: String 返回打包后字符串
	 **/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			FICustomerAccSchema aSchema = this.get(i);
			strReturn.append(aSchema.encode());
			if (i != n)
				strReturn.append(SysConst.RECORDSPLITER);
		}

		return strReturn.toString();
	}

	/**
	 * 数据解包
	 * @param: str String 打包后字符串
	 * @return: boolean
	 **/
	public boolean decode(String str)
	{
		int nBeginPos = 0;
		int nEndPos = str.indexOf('^');
		this.clear();

		while (nEndPos != -1)
		{
			FICustomerAccSchema aSchema = new FICustomerAccSchema();
			if (aSchema.decode(str.substring(nBeginPos, nEndPos)))
			{
				this.add(aSchema);
				nBeginPos = nEndPos + 1;
				nEndPos = str.indexOf('^', nEndPos + 1);
			}
			else
			{
				// @@错误处理
				this.mErrors.copyAllErrors(aSchema.mErrors);
				return false;
			}
		}
		FICustomerAccSchema tSchema = new FICustomerAccSchema();
		if (tSchema.decode(str.substring(nBeginPos)))
		{
			this.add(tSchema);
			return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tSchema.mErrors);
			return false;
		}
	}

}
