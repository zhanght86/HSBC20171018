//�������ƣ�UWApp.js
//�����ܣ�����Ͷ����Ϣ��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  �� WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var tPOLNO = "";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
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
    //ִ����һ������
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




function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

/*********************************************************************
 *  ��ѯ�����ѳб�������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */	
function queryDetailInfo()
{
//	var strSQL = "";
//  strSQL = "select DealType,PayCode,AccName,AccNo,PayMoney,IDNo,IDType,PolNo,(case NoType when '4' then '����Ͷ������' when '6' then '����Ͷ������' when '7','����Ͷ������' then '2' when '���ڱ�����' then '3' when '���ڱ�����' when '10' then '��ȫ�����' when '5' then '�����' else '����' end)"       		                
//              + " from LYSendToBank l where 1=1 "
//		          + " and SerialNo = '"+tSerialNo+"'"	
//              +" order by NoType,PayCode" ; 		 	 
	
  var sqlid1="BankSerDetailInputSql1";
  var mySql1=new SqlClass();
  mySql1.setResourceName("bank.BankSerDetailInputSql");
  mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
  mySql1.addSubPara(tSerialNo);//ָ���������
  var strSQL = mySql1.getString();
  
 turnPage.queryModal(strSQL, PolGrid);
	
}

/*********************************************************************
 *  ��ѯ�ͻ���Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryGolbalInfo(tCustomerNo){
  //var aSQL = "select SerialNo,BankCode,TotalNum,TotalMoney,SendBankFileState from LYBankLog a where a.SerialNo='"+tSerialNo+"'";
 
  var sqlid2="BankSerDetailInputSql2";
  var mySql2=new SqlClass();
  mySql2.setResourceName("bank.BankSerDetailInputSql");
  mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
  mySql2.addSubPara(tSerialNo);//ָ���������
  var aSQL = mySql2.getString();
  
  var arrResult = easyExecSql(aSQL);
  if(arrResult!=null)
  {
  	document.all('SerialNo').value = arrResult[0][0];
    document.all('BankCode').value = arrResult[0][1];
    document.all('TotalNum').value = arrResult[0][2];
    document.all('TotalMoney').value = arrResult[0][3];
    document.all('SendBankFileState').value = arrResult[0][4];
  }
}
