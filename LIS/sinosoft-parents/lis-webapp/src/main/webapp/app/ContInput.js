//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo; 
var approvefalg;
var arrResult;
var arrResult1;
var mDebug = "0";
var mOperate = 0;
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
var turnPage = new turnPageClass();
var updateFlag="0";
this.window.onfocus=myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

//��¼��Ҫ¼������ʱ������ֵ
//var theFirstValue="";
//var theSecondValue="";

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo1;

//�����Ӷ���ִ�еĴ���
var addAction = 0;
//�ݽ����ܽ��
var sumTempFee = 0.0;
//�ݽ�����Ϣ�н��ѽ���ۼ�
var tempFee = 0.0;
//�ݽ��ѷ�����Ϣ�н��ѽ���ۼ�
var tempClassFee = 0.0;
//����ȷ���󣬸ñ�����Ϊ�棬�������һ��ʱ�������ֵ�Ƿ�Ϊ�棬Ϊ�������Ȼ���ٽ��ñ����ü�
var confirmFlag = false;
//
var arrCardRisk;

//������flag
var mWFlag = 0;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�


/*********************************************************************
 *  ���漯��Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function otherCheckappnt(){
   var tPolType =prtNo.substr(0,4);
   //alert(tPolType);
   if(tPolType!="8616"&&tPolType!="8615"&&tPolType!="8625"&&tPolType!="8635"){
      if(fm.AppntMarriage.value==null||fm.AppntMarriage.value==""){
         alert("��¼��Ͷ���˻���״����");
         return false;
      }
      if(fm.AppntOccupationCode.value==null||fm.AppntOccupationCode.value==""){
         alert("��¼��Ͷ����ְҵ���룡");
         return false;
      }
      if(fm.AppntOccupationType.value ==null||fm.AppntOccupationType.value==""){
         alert("��¼��Ͷ����ְҵ���");
         return false;
      }
      //if(fm.Marriage.value ==null||fm.Marriage.value==""){
      //   alert("��¼�뱻���˻���״����");
      //   return false;
      //}
   }
   return true;
}
function otherCheckinsurd(){
   var tPolType =prtNo.substr(0,4);
   //alert(tPolType);
   if(tPolType!="8616"&&tPolType!="8615"&&tPolType!="8625"&&tPolType!="8635"){
   /*
      if(fm.AppntMarriage.value==null||fm.AppntMarriage.value==""){
         alert("��¼��Ͷ���˻���״����");
         return false;
      }
      if(fm.AppntOccupationCode.value==null||fm.AppntOccupationCode.value==""){
         alert("��¼��Ͷ����ְҵ���룡");
         return false;
      }
      if(fm.AppntOccupationType.value ==null||fm.AppntOccupationType.value==""){
         alert("��¼��Ͷ����ְҵ���");
         return false;
      }
      */
      if(fm.Marriage.value ==null||fm.Marriage.value==""){
         alert("��¼�뱻���˻���״����");
         return false;
      }
   }
   return true;
}
 
function submitForm()
{
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	if(otherCheckappnt()==false) return false;
	if(fm.PolAppntDate.value.length!=10 || fm.PolAppntDate.value.substring(4,5)!='-' || fm.PolAppntDate.value.substring(7,8)!='-' || fm.PolAppntDate.value.substring(0,1)=='0')
	{
		alert("��������ȷ�����ڸ�ʽ��");
		fm.PolAppntDate.focus();
		return;
	}
	var sqlid81="ContInputSql81";
	var mySql81=new SqlClass();
	mySql81.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql81.setSqlId(sqlid81);//ָ��ʹ�õ�Sql��id
    mySql81.addSubPara("1");//ָ������Ĳ���
	var tSql=mySql81.getString();
	//var tSql = "select sysdate from dual";
	turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	var tSysDate = turnPage.arrDataCacheSet[0][0];
	if(fm.PolAppntDate.value>tSysDate)
	{
		alert("Ͷ������Ӧ��С�ڵ�ǰ����");
		return ;
	}
	if(fm.AppntIDType.value==""||fm.AppntIDType.value==null)
	{
		alert("������д֤������");
		return false;
	}
	if((fm.PayMode.value=="4"||fm.PayMode.value=="7"))
	{
		if(fm.AppntBankCode.value==""||fm.AppntAccName.value==""||fm.AppntBankAccNo.value==""){
			alert("����������ת�ʿ����С������ʻ������������ʻ��ţ�");
			return false;
		}
	}
	if(fm.SecPayMode.value=="0")
	{
		if(fm.SecAppntBankCode.value==""||fm.SecAppntAccName.value==""||fm.SecAppntBankAccNo.value==""){
			alert("����������ת�ʿ����С������ʻ������������ʻ��ţ�");
			return false;
		}
	}
	//Add 2011-11-28 20:03  cuilong
	if(document.all('AppntIDType').value!="9" && document.all("AppntIDNo").value == "")
	{
		alert("Ͷ����֤�����벻��Ϊ�գ�");
		document.all('AppntIDNo').select();
		return false;
	}
	if(document.all('AppntIDType').value=="0")
	{
		var strChkIdNo = chkIdNo(trim(document.all('AppntIDNo').value),trim(document.all('AppntBirthday').value),trim(document.all('AppntSex').value));
		if (strChkIdNo != "")
		{
			alert(strChkIdNo);
			return false;   //2010-11-09 20:38 cuilong
		}
	}
	if(notNullCheck()==false){return false;}
	//hanlin ����У������⣬��ʱȥ������ͷ�ټ��ϣ�hl 20050502
	//У���Ѿ����� hl 20050512
	if( verifyInputNB("1") == false ) 
	{return false;}
	
	/*����Ƿ����޸�ʱʹ�ã�ִ�и��²��� 20041125 wzw*/
	if(LoadFlag=="3")
	{
		updateClick();
		return;
	}
	if(document.all('PayMode').value=='7'){ }
	if(document.all('SecPayMode').value=='7'){ }

	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	if( mAction == "" )
	{
		mAction = "INSERT";
		document.all( 'fmAction' ).value = mAction;
		ImpartGrid.delBlankLine();
		if (document.all('ProposalContNo').value == "") 
		{
		  mAction = "";
		} 
		else 
		{
		  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		  var iWidth=550;      //�������ڵĿ��; 
		  var iHeight=250;     //�������ڵĸ߶�; 
		  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		  showInfo.focus();

		  //��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
		  tAction = fm.action;
		  fm.action = fm.action + "?BQFlag=" + BQFlag;
		  //tongmeng 2008-06-10 modify
		  //û�б�Ҫ
		  //getdetailwork();
		  //fm.action="ContSave.jsp";
		  document.getElementById("fm").submit(); //�ύ
		  fm.action = tAction;
		}
	}
}

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content,contract )
//function afterSubmit(  )
{
	unlockScreen('lkscreen');  
	//alert(143);
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo1.focus();
	}
	else
	{
		if(loadFlag==5)
		{
			var mark;
			
				var sqlid82="ContInputSql82";
	var mySql82=new SqlClass();
	mySql82.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql82.setSqlId(sqlid82);//ָ��ʹ�õ�Sql��id
    mySql82.addSubPara(contract);//ָ������Ĳ���
	var tSql=mySql82.getString();
			
//			strSql = "select uwflag From lccont where contno='"+contract+"'"; 
			
			var sqlid85="ContInputSql85";
			var mySql85=new SqlClass();
			mySql85.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql85.setSqlId(sqlid85);//ָ��ʹ�õ�Sql��id
			mySql85.addSubPara(contract);//ָ������Ĳ���
			strSql=mySql85.getString();
			
			var uwflag = easyExecSql(strSql);
			if(uwflag!="9")
			{
				mark="fail";
			}
			
	var sqlid83="ContInputSql83";
	var mySql83=new SqlClass();
	mySql83.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql83.setSqlId(sqlid83);//ָ��ʹ�õ�Sql��id
    mySql83.addSubPara(contract);//ָ������Ĳ���
	strSql=mySql83.getString();
			
			//strSql = "select uwflag From lcpol where contno='"+contract+"'"; 
			var uwflag2 = easyExecSql(strSql);
			var i;
			for(i=0;i<uwflag2.length;i++)
			{
				if(uwflag2[i][0]!="9")
				{
					mark="fail";
				}
			}
	    }
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo1.focus();
		showDiv(operateButton, "true");
	    if(loadFlag==1){top.close();  }
	    if(loadFlag==5){top.close();}
		if(approvefalg=="2")
		{
			//top.close();
		}
		//tongmeng 2009-06-02 modify
		//����ˢ�¸��˹�����
		try
		{
			//fm.WorkFlowFlag.value = "0000001002"
			 var tempFlag = fm.WorkFlowFlag.value ;
			 //alert(tempFlag);
			 if(tempFlag!=null&&tempFlag=='0000001002')
			 {
			 	 top.opener.initQuerySelf();
			 	 top.close();
			 }
		}
		catch(e)
		{
		}
	}
	mAction = "";
}



function afterSubmit4( FlagStr, content )
{
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo1.focus();
	}
	else
	{
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo1.focus();
		showDiv(operateButton, "true");
		if(approvefalg=="2")
		{
			//top.close();
		}
		document.all("AppntName").value = document.all("AppntLastName").value+document.all("AppntFirstName").value;
		//���Ͷ��������ͬһ����ͬ���޸ı�������Ϣ
		if(fm.RelationToInsured.value == "00" ){
			//tongmeng 2010-11-12 modify ����ʱ����ͬ������
			//mAction = "INSERT";
		   if(document.all( 'fmAction' ).value == "UPDATE" )
		   {
		   	 //�����������ֵ,���������޸�
		   	 var tempContno = document.all('ProposalContNo').value;
		   	 var sqlid84="ContInputSql84";
				 var mySql84=new SqlClass();
				 mySql84.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
         mySql84.setSqlId(sqlid84);//ָ��ʹ�õ�Sql��id
         mySql84.addSubPara(tempContno);//ָ������Ĳ���
	       var tempstrSql=mySql84.getString();
			
			//strSql = "select uwflag From lcpol where contno='"+contract+"'"; 
			   var insuredFlag = easyExecSql(tempstrSql);
			   if(insuredFlag>0)
			   {
	   	 // 	if(confirm("Ͷ�����뱻���˹�ϵΪ���ˣ�ȷ�ϸ��±�������Ϣ��")){
	    			var showStr="�����޸ı���������Ϣ�����Ժ�...";
	   		  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	    			showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	     			updateInsured();
	     		}
	     	}
	     //	}else{ 
    	//		alert("���޸�Ͷ�����뱻���˹�ϵ������������ݻ��ң�");
    	//	}
    	}
  		//var sqlstr = "select * from lcappnt a,ldperson b "
		//		+" where a.appntname=b.name and a.appntsex=b.sex and a.appntbirthday=b.birthday"
		//		+" and a.appntno='"+fm.AppntNo.value+"' and a.appntno<>b.customerno"
		//		+" union "		
		//		+" select * from lcappnt a,ldperson b "
		//		+" where a.idtype=b.idtype and b.idtype is not null "
		//		+" and b.idno=a.idno and b.idno is not null"
		//		+" and a.appntno='"+fm.AppntNo.value+"' and a.appntno<>b.customerno";
		
		//var arrResult = easyExecSql(sqlstr,1,0);
        //if(arrResult!=null){
		//	alert("Ͷ���˴������ƿͻ��������Ͷ����У�顣");
		//}
		getInsuredPolInfo();
	}
	mAction = "";
}


function afterSubmit5( FlagStr, content )
{
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo1.focus();
	}
	else
	{
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo1.focus();
		showDiv(operateButton, "true");
  	    top.close();
		if(approvefalg=="2")
		{
			//top.close();
		}
	}
	mAction = "";
}

/*********************************************************************
 *  "����"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function resetForm()
{
	try
	{
		initForm();
		document.all('PrtNo').value = prtNo;
	}
	catch( re )
	{
		alert("��GroupPolInput.js-->resetForm�����з����쳣:��ʼ���������!");
	}
}

/*********************************************************************
 *  "ȡ��"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function cancelForm()
{
	showDiv(operateButton,"true");
	showDiv(inputButton,"false");
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  Click�¼������������ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addClick()
{
	//����������Ӧ�Ĵ���
	showDiv( operateButton, "false" );
	showDiv( inputButton, "true" );
	if( verifyInput2() == false ) return false;
}

/*********************************************************************
 *  Click�¼������������ѯ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryClick()
{
	if(this.ScanFlag == "1")
	{
		alert( "��ɨ���¼�벻�����ѯ!" );
		return false;
	}
	if( mOperate == 0 )
	{
		mOperate = 1;
		cContNo = document.all( 'ContNo' ).value;
		showInfo = window.open("./ContQueryMain.jsp?ContNo=" + cContNo,"",sFeatures);
	}
}

/*********************************************************************
 *  Click�¼�����������޸ġ�ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function updateClick()
{
//	var AppntSql="select * from lcappnt where contno='"+fm.ProposalContNo.value+"'";
	var sqlid86="ContInputSql86";
	var mySql86=new SqlClass();
	mySql86.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql86.setSqlId(sqlid86);//ָ��ʹ�õ�Sql��id
	mySql86.addSubPara(fm.ProposalContNo.value);//ָ������Ĳ���
	var AppntSql=mySql86.getString();
	
	turnPage.strQueryResult = easyQueryVer3(AppntSql, 1, 0, 1);
	if(!turnPage.strQueryResult){
		alert("��δ����Ͷ������Ϣ���޷��޸ģ�");
		return false;
	}
	if(notNullCheck()== false){return false;}
	var tAppntName=fm.AppntName.value;
	var tAppntSex = fm.AppntSex.value;
	var tBirthday = fm.Birthday.value;
	var tIdType = fm.AppntIDType.value;
	var tIdNo = fm.IDNo.value;
	if(otherCheckappnt() == false) return false;
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID) == false)
	{
		return false;
	}

  if(fm.AppntIDType.value==""||fm.AppntIDType.value==null){
	  alert("������д֤������");
	  return false;
	}
  if((fm.PayMode.value=="4"||fm.PayMode.value=="7"))
	{
		if(fm.AppntBankCode.value==""||fm.AppntAccName.value==""||fm.AppntBankAccNo.value==""){
			alert("����������ת�ʿ����С������ʻ������������ʻ��ţ�");
			return false;
		}
	}
	if(fm.SecPayMode.value=="0")
	{
		if(fm.SecAppntBankCode.value==""||fm.SecAppntAccName.value==""||fm.SecAppntBankAccNo.value==""){
			alert("����������ת�ʿ����С������ʻ������������ʻ��ţ�");
			return false;
		}
	}
	//Add 2011-11-28 20:03  cuilong
	if(document.all('AppntIDType').value!="9" && document.all("AppntIDNo").value == "")
	{
		alert("Ͷ����֤�����벻��Ϊ�գ�");
		document.all('AppntIDNo').select();
		return false;
	}
	//2005-8-6 16:16 guomy���ϴ˶�У��
  if(document.all('AppntIDType').value=="0")
  {
    var strChkIdNo = chkIdNo(trim(document.all('AppntIDNo').value),trim(document.all('AppntBirthday').value),trim(document.all('AppntSex').value));
    if (strChkIdNo != "")
    {
      alert(strChkIdNo);
	  return false;   //2010-11-09 20:38 cuilong
    }
  }
 
  //���ӡ��������ڡ���У�� --2010-03-18 --hanbin
  //���� ��ServiceSpecification  ����֮��ʼУ�� hanbin 2010-04-29
  //ȥ���������ڵ�У�� 2010-5-5
//  sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
//  var arrResult2 = easyExecSql(sql);
//  if(arrResult2!=null && (calAgeNew(arrResult2[0][0],document.all("PolAppntDate").value)>=0))
//  {
//	  if(fm.FirstTrialDate.value < fm.PolAppntDate.value)
//	  {
//	  	 alert("�������ڲ�������Ͷ���������ڣ�");
//	  	 fm.FirstTrialDate.value = "";
//	  	 return false;
//	  }
//	  strSql = "select makedate from es_doc_main a  where a.doccode = '"+fm.ProposalContNo.value+"' and a.busstype = 'TB' and a.subtype = 'UA001'"; 
//	  var scanDate = easyExecSql(strSql);
//	  if(fm.FirstTrialDate.value >  scanDate)
//	  {
//	  	 alert("�������ڲ�������ɨ�����ڣ�");
//	  	 fm.FirstTrialDate.value = "";
//	  	 return false;
//	  }
//  }
  
  //tongmeng 2007-12-05 modify
	//ȥ���˴�
	/*
  	if(fm.AppntNo.value==''&&fm.AppntAddressNo.value!='')
  {
    alert("Ͷ���˿ͻ���Ϊ�գ������е�ַ����");
    return false;
  }*/
  /*
  	2008-12-27
  	��ѯ�Ƿ���Ͷ������Ҫ��Ϣ�޸ģ�����ǲ����뱻���˹�ϵΪ��00�����ˣ���,�ж��Ƿ��Ѿ�ɾ�������ˣ�
  	���δɾ����ʾɾ���������˲� return false
  */
  //var AppntImport="select * from ldperson where name='"+tAppntName+"' and sex='"+tAppntSex+"'"
  //					+" and idtype='"+tIdType+"' and idno='"+tIdNo+"'"
  //					+" and birthday =to_date('" + tBirthday + "','yyyy-mm-dd') ";
  //turnPage.strQueryResult = easyQueryVer3(AppntImport, 1, 0, 1);
  //if(!turnPage.strQueryResult){
  	//Ͷ������Ҫ��Ϣ����
  //	var InsuredSql = "select * from lcinsured where contno='"+fm.ProposalContNo.value+"'";
  //	turnPage.strQueryResult = easyQueryVer3(InsuredSql, 1, 0, 1);
  //	if(fm.RelationToInsured.value=="00"){
	//  	if(turnPage.strQueryResult){
	//  		alert("���˴β���Ϊ�޸�Ͷ������Ҫ���ϣ�����ɾ����������");
	//  		return false;
	//  	}
  	//}
  //}
  if( verifyInputNB("1") == false ) return false;
	var tGrpProposalNo = "";
	tGrpProposalNo = document.all( 'ProposalContNo' ).value;
	if( tGrpProposalNo == null || tGrpProposalNo == "" )
	{
		alert( "������Ͷ������ѯ�������ٽ����޸�!" );
	}
	else
	{
				ImpartGrid.delBlankLine(); 
		var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			getdetailwork();
			showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
			mAction = "UPDATE";
			document.all( 'fmAction' ).value = mAction;
			//alert(mAction);
			fm.action="ContSave.jsp";
			document.getElementById("fm").submit(); //�ύ
		}
	}
}

