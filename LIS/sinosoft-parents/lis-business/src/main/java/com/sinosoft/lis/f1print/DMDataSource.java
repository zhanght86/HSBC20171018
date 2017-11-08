package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: DataMine
 * </p>
 * <p>
 * Description: Use template to get data that print job needed
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Kevin
 * @version 1.0
 */

public interface DMDataSource {

	public abstract String getID() throws Exception;

	public abstract void setID(String szSource) throws Exception;

	public abstract String getSource() throws Exception;

	public abstract void setSource(String szSource) throws Exception;

	public abstract void setParams(String szParams[]) throws Exception;

	public abstract void fetch() throws Exception;

	public abstract int getFieldCount() throws Exception;

	public abstract int getRowCount() throws Exception;

	public abstract String getFieldName(int nIndex) throws Exception;

	public abstract String getFieldValue(String szName) throws Exception;

	public abstract boolean next() throws Exception;

	public abstract void first() throws Exception;

	public abstract DMDataSource Clone() throws Exception;
}
