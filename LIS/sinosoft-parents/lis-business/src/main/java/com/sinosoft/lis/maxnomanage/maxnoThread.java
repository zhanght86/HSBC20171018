package com.sinosoft.lis.maxnomanage;

import org.apache.log4j.Logger;

import com.sinosoft.service.CovBase;
import com.sinosoft.utility.VData;

public  class maxnoThread extends CovBase{
	VData mInputDataNew = new VData();
	private static Logger logger = Logger.getLogger(maxnoThread.class);
	
	public void setObject(Object tObject) {
		//多线程的外部参数条件
		mInputDataNew = (VData) tObject;
	}
	
	public void run() {
		String tKey = (String)this.mInputDataNew.get(0);
		String tNoType = (String)this.mInputDataNew.get(1);
		CreateMaxNo tC = new CreateMaxNoImp();
		tC.setManageCom("86320000"); 
		String tNo = tC.getMaxNo(tNoType);
		System.out.println("tKey:"+tKey+":tNo:"+tNo);
		logger.debug("tKey:"+tKey+":tNo:"+tNo);
		   
	}

	   public static void main(String[] args){

		  
		   CreateMaxNo tC = new CreateMaxNoImp();
		   tC.setManageCom("86320000");
		   
		   System.out.println(tC.getMaxNo("ContNo"));
	     }

}
