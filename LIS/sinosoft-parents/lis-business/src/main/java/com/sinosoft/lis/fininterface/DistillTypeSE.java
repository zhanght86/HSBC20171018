package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.lis.vschema.FIAboriginalDataForDealSet;

/**
 * <p>Title: DistillTypeSE</p>
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
public interface DistillTypeSE
{
	public FIAboriginalDataForDealSet DitillInfo(VData cInputData) throws Exception;

	public boolean haveDataUnsettled(VData cInputData) throws Exception;
}
