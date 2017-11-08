

package com.sinosoft.lis.reinsure.calculate.exercise;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;
import java.applet.*;
import java.awt.*;

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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class InterfaceTest implements RIDistill {
	public CErrors mErrors = new CErrors();

	public InterfaceTest() {
		System.out.println(" constructor ");
	}

	public boolean submitData() {
		System.out.println(" submitData ");
		return true;
	}
}