/*********************************************************************
 *  Click�¼����������ɾ����ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteClick()
{
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	var tGrpProposalNo = "";
	var tProposalContNo = "";
	if(LoadFlag==1)
	{
		tGrpProposalNo = document.all( 'GrpContNo' ).value;
		if( tGrpProposalNo == null || tGrpProposalNo == "" )
		{	
			alert( "������Ͷ������ѯ�������ٽ���ɾ��!" );
		}
		else
		{
			var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
			if( mAction == "" )
			{
				showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
				mAction = "DELETE";
				document.all( 'fmAction' ).value = mAction;
				fm.action="ContSave.jsp";
				document.getElementById("fm").submit(); //�ύ
			}
		}
    }
}

/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";
}

/*********************************************************************
 *  ����������������Ϣ����ťʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function intoInsured()
{
	//����������Ӧ�Ĵ���
	var tAppntNo = fm.AppntNo.value;
	var tAppntName = fm.AppntName.value;
	fm.ContNo.value=fm.ProposalContNo.value;
	if( fm.ContNo.value == "" )
	{
		alert("��������¼���ͬ��Ϣ���ܽ��뱻��������Ϣ���֡�");
		return false;
	}
	//�Ѽ�����Ϣ�����ڴ�
	mSwitch = parent.VD.gVSwitch;  //���ݴ�
	putCont();
	try { goToPic(2) } catch(e) {}
	try 
	{
	  parent.fraInterface.window.location = "./ContInsuredInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type+ "&MissionID=" + tMissionID+ "&SubMissionID=" + tSubMissionID+ "&AppntNo=" + tAppntNo+ "&AppntName=" + tAppntName+"&checktype=1"+"&scantype="+scantype ;
	}
	catch (e) 
	{
		parent.fraInterface.window.location = "./ContInsuredInput.jsp?LoadFlag=1&type=" + type+ "&MissionID=" +tMissionID+ "&SubMissionID=" + tSubMissionID+ "&AppntNo=" + tAppntNo+ "&AppntName=" + tAppntName;
	}
}

/*************************************************************************************
��¼������ʱ��ϵͳ���ɵı���������¼��ı��ѽ���У�飬
�������¼��ı�����ϵͳ���ɵı��Ѳ�����ϵͳҪ������ʾ�����Կɱ���,�����Կ���ת
�����������ͬ��<Ͷ������>;     ����:true or false
*************************************************************************************/
function checkpayfee(ContNo)
{
	var tContNo=ContNo;
	var tTempFee="";//����¼��ı���
	var tPremFee="";//ϵͳ���ɵı���
	//��ѯ����¼��ı���
	
	var sqlid1="ContInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    mySql1.addSubPara(tContNo);//ָ������Ĳ���
	var tempfeeSQL=mySql1.getString();
	
//	var tempfeeSQL="select sum(nvl(paymoney,0)) from ljtempfee where tempfeetype='1' and confdate is null "
//		+" and otherno=(select contno from lccont where prtno= '"+tContNo+"')";
	var TempFeeArr=easyExecSql(tempfeeSQL);	
	if(TempFeeArr!=null)
	{
		tTempFee=TempFeeArr[0][0];
	}
	//��ѯϵͳ���ɵı���
	
	var sqlid2="ContInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    mySql2.addSubPara(tContNo);//ָ������Ĳ���
	var premfeeSQL=mySql2.getString();
	
//	var premfeeSQL="select sum(nvl(prem,0)) from lcpol where 1=1 "
//		+" and contno=(select contno from lccont where prtno= '"+tContNo+"')";	
	var PremFeeArr=easyExecSql(premfeeSQL);	
	tPremFee=PremFeeArr[0][0];	
	if(PremFeeArr!=null)
	{
		tPremFee=PremFeeArr[0][0];
		if(tPremFee==null || tPremFee=="" || tPremFee=="null")
		{
		alert("��ѯ��Ͷ������������Ϣ�����ɱ�������ʧ�ܣ�����");
		return false;	
		}
	}
	//�Ƚϡ���ѯ����¼��ı��ѡ� �� ����ѯϵͳ���ɵı��ѡ��Ƿ���ȣ��粻����򵯳���Ϣ��ʾ
	if(tTempFee!="" && tPremFee!="" && tTempFee!="null" && tPremFee!="null" && (tTempFee!=tPremFee))
	{
		var ErrInfo="ע�⣺����¼��ı���["+tTempFee+"]��ϵͳ���ɵı���["+tPremFee+"]���ȡ�\n";
		ErrInfo=ErrInfo+"ȷ����Ҫ������ȷ�豣���밴��ȷ���������򰴡�ȡ������";
		if(confirm(ErrInfo)==false)
		{
		   return false;	
		}
	}
	return true;
}
/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm(wFlag) {
    if(checkOtherQuest()==false)
       return false;
	if (wFlag ==1 ) //¼�����ȷ��
	{
		
			var sqlid3="ContInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
    mySql3.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	var tStr=mySql3.getString();
		
		//var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
		turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
		if (turnPage.strQueryResult) 
		{
			alert("�ú�ͬ�Ѿ��������棡");
			return;
		}
		if(document.all('ProposalContNo').value == "")
		{
			alert("��ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
			return;
		}
		//���¼�����ʴ����У��<����¼��ı�����ϵͳ���ɵı��Ѳ�����ϵͳҪ������ʾ�����Կɱ���>
		if(checkpayfee(document.all('ProposalContNo').value)==false)
		{
			return false;	
		}
		if(ScanFlag=="1")
		{
			fm.WorkFlowFlag.value = ActivityID;
		}
		else
		{
			fm.WorkFlowFlag.value = ActivityID;
		}
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;			//¼�����
	}
	else if (wFlag ==2)//�������ȷ��
	{ 
		/*if(fm.AppntChkButton.disabled=="")
		{ 
			var strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj = '"+document.all('AppntNo').value+"'";
			var brrResult = easyExecSql(strSql);
			if(brrResult==null)	
			{
				if(confirm("�Ƿ����Ͷ����У�飿"))
				return;
			}
		}	
		if(fm.InsuredChkButton.disabled=="")
		{
			strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj in (select insuredno from lcinsured where contno='"+fm.ContNo.value+"')";
			var crrResult = easyExecSql(strSql);
			if(crrResult==null)
			{
			if(confirm("�Ƿ���б�����У�飿"))
			return;
			}
		}*/
		if(document.all('ProposalContNo').value == "")
		{
			alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
			return;
		}
		fm.WorkFlowFlag.value = ActivityID;			//�������
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
		approvefalg="2";
	}
	else if (wFlag ==3)
	{
		//09-06-09 ����Ͷ�����˹�ϵΪ����ʱͶ�����˿ͻ����Ƿ�һ�µ�У��
		var tContNo = fm.PrtNo.value;
		var tSplitPrtNo = tContNo.substring(0,4);
		if(tSplitPrtNo=="8611"||tSplitPrtNo=="8621"||tSplitPrtNo=="8616"||tSplitPrtNo=="8615"){
			if(fm.RelationToInsured.value == "00" ){
				  if(fm.AppntNo.value!=fm.InsuredNo.value){
					  alert("Ͷ����"+fm.AppntNo.value+" �뱻����"+fm.InsuredNo.value+"��Ϣ��һ�£���ȷ��Ͷ�����뱻���˹�ϵ��");
					  return false;
				  }
			}
		}
		if(document.all('ProposalContNo').value == "")
		{
		alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
		return;
		}
		fm.WorkFlowFlag.value = ActivityID;				//�����޸����
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else if(wFlag == 4)
	{
		if(document.all('ProposalContNo').value == "")
		{
		alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
		return;
		}
		fm.WorkFlowFlag.value = ActivityID;				//�����޸�
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else{
		return;
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	lockScreen('lkscreen');  
	fm.action = "./InputConfirm.jsp";
	document.getElementById("fm").submit(); //�ύ
}

/*********************************************************************
 *  �Ѻ�ͬ��Ϣ�����ڴ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function putCont()
{
	delContVar();
	addIntoCont();
}

function checkOtherQuest(){
   if(ActivityID=='0000001002')
   {
   	
				var sqlid4="ContInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
    mySql4.addSubPara(prtNo);//ָ������Ĳ���
    mySql4.addSubPara(operator);//ָ������Ĳ���
	var tSql=mySql4.getString();
	
   	//var tSql ="select missionprop9 from lwmission where activityid = '0000001002' and missionprop1 ='"+prtNo+"' and defaultoperator='"+operator+"' and  processid = '0000000003' ";
  	 var tOtherQuestFlag = easyExecSql(tSql);
   	if(tOtherQuestFlag !=""&& tOtherQuestFlag!=null)
  	 {
   	   if(tOtherQuestFlag !="1")
   	   {
  	       //��δ�ظ��������
  	       alert("����δ�ظ�����������뽫���еĵ�������ظ����ٽ���[������޸����]������");
 	        return false;
  	    }
 	  }else{
  	      alert("û���ܹ���ѯ���Ƿ���δ�ظ������������ȷ�Ϻ��ٽ���[������޸����]����");
 	      if(confirm("�Ƿ�ȷ�ϴ˲�����")==false)
  	      {
  	        return false;
 	      }
	    }
   }
   return true;
}

/*********************************************************************
 *  �Ѻ�ͬ��Ϣ����ӵ�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addIntoCont()
{
	//try { mSwitch.addVar( "intoPolFlag", "", "GROUPPOL" ); } catch(ex) { };
	// body��Ϣ
	try { mSwitch.addVar( "BODY", "", window.document.body.innerHTML ); } catch(ex) { };
	// ������Ϣ
	//��"./AutoCreatLDGrpInit.jsp"�Զ�����
    try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
    try{mSwitch.addVar('ProposalContNo','',fm.ProposalContNo.value);}catch(ex){};
    try{mSwitch.addVar('PrtNo','',fm.PrtNo.value);}catch(ex){};
    try{mSwitch.addVar('GrpContNo','',fm.GrpContNo.value);}catch(ex){};
    try{mSwitch.addVar('ContType','',fm.ContType.value);}catch(ex){};
    try{mSwitch.addVar('FamilyType','',fm.FamilyType.value);}catch(ex){};
    try{mSwitch.addVar('PolType','',fm.PolType.value);}catch(ex){};
    try{mSwitch.addVar('CardFlag','',fm.CardFlag.value);}catch(ex){};
    try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
    try{mSwitch.addVar('AgentCom','',fm.AgentCom.value);}catch(ex){};
    try{mSwitch.addVar('AgentCode','',fm.AgentCode.value);}catch(ex){};
    try{mSwitch.addVar('AgentGroup','',fm.AgentGroup.value);}catch(ex){};
    try{mSwitch.addVar('AgentCode1','',fm.AgentCode1.value);}catch(ex){};
    try{mSwitch.addVar('AgentType','',fm.AgentType.value);}catch(ex){};
    try{mSwitch.addVar('SaleChnl','',fm.SaleChnl.value);}catch(ex){};
    try{mSwitch.addVar('Handler','',fm.Handler.value);}catch(ex){};
    try{mSwitch.addVar('Password','',fm.Password.value);}catch(ex){};
    try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
    try{mSwitch.addVar('AppntName','',fm.AppntName.value);}catch(ex){};
    try{mSwitch.addVar('AppntSex','',fm.AppntSex.value);}catch(ex){};
    try{mSwitch.addVar('AppntBirthday','',fm.AppntBirthday.value);}catch(ex){};
    try{mSwitch.addVar('AppntIDType','',fm.AppntIDType.value);}catch(ex){};
    try{mSwitch.addVar('AppntIDNo','',fm.AppntIDNo.value);}catch(ex){};
    try{mSwitch.addVar('AppIDPeriodOfValidity','',fm.AppIDPeriodOfValidity.value);}catch(ex){};
    try{mSwitch.addVar('AppSocialInsuFlag','',fm.AppSocialInsuFlag.value);}catch(ex){};
    try{mSwitch.addVar('AppntLanguage','',fm.AppntLanguage.value);}catch(ex){};

    try{mSwitch.addVar('InsuredNo','',fm.InsuredNo.value);}catch(ex){};
    try{mSwitch.addVar('InsuredName','',fm.InsuredName.value);}catch(ex){};
    try{mSwitch.addVar('InsuredSex','',fm.InsuredSex.value);}catch(ex){};
    try{mSwitch.addVar('InsuredBirthday','',fm.InsuredBirthday.value);}catch(ex){};
    try{mSwitch.addVar('InsuredIDType','',fm.InsuredIDType.value);}catch(ex){};
    try{mSwitch.addVar('InsuredIDNo','',fm.InsuredIDNo.value);}catch(ex){};
    try{mSwitch.addVar('PayIntv','',fm.PayIntv.value);}catch(ex){};
    try{mSwitch.addVar('PayMode','',fm.PayMode.value);}catch(ex){};
    try{mSwitch.addVar('PayLocation','',fm.PayLocation.value);}catch(ex){};
    try{mSwitch.addVar('DisputedFlag','',fm.DisputedFlag.value);}catch(ex){};
    try{mSwitch.addVar('OutPayFlag','',fm.OutPayFlag.value);}catch(ex){};
    try{mSwitch.addVar('GetPolMode','',fm.GetPolMode.value);}catch(ex){};
    try{mSwitch.addVar('SignCom','',fm.SignCom.value);}catch(ex){};
    try{mSwitch.addVar('SignDate','',fm.SignDate.value);}catch(ex){};
    try{mSwitch.addVar('SignTime','',fm.SignTime.value);}catch(ex){};
    try{mSwitch.addVar('ConsignNo','',fm.ConsignNo.value);}catch(ex){};
    try{mSwitch.addVar('BankCode','',fm.BankCode.value);}catch(ex){};
    try{mSwitch.addVar('BankAccNo','',fm.BankAccNo.value);}catch(ex){};
    try{mSwitch.addVar('AccName','',fm.AccName.value);}catch(ex){};
    try{mSwitch.addVar('PrintCount','',fm.PrintCount.value);}catch(ex){};
    try{mSwitch.addVar('LostTimes','',fm.LostTimes.value);}catch(ex){};
    try{mSwitch.addVar('Lang','',fm.Lang.value);}catch(ex){};
    try{mSwitch.addVar('Currency','',fm.Currency.value);}catch(ex){};
    try{mSwitch.addVar('Remark','',fm.Remark.value);}catch(ex){};
    try{mSwitch.addVar('Peoples','',fm.Peoples.value);}catch(ex){};
    try{mSwitch.addVar('Mult','',fm.Mult.value);}catch(ex){};
    try{mSwitch.addVar('Prem','',fm.Prem.value);}catch(ex){};
    try{mSwitch.addVar('Amnt','',fm.Amnt.value);}catch(ex){};
    try{mSwitch.addVar('SumPrem','',fm.SumPrem.value);}catch(ex){};
    try{mSwitch.addVar('Dif','',fm.Dif.value);}catch(ex){};
    try{mSwitch.addVar('PaytoDate','',fm.PaytoDate.value);}catch(ex){};
    try{mSwitch.addVar('FirstPayDate','',fm.FirstPayDate.value);}catch(ex){};
    try{mSwitch.addVar('CValiDate','',fm.CValiDate.value);}catch(ex){};
    try{mSwitch.addVar('InputOperator','',fm.InputOperator.value);}catch(ex){};
    try{mSwitch.addVar('InputDate','',fm.InputDate.value);}catch(ex){};
    try{mSwitch.addVar('InputTime','',fm.InputTime.value);}catch(ex){};
    try{mSwitch.addVar('ApproveFlag','',fm.ApproveFlag.value);}catch(ex){};
    try{mSwitch.addVar('ApproveCode','',fm.ApproveCode.value);}catch(ex){};
    try{mSwitch.addVar('ApproveDate','',fm.ApproveDate.value);}catch(ex){};
    try{mSwitch.addVar('ApproveTime','',fm.ApproveTime.value);}catch(ex){};
    try{mSwitch.addVar('UWFlag','',fm.UWFlag.value);}catch(ex){};
    try{mSwitch.addVar('UWOperator','',fm.UWOperator.value);}catch(ex){};
    try{mSwitch.addVar('UWDate','',fm.UWDate.value);}catch(ex){};
    try{mSwitch.addVar('UWTime','',fm.UWTime.value);}catch(ex){};
    try{mSwitch.addVar('AppFlag','',fm.AppFlag.value);}catch(ex){};
    try{mSwitch.addVar('PolApplyDate','',fm.PolApplyDate.value);}catch(ex){};
    try{mSwitch.addVar('GetPolDate','',fm.GetPolDate.value);}catch(ex){};
    try{mSwitch.addVar('GetPolTime','',fm.GetPolTime.value);}catch(ex){};
    try{mSwitch.addVar('CustomGetPolDate','',fm.CustomGetPolDate.value);}catch(ex){};
    try{mSwitch.addVar('State','',fm.State.value);}catch(ex){};
    try{mSwitch.addVar('Operator','',fm.Operator.value);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',fm.MakeDate.value);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',fm.MakeTime.value);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',fm.ModifyDate.value);}catch(ex){};
	try{mSwitch.addVar('ModifyTime','',fm.ModifyTime.value);}catch(ex){};
	try{mSwitch.addVar('IDPeriodOfValidity','',fm.IDPeriodOfValidity.value);}catch(ex){};
	try{mSwitch.addVar('SocialInsuFlag','',fm.SocialInsuFlag.value);}catch(ex){};
	//�µ����ݴ���
	try { mSwitch.addVar('AppntNo','',fm.AppntNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntName','',fm.AppntName.value); } catch(ex) { };
	try { mSwitch.addVar('AppntSex','',fm.AppntSex.value); } catch(ex) { };
	try { mSwitch.addVar('AppntBirthday','',fm.AppntBirthday.value); } catch(ex) { };
	try { mSwitch.addVar('AppntIDType','',fm.AppntIDType.value); } catch(ex) { };
	try { mSwitch.addVar('AppntIDNo','',fm.AppntIDNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPassword','',fm.AppntPassword.value); } catch(ex) { };
	try { mSwitch.addVar('AppntNativePlace','',fm.AppntNativePlace.value); } catch(ex) { };
	try { mSwitch.addVar('AppntNationality','',fm.AppntNationality.value); } catch(ex) { };
	try { mSwitch.addVar('AddressNo','',fm.AppntAddressNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPlace','',fm.AppntPlace.value); } catch(ex) { };
	try { mSwitch.addVar('AppntMarriage','',fm.AppntMarriage.value); } catch(ex) { };
	try { mSwitch.addVar('AppntMarriageDate','',fm.AppntMarriageDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntHealth','',fm.AppntHealth.value); } catch(ex) { };
	//try { mSwitch.addVar('AppntStature','',fm.AppntStature.value); } catch(ex) { };
	//try { mSwitch.addVar('AppntAvoirdupois','',fm.AppntAvoirdupois.value); } catch(ex) { };
	try { mSwitch.addVar('AppntDegree','',fm.AppntDegree.value); } catch(ex) { };
	try { mSwitch.addVar('AppntCreditGrade','',fm.AppntCreditGrade.value); } catch(ex) { };
	try { mSwitch.addVar('AppntOthIDType','',fm.AppntOthIDType.value); } catch(ex) { };
	try { mSwitch.addVar('AppntOthIDNo','',fm.AppntOthIDNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntICNo','',fm.AppntICNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntGrpNo','',fm.AppntGrpNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntJoinCompanyDate','',fm.AppntJoinCompanyDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntStartWorkDate','',fm.AppntStartWorkDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPosition','',fm.AppntPosition.value); } catch(ex) { };
	try { mSwitch.addVar('AppntSalary','',fm.AppntSalary.value); } catch(ex) { };
	try { mSwitch.addVar('AppntOccupationType','',fm.AppntOccupationType.value); } catch(ex) { };
	try { mSwitch.addVar('AppntOccupationCode','',fm.AppntOccupationCode.value); } catch(ex) { };
	try { mSwitch.addVar('AppntWorkType','',fm.AppntWorkType.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPluralityType','',fm.AppntPluralityType.value); } catch(ex) { };
	try { mSwitch.addVar('AppntDeathDate','',fm.AppntDeathDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntSmokeFlag','',fm.AppntSmokeFlag.value); } catch(ex) { };
	try { mSwitch.addVar('AppntBlacklistFlag','',fm.AppntBlacklistFlag.value); } catch(ex) { };
	try { mSwitch.addVar('AppntProterty','',fm.AppntProterty.value); } catch(ex) { };
	try { mSwitch.addVar('AppntRemark','',fm.AppntRemark.value); } catch(ex) { };
	try { mSwitch.addVar('AppntState','',fm.AppntState.value); } catch(ex) { };
	try { mSwitch.addVar('AppntOperator','',fm.AppntOperator.value); } catch(ex) { };
	try { mSwitch.addVar('AppntMakeDate','',fm.AppntMakeDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntMakeTime','',fm.AppntMakeTime.value); } catch(ex) { };
	try { mSwitch.addVar('AppntModifyDate','',fm.AppntModifyDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntModifyTime','',fm.AppntModifyTime.value); } catch(ex) { };
	try { mSwitch.addVar('AppntHomeAddress','',fm.AppntHomeAddress.value); } catch(ex) { };
	try { mSwitch.addVar('AppntHomeZipCode','',fm.AppntHomeZipCode.value); } catch(ex) { };
	try { mSwitch.addVar('AppntHomePhone','',fm.AppntHomePhone.value); } catch(ex) { };
	try { mSwitch.addVar('AppntHomeFax','',fm.AppntHomeFax.value); } catch(ex) { };
	try { mSwitch.addVar('AppntGrpName','',fm.AppntGrpName.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPhone','',fm.AppntPhone.value); } catch(ex) { };
	try { mSwitch.addVar('CompanyAddress','',fm.CompanyAddress.value); } catch(ex) { };
	try { mSwitch.addVar('AppntGrpZipCode','',fm.AppntGrpZipCode.value); } catch(ex) { };
	try { mSwitch.addVar('AppntGrpFax','',fm.AppntGrpFax.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPostalAddress','',fm.AppntPostalAddress.value); } catch(ex) { };
	try { mSwitch.addVar('AppntZipCode','',fm.AppntZipCode.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPhone','',fm.AppntPhone.value); } catch(ex) { };
	try { mSwitch.addVar('AppntMobile','',fm.AppntMobile.value); } catch(ex) { };
	try { mSwitch.addVar('AppntFax','',fm.AppntFax.value); } catch(ex) { };
	try { mSwitch.addVar('AppntEMail','',fm.AppntEMail.value); } catch(ex) { };
	try { mSwitch.addVar('AppntBankAccNo','',document.all('AppntBankAccNo').value); } catch(ex) { };
	try { mSwitch.addVar('AppntBankCode','',document.all('AppntBankCode').value); } catch(ex) { };
	try { mSwitch.addVar('AppntAccName','',document.all('AppntAccName').value); } catch(ex) { };
	try { mSwitch.addVar('AutoPayFlag','',fm.AutoPayFlag.value); } catch(ex) { };
	try { mSwitch.addVar('RnewFlag','',fm.RnewFlag.value); } catch(ex) { };
	try { mSwitch.addVar('GetPolMode','',fm.GetPolMode.value); } catch(ex) { };
	try { mSwitch.addVar('AppSocialInsuFlag','',fm.AppSocialInsuFlag.value); } catch(ex) { };
	try { mSwitch.addVar('AppntLanguage','',fm.AppntLanguage.value); } catch(ex) { };
	try { mSwitch.addVar('AppIDPeriodOfValidity','',fm.AppIDPeriodOfValidity.value); } catch(ex) { };
}

/*********************************************************************
 *  �Ѽ�����Ϣ�ӱ�����ɾ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function delContVar()
{
	try { mSwitch.deleteVar( "intoPolFlag" ); } catch(ex) { };
	// body��Ϣ
	try { mSwitch.deleteVar( "BODY" ); } catch(ex) { };
	// ������Ϣ
	//��"./AutoCreatLDGrpInit.jsp"�Զ�����
   try { mSwitch.deleteVar('ContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('ProposalContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('PrtNo'); } catch(ex) { };
   try { mSwitch.deleteVar('GrpContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('ContType'); } catch(ex) { };
   try { mSwitch.deleteVar('FamilyType'); } catch(ex) { };
   try { mSwitch.deleteVar('PolType'); } catch(ex) { };
   try { mSwitch.deleteVar('CardFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('ManageCom'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCom'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCode'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentGroup'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCode1'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentType'); } catch(ex) { };
   try { mSwitch.deleteVar('SaleChnl'); } catch(ex) { };
   try { mSwitch.deleteVar('Handler'); } catch(ex) { };
   try { mSwitch.deleteVar('Password'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntNo'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntName'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntSex'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntBirthday'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntIDType'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntIDNo'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredNo'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredNam'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredSex'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredBirthday'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredIDType'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredIDNo'); } catch(ex) { };
   try { mSwitch.deleteVar('PayIntv'); } catch(ex) { };
   try { mSwitch.deleteVar('PayMode'); } catch(ex) { };
   try { mSwitch.deleteVar('PayLocation'); } catch(ex) { };
   try { mSwitch.deleteVar('DisputedFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('OutPayFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolMode'); } catch(ex) { };
   try { mSwitch.deleteVar('SignCom'); } catch(ex) { };
   try { mSwitch.deleteVar('SignDate'); } catch(ex) { };
   try { mSwitch.deleteVar('SignTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ConsignNo'); } catch(ex) { };
   try { mSwitch.deleteVar('BankCode'); } catch(ex) { };
   try { mSwitch.deleteVar('BankAccNo'); } catch(ex) { };
   try { mSwitch.deleteVar('AccName'); } catch(ex) { };
   try { mSwitch.deleteVar('PrintCount'); } catch(ex) { };
   try { mSwitch.deleteVar('LostTimes'); } catch(ex) { };
   try { mSwitch.deleteVar('Lang'); } catch(ex) { };
   try { mSwitch.deleteVar('Currency'); } catch(ex) { };
   try { mSwitch.deleteVar('Remark'); } catch(ex) { };
   try { mSwitch.deleteVar('Peoples'); } catch(ex) { };
   try { mSwitch.deleteVar('Mult'); } catch(ex) { };
   try { mSwitch.deleteVar('Prem'); } catch(ex) { };
   try { mSwitch.deleteVar('Amnt'); } catch(ex) { };
   try { mSwitch.deleteVar('SumPrem'); } catch(ex) { };
   try { mSwitch.deleteVar('Dif'); } catch(ex) { };
   try { mSwitch.deleteVar('PaytoDate'); } catch(ex) { };
   try { mSwitch.deleteVar('FirstPayDate'); } catch(ex) { };
   try { mSwitch.deleteVar('CValiDate'); } catch(ex) { };
   try { mSwitch.deleteVar('InputOperator'); } catch(ex) { };
   try { mSwitch.deleteVar('InputDate'); } catch(ex) { };
   try { mSwitch.deleteVar('InputTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveCode'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveDate'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveTime'); } catch(ex) { };
   try { mSwitch.deleteVar('UWFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('UWOperator'); } catch(ex) { };
   try { mSwitch.deleteVar('UWDate'); } catch(ex) { };
   try { mSwitch.deleteVar('UWTime'); } catch(ex) { };
   try { mSwitch.deleteVar('AppFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('PolApplyDate'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolDate'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolTime'); } catch(ex) { };
   try { mSwitch.deleteVar('CustomGetPolDate'); } catch(ex) { };
   try { mSwitch.deleteVar('State'); } catch(ex) { };
   try { mSwitch.deleteVar('Operator'); } catch(ex) { };
   try { mSwitch.deleteVar('MakeDate'); } catch(ex) { };
   try { mSwitch.deleteVar('MakeTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ModifyDate'); } catch(ex) { };
   try { mSwitch.deleteVar('ModifyTime'); } catch(ex) { };
   try { mSwitch.deleteVar('SocialInsuFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('IDPeriodOfValidity'); } catch(ex) { };

    //�µ�ɾ�����ݴ���
	try { mSwitch.deleteVar  ('AppntNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntName'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntSex'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBirthday'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntIDType'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntIDNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPassword'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntNativePlace'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntNationality'); } catch(ex) { };
	try { mSwitch.deleteVar('AddressNo');}catch(ex){};
	try { mSwitch.deleteVar  ('AppntPlace'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntMarriage'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntMarriageDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntHealth'); } catch(ex) { };
	//try { mSwitch.deleteVar  ('AppntStature'); } catch(ex) { };
	//try { mSwitch.deleteVar  ('AppntAvoirdupois'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntDegree'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntCreditGrade'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntOthIDType'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntOthIDNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntICNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntJoinCompanyDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntStartWorkDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPosition') } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntSalary'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntOccupationType'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntOccupationCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntWorkType'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPluralityType');} catch(ex) { };
	try { mSwitch.deleteVar  ('AppntDeathDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntSmokeFlag'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBlacklistFlag'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntProterty'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntRemark'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntState'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntOperator'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntMakeDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntMakeTime'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntModifyDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntModifyTime'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntHomeAddress'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntHomeZipCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntHomePhone'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntHomeFax'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPostalAddress'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntZipCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPhone'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntFax'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntMobile'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntEMail'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpName'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPhone'); } catch(ex) { };
	try { mSwitch.deleteVar  ('CompanyAddress'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpZipCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpFax'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBankAccNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBankCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntAccName'); } catch(ex) { };
	try { mSwitch.deleteVar  ('GetPolMode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('RnewFlag'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AutoPayFlag'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppIDPeriodOfValidity'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppSocialInsuFlag'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntLanguage'); } catch(ex) { };
}


/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult ) 
{
	if( arrQueryResult != null ) 
	{
		arrResult = arrQueryResult;
		if( mOperate == 1 )
		{		// ��ѯͶ����
			document.all( 'ContNo' ).value = arrQueryResult[0][0];
			
							var sqlid5="ContInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
    mySql5.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���
	var tSql5=mySql5.getString();
	
			arrResult = easyExecSql(tSql5, 1, 0);
			//arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);
			
			if (arrResult == null) 
			{
				alert("δ�鵽Ͷ������Ϣ");
			} 
			else 
			{
				displayLCContPol(arrResult[0]);
			}
		}
		
		if( mOperate == 2 )	
		{		// Ͷ������Ϣ
			//alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
			
	var sqlid6="ContInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
    mySql6.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���
	var tSql6=mySql6.getString();
	
			arrResult = easyExecSql(tSql6, 1, 0);
			//arrResult = easyExecSql("select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			if (arrResult == null) 
			{
				alert("δ�鵽Ͷ������Ϣ");
			} 
			else 
			{
				displayAppnt(arrResult[0]);
			}
		}
	}
	mOperate = 0;		// �ָ���̬
	showCodeName();
}
/*********************************************************************
 *  Ͷ������Ϣ��ʼ����������loadFlag��־��Ϊ��֧
 *  ����  ��  Ͷ����ӡˢ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function detailInit(PrtNo,ContNo)
{
  var tContNo;
  try
  {
    if(PrtNo==null) return;
    if((ContNo == null) || (ContNo == "null") || (ContNo == "")) 
        tContNo = PrtNo;
    else
        tContNo = ContNo;
    //��ѯ�����˺�
	
		var sqlid7="ContInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
    mySql7.addSubPara(tContNo);//ָ������Ĳ���
	var accSQL=mySql7.getString();
	
//    var accSQL = "select a.BankCode,a.AccName,a.BankAccNo from LJTempFeeClass a,LJTempFee b "
//               + "where (a.TempFeeNo)=(b.TempFeeNo) and a.PayMode='7' and b.TempFeeType='1' "
//               + "and b.OtherNo='"+tContNo+"'";
    arrResult = easyExecSql(accSQL,1,0);
    if(arrResult==null)
    {}
    else
    {
      displayFirstAccount();
      //������ڽ��ѷ�ʽΪ����ת�ˣ�����ͬ��Ϊ����ת��
      try{document.all('PayMode').value="7"; }catch(ex){};
      try{document.all('PayModeName').value="���д��ۣ��Ʒ��̣�"; }catch(ex){};
      try{document.all('SecPayMode').value="0"; }catch(ex){};
      try{document.all('SecPayModeName').value="����ת��"; }catch(ex){};
    }

//    arrResult=easyExecSql("select * from LCCont where PrtNo='"+PrtNo+"'",1,0);
    
	var sqlid87="ContInputSql87";
	var mySql87=new SqlClass();
	mySql87.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql87.setSqlId(sqlid87);//ָ��ʹ�õ�Sql��id
	mySql87.addSubPara(PrtNo);//ָ������Ĳ���
	var strSql87=mySql87.getString();
	
	arrResult=easyExecSql(strSql87,1,0);
    
    if(arrResult==null)
    {
      //alert("δ�õ�Ͷ������Ϣ");
      return;
    }
    else
    {
      displayLCCont();       //��ʾͶ������ϸ����
    }
    //alert("arrResult[0][19] is : "+arrResult[0][19]);
	
			var sqlid8="ContInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
    mySql8.addSubPara(arrResult[0][19] );//ָ������Ĳ���
	var accSQL8=mySql8.getString();
	
	arrResult = easyExecSql(accSQL8, 1, 0);
    //arrResult = easyExecSql("select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + arrResult[0][19] + "'", 1, 0);
    if (arrResult == null) 
    {
      //alert("δ�鵽Ͷ���˿ͻ�����Ϣ");
    }
    else
    {
      //��ʾͶ������Ϣ
      displayAppnt(arrResult[0]);
    }
	
	var sqlid9="ContInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
    mySql9.addSubPara(PrtNo);//ָ������Ĳ���
	var accSQL9=mySql9.getString();
	
	 arrResult=easyExecSql(accSQL9,1,0);
   // arrResult=easyExecSql("select * from LCAppnt where PrtNo='"+PrtNo+"'",1,0);
    if(arrResult==null)
    {
      //alert("δ�õ�Ͷ���˱�������Ϣ");
      return;
    }
    else
    {
      displayContAppnt();       //��ʾͶ���˵���ϸ����
    }
    getAge();
       
    var tContNo = arrResult[0][1];
    var tCustomerNo = arrResult[0][3];		// �õ�Ͷ���˿ͻ���
    var tAddressNo = arrResult[0][9]; 		// �õ�Ͷ���˵�ַ��
    fm.AppntAddressNo.value=tAddressNo;
    //��ѯ����ʾͶ���˵�ַ��ϸ����
    getdetailaddress();
    //��ѯͶ���˸�֪��Ϣ
	
		var sqlid10="ContInputSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
    mySql10.addSubPara(tCustomerNo);//ָ������Ĳ���
    mySql10.addSubPara(tContNo);//ָ������Ĳ���
	var tSQL=mySql10.getString();
	
//    var tSQL = "select impartver,impartcode,impartcontent,impartparammodle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'"
//    			+"order by impartver,impartcode";
				
  /*           + " and (impartver<>'01' and impartcode<>'001') and (impartver<>'02' and impartcode <> '000')";
    var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
    var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
    var strSQL2="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='02' and impartcode='000' and ImpartParamNo = '1'";
    var strSQL3="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='02' and impartcode='000' and ImpartParamNo = '2'";
    arrResult = easyExecSql(strSQL0,1,0);
    arrResult1 = easyExecSql(strSQL1,1,0);
    arrResult2 = easyExecSql(strSQL2,1,0);
    arrResult3 = easyExecSql(strSQL3,1,0);
    
	try{document.all('Income0').value= arrResult[0][0];}catch(ex){};
	try{document.all('IncomeWay0').value= arrResult1[0][0];}catch(ex){};
	try{document.all('AppntStature').value= arrResult2[0][0];}catch(ex){};
	try{document.all('AppntAvoirdupois').value= arrResult3[0][0];}catch(ex){};*/
   
   // var strSQL1="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'  and ((impartver ='01' and impartcode<>'001') or impartver ='02' and  impartcode<>'000') ";
//    turnPage.strQueryResult = easyQueryVer3(strSQL1, 1, 0, 1);
//
//    //�ж��Ƿ��ѯ�ɹ�,�ɹ�����ʾ��֪��Ϣ
//    if (turnPage.strQueryResult)
//    {
//      //��ѯ�ɹ������ַ��������ض�ά����
//      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//      //���ó�ʼ������MULTILINE����
//      turnPage.pageDisplayGrid = ImpartGrid;
//      //����SQL���
//      turnPage.strQuerySql = strSQL1;
//      //���ò�ѯ��ʼλ��
//      turnPage.pageIndex = 0;
//      //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
//      arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
//      //����MULTILINE������ʾ��ѯ���
//      displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
//    }
//    else
//    {
//      initImpartGrid();
//    }
      //initImpartGrid();
      turnPage.queryModal(tSQL,ImpartGrid,null,null,100);
     //alert(strSQL1);
    //����з�֧
    if(loadFlag=="5"||loadFlag=="25")
    {
    }
    
	/***********************************/
	
			var sqlid11="ContInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
    mySql11.addSubPara(tContNo);//ָ������Ĳ���
	var sAgentsql=mySql11.getString();
	
//	var sAgentsql="select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr " 
//		+ "from lccont a,labranchgroup b,lacommisiondetail c,laagent d where 1=1 "
//		+" and (a.contno)='" + tContNo + "' "
//		+" and (b.agentgroup)=(c.agentgroup) "
//		+" and (c.agentcode)=trim(a.agentcode) " 
//		+" and (d.agentcode)=trim(a.agentcode) " 
//		+" and (c.grpcontno)=(a.contno)";
    //��ѯ��ҵ��Ա��Ϣ
    arrResult=easyExecSql(sAgentsql,1,0);
    if(arrResult==null)
    {
    }
    else
    {
      displayMainAgent();       //��ʾ��ҵ��Ա����ϸ����
    }
 	displayAgent(); //��ʾ��ҵ��Ա����ϸ����
 	//��ѯҵ��Ա��Ϣ
 	//queryAgent();
	//��ѯҵ��Ա��֪
	
				var sqlid12="ContInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
    mySql12.addSubPara(tCustomerNo);//ָ������Ĳ���
     mySql12.addSubPara(tContNo);//ָ������Ĳ���
	var aSQL=mySql12.getString();
	
  	 //var aSQL="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'";
   	turnPage.queryModal(aSQL, AgentImpartGrid);
  }
  catch(ex)
  {}

}


function displayAgent()
{   
	if(fm.AgentCode.value!="")
	{
		var cAgentCode = fm.AgentCode.value;  //��������
		
	var sqlid13="ContInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
    mySql13.addSubPara(cAgentCode);//ָ������Ĳ���
	var strSQL=mySql13.getString();
		
//		var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//		+ "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
		//alert(strSQL);
		var arrResult = easyExecSql(strSQL);
		//alert(arrResult);
		if (arrResult != null) 
		{
			fm.AgentCode.value = arrResult[0][0];
			fm.BranchAttr.value = arrResult[0][10];
			fm.AgentGroup.value = arrResult[0][1];
			fm.AgentName.value = arrResult[0][3];
			fm.AgentManageCom.value = arrResult[0][2];
			showContCodeName();
			//alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
		}
		else
		{
			fm.AgentGroup.value="";
			alert("����Ϊ:["+document.all('AgentCode').value+"]��ҵ��Ա�����ڣ���ȷ��!");
		}
	}
	else
	{
	return;
	}
}




/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAccount()
{
	try{document.all('AppntBankAccNo').value= arrResult[0][24]; }catch(ex){};
	try{document.all('AppntBankCode').value= arrResult[0][23]; }catch(ex){};
	try{document.all('AppntAccName').value= arrResult[0][25]; }catch(ex){};

}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayCustomer()
{
	try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAddress()
{
	try{document.all('AppntAddressNo').value= arrResult[0][0];}catch(ex){};
	try{document.all('AppntPostalAddress').value= arrResult[0][1];}catch(ex){};
	try{document.all('AppntZipCode').value= arrResult[0][2];}catch(ex){};
	try{document.all('AppntPhone').value= arrResult[0][3];}catch(ex){};
	try{document.all('AppntMobile').value= arrResult[0][4];}catch(ex){};
	try{document.all('AppntEMail').value= arrResult[0][5];}catch(ex){};
	//try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
	try{document.all('AppntPhone').value= arrResult[0][6];}catch(ex){};
	try{document.all('CompanyAddress').value= arrResult[0][7];}catch(ex){};
	try{document.all('AppntGrpZipCode').value= arrResult[0][8];}catch(ex){};
	/*
	try{document.all('AppntPostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{document.all('AppntZipCode').value= arrResult[0][3]; }catch(ex){};
	try{document.all('AppntPhone').value= arrResult[0][4]; }catch(ex){};
	try{document.all('AppntMobile').value= arrResult[0][14]; }catch(ex){};
	try{document.all('AppntEMail').value= arrResult[0][16]; }catch(ex){};
	//try{document.all('AppntGrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('AppntGrpPhone').value= arrResult[0][12]; }catch(ex){};
	try{document.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{document.all('AppntGrpZipCode').value= arrResult[0][11]; }catch(ex){};
	*/
}

/*********************************************************************
 *  �Ѳ�ѯ���صĺ�ͬ�б�����������ʾ��ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayContAppnt()
{
  try{document.all('AppntGrpContNo').value= arrResult[0][0];}catch(ex){};
  try{document.all('AppntContNo').value= arrResult[0][1];}catch(ex){};
  try{document.all('AppntPrtNo').value= arrResult[0][2];}catch(ex){};
  try{document.all('AppntNo').value= arrResult[0][3];}catch(ex){};
  try{document.all('AppntGrade').value= arrResult[0][4];}catch(ex){};
  try{document.all('AppntName').value= arrResult[0][5];}catch(ex){};
  try{document.all('AppntSex').value= arrResult[0][6];}catch(ex){};
  try{document.all('AppntBirthday').value= arrResult[0][7];}catch(ex){};
  try{document.all('AppntType').value= arrResult[0][8];}catch(ex){};
  try{document.all('AppntAddressNo').value= arrResult[0][9]; }catch(ex){};
  try{document.all('AppntIDType').value= arrResult[0][10]; }catch(ex){};
  try{document.all('AppntIDNo').value= arrResult[0][11]; }catch(ex){};
  try{document.all('AppntNativePlace').value= arrResult[0][12];}catch(ex){};
  try{document.all('AppntNationality').value= arrResult[0][13];}catch(ex){};
  try{document.all('AppntPlace').value= arrResult[0][14];}catch(ex){};
  try{document.all('AppntMarriage').value= arrResult[0][15];}catch(ex){};
  try{document.all('AppntMarriageDate').value= arrResult[0][16];}catch(ex){};
  try{document.all('AppntHealth').value= arrResult[0][17];}catch(ex){};
 /* if(arrResult[0][18]!=null&arrResult[0][18]!=0)
  {
  	try{document.all('AppntStature').value= arrResult[0][18];}catch(ex){};
  }
  if(arrResult[0][19]!=null&arrResult[0][19]!=0)
  {
  	try{document.all('AppntAvoirdupois').value= arrResult[0][19];}catch(ex){};
  } */
  try{document.all('AppntDegree').value= arrResult[0][20];}catch(ex){};
  try{document.all('AppntCreditGrade').value= arrResult[0][21];}catch(ex){};
  //try{document.all('AppntBankCode').value= arrResult[0][22];}catch(ex){};
  //try{document.all('AppntBankAccNo').value= arrResult[0][23];}catch(ex){};
  //try{document.all('AppntAccName').value= arrResult[0][24]; }catch(ex){};
  try{document.all('AppntJoinCompanyDate').value= arrResult[0][25];}catch(ex){};
  try{document.all('AppntStartWorkDate').value= arrResult[0][26];}catch(ex){};
  try{document.all('AppntPosition').value= arrResult[0][27];}catch(ex){};
  try{document.all('AppntSalary').value= arrResult[0][28]; }catch(ex){};
  try{document.all('AppntOccupationType').value= arrResult[0][29];}catch(ex){};
  try{document.all('AppntOccupationCode').value= arrResult[0][30];}catch(ex){};
  try{document.all('AppntWorkType').value= arrResult[0][31]; }catch(ex){};
  try{document.all('AppntPluralityType').value= arrResult[0][32];}catch(ex){};
  try{document.all('AppntSmokeFlag').value= arrResult[0][33];}catch(ex){};
  try{document.all('AppntOperator').value= arrResult[0][34];}catch(ex){};
  try{document.all('AppntManageCom').value= arrResult[0][35];}catch(ex){};
  try{document.all('AppntMakeDate').value= arrResult[0][36];}catch(ex){};
  try{document.all('AppntMakeTime').value= arrResult[0][37]; }catch(ex){};
  try{document.all('AppntModifyDate').value= arrResult[0][38];}catch(ex){};
  try{document.all('AppntModifyTime').value= arrResult[0][39];}catch(ex){};
  try{document.all('RelationToInsured').value= arrResult[0][43];}catch(ex){};
  try{document.all('AppSocialInsuFlag').value= arrResult[0][44];}catch(ex){};
  try{document.all('AppIDPeriodOfValidity').value= arrResult[0][45];}catch(ex){};
}
/*********************************************************************
 *  �Ѳ�ѯ���ص�Ͷ����������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt()
{
	try{document.all('AppntNo').value= arrResult[0][0]; }catch(ex){};
	try{document.all('AppntName').value= arrResult[0][1]; }catch(ex){};
	try{document.all('AppntSex').value= arrResult[0][2]; }catch(ex){};
	try{document.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};
	try{document.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};
	try{document.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};
	try{document.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};
	try{document.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};
	try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
	try{document.all('AppntPlace').value= arrResult[0][9]; }catch(ex){};
	try{document.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
	try{document.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
	try{document.all('AppntHealth').value= arrResult[0][12];}catch(ex){};
	/*if(arrResult[0][13]!=null&&arrResult[0][13]!=0)
	{
		try{document.all('AppntStature').value= arrResult[0][13];}catch(ex){};
	}
	if(arrResult[0][14]!=null&&arrResult[0][14]!=0)
	{
		try{document.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};
	}*/
	try{document.all('AppntDegree').value= arrResult[0][15];}catch(ex){};
	try{document.all('AppntCreditGrade').value= arrResult[0][16];}catch(ex){};
	try{document.all('AppntOthIDType').value= arrResult[0][17];}catch(ex){};
	try{document.all('AppntOthIDNo').value= arrResult[0][18];}catch(ex){};
	try{document.all('AppntICNo').value= arrResult[0][19];}catch(ex){};
	try{document.all('AppntGrpNo').value= arrResult[0][20];}catch(ex){};
	try{document.all('AppntJoinCompanyDate').value= arrResult[0][21];}catch(ex){};
	try{document.all('AppntStartWorkDate').value= arrResult[0][22];}catch(ex){};
	try{document.all('AppntPosition').value= arrResult[0][23];}catch(ex){};
	try{document.all('AppntSalary').value= arrResult[0][24];}catch(ex){};
	try{document.all('AppntOccupationType').value= arrResult[0][25];}catch(ex){};
	try{document.all('AppntOccupationCode').value= arrResult[0][26];}catch(ex){};
	try{document.all('AppntWorkType').value= arrResult[0][27];}catch(ex){};
	try{document.all('AppntPluralityType').value= arrResult[0][28];}catch(ex){};
	try{document.all('AppntDeathDate').value= arrResult[0][29];}catch(ex){};
	try{document.all('AppntSmokeFlag').value= arrResult[0][30];}catch(ex){};
	try{document.all('AppntBlacklistFlag').value= arrResult[0][31];}catch(ex){};
	try{document.all('AppntProterty').value= arrResult[0][32];}catch(ex){};
	try{document.all('AppntRemark').value= arrResult[0][33];}catch(ex){};
	try{document.all('AppntState').value= arrResult[0][34];}catch(ex){};
	try{document.all('AppntVIPValue').value= arrResult[0][35];}catch(ex){};
	try{document.all('AppntOperator').value= arrResult[0][36];}catch(ex){};
	try{document.all('AppntMakeDate').value= arrResult[0][37];}catch(ex){};
	try{document.all('AppntMakeTime').value= arrResult[0][38];}catch(ex){};
	try{document.all('AppntModifyDate').value= arrResult[0][39];}catch(ex){};
	try{document.all('AppntModifyTime').value= arrResult[0][40];}catch(ex){};
	try{document.all('AppntGrpName').value= arrResult[0][41];}catch(ex){};
	try{document.all('AppntLicenseType').value= arrResult[0][43];}catch(ex){};	
	//alert(arrResult[0][44]);
	try{document.all('RelationToInsured').value= arrResult[0][44];}catch(ex){};	
	try{document.all('AppSocialInsuFlag').value= arrResult[0][46];}catch(ex){};	
	try{document.all('AppIDPeriodOfValidity').value= arrResult[0][47];}catch(ex){};	
	try{document.all('AppntLastName').value= arrResult[0][48];}catch(ex){};
	try{document.all('AppntFirstName').value= arrResult[0][49];}catch(ex){};
	try{document.all('AppntLastNameEn').value= arrResult[0][50];}catch(ex){};
	try{document.all('AppntFirstNameEn').value= arrResult[0][51];}catch(ex){};
	try{document.all('AppntLastNamePY').value= arrResult[0][53];}catch(ex){};
	try{document.all('AppntFirstNamePY').value= arrResult[0][54];}catch(ex){};
  try{document.all('AppntLanguage').value= arrResult[0][55];}catch(ex){};
	
	//˳�㽫Ͷ���˵�ַ��Ϣ�Ƚ��г�ʼ��
	try{document.all('AppntPostalAddress').value= "";}catch(ex){};
	try{document.all('AppntPostalAddress').value= "";}catch(ex){};
	try{document.all('AppntZipCode').value= "";}catch(ex){};
	try{document.all('AppntPhone').value= "";}catch(ex){};
	try{document.all('AppntFax').value= "";}catch(ex){};
	try{document.all('AppntMobile').value= "";}catch(ex){};
	try{document.all('AppntEMail').value= "";}catch(ex){};
	//try{document.all('AppntGrpName').value= "";}catch(ex){};
	try{document.all('AppntHomeAddress').value= "";}catch(ex){};
	try{document.all('AppntHomeZipCode').value= "";}catch(ex){};
	try{document.all('AppntHomePhone').value= "";}catch(ex){};
	try{document.all('AppntHomeFax').value= "";}catch(ex){};
	try{document.all('AppntPhone').value= "";}catch(ex){};
	try{document.all('CompanyAddress').value= "";}catch(ex){};
	try{document.all('AppntGrpZipCode').value= "";}catch(ex){};
	try{document.all('AppntGrpFax').value= "";}catch(ex){};
}
/**
 *Ͷ������ϸ������ʾ
 */
function displayLCCont() 
{ 
    try { document.all('GrpContNo').value = arrResult[0][0]; } catch(ex) { };
    try { document.all('ContNo').value = arrResult[0][1]; } catch(ex) { };
    try { document.all('ProposalContNo').value = arrResult[0][2]; } catch(ex) { };
    try { document.all('PrtNo').value = arrResult[0][3]; } catch(ex) { };
    try { document.all('ContType').value = arrResult[0][4]; } catch(ex) { };
    try { document.all('FamilyType').value = arrResult[0][5]; } catch(ex) { };
    try { document.all('FamilyID').value = arrResult[0][6]; } catch(ex) { };
    try { document.all('PolType ').value = arrResult[0][7]; } catch(ex) { };
    try { document.all('CardFlag').value = arrResult[0][8]; } catch(ex) { };
    try { document.all('ManageCom').value = arrResult[0][9]; } catch(ex) { };
    try { document.all('ExecuteCom ').value = arrResult[0][10]; } catch(ex) { };
    try { document.all('AgentCom').value = arrResult[0][11]; } catch(ex) { };
    //�����������
    try {
     if(document.all('AgentCom').value!=""){
    	var tAgentCode = document.all('AgentCom').value;
		var sqlid14="ContInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(tAgentCode);//ָ������Ĳ���
		var sql=mySql14.getString();
		
        //var sql ="select  Name from LACom where agentcom ='"+document.all('AgentCom').value+"'";
        document.all('InputAgentComName').value =easyExecSql(sql);
     }
    } catch(ex) { };
    //showOneCodeName('agentcom','AgentCom','InputAgentComName');
    try { document.all('AgentCode').value = arrResult[0][12]; } catch(ex) { };
    //try { document.all('AgentGroup').value = arrResult[0][13]; } catch(ex) { };
    try { document.all('AgentCode1 ').value = arrResult[0][14]; } catch(ex) { };
    try { document.all('AgentType').value = arrResult[0][15]; } catch(ex) { };
    try { document.all('SaleChnl').value = arrResult[0][16]; } catch(ex) { };
    try { document.all('Handler').value = arrResult[0][17]; } catch(ex) { };
    try { document.all('Password').value = arrResult[0][18]; } catch(ex) { };
    try { document.all('AppntNo').value = arrResult[0][19]; } catch(ex) { };
    try { document.all('AppntName').value = arrResult[0][20]; } catch(ex) { };
    try { document.all('AppntSex').value = arrResult[0][21]; } catch(ex) { };
    try { document.all('AppntBirthday ').value = arrResult[0][22]; } catch(ex) { };
    try { document.all('AppntIDType').value = arrResult[0][23]; } catch(ex) { };
    try { document.all('AppntIDNo').value = arrResult[0][24]; } catch(ex) { };
    try { document.all('InsuredNo').value = arrResult[0][25]; } catch(ex) { };
    try { document.all('InsuredName').value = arrResult[0][26]; } catch(ex) { };
    try { document.all('InsuredSex').value = arrResult[0][27]; } catch(ex) { };
    try { document.all('InsuredBirthday').value = arrResult[0][28]; } catch(ex) { };
    try { document.all('InsuredIDType ').value = arrResult[0][29]; } catch(ex) { };
    try { document.all('InsuredIDNo').value = arrResult[0][30]; } catch(ex) { };
    try { document.all('PayIntv').value = arrResult[0][31]; } catch(ex) { };
    try { document.all('SecPayMode').value = arrResult[0][33]; } catch(ex) { }; 
    try { document.all('DisputedFlag').value = arrResult[0][34]; } catch(ex) { };
    try { document.all('OutPayFlag').value = arrResult[0][35]; } catch(ex) { };
    try { document.all('GetPolMode').value = arrResult[0][36]; } catch(ex) { };
    try { document.all('SignCom').value = arrResult[0][37]; } catch(ex) { };
    try { document.all('SignDate').value = arrResult[0][38]; } catch(ex) { };
    try { document.all('SignTime').value = arrResult[0][39]; } catch(ex) { };
    try { document.all('ConsignNo').value = arrResult[0][40]; } catch(ex) { };
    try { document.all('SecAppntBankCode').value = arrResult[0][41]; } catch(ex) { };
    try { document.all('SecAppntBankAccNo').value = arrResult[0][42]; } catch(ex) { };
    try { document.all('SecAppntAccName').value = arrResult[0][43]; } catch(ex) { };
    try { document.all('PrintCount').value = arrResult[0][44]; } catch(ex) { };
    try { document.all('LostTimes').value = arrResult[0][45]; } catch(ex) { };
    try { document.all('Lang').value = arrResult[0][46]; } catch(ex) { };
    try { document.all('Currency').value = arrResult[0][47]; } catch(ex) { };
    try { document.all('Remark').value = arrResult[0][48]; } catch(ex) { };
    try { document.all('Peoples ').value = arrResult[0][49]; } catch(ex) { };
    try { document.all('Mult').value = arrResult[0][50]; } catch(ex) { };
    try { document.all('Prem').value = arrResult[0][51]; } catch(ex) { };
    try { document.all('Amnt').value = arrResult[0][52]; } catch(ex) { };
    try { document.all('SumPrem').value = arrResult[0][53]; } catch(ex) { };
    try { document.all('Dif').value = arrResult[0][54]; } catch(ex) { };
    try { document.all('PaytoDate').value = arrResult[0][55]; } catch(ex) { };
    try { document.all('FirstPayDate').value = arrResult[0][56]; } catch(ex) { };
    try { document.all('CValiDate').value = arrResult[0][57]; } catch(ex) { };
    try { document.all('InputOperator ').value = arrResult[0][58]; } catch(ex) { };
    try { document.all('InputDate').value = arrResult[0][59]; } catch(ex) { };
    try { document.all('InputTime').value = arrResult[0][60]; } catch(ex) { };
    try { document.all('ApproveFlag').value = arrResult[0][61]; } catch(ex) { };
    try { document.all('ApproveCode').value = arrResult[0][62]; } catch(ex) { };
    try { document.all('ApproveDate').value = arrResult[0][63]; } catch(ex) { };
    try { document.all('ApproveTime').value = arrResult[0][64]; } catch(ex) { };
    try { document.all('UWFlag').value = arrResult[0][65]; } catch(ex) { };
    try { document.all('UWOperator').value = arrResult[0][66]; } catch(ex) { };
    try { document.all('UWDate').value = arrResult[0][67]; } catch(ex) { };
    try { document.all('UWTime').value = arrResult[0][68]; } catch(ex) { };
    try { document.all('AppFlag').value = arrResult[0][69]; } catch(ex) { };
    try { document.all('PolAppntDate').value = arrResult[0][70]; } catch(ex) { };
    try { document.all('GetPolDate').value = arrResult[0][71]; } catch(ex) { };
    try { document.all('GetPolTime').value = arrResult[0][72]; } catch(ex) { };
    try { document.all('CustomGetPolDate').value = arrResult[0][73]; } catch(ex) { };
    try { document.all('State').value = arrResult[0][74]; } catch(ex) { };
    try { document.all('Operator').value = arrResult[0][75]; } catch(ex) { };
    try { document.all('MakeDate').value = arrResult[0][76]; } catch(ex) { };
    try { document.all('MakeTime').value = arrResult[0][77]; } catch(ex) { };
    try { document.all('ModifyDate').value = arrResult[0][78]; } catch(ex) { };
    try { document.all('ModifyTime').value = arrResult[0][79]; } catch(ex) { };
    try { document.all('SellType').value = arrResult[0][87]; } catch(ex) { };

    try { document.all('AppntBankCode').value = arrResult[0][90]; } catch(ex) { };
    try { document.all('AppntBankAccNo').value = arrResult[0][91]; } catch(ex) { };
    try { document.all('AppntAccName').value = arrResult[0][92]; } catch(ex) { };
    try { document.all('PayMode').value = arrResult[0][93]; } catch(ex) { };
    try { document.all('AutoPayFlag').value = arrResult[0][96]; } catch(ex) { };
    try { document.all('RnewFlag').value = arrResult[0][97]; } catch(ex) { };
		//tongmeng 2009-04-15 Add
		//���ӳ���ǩ��
		try { document.all('SignName').value = arrResult[0][100]; } catch(ex) { };
	//���ӡ��������ڡ� 2010-03-18 -hanbin 
	try { document.all('FirstTrialDate').value = arrResult[0][81]; } catch(ex) { };	
	try { document.all('XQremindFlag').value = arrResult[0][108]; } catch(ex) { };	
	showOneCodeName('XQremindFlag','XQremindFlag','XQremindFlagName');;
    //���²�ѯ ҵ��Ա��Ͷ���˹�ϵ
    if(document.all('ContNo').value!="" && document.all('AgentCode').value!="")
    {
		
					var sqlid15="ContInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
    mySql15.addSubPara(document.all('ProposalContNo').value);//ָ������Ĳ���
    mySql15.addSubPara(document.all('AgentCode').value);//ָ������Ĳ���
	var relaSQL=mySql15.getString();
		
//    	var relaSQL="select grpcontno,agentcode,relationship from lacommisiondetail where 1=1"
//    			+ " and grpcontno='"+document.all('ProposalContNo').value+"' "
//    			+ " and agentcode='"+document.all('AgentCode').value+"' ";	
    	var arrRela=easyExecSql(relaSQL);
		if(arrRela!=null)
		{
			try { document.all('RelationShip').value =""; } catch(ex) { };
			try { document.all('RelationShipName').value =""; } catch(ex) { };
			try { document.all('RelationShip').value = arrRela[0][2]; } catch(ex) { };
			//showOneCodeName('agentrelatoappnt','RelationShip','RelationShipName');
		}
    }
}
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null)
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



//Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
function queryAppntNo() 
{
	if (document.all("AppntNo").value == "" && loadFlag == "1")
	{
		showAppnt1();
	}
	else if (loadFlag != "1" && loadFlag != "2")
	{
		alert("ֻ����Ͷ����¼��ʱ���в�����");
	}
	else
	{
		
    var sqlid16="ContInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
    mySql16.addSubPara(document.all("AppntNo").value );//ָ������Ĳ���
	var relaSQL16=mySql16.getString();
	
		arrResult = easyExecSql(relaSQL16, 1, 0);
		//arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
		if (arrResult == null) 
		{
			alert("δ�鵽Ͷ������Ϣ");
			displayAppnt(new Array());
			emptyUndefined();
		}
		else
		{
			displayAppnt(arrResult[0]);
			showAppntCodeName();
			getdetailaddress();
		}
	}
}

function showAppnt1()

{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryAppnt" ,"",sFeatures);
	}
}

function queryAgent()
{
	//add by wangyc
	//reutrn;
	
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	/*if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}*/
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length==10){
	  var cAgentCode = fm.AgentCode.value;  //��������
    //alert("cAgentCode=="+cAgentCode);
    //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
    //alert("cAgentCode=="+cAgentCode);
    /*if(cAgentCode.length!=8){
    	return;
    }*/
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   	
	    var sqlid17="ContInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
    mySql17.addSubPara(cAgentCode );//ָ������Ĳ���
	var strSQL=mySql17.getString();
	
//	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+cAgentCode+"' ";//and a.managecom='"+document.all("ManageCom").value+"'";
   //alert(strSQL);
    var arrResult = easyExecSql(strSQL); 
    //alert(arrResult);
    if (arrResult != null) {
    	//fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
      //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][3]+"]");
    }
    else
    {
      fm.AgentCode.value ="";
    	fm.BranchAttr.value ="";
    	fm.AgentGroup.value ="";
  	  fm.AgentName.value ="";
     alert("����Ϊ:["+cAgentCode+"]��ҵ��Ա�����ڣ����߲��ڸù�������£������Ѿ���ְ����ȷ��!");
    }
	}	
	initManageCom();
}

//��ѯ�������
function initManageCom()
{	
	fm.ManageCom.value = '';
	fm.ManageComName.value = '';
	var tSaleChnl = fm.SaleChnl.value;
    if(tSaleChnl!=null && tSaleChnl != '')
	{
		if(tSaleChnl == '03' || tSaleChnl == '05')
		{
			if(fm.AgentCom.value != null && fm.AgentCom.value != "")
			{
				
					    var sqlid18="ContInputSql18";
	var mySql18=new SqlClass();
	mySql18.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
    mySql18.addSubPara(fm.AgentCom.value );//ָ������Ĳ���
	var strSql=mySql18.getString();
				
				//var strSql = "select ManageCom,(select name from ldcom where comcode=managecom),branchtype from LACom where AgentCom='" + fm.AgentCom.value +"'";
			    var arrResult = easyExecSql(strSql);
			       //alert(arrResult);
			    if (arrResult != null) {      
			      fm.ManageCom.value = arrResult[0][0];
			      fm.ManageComName.value = arrResult[0][1];
			      if(arrResult[0][2]!=null && arrResult[0][2]=='6')
			      {
			      	var cManageCom = '';
			      	if(document.all('PrtNo').value != "")
					{		
					
	var sqlid19="ContInputSql19";
	var mySql19=new SqlClass();
	mySql19.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
    mySql19.addSubPara(fm.PrtNo.value );//ָ������Ĳ���
	var sql=mySql19.getString();
					
						//var sql = "SELECT managecom FROM es_doc_main where doccode='"+ trim(document.all('PrtNo').value) + "' and subtype='UA001'";
						cManageCom = easyExecSql(sql);	
					}
			      	if(cManageCom != null && cManageCom[0][0] != "")
			      	{
			      		var length = cManageCom[0][0].length;
			      		for(var i=length;i<8;i++)
			      		{
			      			cManageCom[0][0] = cManageCom[0][0] +'0';
			      		}
			      		fm.ManageCom.value = cManageCom[0][0];
						
							var sqlid20="ContInputSql20";
	var mySql20=new SqlClass();
	mySql20.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
    mySql20.addSubPara(cManageCom[0][0] );//ָ������Ĳ���
	var sql=mySql20.getString();
						
			      		//var sql = "select name from ldcom where comcode='"+cManageCom[0][0]+"'";
						var arrResult1 = easyExecSql(sql);	
						if(arrResult1 != null)
						{							
			     			fm.ManageComName.value = arrResult1[0][0];
						}						
			      	}			      	
			      }
				}
				
			}	
		}
		else
		{
			if(document.all('AgentCode').value != null && document.all('AgentCode').value != "")
			{
				
											var sqlid21="ContInputSql21";
	var mySql21=new SqlClass();
	mySql21.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
    mySql21.addSubPara(fm.AgentCode.value );//ָ������Ĳ���
	var strSql=mySql21.getString();
				
				//var strSql = "select ManageCom,(select name from ldcom where comcode=managecom) from LAAgent where AgentCode='" + fm.AgentCode.value +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
			    var arrResult = easyExecSql(strSql);
			       //alert(arrResult);
			    if (arrResult != null) {      
			      fm.ManageCom.value = arrResult[0][0];
			      fm.ManageComName.value = arrResult[0][1];
				}
			}  
		}			
	}	   
}

//�����¼��
function QuestInput2()
{
	cContNo = fm.ContNo.value;  //��������
	if(cContNo == "")
	{
		alert("���޺�ͬͶ�����ţ����ȱ���!");
	}
	else
	{
		window.open("../uw/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatures);
	}
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery1(arrQueryResult)
{
      //document.all("Donextbutton1").style.display="";
      //document.all("Donextbutton2").style.display="none";
      //document.all("Donextbutton3").style.display="";
      //document.all("butBack").style.display="none";
      //��ϸ��Ϣ��ʼ��
      detailInit(arrQueryResult);

}
//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{
	if(arrResult!=null)
	{
	fm.AgentCode.value = arrResult[0][0];
	fm.BranchAttr.value = arrResult[0][10];
	fm.AgentGroup.value = arrResult[0][1];
	fm.AgentName.value = arrResult[0][3];
	fm.AgentManageCom.value = arrResult[0][2];
	//fm.ManageCom.value=arrResult[0][2];
	//showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('comcode','AgentManageCom','AgentManageComName');
	initManageCom();
	}
}

//���ز�ѯ�����������ҵ��Աmultline
function afterQuery3(arrResult)
{
	
  if(arrResult!=null)
  {
		document.all( tField ).all( 'MultiAgentGrid1').value = arrResult[0][0];
		document.all( tField ).all( 'MultiAgentGrid2').value = arrResult[0][3];
		document.all( tField ).all( 'MultiAgentGrid3').value = arrResult[0][2];
		document.all( tField ).all( 'MultiAgentGrid4').value = arrResult[0][10];
		document.all( tField ).all( 'MultiAgentGrid6').value = arrResult[0][1];
		document.all( tField ).all( 'MultiAgentGrid7').value = arrResult[0][6];
  }
	
}



function queryAgent2()
{
	if(document.all('ManageCom').value=="")
	{
		 alert("����¼����������Ϣ��");
		 return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 
	{
		var cAgentCode = fm.AgentCode.value;  //��������
		
	var sqlid22="ContInputSql22";
	var mySql22=new SqlClass();
	mySql22.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
    mySql22.addSubPara(cAgentCode);//ָ������Ĳ���
     mySql22.addSubPara(document.all('ManageCom').value );//ָ������Ĳ���
	var strSql=mySql22.getString();
		
		//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
	    var arrResult = easyExecSql(strSql);
	    if (arrResult != null) 
	    {
	      fm.AgentGroup.value = arrResult[0][2];
	      alert("��ѯ���:  ҵ��Ա����:["+arrResult[0][0]+"] ҵ��Ա����Ϊ:["+arrResult[0][1]+"]");
	    }
	    else
	    {
	     fm.AgentGroup.value="";
	     alert("����Ϊ:["+document.all('AgentCode').value+"]��ҵ��Ա�����ڣ���ȷ��!");
	    }
	}
}

function getdetail()
{
	//alert("fm.AppntNo.value=="+fm.AppntNo.value);
	
		var sqlid23="ContInputSql23";
	var mySql23=new SqlClass();
	mySql23.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
    mySql23.addSubPara(fm.AppntBankAccNo.value);//ָ������Ĳ���
    mySql23.addSubPara(fm.AppntNo.value );//ָ������Ĳ���
	var strSql=mySql23.getString();
	
	//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.AppntBankAccNo.value+"' and customerno='"+fm.AppntNo.value+"'";
	var arrResult = easyExecSql(strSql);
	if (arrResult != null) 
	{
	      fm.AppntBankCode.value = arrResult[0][0];
	      fm.AppntAccName.value = arrResult[0][1];
	}
	else
	{
	     //fm.AppntBankCode.value = '';
	     //fm.AppntAccName.value = '';
	     return;
	}
}
function getdetailwork()
{
	
	var sqlid24="ContInputSql24";
	var mySql24=new SqlClass();
	mySql24.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
    mySql24.addSubPara(fm.AppntOccupationCode.value);//ָ������Ĳ���
	var strSql=mySql24.getString();
	
	//var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value+"'";
	var arrResult = easyExecSql(strSql);
	if (arrResult != null)
	{
		fm.AppntOccupationType.value = arrResult[0][0];
		fm.AppntOccupationTypeName.value ="";
		showOneCodeName('occupationtype','AppntOccupationType','AppntOccupationTypeName');
		
	}
	else
	{
		fm.AppntOccupationType.value ="";
		fm.AppntOccupationTypeName.value ="";
	}
}

/********************
**
** ��ѯͶ���˵�ַ��Ϣ
**
*********************/
function getdetailaddress()
{
	
		var sqlid25="ContInputSql25";
	var mySql25=new SqlClass();
	mySql25.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
    mySql25.addSubPara(fm.AppntAddressNo.value);//ָ������Ĳ���
    mySql25.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
	var strSQL=mySql25.getString();
	
   //var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
   arrResult=easyExecSql(strSQL);
   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};
   try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
   try{document.all('AddressNoName').value= arrResult[0][2];}catch(ex){};
   try{document.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};
   try{document.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};
   try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
   try{document.all('AppntFax').value= arrResult[0][5];}catch(ex){};
   try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
   try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
   try{document.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};
   try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
   try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
   try{document.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
   //try{document.all('AppntPhone').value= arrResult[0][12];}catch(ex){};
   try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
   try{document.all('AppntMobile').value= arrResult[0][14];}catch(ex){};
   try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
   try{document.all('AppntEMail').value= arrResult[0][16];}catch(ex){};
   try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};
   try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
   try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
   try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
   try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
   try{document.all('AppntProvince').value= arrResult[0][28];}catch(ex){};
   try{document.all('AppntCity').value= arrResult[0][29];}catch(ex){};
   try{document.all('AppntDistrict').value= arrResult[0][30];}catch(ex){};
   try{document.all('AppntGrpName').value= arrResult[0][27];}catch(ex){};
}
function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    if(fm.AppntAddressNo.value=="")
    {
    	fm.AppntPostalAddress.value="";
    	fm.AddressNoName.value="";
    	fm.AppntProvinceName.value="";
    	fm.AppntProvince.value="";
    	fm.AppntCityName.value="";
    	fm.AppntCity.value="";
    	fm.AppntDistrictName.value="";
    	fm.AppntDistrict.value="";
    	fm.AppntZipCode.value="";
    	fm.AppntFax.value="";
    	fm.AppntEMail.value="";
    	fm.AppntDistrict.value="";
    //	fm.AppntGrpName.value="";
    	fm.AppntHomePhone.value="";
    	fm.AppntMobile.value="";
    	fm.AppntPhone.value="";
    	}
		
	var sqlid26="ContInputSql26";
	var mySql26=new SqlClass();
	mySql26.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
    mySql26.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
	strsql=mySql26.getString();
		
   // strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.AppntNo.value+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert ("tcodedata : " + tCodeData);
     document.all("AppntAddressNo").CodeData=tCodeData;
}


