//Creator :���	
//Date :2008-08-18

var showInfo;
var mDebug="0";
var arrDataSet;
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null) //shwoInfo��ʲô��
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
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



function VersionStateQuery()
{
	window.open("./FrameVersionRuleQuery.jsp");
}
function queryClick()
{
	var VersionNo = document.all('VersionNo').value;

	if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��");
  	return;
  }
  
  var DistillMode = document.all('DistillMode').value;
	var DistillModeName = document.all('DistillModeName').value;
  showInfo=window.open("./FrameCostDataAcquisitionDefQuery.jsp?VersionNo=" + VersionNo  );
}

function CostDataBaseDefInputClick()
{
	var VersionNo = document.all('VersionNo').value;
	var AcquisitionID = document.all('AcquisitionID').value;
	var VersionState = document.all('VersionState').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ������ݲɼ�����Դ����");
  	return;
  }
  
  if (AcquisitionID == null||AcquisitionID == '')  
  {
  	alert("���Ƚ������ݲɼ������Ϣ��ѯ��Ȼ���ٽ������ݲɼ�����Դ����");
  	return;
  }

  var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from ficostdataacquisitiondef where versionno = '"+document.all('VersionNo').value+"' and AcquisitionID = '"+document.all('AcquisitionID').value+"' ";
  */
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.CostDataAcquisitionDefInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("CostDataAcquisitionDefInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all('VersionNo').value);//ָ������Ĳ���
		mySql1.addSubPara(document.all('AcquisitionID').value);//ָ������Ĳ���
		strSQL= mySql1.getString();
  
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  if(countjudge == "0")
  {
  	alert("����ɼ���Ϣ�����ڣ���ȷ�������Ӹ�����Ϣ���ٽ������ݲɼ�����Դ����");
  	return false;
  }
	showInfo=window.open("./FrameCostDataBaseDefInput.jsp?VersionNo=" + VersionNo  + "&VersionState=" + VersionState + "&AcquisitionID="+AcquisitionID);
}

function CostDataKeyDefInputClick()
{
	var VersionNo = document.all('VersionNo').value;
	var AcquisitionID = document.all('AcquisitionID').value;
	var VersionState = document.all('VersionState').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ������ݲɼ���������");
  	return;
  }
  
  if (AcquisitionID == null||AcquisitionID == '')  
  {
  	alert("���Ƚ������ݲɼ������Ϣ��ѯ��Ȼ���ٽ������ݲɼ���������");
  	return;
  }
  var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from ficostdataacquisitiondef where versionno = '"+document.all('VersionNo').value+"' and AcquisitionID = '"+document.all('AcquisitionID').value+"' ";
  */
  	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.CostDataAcquisitionDefInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("CostDataAcquisitionDefInputSql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(document.all('VersionNo').value);//ָ������Ĳ���
		mySql2.addSubPara(document.all('AcquisitionID').value);//ָ������Ĳ���
		strSQL= mySql2.getString();
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  if(countjudge == "0")
  {
  	alert("����ɼ���Ϣ�����ڣ���ȷ�������Ӹ�����Ϣ���ٽ������ݲɼ���������");
  	return false;
  }
  showInfo=window.open("./FrameCostDataKeyDefInput.jsp?VersionNo=" + VersionNo  + "&VersionState=" + VersionState + "&AcquisitionID="+AcquisitionID);
}

function submitForm()
{
	if(!beforeSubmit())
	{
		return false;
	}
  fm.OperateType.value = "INSERT||MAIN";
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
  fm.action = './CostDataAcquisitionDefSave.jsp';
  document.getElementById("fm").submit(); //�ύ
  //alert(1)
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
	if(!beforeSubmit())
	{
		return false;
	}
	if(fm.tempAcquisitionType.value != fm.AcquisitionType.value)
	{
		alert("���ö����ݲɼ����ͽ��и��ģ�����������ɼ�!");
		return false;
	}
	if(fm.tempCostOrDataID.value != fm.CostOrDataID.value)
	{
		alert("���öԷ��ñ�����и��ģ�����������ɼ�!");
		return false;
	}
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
  {
    fm.OperateType.value = "UPDATE||MAIN";
    var i = 0;
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './CostDataAcquisitionDefSave.jsp';
    document.getElementById("fm").submit(); //�ύ

  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ�����޸Ĳ�����");
  }
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
	if((document.all('VersionNo').value=="")||(document.all('VersionNo').value==null))
	 {
	 	alert("����ͨ����ѯȡ�ð汾��ţ�����");
	 	return false;
	 }
	 if((document.all('AcquisitionID').value=="")||(document.all('AcquisitionID').value==null))
	 {
	 	alert("��ȷ��Ҫɾ�������ݲɼ���ţ�����");
	 	return false;
	 }
	 
  if (confirm("��ȷʵҪɾ���ü�¼��"))
  {
    fm.OperateType.value = "DELETE||MAIN";
    var i = 0;
    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ�����������Ľ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './CostDataAcquisitionDefSave.jsp';
    document.getElementById("fm").submit();//�ύ

  }
}

