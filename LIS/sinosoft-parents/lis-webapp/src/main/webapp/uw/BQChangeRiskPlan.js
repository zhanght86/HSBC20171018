//�������ƣ�UWManuSpec.js
//�����ܣ��˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var turnPage_OldRisk = new turnPageClass();
var turnPage_AddFee = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var operate = "";
var proposalno = "";
var serialno = "";
var poladdgridid="";
var sqlresourcename = "uw.BQChangeRiskPlanInputSql";

//�ύ�����水ť��Ӧ����
function submitForm(tflag)
{
	fm.action="./BQChangeRiskPlanChk.jsp";
	if(tflag=="1"){
	   	var tSpecType=fm.HealthSpecTemp.value;
	   	var tSpecCode=fm.SpecTemp.value;
	   	var tRemark=fm.Remark.value;
	   	if(trim(tSpecType)=="null"||trim(tSpecType)==null||trim(tSpecType)=="")
	   	{
	   		alert("����ϵͳ���벻��Ϊ��!");
	   		return false;
	   		}
	   	if(trim(tSpecCode)=="null"||trim(tSpecCode)==null||trim(tSpecCode)=="")
	   	{
	   		alert("��Լ���ݱ��벻��Ϊ��!");
	   		return false;
	   		}
	   	if(trim(tRemark)=="null"||trim(tRemark)==null||trim(tRemark)=="")
	   	{
	   		alert("��Լ���ݲ���Ϊ��!");
	   		return false;
	   		}
	     var tNeedPrintFlag=fm.NeedPrintFlag.value;
  	        if(trim(tNeedPrintFlag)=="null"||trim(tNeedPrintFlag)==null||trim(tNeedPrintFlag)=="")
	   	{
	   		alert("��ѡ���·����!");
	   		return false;
	   	}
	    fm.operate.value = "INSERT"
	    //alert(operate);
	    //return;
	}
	else if(tflag=="2"){
		 	var tRemark=fm.Remark.value;
	   	if(trim(tRemark)=="null"||trim(tRemark)==null||trim(tRemark)=="")
	   	{
	   		alert("��Լ���ݲ���Ϊ��!");
	   		return false;
	   	}
	 	 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("��ѡ��Ҫ�޸ĵ���Լ��Ϣ��");
		 	  	return;
		 	  } 
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("ֻ����δ���ͻ����ѻ���״̬�����޸ģ�");
		 	    return;	
		 	  }
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
     // alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);
			fm.operate.value = "UPDATE";
			fm.SpecCode.value=UWSpecGrid.getRowColData(tSelNo,9);
			fm.SpecType.value=UWSpecGrid.getRowColData(tSelNo,8);;
			//alert(operate);
			//return;
		 }
  else if(tflag=="3")
  	 {
		 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("��ѡ��Ҫɾ������Լ��Ϣ��");
		 	  	return;
		 	  }
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("ֻ����δ���ͻ����ѻ���״̬����ɾ����");
		 	    return;	
		 	  }
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
      //alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);  	 	
  	  fm.operate.value   = "DELETE";
  	  //alert(operate);
  	  //return;
     }
      else
  	 {  	    
		 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("��ѡ��Ҫ�޸��·���ǵ���Լ��Ϣ��");
		 	  	return;
		 	  }
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("ֻ����δ���ͻ����ѻ���״̬�����޸��·���ǣ�");
		 	    return;	
		 	  }
		 	   var tNeedPrintFlag=fm.NeedPrintFlag.value;
  	        if(trim(tNeedPrintFlag)=="null"||trim(tNeedPrintFlag)==null||trim(tNeedPrintFlag)=="")
	   	{
	   		alert("��ѡ���·����!");
	   		return false;
	   	}
		 	  if (!confirm('ȷ���޸�?'))
			{
			return false;
			}
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
      //alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);  	 	
  	  fm.operate.value   = "UPDATE";
  	  //alert(operate);
  	  //return;
     }
	   
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit()(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="�����ɹ���";  	
  	showInfo.close();
  	alert(showStr);
  	top.opener.queryRiskInfo();
  	
  //	parent.close();
  	
    //ִ����һ������
  }
  initUWSpecGrid();
  initPolAddGrid();
  //initCancleGiven(tContNo,tInsuredNo);
  QueryPolSpecGrid();
  queryRiskAddFee();
  initOldRislPlanGrid('');
  queryOldRiskPlan();
  //onRiskTabChange(1);
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="�����ɹ���";  	
  	showInfo.close();
  	alert(showStr);
  //	parent.close();
  	
    //ִ����һ������
    top.window.close();
  }
 
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit1( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="�����ɹ���";  	
  	showInfo.close();
  	alert(showStr);  	
  //	parent.close();
  	
    //ִ����һ������
  }
  //initUWSpecGrid();
 // QueryPolSpecGrid(mContNo,mInsuredNo);
  //queryRiskAddFee(mContNo,mInsuredNo);
  //initOldRislPlanGrid('');
  //queryOldRiskPlan();
  //onRiskTabChange(1);
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}
         

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}


function manuchkspecmain()
{
	document.getElementById("fm").submit();
}

function QueryPolSpecGrid()
{
	// ��ʼ�����
	// ��дSQL���
	//alert("QueryPolSpecGrid"+tContNo);
	var strSQL = "";
	var i, j, m, n;	
	var tProposalContNo;
	//tongmeng 2008-10-08 add
	//���ú�ͬ��Լ��
				
               
	var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql4";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);
	strSQL=mySql1.getString();
	
  arrResult=easyExecSql(strSQL);
  
 
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = UWSpecGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
 
  return true;	
}


function getSpecGridCho2()
{
    var tSelNo = UWSpecGrid.getSelNo()-1;
	//alert('tSelNo'+tSelNo);
	var proposalcontno = UWSpecGrid.getRowColData(tSelNo,5);
	var serialno = UWSpecGrid.getRowColData(tSelNo,2);//alert("serialno=="+serialno);
	//var tContent = fm.Remark.value;
	
    
    var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql7";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(proposalcontno);
	mySql1.addSubPara(serialno);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);
	mySql1.addSubPara("1");
	strSQL=mySql1.getString();
	
  var arrResult = easyExecSql(strSQL);
    if(arrResult!=null)
    {
			fm.SpecTemp.value=arrResult[0][1];
			fm.HealthSpecTemp.value=arrResult[0][0];
			fm.SpecTempname.value=arrResult[0][3];
			fm.HealthSpecTempName.value=arrResult[0][2];
			fm.NeedPrintFlag.value=arrResult[0][4];
			fm.IFNeedFlagName.value=arrResult[0][5];
          	fm.Remark.value = arrResult[0][6]; 
    }  
	
}

function getClickSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   //alert(tManageCom.length);
  // alert(sql_temp);
   showCodeList('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}

function getClickUpSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   showCodeListKey('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}

function queryInsuredInfo()
{
	var tContNo = fm.ContNo.value;
	var tEdorNo = fm.EdorNo.value;
	var tInsuredNo = fm.InsuredNo.value;
   var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql1";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdorNo);
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	mySql1.addSubPara(tInsuredNo);
	strSQL=mySql1.getString();
	
  arrResult=easyExecSql(strSQL);
  
    if(arrResult!=null)	
    {
        DisplayInsured(arrResult);
    }
 var strSQL2 = "";
	var sqlid1="BQChangeRiskPlanInputSql2";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdorNo);
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	mySql1.addSubPara(tInsuredNo);
	strSQL2=mySql1.getString();
  arrResult2 = easyExecSql(strSQL2);
    if(arrResult2!=null)
    {
        fm.UWIdea.value=arrResult2[0][0];
    }
}

function DisplayInsured(arrResult)
{
    try{document.all('InsuredName').value= arrResult[0][0]; }catch(ex){};
    try{document.all('InsuredSex').value= arrResult[0][1]; }catch(ex){};
    try{document.all('RelationToAppnt').value= arrResult[0][4]; }catch(ex){};
    try{document.all('RelationToMainInsured').value= arrResult[0][5]; }catch(ex){};
    try{document.all('InsuredOccupationCode').value= arrResult[0][6]; }catch(ex){};
    try{document.all('InsuredOccupationType').value= arrResult[0][7]; }catch(ex){};
    try{document.all('InsuredPluralityType').value= arrResult[0][8]; }catch(ex){};
    try{document.all('InsuredAge').value= calPolCustomerAge(arrResult[0][3],arrResult[0][2]); }catch(ex){};
    console.log(document.all('InsuredOccupationCode'));
    quickGetName('occupationCode',document.all('InsuredOccupationCode'),document.all('InsuredOccupationCodeName'));
    quickGetName('OccupationType',fm.InsuredOccupationType,fm.InsuredOccupationTypeName);
    quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
    quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
}

function calPolCustomerAge(BirthDate,PolAppntDate)
{
	var age ="";
	if(BirthDate=="" || PolAppntDate=="")
	{
		age ="";
		return age;
	}
	var arrBirthDate = BirthDate.split("-");
	if (arrBirthDate[1].length == 1) arrBirthDate[1] = "0" + arrBirthDate[1];
	if (arrBirthDate[2].length == 1) arrBirthDate[2] = "0" + arrBirthDate[2];
//	alert("����"+arrBirthDate);	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];
//	alert("Ͷ������"+arrPolAppntDate);
	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	//��ǰ�´��ڳ�����
	//alert(arrPolAppntDate[1] + " | " + arrBirthDate[1] + " | " + (arrPolAppntDate[1] > arrBirthDate[1]));
	if (arrPolAppntDate[1] > arrBirthDate[1]) 
	{
		age = age + 1;
		return age;
	}
	//��ǰ��С�ڳ�����
	else if (arrPolAppntDate[1] < arrBirthDate[1]) 
	{
		return age;
	}
  	//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
	else if (arrPolAppntDate[2] >= arrBirthDate[2])
	{
		age = age + 1;
		return age;
  	}
	else
	{
		return age;
	}
}


var cacheWin=null;
function quickGetName(strCode, showObjc, showObjn) {
	showOneCodeNameOfObj(strCode, showObjc, showObjn);
}

function queryOldRiskPlan()
{
	
	  
	var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql3";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);
	strSQL=mySql1.getString();
	turnPage_OldRisk.queryModal(strSQL, OldRislPlanGrid); 
}

//tongmeng 2008-10-09 add
//�޸����ֺ˱�����
function makeRiskUWState()
{
	//У���Ƿ������ֱ�ѡ��
	var tSelNo = OldRislPlanGrid.getSelNo();
  if(tSelNo<=0)
 	{
 			alert("��ѡ�����ֱ�����");
 			return;
 	}
 //	alert('1'); 		
 		var olduwstate = OldRislPlanGrid.getRowColData(tSelNo - 1,9);
 		var uwstate = OldRislPlanGrid.getRowColData(tSelNo - 1,10);
 		var polno = OldRislPlanGrid.getRowColData(tSelNo - 1,11);
 		var mainpolno = OldRislPlanGrid.getRowColData(tSelNo - 1,12);
 		fm.PolNo.value=polno;
 		if(uwstate==null || uwstate =="")
 		{
 			alert("����ѡ��˱�������");
 			return;
 		}
 		else if(uwstate == "c")
 		{
 			if(olduwstate == null || olduwstate == "")
 			{			     
 				alert("��ѡ����û��ԭ�˱����ۣ�������ά�ֽ���");
 				return false;
 			}
 			else
 			
 			uwstate = olduwstate;//ά��ԭ�����б�
 		}
 		fm.uwstate.value = uwstate;
 		document.all('flag').value = "risk";
 	//	alert('2:'+document.all('flag').value);
	fm.action="./BQInsuredUWInfoChk.jsp";
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
	//alert('3');
	document.getElementById("fm").submit();
}

//ln 2008-12-1 add
//�б��ƻ�������ۼ��ӷ�ԭ��¼��
function makeRiskUWIdea()
{
 //	alert('1');
 		document.all('flag').value = "Insured";
 	//	alert('2:'+document.all('flag').value);
	fm.action="./BQInsuredUWInfoChk.jsp";
	 var i = 0;
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    //alert('3');
	document.getElementById("fm").submit();
}


//ln 2009-1-7 add
//ȡ����ǰ������Լ
function cancleGiven1()
{
	fm.action="./BQCancleGivenChk.jsp";
	var i = 0;
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();//alert('3');
	document.getElementById("fm").submit();
}

//ln 2009-1-7 add
//ȡ����ǰ������Լ
function initCancleGiven()
{
	var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql1";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdorNo);
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	mySql1.addSubPara(tInsuredNo);
	strSQL=mySql1.getString();
	
    var str  = easyExecSql(strSQL);;
    if(str != null)
    	fm.cancleGiven.disabled = false;
    else
        fm.cancleGiven.disabled = true;
}


//����Ϊ�ӷѳ�������ú���
// ��ѯ��ť
function initlist()
{//alert(636);
	// ��дSQL���
	k++;
	
   
  var strSQL = "";
 
	var sqlid1="BQChangeRiskPlanInputSql5";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);
	strSQL=mySql1.getString();
	
	str  = easyQueryVer3(strSQL, 1, 0, 1);
	
	return str;
;

}

