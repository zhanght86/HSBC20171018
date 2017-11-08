

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */
public interface RIProcessType {
	public CErrors mErrors = new CErrors();

	public boolean dealData(RIPolRecordSchema aRIPolrecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet tRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet);

	public VData getValue();

	public CErrors getCErrors();
}
