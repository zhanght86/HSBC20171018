//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();  
var turnPage2 = new turnPageClass();


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();     
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

}

         

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
	 //add by ml 2006-02-24 for Ч�ʵ���
	 if ((fm.Name.value == "" || fm.Name.value == null) && (fm.IDNo.value == "" || fm.IDNo.value == null))
	 {
			alert("��������ͻ��Ա�����Ĳ�ѯ�������ٽ��в�ѯ��");
			return;	 	
	 }
	 // add end 
	 OPolGrid.clearData();
	 ClientList.clearData();
   var strSQL = "";             
//   strSQL = "select customerno,name,sex,Birthday, IDType,idno, othidtype,othidno from ldperson where 1=1 " 
//             + getWherePart( 'Name','Name' )
//				     + getWherePart( 'sex','AppntSex' )
//				     + getWherePart( 'Birthday','Birthday' )
//				     + getWherePart( 'IDType','AppntIDType' )
//				     + getWherePart( 'idno','IDNo' );
   
    var sqlid1="ClientMergeInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("sys.ClientMergeInputSql");
	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	mySql1.addSubPara(window.document.getElementsByName(trim("Name"))[0].value);//ָ���������
	mySql1.addSubPara(window.document.getElementsByName(trim("AppntSex"))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim("Birthday"))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim("AppntIDType"))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim("IDNo"))[0].value);
	strSQL = mySql1.getString();
   
   
	arrResult = easyExecSql(strSQL);
	if (arrResult)
	{
		displayMultiline(arrResult,OPolGrid);
	}        

}           

function customerUnion()
{
	var selno = ClientList.getSelNo()-1;
	if (selno <0)
	{
		alert( "����ѡ����ͬ��Ͷ����" );
		return;
	}	

//	divCustomerUnion.style.display="";
	
	//var CustomerNo_OLD = ClientList.getRowColData(0, 1);
	var CustomerNo_NEW = ClientList.getRowColData(selno, 1);
	var CustomerName = ClientList.getRowColData(selno, 2);
	//alert(CustomerNo_NEW);
	//alert(CustomerName);
	
	//fm.CustomerNo_OLD.value = CustomerNo_OLD;
	fm.CustomerNo_NEW.value = CustomerNo_NEW;
	//fm.CustomerName.value = CustomerName;
}


//�ύ�����水ť��Ӧ���� 
function ClientMerge()
{
    var selno1 = OPolGrid.getSelNo()-1;
	  var selno2 = ClientList.getSelNo()-1;
	  var birthday1 = OPolGrid.getRowColData(selno1, 4);
	  var birthday2 = ClientList.getRowColData(selno2, 4);
	  var sex1 = OPolGrid.getRowColData(selno1, 3);
	  var sex2 = ClientList.getRowColData(selno2, 3);
	  var name1 = OPolGrid.getRowColData(selno1, 2);
	  var name2 = ClientList.getRowColData(selno2, 2);
	  var idtype1 = OPolGrid.getRowColData(selno1, 5);
	  var idtype2 = ClientList.getRowColData(selno2, 5);
	  var idno1 = OPolGrid.getRowColData(selno1, 6);
	  var idno2 = ClientList.getRowColData(selno2, 6);
	  if(birthday1 != birthday2 || sex1 != sex2)
	  {
	  	  alert("�������ڻ��Ա�Ҫ����Ϣ���������ܽ��пͻ��ϲ�������ȥ��Ҫ�������");
	  	  return;
	  }	
	  if(birthday1 != birthday2 || sex1 != sex2 ||name1!=name2||idtype1!=idtype2||idno1!=idno2)
	  {
	  	  alert("Ҫ����Ϣ����ȫһ�£����ܽ��пͻ��ϲ���");
	  	  return;
	  }
	  
  
	if (fm.CustomerNo_OLD.value == null || fm.CustomerNo_OLD.value == "")
	{
		alert("��ѡ��Ҫ�ϲ���ԭ�ͻ�");
		return;
	}
	if (fm.CustomerNo_NEW.value == null || fm.CustomerNo_NEW.value == "")
	{
		alert("��ѡ��Ҫ�ϲ���ԭ�ͻ�");
		return;
	}
	
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.action =  "../sys/ClientMergeSave.jsp";
  fm.submit(); //�ύ 

}