function afterCodeSelect( cCodeName, Field ) {
		//alert("111:"+cCodeName);
		if( cCodeName == "addfeetype" ) {
	
			if(Field.value=='02')
			{
				
			}
		}
				if(cCodeName == "PlanPay")
		{//alert(cCodeName);
				if(Field.value=='02')
				{
						//��õ�ǰ���������ֱ���
						var tSelNo = PolAddGrid.getSelNo()-1;
						var t = PolAddGrid.lastFocusRowNo;
						//alert('t:'+t+":"+tSelNo);
						
						//alert('tSelNo'+tSelNo);
						if(tSelNo<0)
						{
							/*
							alert('����ѡ��һ����¼!');
							return false;
							*/
							tSelNo = t;
						}
						//111801,111802,121705,121704
						var riskcode = PolAddGrid.getRowColData(tSelNo,1);
						if(riskcode==null||riskcode=='')
						{
							alert('����ѡ������!');
							return false;
						}
						else if(riskcode=='111801'||riskcode=='111802'
							||riskcode=='121705'||riskcode=='121704'
							||riskcode=='121701'||riskcode=='121702'
							
							//test
							//||riskcode=='112213'
							)
							{
								 PolAddGrid.setRowColData(tSelNo,5,'02',PolAddGrid);
					//			 var tSQL = "select medrate from ldoccupation where occupationcode='"+document.all('InsuredOccupationCode').value+"' ";
								    
								    var InsuredOccupationCode9 = document.all('InsuredOccupationCode').value;
								    var sqlid9="BQChangeRiskPlanInputSql9";
									var mySql9=new SqlClass();
									mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
									mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
									mySql9.addSubPara(InsuredOccupationCode9);//�������
									var tSQL = mySql9.getString();
									
								 
								 var tStr1  = easyExecSql(tSQL);
								 //alert(tStr1);
								 if(tStr1!=null)
								 {
								 	 PolAddGrid.setRowColData(tSelNo,6,tStr1[0][0],PolAddGrid);
								 }
								 return ;
							}
				}
		}


		
		if( cCodeName == "addfeedatetype" ) {
			//alert(cCodeName);
			var tFieldValue=Field.value;
			//alert(tFieldValue);
			//alert(eval(poladdgridid));
			//alert(poladdgridid);
//			alert(eval(poladdgridid).all('PolAddGrid9').value);
			var temppoladdgridid =  eval(poladdgridid);
			//alert(temppoladdgridid.all('PolAddGrid9').value);
//			var span = PolAddGrid.getSelNo()-1;
//			var span = PolAddGrid.getSelNo()-1;
//			spanObj = span;
//			if(spanObj<0){
//			   alert("��ѡ����Ҫ���в����ļӷѼ�¼!");
//			   return false;
//		    }
			//alert(temppoladdgridid.all('PolAddGrid9').value);
		    if(temppoladdgridid.all('PolAddGrid9').value==null||temppoladdgridid.all('PolAddGrid9').value==""){
		    	alert("����ѡ��Ҫ�ӷѵ�������Ϣ��");
		    	temppoladdgridid.all('PolAddGrid15').value="";
		    	return false;
		    }
			//�ӷѿ�ʼʱ������
			var tAddForm = temppoladdgridid.all('PolAddGrid15').value;
			//��������
			var tPolNo = temppoladdgridid.all('PolAddGrid12').value;//alert("tPolNo=="+tPolNo);
			//�ӷ����α���
			var tDutyCode = temppoladdgridid.all('PolAddGrid3').value;
			//�ɷѼƻ�����
			var tPayPlanCode = temppoladdgridid.all('PolAddGrid14').value;
			
//			var tSql = "select count(*) from lpprem a where a.payplancode like '000000%' "
//    			+" and contno='"+fm.ContNo.value+"' and edorno <> '"+fm.EdorNo.value+"' "
//    			+" and exists(select 1 from lpedoritem b where b.edorno = a.edorno and b.edorstate = '0')";
//		  	var arrResult =  easyExecSql(tSql);//prompt("",tSql);
//		  	if(arrResult[0][0]!="0"){
//		  		if(tAddForm == "0"||tAddForm == "1"){
//		  			alert("��������ȫ�ӷѣ��ӷѿ�ʼʱ������ֻ��Ϊ���ڣ�");
//		  			tFieldValue="2";
////		  			PolAddGrid.setRowColData(spanObj,15,'2');
//		  			temppoladdgridid.all('PolAddGrid15').value='2';
//		  			//return false;
//		  		}
//		  	}//alert(676);
		  	//alert(tPayPlanCode);
			var tSql ;
		  	if(tPayPlanCode != null && tPayPlanCode!='')
		  	{
//		  		tSql = "select count(*) from lcprem where polno='"+tPolNo+"' "
//		  	     +" and dutycode='"+tDutyCode+"' and payplancode='"+tPayPlanCode+"' ";
//		  	     //prompt('',tSql);
		  		
				    var sqlid10="BQChangeRiskPlanInputSql10";
					var mySql10=new SqlClass();
					mySql10.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
					mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
					mySql10.addSubPara(tPolNo);//�������
					mySql10.addSubPara(tDutyCode);//�������
					mySql10.addSubPara(tPayPlanCode);//�������
					tSql = mySql10.getString();
					
		  		
			  	var arrResult1 =  easyExecSql(tSql);
			  	if(arrResult1[0][0]!="0"){
			  		if(tAddForm != "3" && tAddForm != "4"){
			  			alert("�Ǳ��α�ȫ���ɵļӷѣ��ӷѿ�ʼʱ������ֻ��Ϊ��ֹ��ά��ԭ������");
			  			temppoladdgridid.all('PolAddGrid15').value='';
			  			return false;
			  		}
			  	}
			  	else
			  	{
			  		if(tAddForm != "0" && tAddForm != "1" && tAddForm != "2"){
			  			alert("Ϊ���α�ȫ�ӷѣ��ӷѿ�ʼʱ������ֻ��Ϊ׷�ݡ����ڼӷѻ����ڼӷѣ�");
			  			temppoladdgridid.all('PolAddGrid15').value='';
			  			return false;
			  		}
			  	}
		  	}		  	
		  	else
		  	{
		  		if(tAddForm != "0" && tAddForm != "1" && tAddForm != "2"){
		  			alert("Ϊ���α�ȫ�ӷѣ��ӷѿ�ʼʱ������ֻ��Ϊ׷�ݡ����ڼӷѻ����ڼӷѣ�");
		  			temppoladdgridid.all('PolAddGrid15').value='';
		  			return false;
		  		}
		  	}
		    
	//	    var forPayIntv = "select payintv from lppol where edorno='"+fm.EdorNo.value+"' and contno='"+fm.ContNo.value+"' and polno='"+tPolNo+"'";
		    
		    var sqlid11="BQChangeRiskPlanInputSql11";
			var mySql11=new SqlClass();
			mySql11.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
			mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
			mySql11.addSubPara(fm.EdorNo.value);//�������
			mySql11.addSubPara(fm.ContNo.value);//�������
			mySql11.addSubPara(tPolNo);//�������
			var forPayIntv = mySql11.getString();
		    
		    var arrPayIntv =  easyExecSql(forPayIntv);
		    if(!arrPayIntv){
		    	alert("��ѯ���Ѽ��ʧ�ܣ�");
		    	return false;
		    }
		    var tPayIntv = arrPayIntv[0][0];
//		    forPayIntv = "select paystartdate,paytodate,payenddate from lpprem where edorno='"+fm.EdorNo.value+"' and polno='"+tPolNo+"' and payplancode not like '000000%'";
		   
		    var sqlid12="BQChangeRiskPlanInputSql12";
			var mySql12=new SqlClass();
			mySql12.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
			mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
			mySql12.addSubPara(fm.EdorNo.value);//�������
			mySql12.addSubPara(tPolNo);//�������
			forPayIntv = mySql12.getString();
		    
		    arrPayIntv =  easyExecSql(forPayIntv);
		    if(!arrPayIntv){
		    	alert("��ѯ�ӷ���ֹ����ʧ�ܣ�");
		    	return false;
		    }
		    //���ڽ�������
		    var tBeginAdd = arrPayIntv[0][0];
		    var tToAdd = arrPayIntv[0][1];
		    var tEndAdd = arrPayIntv[0][2]//����ֹ��;
		    //�ӷ�����
		  /*	var strSQL1 = "select "
	       			+ " firstpaydate,paytodate,payenddate "
	       			+ " from lpprem a "
         			+ " where a.edorno='"+fm.EdorNo.value+"' and a.contno='"+fm.ContNo.value+"' and a.polno='"+tPolNo+"' "
         			+ " and dutycode= and payplancode=";
		    //prompt("",strSQL);
		    var arrRes =  easyExecSql(strSQL1);
		    if(arrRes==null||arrRes==""){
		    	alert("��ѯ�ӷ�����ʧ�ܣ�");
		    	return false;
		    }
		    //���ڽ�������
		    var tBeginAdd=arrRes[0][0];*/
		    //�õ���ǰ����
	  		var CurrentDate = getCurrentDate("-");
	  		
	  		//���Ѽ��Ϊ�����ӷѿ�ʼʱ������ֻ��Ϊ׷��
	  		if(tPayIntv=="0"){
	  			if(tAddForm == "2"||tAddForm == "1"){
	  				alert("�ñ������Ѽ��Ϊ�������ӷѿ�ʼʱ������ֻ��Ϊ׷�ݣ�");
//	  				PolAddGrid.setRowColData(spanObj,15,'0');
					tFieldValue = "0";

//	  				PolAddGrid.setRowColData(spanObj,9,tBeginAdd);	  				
	  			}
	  			temppoladdgridid.all('PolAddGrid15').value=tFieldValue;
	  		}
	  		if(tFieldValue=='0')	
	  		{
	  			temppoladdgridid.all('PolAddGrid9').value=tBeginAdd;
	  			temppoladdgridid.all('PolAddGrid10').value=tEndAdd;
	  		}
	  			
	  		else if(tFieldValue=='1' || tFieldValue=='2')//���ڡ�����	
	  		{
	  			temppoladdgridid.all('PolAddGrid9').value=tToAdd;
	  			temppoladdgridid.all('PolAddGrid10').value=tEndAdd;
	  		}
	  			
	  		else if(tFieldValue=='3')//��ֹ
	  		{
	  			temppoladdgridid.all('PolAddGrid9').value=tBeginAdd;
	  			temppoladdgridid.all('PolAddGrid10').value=tToAdd;
	  		}
	  		else if(tFieldValue=='4')//ά��ԭ����
	  		{
	  			temppoladdgridid.all('PolAddGrid9').value=tBeginAdd;
	  			temppoladdgridid.all('PolAddGrid10').value=tEndAdd;
	  		}	  						
	  			    
		}
}

