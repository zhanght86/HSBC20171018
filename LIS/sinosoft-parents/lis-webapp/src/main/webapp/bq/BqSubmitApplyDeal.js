var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var tUpFlag = "0";
function easyQueryClick() 
{  	
	  
    var strSQL = "";
    
    var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(SubNo);//ָ������Ĳ���
		strSQL=mySql1.getString();

    
//    strSQL = "select GrpContNo,EdorType,DispDept,DispPer,SubPer,SubDesc,appText1,appText2,appText3 from LPSubmitApply where SubNo='"+SubNo+"'";
    var arr = easyExecSql(strSQL );
    //alert(arr);
    if(arr!=null)
    {
    
     fm.SubNo.value = SubNo;
     fm.GrpContNo.value=arr[0][0]; 
     fm.EdorType.value=arr[0][1];
     fm.DispDept.value=arr[0][2];
     fm.DispPer.value=arr[0][3]; 
     fm.SubPer.value=arr[0][4];
     fm.SubDesc.value=arr[0][5]; 
     fm.AppText.value=arr[0][6]; 
		 fm.AppTextHide.value= fm.AppText.value;
		 fm.FilePathHide.value = arr[0][7];
		 fm.DatePathHide.value = arr[0][8];
     //showOneCodeName('llinitphase','InitPhase','InitPhaseName');    
     //showOneCodeName('llsubtype','SubType','SubTypeName');    
     //showOneCodeName('llsubstate','SubState','SubStateName');    
     //showOneCodeName('station','SubDept','SubDeptName');   
      } 
    
}  
//���д������
function HaveDeal()
{
	if(DivLPTraceSubmit.style.display=="none"){
			DivLPTraceSubmit.style.display="";
			initLPSubmitApplyTraceGrid();
	
			var sqlid2="DSHomeContSql2";
			var mySql2=new SqlClass();
			mySql2.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
			mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
			mySql2.addSubPara(fm.SubNo.value);//ָ������Ĳ���
			var strSQL=mySql2.getString();
			
//			var strSQL ="select a.SubNo,a.SerialNo,a.GrpContNo,(select edorname from lmedoritem where edorcode = a.EdorType and appobj ='G'),a.SubPer,a.DispPer,decode(a.DealType,'0','�����б�','1','�ʱ����','2','�˻�','����'),a.DealIdea,a.DealDate from LPSubmitApplyTrace a where a.SubNo='"+fm.SubNo.value+"' order by a.SerialNo";
   		turnPage.pageLineNum=5;    //ÿҳ5��
    	turnPage.queryModal(strSQL,LPSubmitApplyTraceGrid);
}else
	{
		DivLPTraceSubmit.style.display="none";
	}
}


function ShowDealIdea()
{
     var i = LPSubmitApplyTraceGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tDealIdea = LPSubmitApplyTraceGrid.getRowColData(i,8); 
        fm.DispIdeaTrace.value=tDealIdea;
        
    }	
}  
//���и������
function ShowAppFile()
{
	//var newWindow = window.open("HaveApp.jsp?SubNo="+SubNo+"","HaveApp",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	//alert(SubNo);
	//alert(fm.FilePathHide.value);
	if(DivAppShow.style.display=="none"){
		var divAppPath = document.getElementById("AppPath");
		if(fm.FilePathHide.value != null && fm.FilePathHide.value != ""){
			if(fm.DatePathHide.value == null || fm.DatePathHide.value == "null")
			{
				fm.DatePathHide.value = "";
			}
			divAppPath.innerHTML="<a href='BqSubmitApplyDownLoad.jsp?file="+fm.DatePathHide.value+fm.SubNo.value+fm.FilePathHide.value+"'>����</a>";
		}else
			{
				divAppPath.innerHTML="�޸���";
			}
		
		DivAppShow.style.display="";
	}else
		{
			var divAppPath = document.getElementById("AppPath")
			divAppPath.innerHTML="";
			DivAppShow.style.display="none";
		}
}

