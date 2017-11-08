

package com.sinosoft.lis.reinsure.calculate.assign;

import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.schema.RIPreceptSchema;
import com.sinosoft.lis.schema.RIItemCalSchema;

/**
 * <p>
 * Title: PIAssign
 * </p>
 * <p>
 * Description: 再保方案分配算法类接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */
public interface RIAssign {
	public CErrors mErrors = new CErrors();

	public boolean dealData(RIItemCalSchema aRIItemCalSchema,
			RIPreceptSchema aRIPreceptSchema);

}
