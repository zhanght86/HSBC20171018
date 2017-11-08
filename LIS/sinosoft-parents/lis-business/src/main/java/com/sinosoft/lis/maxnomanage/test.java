package com.sinosoft.lis.maxnomanage;

import java.util.Date;
import java.util.Vector;

import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public  class test {

	   public static void main(String[] args){

		  
//		   CreateMaxNo tC = new CreateMaxNoImp();
//		   tC.setManageCom("86320000");
//		   
//		   System.out.println(tC.getMaxNo("ContNo"));
		   Vector mTaskWaitList  = new Vector();
		   
		   for (int i = 1; i <= 100; i++) {
				
			   VData tVData = new VData();
			   tVData.add(0,String.valueOf(i));
			   tVData.add(1,"test");
			   mTaskWaitList.add(tVData);
			   
			}
		   	Date start = new Date();
			System.out.println("begin.................");
			ServiceA tService = new ServiceA("com.sinosoft.lis.maxnomanage.maxnoThread", mTaskWaitList, 100, 10);
			tService.start();
			System.out.println("end.................");
			Date end = new Date();
			System.out.println((end.getTime() - start.getTime())+"................."+(end.getTime() - start.getTime())/1000/60/10);
	     }

}
