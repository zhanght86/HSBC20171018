//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();  
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mSwitch = parent.VD.gVSwitch;

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit1( FlagStr, content )
{
	
	showInfo.close();     
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	divCustomerContInfo.style.display="none";
	var showStr="����ˢ�����ݣ������Ժ�";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	lockScreen('lkscreen');
	top.opener.initForm();
	showInfo.close();     
	unlockScreen('lkscreen');
	top.close();
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

//	   strSQL = "select customerno,name,sex,Birthday, IDType,idno, othidtype,othidno from ldperson where 1=1 " 
//   			+" and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//  			+"  union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//             + getWherePart( 'Name','Name' )
//				     + getWherePart( 'sex','AppntSex' )
//				     + getWherePart( 'Birthday','Birthday' )
//				     + getWherePart( 'IDType','AppntIDType' )
//				     + getWherePart( 'idno','IDNo' );
   
	  	var  Name0 = window.document.getElementsByName(trim("Name"))[0].value;
	  	var  AppntSex0 = window.document.getElementsByName(trim("AppntSex"))[0].value;
	  	var  Birthday0 = window.document.getElementsByName(trim("Birthday"))[0].value;
	  	var  AppntIDType0 = window.document.getElementsByName(trim("AppntIDType"))[0].value;
	  	var  IDNo0 = window.document.getElementsByName(trim("IDNo"))[0].value;
		var sqlid0="CustomerMergeSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("app.CustomerMergeSql"); //ָ��ʹ�õ�properties�ļ���
		mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
		mySql0.addSubPara(Name0);//ָ������Ĳ���
		mySql0.addSubPara(AppntSex0);//ָ������Ĳ���
		mySql0.addSubPara(Birthday0);//ָ������Ĳ���
		mySql0.addSubPara(AppntIDType0);//ָ������Ĳ���
		mySql0.addSubPara(IDNo0);//ָ������Ĳ���
		strSQL=mySql0.getString();
	   
	arrResult = easyExecSql(strSQL);
	if (arrResult)
	{
		displayMultiline(arrResult,OPolGrid);
	}        

}           

function customerUnion()
{
	var selno = ClientList.getSelNo()-0;
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
	divCustomerContInfo.style.display="";
	QueryAllPol(CustomerNo_NEW);
}


//�ύ�����水ť��Ӧ���� 
function ClientMerge()
{
      var selno1 = OPolGrid.getSelNo()-1;
	  var selno2 = ClientList.getSelNo()-1;
	  if(selno2<0||selno1<0)
	  {
	  	 alert('��ѡ����Ҫ�ϲ��Ŀͻ�');
	  	 return false;
	  }
	  //alert(selno2+":"+selno1);
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
	  //if(birthday1 != birthday2 || sex1 != sex2)
	  //{
	  //	  alert("�������ڻ��Ա�Ҫ����Ϣ���������ܽ��пͻ��ϲ�������ȥ��Ҫ�������");
	  //	  return;
	  //}	
	  //if(birthday1 != birthday2 || sex1 != sex2 ||name1!=name2||idtype1!=idtype2||idno1!=idno2)
	  //{
	  //	  alert("Ҫ����Ϣ����ȫһ�£����ܽ��пͻ��ϲ���");
	  //	  return;
	  //}
	  
  
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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.action =  "../app/CustomerMergeSave.jsp";
  fm.submit(); //�ύ 

}

