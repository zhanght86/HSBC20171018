package com.sinosoft.lis.actuary.finder;
import org.apache.log4j.Logger;

import java.util.Date;

public interface IModifiedContFinder {
	public String[] findModifiedConts(Date start, Date end);
}