function afterCodeSelect( cCodeName, Field )
{
 	//alert("cCodeName==="+cCodeName);
	if(cCodeName=="GetAddressNo")
	{
		
	var sqlid27="ContInputSql27";
	var mySql27=new SqlClass();
	mySql27.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
    mySql27.addSubPara(fm.AppntAddressNo.value);//ָ������Ĳ���
    mySql27.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
	var strSQL=mySql27.getString();
		
		//var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
		arrResult=easyExecSql(strSQL);
		try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};
		try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
		try{document.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};
		try{document.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};
		//try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
		try{document.all('AppntFax').value= arrResult[0][5];}catch(ex){};
		try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
		try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
		try{document.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};
		try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
		//try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
		try{document.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
		//try{document.all('AppntPhone').value= arrResult[0][12];}catch(ex){};
		try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
		try{document.all('AppntMobile').value= arrResult[0][14];}catch(ex){};
		try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
		try{document.all('AppntEMail').value= arrResult[0][16];}catch(ex){};
		try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};
		try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
		try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
		try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
		try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
		try{document.all('AppntProvince').value= arrResult[0][28];}catch(ex){};
		try{document.all('AppntCity').value= arrResult[0][29];}catch(ex){};
		try{document.all('AppntDistrict').value= arrResult[0][30];}catch(ex){};
		//	showOneCodeName('province','AppntProvince','AppntProvinceName');
		//	showOneCodeName('city','AppntCity','AppntCityName');
		//	showOneCodeName('district','AppntDistrict','AppntDistrictName');
		getAddressName('province','AppntProvince','AppntProvinceName');
		getAddressName('city','AppntCity','AppntCityName');
		getAddressName('district','AppntDistrict','AppntDistrictName');	
		return;
	}
	if(cCodeName=="GetInsuredAddressNo")
	{
		
	var sqlid28="ContInputSql28";
	var mySql28=new SqlClass();
	mySql28.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
    mySql28.addSubPara(fm.InsuredAddressNo.value);//ָ������Ĳ���
    mySql28.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
	var strSQL=mySql28.getString();
		
		//var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
		arrResult=easyExecSql(strSQL);
		try{document.all('InsuredAddressNo').value= arrResult[0][1];}catch(ex){};
		try{document.all('PostalAddress').value= arrResult[0][2];}catch(ex){};
		try{document.all('ZipCode').value= arrResult[0][3];}catch(ex){};
		//try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
		try{document.all('Fax').value= arrResult[0][5];}catch(ex){};
		//try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
		//try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
		try{document.all('HomePhone').value= arrResult[0][8];}catch(ex){};
		//try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
		//try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
		//try{document.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
		try{document.all('Phone').value= arrResult[0][4];}catch(ex){};
		//try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
		try{document.all('Mobile').value= arrResult[0][14];}catch(ex){};
		//try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
		try{document.all('EMail').value= arrResult[0][16];}catch(ex){};
		//try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};
		//try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
		//try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
		//try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
		//try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
		try{document.all('InsuredProvince').value= arrResult[0][28];}catch(ex){};
		try{document.all('InsuredCity').value= arrResult[0][29];}catch(ex){};
		try{document.all('InsuredDistrict').value= arrResult[0][30];}catch(ex){};
		//	showOneCodeName('province','InsuredProvince','InsuredProvinceName');
		//	showOneCodeName('city','InsuredCity','InsuredCityName');
		//	showOneCodeName('district','InsuredDistrict','InsuredDistrictName');
		getAddressName('province','InsuredProvince','InsuredProvinceName');
		getAddressName('city','InsuredCity','InsuredCityName');
		getAddressName('district','InsuredDistrict','InsuredDistrictName');
		return;
	}
 
	if(cCodeName=="paymode")
	{
		if(document.all('PayMode').value=="4")
		{
		//divLCAccount1.style.display="";
		}
		else
		{
		//divLCAccount1.style.display="none";
		//alert("accountImg===");
		}
	}
	if(cCodeName=="impartver"){
		//alert(1928);
		setFocus();
	}
	if (cCodeName == "AgentCom" || cCodeName == "salechnl") {
    	initManageCom();
    }
	
	afterCodeSelect2( cCodeName, Field );
}

/*********************************************************************
 *  ��ʼ��������MissionID
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function initMissionID()
{
	if(tMissionID == "null" || tSubMissionID == "null")
	{
		//alert("tMissionID="+tMissionID);
		//alert("tSubMissionID="+tSubMissionID);
	  tMissionID = mSwitch.getVar('MissionID');
	  tSubMissionID = mSwitch.getVar('SubMissionID');
	}
	else
	{
	  mSwitch.deleteVar("MissionID");
	  mSwitch.deleteVar("SubMissionID");
	  mSwitch.addVar("MissionID", "", tMissionID);
	  mSwitch.addVar("SubMissionID", "", tSubMissionID);
	  mSwitch.updateVar("MissionID", "", tMissionID);
	  mSwitch.updateVar("SubMissionID", "", tSubMissionID);
	}
}
function FillPostalAddress()
{
	if(fm.CheckPostalAddress.value=="1")
	{
		document.all('AppntPostalAddress').value=document.all('CompanyAddress').value;
		document.all('AppntZipCode').value=document.all('AppntGrpZipCode').value;
		document.all('AppntPhone').value= document.all('AppntPhone').value;
		document.all('AppntFax').value= document.all('AppntGrpFax').value;
	}
	else if(fm.CheckPostalAddress.value=="2")
	{
		document.all('AppntPostalAddress').value=document.all('AppntHomeAddress').value;
		document.all('AppntZipCode').value=document.all('AppntHomeZipCode').value;
		document.all('AppntPhone').value= document.all('AppntHomePhone').value;
		document.all('AppntFax').value= document.all('AppntHomeFax').value;
	}
	else if(fm.CheckPostalAddress.value=="3")
	{
		document.all('AppntPostalAddress').value="";
		document.all('AppntZipCode').value="";
		document.all('AppntPhone').value= "";
		document.all('AppntFax').value= "";
	}
}


function AppntChk()
{
return false;
}

//�ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ��
//�ж�ϵͳ���Ƿ�����������Ա𡢳���������ͬ��֤������֤��������ͬ�ı�������Ϣ��
function AppntChkNew()
{

}

function checkidtype()
{
	if(fm.AppntIDType.value==""&&fm.AppntIDNo.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.AppntIDNo.value="";
	}
}
/*********************************************************************
 *  �������֤��ȡ�ó������ں��Ա�
 *  ����  ��  ���֤��
 *  ����ֵ��  ��
 *********************************************************************
 */

function getBirthdaySexByIDNo(iIdNo)
{
	if(document.all('AppntIDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		{
			alert("��������֤��λ������");
			theFirstValue="";
			theSecondValue="";
			//document.all('AppntIDNo').focus();
			return;
		}
		else
		{
			document.all('AppntBirthday').value=tBirthday;
			document.all('AppntSex').value=tSex;
		}
	}
}
function getCompanyCode()
{
    var strsql = "";
    var tCodeData = "";
	
	var sqlid31="ContInputSql31";
	var mySql31=new SqlClass();
	mySql31.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
    mySql31.addSubPara("1");
	strsql=mySql31.getString();
	
//    strsql = "select CustomerNo,GrpName from LDgrp ";
    
	var sqlid88="ContInputSql88";
	var mySql88=new SqlClass();
	mySql88.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql88.setSqlId(sqlid88);//ָ��ʹ�õ�Sql��id
	mySql88.addSubPara(PrtNo);//ָ������Ĳ���
	strsql=mySql88.getString();
	
    document.all("AppntGrpNo").CodeData=tCodeData+easyQueryVer3(strsql, 1, 0, 1);
}
function haveMultiAgent()
{
	if(document.all("multiagentflag").checked)
	{
		DivMultiAgent.style.display="";
	}
	else
	{
		DivMultiAgent.style.display="none";
	}

}

//Muline ����Ӷ����� parm1
function queryAgentGrid(Field)
{
	tField=Field;
	if(document.all('ManageCom').value=="")
	{
		 alert("����¼����������Ϣ��");
		 return;
	}
	if(document.all(Field).all('MultiAgentGrid1').value=="")
	{
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=&queryflag=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}

	if(document.all( Field ).all('MultiAgentGrid1').value != "")	 
	{
		var cAgentCode = document.all( Field ).all('MultiAgentGrid1').value;  //��������
//		var strSql = "select AgentCode from LAAgent where AgentCode='" + cAgentCode +"'";
		
		var sqlid89="ContInputSql89";
		var mySql89=new SqlClass();
		mySql89.setResourceName("app.ContInputSql89"); //ָ��ʹ�õ�properties�ļ���
		mySql89.setSqlId(sqlid89);//ָ��ʹ�õ�Sql��id
		mySql89.addSubPara(cAgentCode);//ָ������Ĳ���
		var strSql=mySql89.getString();
		
	    var arrResult = easyExecSql(strSql);
	    if (arrResult == null) 
	    {
	      alert("����Ϊ:["+document.all( Field ).all('MultiAgentGrid1').value+"]�Ĵ����˲����ڣ���ȷ��!");
	    }
    }
}


//��ʼ����ҵ��Ա��Ϣ
function displayMainAgent()
{
  document.all("BranchAttr").value = arrResult[0][3];
  document.all("AgentName").value = arrResult[0][1];
}

function displayMultiAgent()
{
   document.all('multiagentflag').checked="true";
   haveMultiAgent();
}

//�������������У��
function confirmSecondInput1(aObject,aEvent)
{
	if(aEvent=="onkeyup")
	{
		var theKey = window.event.keyCode;
		if(theKey=="13")
		{
			if(theFirstValue!="")
			{
				theSecondValue = aObject.value;
				if(theSecondValue=="")
				{
					alert("���ٴ�¼�룡");
					aObject.value="";
					aObject.focus();
					return;
				}
				if(theSecondValue==theFirstValue)
				{
					aObject.value = theSecondValue;
					return;
				}
				else
				{
					alert("����¼����������������¼�룡");
					theFirstValue="";
					theSecondValue="";
					aObject.value="";
					aObject.focus();
					return;
				}
			}
			else
			{
				theFirstValue = aObject.value;
				if(theFirstValue=="")
				{
					theSecondValue="";
					alert("��¼�����ݣ�");
					return;
				}
				aObject.value="";
				aObject.focus();
				return;
			}
		}
	}
	else if(aEvent=="onblur")
	{
		if(theFirstValue!="")
		{
			theSecondValue = aObject.value;
			if(theSecondValue=="")
			{
				alert("���ٴ�¼�룡");
				aObject.value="";
				aObject.focus();
				return;
			}
			if(theSecondValue==theFirstValue)
			{
				aObject.value = theSecondValue;
				theSecondValue="";
				theFirstValue="";
				return;
			}
			else
			{
				alert("����¼����������������¼�룡");
				theFirstValue="";
				theSecondValue="";
				aObject.value="";
				aObject.focus();
				return;
			}
		}
		else
		{
			theFirstValue = aObject.value;
			theSecondValue="";
			if(theFirstValue=="")
			{
				return;
			}
			aObject.value="";
			aObject.focus();
			return;
		}
	}
}
//У��Ͷ������
function checkapplydate()
{
	if(trim(fm.PolAppntDate.value)==""){return ;}
    else if (fm.PolAppntDate.value.length == 8)
	{
		if(fm.PolAppntDate.value.indexOf('-')==-1)
		{
			var Year =     fm.PolAppntDate.value.substring(0,4);
			var Month =    fm.PolAppntDate.value.substring(4,6);
			var Day =      fm.PolAppntDate.value.substring(6,8);
			fm.PolAppntDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("�������Ͷ����������!");
			fm.PolAppntDate.value = "";
			fm.PolAppntDate.focus();
			return;
			}
		}else{
			alert("�������Ͷ�����ڸ�ʽ����!");
			fm.PolAppntDate.value = "";
			fm.PolAppntDate.focus();
			return;
		}
	}
	else if(fm.PolAppntDate.value.length == 10)
	{
		var Year =     fm.PolAppntDate.value.substring(0,4);
		var Month =    fm.PolAppntDate.value.substring(5,7);
		var Day =      fm.PolAppntDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("�������Ͷ����������!");
		fm.PolAppntDate.value = "";
		fm.PolAppntDate.focus();
		return;
		}
	}
	else
	{
		alert("�������Ͷ����������!");
        fm.PolAppntDate.value = "";
        fm.PolAppntDate.focus();
        return;
	}
	//����ϵͳ��¼���Ͷ������У��
	if(checkPolDate(fm.ProposalContNo.value,fm.PolAppntDate.value)==false)
	{
		fm.PolAppntDate.value="";
		fm.PolAppntDate.focus();
		return;
	}
}

//У�顾�������ڡ� -2010-03-19 - hanbin
function checkFirstTrialDate()
{
	if(trim(fm.FirstTrialDate.value)==""){return ;}
    else if (fm.FirstTrialDate.value.length == 8)
	{
		if(fm.FirstTrialDate.value.indexOf('-')==-1)
		{ 
			var Year =     fm.FirstTrialDate.value.substring(0,4);
			var Month =    fm.FirstTrialDate.value.substring(4,6);
			var Day =      fm.FirstTrialDate.value.substring(6,8);
			fm.FirstTrialDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			 alert("�������Ͷ����������!");
			fm.FirstTrialDate.value = "";  
			return;
			}
		}else{
			alert("�������Ͷ�����ڸ�ʽ����!");
			fm.FirstTrialDate.value = "";  
			return;
		}
	}
	else if(fm.FirstTrialDate.value.length == 10)
	{
		var Year =     fm.FirstTrialDate.value.substring(0,4);
		var Month =    fm.FirstTrialDate.value.substring(5,7);
		var Day =      fm.FirstTrialDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("�������Ͷ����������!");
		fm.FirstTrialDate.value = "";
		fm.FirstTrialDate.focus();
		return;
		}
	}
	else
	{
		alert("�������Ͷ����������!");
		fm.FirstTrialDate.value = "";
		fm.FirstTrialDate.focus();
	}

}

/******************************************************************************
* ���У�飬����ϵͳ��¼���Ͷ������У�飬У�����Ϊ��¼���Ͷ�����ڿ�����¼����ϵͳ��������60����
* ���������ContNo---��ͬ�ţ�ӡˢ�ţ���  PolAppntDate---Ͷ������
*******************************************************************************/
function checkPolDate(ContNo, PolAppntDate)
{
    var tContNo = ContNo;//��ͬ��
    var tPolAppntDate = PolAppntDate;//Ͷ������
     //Ͷ������ֻ��Ϊ��ǰ������ǰ
    if (calAge(tPolAppntDate) < 0)
    {
        alert("Ͷ������ֻ��Ϊ��ǰ������ǰ!");
        return false;
    }
    var DayIntv=60;//¼��������Ͷ�����ڵı�׼���������Ĭ��Ϊ60��
    //��ѯ¼��������Ͷ�����ڵı�׼�������
	
		var sqlid32="ContInputSql32";
	var mySql32=new SqlClass();
	mySql32.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
    mySql32.addSubPara("1");
	var strsql32=mySql32.getString();
	
	var DayIntvArr = easyExecSql(strsql32);
    //var DayIntvArr = easyExecSql("select sysvarvalue from ldsysvar where sysvar='input_poldate_intv'");
    if (DayIntvArr != null) { DayIntv = DayIntvArr[0][0]; }
    //���ݺ�ͬ�Ż�ȡ¼������<��ѯ��ͬ��������ȡ֮������ȡϵͳ��ǰ����>
    var tMakeDate = "";//¼����ϵͳ��������
    if(tContNo!=null && tContNo!="")
    {
		//var makedatesql = "select contno,prtno,makedate from lccont where prtno='" + tContNo + "'";
		
				var sqlid33="ContInputSql33";
	var mySql33=new SqlClass();
	mySql33.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
     mySql33.addSubPara(tContNo);
	var makedatesql=mySql33.getString();
	
		var makedatearr = easyExecSql(makedatesql);
		if (makedatearr != null){ tMakeDate = makedatearr[0][2];}
    }
    if(tMakeDate=="")  //¼����ϵͳ��������Ϊ�գ��� Ĭ��ϵͳ����
    {    
	
					var sqlid34="ContInputSql34";
	var mySql34=new SqlClass();
	mySql34.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
     mySql34.addSubPara("1");
	var sql34=mySql34.getString();
	
	var sysdatearr = easyExecSql(sql34);
    	//var sysdatearr = easyExecSql("select to_date(sysdate) from dual");
    	tMakeDate = sysdatearr[0][0];//¼����ϵͳ�������ڣ�Ĭ��ϵͳ���ڡ�
    }
    /*
    var Days = dateDiff(tPolAppntDate, tMakeDate, "D");//¼��������Ͷ�����ڵļ��
    if (Days > DayIntv || Days < 0)
    {
        var strInfo = "Ͷ������Ӧ��¼����ϵͳ�������� "+DayIntv+" ���ڡ�";
        strInfo = strInfo +"\n��¼������["+tMakeDate+"] �� Ͷ������["+tPolAppntDate+"]=="+Days+" �졣";
		strInfo = strInfo +"\n��������дͶ�����ڡ�";
        alert(strInfo);
        return false;
    }
    */
    return true;
}

//У��Ͷ���˳�������
function checkappntbirthday()
{
	if(fm.AppntBirthday.value.length==8)
	{
		if(fm.AppntBirthday.value.indexOf('-')==-1)
		{
			var Year =     fm.AppntBirthday.value.substring(0,4);
			var Month =    fm.AppntBirthday.value.substring(4,6);
			var Day =      fm.AppntBirthday.value.substring(6,8);
			fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("�������Ͷ���˳�����������!");
			fm.AppntBirthday.value = "";
			return;
			}
		}
	}
	else 
	{
		var Year =     fm.AppntBirthday.value.substring(0,4);
		var Month =    fm.AppntBirthday.value.substring(5,7);
		var Day =      fm.AppntBirthday.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("�������Ͷ���˳�����������!");
		fm.AppntBirthday.value = "";
		return;
		}
	}
}


//У�鱻���˳�������
function checkinsuredbirthday()
{
	if(fm.Birthday.value.length==8)
	{
		if(fm.Birthday.value.indexOf('-')==-1)
		{
			var Year =     fm.Birthday.value.substring(0,4);
			var Month =    fm.Birthday.value.substring(4,6);
			var Day =      fm.Birthday.value.substring(6,8);
			fm.Birthday.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("������ı����˳�����������!");
			fm.Birthday.value = "";
			return;
			}
		}
	}
	else 
	{
		var Year =     fm.Birthday.value.substring(0,4);
		var Month =    fm.Birthday.value.substring(5,7);
		var Day =      fm.Birthday.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("������ı����˳�����������!");
		fm.Birthday.value = "";
		return;
		}
	}
}
//Ͷ��������<Ͷ���˱���������Ӧ��ΪͶ������������֮��;2005-11-18�޸�>
function getAge()
{
	if(fm.AppntBirthday.value=="")
	{
		return;
	}
	if(fm.AppntBirthday.value.indexOf('-')==-1)
	{
		var Year =fm.AppntBirthday.value.substring(0,4);
		var Month =fm.AppntBirthday.value.substring(4,6);
		var Day =fm.AppntBirthday.value.substring(6,8);
		fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
	}
	if(calAge(fm.AppntBirthday.value)<0)
	{
		alert("��������ֻ��Ϊ��ǰ������ǰ");
		fm.AppntAge.value="";
		return;
	}
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
	fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolAppntDate.value);
  	return ;
}
//������������<����������Ӧ��ΪͶ������������֮��;2005-11-18�޸�>
function getAge2()
{	
	if(fm.Birthday.value=="")
	{
		return;
	}
	if(fm.Birthday.value.indexOf('-')==-1)
	{
		var Year =     fm.Birthday.value.substring(0,4);
		var Month =    fm.Birthday.value.substring(4,6);
		var Day =      fm.Birthday.value.substring(6,8);
		fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	}
  	else
  	{
    	var Year1 =     fm.Birthday.value.substring(0,4);
	    var Month1 =    fm.Birthday.value.substring(5,7);
	    var Day1 =      fm.Birthday.value.substring(8,10);	
	    fm.Birthday.value = Year1+"-"+Month1+"-"+Day1;	
	}
	if(calAge(fm.Birthday.value)<0)
  	{
		alert("��������ֻ��Ϊ��ǰ������ǰ");
		fm.InsuredAppAge.value="";
		return;
    }
    fm.InsuredAppAge.value=calPolAppntAge(fm.Birthday.value,fm.PolAppntDate.value);
  	return ;
}  


//¼��У�鷽��
//���������verifyOrderУ���˳��
//ҵ�������ýӿڣ����ͨ��У�鷵��true�����򷵻�false
function verifyInputNB(verifyOrder)
{
	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var passFlag = true;	//У��ͨ����־
	//��������FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//����FORM�е�����ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//Ԫ��У������verify��ΪNULL
			if (window.document.forms[formsNum].elements[elementsNum].verify != null && window.document.forms[formsNum].elements[elementsNum].verify != "" && window.document.forms[formsNum].elements[elementsNum].verifyorder == verifyOrder)
			{
				//����У��verifyElement
				if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].verify, window.document.forms[formsNum].elements[elementsNum].value,window.document.forms[formsNum].name+"."+window.document.forms[formsNum].elements[elementsNum].name))
				{
					passFlag = false;
					break;
				}
			}
		}
		if (!passFlag) break;
	}
	return passFlag;
}

//***********************************************************


function focuswrap()
{
	myonfocus(showInfo1);
}

//�ύ�����水ť��Ӧ����
//function submitForm()
//{
//}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
	unlockScreen('lkscreen');
	try { showInfo.close(); } catch(e) {}
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo1.focus();
	if (FlagStr=="Succ" && mWFlag == 0)
	{
		if(confirm("�Ƿ����¼�������������ˣ�"))
		{
			emptyInsured();//���������¼�������Ա����¼�������ͻ�
			if(fm.InsuredSequencename.value=="������������")
			{
				//emptyFormElements();
				param="122";
				fm.pagename.value="122";
				fm.SequenceNo.value="2";
				fm.InsuredSequencename.value="�ڶ�������������";
				if (scantype== "scan")
				{
					setFocus();
				}
				noneedhome();
				return false;
			}
			if(fm.InsuredSequencename.value=="�ڶ�������������")
			{
				//emptyFormElements();
				param="123";
				fm.pagename.value="123";
				fm.SequenceNo.value="3";
				fm.InsuredSequencename.value="����������������";
				if (scantype== "scan")
				{
				setFocus();
				}
				noneedhome();
				return false;
			}
			if(fm.InsuredSequencename.value=="����������������")
			{
				//emptyFormElements();
				param="124";
				fm.pagename.value="124";
				fm.SequenceNo.value="";
				fm.InsuredSequencename.value="���ı�����������";
				if (scantype== "scan")
				{
				setFocus();
				}
				return false;
			}
		}else{
			document.all("Name").value = document.all("InsuredLastName").value+document.all("InsuredFirstName").value;
		}
	}
		getInsuredPolInfo();
	
	//if(InsuredGrid.mulLineCount>0)
	//{
	//	document.all('spanInsuredGrid').all('InsuredGridSel').checked='true'
	//}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit3( FlagStr, content )
{
	unlockScreen('lkscreen');  
	try { showInfo.close(); } catch(e) {}
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo1.focus();
	if (FlagStr=="Succ" && mWFlag == 0)
	{
	/****************
	if(confirm("�Ƿ����¼�������������ˣ�"))
	{
	if(fm.InsuredSequencename.value=="��һ������������")
	{
	//emptyFormElements();
	param="122";
	fm.pagename.value="122";
	fm.SequenceNo.value="2";
	fm.InsuredSequencename.value="�ڶ�������������";
	if (scantype== "scan")
	{
	setFocus();
	}
	noneedhome();
	return false;
	}
	if(fm.InsuredSequencename.value=="�ڶ�������������")
	{
	//emptyFormElements();
	param="123";
	fm.pagename.value="123";
	fm.SequenceNo.value="3";
	fm.InsuredSequencename.value="����������������";
	if (scantype== "scan")
	{
	setFocus();
	}
	noneedhome();
	return false;
	}
	if(fm.InsuredSequencename.value=="����������������")
	{
	//emptyFormElements();
	param="124";
	fm.pagename.value="124";
	fm.SequenceNo.value="";
	fm.InsuredSequencename.value="���ı�����������";
	if (scantype== "scan")
	{
	setFocus();
	}
	return false;
	}
	}
	***************************/
	}
	//Ĭ��ѡ���һ������
	document.all('spanInsuredGrid0').all('InsuredGridSel').checked="true";
	getInsuredDetail("spanInsuredGrid0","");
	mSwitch.deleteVar('PolNo');
	mSwitch.deleteVar('SelPolNo');
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

//�����¼��
function QuestInput()
{
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
    cContNo = fm.ContNo.value;  //��������
    if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    {
        if(mSwitch.getVar( "ProposalGrpContNo" )=="")
        {
            alert("���޼����ͬͶ�����ţ����ȱ���!");
        }
		else
		{
            window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatures);
        }
    }
    else
    {
        if(cContNo == "")
        {
            alert("���޺�ͬͶ�����ţ����ȱ���!");
        }
        else
        {
            window.open("../uw/MSQuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
        }
    }
}
//�������ѯ
function QuestQuery()
{
	cContNo = document.all("ContNo").value;  //��������
	if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
	{
		if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
		{
		alert("����ѡ��һ����������Ͷ����!");
		return ;
		}
		else
		{
		window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatures);
		}
	}
	else
	{
		if(cContNo == "")
		{
		alert("���޺�ͬͶ�����ţ����ȱ���!");
		}
		else
		{
		window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1",sFeatures);
		}
	}
}