function initcustomerInfoequels()
{
//alert(120);
	var tSel = 0;
	var Customerno = OPolGrid.getRowColData(tSel, 1);
	var CustomerName = OPolGrid.getRowColData(tSel, 2);
	var CustomerSex = OPolGrid.getRowColData(tSel, 3);
	var CustomerBrithday = OPolGrid.getRowColData(tSel, 4);
	var CustomerIDtype = OPolGrid.getRowColData(tSel, 5);
	var CustomerIDno = OPolGrid.getRowColData(tSel, 6);
	fm.CustomerNo_OLD.value  =  Customerno;
	var CustomerIDno15="";
	var CustomerIDno18="";
	//alert(CustomerIDno.length+"      "+CustomerIDno);
	if(CustomerIDno.length==15||CustomerIDno.length==18){
		if(CustomerIDno.length==15){
			CustomerIDno15=CustomerIDno;
			CustomerIDno18=IdCard15to18(CustomerIDno15);
		}
		if(CustomerIDno.length==18){
			CustomerIDno18=CustomerIDno;
			CustomerIDno15=IdCard18to15(CustomerIDno18);
		}
	}else{
		CustomerIDno15=CustomerIDno;
		CustomerIDno18=CustomerIDno;
	}
	//alert("CustomerIDno18=="+CustomerIDno18+"  CustomerIDno15=="+CustomerIDno15);
	//alert(CustomerIDno.replace(/[^0-9]/g,''));
	ClientList.clearData();
  var strSQL;
//  strSQL=" select customerno, name, sex, birthday, "
//  			+" idtype, idno, othidtype, othidno " 
//  			+" from ldperson where name='" + CustomerName 
//  			+"' and to_date(to_char(birthday,'yyyy-mm-dd'),'yyyy-mm-dd') = to_date('" + CustomerBrithday + "','yyyy-mm-dd') "
//  			+" and sex='" + CustomerSex + "'and customerno<> '" + Customerno + "' "
//  			+" and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//  			+"  union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//  			//+" and (lower(idno) <> lower('"+CustomerIDno18+"') or lower(idno) <> lower('"+CustomerIDno15
//  			//+"')) " 
//  			+" union "
//  			+" select customerno, name, sex, birthday, "
//  			+" idtype, idno, othidtype, othidno "
//  			+" from ldperson where "
//  			+" (idtype='0' or idtype='1') and (lower(idno) = lower('"+CustomerIDno18+"') or lower(idno)"
//  			+" = lower('"+CustomerIDno15
//  			+"'))"
//  			+" and (to_date(to_char(birthday,'yyyy-mm-dd'),'yyyy-mm-dd') = to_date('" + CustomerBrithday + "','yyyy-mm-dd') "
//  			+" or sex='" + CustomerSex + "' or name='"+CustomerName+"')"
//  			+" and customerno<> '"+Customerno+"'"
//  			+" and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//  			+"  union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//  			+" union "
//  			+" select customerno,name,sex,birthday,"
//  			+" idtype, idno, othidtype, othidno "
//  			+" from ldperson where "
//  			+" idtype in ('0','1','3','4') and (lower(idno) = lower('"+CustomerIDno18+"') or lower(idno)"
//  			+" = lower('"+CustomerIDno15
//  			+"'))"
//  			+" and customerno in (select a.customerno"
//			+" from lcaddress a "
//			+" where customerno != '"+Customerno+"'" 
//			+" and  (mobile in "
//			+" (select REGEXP_REPLACE(mobile, '��', '') from lcaddress where customerno = '"+Customerno+"')"
//			+" or phone in (select REGEXP_REPLACE(phone, '��', '') from lcaddress where customerno = '"+Customerno+"')"
//			+" or phone in (select REGEXP_REPLACE(mobile, '��', '') from lcaddress where customerno = '"+Customerno+"')"
//			+" or mobile in (select  REGEXP_REPLACE(phone, '��', '') from lcaddress where customerno = '"+Customerno+"')"
//			+"))"
//			+" and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//  			+"  union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//  			+" union "
//  			+" select customerno,name,sex,birthday,"
//  			+" idtype, idno, othidtype, othidno "
//  			+" from ldperson where "
//  			//+" idtype='0' and idno='"+CustomerIDno+"'"
//  			//+" and ";
//  			+" sex='"+ CustomerSex 
//  			+"' and to_date(to_char(birthday,'yyyy-mm-dd'),'yyyy-mm-dd') = to_date('" + CustomerBrithday + "','yyyy-mm-dd') "
//  			+" and idtype='2' and REGEXP_REPLACE(idno,'[^0-9]','')  ="
//  			+ "'"+CustomerIDno.replace(/[^0-9]/g,'')+"'"
//  			+" and customerno<> '" + Customerno+"'"
//	  			+" and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//	  			+"  union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//  			;
  			//+"') or lower(idno) <> lower('"+CustomerIDno15+"'))";
  			//+"' union "
  			//+" select customerno, name, sex, birthday, "
  			//+" idtype, idno, othidtype, othidno "
  			//+" from ldperson where "
  			//+" (idtype='0') and idno='" + CustomerIDno 
  			//+"' and birthday = to_date('" + CustomerBrithday + "','yyyy-mm-dd') "
  			//+" and sex='" + CustomerSex + "' "
  			//+" and customerno<> '"+Customerno+"'";
  
	var sqlid1="CustomerMergeSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.CustomerMergeSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(CustomerName);//ָ������Ĳ���1
	mySql1.addSubPara(CustomerBrithday);//ָ������Ĳ���2
	mySql1.addSubPara(CustomerSex);//ָ������Ĳ���3
	mySql1.addSubPara(Customerno);//ָ������Ĳ���4
	mySql1.addSubPara(CustomerIDno18);//ָ������Ĳ���5
	mySql1.addSubPara(CustomerIDno15);//ָ������Ĳ���6
	mySql1.addSubPara(CustomerBrithday);//ָ������Ĳ���7
	mySql1.addSubPara(CustomerSex);//ָ������Ĳ���8
	mySql1.addSubPara(CustomerName);//ָ������Ĳ���9
	mySql1.addSubPara(Customerno);//ָ������Ĳ���10
	mySql1.addSubPara(CustomerIDno18);//ָ������Ĳ���11
	mySql1.addSubPara(CustomerIDno15);//ָ������Ĳ���12
	mySql1.addSubPara(Customerno);//ָ������Ĳ���13
	mySql1.addSubPara(Customerno);//ָ������Ĳ���14
	mySql1.addSubPara(Customerno);//ָ������Ĳ���15
	mySql1.addSubPara(Customerno);//ָ������Ĳ���16
	mySql1.addSubPara(Customerno);//ָ������Ĳ���17
	mySql1.addSubPara(CustomerSex);//ָ������Ĳ���18
	mySql1.addSubPara(CustomerBrithday);//ָ������Ĳ���19
	mySql1.addSubPara(CustomerIDno.replace(/[^0-9]/g,''));//ָ������Ĳ���20
	mySql1.addSubPara(Customerno);//ָ������Ĳ���21
	strSQL=mySql1.getString();
  
	arrResult = easyExecSql(strSQL);//prompt("",strSQL);
	if (arrResult)
	{
		displayMultiline(arrResult,ClientList);		
	} 

}

