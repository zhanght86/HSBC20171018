package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-保全回退公用接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-09-17
 */
public interface EdorBack {
	public CErrors mErrors = new CErrors();

	public boolean submitData(VData cInputData, String cOperate);

	public VData getResult();

	public CErrors getErrors();
}