/*********************************************************************
 *  ����������Ϣ¼��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function intoRiskInfo()
{

	//�Ѻ�ͬ��Ϣ�ͱ�������Ϣ�����ڴ�
	mSwitch = parent.VD.gVSwitch;  //���ݴ�
	putCont();   //ע��ú�����ContInput.js��
	if((fm.InsuredNo.value==""||fm.ContNo.value==""||InsuredGrid.mulLineCount=="0" )&& loadFlag!="17")
	{
		alert("������ӣ�ѡ�񱻱���");
		return false;
	}
	//alert(InsuredGrid.mulLineCount);
	var tRow = InsuredGrid.getSelNo() - 1;
	//var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredNo=fm.InsuredNo.value;
	//tongmeng 2008-07-02 Modify 
	//����Լ֧�ֶ౻����
	//var sqlstr="select sequenceno From lcinsured where (prtno,contno)= (select prtno,contno from lccont where proposalcontno='"+fm.ProposalContNo.value+"') and insuredno='"+tInsuredNo+"'" ;
	//var tSql = "select * from lcpol where contno = '"+fm.ProposalContNo.value+"' and riskcode = '00150000'";//�������Ϊ150,Ҫ���⴦��
	//turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);      
	//var sequence = easyExecSql(sqlstr,1,0);
	//if(sequence!="1" && InsuredGrid.mulLineCount>1 && !turnPage.strQueryResult)
	//{ 
	//	alert("����ȷѡ���һ������!");
	//	return;
	//}
	//�������
	delInsuredVar();
	//�������û���
	addInsuredVar();
  	try{mSwitch.addVar('SelPonNo','',fm.SelPolNo.value);}catch(ex){} //ѡ�����ֵ��������ֽ�������ѱ������Ϣ
	if ((LoadFlag=='5'||LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("����ѡ�񱻱�����������Ϣ��");
		return;
	}
	//������ƣ���¼����������޸�ʱ������������ֱ�����ѡ��һ�����ֺ���ܽ���������Ϣ
	if((LoadFlag=='1'||LoadFlag=='3') && (PolGrid.mulLineCount>0))
	{
		var tSelPol=PolGrid.getSelNo();
		if(tSelPol==null || tSelPol==0)
		{
			alert("����ѡ��һ�����ֺ��ٽ���������Ϣҳ�棡");
			return;
		}
	}
	//try{mSwitch.addVar('InsuredNo','',fm.InsuredNo.value);}catch(ex){}
	try{mSwitch.addVar('SelPolNo','',fm.SelPolNo.value);}catch(ex){}
	try{mSwitch.deleteVar('ContNo');}catch(ex){}
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.updateVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){}
	try{mSwitch.deleteVar('CValiDate');}catch(ex){}
	try{mSwitch.addVar('CValiDate','',document.all('PolAppntDate').value);}catch(ex){}
	try{mSwitch.addVar('InsuredNo','',document.all('InsuredNo').value);}catch(ex){}
	//alert("��Ч����=Ͷ������="+mSwitch.getVar('CValiDate'));

	//�ж��Ƿ���Ҫ���пͻ��ϲ�У��
	//alert(document.all('AppntChkButton').disabled);
	//alert(document.all('InsuredChkButton').disabled);
	//modified by ������ 2011-05-30
	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled+"&AppntChkFlag="+document.all('AppntChkButton').disabled+"&InsuredNo="+document.all('InsuredNo').value + "&isTab=yes";
}

/*********************************************************************
 *  ɾ�������б���������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function delInsuredVar()
{
    try{mSwitch.deleteVar('ContNo');}catch(ex){};
    try{mSwitch.deleteVar('InsuredNo');}catch(ex){};
    try{mSwitch.deleteVar('PrtNo');}catch(ex){};
    try{mSwitch.deleteVar('GrpContNo');}catch(ex){};
 //   try{mSwitch.deleteVar('AppntNo');}catch(ex){};
 //   try{mSwitch.deleteVar('ManageCom');}catch(ex){};
    try{mSwitch.deleteVar('ExecuteCom');}catch(ex){};
    try{mSwitch.deleteVar('FamilyType');}catch(ex){};
    try{mSwitch.deleteVar('RelationToMainInsure');}catch(ex){};
    try{mSwitch.deleteVar('RelationToAppnt');}catch(ex){};
    try{mSwitch.deleteVar('AddressNo');}catch(ex){};
    try{mSwitch.deleteVar('SequenceNo');}catch(ex){};
    try{mSwitch.deleteVar('Name');}catch(ex){};
    try{mSwitch.deleteVar('Sex');}catch(ex){};
    try{mSwitch.deleteVar('Birthday');}catch(ex){};
    try{mSwitch.deleteVar('IDType');}catch(ex){};
    try{mSwitch.deleteVar('IDNo');}catch(ex){};
    try{mSwitch.deleteVar('InsuredPlace');}catch(ex){};
    try{mSwitch.deleteVar('Marriage');}catch(ex){};
    try{mSwitch.deleteVar('MarriageDate');}catch(ex){};
    try{mSwitch.deleteVar('Health');}catch(ex){};
    //try{mSwitch.deleteVar('Stature');}catch(ex){};
    //try{mSwitch.deleteVar('Avoirdupois');}catch(ex){};
    try{mSwitch.deleteVar('Degree');}catch(ex){};
    try{mSwitch.deleteVar('CreditGrade');}catch(ex){};
    try{mSwitch.deleteVar('BankCode');}catch(ex){};
    try{mSwitch.deleteVar('BankAccNo');}catch(ex){};
    try{mSwitch.deleteVar('AccName');}catch(ex){};
    try{mSwitch.deleteVar('JoinCompanyDate');}catch(ex){};
    try{mSwitch.deleteVar('StartWorkDate');}catch(ex){};
    try{mSwitch.deleteVar('Position');}catch(ex){};
    try{mSwitch.deleteVar('Salary');}catch(ex){};
    try{mSwitch.deleteVar('OccupationType');}catch(ex){};
    try{mSwitch.deleteVar('OccupationCode');}catch(ex){};
    try{mSwitch.deleteVar('WorkType');}catch(ex){};
    try{mSwitch.deleteVar('PluralityType');}catch(ex){};
    try{mSwitch.deleteVar('SmokeFlag');}catch(ex){};
    try{mSwitch.deleteVar('ContPlanCode');}catch(ex){};
    try{mSwitch.deleteVar('Operator');}catch(ex){};
    try{mSwitch.deleteVar('MakeDate');}catch(ex){};
    try{mSwitch.deleteVar('MakeTime');}catch(ex){};
    try{mSwitch.deleteVar('ModifyDate');}catch(ex){};
    try{mSwitch.deleteVar('ModifyTime');}catch(ex){};
    try{mSwitch.deleteVar('IDPeriodOfValidity');}catch(ex){};
    try{mSwitch.deleteVar('SocialInsuFlag');}catch(ex){};
}

/*********************************************************************
 *  ������������Ϣ���뵽������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addInsuredVar()
{
    try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
//    alert("ContNo:"+fm.InsuredNo.value);
    try{mSwitch.addVar('InsuredNo','',fm.InsuredNo.value);}catch(ex){};
    try{mSwitch.addVar('PrtNo','',fm.PrtNo.value);}catch(ex){};
    try{mSwitch.addVar('GrpContNo','',fm.GrpContNo.value);}catch(ex){};
 //   try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
 //   try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
    try{mSwitch.addVar('ExecuteCom','',fm.ExecuteCom.value);}catch(ex){};
    try{mSwitch.addVar('FamilyType','',fm.FamilyType.value);}catch(ex){};
    try{mSwitch.addVar('RelationToMainInsure','',fm.RelationToMainInsure.value);}catch(ex){};
    try{mSwitch.addVar('RelationToAppnt','',fm.RelationToAppnt.value);}catch(ex){};

    try{mSwitch.addVar('AddressNo','',fm.AppntAddressNo.value);}catch(ex){};
    try{mSwitch.addVar('SequenceNo','',fm.SequenceNo.value);}catch(ex){};
    try{mSwitch.addVar('Name','',fm.Name.value);}catch(ex){};
    try{mSwitch.addVar('Sex','',fm.Sex.value);}catch(ex){};
    try{mSwitch.addVar('Birthday','',fm.Birthday.value);}catch(ex){};
    try{mSwitch.addVar('IDType','',fm.IDType.value);}catch(ex){};
    try{mSwitch.addVar('IDNo','',fm.IDNo.value);}catch(ex){};
    try{mSwitch.addVar('InsuredPlace','',fm.InsuredPlace.value);}catch(ex){};
    try{mSwitch.addVar('Marriage','',fm.Marriage.value);}catch(ex){};
    try{mSwitch.addVar('MarriageDate','',fm.MarriageDate.value);}catch(ex){};
    try{mSwitch.addVar('Health','',fm.Health.value);}catch(ex){};
   // try{mSwitch.addVar('Stature','',fm.Stature.value);}catch(ex){};
   // try{mSwitch.addVar('Avoirdupois','',fm.Avoirdupois.value);}catch(ex){};
    try{mSwitch.addVar('Degree','',fm.Degree.value);}catch(ex){};
    try{mSwitch.addVar('CreditGrade','',fm.CreditGrade.value);}catch(ex){};
    try{mSwitch.addVar('BankCode','',fm.BankCode.value);}catch(ex){};
    try{mSwitch.addVar('BankAccNo','',fm.BankAccNo.value);}catch(ex){};
    try{mSwitch.addVar('AccName','',fm.AccName.value);}catch(ex){};
    try{mSwitch.addVar('JoinCompanyDate','',fm.JoinCompanyDate.value);}catch(ex){};
    try{mSwitch.addVar('StartWorkDate','',fm.StartWorkDate.value);}catch(ex){};
    try{mSwitch.addVar('Position','',fm.Position.value);}catch(ex){};
    try{mSwitch.addVar('Salary','',fm.Salary.value);}catch(ex){};
//    alert("fm.OccupationType.value=="+fm.OccupationType.value);
    try{mSwitch.addVar('OccupationType','',fm.OccupationType.value);}catch(ex){};
    try{mSwitch.addVar('OccupationCode','',fm.OccupationCode.value);}catch(ex){};
    try{mSwitch.addVar('WorkType','',fm.WorkType.value);}catch(ex){};
    try{mSwitch.addVar('PluralityType','',fm.PluralityType.value);}catch(ex){};
    try{mSwitch.addVar('SmokeFlag','',fm.SmokeFlag.value);}catch(ex){};
    try{mSwitch.addVar('ContPlanCode','',fm.ContPlanCode.value);}catch(ex){};
    try{mSwitch.addVar('Operator','',fm.Operator.value);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',fm.MakeDate.value);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',fm.MakeTime.value);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',fm.ModifyDate.value);}catch(ex){};
    try{mSwitch.addVar('ModifyTime','',fm.ModifyTime.value);}catch(ex){};
    try{mSwitch.addVar('ProposalContNo','',fm.ProposalContNo.value);}catch(ex){};
    try{mSwitch.addVar('SocialInsuFlag','',fm.SocialInsuFlag.value);}catch(ex){};
    try{mSwitch.addVar('IDPeriodOfValidity','',fm.IDPeriodOfValidity.value);}catch(ex){};
//    alert("=fm.PolAppntDate.value="+fm.PolAppntDate.value);
//    try{mSwitch.addVar('CValidate','',fm.PolAppntDate.value);}catch(ex){};
    
}

/*********************************************************************
 *  ��ӱ�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addRecord()
{
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	if(addRecordNotNullCheck() == false){return false;}
	if(otherCheckinsurd()==false) return false;
	if(fm.AppntName.value==fm.Name.value && fm.AppntIDType.value==fm.IDType.value && fm.IDNo.value==fm.AppntIDNo.value && fm.Sex.value==fm.AppntSex.value && fm.Birthday.value==fm.AppntBirthday.value)
	{
		//alert("����������Ͷ������ͬ��");	
		//	fm.SamePersonFlag.checked=true;
		//isSamePerson();
	}
	if(fm.RelationToAppnt.value=="00")
	{
		if(fm.AppntName.value!=fm.Name.value || fm.AppntIDType.value!=fm.IDType.value || fm.IDNo.value!=fm.AppntIDNo.value || fm.Sex.value!=fm.AppntSex.value || fm.Birthday.value!=fm.AppntBirthday.value)
		{
			alert("����������Ͷ������Ϣ��һ�£�");
			fm.RelationToAppnt.focus();
			return;  
		}  
	}
	//�ж��Ƿ��Ѿ���ӹ�Ͷ���ˣ�û�еĻ���������ӱ�����
	if(!checkAppnt())
	{
		alert("������Ӻ�ͬ��Ϣ��Ͷ������Ϣ��");
		fm.AppntName.focus();
		return;
	}
	if(addRecordNotNullCheck() == false){return false;}

  	getdetailwork2();

  //2005.03.18 chenhq �Դ˽����޸�
  if(LoadFlag==1){

	 if(fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
	  {
	   alert("��Ͷ���˹�ϵֻ��ѡ���ˣ�");
	   fm.RelationToAppnt.value="00";
	   fm.RelationToAppntName.value="����";
	   return ;
	  }
	  var tPrtNo=document.all("PrtNo").value;

	var sqlid35="ContInputSql35";
	var mySql35=new SqlClass();
	mySql35.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
     mySql35.addSubPara(tPrtNo);
	var sqlstr=mySql35.getString();


	  //var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("�Ѿ����ڸÿͻ��ڲ���");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}

  //2005.03.18 chenhq �Դ˽����޸�
  if(LoadFlag==3){

	  var tPrtNo=document.all("PrtNo").value;

	var sqlid36="ContInputSql36";
	var mySql36=new SqlClass();
	mySql36.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
     mySql36.addSubPara(tPrtNo);
	var sqlstr=mySql36.getString();

	//  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("�Ѿ����ڸÿͻ��ڲ���");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


  //2005.03.18 chenhq �Դ˽����޸�
  if(LoadFlag==5){

	  var tPrtNo=document.all("PrtNo").value;


	var sqlid37="ContInputSql37";
	var mySql37=new SqlClass();
	mySql37.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
     mySql37.addSubPara(tPrtNo);
	var sqlstr=mySql37.getString();

	  //var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("�Ѿ����ڸÿͻ��ڲ���");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


  //2005.03.18 chenhq �Դ˽����޸�
  if(LoadFlag==6){

	  var tPrtNo=document.all("PrtNo").value;

	var sqlid38="ContInputSql38";
	var mySql38=new SqlClass();
	mySql38.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
     mySql38.addSubPara(tPrtNo);
	var sqlstr=mySql38.getString();

	 // var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("�Ѿ����ڸÿͻ��ڲ���");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


/***************************************************
  if(LoadFlag==1)
  {
    if(fm.Marriage.value=="")
    {
      alert("����д����״����");
    	return false;
    }
    if(fm.RelationToMainInsured.value=="")
    {
      alert("����д�����������˹�ϵ��");
    	return false;
    }
    if(fm.RelationToAppnt.value=="")
    {
      alert("����д��Ͷ���˹�ϵ����");
    	return false;
    }
  }

*******************************************************/

  if (document.all('PolTypeFlag').value==0)
  {
    if( verifyInputNB('2') == false ) return false;
  }
  //Add 2011-11-28 20:03  cuilong
  if(document.all('IDType').value!="9" && document.all('IDNo').value=="")
  {
	 alert("������֤�����벻��Ϊ�գ�");
	 document.all('IDNo').select();
	 return false;
  }
  if(document.all('IDType').value=="0")
  {
    var strChkIdNo = chkIdNo(trim(document.all('IDNo').value),trim(document.all('Birthday').value),trim(document.all('Sex').value));
    if (strChkIdNo != "")
    {
      alert(strChkIdNo);
	  return false;   //2010-11-09 20:38 cuilong
    }
  }

  if(!checkself()) return false;

  if(!checkrelation()) return false;

  if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false) return false;

  //if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;
	ImpartGrid.delBlankLine();
	//  ImpartDetailGrid.delBlankLine();
	//alert("fm.AddressNo.value=="+fm.InsuredAddressNo.value);
  if(fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
  {
    alert("�������˿ͻ���Ϊ�գ������е�ַ����");
    return false;
  }

  //hanlin 20050504
  fm.action="ContInsuredSave.jsp";
  //end hanlin
  document.all('ContType').value=ContType;
  document.all( 'BQFlag' ).value = BQFlag;
  document.all('fmAction').value="INSERT||CONTINSURED";
  document.getElementById("fm").submit();
}