//ɨ������
function HaveApp()
{
	var newWindow = window.open("DiskApp.jsp?SubNo="+SubNo+"","DiskApp",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	//alert(SubNo);
}

function afterCodeSelect( cName, Filed)   //CodeSelect�������
{

    if(cName=='DispType')           //
    {
    	if(fm.DispType.value=="0")
    	{
    		DivNewDispPer.style.display="";
    		if(fm.DispPer.value == fm.SubPer.value)
    		{
    			var sqlid3="DSHomeContSql3";
					var mySql3=new SqlClass();
					mySql3.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
					mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
					mySql3.addSubPara(fm.SubNo.value);//ָ������Ĳ���
					var QuerySQL=mySql3.getString();
    			
//    			var QuerySQL = "select substate from LPSubmitApply where subno = '"+fm.SubNo.value+"'";
	 				var rResult;					 

      			rResult = easyExecSql(QuerySQL, 1, 0);
      			if (rResult != null && trim(rResult[0][0]) == "4")
     				{
    					DivApp.style.display="";
    					UpSubApp.style.display="";
    					DivNewDispPer.style.display="";
     				}else{
     					   DivApp.style.display="none";
								UpSubApp.style.display="none"
								DivNewDispPer.style.display="";
     					}


    	}else{
    		
    			DivApp.style.display="none";
					UpSubApp.style.display="none"
    		}
    	}else
    		{
    			DivNewDispPer.style.display="none";
    			DivApp.style.display="none";
					UpSubApp.style.display="none"
    		}
    }
}

function upSubInfo()
{
	var appTextArr = document.getElementsByName("AppTextChk");
	var appText ="";
	for( var i = 0; i < appTextArr.length; i++ )
	{
		if ( appTextArr[i].checked )
		{
			appText += appTextArr[i].value;
		}
	}
	if(fm.AppTextChkOther.checked)
	{
		var appTextOther = trim(fm.AppTextOther.value);
		if(appTextOther == null || appTextOther == "")
		{
			alert("��ע��������������!");
			return;
		}
		appText += "����:"+appTextOther
	} 
	//alert(appText);
	fm.AppText.value = appText;
	//alert(fm.AppText.value);
	////����һЩУ��
	if(trim(fm.EdorType.value)=="")
	{
		alert("��ѡ��ȫ��Ŀ!");
		return;
	}
	if(trim(fm.GrpContNo.value)=="")
	{
		alert("��¼�������ͬ���룡");
		return;
	}

	
	
	//У�������ͬ���Ƿ����
	var sqlid4="DSHomeContSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
	var QuerySQL=mySql4.getString();
	
//	var QuerySQL = "select 1 from lcgrpcont where grpcontno = '"+fm.GrpContNo.value+"'";
	 var rResult;					 
   try
   {
      rResult = easyExecSql(QuerySQL, 1, 0);
      if (rResult == null || trim(rResult[0][0]) != "1")
     	{
    		alert("¼��������ͬ�Ų�����");
    		return;
     	}
   }
   catch (ex)
   {
       alert("���棺��ѯ�����ͬ�ų����쳣�� ");
       return;
   }
  //�ύҳ������
    
   var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
   var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
   //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   //  showSubmitFrame(mDebug);
   //alert(fm.ClmNo.value);
		tUpFlag = "1";
    fm.action = "./BqSubmitApplyDealUp.jsp?SubNo="+fm.SubNo.value;    
    document.getElementById("fm").submit(); //�ύ

}

//"�ʱ�ȷ��"�ύ��ť
function Replyport()
{
	var appTextArr = document.getElementsByName("AppTextChk");
	var appText ="";
	for( var i = 0; i < appTextArr.length; i++ )
	{
		if ( appTextArr[i].checked )
		{
			appText += appTextArr[i].value;
		}
	}
	if(fm.AppTextChkOther.checked)
	{
		var appTextOther = trim(fm.AppTextOther.value);
		if(appTextOther == null || appTextOther == "")
		{
			alert("��ע��������������!");
			return;
		}
		appText += "����:"+appTextOther
	} 
	//alert(appText);
	fm.AppText.value = appText;
	//alert(fm.AppText.value);
	////����һЩУ��
	if(trim(fm.EdorType.value)=="")
	{
		alert("��ѡ��ȫ��Ŀ!");
		return;
	}
	if(trim(fm.GrpContNo.value)=="")
	{
		alert("��¼�������ͬ���룡");
		return;
	}
	if(trim(fm.DispPer.value)=="")
	{
		alert("��ѡ��н��ˣ�");
		return;
	}

	
	
	//У�������ͬ���Ƿ����
	var sqlid5="DSHomeContSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
	var QuerySQL=mySql5.getString();
	
//	var QuerySQL = "select 1 from lcgrpcont where grpcontno = '"+fm.GrpContNo.value+"'";
	 var rResult;					 
   try
   {
      rResult = easyExecSql(QuerySQL, 1, 0);
      if (rResult == null || trim(rResult[0][0]) != "1")
     	{
    		alert("¼��������ͬ�Ų�����");
    		return;
     	}
   }
   catch (ex)
   {
       alert("���棺��ѯ�����ͬ�ų����쳣�� ");
       return;
   }
    //var tGrpContNo = fm.GrpContNo.value;
   	//var tFileName=document.all('filePath').value;
		////alert(tFileName);
		//if(tFileName != null && tFileName !="")
		//{
		//	if ( tFileName.indexOf("\\")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("\\")+1);
		//	if ( tFileName.indexOf("/")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("/")+1);
		//	if ( tFileName.indexOf("_")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("_"));
		//	if ( tFileName.indexOf(".")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("."));
		//	if(tFileName != tGrpContNo)
		//	{
		//		alert("�ļ�������������ͬ�Ų�һ�»򲻷����ϴ�Ҫ��,����!");
		//		return ;
		//	}
		//}
	//if(fm.CustomerNo.value=="")
	//{
	//	alert("������ʱ��ˣ�");
	//	return;
	//}
	submitForm();   //�ύҳ������
}
//�ʱ�����ȷ��
function ReplyportConfirm()
{
	if(fm.DispType.value!="0" && fm.DispType.value!="1" && fm.DispType.value!="2"&& fm.DispType.value!="C")
	{
		 alert("��ѡ����ȷ�Ĵ������ͣ�");
		 return;
	}
	//if(trim(fm.DispIdea.value)==""&& fm.DispType.value!="C")
	//{
	//	alert("�����봦�������");
	//	return;
	//}
	//
	if(fm.DispType.value=="0")
	{
			if(document.all("NewDispPer").value==null ||document.all("NewDispPer").value=="")
			{
				alert("��ѡ���µĳн��ˣ�");
				return;
			}
	}
	
	if(fm.DispType.value=="C")
	{
		var sqlid6="DSHomeContSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(SubNo);//ָ������Ĳ���
		var strSQL=mySql6.getString();
		
//		var strSQL = "select DispPer,SubPer from LPSubmitApply where SubNo='"+SubNo+"'";
    var arr = easyExecSql(strSQL );
    if(arr!=null)
    {
    	var tDispPer = arr[0][0];
    	var tSubPer = arr[0][1];
    	if(tDispPer != tSubPer)
    	{
    		alert("��������ִ�иô�������!");
    		return;
    	}
    }else{
    	alert("��ѯ�����쳣!");
    	return;
    }
	}
	 //alert(SubNo);
   var i = 0;
   var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
   var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
   //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
    fm.action = "./BqSubmitApplyConfirmSave.jsp?DispType="+fm.DispType.value+"&SubNo="+SubNo+"&NewDispPer="+fm.NewDispPer.value+"&NewDispDept="+fm.NewDispDept.value+"&DispIdea="+fm.DispIdea.value;   
    document.getElementById("fm").submit(); //�ύ	
  
}
function TurnBack()
{
	 var strUrl="";
	 //alert(flag);
	  if(flag=="1")
	  {
	  	strUrl= "HaveAppQueryInput.jsp";
	  	location.href=strUrl;
	  }else if(flag=="5")
	  	{
	  	strUrl= "BqSubmitApplyScanMissInput.jsp";
	  	location.href=strUrl;
	  	}else{
	   if(flag=="3")
	   {
	   	top.close();
	   }else
	   	{
   		  strUrl= "BqSubmitApplyDealMissInput.jsp";
     		location.href=strUrl;
    	}
    }

}
  //�ύ����
function submitForm()
{
	//alert(document.all('FileName').value);
   var i = 0;
   var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
   var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
   //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   //  showSubmitFrame(mDebug);
   //alert(fm.ClmNo.value);

  
    fm.action = "./BqSubmitApplyDealSave.jsp";    
    document.getElementById("fm").submit(); //�ύ

    
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    showInfo.close();
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    	if(tUpFlag == "1"){
    		easyQueryClick();
    	}else{
    		TurnBack();//[�ɹ��򷵻ض���ҳ��]
    	}
    	
    }
    tSaveFlag ="0";
    tUpFlag = "0";
    //alert(tUpFlag);
}

