/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.PD_LCSetMealRiskRelaSchema;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_LCSetMealRiskRelaSet </p>
 * <p>Description: PD_LCSetMealRiskRelaSchemaSet类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: 卡单
 * @author：Makerx
 * @CreateDate：2009-04-21
 */
public class PD_LCSetMealRiskRelaSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(PD_LCSetMealRiskRelaSet.class);

    // @Method
    public boolean add(PD_LCSetMealRiskRelaSchema schema)
    {
        return super.add(schema);
    }

    public boolean add(PD_LCSetMealRiskRelaSet set)
    {
        return super.add(set);
    }

    public boolean remove(PD_LCSetMealRiskRelaSchema schema)
    {
        return super.remove(schema);
    }

    public PD_LCSetMealRiskRelaSchema get(int index)
    {
        PD_LCSetMealRiskRelaSchema schema = (PD_LCSetMealRiskRelaSchema)super.getObj(index);
        return schema;
    }

    public boolean set(int index, PD_LCSetMealRiskRelaSchema schema)
    {
        return super.set(index, schema);
    }

    public boolean set(PD_LCSetMealRiskRelaSet set)
    {
        return super.set(set);
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCSetMealRiskRela描述/A>表字段
     * @return: String 返回打包后字符串
     */
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer("");
        int n = this.size();
        for (int i = 1; i <= n; i++)
        {
            PD_LCSetMealRiskRelaSchema schema = this.get(i);
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
            PD_LCSetMealRiskRelaSchema aSchema = new PD_LCSetMealRiskRelaSchema();
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
        PD_LCSetMealRiskRelaSchema tSchema = new PD_LCSetMealRiskRelaSchema();
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
