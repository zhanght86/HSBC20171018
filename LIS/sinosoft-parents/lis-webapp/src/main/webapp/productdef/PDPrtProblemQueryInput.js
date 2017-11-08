var turnPage = new turnPageClass();  
          
//²éÑ¯°´Å¥
function easyQueryClick()
{   //ÊéĞ´SQLÓï¾ä
	var tSQL = "";	      
    tSQL  = " SELECT a.riskcode,b.Riskname,a.Serialno,a.Issuecont,a.Operator,a.Replyman,"
    	  + " 	(SELECT codename FROM LDCode WHERE Codetype = 'pd_issuestate' and code = a.Issuestate),a.Makedate "
    	  + " FROM Pd_Issue a,Pd_Lmrisk b WHERE a.Riskcode = b.Riskcode "       
	      + getWherePart('a.RiskCode','RiskCode')
	      + getWherePart('a.Operator','issueCreater')             
	      + getWherePart('a.Replyman','issueDealPer')          
		  + getWherePart('a.Makedate','StartDate','>=')    
		  + getWherePart('a.Makedate','EndDate','<=')	      
	      + " ORDER BY RiskCode,SerialNo ";	       	  
	turnPage.queryModal(tSQL,RiskStateInfoGrid);
}