//У�� �ӷ�����
function CheckAddType(tSelNo)
{
	var tRiskCode = PolAddGrid.getRowColData(tSelNo,1);
	var tDutyCode = PolAddGrid.getRowColData(tSelNo,3);
	var tAddFeeType = PolAddGrid.getRowColData(tSelNo,4);
	var tAddFeeMethod = PolAddGrid.getRowColData(tSelNo,5);
	var tAddFeePoint = PolAddGrid.getRowColData(tSelNo,6);
	/*var tSQL = " select 1 from LMDutyPayAddFee where "
           + " riskcode='"+tRiskCode+"' and dutycode='"+tDutyCode+"' and addfeetype='"+tAddFeeType+"' "
           + " and  addfeeobject='"+tAddFeeMethod+"'";
    //prompt('',tSQL);
   var arrRes =  easyQueryVer3(tSQL, 1, 0, 1);
   //alert("arrRes:"+arrRes);
   if(arrRes==null||!arrRes)
   {
   	  alert("������������мӷѣ����߸�����û�д˼ӷ�����!");
   	  PolAddGrid.getRowColData(tSelNo,7,'0');
   	  return false;
   }*/

  if((tAddFeeMethod!=null && tAddFeeMethod=='01')&&(tAddFeePoint==null||tAddFeePoint<='0'))
  {
  	alert("��EMֵ�ӷ�,������ӷ�����!");
  	return false;
  }
  
  return true;
}

