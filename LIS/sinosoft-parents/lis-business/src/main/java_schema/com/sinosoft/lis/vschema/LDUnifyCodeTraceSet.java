/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LDUnifyCodeTraceSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDUnifyCodeTraceSet </p>
 * <p>Description: LDUnifyCodeTraceSchemaSet绫绘枃浠�</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate锛�011-12-30
 */
public class LDUnifyCodeTraceSet extends SchemaSet
{
	// @Method
	public boolean add(LDUnifyCodeTraceSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LDUnifyCodeTraceSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LDUnifyCodeTraceSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LDUnifyCodeTraceSchema get(int index)
	{
		LDUnifyCodeTraceSchema tSchema = (LDUnifyCodeTraceSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LDUnifyCodeTraceSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LDUnifyCodeTraceSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 鏁版嵁鎵撳寘锛屾寜 XML 鏍煎紡鎵撳寘锛岄『搴忓弬瑙�A href ={@docRoot}/dataStructure/tb.html#PrpLDUnifyCodeTrace鎻忚堪/A>琛ㄥ瓧娈�
	* @return: String 杩斿洖鎵撳寘鍚庡瓧绗︿覆
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LDUnifyCodeTraceSchema aSchema = this.get(i);
			strReturn.append(aSchema.encode());
			if( i != n ) strReturn.append(SysConst.RECORDSPLITER);
		}

		return strReturn.toString();
	}

	/**
	* 鏁版嵁瑙ｅ寘
	* @param: str String 鎵撳寘鍚庡瓧绗︿覆
	* @return: boolean
	**/
	public boolean decode( String str )
	{
		int nBeginPos = 0;
		int nEndPos = str.indexOf('^');
		this.clear();

		while( nEndPos != -1 )
		{
			LDUnifyCodeTraceSchema aSchema = new LDUnifyCodeTraceSchema();
			if(aSchema.decode(str.substring(nBeginPos, nEndPos)))
			{
			this.add(aSchema);
			nBeginPos = nEndPos + 1;
			nEndPos = str.indexOf('^', nEndPos + 1);
			}
			else
			{
				// @@閿欒澶勭悊
				this.mErrors.copyAllErrors( aSchema.mErrors );
				return false;
			}
		}
		LDUnifyCodeTraceSchema tSchema = new LDUnifyCodeTraceSchema();
		if(tSchema.decode(str.substring(nBeginPos)))
		{
		this.add(tSchema);
		return true;
		}
		else
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors( tSchema.mErrors );
			return false;
		}
	}

}