function customerInfoequels()
{
//alert(120);
	var tSel = OPolGrid.getSelNo() - 1;
	
	var Customerno = OPolGrid.getRowColData(tSel, 1);
	var CustomerName = OPolGrid.getRowColData(tSel, 2);
	var CustomerSex = OPolGrid.getRowColData(tSel, 3);
	var CustomerBrithday = OPolGrid.getRowColData(tSel, 4);
	var CustomerIDtype = OPolGrid.getRowColData(tSel, 5);
	var CustomerIDno = OPolGrid.getRowColData(tSel, 6);
	fm.CustomerNo_OLD.value  =  Customerno;
	var CustomerIDno15="";
	var CustomerIDno18="";
	//alert(CustomerIDno.length+"      "+CustomerIDno);
	if(CustomerIDno.length==15||CustomerIDno.length==18){
		if(CustomerIDno.length==15){
			CustomerIDno15=CustomerIDno;
			CustomerIDno18=IdCard15to18(CustomerIDno15);
		}
		if(CustomerIDno.length==18){
		//alert('2');
			CustomerIDno18=CustomerIDno;
			CustomerIDno15=IdCard18to15(CustomerIDno18);
			//alert('CustomerIDno15:'+CustomerIDno15);
		}
	}else{
		CustomerIDno15=CustomerIDno;
		CustomerIDno18=CustomerIDno;
	}
	//alert("CustomerIDno18=="+CustomerIDno18+"  CustomerIDno15=="+CustomerIDno15);
	//alert(CustomerIDno.replace(/[^0-9]/g,''));
	ClientList.clearData();
  var strSQL;
//  strSQL=" select customerno, name, sex, birthday, "
//  			+" idtype, idno, othidtype, othidno " 
//  			+" from ldperson where name='" + CustomerName 
//  			+"' and to_date(to_char(birthday,'yyyy-mm-dd'),'yyyy-mm-dd') = to_date('" + CustomerBrithday + "','yyyy-mm-dd') "
//  			+" and sex='" + CustomerSex + "'and customerno<> '" + Customerno + "' "
//  			+" and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//  			+"  union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//  			//+" and (lower(idno) <> lower('"+CustomerIDno18+"') or lower(idno) <> lower('"+CustomerIDno15
//  			//+"')) " 
//  			+" union "
//  			+" select customerno, name, sex, birthday, "
//  			+" idtype, idno, othidtype, othidno "
//  			+" from ldperson where "
//  			+" (idtype='0' or idtype='1') and (lower(idno) = lower('"+CustomerIDno18+"') or lower(idno)"
//  			+" = lower('"+CustomerIDno15
//  			+"'))"
//  			+" and (to_date(to_char(birthday,'yyyy-mm-dd'),'yyyy-mm-dd') = to_date('" + CustomerBrithday + "','yyyy-mm-dd') "
//  			+" or sex='" + CustomerSex + "' or name='"+CustomerName+"')"
//  			+" and customerno<> '"+Customerno+"'"
//  			+" and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//  			+"  union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//  			+" union "
//  			+" select customerno,name,sex,birthday,"
//  			+" idtype, idno, othidtype, othidno "
//  			+" from ldperson where "
//  			+" idtype in ('0','1','3','4') and (lower(idno) = lower('"+CustomerIDno18+"') or lower(idno)"
//  			+" = lower('"+CustomerIDno15
//  			+"'))"
//  			+" and customerno in (select a.customerno"
//			+" from lcaddress a "
//			+" where customerno != '"+Customerno+"'" 
//			+" and  (mobile in "
//			+" (select REGEXP_REPLACE(mobile, '��', '') from lcaddress where customerno = '"+Customerno+"')"
//			+" or phone in (select REGEXP_REPLACE(phone, '��', '') from lcaddress where customerno = '"+Customerno+"')"
//			+" or phone in (select REGEXP_REPLACE(mobile, '��', '') from lcaddress where customerno = '"+Customerno+"')"
//			+" or mobile in (select  REGEXP_REPLACE(phone, '��', '') from lcaddress where customerno = '"+Customerno+"')"
//			+"))"
//			+" and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//  			+"  union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//  			+" union "
//  			+" select customerno,name,sex,birthday,"
//  			+" idtype, idno, othidtype, othidno "
//  			+" from ldperson where "
//  			//+" idtype='0' and idno='"+CustomerIDno+"'"
//  			//+" and ";
//  			+" sex='"+ CustomerSex 
//  			+"' and to_date(to_char(birthday,'yyyy-mm-dd'),'yyyy-mm-dd') = to_date('" + CustomerBrithday + "','yyyy-mm-dd') "
//  			+" and idtype='2' and REGEXP_REPLACE(idno,'[^0-9]','')  ="
//  			+ "'"+CustomerIDno.replace(/[^0-9]/g,'')+"'"
//  			+" and customerno<> '" + Customerno+"'"
//	  			+" and  exists (select 1 from lcpol where conttype='1' and appntno=ldperson.customerno"
//	  			+"  union select 1  from lcpol where conttype='1' and insuredno=ldperson.customerno)"
//  			;
  			//+"') or lower(idno) <> lower('"+CustomerIDno15+"'))";
  			//+"' union "
  			//+" select customerno, name, sex, birthday, "
  			//+" idtype, idno, othidtype, othidno "
  			//+" from ldperson where "
  			//+" (idtype='0') and idno='" + CustomerIDno 
  			//+"' and birthday = to_date('" + CustomerBrithday + "','yyyy-mm-dd') "
  			//+" and sex='" + CustomerSex + "' "
  			//+" and customerno<> '"+Customerno+"'";
  
	var sqlid2="CustomerMergeSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.CustomerMergeSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(CustomerName);//ָ������Ĳ���1
	mySql2.addSubPara(CustomerBrithday);//ָ������Ĳ���2
	mySql2.addSubPara(CustomerSex);//ָ������Ĳ���3
	mySql2.addSubPara(Customerno);//ָ������Ĳ���4
	mySql2.addSubPara(CustomerIDno18);//ָ������Ĳ���5
	mySql2.addSubPara(CustomerIDno15);//ָ������Ĳ���6
	mySql2.addSubPara(CustomerBrithday);//ָ������Ĳ���7
	mySql2.addSubPara(CustomerSex);//ָ������Ĳ���8
	mySql2.addSubPara(CustomerName);//ָ������Ĳ���9
	mySql2.addSubPara(Customerno);//ָ������Ĳ���10
	mySql2.addSubPara(CustomerIDno18);//ָ������Ĳ���11
	mySql2.addSubPara(CustomerIDno15);//ָ������Ĳ���12
	mySql2.addSubPara(Customerno);//ָ������Ĳ���13
	mySql2.addSubPara(Customerno);//ָ������Ĳ���14
	mySql2.addSubPara(Customerno);//ָ������Ĳ���15
	mySql2.addSubPara(Customerno);//ָ������Ĳ���16
	mySql2.addSubPara(Customerno);//ָ������Ĳ���17
	mySql2.addSubPara(CustomerSex);//ָ������Ĳ���18
	mySql2.addSubPara(CustomerBrithday);//ָ������Ĳ���19
	mySql2.addSubPara(CustomerIDno.replace(/[^0-9]/g,''));//ָ������Ĳ���20
	mySql2.addSubPara(Customerno);//ָ������Ĳ���21
	strSQL=mySql2.getString();
  
	arrResult = easyExecSql(strSQL);//prompt("",strSQL);
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
	   //����
		var selno = ClientList.getSelNo()-1;
		if (selno <0)
		{
			alert( "����ѡ����ͬ��Ͷ����" );
			return;
		}	
		fm.CustomerNo_NEW.value = OPolGrid.getRowColData(0, 1);
		
	}
	if(exchangeValue == "-1")
	{
	   //����
		var selno = ClientList.getSelNo()-1;
		if (selno <0)
		{
			alert( "����ѡ����ͬ��Ͷ����" );
			return;
		}
		var selno1 = 	OPolGrid.getSelNo()-1;
		fm.CustomerNo_NEW.value = ClientList.getRowColData(selno, 1);
			
	}
}