//У�� �ӷѿ�ʼʱ������
function CheckAddDateType(tSelNo,tDutyCode,tPayPlanCode,tPolNo,tAddForm)
{
	//alert("tSelNo:"+tSelNo+"tAddForm:"+tAddForm);
	var tFieldValue = tAddForm ;
	var tThisFlag = true;//�Ǳ��α�ȫ�ӷ�
	var tSql = "";
	/*tSql = "select count(*) from lpprem a where a.payplancode like '000000%' "
    			+" and contno='"+fm.ContNo.value+"' and edorno <> '"+fm.EdorNo.value+"' "
    			+" and exists(select 1 from lpedoritem b where b.edorno = a.edorno and b.edorstate = '0')";
		  	var arrResult =  easyExecSql(tSql);//prompt("",tSql);
		  	if(arrResult[0][0]!="0"){
		  		if(tAddForm == "0"||tAddForm == "1"){
		  			alert("��������ȫ�ӷѣ��ӷѿ�ʼʱ������ֻ��Ϊ���ڣ�");
		  			tFieldValue="2";
//		  			PolAddGrid.setRowColData(spanObj,15,'2');
		  			PolAddGrid.getRowColData(tSelNo,15,'2');
		  			//return false;
		  		}
		  	}*/
		  	if(tPayPlanCode != null && tPayPlanCode!='')
		  	{
//		  		tSql = "select count(*) from lcprem where polno='"+tPolNo+"' "
//		  	     +" and dutycode='"+tDutyCode+"' and payplancode='"+tPayPlanCode+"' ";
//		  	     //prompt('',tSql);
		  		
		  		var sqlid13="BQChangeRiskPlanInputSql13";
				var mySql13=new SqlClass();
				mySql13.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
				mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
				mySql13.addSubPara(tPolNo);//�������
				mySql13.addSubPara(tDutyCode);//�������
				mySql13.addSubPara(tPayPlanCode);//�������
				tSql = mySql13.getString();
		  		
			  	var arrResult1 =  easyExecSql(tSql);
			  	if(arrResult1[0][0]!="0"){
			  		tThisFlag = false;
			  		if(tAddForm != "3" && tAddForm != "4"){
			  			alert("�Ǳ��α�ȫ���ɵļӷѣ��ӷѿ�ʼʱ������ֻ��Ϊ��ֹ��ά��ԭ������");
			  			PolAddGrid.getRowColData(tSelNo,15,'');
			  			return false;
			  		}
			  	}
			  	else
			  	{
			  		tThisFlag = true;
			  		if(tAddForm != "0" && tAddForm != "1" && tAddForm != "2"){
			  			alert("Ϊ���α�ȫ�ӷѣ��ӷѿ�ʼʱ������ֻ��Ϊ׷�ݡ����ڼӷѻ����ڼӷѣ�");
			  			PolAddGrid.getRowColData(tSelNo,15,'');
			  			return false;
			  		}
			  	}
		  	}		  	
		  	else
		  	{
		  		tThisFlag = true;
		  		if(tAddForm != "0" && tAddForm != "1" && tAddForm != "2"){
		  			alert("Ϊ���α�ȫ�ӷѣ��ӷѿ�ʼʱ������ֻ��Ϊ׷�ݡ����ڼӷѻ����ڼӷѣ�");
		  			PolAddGrid.getRowColData(tSelNo,15,'');
		  			return false;
		  		}
		  	}
		    
//		    var forPayIntv = "select payintv from lppol where edorno='"+fm.EdorNo.value+"' and contno='"+fm.ContNo.value+"' and polno='"+tPolNo+"'";
		   

	  		var sqlid14="BQChangeRiskPlanInputSql14";
			var mySql14=new SqlClass();
			mySql14.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
			mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
			mySql14.addSubPara(fm.EdorNo.value);//�������
			mySql14.addSubPara(fm.ContNo.value);//�������
			mySql14.addSubPara(tPolNo);//�������
			var forPayIntv = mySql14.getString();
		    
		    var arrPayIntv =  easyExecSql(forPayIntv);
		    if(!arrPayIntv){
		    	alert("��ѯ���Ѽ��ʧ�ܣ�");
		    	return false;
		    }
		    var tPayIntv = arrPayIntv[0][0];
//		    forPayIntv = "select paystartdate,paytodate,payenddate from lpprem where edorno='"+fm.EdorNo.value+"' and polno='"+tPolNo+"' and payplancode not like '000000%'";
		    
		    var sqlid15="BQChangeRiskPlanInputSql15";
			var mySql15=new SqlClass();
			mySql15.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
			mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
			mySql15.addSubPara(fm.EdorNo.value);//�������
			mySql15.addSubPara(tPolNo);//�������
			forPayIntv = mySql15.getString();
		    
		    arrPayIntv =  easyExecSql(forPayIntv);
		    if(!arrPayIntv){
		    	alert("��ѯ�ӷ���ֹ����ʧ�ܣ�");
		    	return false;
		    }
		    //���ڽ�������
		    var tBeginAdd = arrPayIntv[0][0];
		    var tToAdd = arrPayIntv[0][1];
		    var tEndAdd = arrPayIntv[0][2]//����ֹ��;
		   
		    //�õ���ǰ����
	  		var CurrentDate = getCurrentDate("-");
	  		
	  		//���Ѽ��Ϊ�����ӷѿ�ʼʱ������ֻ��Ϊ׷��
	  		if(tPayIntv=="0" && tThisFlag == true){
	  			if(tAddForm == "2"||tAddForm == "1"){
	  				alert("�ñ������Ѽ��Ϊ�������ӷѿ�ʼʱ������ֻ��Ϊ׷�ݣ�");
//	  				PolAddGrid.setRowColData(spanObj,15,'0');
					tFieldValue = "0";
	  				PolAddGrid.getRowColData(tSelNo,15,'0');
//	  				PolAddGrid.setRowColData(spanObj,9,tBeginAdd);	  				
	  			}
	  		}
	  		if(tFieldValue=='0')	
	  		{
	  			PolAddGrid.getRowColData(tSelNo,9,tBeginAdd);
	  			PolAddGrid.getRowColData(tSelNo,10,tEndAdd);
	  		}
	  			
	  		else if(tFieldValue=='1' || tFieldValue=='2')//���ڡ�����	
	  		{
	  			PolAddGrid.getRowColData(tSelNo,9,tToAdd);
	  			PolAddGrid.getRowColData(tSelNo,10,tEndAdd);
	  		}
	  			
	  		else if(tFieldValue=='3')//��ֹ
	  		{
	  			PolAddGrid.getRowColData(tSelNo,9,tBeginAdd);
	  			PolAddGrid.getRowColData(tSelNo,10,tToAdd);
	  			
	  		}
	  		else if(tFieldValue=='4')//ά��ԭ����
	  		{
	  			PolAddGrid.getRowColData(tSelNo,9,tBeginAdd);
	  			PolAddGrid.getRowColData(tSelNo,10,tEndAdd);
	  			
	  		}
	  	return true;
}