function initComCode()
{
	var mclength = manageCom.length;
	if(mclength<=2)
	{
		fm.DispDept.value = manageCom;
	}else{
		
		var dispDept = manageCom.substr(0,mclength-2);
		fm.DispDept.value = dispDept;
		
	}

	//showOneCodeName('station', 'DispDept', 'DispDeptName');
	var sqlid7="DSHomeContSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(fm.DispDept.value);//ָ������Ĳ���
	var tSQL=mySql7.getString();
	
//	var tSQL = "select name from ldcom where comcode = '"+fm.DispDept.value+"'";
	var arr =  easyExecSql(tSQL, 1, 0, 1);
	if(arr != null)
	{
		//alert(arr);
			fm.DispDeptName.value = arr[0][0];
	}
}

function initNewComCode()
{
	var mclength = manageCom.length;
	if(mclength<=2)
	{
		fm.NewDispDept.value = manageCom;
	}else{
		
		var dispDept = manageCom.substr(0,mclength-2);
		fm.NewDispDept.value = dispDept;
		
	}

	//showOneCodeName('station', 'DispDept', 'DispDeptName');
	var sqlid8="DSHomeContSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(fm.DispDept.value);//ָ������Ĳ���
	var tSQL=mySql8.getString();
	
//	var tSQL = "select name from ldcom where comcode = '"+fm.DispDept.value+"'";
	var arr =  easyExecSql(tSQL, 1, 0, 1);
	if(arr != null)
	{
		//alert(arr);
			fm.NewDispDeptName.value = arr[0][0];
	}
}