function afterQuery1( arrQueryResult1 )
{
	var arrResult1 = new Array();
	if( arrQueryResult1 != null )
	{
   
		arrResult1 = arrQueryResult1;
		arrResult1[0][6] = arrResult1[0][6].split("@@").join("||");
		arrResult1[0][7] = arrResult1[0][7].split("@@").join("||");
		arrResult1[0][6] = arrResult1[0][6].split("@").join("|");
		arrResult1[0][7] = arrResult1[0][7].split("@").join("|");
		document.all('AcquisitionID').value = arrResult1[0][0];
		document.all('AcquisitionType').value = arrResult1[0][1];
		document.all('tempAcquisitionType').value = arrResult1[0][1];
		document.all('DataSourceType').value = arrResult1[0][2];
		document.all('CostOrDataID').value = arrResult1[0][3];
		document.all('tempCostOrDataID').value = arrResult1[0][3];
		document.all('DistillMode').value = arrResult1[0][4];
		 
	if(document.all('DistillMode').value=='1')
	{
		 classdiv.style.display='none';
		 sqldiv.style.display=''; 
		 document.all('DistillClass').value = '';
		 document.all('DistillClassForOne').value = '';
	}
			
  if(document.all('DistillMode').value=='2')
  {
		 classdiv.style.display='';
		 sqldiv.style.display='none';	
		 document.all('DistillSQL').value = '';
     document.all('DistillSQLForOne').value = '';
	}		
		document.all('Remark').value = arrResult1[0][5];
		document.all('DistillSQL').value = arrResult1[0][6];
		document.all('DistillSQLForOne').value = arrResult1[0][7];
		document.all('DistillClass').value = arrResult1[0][8];
		document.all('DistillClassForOne').value = arrResult1[0][9];


		document.all('addbutton').disabled = false;
		document.all('updatebutton').disabled = false;		
		document.all('deletebutton').disabled = false;
		
		if (document.all('VersionState').value == "01"||document.all('VersionState').value == "03"||document.all('VersionState').value == ""||document.all('VersionState').value == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}		
		showCodeName();
	}
	
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //�ͷš����ӡ���ť

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
    //resetForm();
	fm.OperateType.value="";
  }
  else
  {
    //alert(content);
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

		if(fm.OperateType.value == "DELETE||MAIN")
		{
			document.all('AcquisitionID').value = '';
			document.all('tempAcquisitionID').value = '';
    	document.all('AcquisitionType').value = '';
    	document.all('tempAcquisitionType').value = '';
    	document.all('DataSourceType').value = '';
    	document.all('CostOrDataID').value = '';
    	document.all('DistillMode').value = '';
    	document.all('DistillSQL').value = '';
    	document.all('DistillSQLForOne').value = '';
    	document.all('DistillClass').value = '';
    	document.all('DistillClassForOne').value = '';
    	document.all('Remark').value = '';
    	document.all('AcquisitionTypeName').value = '';
    	document.all('CostOrDataName').value = '';
    	document.all('DistillModeName').value = '';
    	document.all('DataSourceTypeName').value = '';
    	document.all('tempCostOrDataID').value = '';

    	
    	document.all('updatebutton').disabled = true;	
    	document.all('deletebutton').disabled = true;
    	classdiv.style.display='none';
		 	sqldiv.style.display='none'; 
		}
    fm.OperateType.value="";
   
  }

}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('VersionNo').value = arrResult[0][0];
		document.all('VersionState').value = arrResult[0][5];	
		document.all('VersionState2').value = arrResult[0][9];	
		
			document.all('AcquisitionID').value = '';
			document.all('tempAcquisitionID').value = '';
    	document.all('AcquisitionType').value = '';
    	document.all('tempAcquisitionType').value = '';
    	document.all('DataSourceType').value = '';
    	document.all('CostOrDataID').value = '';
    	document.all('DistillMode').value = '';
    	document.all('DistillSQL').value = '';
    	document.all('DistillSQLForOne').value = '';
    	document.all('DistillClass').value = '';
    	document.all('DistillClassForOne').value = '';
    	document.all('Remark').value = '';
    	document.all('AcquisitionTypeName').value = '';
    	document.all('CostOrDataName').value = '';
    	document.all('DistillModeName').value = '';
    	document.all('DataSourceTypeName').value = '';
    	document.all('tempCostOrDataID').value = ''

    	classdiv.style.display='none';
		 	sqldiv.style.display='none'; 
    //���汾״̬��Ϊ02-ά����ʱ����ɾ�İ�ťΪ��ɫ		
		if (arrResult[0][5] == "01"||arrResult[0][5] == "03"||arrResult[0][5] == ""||arrResult[0][5] == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		if (arrResult[0][5] == "02")
		{
			document.all('addbutton').disabled = false;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
			
		
		//������Common\javascript\Common.js�����ݴ���ѡ��Ĵ�����Ҳ���ʾ����
		showCodeName(); 

	}
}

