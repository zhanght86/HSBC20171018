

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "appgrp.GroupContDeleteSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
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
  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //�ύ
  alert("submit");
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    alert(content);
  }
  else
  { 
    alert("�����ɹ�");
  }
  querygrp();
  document.all("GrpContNo").value = "";
  document.all("PrtNo").vlaue = "";
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
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

//��ѯ���嵥
function querygrp()
{
	// ��ʼ�����                                                                                                                                                           
	initGrpGrid();	
	
	if(fm.QGrpProposalNo.value==null||fm.QGrpProposalNo.value==""){
	  alert("��¼��Ͷ�����ţ�");
	  return false;
	}
	// ��дSQL���
	var str = "";	
	
	var strsql = "";
	if(manageCom=="%")
	{
	
		strsql = "select a.ProposalGrpContNo,a.GrpContNo,a.PrtNo,a.GrpName,a.ManageCom from LCGrpCont a where (markettype='0' or markettype is null)  and managecom like '"+manageCom+"%' "
				 + getWherePart( 'a.ProposalGrpContNo','QGrpProposalNo' )
				 +" and cardflag is null";
		/*
		var sqlid1="GroupContDeleteSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(manageCom);
		mySql1.addSubPara(fm.QGrpProposalNo.value);
			
			strsql =	mySql1.getString(); */
				 
	}else{
	
	strsql = "select a.ProposalGrpContNo,a.GrpContNo,a.PrtNo,a.GrpName,a.ManageCom from LCGrpCont a where (markettype='0' or markettype is null) and managecom like '"+manageCom+"%%' "
				 + getWherePart( 'a.ProposalGrpContNo','QGrpProposalNo' )
				 +" and cardflag is null";
	/*		
				var sqlid2="GroupContDeleteSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(manageCom);
		mySql2.addSubPara(fm.QGrpProposalNo.value);
			
			strsql =	mySql2.getString(); 
				*/
				
				}
				 
				
				 
				
	if (document.all("QState").value == "��ǩ��")
		strsql = strsql + " and a.AppFlag = '1' "
	else if (document.all("QState").value != null && document.all("QState").value != "")
		strsql = strsql + " and (a.AppFlag <> '1' or a.AppFlag is null)"
	strsql = strsql + " order by  a.GrpContNo ";
	
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�з����������嵥��");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = GrpGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strsql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  document.all("GrpContNo").value = "";
  return true;
}


//
function GrpContSelect(parm1,parm2)
{
	var cPrtNo = "";
	var cGrpPolNo = "";
	
	if(document.all(parm1).all('InpGrpGridSel').value == '1' )
	{
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
   		cPrtNo = document.all(parm1).all('GrpGrid3').value;	//��ˮ��
   		cGrpContNo = document.all(parm1).all('GrpGrid2').value;	//�����ͬ��
   		cGrpProposalNo = document.all(parm1).all('GrpGrid1').value;	//����Ͷ������
   		//alert(cGrpContNo);
   	}
   	if(cPrtNo == null || cPrtNo == "" || cGrpContNo == null || cGrpContNo == "")
   	{
   		alert("��ѡ��һ����Ч�����嵥!");
   	}
   	else
   	{
		document.all("GrpContNo").value = cGrpContNo;
		document.all("PrtNo").vlaue = cPrtNo;
   	}
}

function deleteGrpCont()
{
	//ֻ������λ��վ����
	var tLine=manageCom.length;
	//if(tLine<4)
	//{
	//	alert("ֻ������λ����Ļ�������ϵͳ������");
	//	return false;
	//}
	
	if (document.all("GrpContNo").value == null || document.all("GrpContNo").value == "")
	{
		alert("����ѡ��һ�����屣����");
		return;
	}

	if (!confirm("ȷ��Ҫɾ���ñ�����"))
	{
		return;
	}

	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	lockScreen('lkscreen');  
	fm.action = "./GroupContDeleteChk.jsp";
	document.getElementById("fm").submit();
}


function deleteCont()
{
	
	//ֻ������λ��վ����
	var tLine=manageCom.length;
	//if(tLine<4)
	//{
	//	alert("ֻ������λ����Ļ�������ϵͳ������");
	//	return false;
	//}
	
	if (document.all("GrpContNo").value == null || document.all("GrpContNo").value == "")
	{
		alert("����ѡ��һ�����屣����");
		return;
	}
  else
  {
	       window.open("./InGrpContDeleteMain.jsp?GrpContNo="+ document.all("GrpContNo").value,"",sFeatures);
	   
	}

}