package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LZCardAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vdb.LZCardAppDBSet;
import com.sinosoft.lis.vschema.LZCardAppSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class AuotCertifyImportDestroy {
private static Logger logger = Logger.getLogger(AuotCertifyImportDestroy.class);

	public CErrors mErrors = new CErrors(); 
	/**
	 * @param args
	 */
	public boolean  submitData() {
		String tCom[] = new String[3];
		tCom[0]="8632";
		tCom[1]="8611";
//		tCom[2]="";
		for(int i=1;i<=2;i++)
		{
			GlobalInput mGlobalInput = new GlobalInput();
			mGlobalInput.ComCode=tCom[i-1];
			if("8632".equals(mGlobalInput.ComCode))
			{
				mGlobalInput.Operator="320013";
			}
			if("8611".equals(mGlobalInput.ComCode))
			{
				mGlobalInput.Operator="110164";
			}
			String tSql = "select * from lzcardapp a where stateflag<>'3' and exists(select 1 from "
						+ "lzcardimport where certifycode=a.certifycode and startno=a.startno and endno=a.endno and state='Y') and applycom='"+"?applycom?"+"' ";
			logger.debug("tSql--------"+tSql);
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("applycom", mGlobalInput.ComCode);
			LZCardAppDB tLZCardAppDB = new LZCardAppDB();
			LZCardAppSet tLZCardAppSet = tLZCardAppDB.executeQuery(sqlbv);
	
			if(tLZCardAppSet.size()>0)
			{							
				if(!CertifyFunc.DesConfirm(tLZCardAppSet, mGlobalInput))
				{
//					logger.debug("false---"+CertifyFunc.mErrors.getFirstError());
					mErrors.copyAllErrors(CertifyFunc.mErrors);
					logger.debug("单证销毁失败-------------"+CertifyFunc.mErrors.getFirstError());
//					return false;
					continue;
				}
			}
		}

		return true;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AuotCertifyImportDestroy auotCertifyImportDestroy = new AuotCertifyImportDestroy();
		if(!auotCertifyImportDestroy.submitData())
		{
			logger.debug("false-------");
		}
		logger.debug("end-------");

	}

}
