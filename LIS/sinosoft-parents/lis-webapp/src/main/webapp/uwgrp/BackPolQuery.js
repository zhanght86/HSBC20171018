//�������ƣ�BackPolQuery.js
//�����ܣ����������ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //���������λ�� 1.�˱�
var sqlresourcename = "uwgrp.BackPolQuerySql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showInfo.close();
    alert(content);
  }
  else
  { 
	var showStr="�����ɹ�";
  	//alert(showStr);
  	//showInfo.close();
  	
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
  else 
  {
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

// ��ѯ��ť
function easyQueryClick(tProposalNo)
{
	
	var strsql = "";
		
        if(tProposalNo != "")
        {
		//strsql = "select MainPolNo,ProposalNo,Remark,Operator,MakeDate from LCApplyRecallPol where proposalno = '"+tProposalNo+"'";	
		
			var sqlid1="BackPolQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tProposalNo);
		
		//alert(strsql);
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�˵�û�г������룡");
    //easyQueryClickInit();
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = BackPolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strsql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  return true;
}

// ��ѯ��ť
function easyQueryChoClick(parm1,parm2)
{	
	// ��дSQL���
	k++;
	var tProposalNo = "";
	var strSQL = "";

	if(fm.all(parm1).all('InpBackPolGridSel').value == '1' )
	{
	//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
   		tProposalNo = fm.all(parm1).all('BackPolGrid2').value;
  	}	
	
	
	//alert(tProposalNo);
	//alert(tSerialNo);	
	if (tProposalNo != " ")
	{
		//strSQL = "select Remark from LCApplyRecallPol where proposalno = '"+tProposalNo+"'";
	
	var sqlid2="BackPolQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tProposalNo);
	strSQL = mySql1.getString();
	}
	
	//alert(strSQL);
	//execEasyQuery( strSQL );
	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  fm.Content.value = turnPage.arrDataCacheSet[0][0];
  
  return true;
}