function displayAppnt(){
	var arrResult;
	if(AppntNo==""||AppntNo=="null"||AppntNo==null){
		return;
	}
	//tongmeng 2009-06-08 modify
	//�޸Ĳ�ѯ�߼�
//	var AppntSql = "select customerno,name,sex,birthday,idtype,idno,othidtype,othidno from ldperson where customerno='"+AppntNo+"'";
//	var AppntSql = "select a.appntno,a.appntname,a.appntsex,a.appntbirthday,a.idtype,a.idno,'','' "
//				+ ",b.PostalAddress,b.mobile,b.phone,b.grpname,c.managecom,(select d.name from laagent d where d.agentcode=c.agentcode) "
//				+ " from lcappnt a ,lcaddress b,lccont c "
//				+ " where c.contno='"+PtrNo+"' and a.contno=c.contno and a.appntno='"+AppntNo+"' and b.customerno=a.appntno and b.addressno=a.addressno ";
	
	var sqlid3="CustomerMergeSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.CustomerMergeSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(PtrNo);//ָ������Ĳ���
	mySql3.addSubPara(AppntNo);//ָ������Ĳ���
	var AppntSql=mySql3.getString();
	
	//prompt('',AppntSql);
	arrResult=easyExecSql(AppntSql,1,0);
	if (arrResult)
	{
		displayMultiline(arrResult,OPolGrid);
		if(OPolGrid.canSel==1&&OPolGrid.mulLineCount>1)
		 {
		 	document.all('OPolGridSel')[0].checked=true;
		 	initcustomerInfoequels();
		 }
		 if(OPolGrid.canSel==1&&OPolGrid.mulLineCount==1)
		 {		 			
		 	document.all('OPolGridSel').checked=true;
		 	initcustomerInfoequels();
		 }
	}
}

