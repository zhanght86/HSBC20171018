//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sumMoney=0;
var flag = 0;
var sqlresourcename = "finfee.NewGrpPolFeeWithDrowInputSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(ManageCom.length != 6)
 {
	//alert(manageCom);
	alert("�罻�˷Ѳ���ֻ������λ�����½��У�������ѡ����λ������½��");
	return false;
 }
    if(!GetRecord())
	{
		alert("����ѡ�񸶷Ѽ�¼��");
		return false;	
	}
	
	if(!check())
    {	
  	    return false;  
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
 lockScreen('lkscreen');  
  fm.action= "./NewGrpPolFeeWithDrow.jsp";
  document.getElementById("fm").submit(); //�ύ

}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();

	/*
    if(typeof(showInfo)=="object")
	{
		showInfo.close();
		if(typeof(showInfo.parent)=="object")
		{
			showInfo.parent.focus();
			if(typeof(showInfo.parent.parent)=="object")
			{
				showInfo.parent.parent.blur();
			}
		}
  	}
  	*/
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	 //divDifShow.style.display = 'none';
	// document.all('Dif').value=0;
  }
  // document.all("BankCodeName").value ="";
  // document.all("BankAccNo").value ="";
 //  document.all("BankCode").value="";
 //  document.all("Confirm").disabled=false;
   	queryClick();
	 
}

function check()
{	

	var i = 0;	
	var sum = 0;
	var tRow = -1;
	var selno = FinFeeWithDrawGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return false;
	}	
/*
   if(FinFeeWithDrawGrid.getRowColData(selno,6) == null || FinFeeWithDrawGrid.getRowColData(selno,6) == '')
   {      
   	      if(document.all("BankCodeName").value==""||document.all("BankCodeName").value==null)
   	      {
    			alert("����"+FinFeeWithDrawGrid.getRowColData(i,1)+"û��Ͷ����λ����������Ϣ����¼��������˷Ѵ���");
    			return false;
    		  }
   }//������û��������Ϣ
   if(FinFeeWithDrawGrid.getRowColData(selno,8) == null || FinFeeWithDrawGrid.getRowColData(selno,8) == '')
   {      if(document.all("BankAccNo").value==""||document.all("BankAccNo").value==null)
   	      {
    			alert("����"+FinFeeWithDrawGrid.getRowColData(selno,1)+"û��Ͷ����λ�����ʻ���Ϣ����¼��������˷Ѵ���");
    			return false;
    		  }
   }//������û���ʻ���Ϣ
   if(document.all("BankCodeName").value!=""&&document.all("BankCodeName").value!=null)
   {
   	if(document.all("BankCode").value==null||document.all("BankCode").value=="")
   	{
   		alert("���Ѿ������˿���������Ϣ����ȷ��!");
   		return false;
   	}
   	
   }//û�е��ȷ�ϵ�У��
   	*/
				
  
	return true;
}

function queryClick()
{
	if(ManageCom.length != 6)
 {
	//alert(manageCom);
	alert("�罻�˷Ѳ���ֻ������λ�����½��У�������ѡ����λ������½��");
	return false;
 }
	initFinFeeWithDrawGrid();
		
	var strSQL = "select q.grpcontno,q.grpname,q.dif,q.signdate, "
				+" case when q.bankcode is not null then (select codename from ldcode where codetype = 'bank' and code = q.bankcode ) else '' end,q.bankaccno,q.managecom  "
				+ " from lcgrpcont q where q.ManageCom like '"+ManageCom+"%%' "
				+ " and q.dif>0 "
				+ getWherePart( 'q.GrpContNo','GrpContNo' )
	           	+ getWherePart( 'q.PrtNo','PrtNo' )
	           	+ getWherePart( 'q.AgentCode','AgentCode' );
	          	
/*	   var strSQL ="";        	
		var sqlid1="NewGrpPolFeeWithDrowInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(ManageCom);
		mySql1.addSubPara(fm.GrpContNo.value);
		mySql1.addSubPara(fm.PrtNo.value);
		mySql1.addSubPara(fm.AgentCode.value);
	           	
	      strSQL =   mySql1.getString();   */	
	           	if(fm.GrpName.value !="")
			     {
			    	 strSQL+= " and q.grpname like '%%"+fm.GrpName.value+"%%' ";
			     }
			    
				strSQL+=" order by q.signdate desc,q.grpcontno desc";
				//prompt("",strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  

                  
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) 
    {      
	    alert("δ�鵽���������Ľ����");
	    return false;
    }
	
	turnPage.pageLineNum = 10;
    //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
    //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
    turnPage.pageDisplayGrid = FinFeeWithDrawGrid;    
          
    //����SQL���
    turnPage.strQuerySql     = strSQL; 
  
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex       = 0;  
  
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}


function queryAgent()
{
    window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+ManageCom, "AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/*********************************************************************
 *  ��ѯҵ����Ա���������ҵ����Ա����
 *  ����:��ѯҵ����Ա����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery2(arrResult)
{
    if(arrResult != null)
    {
        fm.AgentCode.value  = arrResult[0][0];    //��ʾ�����˴���
    }
}

function GetRecord()
{
	var i = 0;	
	var sum = 0;
	var tRow = -1;

	//�ж��Ƿ�ѡ���˱�������
	var selno = FinFeeWithDrawGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return false;
	}	
		return true;	

}

function creatBankCode()
{
	if(document.all("BankCodeName").value == "")
	{
		alert("�����뿪��������!");
		return false;
	}
	var selno = FinFeeWithDrawGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ����¼Ϊ����ӻ��޸�������Ϣ��");
	      return false;
	}	
	var ManageCom = FinFeeWithDrawGrid.getRowColData(selno,8);
	var BankCodeName = document.all("BankCodeName").value;	

	//var sql="select code from ldcode where codetype='bank' and codename = '"+document.all("BankCodeName").value+"' ";
      	
		var sqlid2="NewGrpPolFeeWithDrowInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(document.all("BankCodeName").value);

	var arrResult = easyExecSql(mySql2.getString(), 1, 0,1);	
	/*
	if(!confBankCode())
	{
		return false;
	}	
	*/
	if(arrResult == null)
	{		
		fm.action="../appgrp/CreatBankCode.jsp?BankType=2&BankCodeName="+document.all('BankCodeName').value+"";
		document.getElementById("fm").submit(); //�ύ
	}
	else
	{
		document.all("BankCode").value = arrResult[0][0];
		document.all("Confirm").disabled=true;
	}
}

/*
function GetRecord()
{
	var i = 0;	
	var sum = 0;
	var tRow = -1;

	//�ж��Ƿ�ѡ���˱�������
	for( i = 0; i < FinFeeWithDrawGrid.mulLineCount; i++ )
	{
		if( FinFeeWithDrawGrid.getChkNo(i) == true )
		{
			sumMoney= sumMoney+ parseFloat(FinFeeWithDrawGrid.getRowColData(i,4));
			flag = 1;			
		}
	}
	
	document.all('Dif').value = sumMoney; 
	sumMoney =0;
}

*/