package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-保全项目明细保存公用接口
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
 * @CreateDate：2005-06-28
 */
public interface EdorDetail {
	public CErrors mErrors = new CErrors();

	public boolean submitData(VData cInputData, String cOperate);

	public VData getResult();

	public CErrors getErrors();
}