function querEdorInfo()
{
	var tEdorAcceptNo = fm.EdorAcceptNo.value;
	if(tEdorAcceptNo!=null && tEdorAcceptNo != "")
	{
		var sqlid9="DSHomeContSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
		var tSQL=mySql9.getString();
		
//		var tSQL = "select grpcontno,edortype from lpgrpedoritem where edoracceptno = '"+tEdorAcceptNo+"'";
		var arr =  easyExecSql(tSQL, 1, 0, 1);
		if(arr != null)
		{
			//alert(arr);
			fm.GrpContNo.value = arr[0][0];
			fm.EdorType.value = arr[0][1];
		}
	}
}

function getDispPer()
{
	var sqlid10="DSHomeContSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
	mySql10.addSubPara(fm.DispDept.value);//ָ������Ĳ���
	var sql=mySql10.getString();
	
//	var sql = "select a.usercode, b.username "
//  				+ "from ldedoruser a, lduser b "
// 					+ " where a.usercode = b.usercode "
//      	  + " and a.usertype = '2' and b.comcode = '"+fm.DispDept.value+"'";
  var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
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
  document.all("DispPer").CodeData = tCodeData;	
}

function getNewDispPer()
{
	var sqlid11="DSHomeContSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(fm.NewDispDept.value);//ָ������Ĳ���
	var sql=mySql11.getString();
	
//	var sql = "select a.usercode, b.username "
//  				+ "from ldedoruser a, lduser b "
// 					+ " where a.usercode = b.usercode "
//      	  + " and a.usertype = '2' and b.comcode = '"+fm.NewDispDept.value+"'";
  var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
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
  document.all("NewDispPer").CodeData = tCodeData;	
}

function getEdorInfo()
{
	var sqlid12="DSHomeContSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName("bq.BqSubmitApplyDealInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	mySql12.addSubPara();//ָ������Ĳ���
	var sql=mySql12.getString();
		
//	var sql = "select  EdorCode, EdorName "
// 												+ " from lmedoritem"
// 												+ "	where appobj = 'G'"
  var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
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
  document.all("EdorType").CodeData = tCodeData;	
}