function displayInsured(){
	var arrResult;//alert(203);
	if(InsuredNo==""||InsuredNo=="null"||InsuredNo==null){
		return;
	}
	//var InsSql="select "
//	var InsuredSql="select customerno,name,sex,birthday,idtype,idno,othidtype,othidno from ldperson where customerno in ("+
//				"select Insuredno from lcinsured where contno='"+PtrNo+"')";
//	var InsuredSql="select a.insuredno,a.name,a.sex,a.birthday,a.idtype,a.idno,'','' "
//				+ ",b.PostalAddress,b.mobile,b.phone,b.grpname,c.managecom,(select d.name from laagent d where d.agentcode=c.agentcode) "
//				+ " from lcinsured a ,lcaddress b,lccont c "
//				+ " where c.contno='"+PtrNo+"' and a.contno=c.contno and a.insuredno='"+InsuredNo+"' and b.customerno=a.insuredno and b.addressno=a.addressno ";

	var sqlid4="CustomerMergeSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("app.CustomerMergeSql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(PtrNo);//ָ������Ĳ���
	mySql4.addSubPara(InsuredNo);//ָ������Ĳ���
	var InsuredSql=mySql4.getString();
	
	arrResult=easyExecSql(InsuredSql,1,0);
	if (arrResult)
	{
		displayMultiline(arrResult,OPolGrid);
		if(OPolGrid.canSel==1&&OPolGrid.mulLineCount>1)
		 {
		 	document.all('OPolGridSel')[0].checked=true;
		 	initcustomerInfoequels();
		 }
		 if(OPolGrid.canSel==1&&OPolGrid.mulLineCount==1)
		 {	 			
		 	document.all('OPolGridSel').checked=true;
		 	initcustomerInfoequels();
		 }
	} 

}