//�ӷѱ���
function addFeeSave()
{
    var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		 alert("��ѡ����Ҫ����ļӷѼ�¼!");
		 return false;
	}
	
	var tPolNo = PolAddGrid.getRowColData(tSelNo,12);
	var tDutyCode = PolAddGrid.getRowColData(tSelNo,3);
	var tPayPlanType = PolAddGrid.getRowColData(tSelNo,4);
	var tPayPlanCode = PolAddGrid.getRowColData(tSelNo,14);
	var tAddForm = PolAddGrid.getRowColData(tSelNo,15);	
	
	//У�� �ӷѿ�ʼʱ������
	if(!CheckAddDateType(tSelNo,tDutyCode,tPayPlanCode,tPolNo,tAddForm))
		return false;
	
	//У��ӷѷ�ʽ�Ƿ����
	if((tAddForm=='0' ||tAddForm=='1'||tAddForm=='2') && !CheckAddType(tSelNo))
		return false;
	
	if(tPayPlanCode == null || tPayPlanCode == '')
	{}
	else
	{		
//		var strSQL =" select dutycode,payplantype,AddFeeDirect,suppriskscore,prem from lpprem " 
//	           + " where polno='"+tPolNo+"' and dutycode='"+tDutyCode+"' and payplancode='"+tPayPlanCode+"' "
//	           +" and edorno='"+fm.EdorNo.value+"'";
//	    //prompt('',strSQL); 
		
		var sqlid16="BQChangeRiskPlanInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
		mySql16.addSubPara(tPolNo);//�������
		mySql16.addSubPara(tDutyCode);//�������
		mySql16.addSubPara(tPayPlanCode);//�������
		mySql16.addSubPara(fm.EdorNo.value);//�������
		var strSQL = mySql16.getString();
		
	    var arrResult=easyExecSql(strSQL);
	    if(arrResult!=null)
	    {
	    	//У������ǳб����ļӷѣ������޸ı����Ϣ��ֻ���޸ļӷѿ�ʼʱ������
	        //��ԭΪ�б���Ϣ
	        PolAddGrid.setRowColData(tSelNo,3,arrResult[0][0]);
	        PolAddGrid.setRowColData(tSelNo,4,arrResult[0][1]);
			PolAddGrid.setRowColData(tSelNo,5,arrResult[0][2]);
			PolAddGrid.setRowColData(tSelNo,6,arrResult[0][3]);
			PolAddGrid.setRowColData(tSelNo,7,arrResult[0][4]);
	    }
	}  
	
	if(tAddForm != null && tAddForm!='')
	{
		if(tAddForm =='3' || tAddForm =='4')
		{
			//����ʱ�����Ͳ���ֱ����ʾ�ɹ�
//			var strSQL =" select addform from lpprem " 
//		           + " where edorno='"+fm.EdorNo.value+"' and polno='"+tPolNo+"' and dutycode='"+tDutyCode+"' and payplancode='"+tPayPlanCode+"' ";
//		    //prompt('',strSQL); 
//			
			var sqlid17="BQChangeRiskPlanInputSql17";
			var mySql17=new SqlClass();
			mySql17.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
			mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
			mySql17.addSubPara(fm.EdorNo.value);//�������
			mySql17.addSubPara(tPolNo);//�������
			mySql17.addSubPara(tDutyCode);//�������
			mySql17.addSubPara(tPayPlanCode);//�������
			var strSQL = mySql17.getString();
			
			var arrResult=easyExecSql(strSQL);
			if(arrResult!=null && arrResult[0][0]==tAddForm)
			{
				  var FlagStr = "Succ";
				  var Content = "�˹��˱��ӷѳɹ�!";
				  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
				  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
					var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
					var iWidth=550;      //�������ڵĿ��; 
					var iHeight=250;     //�������ڵĸ߶�; 
					var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
					var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
					showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

					showInfo.focus();				  
					afterSubmit(FlagStr,Content);
				  return true;
			}
		}		
		
		//У������б��α�ȫ��ͬ���ͼӷ� �����һ������ֹ����Խ��мӷ�
//		var strSQL1 =" select addform from lpprem " 
//	           + " where edorno='"+fm.EdorNo.value+"' and polno='"+tPolNo+"' "
//	           + " and dutycode='"+tDutyCode+"' and payplantype='"+tPayPlanType+"' and PayPlanCode like '000000%' "
//	           + " and (addform is null or addform='' or addform<>'3')";//�ų���ֹ�ļӷ�
//	    //prompt('',strSQL1); 
		
		var sqlid18="BQChangeRiskPlanInputSql18";                              
		var mySql18=new SqlClass();                                            
		mySql18.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���         
		mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id                                
		mySql18.addSubPara(fm.EdorNo.value);//�������                             
		mySql18.addSubPara(tPolNo);//�������                                      
		mySql18.addSubPara(tDutyCode);//�������                                   
		mySql18.addSubPara(tPayPlanCode);//�������                                
		var strSQL1 = mySql18.getString();                                      
		
		var arrResult1=easyExecSql(strSQL1);
		if(arrResult1!=null)
		{
		    if(tPayPlanCode == null || tPayPlanCode == '') //lpprem���з���ֹ��¼��������������¼
		    {
		       alert("������ֹ��ɾ����ͬ�ӷ����ļӷѼ�¼!");
			 	return false;
		    }
		    else //�����м�¼���������޸�
		    {
			    if( (tAddForm != arrResult1[0][0]) || (tAddForm=='3') 
					    ||(tAddForm=='4' && arrResult1[0][0] ==''))
				{}
				else
				{
					alert("������ֹ��ɾ����ͬ�ӷ����ļӷѼ�¼!");
	//				alert(arrResult1[0][0]);
	//				PolAddGrid.setRowColData(tSelNo,15,arrResult1[0][0]);
			 		return false;
				}
			}
		}
	}	
    
	fm.action="./BQManuChangeRiskAddChk.jsp";
	var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit()();
}

//��ʼ����ѯ�ӷ�����
function queryRiskAddFee()
{
	var tSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql6";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);
	tSQL=mySql1.getString();
	 turnPage_AddFee.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);          
	//�ж��Ƿ��ѯ�ɹ�
  if (turnPage_AddFee.strQueryResult) {  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage_AddFee.arrDataCacheSet = decodeEasyQueryResult(turnPage_AddFee.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage_AddFee.pageDisplayGrid = PolAddGrid;    
          
  //����SQL���
  turnPage_AddFee.strQuerySql     = tSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage_AddFee.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage_AddFee.getData(turnPage_AddFee.arrDataCacheSet, turnPage_AddFee.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage_AddFee.pageDisplayGrid);
  }
}