function afterCodeSelect( cName, Filed)
{   
	if(fm.DistillMode.value=='1')
	{
		 classdiv.style.display='none';
		 sqldiv.style.display=''; 
		 document.all('DistillClass').value = '';
		 document.all('DistillClassForOne').value = '';
	}
			
  if(fm.DistillMode.value=='2')
  {
		 classdiv.style.display='';
		 sqldiv.style.display='none';	
		 document.all('DistillSQL').value = '';
     document.all('DistillSQLForOne').value = '';
	}			

}

function AcquisitionClick()
{
	var VersionNo = document.all('VersionNo').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��");
  	return;
  }
	initAcquisition();
	document.all('AcquisitionID').value ='';
	document.all('AcquisitionID').value = document.all('tempAcquisitionID').value;
}

function beforeSubmit()
{
	if((document.all('VersionNo').value=="")||(document.all('VersionNo').value==null))
	 {
	 	alert("����ͨ����ѯȡ�ð汾��ţ�����");
	 	return false;
	 }
	 if((document.all('AcquisitionID').value=="")||(document.all('AcquisitionID').value==null))
	 {
	 	alert("���ݲɼ����Ϊ�գ����Ƚ������룡����");
	 	return false;
	 }
	 if((document.all('AcquisitionType').value=="")||(document.all('AcquisitionType').value==null))
	 {
	  alert("���ݲɼ�����Ϊ�գ�");
	  return false
	 }
	 if((document.all('DataSourceType').value=="")||(document.all('DataSourceType').value==null))
	 {
	  alert("�ɼ����ݿ�����Ϊ�գ�");
	  return false
	 }
	 if((document.all('CostOrDataID').value=="")||(document.all('CostOrDataID').value==null))
	 {
	  alert("���û��������ͱ���Ϊ�գ�");
	  return false
	 }
	 if((document.all('DistillMode').value=="")||(document.all('DistillMode').value==null))
	 {
	  alert("���ݲɼ���ʽΪ�գ�");
	  return false
	 }
	 if(document.all('DistillMode').value=="1")
	 {
	 	if(((document.all('DistillSQL').value =="")||(document.all('DistillSQL').value ==null))&&((document.all('DistillSQLForOne').value =="")||(document.all('DistillSQLForOne').value ==null)))
	 	{
	 		alert("�ɼ�SQL����Ϊ�գ�");
	 		return false;
	 	}
	 }
	 if(document.all('DistillMode').value=="2")
	 {
	 	if((document.all('DistillClass').value=="")||(document.all('DistillClass').value==null))
	 	{
	 		alert("�ɼ������಻��Ϊ�գ�");
	 		return false;
	 	}
	 }
	 return true;
}

function resetAgain()
{
			document.all('AcquisitionID').value = '';
			document.all('tempAcquisitionID').value = '';
    	document.all('AcquisitionType').value = '';
    	document.all('tempAcquisitionType').value = '';
    	document.all('DataSourceType').value = '';
    	document.all('CostOrDataID').value = '';
    	document.all('DistillMode').value = '';
    	document.all('DistillSQL').value = '';
    	document.all('DistillSQLForOne').value = '';
    	document.all('DistillClass').value = '';
    	document.all('DistillClassForOne').value = '';
    	document.all('Remark').value = '';
    	document.all('AcquisitionTypeName').value = '';
    	document.all('CostOrDataName').value = '';
    	document.all('DistillModeName').value = '';
    	document.all('DataSourceTypeName').value = '';
    	document.all('tempCostOrDataID').value = '';

    	
    	document.all('updatebutton').disabled = true;	
    	document.all('deletebutton').disabled = true;
    	classdiv.style.display='none';
		 	sqldiv.style.display='none'; 
		
}