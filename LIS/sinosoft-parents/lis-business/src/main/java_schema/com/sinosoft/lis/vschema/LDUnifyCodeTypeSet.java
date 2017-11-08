/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LDUnifyCodeTypeSchema;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDUnifyCodeTypeSet </p>
 * <p>Description: LDUnifyCodeTypeSchemaSet绫绘枃浠�</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate锛�011-12-30
 */
public class LDUnifyCodeTypeSet extends SchemaSet
{
	// @Method
	public boolean add(LDUnifyCodeTypeSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LDUnifyCodeTypeSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LDUnifyCodeTypeSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LDUnifyCodeTypeSchema get(int index)
	{
		LDUnifyCodeTypeSchema tSchema = (LDUnifyCodeTypeSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LDUnifyCodeTypeSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LDUnifyCodeTypeSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 鏁版嵁鎵撳寘锛屾寜 XML 鏍煎紡鎵撳寘锛岄『搴忓弬瑙�A href ={@docRoot}/dataStructure/tb.html#PrpLDUnifyCodeType鎻忚堪/A>琛ㄥ瓧娈�
	* @return: String 杩斿洖鎵撳寘鍚庡瓧绗︿覆
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LDUnifyCodeTypeSchema aSchema = this.get(i);
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
			LDUnifyCodeTypeSchema aSchema = new LDUnifyCodeTypeSchema();
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
		LDUnifyCodeTypeSchema tSchema = new LDUnifyCodeTypeSchema();
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