//�Զ�����ӷ���Ϣ
function AutoCalAddFee(span)
{
	spanObj = span;

  
  //alert(document.all( spanObj ).all( 'PolAddGrid4' ).value);
  //�����ж���û�мӷ��㷨
  //���ֱ���
  var tRiskCode = document.all( spanObj ).all( 'PolAddGrid1' ).value;
  // alert(tRiskCode);
  //�ӷ����
  var tAddFeeType = document.all( spanObj ).all( 'PolAddGrid4' ).value;
  //�ӷѷ�ʽ
  var tAddFeeMethod = document.all( spanObj ).all( 'PolAddGrid5' ).value;
  //���α���
  var tDutyCode = document.all( spanObj ).all( 'PolAddGrid3' ).value;
  
  var tEdorType = document.all( 'EdorType' ).value;
  
  var tInsuredNo = document.all( 'InsuredNo' ).value;
  
  if(tRiskCode==null||tRiskCode=='')
  {
  	alert("��ѡ������!");
  	return;
  }
  if(tAddFeeType==null||tAddFeeType=='')
  {
  	alert("��ѡ��ӷ����!");
  	return;
  }
  if(tAddFeeMethod==null||tAddFeeMethod=='')
  {
  	alert("��ѡ��ӷѷ�ʽ!");
  	return;
  }
  
 //  var tSelNo = PolAddGrid.lastFocusRowNo;
 //  alert(tSelNo);
 //alert(mInsuredNo);

//  var tSQL = " select 1 from LMDutyPayAddFee where "
//           + " riskcode='"+tRiskCode+"' and dutycode='"+tDutyCode+"' and addfeetype='"+tAddFeeType+"' "
//           + " and  addfeeobject='"+tAddFeeMethod+"'";
  
    var sqlid19="BQChangeRiskPlanInputSql19";                              
	var mySql19=new SqlClass();                                            
	mySql19.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���         
	mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id                                
	mySql19.addSubPara(tRiskCode);//�������                             
	mySql19.addSubPara(tDutyCode);//�������                                      
	mySql19.addSubPara(tAddFeeType);//�������                                   
	mySql19.addSubPara(tAddFeeMethod);//�������                                
	var tSQL = mySql19.getString();     
  
   var arrRes =  easyQueryVer3(tSQL, 1, 0, 1);
   //alert("arrRes:"+arrRes);
   if(arrRes==null||!arrRes)
   {
   	  alert("������������мӷѣ����߸�����û�д˼ӷ�����!");
   	  document.all( spanObj ).all( 'PolAddGrid7' ).value='0';
   	  return;
   }
   //׼������
  var tPolNo =  document.all( spanObj ).all( 'PolAddGrid12' ).value;
  var tAddFeePoint = document.all( spanObj ).all( 'PolAddGrid6' ).value;
  if(tAddFeeMethod=='01'&&(tAddFeePoint==null||tAddFeePoint<='0'))
  {
  	alert("��EMֵ�ӷ�,������ӷ�����!");
  	return;
  }
 // if(tAddFeeMethod!='01' && !(tAddFeePoint==null||tAddFeePoint==''))
 // {
 // 	 alert("�����ӷѷ�ʽ,�벻Ҫ¼��ӷ�����!");
 // 	 return;
 // }
    var i = 0;
    var showStr="���ڼ���ӷ����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   //�ύ���ݿ�
  fm.action= "./BQCalHealthAddFeeChk.jsp?AddFeePolNo="+tPolNo+"&AddFeeRiskCode="+tRiskCode+"&AddFeeDutyCode="+tDutyCode+"&AddFeeType="+tAddFeeType+"&AddFeeMethod="+tAddFeeMethod+"&AddFeeInsuredNo="+tInsuredNo+"&AddFeePoint="+tAddFeePoint+"&EdorType="+tEdorType;
  document.getElementById("fm").submit(); //�ύ
}


//�ӷ�ɾ��
//created by guanwei
function deleteData()
{ 
	var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		 alert("��ѡ����Ҫɾ���ļӷѼ�¼!");
		 return false;
	}
	
	var tPolNo = PolAddGrid.getRowColData(tSelNo,12);
	var tDutyCode = PolAddGrid.getRowColData(tSelNo,3);
	var tPayPlanCode = PolAddGrid.getRowColData(tSelNo,14);
	if(tPayPlanCode==null||tPayPlanCode=='')
	{
		 alert("�ü�¼��û�б���,��ˢ��ҳ�����²�ѯ!");
		 return false;
	}
	
	//У������ǳб����ļӷѣ�����ɾ��
//	var strSQL =" select 1 from lcprem " 
//           + " where polno='"+tPolNo+"' and dutycode='"+tDutyCode+"' and payplancode='"+tPayPlanCode+"' ";
//    //prompt('',strSQL);

	var sqlid20="BQChangeRiskPlanInputSql20";                              
	var mySql20=new SqlClass();                                            
	mySql20.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���         
	mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id                                
	mySql20.addSubPara(tPolNo);//�������                             
	mySql20.addSubPara(tDutyCode);//�������                                      
	mySql20.addSubPara(tPayPlanCode);//�������                                                   
	var strSQL = mySql20.getString();     
	
    var arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        alert("�Ǳ��α�ȫ���ɵļӷѣ�����ɾ����");
        return false;
    }
	//initForm(cPolNo,cContNo, cMissionID, cSubMissionID);
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./BQManuAddDelSave.jsp?DelPolNo="+tPolNo+"&DelDutyCode="+tDutyCode+"&DelPayPlanCode="+tPayPlanCode;
    document.getElementById("fm").submit();
  
  //var cPolNo= fm.PolNo2.value ;   
  //alert(cPolNo);
  
}


