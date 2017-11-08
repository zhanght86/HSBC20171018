

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.pubfun.diskimport.DefaultDiskImporter;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.vschema.RIPolRecordSet;

/**
 * <p>
 * Title: LIS
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZhangBin
 * @version 6.0
 */
public class RiImportAct {
	private static final String schemaClassName = "com.sinosoft.lis.schema.RIPolRecordSchema";

	private static final String schemaSetClassName = "com.sinosoft.lis.vschema.RIPolRecordSet";
	/** 使用默认的导入方式 */
	private DefaultDiskImporter importer = null;
	/** 错误处理 */
	public CErrors mErrrors = new CErrors();

	public RiImportAct(String fileName, String configFileName, String sheetName) {
		importer = new DefaultDiskImporter(fileName, configFileName, sheetName);
	}

	/**
	 * 执行导入
	 * 
	 * @return boolean
	 */
	public boolean doImport() {
		importer.setSchemaClassName(schemaClassName);
		importer.setSchemaSetClassName(schemaSetClassName);
		if (!importer.doImport()) {
			mErrrors.copyAllErrors(importer.mErrors);
			String errMess = "";
			for (int i = 0; i < mErrrors.getErrorCount(); i++) {
				errMess += mErrrors.getError(i).errorMessage;
			}

			System.out.println("mErrrors信息: " + errMess);
			return false;
		}
		return true;
	}

	/**
	 * 得到导入结果
	 * 
	 * @return SchemaSet
	 */
	public SchemaSet getSchemaSet() {
		return importer.getSchemaSet();
	}

	public static void main(String[] args) {
	}
}