/*********************************************************************
 *  �޸ı�ѡ�еı�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function modifyRecord()
{
	lockScreen('lkscreen');
	//09-06-09
	//���loadflag=3������޸ĸڣ��޸ı�������Ҫ��Ϣ��Ͷ�����˹�ϵΪ00-���������޸�Ͷ������Ϣ
	var tName = fm.Name.value;
	var tIDType = fm.IDType.value;
	var tIDNo = fm.IDNo.value;
	var tSex = fm.Sex.value;
	var tBirthday = fm.Birthday.value;
	var tContNo = fm.PrtNo.value;
	if(LoadFlag=="3"){
		var tSplitPrtNo = tContNo.substring(0,4);
		//��ͥ���ݲ�У��
		if(tSplitPrtNo=="8611"||tSplitPrtNo=="8621"||tSplitPrtNo=="8616"||tSplitPrtNo=="8615"){
			if(fm.RelationToInsured.value=="00"){
				
	var sqlid39="ContInputSql39";
	var mySql39=new SqlClass();
	mySql39.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
    mySql39.addSubPara(tContNo);
	mySql39.addSubPara(tName);
	mySql39.addSubPara(tBirthday);
	
	mySql39.addSubPara(tSex);
	mySql39.addSubPara(tIDType);
	mySql39.addSubPara(tIDNo);
	var tCheckSql=mySql39.getString();
				
//				var tCheckSql = "select * from lcappnt where prtno='"+tContNo+"' and appntname='"+tName+"'"
//							+ " and appntbirthday='"+tBirthday+"' and appntsex='"+tSex+"'"
//							+ " and idtype='"+tIDType+"' and idno='"+tIDNo+"'";
				var tArr = easyExecSql(tCheckSql,1,0);
				if(!tArr){
					alert("Ͷ�����˹�ϵΪ���ˣ����޸��˱�������Ҫ��Ϣ����Ͷ�����˿��ܲ���ͬһ�ˣ����޸�Ͷ�����˹�ϵ�����޸�Ͷ������Ϣ��");
					unlockScreen('lkscreen');
					return false;
				}
			}
		}
	}
	if(otherCheckinsurd()==false) {
	   unlockScreen('lkscreen');
	   return false;
	   }
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		unlockScreen('lkscreen');
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	/*
	�˴���ӱ������޸�У�飬����޸��˹ؼ���Ϣ<�������Ա�֤�����ͻ���롢��������>���� ְҵ���
	������ɾ��������Ϣ����Ϊ����ܻ����ͻ��Ż����¼��㱣��
	*/ 
	if(updateFlag=="1"){
		var tselno=InsuredGrid.getSelNo();
		if(tselno==0)
		{
			alert("��ѡ��һ�б����˼�¼");
			unlockScreen('lkscreen');
			return false;
		}
		/*start.ע�͡�MSҪ������ɾ�����ּ����޸ı�����---yeshu,20071224
		var tOldInsuredNo=InsuredGrid.getRowColData(tselno-1,1);
		var SSQL=" select t.insuredno,t.name,t.sex,t.birthday,t.idtype,t.idno,t.occupationcode"
				+" from lcinsured t where t.contno='"+fm.ContNo.value+"' and t.insuredno='"+tOldInsuredNo+"'";
		var tOldArr=easyExecSql(SSQL,1,0);
		//��ȡ׼���޸ĵ���Ϣ,ͬ��ѯ������Ϣ�Ƚϣ������һ�ͬ����ô��ѯ�Ƿ������֣�������ʾ����ɾ��������Ϣ
		if( fm.Name.value!=tOldArr[0][1] || fm.IDType.value!=tOldArr[0][4] || fm.IDNo.value!=tOldArr[0][5] 
		  || fm.Sex.value!=tOldArr[0][2] || fm.Birthday.value!=tOldArr[0][3] || fm.OccupationCode.value!=tOldArr[0][6])
		{
			var sqlstr="select polno from lcpol where contno='"+fm.ContNo.value+"' and insuredno='"+tOldInsuredNo+"' ";
			arrResult=easyExecSql(sqlstr,1,0);
			if(arrResult!=null)
			{
				alert("������޸��˸ñ����˹ؼ���Ϣ<�������Ա�֤�����ͻ���롢��������>��ְҵ�������ɾ���ñ������µ�������Ϣ��");
				return false;    
			}
		}end.---yeshu,20071224*/
	}
    //-----------------------------------------------------------------
	if(fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
	{
		alert("��Ͷ���˹�ϵֻ��ѡ���ˣ�");
		fm.RelationToAppnt.value="00";
		fm.RelationToAppntName.value="����";
		unlockScreen('lkscreen');
		return ;
	}

	if (document.all('PolTypeFlag').value==0)
	{
		if( verifyInputNB('2') == false ) {
			unlockScreen('lkscreen');
			return false;
		}
	}
	
	if(!checkself()){
		unlockScreen('lkscreen');
		return false;
	}
	if (fm.Name.value=='')
	{
		alert("��ѡ����Ҫ�޸ĵĿͻ���");
		unlockScreen('lkscreen');
		return false;
	}
	//alert("SelNo:"+InsuredGrid.getSelNo());
	if (InsuredGrid.mulLineCount==0)
	{
		showInfo.close();
		alert("�ñ������˻�û�б��棬�޷������޸ģ�");
		unlockScreen('lkscreen');
		return false;
	}
	/*
	if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
	{
		fm.InsuredAddressNo.value='';
		//alert("�������˿ͻ���Ϊ�գ������е�ַ����");
		//return false;
	}*/
	if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)
	{
		unlockScreen('lkscreen');
		return false;
	}
	//Add 2011-11-28 20:03  cuilong
	if(document.all('IDType').value!="9" && document.all('IDNo').value=="")
	{
		 alert("������֤�����벻��Ϊ�գ�");
		 document.all('IDNo').select();
		 return false;
	}
	if(document.all('IDType').value=="0")
	{
		var strChkIdNo = chkIdNo(trim(document.all('IDNo').value),trim(document.all('Birthday').value),trim(document.all('Sex').value));
		if (strChkIdNo != "")
		{
			alert(strChkIdNo);
			if(LoadFlag=="1")
			{
				unlockScreen('lkscreen');
				return false;   //2005-8-6 16:08 guomy
			}
		}
	}
	
	ImpartGrid.delBlankLine();
	getdetailwork2();
	document.all('ContType').value=ContType;
	document.all('fmAction').value="UPDATE||CONTINSURED";
	fm.action="ContInsuredSave.jsp";
	document.getElementById("fm").submit();
}
/*********************************************************************
 *  ɾ����ѡ�еı�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function deleteRecord()
{
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
    if (fm.InsuredNo.value=='')
    {
        alert("��ѡ����Ҫɾ���Ŀͻ���")
        return false;
    }
    if (InsuredGrid.mulLineCount==0)
    {
        alert("�ñ������˻�û�б��棬�޷������޸ģ�");
        return false;
    }
    
     if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("�������˿ͻ���Ϊ�գ������е�ַ����");
        return false;
    }
    //��ѯ�ÿͻ����Ƿ���������Ϣ�������� ��ʾ������ɾ���ÿͻ���������Ϣ
	
	var sqlid40="ContInputSql40";
	var mySql40=new SqlClass();
	mySql40.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
    mySql40.addSubPara(fm.ContNo.value);
	mySql40.addSubPara(fm.InsuredNo.value);
;
	var sqlstr=mySql40.getString();
	
    //var sqlstr="select polno from lcpol where contno='"+fm.ContNo.value+"' and insuredno='"+fm.InsuredNo.value+"' ";
    arrResult=easyExecSql(sqlstr,1,0);
    if(arrResult!=null)
    {
    	alert("�ñ�������ӵ�����֣�����ɾ�����������ֲ�Ʒ��Ϣ");
        return false;    
    }
    document.all('ContType').value=ContType;
    document.all('fmAction').value="DELETE||CONTINSURED";
    fm.action="ContInsuredSave.jsp";
    document.getElementById("fm").submit();
}
/*********************************************************************
 *  ������һҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function returnparent()
{
  	//alert("LoadFlag=="+LoadFlag);
  	var backstr=document.all("ContNo").value;

	mSwitch.addVar("PolNo", "", backstr);
	mSwitch.updateVar("PolNo", "", backstr);
	try
	{
	    mSwitch.deleteVar('ContNo');
	}
	catch(ex){};
	if(LoadFlag=="1"||LoadFlag=="3")
	{
		//alert(document.all("PrtNo").value);
  	location.href="../app/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
  }
  if(LoadFlag=="5"||LoadFlag=="25")
	{
		//alert(document.all("PrtNo").value);
    location.href="../app/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
  }

	if(LoadFlag=="2")
	{
    location.href="../app/ContGrpInsuredInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + document.all("GrpContNo").value+"&scantype="+scantype;
  }
	else if (LoadFlag=="6")
	{
	    location.href="ContInput.jsp?LoadFlag="+ LoadFlag + "&ContNo=" + backstr+"&prtNo="+document.all("PrtNo").value;
	    return;
	}
	else if (LoadFlag=="7")
	{
    location.href="../bq/GEdorTypeNI.jsp?BQFlag="+BQFlag;
	  return;
	}
	else if(LoadFlag=="4"||LoadFlag=="16"||LoadFlag=="13"||LoadFlag=="14"||LoadFlag=="23")
	{
	  if(Auditing=="1")
	  {
	  	top.close();
	  }
	  else
	  {
	    mSwitch.deleteVar("PolNo");
      parent.fraInterface.window.location = "../app/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype;
	  }
	}
	else if (LoadFlag=="99")
	{
	  location.href="ContPolInput.jsp?LoadFlag="+ LoadFlag+"&scantype="+scantype;
	  return;
	}
/*    else
    {
        location.href="ContInput.jsp?LoadFlag="+ LoadFlag;
	}  ���	��������Ĳ�ͬ�ݲ�֧��else��ʽ
*/
}
/*********************************************************************
 *  �������ּƻ�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function grpRiskPlanInfo()
{
    var newWindow = window.open("../app/GroupRiskPlan.jsp","',sFeatures");
}
/*********************************************************************
 *  ����ѡ��󴥷�ʱ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect2( cCodeName, Field )
{
    try
    {
        //�����������
        if( cCodeName == "PolTypeFlag")
        {
            if (Field.value!='0')
            {
                document.all('InsuredPeoples').readOnly=false;
                document.all('InsuredAppAge').readOnly=false;
            }
            else
            {
                document.all('InsuredPeoples').readOnly=true;
                document.all('InsuredAppAge').readOnly=true;
            }
        }
        if( cCodeName == "ImpartCode")
        {

        }
        if( cCodeName == "SequenceNo")
        {
	        if(Field.value=="1"&&fm.SamePersonFlag.checked==false)
	        {
	   	      //emptyInsured();
		        param="11";
		        fm.pagename.value="11";
            	fm.InsuredSequencename.value="������������";
           	 	fm.RelationToMainInsured.value="00";
	        	var tContNo=document.all("ContNo").value;
				
					var sqlid41="ContInputSql41";
	var mySql41=new SqlClass();
	mySql41.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
    mySql41.addSubPara(tContNo);
;
	var InsuredSql=mySql41.getString();
				
            	//var InsuredSql="select * from lcinsured where contno='"+tContNo+"' and SequenceNo='1'";
            	arrResult=easyExecSql(InsuredSql);//prompt("",InsuredSql);
            	if(arrResult!=null){
            		if(InsuredGrid.canSel==1)
 					{
 						eval("document.all('InsuredGridSel')[0].checked=true");
 						eval("document.all('InpInsuredGridSel')[0].value='1'");
 						//eval("")
 						getInsuredDetail("spanInsuredGrid0","");
 					}
 				}else{
            		emptyInsured();
            		fm.SequenceNo.value="1";
            		fm.SequenceNoName.value="��������";
            	}
	        }
	        if(Field.value=="2"&&fm.SamePersonFlag.checked==false)
	        {
	        	if(InsuredGrid.mulLineCount==0)
	        	{
	        		alert("������ӵ�һ������");
	        		fm.SequenceNo.value="1";
	        		fm.SequenceNoName.value="��������";
	        		return false;
	        	}
	        	//emptyInsured();
            	noneedhome();
		        param="122";
		        fm.pagename.value="122";
            	fm.InsuredSequencename.value="�ڶ�������������";
            	var tContNo=document.all("ContNo").value;
				
	var sqlid42="ContInputSql42";
	var mySql42=new SqlClass();
	mySql42.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
    mySql42.addSubPara(tContNo);
;
	var InsuredSql=mySql42.getString();
				
            	//var InsuredSql="select * from lcinsured where contno='"+tContNo+"' and SequenceNo='2'";
            	arrResult=easyExecSql(InsuredSql);
            	if(arrResult!=null){
            		if(InsuredGrid.canSel==1&&InsuredGrid.mulLineCount>1)
 					{
 						//document.all('InsuredGridSel')[1].checked=true;
 						//document.all('InsuredGridSel')[1].value='1';
 						eval("document.all('InsuredGridSel')[1].checked=true");
 						eval("document.all('InpInsuredGridSel')[1].value='1'");
 						//eval("")
 						getInsuredDetail("spanInsuredGrid1","");
 						//alert(document.all('InsuredGridSel')[0].value);
 						//InsuredGrid.setFocus(1,"");
 					}
            	}else{
            		//arrResult=null;
            		//DisplayInsured();
            		emptyInsured();
            		fm.SequenceNo.value="2";
            	}
	        }
	        if(Field.value=="3"&&fm.SamePersonFlag.checked==false)
	        {
	      	  if(InsuredGrid.mulLineCount==0)
	   	      {
	   		      alert("������ӵ�һ������");
	   		      Field.value="1";
	        		fm.SequenceNo.value="1";
	        		fm.SequenceNoName.value="��������";

	   		      return false;
	   	      }
	   	      if(InsuredGrid.mulLineCount==1)
	   	      {
	   	      	alert("������ӵڶ�������");
	   	      	Field.value="1";
	        		fm.SequenceNo.value="2";
	        		fm.SequenceNoName.value="�ڶ���������";
	   	      	return false;
	   	      }
	   	      //emptyInsured();
            	noneedhome();
		        param="123";
		        fm.pagename.value="123";
            	fm.InsuredSequencename.value="����������������";
            	var tContNo=document.all("ContNo").value;
				
					var sqlid43="ContInputSql43";
	var mySql43=new SqlClass();
	mySql43.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
    mySql43.addSubPara(tContNo);
;
	var InsuredSql=mySql43.getString();
				
              	//var InsuredSql="select * from lcinsured where contno='"+tContNo+"' and SequenceNo='3'";
             	arrResult=easyExecSql(InsuredSql);//prompt("",InsuredSql);
              	if(arrResult!=null){
            	if(InsuredGrid.canSel==1&&InsuredGrid.mulLineCount>1)
 					{
 						eval("document.all('InsuredGridSel')[2].checked=true");
 						eval("document.all('InpInsuredGridSel')[2].value='1'");
 						//eval("")
 						getInsuredDetail("spanInsuredGrid2","");
 					}
              	}else{//alert(3275);
            		//arrResult=null;
            		//DisplayInsured();
            		emptyInsured();
            		fm.SequenceNo.value="3";
              }
	        }
          if (scantype== "scan")
          {
            setFocus();
          }
        }
        if( cCodeName == "CheckPostalAddress")
        {
	        if(fm.CheckPostalAddress.value=="1")
	        {
	        	document.all('PostalAddress').value=document.all('GrpAddress').value;
            document.all('ZipCode').value=document.all('GrpZipCode').value;
            document.all('Phone').value= document.all('Phone').value;
  
	        }
	        else if(fm.CheckPostalAddress.value=="2")
	        {
	        	document.all('PostalAddress').value=document.all('HomeAddress').value;
            document.all('ZipCode').value=document.all('HomeZipCode').value;
            document.all('Phone').value= document.all('HomePhone').value;
	        }
	        else if(fm.CheckPostalAddress.value=="3")
	        {
	        	document.all('PostalAddress').value="";
            document.all('ZipCode').value="";
            document.all('Phone').value= "";
	        }
        }

    }
    catch(ex) {}

}
/*********************************************************************
 *  ��ʾ��ͥ���±������˵���Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredInfo()
{
    //alert("hl in getInsuredInfo ContNo="+document.all("ContNo").value);
    //alert("hl in getInsuredInfo GrpName="+document.all("GrpName").value);
    var ContNo=document.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
		
							var sqlid44="ContInputSql44";
	var mySql44=new SqlClass();
	mySql44.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
    mySql44.addSubPara(ContNo);
	var strSQL=mySql44.getString();
		
       // var strSQL ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+ContNo+"'";

        //alert("strSQL=="+strSQL);

        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //alert("turnPage==="+turnPage.strQueryResult);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = InsuredGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

        //Ĭ�Ͻ�����ָ���һ������
        //alert("fm.InsuredGrid=="+document.all('spanInsuredGrid0').all('InsuredGridSel').checked);
        document.all('InsuredGridSel0').checked=true;
        getInsuredDetail("spanInsuredGrid0","");
//        showInsuredCodeName();

    }
}

/*********************************************************************
 *  ��ø�����ͬ�ı�������Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function getProposalInsuredInfo()
{
    //alert("ContNo=="+document.all("ContNo").value);
    var tContNo=document.all("ContNo").value;
//    arrResult=easyExecSql("select * from LCInsured where ContNo='"+tContNo+"'",1,0);
    
	var sqlid1="ContInputSql90";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	var strSql1=mySql1.getString();
	
	arrResult=easyExecSql(strSql1,1,0);
    
    if(arrResult==null||InsuredGrid.mulLineCount>1)
    {
        return;
    }
    else
    {
    	if(InsuredGrid.mulLineCount=1){
        DisplayInsured();//�ú�ͬ�µı�Ͷ������Ϣ
        var tCustomerNo = arrResult[0][2];		// �õ������˿ͻ���
        var tAddressNo = arrResult[0][10]; 		// �õ������˵�ַ��
        
									var sqlid45="ContInputSql45";
	var mySql45=new SqlClass();
	mySql45.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql45.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
    mySql45.addSubPara(tCustomerNo);
;
	var strSQL45=mySql45.getString();
	
		arrResult=easyExecSql(strSQL45,1,0);
        //arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
      }
      if(arrResult==null)
      {
          //alert("δ�õ��û���Ϣ");
          //return;
      }
      else
      {
          //displayAppnt();       //��ʾ��������ϸ����
          emptyUndefined();
          fm.InsuredAddressNo.value=tAddressNo;
          getdetailaddress2();//��ʾ�����˵�ַ��ϸ����
      }
    }
    getInsuredPolInfo();
    getImpartInfo();
    getImpartDetailInfo();
}

/*********************************************************************
 *  MulLine��RadioBox����¼�����ñ�������ϸ��Ϣ�����뱻������Ϣ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredDetail(parm1,parm2)
{
	//  ��¼����������ʱ��MulLine��RadioBox����¼�ʱ�����ѡ�еı�������ϸ��Ϣ
	var idName="InsuredGrid1r"+(InsuredGrid.getSelNo()-1);
    var InsuredNo=document.all(idName).value;
//    alert("InsuredNo="+InsuredNo);
    var ContNo = fm.ContNo.value;
    emptyInsured();//���������¼���������¸�ֵ
    //��������ϸ��Ϣ
	
	var sqlid46="ContInputSql46";
	var mySql46=new SqlClass();
	mySql46.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
    mySql46.addSubPara(InsuredNo);
	var strSQL=mySql46.getString();
	
   // var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQL);
	//������LDPerson����δ����ͻ������---����������---����Ϣ���ʴ�LCCustomerImpartParams���ͻ���֪��������ѯ
	 //��ѯ��������ͬ��,�ͻ����룬�ͻ�����<1--�ͻ�����Ϊ������>����֪����<02--������֪>����֪���<000>��
	 
	 	var sqlid47="ContInputSql47";
	var mySql47=new SqlClass();
	mySql47.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
    mySql47.addSubPara(ContNo);
	mySql47.addSubPara(InsuredNo);
	var ssql=mySql47.getString();
	 
//	 var ssql="select tt.impartparamname,tt.impartparam from LCCustomerImpartParams tt where tt.contno='"+ContNo+"' "
//		  +" and tt.customerno='"+InsuredNo+"' and tt.customernotype='1'"
//		  +" and tt.impartver='02' and tt.impartcode='000'";
	 var ssArr=easyExecSql(ssql);
	try
	{
	 arrResult[0][13]=ssArr[0][1];//���
	 arrResult[0][14]=ssArr[1][1];//����		
	}catch(ex){};
    if(arrResult!=null)
    {
        displayInsuredInfo();
    }
	
		 	var sqlid48="ContInputSql48";
	var mySql48=new SqlClass();
	mySql48.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
    mySql48.addSubPara(ContNo);
	mySql48.addSubPara(InsuredNo);
	strSQL=mySql48.getString();
	
    //strSQL ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        DisplayInsured();
    }
    var tAddressNo = arrResult[0][10]; 		// �õ������˵�ַ��
    fm.InsuredAddressNo.value=tAddressNo;
    //��ʾ�����˵�ַ��Ϣ
    getdetailaddress2();
    getInsuredPolInfo();
    getImpartInfo();
    getAge2();
    showInsuredCodeName();//��ʾ������Ŀ�ĺ�����Ϣ
    //��¼�������в���Ҫ���пͻ��ϲ�����ע�͵�  hl 20050505
    //����Ǹ���״̬��������ظ��ͻ�У��
    if(LoadFlag=="5" )
    {
      InsuredChkNew();
      return;
    }
}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ�������˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayCustomer2()
{
	try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};

}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ������ʾ�������˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayAddress2()
{
	try{document.all('PostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{document.all('ZipCode').value= arrResult[0][3]; }catch(ex){};
	try{document.all('Phone').value= arrResult[0][4]; }catch(ex){};
	try{document.all('Mobile').value= arrResult[0][14]; }catch(ex){};
	try{document.all('EMail').value= arrResult[0][16]; }catch(ex){};
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('Phone').value= arrResult[0][12]; }catch(ex){};
	try{document.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{document.all('GrpZipCode').value= arrResult[0][11]; }catch(ex){};
}
/*********************************************************************
 *  ��ʾ��������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayInsured()
{
    try{document.all('GrpContNo').value=arrResult[0][0];}catch(ex){};
    try{document.all('ContNo').value=arrResult[0][1];}catch(ex){};
    try{document.all('InsuredNo').value=arrResult[0][2];}catch(ex){};
    try{document.all('PrtNo').value=arrResult[0][3];}catch(ex){};
    try{document.all('AppntNo').value=arrResult[0][4];}catch(ex){};
    try{document.all('ManageCom').value=arrResult[0][5];}catch(ex){};
    try{document.all('ExecuteCom').value=arrResult[0][6];}catch(ex){};
    try{document.all('FamilyID').value=arrResult[0][7];}catch(ex){};
    try{document.all('RelationToMainInsured').value=arrResult[0][8];}catch(ex){};
    try{document.all('RelationToAppnt').value=arrResult[0][9];}catch(ex){};
    try{document.all('InsuredAddressNo').value=arrResult[0][10];}catch(ex){};
    try{document.all('SequenceNo').value=arrResult[0][11];}catch(ex){};
    try{document.all('Name').value=arrResult[0][12];}catch(ex){};
    try{document.all('Sex').value=arrResult[0][13];}catch(ex){};
    try{document.all('Birthday').value=arrResult[0][14];}catch(ex){};
    try{document.all('IDType').value=arrResult[0][15];}catch(ex){};
    try{document.all('IDNo').value=arrResult[0][16];}catch(ex){};
    try{document.all('NativePlace').value=arrResult[0][17];}catch(ex){};
    try{document.all('Nationality').value=arrResult[0][18];}catch(ex){};
    try{document.all('InsuredPlace').value=arrResult[0][19];}catch(ex){};
    try{document.all('Marriage').value=arrResult[0][20];}catch(ex){};
    try{document.all('MarriageDate').value=arrResult[0][21];}catch(ex){};
    try{document.all('Health').value=arrResult[0][22];}catch(ex){};
    //try{document.all('Stature').value=arrResult[0][23];}catch(ex){};
    //alert(arrResult[0][22]);
    //alert(arrResult[0][23]);
    //try{document.all('Avoirdupois').value=arrResult[0][24];}catch(ex){};
    try{document.all('Degree').value=arrResult[0][25];}catch(ex){};
    try{document.all('CreditGrade').value=arrResult[0][26];}catch(ex){};
    try{document.all('BankCode').value=arrResult[0][27];}catch(ex){};
    try{document.all('BankAccNo').value=arrResult[0][28];}catch(ex){};
    try{document.all('AccName').value=arrResult[0][29];}catch(ex){};
    try{document.all('JoinCompanyDate').value=arrResult[0][30];}catch(ex){};
    try{document.all('StartWorkDate').value=arrResult[0][31];}catch(ex){};
    try{document.all('Position').value=arrResult[0][32];}catch(ex){};
    try{document.all('Salary').value=arrResult[0][33];}catch(ex){};
    try{document.all('OccupationType').value=arrResult[0][34];}catch(ex){};
    try{document.all('OccupationCode').value=arrResult[0][35];}catch(ex){};
    try{document.all('WorkType').value=arrResult[0][36];}catch(ex){};
    try{document.all('PluralityType').value=arrResult[0][37];}catch(ex){};
    try{document.all('SmokeFlag').value=arrResult[0][38];}catch(ex){};
    try{document.all('ContPlanCode').value=arrResult[0][39];}catch(ex){};
    try{document.all('Operator').value=arrResult[0][40];}catch(ex){};
    try{document.all('MakeDate').value=arrResult[0][42];}catch(ex){};
    try{document.all('MakeTime').value=arrResult[0][43];}catch(ex){};
    try{document.all('ModifyDate').value=arrResult[0][44];}catch(ex){};
    try{document.all('ModifyTime').value=arrResult[0][45];}catch(ex){};
    try{document.all('LicenseType').value=arrResult[0][53];}catch(ex){};
    try{document.all('IDPeriodOfValidity').value=arrResult[0][64];}catch(ex){};
    try{document.all('SocialInsuFlag').value=arrResult[0][63];}catch(ex){};
    try{document.all('InsuredLanguage').value=arrResult[0][74];}catch(ex){};
}
function displayissameperson()
{
  try{fm.SequenceNo.value="1";}catch(ex){};//�ڵ�һ��¼�뱻������ʱĬ��Ϊ��1���������ˡ�
  try{fm.RelationToMainInsured.value="00";}catch(ex){};//���һ�������˹�ϵ:���������˿ͻ��ڲ���Ϊ��1���������ˡ�ʱ��Ĭ��Ϊ��00�����ˡ�
//  try{fm.RelationToAppnt.value="00";}catch(ex){};
  try{document.all('InsuredNo').value= document.all("AppntNo").value;                 }catch(ex){};
  try{document.all('Name').value= document.all("AppntName").value;                    }catch(ex){};
  try{document.all('Sex').value= document.all("AppntSex").value;                      }catch(ex){};
  try{document.all('Birthday').value= document.all("AppntBirthday").value;            }catch(ex){};
  try{document.all('IDType').value= document.all("AppntIDType").value;                }catch(ex){};
  try{document.all('IDNo').value= document.all("AppntIDNo").value;                    }catch(ex){};
  try{document.all('Password').value= document.all( "AppntPassword" ).value;          }catch(ex){};
  try{document.all('NativePlace').value= document.all("AppntNativePlace").value;      }catch(ex){};
  try{document.all('Nationality').value= document.all("AppntNationality").value;      }catch(ex){};
  try{document.all('InsuredAddressNo').value= document.all("AppntAddressNo").value;   }catch(ex){};
  try{document.all('InsuredPlace').value= document.all( "AppntPlace" ).value;      	  }catch(ex){};
  try{document.all('Marriage').value= document.all( "AppntMarriage" ).value;          }catch(ex){};
  try{document.all('MarriageDate').value= document.all( "AppntMarriageDate" ).value;  }catch(ex){};
  try{document.all('Health').value= document.all( "AppntHealth" ).value;              }catch(ex){};
  //try{document.all('Stature').value= document.all( "AppntStature" ).value;            }catch(ex){};
  //try{document.all('Avoirdupois').value= document.all( "AppntAvoirdupois" ).value;    }catch(ex){};
  try{document.all('Degree').value= document.all( "AppntDegree" ).value;              }catch(ex){};
  try{document.all('CreditGrade').value= document.all( "AppntDegreeCreditGrade" ).value;}catch(ex){};
  try{document.all('OthIDType').value= document.all( "AppntOthIDType" ).value;}catch(ex){};
  try{document.all('OthIDNo').value= document.all( "AppntOthIDNo" ).value;}catch(ex){};
  try{document.all('ICNo').value= document.all("AppntICNo").value;}catch(ex){};
  try{document.all('GrpNo').value= document.all("AppntGrpNo").value;}catch(ex){};
  try{document.all( 'JoinCompanyDate' ).value = document.all("JoinCompanyDate").value; if(document.all( 'JoinCompanyDate' ).value=="false"){document.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
  try{document.all('StartWorkDate').value= document.all("AppntStartWorkDate").value;}catch(ex){};
  try{document.all('Position').value= document.all("AppntPosition").value;}catch(ex){};
  try{document.all( 'Position' ).value = document.all("Position").value;} catch(ex) { };
  try{document.all('Salary').value= document.all("AppntSalary").value;}catch(ex){};
  try{document.all('OccupationType').value= document.all("AppntOccupationType").value;}catch(ex){};
  try{document.all('OccupationCode').value= document.all("AppntOccupationCode").value;}catch(ex){};
  try{document.all('WorkType').value= document.all("AppntWorkType").value;}catch(ex){};
  try{document.all('PluralityType').value= document.all("AppntPluralityType").value;}catch(ex){};
  try{document.all('DeathDate').value= document.all("AppntDeathDate").value;}catch(ex){};
  try{document.all('SmokeFlag').value= document.all("AppntSmokeFlag").value;}catch(ex){};
  try{document.all('BlacklistFlag').value= document.all("AppntBlacklistFlag").value;}catch(ex){};
  try{document.all('Proterty').value= document.all("AppntProterty").value;}catch(ex){};
  //try{document.all('Remark').value= document.all("AppntRemark").value;}catch(ex){};
  try{document.all('State').value= document.all("AppntState").value;}catch(ex){};
  try{document.all('Operator').value= document.all("AppntOperator").value;}catch(ex){};
  try{document.all('MakeDate').value= document.all("AppntMakeDate").value;}catch(ex){};
  try{document.all('MakeTime').value= document.all("AppntMakeTime").value;}catch(ex){};
  try{document.all('ModifyDate').value= document.all("AppntModifyDate").value;}catch(ex){};
  try{document.all('ModifyTime').value= document.all("AppntModifyTime").value;}catch(ex){};
  try{document.all('PostalAddress').value= document.all("AppntPostalAddress").value;}catch(ex){};
  try{document.all('ZIPCODE').value= document.all("AppntZipCode").value;}catch(ex){}; //ZipCode��ΪZIPCODE���idһ�� by lvshuaiqi 2016-07-12
  try{document.all('Phone').value= document.all("AppntPhone").value;}catch(ex){};
  try{document.all('Fax').value= document.all("AppntFax").value;}catch(ex){};
  try{document.all('Mobile').value= document.all("AppntMobile").value;}catch(ex){};
  try{document.all('EMail').value= document.all("AppntEMail").value;}catch(ex){};
  try{document.all('GrpName').value= document.all("AppntGrpName").value;}catch(ex){};
  try{document.all('Phone').value= document.all("AppntPhone").value;}catch(ex){};
  try{document.all('GrpAddress').value= document.all("CompanyAddress").value;}catch(ex){};
  try{document.all('GrpZipCode').value= document.all("AppntGrpZipCode").value;}catch(ex){};
  try{document.all('GrpFax').value= document.all("AppntGrpFax").value;}catch(ex){};
  try{document.all('HomeAddress').value= document.all("AppntHomeAddress").value;}catch(ex){};
  try{document.all('HomePhone').value= document.all("AppntHomePhone").value;}catch(ex){};
  try{document.all('HomeZipCode').value= document.all("AppntHomeZipCode").value;}catch(ex){};
  try{document.all('HomeFax').value= document.all("AppntHomeFax").value;}catch(ex){};
  try{document.all('RelationToAppnt').value="00";}catch(ex){};
  try{document.all('InsuredProvince').value= document.all("AppntProvince").value;}catch(ex){};
  try{document.all('InsuredCity').value= document.all("AppntCity").value;}catch(ex){};
  try{document.all('InsuredDistrict').value= document.all("AppntDistrict").value;}catch(ex){};
  try{document.all('LicenseType').value= document.all("AppntLicenseType").value;}catch(ex){};
  try{document.all('IDPeriodOfValidity').value= document.all("AppIDPeriodOfValidity").value;}catch(ex){};
  try{document.all('SocialInsuFlag').value= document.all("AppSocialInsuFlag").value;}catch(ex){};
  try{document.all('InsuredLastName').value= document.all("AppntLastName").value;}catch(ex){};
  try{document.all('InsuredFirstName').value= document.all("AppntFirstName").value;}catch(ex){};
  try{document.all('InsuredLastNameEn').value= document.all("AppntLastNameEn").value;}catch(ex){};
  try{document.all('InsuredFirstNameEn').value= document.all("AppntFirstNameEn").value;}catch(ex){};
  try{document.all('InsuredLastNamePY').value= document.all("AppntLastNamePY").value;}catch(ex){};
  try{document.all('InsuredFirstNamePY').value= document.all("AppntFirstNamePY").value;}catch(ex){};
  try{document.all('InsuredLanguage').value= document.all("AppntLanguage").value;}catch(ex){};
  /*try{document.all('IncomeWay').value= document.all("IncomeWay0").value;}catch(ex){};
  try{document.all('Income').value= document.all("Income0").value;}catch(ex){};
  try{document.all('Stature').value= document.all("AppntStature").value;}catch(ex){};
  try{document.all('Avoirdupois').value= document.all("AppntAvoirdupois").value;}catch(ex){};
  showOneCodeName('incomeway','IncomeWay','IncomeWayName');*/

/***************************
  if(document.all('Position').value=="false"){
	  document.all('Position').value="";
	}
  if(document.all('Salary').value=="false"){
  	document.all( 'Salary' ).value="";
  }
***************************/
}
/*********************************************************************
 *  ��ѯ�����˸�֪��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getImpartInfo()
{
    initImpartGrid2();
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //��֪��Ϣ��ʼ��
    if(InsuredNo!=null&&InsuredNo!="")
    {

    	/*    var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";

	    var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";

	    arrResult = easyExecSql(strSQL0,1,0);
	    arrResult1 = easyExecSql(strSQL1,1,0);



	    try{document.all('Income').value= arrResult[0][0];}catch(ex){};
	    try{document.all('IncomeWay').value= arrResult1[0][0];}catch(ex){};*/


		 	var sqlid49="ContInputSql49";
	var mySql49=new SqlClass();
	mySql49.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql49.setSqlId(sqlid49);//ָ��ʹ�õ�Sql��id
    mySql49.addSubPara(InsuredNo);
	mySql49.addSubPara(ContNo);
	  var strSQL=mySql49.getString();

//        var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='1' "
//        			+" order by impartver,impartcode";
        //+ "and (impartver<>'01' and impartcode<>'001') and (impartver<>'02' and impartcode <> '000') ";
        var arrResult;
		arrResult=easyExecSql(strSQL);
		
		var ImpartLength = 0;
		if(arrResult!=null)
		{
			ImpartLength = arrResult.length;
		}
		if(arrResult!=null)
		{  
			turnPage.queryModal(strSQL, ImpartInsuredGrid,null,null,ImpartLength);
		}
