package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.FICustomerAccTraceBakeSchema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * ClassName: FICustomerAccTraceBakeSet
 * </p>
 * <p>
 * Description: 客户账户轨迹备份表（FICustomerAccTraceBake）的Set文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-12-30
 */
public class FICustomerAccTraceBakeSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(FICustomerAccTraceBakeSet.class);

	// @Method
	public boolean add(FICustomerAccTraceBakeSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(FICustomerAccTraceBakeSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(FICustomerAccTraceBakeSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public FICustomerAccTraceBakeSchema get(int index)
	{
		FICustomerAccTraceBakeSchema tSchema = (FICustomerAccTraceBakeSchema) super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, FICustomerAccTraceBakeSchema aSchema)
	{
		return super.set(index, aSchema);
	}

	public boolean set(FICustomerAccTraceBakeSet aSet)
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
			FICustomerAccTraceBakeSchema aSchema = this.get(i);
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
			FICustomerAccTraceBakeSchema aSchema = new FICustomerAccTraceBakeSchema();
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
		FICustomerAccTraceBakeSchema tSchema = new FICustomerAccTraceBakeSchema();
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
