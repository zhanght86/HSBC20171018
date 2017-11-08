package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;

/**
 * <p>Title: DataTypeProcess</p>
 *
 * <p>Description: 数据二次处理->数据类型规则处理类接口</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public interface DataTypeProcess
{
	public FIAboriginalDataSet SEDealInfo(VData cInputData) throws Exception;

	public boolean haveDataUnsettled(VData cInputData) throws Exception;
}