//        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
//        //�ж��Ƿ��ѯ�ɹ�
//        if (!turnPage.strQueryResult)
//        {
//            return false;
//        }
//        //��ѯ�ɹ������ַ��������ض�ά����
//        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//        //���ó�ʼ������MULTILINE����
//        turnPage.pageDisplayGrid = ImpartInsuredGrid;
//        //����SQL���
//        turnPage.strQuerySql = strSQL;
//        //���ò�ѯ��ʼλ��
//        turnPage.pageIndex = 0;
//        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
//        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
//        //����MULTILINE������ʾ��ѯ���
//        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  ��ѯ��֪��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getImpartDetailInfo()
{
    initImpartDetailGrid2();
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //��֪��Ϣ��ʼ��
    if(InsuredNo!=null&&InsuredNo!="")
    {
		
				 	var sqlid50="ContInputSql50";
	var mySql50=new SqlClass();
	mySql50.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql50.setSqlId(sqlid50);//ָ��ʹ�õ�Sql��id
    mySql50.addSubPara(InsuredNo);
	mySql50.addSubPara(ContNo);
	  var strSQL=mySql50.getString();
		
        //var strSQL ="select ImpartVer,ImpartCode,ImpartDetailContent from LCCustomerImpartDetail where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='I'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = ImpartDetailGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  ��ñ�����������Ϣ��д��MulLine
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredPolInfo()
{
    initPolGrid();
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //var SQLForInsuredNo="select InsuredNo from LCInsured where ContNo='"+ContNo+"'"
    //turnPage.strQueryResult = easyQueryVer3(SQLForInsuredNo, 1, 0, 1);
    
    //������Ϣ��ʼ��
    if(InsuredNo!=null&&InsuredNo!="")
    {
        //alert("InsuredNo=="+InsuredNo+"--ContNo=="+ContNo);
		
	var sqlid51="ContInputSql51";
	var mySql51=new SqlClass();
	mySql51.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql51.setSqlId(sqlid51);//ָ��ʹ�õ�Sql��id
    mySql51.addSubPara(InsuredNo);
	mySql51.addSubPara(ContNo);
	  var strSQL=mySql51.getString();
		
/*        var strSQL ="select a.polno,a.riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),(insuyear || a.insuyearflag),a.payyears,a.amnt,a.Mult,a.prem,"
        	+ " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='02'),0) "
        	//+" where contno='"+ContNo+"'";
        	+ " from lcpol a"
        	+" where insuredno='"+InsuredNo+"' and contno='"+ContNo+"'";
        //alert(strSQL);
*/        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = PolGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //alert(arrDataSet[0]);
        
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  MulLine��RadioBox����¼�����ñ�����������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getPolDetail(parm1,parm2)
{
	var idName="PolGrid1r"+(PolGrid.getSelNo()-1);
    var PolNo=document.all(idName).value;
    try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',PolNo);}catch(ex){};
    fm.SelPolNo.value=PolNo;
}
/*********************************************************************
 *  ���ݼ�ͥ�����ͣ����ؽ���ؼ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function choosetype(){
	if(fm.FamilyType.value=="1")
	divTempFeeInput.style.display="";
	if(fm.FamilyType.value=="0")
	divTempFeeInput.style.display="none";
}
/*********************************************************************
 *  У�鱻�����������������˹�ϵ
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function checkself()
{
	if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value=="")
	{
	  fm.RelationToMainInsured.value="00";
	  return true;
  }
	else if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value!="00")
	{
	  alert("���˵���'�����������˹�ϵ'ֻ����'����'");
	  fm.RelationToMainInsured.value="00";
	  return false;
  }
	else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value==""&&InsuredGrid.mulLineCount==0)
	{
	  fm.RelationToMainInsured.value="00";
	  return true;
  }
  else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value!="00"&&InsuredGrid.mulLineCount==0)
  {
	  alert("��ͥ���е�һλ�������˵�'�����������˹�ϵ'ֻ����'����'");
	  fm.RelationToMainInsured.value="00";
	  return false;
  }
  else{
    return true;
  }
}
/*********************************************************************
 *  У�鱣����
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function checkrelation()
{
	if(LoadFlag==2||LoadFlag==7)
	{
        if (document.all('ContNo').value != "")
        {
            alert("�ŵ��ĸ��������ж౻������");
            return false;
        }
        else
            return true;
    }
    else
    {
        if (document.all('ContNo').value != ""&&fm.FamilyType.value=="0")
        {
			
				var sqlid52="ContInputSql52";
	var mySql52=new SqlClass();
	mySql52.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql52.setSqlId(sqlid52);//ָ��ʹ�õ�Sql��id
    mySql52.addSubPara(document.all('ContNo').value);
	  var strSQL=mySql52.getString();
			
           // var strSQL="select * from LCInsured where contno='"+document.all('ContNo').value +"'";
            turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
            if(turnPage.strQueryResult)
            {
                alert("���������ж౻������");
                return false;
            }
            else
                return true;
        }
       /** else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&InsuredGrid.mulLineCount>0&&fm.RelationToMainInsured.value=="00")
        {
            alert("��ͥ��ֻ����һ������������");
            return false;
        }
        ***/
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&fm.RelationToAppnt.value=="00")
        {
			
	var sqlid53="ContInputSql53";
	var mySql53=new SqlClass();
	mySql53.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql53.setSqlId(sqlid53);//ָ��ʹ�õ�Sql��id
    mySql53.addSubPara(document.all('ContNo').value);
	 var strSql=mySql53.getString();
			
           // var strSql="select * from LCInsured where contno='"+document.all('ContNo').value +"' and RelationToAppnt='00' ";
            turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
            if(turnPage.strQueryResult)
		    {
                alert("Ͷ�����Ѿ��Ǹú�ͬ���µı�������");
                return false;
            }
		    else
		        return true;
        }
        else
            return true;
    }
	//select count(*) from ldinsured

}
/*********************************************************************
 *  Ͷ�����뱻������ͬѡ����¼�
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function isSamePerson()
{
    //��Ӧδѡͬһ�ˣ��ִ򹳵����
     //��Ӧδѡͬһ�ˣ��ִ򹳵����
    if (fm.RelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) 
    {
      document.all('DivLCInsured').style.display = "";
      fm.SamePersonFlag.checked = false;
      alert("Ͷ�����뱻���˹�ϵ���Ǳ��ˣ����ܽ��иò�����");
    }
    
    if ( fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
    {
      DivLCInsured.style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";
      fm.SamePersonFlag.checked = true;
      fm.RelationToAppnt.value="00"
      displayissameperson();
    }
    //��Ӧ��ͬһ�ˣ��ִ򹳵����
    else if (fm.SamePersonFlag.checked == true)
    {
      document.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";

      displayissameperson();
    }
    //��Ӧ��ѡͬһ�˵����
    else if (fm.SamePersonFlag.checked == false)
    {
      document.all('DivLCInsured').style.display = "";
      divLCInsuredPerson.style.display = "";
      divSalary.style.display = "";
      if(InsuredGrid.mulLineCount==0)
	  {
		fm.SequenceNo.value="1";//�ڵ�һ��¼�뱻������ʱĬ��Ϊ��1���������ˡ�
		fm.RelationToMainInsured.value="00";//���һ�������˹�ϵ:���������˿ͻ��ڲ���Ϊ��1���������ˡ�ʱ��Ĭ��Ϊ��00�����ˡ�
	  }
	  else
	  {
	   try{document.all('SequenceNo').value=""; }catch(ex){};
       try{document.all('RelationToMainInsured').value=""; }catch(ex){};
	  }
      try{document.all('InsuredNo').value=""; }catch(ex){};
      try{document.all('Name').value=""; }catch(ex){};
      try{document.all('Sex').value= ""; }catch(ex){};
      try{document.all('Birthday').value= ""; }catch(ex){};
      try{document.all('IDType').value= "0"; }catch(ex){};
      try{document.all('IDNo').value= ""; }catch(ex){};
      try{document.all('Password').value= ""; }catch(ex){};
      try{document.all('NativePlace').value= ""; }catch(ex){};
      try{document.all('Nationality').value=""; }catch(ex){};
      try{document.all('InsuredPlace').value= ""; }catch(ex){};
      try{document.all('Marriage').value= "";}catch(ex){};
      try{document.all('MarriageDate').value= "";}catch(ex){};
      try{document.all('Health').value= "";}catch(ex){};
      /*try{document.all('Stature').value= "";}catch(ex){};
      try{document.all('Avoirdupois').value= "";}catch(ex){};*/
      try{document.all('Degree').value= "";}catch(ex){};
      try{document.all('CreditGrade').value= "";}catch(ex){};
      try{document.all('OthIDType').value= "";}catch(ex){};
      try{document.all('OthIDNo').value= "";}catch(ex){};
      try{document.all('ICNo').value="";}catch(ex){};
      try{document.all('GrpNo').value= "";}catch(ex){};
      try{document.all('JoinCompanyDate').value= "";}catch(ex){}
      try{document.all('StartWorkDate').value= "";}catch(ex){};
      try{document.all('Position').value= "";}catch(ex){};
      try{document.all('Salary').value= "";}catch(ex){};
      try{document.all('OccupationType').value= "";}catch(ex){};
      try{document.all('OccupationCode').value= "";}catch(ex){};
      try{document.all('WorkType').value= "";}catch(ex){};
      try{document.all('PluralityType').value= "";}catch(ex){};
      try{document.all('DeathDate').value= "";}catch(ex){};
      try{document.all('SmokeFlag').value= "";}catch(ex){};
      try{document.all('BlacklistFlag').value= "";}catch(ex){};
      try{document.all('Proterty').value= "";}catch(ex){};
      //try{document.all('Remark').value= "";}catch(ex){};
      try{document.all('State').value= "";}catch(ex){};
      try{document.all('Operator').value= "";}catch(ex){};
      try{document.all('MakeDate').value= "";}catch(ex){};
      try{document.all('MakeTime').value="";}catch(ex){};
      try{document.all('ModifyDate').value= "";}catch(ex){};
      try{document.all('ModifyTime').value= "";}catch(ex){};
      try{document.all('PostalAddress').value= "";}catch(ex){};
      try{document.all('PostalAddress').value= "";}catch(ex){};
      try{document.all('ZIPCODE').value= "";}catch(ex){};//ZipCode��ΪZIPCODE���idһ�� by lvshuaiqi 2016-07-12
      try{document.all('Phone').value= "";}catch(ex){};
      try{document.all('Mobile').value= "";}catch(ex){};
      try{document.all('EMail').value="";}catch(ex){};
      try{document.all('GrpName').value= "";}catch(ex){};
      try{document.all('Phone').value= "";}catch(ex){};
      try{document.all('GrpAddress').value="";}catch(ex){};
      try{document.all('GrpZipCode').value= "";}catch(ex){};
      try{document.all('RelationToAppnt').value= "";}catch(ex){};
      try{document.all('Fax').value= "";}catch(ex){};
      try{document.all('HomePhone').value= "";}catch(ex){};
      try{document.all('IDPeriodOfValidity').value= "";}catch(ex){};
      try{document.all('SocialInsuFlag').value= "";}catch(ex){};
      try{document.all('InsuredLastName').value= "";}catch(ex){};
	  try{document.all('InsuredFirstName').value= "";}catch(ex){};
  	  try{document.all('InsuredLastNameEn').value= "";}catch(ex){};
  	  try{document.all('InsuredFirstNameEn').value= "";}catch(ex){};
      try{document.all('InsuredLastNamePY').value= "";}catch(ex){};
      try{document.all('InsuredFirstNamePY').value= "";}catch(ex){};
     /* try{document.all('IncomeWay').value=  "";}catch(ex){};
      try{document.all('Income').value=  "";}catch(ex){};*/

    }
}
/*********************************************************************
 *  Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryInsuredNo()
{
    if (document.all("InsuredNo").value == "")
    {
      //showAppnt1();
      showInsured1();
    }
    else
    {
		
			var sqlid54="ContInputSql46";
	var mySql54=new SqlClass();
	mySql54.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql54.setSqlId(sqlid54);//ָ��ʹ�õ�Sql��id
    mySql54.addSubPara(document.all('InsuredNo').value);
	 var strSql=mySql54.getString();
		
		arrResult = easyExecSql(strSql, 1, 0);
		
    	//arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
        if (arrResult == null)
        {
          alert("δ�鵽��������Ϣ");
          displayInsuredInfo(new Array());
          emptyUndefined();
        }
        else
        {
          //displayAppnt(arrResult[0]);
          displayInsuredInfo(arrResult[0]);
          showInsuredCodeName();
        }
        
    }
}
/*********************************************************************
 *  Ͷ���˲�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
//function showAppnt1()
function showInsured1()
{
	if( mOperate == 0 )
	{
		//mOperate = 2;
		mOperate = 3;  //��������Ϣ��ѯ  mOperate = 3; hl20050503
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryInsured" ,"",sFeatures);
	}
}
/*********************************************************************
 *  ��ʾͶ������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 //hanlin 20050503
//function displayAppnt()
function displayInsuredInfo()
{
	  //alert("asdfasfsf");
    try{document.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};
    try{document.all('Name').value= arrResult[0][1]; }catch(ex){};
    try{document.all('Sex').value= arrResult[0][2]; }catch(ex){};
    try{document.all('Birthday').value= arrResult[0][3]; }catch(ex){};
    try{document.all('IDType').value= arrResult[0][4]; }catch(ex){};
    try{document.all('IDNo').value= arrResult[0][5]; }catch(ex){};
    try{document.all('Password').value= arrResult[0][6]; }catch(ex){};
    try{document.all('NativePlace').value= arrResult[0][7]; }catch(ex){};
    try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};
    try{document.all('InsuredPlace').value= arrResult[0][9]; }catch(ex){};
    try{document.all('Marriage').value= arrResult[0][10];}catch(ex){};
    try{document.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try{document.all('Health').value= arrResult[0][12];}catch(ex){};
   /* if(arrResult[0][13]!=null&&arrResult[0][13]!=0){
      try{document.all('Stature').value= arrResult[0][13];}catch(ex){};
    }
    if(arrResult[0][14]!=null&&arrResult[0][14]!=0){
      try{document.all('Avoirdupois').value= arrResult[0][14];}catch(ex){};
    }*/
    try{document.all('Degree').value= arrResult[0][15];}catch(ex){};
    try{document.all('CreditGrade').value= arrResult[0][16];}catch(ex){};
    try{document.all('OthIDType').value= arrResult[0][17];}catch(ex){};
    try{document.all('OthIDNo').value= arrResult[0][18];}catch(ex){};
    try{document.all('ICNo').value= arrResult[0][19];}catch(ex){};
    try{document.all('GrpNo').value= arrResult[0][20];}catch(ex){};
    try{document.all('JoinCompanyDate').value= arrResult[0][21];}catch(ex){};
    try{document.all('StartWorkDate').value= arrResult[0][22];}catch(ex){};
    try{document.all('Position').value= arrResult[0][23];}catch(ex){};
    try{document.all('Salary').value= arrResult[0][24];}catch(ex){};
    try{document.all('OccupationType').value= arrResult[0][25];}catch(ex){};
    try{document.all('OccupationCode').value= arrResult[0][26];}catch(ex){};
    try{document.all('WorkType').value= arrResult[0][27];}catch(ex){};
    try{document.all('PluralityType').value= arrResult[0][28];}catch(ex){};
    try{document.all('DeathDate').value= arrResult[0][29];}catch(ex){};
    try{document.all('SmokeFlag').value= arrResult[0][30];}catch(ex){};
    try{document.all('BlacklistFlag').value= arrResult[0][31];}catch(ex){};
    try{document.all('Proterty').value= arrResult[0][32];}catch(ex){};
    //try{document.all('Remark').value= arrResult[0][33];}catch(ex){};
    try{document.all('State').value= arrResult[0][34];}catch(ex){};
    try{document.all('VIPValue1').value= arrResult[0][35];}catch(ex){};
    try{document.all('Operator').value= arrResult[0][36];}catch(ex){};
    try{document.all('MakeDate').value= arrResult[0][37];}catch(ex){};
    try{document.all('MakeTime').value= arrResult[0][38];}catch(ex){};
    try{document.all('ModifyDate').value= arrResult[0][39];}catch(ex){};
    try{document.all('ModifyTime').value= arrResult[0][40];}catch(ex){};
    try{document.all('LicenseType').value= arrResult[0][43];}catch(ex){};
    try{document.all('GrpName').value= arrResult[0][41];}catch(ex){};
    try{document.all('SocialInsuFlag').value= arrResult[0][46];}catch(ex){};
    try{document.all('IDPeriodOfValidity').value= arrResult[0][47];}catch(ex){};
    
	try{document.all('InsuredLastName').value= arrResult[0][48];}catch(ex){};
	try{document.all('InsuredFirstName').value= arrResult[0][49];}catch(ex){};
	try{document.all('InsuredLastNameEn').value= arrResult[0][50];}catch(ex){};
	try{document.all('InsuredFirstNameEn').value= arrResult[0][51];}catch(ex){};
	try{document.all('InsuredLastNamePY').value= arrResult[0][53];}catch(ex){};
	try{document.all('InsuredFirstNamePY').value= arrResult[0][54];}catch(ex){};


    //��ַ��ʾ���ֵı䶯
    try{document.all('InsuredAddressNo').value= "";}catch(ex){};
    try{document.all('PostalAddress').value=  "";}catch(ex){};
    try{document.all('ZipCode').value=  "";}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
    try{document.all('Mobile').value=  "";}catch(ex){};
    try{document.all('EMail').value=  "";}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
    try{document.all('GrpAddress').value=  "";}catch(ex){};
    try{document.all('GrpZipCode').value=  "";}catch(ex){};
}
/*********************************************************************
 *  ��ѯ���غ󴥷�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery21( arrQueryResult )
{
  //alert("1111here:" + arrQueryResult + "\n" + mOperate);

    if( arrQueryResult != null )
    {
        arrResult = arrQueryResult;

        if( mOperate == 1 )
        {		// ��ѯͶ����
            document.all( 'ContNo' ).value = arrQueryResult[0][0];

			var sqlid56="ContInputSql56";
	var mySql56=new SqlClass();
	mySql56.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql56.setSqlId(sqlid56);//ָ��ʹ�õ�Sql��id
    mySql56.addSubPara(arrQueryResult[0][0] );
	 var strSql56=mySql56.getString();
	 
      arrResult = easyExecSql(strSql56, 1, 0);
           // arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);

            if (arrResult == null)
            {
                alert("δ�鵽Ͷ������Ϣ");
            }
            else
            {
                displayLCContPol(arrResult[0]);
            }
        }

        if( mOperate == 3 )
        {		// ��������Ϣ
        	//arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	
						var sqlid57="ContInputSql57";
	var mySql57=new SqlClass();
	mySql57.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql57.setSqlId(sqlid57);//ָ��ʹ�õ�Sql��id
    mySql57.addSubPara(arrQueryResult[0][0] );
	 var strSql57=mySql57.getString();
	 
			arrResult = easyExecSql(strSql57, 1, 0);
			//arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	if (arrResult == null)
        	{
        	    alert("δ�鵽Ͷ������Ϣ");
        	}
        	else
        	{
        		 //hl 20050503
        	   // displayAppnt(arrResult[0]);
        	   //alert("arrResult[0][35]=="+arrResult[0][35]);
        	   displayInsuredInfo(arrResult[0]);
        	  // showCodeName();
        	}
        }
    }

	mOperate = 0;		// �ָ���̬
	showCodeName();
}
/*********************************************************************
 *  ��ѯְҵ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getdetailwork2()
{
	
							var sqlid58="ContInputSql58";
	var mySql58=new SqlClass();
	mySql58.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql58.setSqlId(sqlid58);//ָ��ʹ�õ�Sql��id
    mySql58.addSubPara(fm.OccupationCode.value );
	 var strSql=mySql58.getString();
	
//    var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null)
    {
        fm.OccupationType.value = arrResult[0][0];
        showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
    }
    else
    {
        fm.OccupationType.value ="";
        fm.OccupationTypeName.value = "";
    }
}


/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��(����)
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm2(wFlag)
{
	//alert("LoadFlag=="+LoadFlag);


    if (wFlag ==1 ) //¼�����ȷ��
    {
		
									var sqlid59="ContInputSql59";
	var mySql59=new SqlClass();
	mySql59.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql59.setSqlId(sqlid59);//ָ��ʹ�õ�Sql��id
    mySql59.addSubPara(fm.ContNo.value );
	 var tStr=mySql59.getString();
	
		
        //var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult)
        {
		    alert("�ú�ͬ�Ѿ��������棡");
		    return;
		}
		fm.AppntNo.value = AppntNo;
		fm.AppntName.value = AppntName;
		fm.WorkFlowFlag.value = "7999999999";
    }
    else if (wFlag ==2)//�������ȷ��
    {
  	    if(document.all('ProposalContNo').value == "")
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
            return;
        }
		fm.WorkFlowFlag.value = "0000001001";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
    }
    else if (wFlag ==3)
    {
        if(document.all('ProposalContNo').value == "")
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
            return;
        }
		fm.WorkFlowFlag.value = "0000001002";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else
		return;

    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    fm.action = "./InputConfirm.jsp";
    document.getElementById("fm").submit(); //�ύ
}
/*********************************************************************
 *  ��ѯ����������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getdetailaddress2()
{
    //alert("abc");
	
	var sqlid60="ContInputSql60";
	var mySql60=new SqlClass();
	mySql60.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql60.setSqlId(sqlid60);//ָ��ʹ�õ�Sql��id
    mySql60.addSubPara(fm.InsuredAddressNo.value );
    mySql60.addSubPara(fm.InsuredNo.value );
	 var strSQL=mySql60.getString();
	
   // var strSQL="select b.AddressNo,b.postaladdress,b.zipcode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.fax,b.HomePhone,b.grpName,b.province,b.city,b.County from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
    arrResult=easyExecSql(strSQL);
    try{document.all('InsuredAddressNo').value= arrResult[0][0];}catch(ex){};
    try{document.all('InsuredAddressNoName').value= arrResult[0][1];}catch(ex){};
    try{document.all('PostalAddress').value= arrResult[0][1];}catch(ex){};
    try{document.all('ZIPCODE').value= arrResult[0][2];}catch(ex){}; //ZipCode ��Ϊ ZIPCODE �����id��һ��   zhang ying feng  2016-05-11
    try{document.all('Phone').value= arrResult[0][3];}catch(ex){};
    try{document.all('Mobile').value= arrResult[0][4];}catch(ex){};
    try{document.all('EMail').value= arrResult[0][5];}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
   // try{document.all('Phone').value= arrResult[0][4];}catch(ex){};
    try{document.all('GrpAddress').value= arrResult[0][7];}catch(ex){};
    try{document.all('GrpZipCode').value= arrResult[0][8];}catch(ex){};
    try{document.all('Fax').value= arrResult[0][9];}catch(ex){};
    try{document.all('HomePhone').value= arrResult[0][10];}catch(ex){};
    //alert("arrResult[0][11]=="+arrResult[0][11]);
    try{document.all('GrpName').value= arrResult[0][11];}catch(ex){};
    //alert("fm.GrpName="+fm.GrpName.value);
   try{document.all('InsuredProvince').value= arrResult[0][12];}catch(ex){};
   try{document.all('InsuredCity').value= arrResult[0][13];}catch(ex){};
   try{document.all('InsuredDistrict').value= arrResult[0][14];}catch(ex){};
//   showOneCodeName('Province','InsuredProvince','InsuredProvinceName');
//   showOneCodeName('City','InsuredCity','InsuredCityName');
//   showOneCodeName('District','InsuredDistrict','InsuredDistrictName');
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName');
}
/*********************************************************************
 *  ��ѯ���ռƻ�
 *  ����  ��  �����ͬͶ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function getContPlanCode(tProposalGrpContNo)
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
	
		var sqlid61="ContInputSql61";
	var mySql61=new SqlClass();
	mySql61.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql61.setSqlId(sqlid61);//ָ��ʹ�õ�Sql��id
    mySql61.addSubPara(tProposalGrpContNo);
	strsql=mySql61.getString();
	
   // strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    	 divContPlan.style.display="";
    }
    else
    {
      //alert("���ռƻ�û�鵽");
        divContPlan.style.display="none";
    }
    //alert ("tcodedata : " + tCodeData);
    return tCodeData;
}

/*********************************************************************
 *  ��ѯ�������
 *  ����  ��  �����ͬͶ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function getExecuteCom(tProposalGrpContNo)
{
    	//alert("1");
    var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	//strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='"+tProposalGrpContNo+"' and a.ExecuteCom=b.ComCode";
	
	var sqlid62="ContInputSql62";
	var mySql62=new SqlClass();
	mySql62.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql62.setSqlId(sqlid62);//ָ��ʹ�õ�Sql��id
    mySql62.addSubPara(tProposalGrpContNo);
	strsql=mySql62.getString();
	
	//alert("strsql :" + strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
		divExecuteCom.style.display="";
	}
	else
	{
	    divExecuteCom.style.display="none";
    }
	//alert ("tcodedata : " + tCodeData);

	return tCodeData;
}
/*********************************************************************
 *  ���Ͷ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 **********************************************************************/
function emptyAppnt()
{
        try{document.all('AppntVIPValue').value= ""; }catch(ex){};
        try{document.all('AppntVIPFlagname').value= ""; }catch(ex){};
        try{document.all('AppntName').value= ""; }catch(ex){};
        try{document.all('AppntIDNo').value= ""; }catch(ex){};
        try{document.all('AppntSex').value= ""; }catch(ex){};
        try{document.all('AppntSexName').value= ""; }catch(ex){};
        try{document.all('AppntBirthday').value= ""; }catch(ex){};
        try{document.all('AppntAge').value= ""; }catch(ex){};
        try{document.all('AppntMarriage').value= ""; }catch(ex){};
        try{document.all('AppntMarriageName').value= ""; }catch(ex){};
        try{document.all('AppntNativePlace').value= ""; }catch(ex){};
        try{document.all('AppntNativePlaceName').value= ""; }catch(ex){};
        try{document.all('AppntOccupationCode').value= ""; }catch(ex){};
        try{document.all('AppntOccupationCodeName').value= ""; }catch(ex){};
        try{document.all('AppntLicenseType').value= ""; }catch(ex){};
        try{document.all('AppntLicenseTypeName').value= ""; }catch(ex){};
        try{document.all('AppntAddressNo').value= ""; }catch(ex){};
        try{document.all('AddressNoName').value= ""; }catch(ex){};
        try{document.all('AppntProvince').value= ""; }catch(ex){};
        try{document.all('AppntProvinceName').value= ""; }catch(ex){};
        try{document.all('AppntCity').value= ""; }catch(ex){};
        try{document.all('AppntCityName').value= ""; }catch(ex){};
        try{document.all('AppntDistrict').value= ""; }catch(ex){};
        try{document.all('AppntDistrictName').value= ""; }catch(ex){};
        try{document.all('AppntPostalAddress').value= ""; }catch(ex){};
        try{document.all('AppntZipCode').value= ""; }catch(ex){};
        try{document.all('AppntMobile').value= ""; }catch(ex){};
        try{document.all('AppntPhone').value= ""; }catch(ex){};
        try{document.all('AppntFax').value= ""; }catch(ex){};
        try{document.all('AppntHomePhone').value= ""; }catch(ex){};
        try{document.all('AppntGrpName').value= ""; }catch(ex){};
        try{document.all('AppntEMail').value= ""; }catch(ex){};
        try{document.all('PayMode').value= ""; }catch(ex){};
        try{document.all('PayModeName').value= ""; }catch(ex){};
        try{document.all('AppntBankCode').value= ""; }catch(ex){};
        try{document.all('FirstBankCodeName').value= ""; }catch(ex){};
        try{document.all('AppntAccName').value= ""; }catch(ex){};
        try{document.all('AppntBankAccNo').value= ""; }catch(ex){};
        try{document.all('SecPayMode').value= ""; }catch(ex){};
        try{document.all('SecPayModeName').value= ""; }catch(ex){};
        try{document.all('SecAppntBankCode').value= ""; }catch(ex){};
        try{document.all('AppntBankCodeName').value= ""; }catch(ex){};
        try{document.all('SecAppntAccName').value= ""; }catch(ex){};
        try{document.all('SecAppntBankAccNo').value= ""; }catch(ex){};
        try{document.all('AppSocialInsuFlag').value= ""; }catch(ex){};
        try{document.all('AppIDPeriodOfValidity').value= ""; }catch(ex){};
        //try{document.all('Income0').value= ""; }catch(ex){};
        //try{document.all('IncomeWay0').value= ""; }catch(ex){};
       // try{document.all('IncomeWayName0').value= ""; }catch(ex){};
        ImpartGrid.clearData();

}
function emptyInsured()
{

	try{document.all('InsuredNo').value= ""; }catch(ex){};
	try{document.all('VIPValue1').value= ""; }catch(ex){};
	try{document.all('AppntVIPFlagname1').value= ""; }catch(ex){};
	try{document.all('SequenceNo').value= ""; }catch(ex){};
	try{document.all('ExecuteCom').value= ""; }catch(ex){};
	try{document.all('FamilyID').value= ""; }catch(ex){};
	try{document.all('RelationToMainInsured').value= ""; }catch(ex){};
	try{document.all('RelationToAppnt').value= ""; }catch(ex){};
	try{document.all('RelationToAppntName').value= ""; }catch(ex){};
	try{document.all('InsuredAddressNo').value= ""; }catch(ex){};
	//try{document.all('SequenceNo').value= ""; }catch(ex){};
	try{document.all('Name').value= ""; }catch(ex){};
	try{document.all('Sex').value= ""; }catch(ex){};
	try{document.all('SexName').value= ""; }catch(ex){};
	try{document.all('RelationToMainInsuredName').value= ""; }catch(ex){};
	try{document.all('SequenceNoName').value= ""; }catch(ex){};
	try{document.all('Birthday').value= ""; }catch(ex){};
	try{document.all('InsuredAppAge').value= ""; }catch(ex){};
	try{document.all('IDType').value= ""; }catch(ex){};
	try{document.all('IDNo').value= ""; }catch(ex){};
	//try{document.all('IncomeWayName').value= ""; }catch(ex){};
	try{document.all('InsuredProvince').value= ""; }catch(ex){};
	try{document.all('InsuredProvinceName').value= ""; }catch(ex){};
	try{document.all('InsuredCity').value= ""; }catch(ex){};
	try{document.all('InsuredCityName').value= ""; }catch(ex){};
	try{document.all('InsuredDistrict').value= ""; }catch(ex){};
	try{document.all('InsuredDistrictName').value= ""; }catch(ex){};
	//try{document.all('Income').value= ""; }catch(ex){};
	try{document.all('LicenseType').value= ""; }catch(ex){};
	try{document.all('LicenseTypeName').value= ""; }catch(ex){};
	try{document.all('IDTypeName').value= ""; }catch(ex){};
	try{document.all('InsuredAddressNoName').value= ""; }catch(ex){};
	//try{document.all('IncomeWay').value= ""; }catch(ex){};
	try{document.all('NativePlace').value= ""; }catch(ex){};
	 try{document.all('NativePlaceName').value= ""; }catch(ex){};
	try{document.all('Nationality').value= ""; }catch(ex){};
	try{document.all('InsuredPlace').value= ""; }catch(ex){};
	try{document.all('Marriage').value= ""; }catch(ex){};
	try{document.all('MarriageName').value= ""; }catch(ex){};
	try{document.all('MarriageDate').value= ""; }catch(ex){};
	try{document.all('Health').value= ""; }catch(ex){};
	/*try{document.all('Stature').value= ""; }catch(ex){};
	try{document.all('Avoirdupois').value= ""; }catch(ex){};*/
	try{document.all('Degree').value= ""; }catch(ex){};
	try{document.all('CreditGrade').value= ""; }catch(ex){};
	try{document.all('BankCode').value= ""; }catch(ex){};
	try{document.all('BankAccNo').value= ""; }catch(ex){};
	try{document.all('AccName').value= ""; }catch(ex){};
	try{document.all('JoinCompanyDate').value= ""; }catch(ex){};
	try{document.all('StartWorkDate').value= ""; }catch(ex){};
	try{document.all('Position').value= ""; }catch(ex){};
	try{document.all('Salary').value= ""; }catch(ex){};
	try{document.all('OccupationType').value= ""; }catch(ex){};
	try{document.all('OccupationTypeName').value= ""; }catch(ex){};
	try{document.all('OccupationCodeName').value= ""; }catch(ex){};
	try{document.all('OccupationCode').value= ""; }catch(ex){};
	try{document.all('WorkType').value= ""; }catch(ex){};
	try{document.all('PluralityType').value= ""; }catch(ex){};
	try{document.all('SmokeFlag').value= ""; }catch(ex){};
	try{document.all('ContPlanCode').value= ""; }catch(ex){};
  try{document.all('GrpName').value= ""; }catch(ex){};
  try{document.all('HomeAddress').value= ""; }catch(ex){};
  try{document.all('HomeZipCode').value= ""; }catch(ex){};
  try{document.all('HomePhone').value= ""; }catch(ex){};
  try{document.all('HomeFax').value= ""; }catch(ex){};
  try{document.all('GrpFax').value= ""; }catch(ex){};
  try{document.all('Fax').value= ""; }catch(ex){};
  try{document.all('IDPeriodOfValidity').value= ""; }catch(ex){};
  try{document.all('SocialInsuFlag').value= ""; }catch(ex){};
    try{document.all('InsuredLastName').value= "";}catch(ex){};
	try{document.all('InsuredFirstName').value= "";}catch(ex){};
  	try{document.all('InsuredLastNameEn').value= "";}catch(ex){};
  	try{document.all('InsuredFirstNameEn').value= "";}catch(ex){};
    try{document.all('InsuredLastNamePY').value= "";}catch(ex){};
    try{document.all('InsuredFirstNamePY').value= "";}catch(ex){};
	emptyAddress();
	ImpartInsuredGrid.clearData();
	//ImpartDetailGrid.clearData();
	//ImpartDetailGrid.addOne();
}

/*********************************************************************
 *  ��տͻ���ַ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function emptyAddress()
{
	try{document.all('PostalAddress').value= "";  }catch(ex){};
	try{document.all('ZipCode').value= "";  }catch(ex){};
	try{document.all('Phone').value= "";  }catch(ex){};
	try{document.all('Mobile').value= "";  }catch(ex){};
	try{document.all('EMail').value= "";  }catch(ex){};
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('Phone').value= "";  }catch(ex){};
	try{document.all('GrpAddress').value= ""; }catch(ex){};
	try{document.all('GrpZipCode').value= "";  }catch(ex){};
}
/*********************************************************************
 *  �������֤��ȡ�ó������ں��Ա�
 *  ����  ��  ���֤��
 *  ����ֵ��  ��
 *********************************************************************
 */