function initMission(){
	fm.MissionID.value=tMissionId;
	fm.SubMissionID.value=tSubMissionId;
	fm.CustomerType.value=tCustomerType;
	//alert("missionid=="+fm.MissionID.value+"&&submissionid=="+fm.SubMissionID.value+"fm.CustomerType.value=="+fm.CustomerType.value);
}

//function get15IDNo(IDNo) {
//	var str = "";
//	str += IDNo.substring(0, 6);
//	str += IDNo.substring(8, 17);
//	return str;
//}

//function get18IDNo(IDNo, Birthday) {
//	if (IDNo.length() == 18) {
//		if (IDNo.endsWith("x"))
//			IDNo = IDNo.substring(0, 17) + "X";
//		return IDNo;
//	}
//	var str = "";
//	str += IDNo.substring(0, 6);
//	if (Birthday.length == 10) {
//		str += Birthday.substring(0, 2);
//	} else
//		str += "19";
//	str += IDNo.substring(6, 15);
//	var n = 0;
//	var weight =  { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
//			8, 4, 2 };
//	for (i = 0; i < 17; i++) {
//		n += str.substring(i, i + 1) * weight[i];
//	}
//	n %= 11;
//	if (n == 0)
//		str += "1";
//	else if (n == 1)
//		str += "0";
//	else if (n == 2)
//		str += "X";
//	else if (n == 3)
//		str += "9";
//	else if (n == 4)
//		str += "8";
//	else if (n == 5)
//		str += "7";
//	else if (n == 6)
//		str += "6";
//	else if (n == 7)
//		str += "5";
//	else if (n == 8)
//		str += "4";
//	else if (n == 9)
//		str += "3";
//	else if (n == 10)
//		str += "2";
//	return str;
//}

