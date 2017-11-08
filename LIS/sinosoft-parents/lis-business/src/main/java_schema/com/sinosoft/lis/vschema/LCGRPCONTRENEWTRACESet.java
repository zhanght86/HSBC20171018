/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LCGRPCONTRENEWTRACESchema;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: LCGRPCONTRENEWTRACESet </p>
 * <p>Description: LCGRPCONTRENEWTRACESchemaSet类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: temp
 * @author：Makerx
 * @CreateDate：2011-09-13
 */
public class LCGRPCONTRENEWTRACESet extends SchemaSet
{
    // @Method
    public boolean add(LCGRPCONTRENEWTRACESchema schema)
    {
        return super.add(schema);
    }

    public boolean add(LCGRPCONTRENEWTRACESet set)
    {
        return super.add(set);
    }

    public boolean remove(LCGRPCONTRENEWTRACESchema schema)
    {
        return super.remove(schema);
    }

    public LCGRPCONTRENEWTRACESchema get(int index)
    {
        LCGRPCONTRENEWTRACESchema schema = (LCGRPCONTRENEWTRACESchema)super.getObj(index);
        return schema;
    }

    public boolean set(int index, LCGRPCONTRENEWTRACESchema schema)
    {
        return super.set(index, schema);
    }

    public boolean set(LCGRPCONTRENEWTRACESet set)
    {
        return super.set(set);
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGRPCONTRENEWTRACE描述/A>表字段
     * @return: String 返回打包后字符串
     */
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer("");
        int n = this.size();
        for (int i = 1; i <= n; i++)
        {
            LCGRPCONTRENEWTRACESchema schema = this.get(i);
            strReturn.append(schema.encode());
            if (i != n)
                strReturn.append(SysConst.RECORDSPLITER);
        }
        return strReturn.toString();
    }

    /**
     * 数据解包
     * @param: str String 打包后字符串
     * @return: boolean
     */
    public boolean decode(String str)
    {
        int nBeginPos = 0;
        int nEndPos = str.indexOf('^');
        this.clear();
        while (nEndPos != -1)
        {
            LCGRPCONTRENEWTRACESchema aSchema = new LCGRPCONTRENEWTRACESchema();
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
        LCGRPCONTRENEWTRACESchema tSchema = new LCGRPCONTRENEWTRACESchema();
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
