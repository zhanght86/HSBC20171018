//�������ƣ�LLSecondUW.js
//�����ܣ���ͬ���˹��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var k = 0;
var mySql = new SqlClass();
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

// ��ʼ����ť------------�ñ��������µ����к�ͬ 
function initLCContGridQuery()
{
	var tInsuredNo = fm.InsuredNo.value;
	//���� ���������˱�lcinsured�������˱�����---��ͬ��LCCont�����Ӳ�ѯ
	/*var strSQL = " select a.contno,a.appntno,a.appntname,a.managecom "
			+" from lccont a,lcinsured b where 1=1 and a.contno = b.contno "
 			+" and b.insuredno = '"+tInsuredNo+"' ";*/
	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLSecondUWInputSql");
mySql.setSqlId("LLSecondUWSql1");
mySql.addSubPara(tInsuredNo); 
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  //��ѯSQL�����ؽ���ַ���
	if (!turnPage.strQueryResult)  //�ж��Ƿ��ѯ�ɹ�
	{
		alert("û�в鵽�ñ��������µ���غ�ͬ��");
		return "";
	}
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = LCContGrid;     
	turnPage.strQuerySql= strSQL; //����SQL���
	turnPage.pageIndex = 0;  //���ò�ѯ��ʼλ��
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);//����MULTILINE������ʾ��ѯ���
//	isClaimRelFlag();	
	return true;
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;
	if( arrResult == null )
	{
		alert( "û���ҵ���ص�����!" );
	}
	else
	{
		arrGrid = arrResult;// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				CheckGrid.setRowColData( i, j+1, arrResult[i][j] );
			} 	
		} 	
		
	} 	
}

//[��ѯ��ÿ����ͬ���뵱ǰ�ⰸ���]------��ѯ�ⰸ������ϸ��LLClaimPolicy�����
function isClaimRelFlag()
{
	var strSQL="";
	var row = LCContGrid.mulLineCount;
	for(var m=0;m<row;m++ )
	{
		/*var strSQL="select contno from llclaimpolicy where 1=1"
			+" and clmno='"+fm.CaseNo.value +"'"
			+" and contno='"+LCContGrid.getRowColData(m,1) +"'";*/
		mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLSecondUWInputSql");
mySql.setSqlId("LLSecondUWSql2");
mySql.addSubPara(fm.CaseNo.value ); 
mySql.addSubPara(LCContGrid.getRowColData(m,1)); 
		var arr=easyExecSql(mySql.getString());
		if(arr==null ||arr=="")
		{
			LCContGrid.setRowColData(m,5,"1");//�á�1--����ر�־��
		}
		else
			 
		{ 
			LCContGrid.setRowColData(m,5,"0");//�á�0--��ر�־��
		} 

	}
}

function LLSeUWSaveClick()
{
    //����Ƿ���д����()
    var row = LCContGrid.mulLineCount;
    var i=0;
    for(var m=0;m<row;m++ )
    {
    	if(LCContGrid.getChkNo(m))
    	{
    		i=i+1;
    		// ����Ƿ������д����е�����
    	}
    }
    if(i==0)
	{
		alert("��û��ѡ���κκ�ͬ��");
		return;
	}
	if(trim(fm.Note.value)=="") 
	{
		alert("����д������Ŀǰ����״�����ܣ�");
		return;
	}
//	if(confirm("�Ѿ�������˱�,ȷʵҪ�ٴ�����˱���")==true)
//	{
//	}
//    return;    
	fm.action="LLSecondUWSave.jsp";
	submitForm(); //�ύ
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
}

