package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.vschema.FIAboriginalDataForDealSet;
import com.sinosoft.lis.schema.FIDataBaseLinkSchema;

/**
 * <p>Title: DistillType</p>
 *
 * <p>Description: 处理数据提取特殊处理类接口</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

/*****
 *    sVData.add(mGlobalInput);
      sVData.add(StartDate);
      sVData.add(EndDate);
      sVData.add(String.valueOf(mMaxDealNUm));
 */
public interface DistillType
{
    public CErrors mErrors = new CErrors();
    public boolean initInfo(VData cInputData,LogInfoDeal tLogInfoDeal);
    public FIAboriginalDataSet DitillInfoNormal(FIDataBaseLinkSchema tFIDataBaseLinkSchema) throws Exception;
    public FIAboriginalDataForDealSet DitillInfoForDeal(FIDataBaseLinkSchema tFIDataBaseLinkSchema) throws Exception;
    public FIAboriginalDataSet DitillInfoNormalForOne(FIDataBaseLinkSchema tFIDataBaseLinkSchema) throws Exception;
    public FIAboriginalDataForDealSet DitillInfoForDealForOne(FIDataBaseLinkSchema tFIDataBaseLinkSchema) throws Exception;
}
