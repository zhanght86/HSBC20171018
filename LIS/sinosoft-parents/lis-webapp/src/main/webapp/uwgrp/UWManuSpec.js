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
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var sqlresourcename = "uwgrp.UWManuSpecSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  fm.submit(); //�ύ
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
    parent.close();
  }
  else
  { 
	var showStr="�����ɹ�";  	
  	showInfo.close();
  	alert(showStr);
  	parent.close();
  	
    //ִ����һ������
  }
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
	fm.submit();
}

function QueryPolSpecGrid(tContNo)
{
	// ��ʼ�����
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
	
       //��ȡԭ������Ϣ
       /*
        strSQL = "select LCPol.ContNo,LCPol.PrtNo,LCPol.PolNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "				 			
			 + "  ContNo ='"+tContNo+"'"
			 + "  order by polno ";	
			 */		
	var sqlid1="UWManuSpecSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContNo);
		
	turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1); 

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


function QueryPolSpecCont()
{
	
		var strSQL = "";
	
       //��ȡԭ������Ϣ
       // strSQL = "select cont from ldcodemod where 1 =1 and codetype='spectype'";		
       /*
       strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype'"
				 ;	
			*/	 
				 var sqlid2="UWManuSpecSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(k);
		mySql2.addSubPara(k);
				 
	turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) 
  {  
  	
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = UWSpecContGrid;    
          
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
  

}
function getPolGridCho()
{
  var tSelNo = UWSpecGrid.getSelNo()-1;
  var cPolNo = UWSpecGrid.getRowColData(tSelNo,3);
  var tcontno = UWSpecGrid.getRowColData(tSelNo,1);
  //alert(tcontno);
 if(cPolNo != null && cPolNo != "" )
  {
    fm.all("PolNo").value = cPolNo;
   
    QuerySpecReason(cPolNo);   
    QuerySpec(tcontno);
  }	
}



//��ѯ�Ѿ�¼����Լ����
function QuerySpec(tcontno)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
	/*
	strSQL = "select speccontent from LCSpec where contno='"+tcontno+
			   "' and SerialNo in (select max(SerialNo) from lcspec where contno = '"+tcontno+"')";
	*/
	var sqlid3="UWManuSpecSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tcontno);
		mySql3.addSubPara(tcontno);
			
	var arrResult=easyExecSql(mySql3.getString());  
	try{fm.all('Remark').value= arrResult[0][0];}catch(ex){};                                                                                          
//	tur   try{fm.all('AppntNo').value= arrResult[0][0];}catch(ex){};                                                                     nPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
//
//  //�ж��Ƿ��ѯ�ɹ�
//  if (!turnPage.strQueryResult) {
//    return "";
//  }
//  
//  //��ѯ�ɹ������ַ��������ض�ά����
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//  
//  fm.Remark.value = turnPage.arrDataCacheSet[0][0];	
  
  return true;	
}


//��ѯ�Ѿ�¼��ӷ���Լԭ��
function QuerySpecReason(cPolNo)
{
	
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
	/*
	strSQL = "select specreason from LCUWMaster where 1=1 "
			 + " and proposalno = '"+cPolNo+"'";
	*/
	var sqlid4="UWManuSpecSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(cPolNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql4.getString(), 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.SpecReason.value = turnPage.arrDataCacheSet[0][0];
  //fm.Reason.value = turnPage.arrDataCacheSet[0][1];
  
  return true;	
}


function getSpecGridCho()
{
	var tContent = fm.Remark.value;
	
	var tSelNo = UWSpecContGrid.getSelNo()-1;
	var tRemark = UWSpecContGrid.getRowColData(tSelNo,1);	
	
  fm.Remark.value = tRemark + tContent;
  

		
}