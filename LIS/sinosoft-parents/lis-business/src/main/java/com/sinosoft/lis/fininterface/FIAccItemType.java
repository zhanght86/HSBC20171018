package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.FIDataTransResultSchema;
import com.sinosoft.lis.schema.FIAboriginalDataSchema;
import com.sinosoft.lis.schema.FICostTypeDefSchema;
import com.sinosoft.utility.CErrors;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface FIAccItemType
{
   /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    public boolean InitInfo(FICostTypeDefSchema tFICostTypeDefSchema,LogInfoDeal tLogInfoDeal);
    public FIDataTransResultSchema DealInfo(FIAboriginalDataSchema tFIAboriginalDataSchema);
}