function customerInfoequels()
{

	var tSel = OPolGrid.getSelNo() - 1;
	
	var Customerno = OPolGrid.getRowColData(tSel, 1);
	var CustomerName = OPolGrid.getRowColData(tSel, 2);
	var CustomerSex = OPolGrid.getRowColData(tSel, 3);
	var CustomerBrithday = OPolGrid.getRowColData(tSel, 4);
	var CustomerIDtype = OPolGrid.getRowColData(tSel, 5);
	var CustomerIDno = OPolGrid.getRowColData(tSel, 6);
	fm.CustomerNo_OLD.value  =  Customerno;
	
	ClientList.clearData();
  var strSQL;
//  strSQL=" select customerno, name, sex, birthday, "
//  			+" idtype, idno, othidtype, othidno " 
//  			+" from ldperson where name='" + CustomerName 
//  			+"' and birthday = to_date('" + CustomerBrithday + "','yyyy-mm-dd') "
//  			+" and sex='" + CustomerSex + "'and customerno<> '" + Customerno
//  			+"' union "
//  			+" select customerno, name, sex, birthday, "
//  			+" idtype, idno, othidtype, othidno "
//  			+" from ldperson where idtype='" + CustomerIDtype + "' and idno='" + CustomerIDno + "'and customerno<> '"+Customerno+"'";
	
    var sqlid2="ClientMergeInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("sys.ClientMergeInputSql");
	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
	mySql2.addSubPara(CustomerName);//ָ���������
	mySql2.addSubPara(CustomerBrithday);
	mySql2.addSubPara(CustomerSex);
	mySql2.addSubPara(Customerno);
	mySql2.addSubPara(CustomerIDtype);
	mySql2.addSubPara(CustomerIDno);
	mySql2.addSubPara(Customerno);
	strSQL = mySql2.getString();
  
  arrResult = easyExecSql(strSQL);
	if (arrResult)
	{
		displayMultiline(arrResult,ClientList);
	} 

}

function exchangeCustomerNo()
{
	var exchangeValue="";
	for(i = 0; i < fm.exchangeRadio.length; i++)
	{   
		if(fm.exchangeRadio[i].checked)
		{ 
			exchangeValue = fm.exchangeRadio[i].value;
			break;                         
		}
	}		

	if(exchangeValue == "1")
	{
		var selno = ClientList.getSelNo()-1;
		if (selno <0)
		{
			alert( "����ѡ����ͬ��Ͷ����" );
			return;
		}	
			
		var CustomerNo_OLD =  ClientList.getRowColData(selno, 1);
		var CustomerNo_NEW = OPolGrid.getRowColData(0, 1);
		//var CustomerName = ClientList.getRowColData(selno, 2);
		
		//fm.CustomerNo_OLD.value = CustomerNo_OLD;
		fm.CustomerNo_NEW.value = CustomerNo_OLD;
		
	}
	if(exchangeValue == "-1")
	{
		var selno = ClientList.getSelNo()-1;
		if (selno <0)
		{
			alert( "����ѡ����ͬ��Ͷ����" );
			return;
		}	
		var selno1 = 	OPolGrid.getSelNo()-1;
		var CustomerNo_NEW = OPolGrid.getRowColData(selno1, 1);
		var CustomerNo_OLD = ClientList.getRowColData(selno, 1);
		var CustomerName = ClientList.getRowColData(selno, 2);
		
		fm.CustomerNo_OLD.value = CustomerNo_OLD;
		fm.CustomerNo_NEW.value = CustomerNo_NEW;
			
	}
}



