var showInfo;
var mDebug="0";
var showInfo;
var turnPage = new turnPageClass();

var tResourceName="operfee.IndiDueFeePartInputSql";
var tResourceSQL1="IndiDueFeePartInputSql1";
var tResourceSQL2="IndiDueFeePartInputSql2";
var tResourceSQL3="IndiDueFeePartInputSql3";
var tResourceSQL4="IndiDueFeePartInputSql4"; 
var tResourceSQL5="IndiDueFeePartInputSql5";
var tResourceSQL6="IndiDueFeePartInputSql6"; 

function PartSingle()
{	
	//if(!beforeSubmit())
	//return false;
	var i = 0;
  	var showStr="�������ս����У������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./IndiDueFeePartInputQuery.jsp";
	document.getElementById("fm").submit();
}

function beforeSubmit()
{
  if (fm.ManageCom.value=="")
  	{
  		alert("��ѡ���շѻ���");
  		fm.ManageCom.focus();
  		return false;
  	}
  if((fm.ManageCom.value).length<=4)
  	{
  		alert("����������ѡ����λ���»�����");
  		fm.ManageCom.focus();
  		return false;
  	}
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

   //var tSql = "select count(distinct contno) from lcpol "  
   //         + " where 1=1 "
   //         //+ " and paytodate between '"+fm.StartDate.value+"' and '"+fm.EndDate.value+"'"
   //         + " and payintv>'0'"
   //         + " and appflag='1'"
   //         + " and managecom like '"+fm.ManageCom.value+"%%' ";
   var tSql  = wrapSql(tResourceName,tResourceSQL1,[fm.ManageCom.value]); 	   
   if (fm.ContNo.value!="") 
   {
   	  //tSql = tSql + 	 " and contno='"+fm.ContNo.value+"'";	   
      tSql = tSql + wrapSql(tResourceName,tResourceSQL2,[fm.ContNo.value]); 		   
   }        
   if (fm.RiskCode.value!="")
   {
   	  // //tSql = tSql + 	  " and riskcode='"+fm.RiskCode.value+"'";	   
     	tSql = tSql +wrapSql(tResourceName,tResourceSQL3,[fm.RiskCode.value]); 		   
   }   
   if (fm.AgentCode.value!="")
   {
   	// tSql = tSql +  " and AgentCode='"+fm.AgentCode.value+"'";	    
   	  tSql = tSql + wrapSql(tResourceName,tResourceSQL4,[fm.AgentCode.value]); 	   
   }      
   if (fm.SecPayMode.value!="")
   {
   	//// tSql = tSql +   " and exists(select 1 from lccont where contno=lcpol.contno and paylocation'"+fm.SecPayMode.value+"')";	      		    
   	  tSql = tSql +wrapSql(tResourceName,tResourceSQL5,[fm.ManageCom.value]); 	 
   }   
   if (fm.ContType.value!="")
   {
   	  //// tSql = tSql +   " and salechnl='"+fm.ContType.value+"'";	   
   		 tSql = tSql +wrapSql(tResourceName,tResourceSQL6,[fm.ContType.value]); 	    
   }
   
   var varity=easyExecSql(tSql);
   showInfo.close();
	 if(varity!=null && varity!=0){
	 	if(confirm("��ѯ��"+varity+"�����ݣ�ȷ��Ҫ����������"))
	    {
	    	document.all("magan").disabled = true;
	    	return true;
      	}else
      	{
  	    	return false;
      	}
    }else
   	{
   		alert("û�в�ѯ����Ҫ���ձ���");
   		return false;
   	}
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;      
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   }	
}

function getTime(strtime,endtime)
{
	var StartTime = strtime;
	var EndTime = endtime;
}

function showRecord(strRecord)
{
  //�����ѯ����ַ���
  turnPage.strQueryResult  = strRecord;
  
  //ʹ��ģ������Դ������д�ڲ��֮ǰ
  turnPage.useSimulation   = 1;  
   
  //��ѯ�ɹ������ַ��������ض�ά����
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��
  var filterArray = new Array(1,20);
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //���˶�ά���飬ʹ֮��MULTILINEƥ��

  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);

  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = ContGrid;             
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  //����MULTILINE������ʾ��ѯ���

  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  //���뽫������������Ϊһ�����ݿ�
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
}

function afterSubmit1( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    resetForm();    
  }
}

function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("��LJSPayPersonInput.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

function easyQueryClick()
{  
	if(fm.ContNo.value == "" && document.fm.StartDate.value == ""){
		window.alert("��û���趨��������,��ѡ�񱣵��Ż�ʱ���!");
		document.fm.EndDate.focus;
		return false;
	}

	if(fm.EndDate.value != "" &&document.fm.StartDate.value == ""){
		window.alert("��û���趨ʱ���,���趨ʱ���!");
		document.fm.EndDate.focus;
		return false;
	}

  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
	fm.action = "./IndiDueFeeQueryDate.jsp";
	document.getElementById("fm").submit();
}

function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  }
}

function afterQuery( FlagStr, content )
{
  showInfo.close();
	
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
}

function queryAgent()
{
	if( verifyInput2() == false ) return false;
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
	
    if(document.all('AgentCode').value == "")	{  
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
	var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) {
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
      } 
	}	
}