//����ӷ�����
function addFormMethodNew(span){
	
	showCodeList('addfeedatetype',[document.all(span).all('PolAddGrid15')],null,null,null,null,null,null);
	
	//showCodeListKey('addfeedatetype',[document.all(span).all('PolAddGrid15')],null,null,null,null,null,null);
	//alert("test:"+document.all(span).all('PolAddGrid15').value);
	//alert(span);
	poladdgridid = span;
	
}
//�����꽻�ӷ�����
function payYear(tBeginAdd,tFieldValue,CurrentDate){
	//2008-12-02
	//var tempDate=tBeginAdd.substr(0,4);
	/*
	 *ȡ���ڽ������ڵ������뵱ǰ���ڵ�������ȣ������ǰ����С�����ڽ��ѣ�����Ϊ��ǰ�������һ�������㷨Ҳһ��
	*/
	//���ڽ������ڵ��¡���
	//alert("wktest1"+temppoladdgrididtest);
	//alert("wktest2"+eval(poladdgridid));
	var temppoladdgridid =  eval(poladdgridid);
	//alert("wktest2"+eval(poladdgridid).all('PolAddGrid15').value);
	var tempDate=tBeginAdd.substr(4,tBeginAdd.length);
	//alert("tempDate2=="+tempDate2);return false;
	//��ǰ���ڵ��¡���
	var tempDate2=CurrentDate.substr(4,tBeginAdd.length);
	//��ǰ���ڵ����
	var tempDate3=CurrentDate.substr(0,4);
		  	
	
	var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql7";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdorNo);
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	mySql1.addSubPara(tInsuredNo);
	strSQL=mySql1.getString();
	var arrResult =  easyExecSql(strSQL);
	//prompt("",strSql);
	//׷��
	if(tFieldValue=="0"){
		//alert(678);
//		PolAddGrid.setRowColData(spanObj,9,tBeginAdd);
		temppoladdgridid.all('PolAddGrid9').value=tBeginAdd;
	}
	//����
	if(tFieldValue=="1"){
		//tempDate=tempDate+1;
		//var tempDate3=tempDate+1;
		//tBeginAdd=tempDate3+tempDate2;
		//alert("tBeginAdd=="+tBeginAdd);
		//PolAddGrid.setRowColData(spanObj,9,"2009-12-30");
		//alert("getCurrentDate=="+getCurrentDate("-"));
		//tBeginAdd=tempDate4+tempDate2;
		//PolAddGrid.setRowColData(spanObj,9,tBeginAdd);
		 		
		if(arrResult==null||arrResult==""){
			//alert(729);
//			PolAddGrid.setRowColData(spanObj,9,tempDate3+tempDate);
			temppoladdgridid.all('PolAddGrid9').value=tempDate3+tempDate
		}else{
			//alert("321324324");
			tempDate3 = parseInt(tempDate3)-1;
//			PolAddGrid.setRowColData(spanObj,9,tempDate3+tempDate);
			temppoladdgridid.all('PolAddGrid9').value=tempDate3+tempDate
		}
	}
	//����
	if(tFieldValue=="2"){
		if(arrResult==null||arrResult==""){
		//alert(739);
		tempDate3 = parseInt(tempDate3)+1;
//		PolAddGrid.setRowColData(spanObj,9,tempDate3+tempDate);
		temppoladdgridid.all('PolAddGrid9').value=tempDate3+tempDate;
		}else{
			//alert("321==");
//			PolAddGrid.setRowColData(spanObj,9,tempDate3+tempDate);
			temppoladdgridid.all('PolAddGrid9').value=tempDate3+tempDate
		}
	}
}
//�����½��ӷ�����
function payMonth(tBeginAdd,tFieldValue,CurrentDate){
	/*2008-12-31
	 *ȡ���ڽ������ڵ������뵱ǰ���ڵ�������ȣ������ǰ����С�����ڽ��ѣ�����Ϊ��ǰ�������һ�������㷨Ҳһ��
	*/
	//���ڽ������ڵ���
	//var tempDate=tBeginAdd.substr(8,tBeginAdd.length);
	//alert("tempDate2=="+tempDate2);return false;
	//��ֳ���ǰ���ڵ���
	var tempStr=CurrentDate.split("-");
	var tempDate = tempStr[2];
	var tempMonth = tempStr[1];
	var tempYear = tempStr[0];
	
	//var BeginDate=tBeginAdd.substr(8,tBeginAdd.length);
	//alert("tempDate2=="+tempDate2);return false;
	//��ֳ���ǰ���ڵ���
	var BeginStr=CurrentDate.split("-");
	var BeginDate = tempStr[2];
	
	var inttempDate = parseInt(tempDate);
	var intBeginDate = parseInt(BeginDate);
	//��ǰ���ڵ����
	//var tempDate3=CurrentDate.substr(0,4);
	//׷��
	if(tFieldValue=="0"){
//		PolAddGrid.setRowColData(spanObj,9,tBeginAdd);
		temppoladdgridid.document.all('PolAddGrid9').value=tBeginAdd;
	}
	//����
	if(tFieldValue=="1"){
		//tempDate=tempDate+1;
		//var tempDate3=tempDate+1;
		//tBeginAdd=tempDate3+tempDate2;
		//alert("tBeginAdd=="+tBeginAdd);
		//PolAddGrid.setRowColData(spanObj,9,"2009-12-30");
		//alert("getCurrentDate=="+getCurrentDate("-"));
		//tBeginAdd=tempDate4+tempDate2;
		//PolAddGrid.setRowColData(spanObj,9,tBeginAdd);
		 		
		if(inttempDate<intBeginDate){
			tempMonth = parseInt(tempMonth)-1;
//			PolAddGrid.setRowColData(spanObj,9,tempYear+"-"+tempMonth+"-"+tempDate);
			temppoladdgridid.document.all('PolAddGrid9').value=tempYear+"-"+tempMonth+"-"+tempDate;
		}else{
//			PolAddGrid.setRowColData(spanObj,9,tempYear+"-"+tempMonth+"-"+tempDate);
			temppoladdgridid.document.all('PolAddGrid9').value=tempYear+"-"+tempMonth+"-"+tempDate;
		}
	}
	//����
	if(tFieldValue=="2"){
		if(inttempDate<intBeginDate){
//			PolAddGrid.setRowColData(spanObj,9,tempYear+"-"+tempMonth+"-"+tempDate);
			temppoladdgridid.document.all('PolAddGrid9').value=tempYear+"-"+tempMonth+"-"+tempDate;
		}else{
			tempMonth = parseInt(tempMonth)+1;
//			PolAddGrid.setRowColData(spanObj,9,tempYear+"-"+tempMonth+"-"+tempDate);
			temppoladdgridid.document.all('PolAddGrid9').value=tempYear+"-"+tempMonth+"-"+tempDate;
		}
	}	  	
}
//���㼾���ӷ�����
function paySeason(tBeginAdd,tFieldValue,spanObj,CurrentDate){
	
}
//������꽻�ӷ�����
function payHalfYear(tBeginAdd,tFieldValue,spanObj,CurrentDate){

}
function verifyChoose(){
	var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0){
		 alert("����ѡ��һ���ӷѼ�¼!");
		 return false;
	}
}
