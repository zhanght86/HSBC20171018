//�������ƣ�LLInqCourse.js
//�����ܣ��������
//�������ڣ�2005-6-22

//���¼�¼��  ������:yuejw    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var k = 0;
var mySql = new SqlClass();
//�ύ��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit(); //�ύ
}


//[���̻��߷���¼��]�ύ�����,���������ݷ��غ�ִ�еĲ���
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
    initInqCourseInfo();
    initInqFeeInfo();
    initLLInqCertificateInfo();
  }

}

//[����ȷ��]�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterComfirmSubmit( FlagStr, content )
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
	turnback();//����
  }

}

// ��ʼ�������ѯ
function LLInqApplyQueryClick()
{
	if(document.all('ClmNo').value=="" ||document.all('InqNo').value=="")
	{
		alert("���ݴ��ʹ���!");
	    return;
	}
	//Modify by zhaorx 2006-03-15 ��������������
   /* var strSQL = "";
    strSQL = "select clmno,inqno,batno,customerno,customername,vipflag,inqdept,initphase"
    	   +",inqrcode,inqitem,(select username from lduser where usercode = applyper),applydate,initdept, "
    	   +" (case locflag when '0' then '����' when '1' then '���' end), "
    	   +" inqdesc "
           +" from llinqapply where 1=1 "
           +" and clmno='"+document.all('ClmNo').value+"' and inqno='"+document.all('InqNo').value+"'"; */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqCourseInputeSql");
	mySql.setSqlId("LLInqCourseSql1");
	mySql.addSubPara(document.all('ClmNo').value ); 
	mySql.addSubPara(document.all('InqNo').value ); 
    var arr = easyExecSql(mySql.getString());
    if (arr == null)
    {
	      alert("�������벻����!");
	      return;
    }
    else
    {
    	fm.ClmNo.value=arr[0][0]; //�ⰸ��
        fm.InqNo.value=arr[0][1];  //�������
        fm.BatNo.value=arr[0][2]; //���κ�
        fm.CustomerNo.value=arr[0][3]; //�����˿ͻ���
        fm.CustomerName.value=arr[0][4];//�����˿ͻ�����
        fm.VIPFlag.value=arr[0][5];   //VIP�ͻ�
        fm.InqDept.value=arr[0][6];   //�������
        fm.InitPhase.value=arr[0][7]; //����׶�
        fm.InqRCode.value=arr[0][8];  //����ԭ��
        fm.InqItem.value=arr[0][9];  //������Ŀ
        fm.ApplyPer.value=arr[0][10];  //������
        fm.ApplyDate.value=arr[0][11];  //��������
        fm.InitDept.value=arr[0][12];  //�������
        fm.LocFlag.value=arr[0][13];  //���ر�־
        fm.InqDesc.value=arr[0][14];  //��������
        showOneCodeName('llinitphase','InitPhase','InitPhaseName');
        showOneCodeName('llinqreason','InqRCode','InqRCodeName');
        showOneCodeName('stati','InqDept','InqDeptName');   
        showOneCodeName('stati','InitDept','InitDeptName');  
        //showOneCodeNameEx('LocFlag','LocFlag','LocFlagName');  
    }
}
//��ӱ�����������Ϣ
function AddInqCourseClick()
{
	var tInqMode=fm.InqMode.value;
	var tInqSite=fm.InqSite.value;
	var tInqDate=fm.InqDate.value;
	var tInqPer1=fm.InqPer1.value;
	var tInqByPer=fm.InqByPer.value; 
	var tInqCourse=fm.InqCourse.value; 
	var tInqPer1=fm.InqPer1.value;
	var tInqByPer=fm.InqByPer.value;
	//alert("tInqCourse"+tInqCourse);
    if (tInqMode=="" ||tInqSite == "" || tInqCourse== "")
    {
        alert("���鷽ʽ���ص㡢���������д��������");
        return;
    }
//    if (tInqPer1=="" ||tInqByPer == "" || tInqCourse== "")
//    {
//        alert("�����ˡ��������ˡ���д��������");
//        return;
//    }
    //�õ�ϵͳ��ǰʱ�亯���ĺ���ΪgetCurrentDate()������У���������
    if((fm.InqDate.value!="") && (dateDiff(mCurrentDate,fm.InqDate.value,'D') > 0))
    {
    	alert("�������ڲ������ڵ�ǰ���ڣ�");
    	return;
    }
    
    
    //��1�����˱���¼��
    if(tInqPer1==""||tInqPer1==null)
    {
    	alert("����¼���һ�����ˣ�");
    	return;
    }
    
    //�������˱���¼��
    if(tInqByPer==""||tInqByPer==null)
    {
    	alert("����¼�뱻�����ˣ�");
    	return;
    }
    
    //��� ������̵�֤��Ϣ
    var rowNum=LLInqCertificateGrid.mulLineCount ; //����
    if(rowNum==0)
    {
    	if(confirm("�㻹û��¼�������̵�֤��Ϣ���Ƿ���Ҫ¼��?")==true)
    	{
//    		alert("¼�뵥֤");
    		showInqCerficate();
    		return;
    	}
    }
	fm.action="LLInqCourseSave.jsp";
	submitForm();
}
//��ѯ���������Ϣ
function InqCourseQueryClick()
{
	 /*var strSql="select * from llinqcourse where 1=1 "
	 		+" and clmno='"+fm.ClmNo.value+"' "
	 		+" and inqno='"+fm.InqNo.value+"'  "
	 		+" order by clmno" ;*/
	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqCourseInputeSql");
	mySql.setSqlId("LLInqCourseSql2");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(fm.InqNo.value );
	 //prompt("��ѯ���������Ϣ",strSql)
	 var arr = easyExecSql(mySql.getString());
	 //alert("arr="+arr);
	 if (arr == null)
    {
	      alert("�õ������뻹û���κε��������Ϣ!");
	      return;
    }
    //�����鿴���������Ϣҳ��
	var strUrl="LLInqCourseQueryMain.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value;
    //System.out.println(strUrl);
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//��ӱ�����������Ϣ
function AddInqFeeClick()
{
    if (fm.FeeType.value == "" || fm.FeeSum.value == "" ||fm.FeeDate.value=="")
    {
        alert("�������͡����ý�����ʱ�䲻������");
        return;
    }
//    if (fm.Payee.value == "" || fm.PayeeType.value == "" )
//    {
//       alert("����ˡ���ʽ��д��������");
//        return;
//    }
    if (fm.FeeSum.value!="" && !isNumeric(fm.FeeSum.value))
    {
        alert("���ý����д����");
        return;
    }
    //�õ�ϵͳ��ǰʱ�亯���ĺ���ΪgetCurrentDate()������У����÷���ʱ��
    if( (fm.FeeDate.value!="")&& (dateDiff(mCurrentDate,fm.FeeDate.value,'D') > 0))
    {
    	alert("���÷������ڲ������ڵ�ǰ���ڣ�");
    	return;
    }
	fm.fmtransact.value = "INSERT";
	fm.action="LLInqFeeSave.jsp";
	submitForm();

}
//��ѯ���������Ϣ
function QueryInqFeeClick()
{
	 /*var strSql="select * from llinqfee where 1=1 "
 			+" and clmno='"+fm.ClmNo.value+"' "
 			+" and inqno='"+fm.InqNo.value+"'  "
 			+" order by clmno" ;*/
 	mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqCourseInputeSql");
	mySql.setSqlId("LLInqCourseSql3");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(fm.InqNo.value );		
	 var arr = easyExecSql(mySql.getString());
	 if (arr == null)
    {
	      alert("�õ������뻹û���κη�����Ϣ!");
	      return;
    }
    //�����鿴���������Ϣҳ��
	var strUrl="LLInqFeeQueryMain.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//������ɱ�־
function MoreInqClick()
{
	if (fm.MoreInq.checked==true)
    {
    	fm.InqState.value = "1";//������ɱ�ʾ
        fm.InqConclusion.disabled = false;
        fm.InqConfirm.disabled = false;        
    }
    else
    {
        fm.InqState.value = "0";//����δ��ɱ�ʾ
        fm.InqConclusion.disabled = true;
        fm.InqConfirm.disabled = true;  
    }	  	  
}
//���ض���ҳ��
function turnback()
{
    var strUrl= "LLInqCourseMissInpute.jsp?";    
    location.href=strUrl;
}

function InqConfirmClick()
{
	 /* var strinqcourse="select * from llinqcourse where 1=1"
	             + getWherePart('ClmNo','ClmNo')
                 + getWherePart('InqNo','InqNo')
                 + " order by ClmNo";    */  
      mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqCourseInputeSql");
	mySql.setSqlId("LLInqCourseSql4");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(fm.InqNo.value );	
      var arrinqcourse=easyExecSql(mySql.getString());
      if(arrinqcourse==null)
      {
      	alert("û���κε�����̣����鲻������ɣ�");	
      	return;
      }           
	 /* var strSQL = "";//��ѯ�������
	  strSQL ="select max(ConNo) from Llinqconclusion where 1=1"
	        +" and clmno='"+document.all('clmno').value+"'"    //�ⰸ��
	        +" and batno='"+document.all('batno').value+"'"    //��������
	        +" and inqdept='"+document.all('inqdept').value+"'"; //�������*/
	  mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqCourseInputeSql");
	mySql.setSqlId("LLInqCourseSql5");
	//mySql.addSubPara(document.all('clmno').value ); 
	mySql.addSubPara(fm.ClmNo.value); 
	//mySql.addSubPara(document.all('batno').value );	
	mySql.addSubPara(fm.BatNo.value);	
	//mySql.addSubPara(document.all('inqdept').value );
	mySql.addSubPara(fm.InqDept.value);	
	  var arr=easyExecSql(mySql.getString());  
      fm.ConNo.value = arr;
	  if(fm.InqConclusion.value =="" || fm.InqConclusion.value ==null)
	  {
		  alert("����д�������!");
	      return;  
	  }
    fm.fmtransact.value = "UPDATE";
	fm.action="LLInqConfirmSave.jsp";
	submitForm();    
}
/*****************************************************************
����Ϊ���--��������̵�֤��Ϣ������----2005-08-28����޸�
******************************************************************/
//[ȷ�ϰ�ť]--------¼��������ʱ��������� ������̵�֤��Ϣ
function addInqCertificate()
{
	var affixCode="";   
	var affixName="";
	if(!fm.checkbox.checked)
	{
	    affixCode=fm.AffixCode.value;
	    affixName=fm.AffixName.value;
		if (affixCode=="")
		{
			alert("��ѡ�����赥֤");
			return;
		}
		if (codeCheck(affixCode))
		{
	        alert("�õ�֤�Ѵ��ڣ�������ѡ��");
	        return;
	    }
	}
	else
	{
	  affixCode="000000";
      affixName=fm.OtherName.value;
      if (affixName=="")
      {
        alert("�����뵥֤����");
        return;
      }
	}

	LLInqCertificateGrid.addOne();
	var rows=LLInqCertificateGrid.mulLineCount;
	LLInqCertificateGrid.setRowColData(rows-1,1,affixCode);
	LLInqCertificateGrid.setRowColData(rows-1,2,affixName);
}
//[У�鵥֤����]
function codeCheck(mcode)
{
	var rows=LLInqCertificateGrid.mulLineCount;
	var affixCode="";
	for (var i=0;i<rows;i++)
	{
	  	affixCode=LLInqCertificateGrid.getRowColData(i,1);
	      if (affixCode==mcode)
	      {
	    	return true;
	      }
	}
}


function checkboxClick()
{   
	if(fm.checkbox.checked==true)
	{
//		alert("������֤");
		fm.AffixCode.value="";
		fm.AffixName.value="";
		fm.AffixCode.disabled=true;
		fm.OtherName.value="";
		fm.OtherName.disabled=false;
	}
	else
	{
//		alert("��׼��֤");
		fm.AffixCode.value="";
		fm.AffixName.value="";
		fm.AffixCode.disabled=false;
		fm.OtherName.value="";
		fm.OtherName.disabled=true;
	}
	
}

function cancelInqCerficate()
{
	DivInqCertificate.style.display="none";
}

function showInqCerficate()
{
	
	fm.AffixCode.value="";
	fm.AffixName.value="";
	fm.checkbox.checked=false;
	fm.OtherName.value="";
	fm.OtherName.disabled=true;
	DivInqCertificate.style.display="";
}

/*****************************************************************
����Ϊ���--����ѯ����������Ϣ������----2005-10-6����޸�
******************************************************************/
//[��ѯ����������Ϣ]--------��ѯ����������Ϣ
function queryOtherInqClick()
{
	var strUrl="LLInqOtherQueryMain.jsp";
	strUrl=strUrl+"?ClmNo="+fm.ClmNo.value;
	strUrl=strUrl+"&BatNo="+fm.BatNo.value;
	strUrl=strUrl+"&InqNo="+fm.InqNo.value;
	strUrl=strUrl+"&InqDept="+fm.InqDept.value;
	strUrl=strUrl+"&Type=0";
//	window.open(strUrl,"��ѯ����������Ϣ");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