function getBirthdaySexByIDNo2(iIdNo)
{
	//alert("aafsd");
	if(document.all('IDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		{
			alert("��������֤��λ������");
			theFirstValue="";
			theSecondValue="";
			//document.all('IDNo').focus();
			return;

		}
		else
		{
			document.all('Birthday').value=tBirthday;
			document.all('Sex').value=tSex;
		}

	}
}
/*********************************************************************
 *  ��ͬ��Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function GrpInputConfirm(wFlag)
{
	mWFlag = 1;
	if (wFlag ==1 ) //¼�����ȷ��
	{
		
			var sqlid63="ContInputSql63";
	var mySql63=new SqlClass();
	mySql63.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql63.setSqlId(sqlid63);//ָ��ʹ�õ�Sql��id
    mySql63.addSubPara(fm.ProposalGrpContNo.value);
	var tStr=mySql63.getString();
		
//	    var tStr= "	select * from lwmission where 1=1 "
//	    					+" and lwmission.processid = '0000000004'"
//	    					+" and lwmission.activityid = '0000002001'"
//	    					+" and lwmission.missionprop1 = '"+fm.ProposalGrpContNo.value+"'";
	    turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
	    if (turnPage.strQueryResult)
	    {
	        alert("���ŵ���ͬ�Ѿ��������棡");
	        return;
	    }
		if(document.all('ProposalGrpContNo').value == "")
	    {
	        alert("�ŵ���ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
	        return;
	    }
		fm.WorkFlowFlag.value = "6999999999";			//¼�����
    }
    else if (wFlag ==2)//�������ȷ��
    {
        if(document.all('ProposalGrpContNo').value == "")
	    {
	        alert("δ��ѯ���ŵ���ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000002002";					//�������
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if (wFlag ==3)
    {
  	    if(document.all('ProposalGrpContNo').value == "")
	    {
	        alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000001002";					//�����޸����
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if(wFlag == 4)
	{
		if(document.all('ProposalGrpContNo').value == "")
	    {
	       alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
	       return;
	    }
	    fm.WorkFlowFlag.value = "0000001021";					//�����޸�
	    fm.MissionID.value = MissionID;
	    fm.SubMissionID.value = SubMissionID;
	}
	else
		return;

    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	fm.action = "./GrpInputConfirm.jsp";
    document.getElementById("fm").submit(); //�ύ
}
function getaddresscodedata2()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    if(fm.InsuredAddressNo.value=="")
    {
    	fm.PostalAddress.value="";
    	fm.InsuredAddressNoName.value="";
    	fm.InsuredProvinceName.value="";
    	fm.InsuredProvince.value="";
     	fm.InsuredCityName.value="";
    	fm.InsuredCity.value="";
    	fm.InsuredDistrictName.value="";
    	fm.InsuredDistrict.value="";
    	fm.PostalAddress.value="";
    	fm.ZipCode.value="";
      fm.Mobile.value="";
     	fm.Phone.value="";
      fm.Fax.value="";
    	fm.HomePhone.value="";
    	fm.EMail.value="";
    	}
		
					var sqlid64="ContInputSql64";
	var mySql64=new SqlClass();
	mySql64.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql64.setSqlId(sqlid64);//ָ��ʹ�õ�Sql��id
    mySql64.addSubPara(fm.InsuredNo.value);
	strsql=mySql64.getString();
		
//    strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.InsuredNo.value+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert ("tcodedata : " + tCodeData);
    //return tCodeData;
    document.all("InsuredAddressNo").CodeData=tCodeData;
}

function getImpartCode(parm1,parm2){
  //alert("hehe:"+document.all(parm1).all('ImpartGrid1').value);
  var impartVer=document.all(parm1).all('ImpartGrid1').value;
  window.open("../app/ImpartCodeSel.jsp?ImpartVer="+impartVer,"",sFeatures);
}
function checkidtype2()
{
	//alert("sdasf");
	if(fm.IDType.value=="")
	{
		alert("����ѡ��֤�����ͣ�");
	//	fm.IDNo.value="";
        }
}
function getallinfo()
{
 	if(fm.InsuredLastName.value!=""&&fm.InsuredFirstName.value != "" &&fm.IDType.value!=""&&fm.IDNo.value!="")
 	{
	fm.Name.value = fm.InsuredLastName.value + fm.InsuredFirstName.value;
	var sqlid65="ContInputSql65";
	var mySql65=new SqlClass();
	mySql65.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql65.setSqlId(sqlid65);//ָ��ʹ�õ�Sql��id
    mySql65.addSubPara(fm.Name.value);
	mySql65.addSubPara(fm.IDType.value);
	mySql65.addSubPara(fm.IDNo.value);
	strSQL=mySql65.getString();
		
//	    strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
//                +"  and Name='"+fm.Name.value
//                +"' and IDType='"+fm.IDType.value
//                +"' and IDNo='"+fm.IDNo.value
//		+"' order by a.CustomerNo";
             turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
             if (turnPage.strQueryResult != "")
             {
 		  mOperate = 2;
 		  window.open("../sys/LDPersonQueryAll.html?Name="+fm.Name.value+"&IDType="+fm.IDType.value+"&IDNo="+fm.IDNo.value,"newwindow","height=10,width=1090,top=180,left=180, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no");
 	     }
 	     else
 	     return;
 	}
}
function DelRiskInfo()
{
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	if(fm.InsuredNo.value=="")
	{
		alert("����ѡ�񱻱���");
		return false;
	}
	var tSel =PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert("�ÿͻ�û�����ֻ���������ѡ���ˣ�");
		return false;
	}
	var tRow = PolGrid.getSelNo() - 1;
	var tpolno=PolGrid.getRowColData(tRow,1)
	document.all('fmAction').value="DELETE||INSUREDRISK";
	lockScreen('lkscreen');  
	fm.action="./DelIsuredRisk.jsp?polno="+tpolno;
	document.getElementById("fm").submit(); //�ύ

}
function InsuredChk()
{
	var tSel =InsuredGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert("����ѡ�񱻱����ˣ�");
		return false;
	}
	var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
	
		var sqlid66="ContInputSql66";
	var mySql66=new SqlClass();
	mySql66.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql66.setSqlId(sqlid66);//ָ��ʹ�õ�Sql��id
    mySql66.addSubPara(tInsuredName);
	mySql66.addSubPara(tInsuredSex);
	mySql66.addSubPara(tBirthday);
	
	 mySql66.addSubPara(tInsuredNo);
	mySql66.addSubPara(fm.IDNo.value);
	mySql66.addSubPara(tInsuredNo);
	
	 var sqlstr=mySql66.getString();
	
//  var sqlstr="select * from ldperson where Name='"+tInsuredName
//            +"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
//            +"' and CustomerNo<>'"+tInsuredNo+"'"
//            +" union select * from ldperson where 1=1 "
////            +" and IDType = '"+fm.IDType.value
////            +"' and IDType is not null "
//            +" and IDNo = '"+fm.IDNo.value
//            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);

        if(arrResult==null)
        {
	   alert("��û����ñ����˱������ƵĿͻ�,����У��");
	   return false;
        }

    window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
}
function getdetailaccount()
{
	if(fm.AccountNo.value=="1")
	{
           document.all('BankAccNo').value=mSwitch.getVar("AppntBankAccNo");
           document.all('BankCode').value=mSwitch.getVar("AppntBankCode");
           document.all('AccName').value=mSwitch.getVar("AppntAccName");
	}
	if(fm.AccountNo.value=="2")
	{
           document.all('BankAccNo').value="";
           document.all('BankCode').value="";
           document.all('AccName').value="";
	}

}
function AutoMoveForNext()
{
	if(fm.AutoMovePerson.value=="���Ƶڶ���������")
	{
		     //emptyFormElements();
		     param="122";
		     fm.pagename.value="122";
                     fm.AutoMovePerson.value="���Ƶ�����������";
                     return false;
	}
	if(fm.AutoMovePerson.value=="���Ƶ�����������")
	{
		     //emptyFormElements();
		     param="123";
		     fm.pagename.value="123";
                     fm.AutoMovePerson.value="���Ƶ�һ��������";
                     return false;
	}
		if(fm.AutoMovePerson.value=="���Ƶ�һ��������")
	{
		     //emptyFormElements();
		     param="121";
		     fm.pagename.value="121";
                     fm.AutoMovePerson.value="���Ƶڶ���������";
                     return false;
	}
}
function noneedhome()
{

    var insuredno="";
    if(InsuredGrid.mulLineCount>=1)
    {
        for(var personcount=0;personcount<=InsuredGrid.mulLineCount;personcount++)
        {
        	if(InsuredGrid.getRowColData(personcount,5)=="00")
        	{
        		insuredno=InsuredGrid.getRowColData(personcount,1);

        		break;
        	}
        }
		
	var sqlid67="ContInputSql67";
	var mySql67=new SqlClass();
	mySql67.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql67.setSqlId(sqlid67);//ָ��ʹ�õ�Sql��id
    mySql67.addSubPara(insuredno);
	mySql67.addSubPara(fm.ContNo.value);
	mySql67.addSubPara(insuredno);
	
	 var strhomea=mySql67.getString();
		
      // var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
       arrResult=easyExecSql(strhomea,1,0);
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){};

       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};

       try{document.all('HomePhone').value= arrResult[0][2];}catch(ex){};

       fm.InsuredAddressNo.value = "";
       fm.InsuredNo.value = "";
    }
}
function getdetail2()
{
	
		var sqlid68="ContInputSql68";
	var mySql68=new SqlClass();
	mySql68.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql68.setSqlId(sqlid68);//ָ��ʹ�õ�Sql��id
	mySql68.addSubPara(fm.BankAccNo.value);
	
	 var strSql=mySql68.getString();
	
//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value+"'";
arrResult = easyExecSql(strSql);
if (arrResult != null) {
      fm.BankCode.value = arrResult[0][0];
      fm.AccName.value = arrResult[0][1];
    }
}


// �ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ
function InsuredChkNew()
{
	var tSel = InsuredGrid.getSelNo();
	if(tSel==null || tSel==0 ){ return ;}
	var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
	
			var sqlid69="ContInputSql69";
	var mySql69=new SqlClass();
	mySql69.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql69.setSqlId(sqlid69);//ָ��ʹ�õ�Sql��id
	mySql69.addSubPara(tInsuredName);
	mySql69.addSubPara(tInsuredSex);
	mySql69.addSubPara(tBirthday);
	mySql69.addSubPara(tInsuredNo);
	
	mySql69.addSubPara(fm.IDNo.value);
	mySql69.addSubPara(tInsuredNo);
	
	
	 var sqlstr=mySql69.getString();
	
//  var sqlstr="select * from ldperson where Name='"+tInsuredName
//            +"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
//            +"' and CustomerNo<>'"+tInsuredNo+"'"
//            +" union select * from ldperson where 1=1 "
////            +" and IDType = '"+fm.IDType.value
////            +"' and IDType is not null "
//            +" and IDNo = '"+fm.IDNo.value
//            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
 // alert("arrResult:"+arrResult)
  if(arrResult==null)
  {
  	//disabled"Ͷ����Ч��"��ť
		fm.InsuredChkButton.disabled = true;
		fm.InsuredChkButton2.disabled = true;
//				  return false;
  }
  else
  {
		fm.InsuredChkButton.disabled = "";
		fm.InsuredChkButton2.disabled = "";
					//�������ͬ�������Ա����ղ�ͬ�ͻ��ŵ��û���ʾ"Ͷ����У��"��ť
	}
}


//*******************************************************************
//�������˺Ÿ�ֵ�������˺���
function theSameToFirstAccount(){
	//alert("aasf");
  if(fm.theSameAccount.checked==true){
  	document.all('SecPayMode').value=document.all('PayMode').value;
  	document.all('SecPayModeName').value=document.all('PayModeName').value;
  	document.all('SecAppntBankCode').value=document.all('AppntBankCode').value;
  	document.all('SecAppntAccName').value=document.all('AppntAccName').value;
//    alert(document.all('AppntBankAccNo').value);
//    alert(document.all('SecAppntBankAccNo').value);
  	document.all('SecAppntBankAccNo').value=document.all('AppntBankAccNo').value;
    return;
  }
  if(fm.theSameAccount.checked==false){
//  	document.all('AppntBankCode').value='';
//  	document.all('AppntAccName').value='';
//  	document.all('AppntBankAccNo').value='';
    return;
  }
}

//��ʾ�����˺���Ϣ
function displayFirstAccount(){
  document.all('AppntBankCode').value = arrResult[0][0];
  document.all('AppntAccName').value = arrResult[0][1];
  document.all('AppntBankAccNo').value = arrResult[0][2];

  //��ѯ�������˺ź󣬲�ͬʱ��ֵ�������˺�
 	//document.all('SecAppntBankCode').value=document.all('AppntBankCode').value;
	//document.all('SecAppntAccName').value=document.all('AppntAccName').value;
 	//document.all('SecAppntBankAccNo').value=document.all('AppntBankAccNo').value;

}

//ǿ�ƽ����˹��˱�
function forceUW(){
	//��ѯ�Ƿ��Ѿ�����ѡ��ǿ�ƽ����˹��˱�
  var ContNo=document.all("ContNo").value;
  
  	var sqlid70="ContInputSql70";
	var mySql70=new SqlClass();
	mySql70.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql70.setSqlId(sqlid70);//ָ��ʹ�õ�Sql��id
	mySql70.addSubPara(ContNo);
	 var sqlstr=mySql70.getString();
  
//  var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
  if(arrResult==null){
  	alert("�����ڸ�Ͷ������");
  }
  else{
    window.open("../uw/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
  }

}

//��ѯ�Ƿ��Ѿ���ӹ�Ͷ����
function checkAppnt(){
  var ContNo=document.all("ContNo").value;
    
  	var sqlid71="ContInputSql71";
	var mySql71=new SqlClass();
	mySql71.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql71.setSqlId(sqlid71);//ָ��ʹ�õ�Sql��id
	mySql71.addSubPara(ContNo);
	 var sqlstr=mySql71.getString();
  
  //var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
  if(arrResult==null){
  	return false;
  }
  else{
    return true;
  }

}

function exitAuditing(){
  if(confirm("ȷ�ϸ�Ͷ�����ĸ��˽��棿")){
    top.close();
  }
  else{
    return;
  }
}

function exitAuditing2(){
  if(confirm("ȷ���뿪��Ͷ����������޸Ľ��棿")){
    top.close();
  }
  else{
    return;
  }
}


function showNotePad()
{

//	var selno = SelfGrpGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("��ѡ��һ������");
//	      return;
//	}

//var MissionID = SelfGrpGrid.getRowColData(selno, 4);
  //var MissionID = MissionID;
  //alert("MissionID="+MissionID);

//var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
 // var SubMissionID = document.all.SubMissionID.value;
  //alert("SubMissionID="+SubMissionID);

//	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
	var ActivityID = document.all.ActivityID.value;
	//alert("ActivityID="+ActivityID);

//	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
  var PrtNo = document.all.PrtNo2.value;
 // alert("PrtNo="+ document.all.PrtNo2.value);

	var NoType = document.all.NoType.value;
	//alert("NoType="+NoType);
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	}
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

}

/*********************************************************************
 *  ����򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showCodeName()
{
	showAppntCodeName();
	showContCodeName();
	showInsuredCodeName();
//	CodeQueryApplet.codeQuery();
}
/*********************************************************************
 *  ��ͬ����򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 **********************************************************************/
 function showContCodeName()
{
	 quickGetName('comcode', fm.ManageCom,fm.ManageComName);  //���뺺��
	 quickGetName('comcode', fm.AgentManageCom,fm.AgentManageComName);
	 quickGetName('sellType',fm.SellType,fm.sellTypeName);
	 quickGetName('agentrelatoappnt',fm.RelationShip,fm.RelationShipName);
	 //quickGetName('salechnl',fm.SaleChnl,fm.SaleChnlName);
	 if(fm.SaleChnl.value!="")
	{
		  	var sqlid72="ContInputSql72";
	var mySql72=new SqlClass();
	mySql72.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql72.setSqlId(sqlid72);//ָ��ʹ�õ�Sql��id
	mySql72.addSubPara(document.all('SaleChnl').value);
	 var strSql=mySql72.getString();
		
		//var strSql = "select codename from ldcode where codetype = 'salechnl' and code='"+document.all('SaleChnl').value+"'";
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.SaleChnlName.value = arrResult[0][0];
	      }
	}
	 
}
/*********************************************************************
 *  Ͷ���˴���򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAppntCodeName()
{
	quickGetName('vipvalue',fm.AppntVIPValue,fm.AppntVIPFlagname);
	quickGetName('IDType',fm.AppntIDType,fm.AppntIDTypeName);
	quickGetName('Sex',fm.AppntSex,fm.AppntSexName);
	quickGetName('Marriage',fm.AppntMarriage,fm.AppntMarriageName);
	quickGetName('NativePlace',fm.AppntNativePlace,fm.AppntNativePlaceName);
	quickGetName('language',fm.AppntLanguage,fm.AppntLanguageName);
	//quickGetName('OccupationCode',fm.AppntOccupationCode,fm.AppntOccupationCodeName);
	if(fm.AppntOccupationCode.value!="")
	{
		
				  	var sqlid73="ContInputSql73";
	var mySql73=new SqlClass();
	mySql73.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql73.setSqlId(sqlid73);//ָ��ʹ�õ�Sql��id
	mySql73.addSubPara(document.all('AppntOccupationCode').value);
	 var strSql=mySql73.getString();
		
		//var strSql = "select OccupationName from LDOccupation where occupationver = '002' and OccupationCode='"+document.all('AppntOccupationCode').value+"'";
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.AppntOccupationCodeName.value = arrResult[0][0];
	      }
	}
	quickGetName('occupationtype',fm.AppntOccupationType,fm.AppntOccupationTypeName);
//  quickGetName('province',fm.AppntProvince,fm.AppntProvinceName);
//  quickGetName('city',fm.AppntCity,fm.AppntCityName);
//  quickGetName('district',fm.AppntDistrict,fm.AppntDistrictName);
	getAddressName('province','AppntProvince','AppntProvinceName');
	getAddressName('city','AppntCity','AppntCityName');
	getAddressName('district','AppntDistrict','AppntDistrictName');
	if(fm.PayMode.value!="")
	{
		
						  	var sqlid74="ContInputSql74";
	var mySql74=new SqlClass();
	mySql74.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql74.setSqlId(sqlid74);//ָ��ʹ�õ�Sql��id
	mySql74.addSubPara(document.all('PayMode').value);
	 var strSql=mySql74.getString();
		
		//var strSql = "select codename from ldcode where codetype = 'paylocation2' and code='"+document.all('PayMode').value+"'";
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.PayModeName.value = arrResult[0][0];
	      }
	}
	//quickGetName('paylocation2',fm.PayMode,fm.PayModeName);
	if(fm.SecPayMode.value!="")
	{
		
								  	var sqlid75="ContInputSql75";
	var mySql75=new SqlClass();
	mySql75.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql75.setSqlId(sqlid75);//ָ��ʹ�õ�Sql��id
	mySql75.addSubPara(document.all('SecPayMode').value);
	 var strSql=mySql75.getString();
		
		//var strSql = "select codename from ldcode where codetype = 'paylocation' and code='"+document.all('SecPayMode').value+"'";
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.SecPayModeName.value = arrResult[0][0];
	      }
	}
	//quickGetName('paylocation',fm.SecPayMode,fm.SecPayModeName);
	quickGetName('bank',fm.AppntBankCode,fm.AppntBankCodeName);
	quickGetName('bank',fm.SecAppntBankCode,fm.SecAppntBankCodeName);
	//quickGetName('incomeway',fm.IncomeWay0,fm.IncomeWayName0);
	quickGetName('relation',fm.RelationToInsured,fm.RelationToInsuredName);
	

}

/*********************************************************************
 *  �����˴���򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsuredCodeName()
{
	quickGetName('SequenceNo',fm.SequenceNo,fm.SequenceNoName);
	quickGetName('vipvalue',fm.VIPValue1,fm.AppntVIPFlagname1);
	quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
	quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
	quickGetName('IDType',fm.IDType,fm.IDTypeName);
	quickGetName('Sex',fm.Sex,fm.SexName);
	quickGetName('Marriage',fm.Marriage,fm.MarriageName);
	quickGetName('language',fm.InsuredLanguage,fm.InsuredLanguageName);
	//quickGetName('OccupationCode',fm.OccupationCode,fm.OccupationCodeName);
	if(fm.OccupationCode.value!="")
	{
		
										  	var sqlid76="ContInputSql76";
	var mySql76=new SqlClass();
	mySql76.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql76.setSqlId(sqlid76);//ָ��ʹ�õ�Sql��id
	mySql76.addSubPara(document.all('OccupationCode').value);
	 var strSql=mySql76.getString();
		
		//var strSql = "select OccupationName from LDOccupation where occupationver = '002' and OccupationCode='"+document.all('OccupationCode').value+"'";
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.OccupationCodeName.value = arrResult[0][0];
	      }
	}
	quickGetName('occupationtype',fm.OccupationType,fm.OccupationTypeName);
	quickGetName('NativePlace',fm.NativePlace,fm.NativePlaceName);
	quickGetName('LicenseType',fm.LicenseType,fm.LicenseTypeName);
//	quickGetName('Province',fm.InsuredProvince,fm.InsuredProvinceName);
//	quickGetName('City',fm.InsuredCity,fm.InsuredCityName);
//	quickGetName('District',fm.InsuredDistrict,fm.InsuredDistrictName);
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName');
	//quickGetName('incomeway',fm.IncomeWay,fm.IncomeWayName);
}

//�����Ӱ���ѯ
function QuestPicQuery(){
	  var ContNo = fm.ContNo.value;
		if (ContNo == "")
		    return;
		  window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}
//��ѯҵ��Ա��֪��Ϣ
function getImpartInitInfo()
{
	var tContNo = fm.ContNo.value;

	var sqlid77="ContInputSql77";
	var mySql77=new SqlClass();
	mySql77.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql77.setSqlId(sqlid77);//ָ��ʹ�õ�Sql��id
	 var strSQL=mySql77.getString();

	//var strSQL ="select impartver,impartcode,ImpartContent from LDImpart where impartver='05' ";

	//turnPage.queryModal(strSQL, AgentImpartGrid);
//        var aSQL="SELECT distinct a.impartver,a.impartcode,a.ImpartContent,b.impartparammodle "
//		+"FROM ldimpart a left join lccustomerimpart b on a.impartver=b.impartver and a.ImpartCode=b.ImpartCode and b.contno='"+tContNo+"' "
//		+"WHERE a.impartver='05'";

	//turnPage.queryModal(aSQL,AgentImpartGrid);
	return true;
}


/**
* ���ݴ���ѡ��Ĵ�����Ҳ���ʾ���ƣ���ʾָ����һ��
* strCode - ����ѡ��Ĵ���
* showObjCode - �����ŵĽ������
* showObjName - Ҫ��ʾ���ƵĽ������
*/
function quickGetName(strCode, showObjc, showObjn) {
	showOneCodeNameOfObj(strCode, showObjc, showObjn);
}

/*************************************************************
 *                      ���Ҵ���(���̰����¼�)
 *  ����  ��  Field ��Ҫ��ʾ����Ŀؼ�;
 *            strCodeName ��������(Lx_Code);
 *            intFunctionIndex ��Ҫ��ֵ�ؼ���˳�� 1-ǰһ���ͱ���
 *                                                2-����ͺ�һ��;
 *            stationCode ����������վ.
 *  ����ֵ��  string  ��code �� null
 *************************************************************
 */
function showCodeListKey2(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven)
{
    
  	var ex,ey,i,intElementIndex;
  	var Field;
  	var tCode;
  	
    var strCondition = "";
    var strConditionField = "";
    
  	Field=arrShowCodeObj[0];
  	oldFieldKey = Field;
  	oldField = "";
  	alert(typeof(objCondition));
  	//alert(objCondition.value);
  	//���Ӵ���ѡ��Ĳ�ѯ������MINIM
    if (objCondition != null) {
      if (typeof(objCondition) == "object") {
        for(var m=0;m<objCondition.length;m++){
        	strCondition = strCondition+objCondition[m];
        	if(m<objCondition.length-1)
        	strCondition=strCondition+"|";
        }        
      }
      else {
        strCondition = objCondition;
      }
    }
    if (conditionField != null) {
      strConditionField = conditionField;
    }
  	
  	eobj = window.event;
    key  = eobj.keyCode;
    
    if (  document.all("spanCode").style.display=="" && (key == 13 || key==16)) {
       document.all("codeselect").focus();
       document.all("codeselect").onclick();
       try { Field.focus(); } catch(ex) {}
    }
    
    if ( key == 40 && document.all("spanCode").style.display=="") {
      Field.onblur=null;
      document.all("codeselect").focus();
      
      try {
        document.all("codeselect").children[showFirstIndex].selected=true;
      } 
      catch(e) {
    	  document.all("codeselect").children[0].selected=true;
    	}
      //showFirstIndex += showFirstIndex;
    }    
    else {
      Field.onblur=closeCodeList;    
    }
    
    if (document.all("spanCode").style.display=="none" && (key >= 48 || key==8 || key==46 || key==40 ))
    {
      setFieldPos(Field);
      //�ύ�������Ƽ���Ϣ
      if (arrShowCodeOrder == null) {
        arrShowCodeOrder = [0];
      }
      
      if (objShowCodeFrame == null) {
        objShowCodeFrame = parent.fraInterface;//window.self;
      }
      
      setFieldPos(Field);
      
      try {
    	  mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
    	  mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
        mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame��ָ��
      } 
      catch(e) {
        //���³�ʼ����������
        mVs=parent.VD.gVCode;  
        mCs=parent.VD.gVSwitch;                       
        var _Code_FIELDDELIMITER    = "|";   //��֮��ķָ���
        var _Code_RECORDDELIMITER   = "^";   //��¼֮��ķָ���
        var arrayBmCode;                     //����������Ĵ���
        var arrEasyQuery = new Array();			 // ���ʹ��easyQuery()�õ��Ĳ�ѯ�������   
        var showFirstIndex=0;
  
     	  showCodeListKey(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
    	}
    	
      //�ύ�������Ƽ���Ϣ
//      getCode(strCodeName,arrShowCodeObj,strCondition,strConditionField,refresh,showWidth,changeEven);
	tCode=searchCode(strCodeName,strCondition,strConditionField);  //�Ӵ�������ȡ����
	//ǿ��ˢ�²�ѯ
	if (refresh) {  
	  tCode = false;
    try { 
    	//����newһ��codeselect�����ʱ�򣬻���������������������ǿ��ˢ�µ�ʱ��Ҳ��Ҫdelete��������
    	mVs.deleteVar(strCodeName+strCondition+strConditionField); 
    	mVs.deleteVar(strCodeName+strCondition+strConditionField+"Select"); 
    } catch(ex) {}
  }
	if (tCode == false && arrShowCodeObj != null) {
	  //����������ˣ���ȡ����
	        //alert(strCondition);
	        //alert(strConditionField);
		requestServer(strCodeName, strCondition, strConditionField, showWidth,changeEven); 
		//��js��ֹ��ת������������������ҳ��ִ�У���ҳ���ѯ���ݺ󱣴浽ҳ���ϵ��ڴ�����
    //���ٴε��ø�js,������initializeCode��Ȼ��showCodeList,��ע��initializeCode�����ٵ����ã�
    //����ִ��js��������δ������Ѿ����˴��룬����������������
		return false;
	}

	//���������õ�������ͱ������ƴ��룬��ʾ����
	showCodeList1(tCode,strCodeName,strCondition,strConditionField,showWidth,changeEven);

      goToSelect(strCodeName,Field,strCondition,strConditionField);
    }
    else if ( document.all("spanCode").style.display=="" && (key >= 48 || key==8 || key==46))
    {
      if ( Field.value != null)   
      {
        goToSelect(strCodeName,Field,strCondition,strConditionField);
      }
    }
}

/**
* ����Ͷ���˱��������䡶Ͷ������������֮��=Ͷ���˱��������䡷,2005-11-18�����
* ��������������yy��mm��dd��Ͷ������yy��mm��dd
* ����  ����
*/
function calPolAppntAge(BirthDate,PolAppntDate)
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

//���ݵ�ַ�����ѯ��ַ������Ϣ,��ѯ��ַ�����<ldaddress>
//�������,��ַ����<province--ʡ;city--��;district--��/��;>,��ַ����<�������>,��ַ������Ϣ<���ֱ���>
//����,ֱ��Ϊ--��ַ������Ϣ<���ֱ���>--��ֵ
function getAddressName(strCode, strObjCode, strObjName)
{
	var PlaceType="";
	var PlaceCode="";
	//�жϵ�ַ����,<province--ʡ--01;city--��--02;district--��/��--03;>
	switch(strCode)
	{
	   case "province":
	      	PlaceType="01";
	      	break;
	   case "city":
	      	PlaceType="02";
	      	break;
	   case "district":
	      	PlaceType="03";
	      	break;   
	   default:
	   		PlaceType="";   		   	
	}
	//����FORM�е�����ELEMENT
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//Ѱ�Ҵ���ѡ��Ԫ��
			if (window.document.forms[formsNum].elements[elementsNum].name == strObjCode)
			{
				strObjCode = window.document.forms[formsNum].elements[elementsNum];
			}
			if (window.document.forms[formsNum].elements[elementsNum].name == strObjName)
			{
				strObjName = window.document.forms[formsNum].elements[elementsNum];
				break;
			}
		}
	}
	//��������������ݳ���Ϊ[6]�Ų�ѯ���������κβ���

//	strObjName.value="";
	if(strObjCode.value !="")
	{
		PlaceCode=strObjCode.value;
		
	var sqlid78="ContInputSql78";
	var mySql78=new SqlClass();
	mySql78.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql78.setSqlId(sqlid78);//ָ��ʹ�õ�Sql��id
    mySql78.addSubPara(PlaceCode);
    mySql78.addSubPara(PlaceType);
	 var strSQL=mySql78.getString();
		
		//var strSQL="select placecode,placename from ldaddress where placecode='"+PlaceCode+"' and placetype='"+PlaceType+"'";
		var arrAddress=easyExecSql(strSQL);
		if(arrAddress!=null)
		{   
			strObjName.value = arrAddress[0][1];
		}
		else
		{
			strObjName.value="";
		}
	}	
}


//��ѯ���±�
function checkNotePad(prtNo,LoadFlag){
	
		var sqlid79="ContInputSql79";
	var mySql79=new SqlClass();
	mySql79.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql79.setSqlId(sqlid79);//ָ��ʹ�õ�Sql��id
    mySql79.addSubPara(prtNo);
	 var strSQL=mySql79.getString();
	
	//var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";
	
	var arrResult = easyExecSql(strSQL);
	
	eval("document.all('NotePadButton"+LoadFlag+"').value='���±��鿴 ����"+arrResult[0][0]+"����'");
	
}


//���ƽ��水ť��������ʾ
function ctrlButtonDisabled(tContNo,tLoadFlag){
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array;
  
  if(tLoadFlag==5){
  	
			var sqlid80="ContInputSql80";
	var mySql80=new SqlClass();
	mySql80.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql80.setSqlId(sqlid80);//ָ��ʹ�õ�Sql��id
    mySql80.addSubPara(tContNo);
	 var strSQL=mySql80.getString();
	
    arrButtonAndSQL[0] = new Array;
    arrButtonAndSQL[0][0] = "ApproveQuestQuery";
    arrButtonAndSQL[0][1] = "�������ѯ";
    arrButtonAndSQL[0][2] =strSQL;// "select * from lcissuepol where 2 = 2 and OperatePos in ('0', '1', '5', 'A', 'I') and ContNo = '"+tContNo+"'";
  }


  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
      arrResult = easyExecSql(arrButtonAndSQL[i][2]);
      if(arrResult!=null){
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
      }
      else{
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
      }
    }
    else{
      continue;
    }	
  }
  	
}

/*
*Ͷ������Ϣ���޸ģ�Ͷ�����뱻���˹�ϵΪ���ˣ�ͬ���޸ı�������Ϣ��
*/
function updateInsured()
{
//	alert("��������2007-12-20���˹��ܴ�����");
	document.all('InsuredNo').value=document.all('AppntNo').value;
	displayissameperson();
	modifyRecord();
	updateFlag="1";
}


/**
 * У�������˺�  090910����  У�������˺������б���Ĺ���
 * ����˫¼�������ж�LoadFlag��ҳ�������÷���
 * ˫¼�Ҳ����жϵĽ�����ȥ��LoadFlag=='1'��У��
 * */
function checkAccNo(aObject,bObject){
	if(checkBankAccNo(aObject.value,bObject.value)==true){
		if(LoadFlag=='1'){
			confirmSecondInput(bObject,'onblur');
		}
	}else{
		bObject.focus();
	}
}

/**
 * Ͷ������ǿ�ƹ���
 * customerType: 0-Ͷ����  1-������
 */
function customerForceUnion(customerType){
	var customerno = "";
	var prtNo = fm.PrtNo.value;
	if(customerType=="0"){
		customerno = fm.AppntNo.value;
	} else if(customerType="1"){
		customerno = fm.InsuredNo.value;
	}
	var newWindow = window.open("../app/CustomerForceUnionMain.jsp?customerno="+customerno+"&prtno="+prtNo,"CustomerForceUnionMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}

function notNullCheck(){
	var AppntPostalAddress = document.getElementById("AppntPostalAddress").value,   
		AppntZipCode = document.getElementById("AppntZipCode").value,
		PayMode = document.getElementById("PayMode").value;
	var regBunber = /\d/;
	if(!AppntZipCode || !AppntZipCode.length == 6 || !regBunber.test(AppntZipCode)){
		alert("�ʱ���д����");
		$(AppntZipCode).focus();
		return false;
	}
	if(!AppntPostalAddress || !AppntPostalAddress.length > 80){
		alert("ͨѶ��ַ��д����");
		$(AppntPostalAddress).focus();
		return false;
	}
	if(!PayMode){
		alert("������ʽΪ�գ�");
		$(PayMode).focus();
		return false;
	}
}

function addRecordNotNullCheck(){
	var OccupationCode = document.getElementById("OccupationCode").value;
	if(!OccupationCode){
		alert("��ѡ��ְҵ���룡");
		$(OccupationCode).focus();
	}
}