/**
  * 15λ���֤����ת��Ϊ18λ�����֤��
  * �����18λ�����֤��ֱ�ӷ��أ������κα仯��
  * @param idCard,15λ����Ч���֤����
  * @return idCard18 ����18λ����Ч���֤
  */
 function IdCard15to18(idCard){
  idCard = idCard.trim();
  var idCard18 = new String(idCard);
  //У����ֵ
  var checkBit = ['1','0','X','9','8','7','6','5','4','3','2'];
  var sum = 0;
  //alert("idCard18="+idCard18.length);
  
  //15λ�����֤
  if(idCard!=null && idCard.length==15){
  
   idCard18 = idCard18.substring(0,6)+"19"+idCard18.substring(6,15);

   for(var index=0;index<idCard18.length;index++){
    var c = idCard18.charAt(index);
    var ai = parseInt(c);
    //��Ȩ���ӵ��㷨
    var Wi = (Math.pow(2, idCard18.length-index))%11;
    sum = sum+ai*Wi;
   }
   var indexOfCheckBit = sum%11; //ȡģ
   idCard18 = idCard18+(checkBit[indexOfCheckBit]);
  }
  
  //alert("idCard18="+idCard18);
  return idCard18;
 }
 
 /**
  * ת��18λ���֤λ15λ���֤������������15λ�����֤�����κ�ת����ֱ�ӷ��ء�
  * @param idCard 18λ���֤����
  * @return idCard15
  */
 function IdCard18to15(idCard){
   idCard = idCard.trim();
  var idCard15 = new String(idCard);
  if(idCard!=null && idCard.length==18){
  
   idCard15 = idCard15.substring(0, 6)+idCard15.substring(8, 17);
   
  }
  //alert("idCard15="+idCard15);
  return idCard15;
  
 }

function QueryAllPol(tCustomerNo)
{
	initCustomerContGrid();
//	var tSQL_CustomerPol = "select a.grpcontno,a.contno,a.prtno "
//						 + " ,b.name,b.sex,b.birthday,b.idtype,b.idno,c.PostalAddress,c.mobile,c.phone,c.grpname "
//	                     + " ,d.appntname,d.appntsex,d.appntbirthday,d.idtype,d.idno,e.PostalAddress,e.mobile,e.phone,e.grpname "
//	                     + " ,a.managecom,(select f.name from laagent f where f.agentcode=a.agentcode) "
//	                     + " ,(case when (a.appflag='1' or a.appflag='4') then  to_char(a.cvalidate,'yyyy-mm-dd') when a.appflag='0' then '' end ),a.conttype "
//	                     + " from lccont a,lcinsured b,lcaddress c,lcappnt d, lcaddress e "
//                       + " where a.contno=b.contno and b.insuredno='"+tCustomerNo+"' "
//                       + " and c.customerno=b.insuredno and c.addressno=b.addressno "
//                       + " and d.contno=a.contno "
//                       + " and e.customerno=d.appntno and e.addressno=d.addressno "
//                       + " and a.appflag in ('0','1','4') "
//										   + " union "
//                       + "select a.grpcontno,a.contno,a.prtno "
//						 + " ,b.name,b.sex,b.birthday,b.idtype,b.idno,c.PostalAddress,c.mobile,c.phone,c.grpname "
//	                     + " ,d.appntname,d.appntsex,d.appntbirthday,d.idtype,d.idno,e.PostalAddress,e.mobile,e.phone,e.grpname "
//	                     + " ,a.managecom,(select f.name from laagent f where f.agentcode=a.agentcode) "
//	                     + " ,(case when (a.appflag='1' or a.appflag='4') then  to_char(a.cvalidate,'yyyy-mm-dd') when a.appflag='0' then '' end ),a.conttype "
//	                     + " from lccont a,lcinsured b,lcaddress c,lcappnt d, lcaddress e "
//                       + " where a.contno=b.contno "
//                       + " and c.customerno=b.insuredno and c.addressno=b.addressno "
//                       + " and d.contno=a.contno and d.appntno='"+tCustomerNo+"' "
//                       + " and e.customerno=d.appntno and e.addressno=d.addressno "
//                       + " and a.appflag in ('0','1','4') ";
	
	var sqlid5="CustomerMergeSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("app.CustomerMergeSql"); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(PtrNo);//ָ������Ĳ���
	mySql5.addSubPara(InsuredNo);//ָ������Ĳ���
	var tSQL_CustomerPol=mySql5.getString();
	
	
	
	turnPage3.queryModal(tSQL_CustomerPol, CustomerContGrid);
}

