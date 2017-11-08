package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LFCKErrorSchema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * ClassName: LFCKErrorSet
 * </p>
 * <p>
 * Description: 保监会报表校验错误信息表（LFCKError）的Set文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-11-03
 */
public class LFCKErrorSet extends SchemaSet
{
	// @Method
	public boolean add(LFCKErrorSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LFCKErrorSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LFCKErrorSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LFCKErrorSchema get(int index)
	{
		LFCKErrorSchema tSchema = (LFCKErrorSchema) super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LFCKErrorSchema aSchema)
	{
		return super.set(index, aSchema);
	}

	public boolean set(LFCKErrorSet aSet)
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
			LFCKErrorSchema aSchema = this.get(i);
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
			LFCKErrorSchema aSchema = new LFCKErrorSchema();
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
		LFCKErrorSchema tSchema = new LFCKErrorSchema();
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
