var showInfo;
var turnPage5 = new turnPageClass();
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage1 = new turnPageClass(); 
function getPolGrid(tGrpContNo)
{
	var tGrpContno=document.all('GrpContNo').value;
//    var strSQL = " select RiskCode," +
//    			 " (select RiskName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
//    			 " grppolno, Amnt, " +
//				 " nvl((select sum(p.prem) from lcprem p where p.polno = c.polno and p.payplantype in ('0')), 0), " +
//				 " nvl((select sum(p.prem) from lcprem p where p.polno = c.polno and p.payplantype in ('01', '03')), 0), " +
//				 " nvl((select sum(p.prem) from lcprem p where p.polno = c.polno and p.payplantype in ('02', '04')), 0), " +
//   			 " polno " +
//    			 " from LCGrpPol c where GrpContNo='" + tGrpContNo + "' and ( " +
//    			 " (appflag = '1' and  (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and ((startdate <= '" + fm.EdorItemAppDate.value + "' and '" + fm.EdorItemAppDate.value + "' <= enddate )or(startdate <= '" + fm.EdorItemAppDate.value + "' and enddate is null ))and s.polno = c.polno) = 0 )" +
//    			 " or " +
//    			 " (appflag = '4' and  (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and trim(statereason) in ('05', '06', '07') and ((startdate <= '" + fm.EdorItemAppDate.value + "' and '" + fm.EdorItemAppDate.value + "' <= enddate )or(startdate <= '" + fm.EdorItemAppDate.value + "' and enddate is null ))and s.polno = c.polno) > 0 )" +
//    			 " )";
var sqlid816110719="DSHomeContSql816110719";
var mySql816110719=new SqlClass();
mySql816110719.setResourceName("bq.GEdorTypeCTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql816110719.setSqlId(sqlid816110719);//ָ��ʹ�õ�Sql��id
mySql816110719.addSubPara(tGrpContNo);//ָ������Ĳ���
var strSQL=mySql816110719.getString();
//
//    var strSQL = " select RiskCode," +
//                 " (select RiskName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
//                 " grppolno, Amnt, Prem, SumPrem, Peoples2,grpcontno" +
//                 " from LCGrpPol c where GrpContNo='" + tGrpContNo + "' and " +
//                 " appflag = '1' ";
    			 
	brrResult = easyExecSql(strSQL);
	if (brrResult)
	{
		displayMultiline(brrResult,GrpPolGrid);
	}
}

function getCustomerGrid()
{

    var tContNo=document.all("GrpContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
       var sqlid816110825="DSHomeContSql816110825";
var mySql816110825=new SqlClass();
mySql816110825.setResourceName("bq.GEdorTypeCTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql816110825.setSqlId(sqlid816110825);//ָ��ʹ�õ�Sql��id
mySql816110825.addSubPara(tContNo);//ָ������Ĳ���
var strSQL=mySql816110825.getString();
       
//        var strSQL = " Select i.insuredno, '������', i.name, " 
//					+" (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),"
//					+" i.Birthday," 
//					+" (select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)), "
//					+" i.IDNo " 
//					+" From lcinsured i Where (insuredstat is null or insuredstat<>'4') and GrpContNo='" + tContNo+"'";
		//arrResult = easyExecSql(strSQL);
		//if (arrResult)
		//{
			//displayMultiline(arrResult,CustomerGrid);
			turnPage5.queryModal(strSQL, CustomerGrid);
		//}
    }
} 

function chkPol()
{
    var tContno   = document.all('GrpContNo').value;
    var tEdorNo   = document.all('EdorNo').value;
    var tEdorType = document.all('EdorType').value;
    var sqlid816110958="DSHomeContSql816110958";
var mySql816110958=new SqlClass();
mySql816110958.setResourceName("bq.GEdorTypeCTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql816110958.setSqlId(sqlid816110958);//ָ��ʹ�õ�Sql��id
mySql816110958.addSubPara(tEdorNo);//ָ������Ĳ���
mySql816110958.addSubPara(tEdorType);//ָ������Ĳ���
mySql816110958.addSubPara(tContno);//ָ������Ĳ���
var strSQL=mySql816110958.getString();
    
//    var strSQL = " select grppolno from lpgrppol " +
//    			 " where edorno='" + tEdorNo + 
//    			 "' and edortype='" + tEdorType + 
//    			 "' and Grpcontno='"+tContno+"'";        
    var arrResult2=easyExecSql(strSQL);
    var m=0;
  	var n=0;
  	
  	if(arrResult2!=null)
  	{
  		var q = arrResult2.length;
  		for(m = 0; m < PolGrid.mulLineCount; m++)
	  	{
	  		for(n = 0; n < q; n++)
	  	   {
	  	   	//alert(arrResult2[n][0]);
	  	   	//alert(PolGrid.getRowColData(m, 8));
	  			if(PolGrid.getRowColData(m, 3) == arrResult2[n][0])
	  			{
	  				
	  				PolGrid.checkBoxSel(m+1);
	  			}
	  		}
	  	}				
  	}
}


function getZTMoney()
{
    var sqlid816111122="DSHomeContSql816111122";
var mySql816111122=new SqlClass();
mySql816111122.setResourceName("bq.GEdorTypeCTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql816111122.setSqlId(sqlid816111122);//ָ��ʹ�õ�Sql��id
mySql816111122.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
strSQL=mySql816111122.getString();
    
//    strSQL = " select sum(getmoney) from ljsgetendorse " +
//    		 " where endorsementno = '" + fm.EdorNo.value +
//    		 "' and FEEOPERATIONTYPE = 'CT' ";
            
    var brr = easyExecSql(strSQL);
    
    if ( brr ) 
    {
        (brr[0][0]==null||brr[0][0]=='null')?fm.GetMoney.value='0.0':fm.GetMoney.value= brr[0][0];
      //alert(brr[0][0]);
    } 
    	

    		
}
function getContZTInfo()
{
		var sqlid816111258="DSHomeContSql816111258";
var mySql816111258=new SqlClass();
mySql816111258.setResourceName("bq.GEdorTypeCTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql816111258.setSqlId(sqlid816111258);//ָ��ʹ�õ�Sql��id
mySql816111258.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql816111258.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
mySql816111258.addSubPara(fm.EdorType.value);//ָ������Ĳ���
var tSql=mySql816111258.getString();
		
//		var tSql = "select edorstate from lpgrpedoritem where grpcontno = '"+fm.GrpContNo.value+"' and edorno = '"+fm.EdorNo.value+"' and edortype = '"+fm.EdorType.value+"'";
		var tArr = easyExecSql(tSql, 1, 0, 1);
		var tEdorState;
		if (tArr != null)
		{
		  tEdorState = tArr[0][0];
		  
		}
		fm.EdorState.value = tEdorState;
		if(tEdorState == "3")
		{
			//alert(tEdorState);
			divCTInfo.style.display = 'none';
			divCTFeePolDetail.style.display = 'none';
			divCTFeeInfo.style.display = 'none';
		}else
			{
				
				initLCPolGrid();
				var sqlid816113245="DSHomeContSql816113245";
var mySql816113245=new SqlClass();
mySql816113245.setResourceName("bq.GEdorTypeCTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql816113245.setSqlId(sqlid816113245);//ָ��ʹ�õ�Sql��id
mySql816113245.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
mySql816113245.addSubPara(fm.EdorType.value);//ָ������Ĳ���
mySql816113245.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql816113245.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
mySql816113245.addSubPara(fm.EdorType.value);//ָ������Ĳ���
mySql816113245.addSubPara(fm.EdorType.value);//ָ������Ĳ���
var strSQL=mySql816113245.getString();
				
//				var strSQL = "select a.PolNo, a.RiskCode, b.RiskName, a.InsuredNo,a.InsuredName, " 
//    	  			 + "a.CValiDate,a.prem,a.amnt,a.mult,(select getmoney from lpedoritem where polno = a.polno and edorno = '"+fm.EdorNo.value+"' and edortype = '"+fm.EdorType.value+"') from LPPol a, LMRiskApp b where a.grpContNo = '"
//	  	         + fm.GrpContNo.value + "'"
//    	         + " and a.EdorNo = '"+fm.EdorNo.value+"' and a.EdorType = '"+fm.EdorType.value+"' and a.RiskCode = b.RiskCode and a.riskcode in (select riskcode from lmriskedoritem where edorcode ='"+fm.EdorType.value+"')";    
  			turnPage1.queryModal(strSQL, LCPolGrid);
  			divCTFeePolDetail.style.display = '';
				divCTFeeInfo.style.display = '';
				divCTInfo.style.display = '';
  		}
}

function getContZTInfo_bak()
{
	var PayIntv;
	var PayToDate;
	var PayEndDate;
	
	var sqlid816113652="DSHomeContSql816113652";
var mySql816113652=new SqlClass();
mySql816113652.setResourceName("bq.GEdorTypeCTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql816113652.setSqlId(sqlid816113652);//ָ��ʹ�õ�Sql��id
mySql816113652.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
strSQL=mySql816113652.getString();

//    strSQL = " select distinct paytodate, payintv, payenddate from lcGrpPol " +
//    		 " where Grpcontno = '" + fm.GrpContNo.value + "' ";
  var hrr = easyExecSql(strSQL);
    if ( hrr ) 
    {    	
    	hrr[0][0]==null||hrr[0][0]=='null'?'0':PayToDate = hrr[0][0];
    	hrr[0][1]==null||hrr[0][1]=='null'?'0':PayIntv = hrr[0][1];
    	hrr[0][2]==null||hrr[0][2]=='null'?'0':PayEndDate = hrr[0][2];
    }
	else
	{
		alert("���ұ�����Ϣʧ��");
		return;
	}	
	    
    var sqlid816113739="DSHomeContSql816113739";
var mySql816113739=new SqlClass();
mySql816113739.setResourceName("bq.GEdorTypeCTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql816113739.setSqlId(sqlid816113739);//ָ��ʹ�õ�Sql��id
mySql816113739.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
strSQL=mySql816113739.getString();
    
//    strSQL = " select cvalidate from lcgrpcont " +
//    		 " where grpcontno = '" + fm.GrpContNo.value + "' ";
    var drr = easyExecSql(strSQL);
    
    if ( drr ) 
    {
    	drr[0][0]==null||drr[0][0]=='null'?'0':fm.CvaliDate.value = drr[0][0];

	    var CalDate;

      //�жϽ�������
     var intv = dateDiffCT(fm.EdorItemAppDate.value, PayToDate, "D");
	   
	    		if (intv > 0)
	    		{
	    			CalDate = fm.EdorItemAppDate.value;
	    		}
	    	    else
	    		{
	    			if (PayIntv == 0 || PayToDate == PayEndDate)
	    			{
	    				CalDate = fm.EdorItemAppDate.value;
	    			}
	    		    else
	    		    {
	    		    	CalDate = PayToDate;
	    		    }
	    		}
	    		        
        var intval = dateDiffCT(fm.CvaliDate.value, CalDate, "M");
       
        var year = (intval- intval%12)/12;
        var month = intval%12;
        fm.Inteval.value = year + " �� �� " + month + " ��";
        
        if (PayIntv == 0)
        {
        	fm.PayToDate.value = "";
        }
        else
    	  {
    		if (PayEndDate == PayToDate)
    		{
	        fm.PayToDate.value = "";
			    
    		}
    	  else
    		{
    			fm.PayToDate.value = PayToDate;
    		}
    	}
    }
}

function edorTypeCTSave()
{
	var tEdorState = fm.EdorState.value;
	if(tEdorState == "1")
	{
		if(!confirm("���α�ȫ�ѽ��й������ͬ���㣬�������ظ�������ȷ�ϼ�����"))
		{
			return;	
		}
	}

  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  fm.submit();
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{

	showInfo.close(); 
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  initForm();
    
    
//  if (FlagStr == "Succ")
//    {
//    	initForm();
//    }
}


function returnParent()
{
	top.opener.initEdorItemGrid();
	top.opener.getEdorItemGrid();
	top.close();
}

function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;;
	var ActivityID = "0000000103";
	var OtherNo = top.opener.document.all("OtherNo").value;;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
}

function getCTFeeDetailGrid()
{
	initCTFeeDetailGrid();
	var sqlid816114007="DSHomeContSql816114007";
var mySql816114007=new SqlClass();
mySql816114007.setResourceName("bq.GEdorTypeCTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql816114007.setSqlId(sqlid816114007);//ָ��ʹ�õ�Sql��id
mySql816114007.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
mySql816114007.addSubPara(fm.EdorType.value);//ָ������Ĳ���
var strSQL=mySql816114007.getString();
	
//    var strSQL = " select " +
//    			 " grppolno, " +
//    			 " (select riskcode from lcgrppol p where p.grppolno = j.grppolno), " +
//    			 " (select RiskName from LMRisk m where m.RiskCode = (select riskcode from lcgrppol p where p.grppolno = j.grppolno)), " +
//    			 " (select codename from ldcode d where trim(d.codetype) = 'BQSubFeeType' and trim(d.code) = trim(j.subfeeoperationtype)), " +
//    			 " j.getmoney, " +
//    			 " (select codename from ldcode d2 where trim(d2.codetype) = 'finfeetype' and trim(d2.code) = trim(j.feefinatype) ) " +
//    			 " from ljsgetendorse j  " +
//    			 " where j.endorsementno in " +
//    			 " (select edorno from lpgrpedormain where edoracceptno = '" + fm.EdorAcceptNo.value + "') and j.FeeOperationType = '" + fm.EdorType.value + "' " ;
    			 
	drrResult = easyExecSql(strSQL);
	if (drrResult)
	{
		displayMultiline(drrResult,CTFeeDetailGrid);
	}
}

function getCTFeePolGrid()
{
	initCTFeePolGrid();	
	var sqlid816114226="DSHomeContSql816114226";
var mySql816114226=new SqlClass();
mySql816114226.setResourceName("bq.GEdorTypeCTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql816114226.setSqlId(sqlid816114226);//ָ��ʹ�õ�Sql��id
mySql816114226.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
mySql816114226.addSubPara(fm.EdorType.value);//ָ������Ĳ���
var strSQL=mySql816114226.getString();
	
//    var strSQL = " select " +
//    			 " grppolno, " +
//    			 " riskcode, " +
//    			 " (select RiskName from LMRisk m where m.RiskCode = j.riskcode), " +
//    			 "  sum(getmoney) " +
//    			 " from ljsgetendorse j   " +
//    			 " where j.endorsementno in  " +
//    			 " (select edorno from lpgrpedoritem " +
//    			 " where edoracceptno = '" + fm.EdorAcceptNo.value + "' ) and j.FeeOperationType = '" + fm.EdorType.value + "' and FeeFinaType='TB' " +
//    			 " group by grppolno,riskcode  ";   
	drrResult = easyExecSql(strSQL);
	if (drrResult)
	{
		displayMultiline(drrResult,CTFeePolGrid);
	}
}

/**
* �����������ڵĲ�,���ز������(M)������(D) (����������2.29��һ��)
* <p><b>Example: </b><p>
* <p>dateDiff("2002-10-1", "2002-10-3", "D") returns "2"<p>
* <p>dateDiff("2002-1-1", "2002-10-3", "M") returns "9"<p>
* @param dateStart ������
* @param dateEnd ��������
* @param MD ��ǣ���M��ΪҪ�󷵻ز����������D��ΪҪ�󷵻ز������
* @return �����������ڲ������(M)������(D)
*/
function dateDiffCT(dateStart,dateEnd,MD)
{
  if(dateStart==""||dateEnd=="")
  {
  	return false;
  }
  if (typeof(dateStart) == "string") {
    dateStart = getDate(dateStart);
  }

  if (typeof(dateEnd) == "string") {
    dateEnd = getDate(dateEnd);
  }

  var i;
  if(MD=="D") //��������
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();
    var startT=new Date(startY,startM-1,startD);
    var endT=new Date(endY,endM-1,endD);
    var diffDay=(endT.valueOf()-startT.valueOf())/86400000;
    return diffDay;
  }
  else //���¼����
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();

    if(endD<startD)
    {
      return (endY-startY)*12 + (endM-startM) -1;  //ֻ��������
    }
    else
    {
      return (endY-startY)*12 + (endM-startM);
    }
  }
}

function Edorquery()
{
	var ButtonFlag = top.opener.document.all('ButtonFlag').value;
	if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
 	{
 		divEdorquery.style.display = "";
 	}
}

function MoneyDetail()
{
  window.open("./GetLJSGetEndorse.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");  
}