function getContDetailInfo()
{
    var tsel = CustomerContGrid.getSelNo() - 1;
    if (tsel < 0)
    {
        alert("��ѡ�񱣵���");
        return;
    }
    //09-12-22 lixiang  ������һ�汾�Բ�ѯ��mulitLine�����ݽ��й��޸ģ�����ԭ����conttype
    //��������ڵ�idtype  ���Իᵼ���޷��򿪱�����ϸҳ�� 
    //���ǵ��ͻ����������漰�����գ����Դ˴��޸�Ϊ��conttypeд��Ϊ1
//    var tContType = CustomerContGrid.getRowColData(tsel, 7);
    var tContType = "1";
//alert(tContType);
    if (tContType == "1")
    {
    	//tongmeng 2009-06-09 modify
    	//�޸�Ͷ������ʾ�߼�
        //var tSql = "select salechnl from lccont where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
       /* var tSql = "select case lmriskapp.riskprop"
                + " when 'I' then"
                + " '1'"
                + " when 'G' then"
                + " '2'"
                + " when 'Y' then"
                + " '3'"
                + " end"
                + " from lmriskapp"
                + " where riskcode in (select riskcode"
                + " from lcpol"
                + " where polno = mainpolno"
                + " and contno = '" + CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 2) + "')"
    
        var BankFlag = ""
        var brrResult = easyExecSql(tSql);
        if (brrResult != null)
        {
            BankFlag = brrResult[0][0];
        }*/
        
       var  tSplitPrtNo = CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 3).substring(0,4);
    var BankFlag = "";
    var SubType = "";
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	//�ж��Ƿ�Ϊ���ʱ�ͨ  ��������ʱ�ͨ BankFlag=3 subtype='TB1003'
    	//���������һ��
    	var prtno = CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 3);
//    	var CheckSql = "select 1 from lccont where prtno='"+prtno+"' and salechnl='03' and selltype='08'";
    	
    	var sqlid6="CustomerMergeSql6";
    	var mySql6=new SqlClass();
    	mySql6.setResourceName("app.CustomerMergeSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
    	mySql6.addSubPara(PtrNo);//ָ������Ĳ���
    	mySql6.addSubPara(InsuredNo);//ָ������Ĳ���
    	var CheckSql=mySql6.getString();
    	
    	var arr = easyExecSql(CheckSql);//prompt("",CheckSql);
        if(!arr){
        	BankFlag="1";
        	SubType = "01";
        }else {
        	BankFlag="3";
        	SubType = "03";
        }
    }else{
    	BankFlag="1";
    	SubType = "01";
    }
        window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo=" + CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 3) + "&ContNo=" + CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 2) + "&BankFlag=" + BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
    }
    
    if (tContType == "2")
    {
        cContNo=CustomerContGrid.getRowColData(tsel,2);
        cPrtNo=CustomerContGrid.getRowColData(tsel,3);;
        cGrpContNo=CustomerContGrid.getRowColData(tsel,1);
        mSwitch.deleteVar( "ContNo" );
      	mSwitch.addVar( "ContNo", "", cContNo );
      	mSwitch.updateVar("ContNo", "", cContNo);
      	mSwitch.deleteVar( "ProposalContNo" );
      	mSwitch.addVar( "ProposalContNo", "", cContNo );
      	mSwitch.updateVar("ProposalContNo", "", cContNo);
      	mSwitch.deleteVar( "PrtNo" );
      	mSwitch.addVar( "PrtNo", "", cPrtNo );
      	mSwitch.updateVar("PrtNo", "", cPrtNo);
      	mSwitch.deleteVar( "GrpContNo" );
      	mSwitch.addVar( "GrpContNo", "", cGrpContNo );
      	mSwitch.updateVar("GrpContNo", "", cGrpContNo);
  	    //window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&Auditing=1","window1");
  		window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&checktype=2&ContType=2&Auditing=1&ProposalGrpContNo="+cGrpContNo+"&ContNo="+cContNo+"&NameFlag=0","window1");
    }
}