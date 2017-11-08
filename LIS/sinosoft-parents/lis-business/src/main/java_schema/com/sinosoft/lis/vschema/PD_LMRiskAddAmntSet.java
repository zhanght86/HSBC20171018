/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.PD_LMRiskAddAmntSchema;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_LMRiskAddAmntSet </p>
 * <p>Description: PD_LMRiskAddAmntSchemaSet类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: 产品定义平台_PDM
 * @author：Makerx
 * @CreateDate：2009-03-04
 */
public class PD_LMRiskAddAmntSet extends SchemaSet
{
private static Logger logger = Logger.getLogger(PD_LMRiskAddAmntSet.class);

    // @Method
    public boolean add(PD_LMRiskAddAmntSchema schema)
    {
        return super.add(schema);
    }

    public boolean add(PD_LMRiskAddAmntSet set)
    {
        return super.add(set);
    }

    public boolean remove(PD_LMRiskAddAmntSchema schema)
    {
        return super.remove(schema);
    }

    public PD_LMRiskAddAmntSchema get(int index)
    {
        PD_LMRiskAddAmntSchema schema = (PD_LMRiskAddAmntSchema)super.getObj(index);
        return schema;
    }

    public boolean set(int index, PD_LMRiskAddAmntSchema schema)
    {
        return super.set(index, schema);
    }

    public boolean set(PD_LMRiskAddAmntSet set)
    {
        return super.set(set);
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskAddAmnt描述/A>表字段
     * @return: String 返回打包后字符串
     */
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer("");
        int n = this.size();
        for (int i = 1; i <= n; i++)
        {
            PD_LMRiskAddAmntSchema schema = this.get(i);
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
            PD_LMRiskAddAmntSchema aSchema = new PD_LMRiskAddAmntSchema();
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
        PD_LMRiskAddAmntSchema tSchema = new PD_LMRiskAddAmntSchema();
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
