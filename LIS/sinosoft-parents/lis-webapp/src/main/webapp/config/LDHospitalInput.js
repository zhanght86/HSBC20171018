//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
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
function checkuseronly(comname)
{
	//add by yaory
	

} 
//�ύ�����水ť��Ӧ����
function submitForm()
{
 if(!verifyInput())
 {
 	 return false;
 }
 if(!checkData())
 {
 	 return false;
 }
 
  var i = 0;
  fm.fmtransact.value="INSERT";
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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

//У���Ƿ������Ϣ�Ѵ���
function checkData()
{
	var arrResult = new Array();
//	var SQL = "select '1' from ldhospital where hospitcode='"+document.all('HospitalCode').value+"' ";
	
	var sqlid0="LDHospitalInputSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("config.LDHospitalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(document.all('HospitalCode').value);//ָ������Ĳ���
	var SQL=mySql0.getString();
	
	arrResult = easyExecSql(SQL);
	//alert(arrResult);
	if(arrResult!=null)
	{
		alert('�Ѵ��ڸ����ҽԺ,��������!');
		initForm();
		return false;
	}
	return true;
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
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //ִ����һ������
  }
  fm.fmtransact.value = "";
  initForm();
}
//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��LDUWUser.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 
//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
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
       
//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  if((fm.fmtransact.value == null)||(fm.fmtransact.value != "QUERY"))
  {
  	alert("���Ȳ�ѯ��¼�����޸ģ�");  	
  	return false;
  }

  //����������Ӧ�Ĵ���
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
  {
  	document.all('HospitalCode').disabled=false;
  	if(!verifyInput())
 		{
 			document.all('HospitalCode').disabled=true;
 	 		return false;
 		}
  	var i = 0;
  	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  //showSubmitFrame(mDebug);
  	fm.fmtransact.value = "UPDATE";
  	document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    //mOperate="";
    alert("��ȡ�����޸Ĳ�����");
  }
}           
//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";
  showInfo=window.open("./LDHospitalDetailQuery.html");
}           
//Click�¼����������ɾ����ͼƬʱ�����ú���

function deleteClick()
{
	if((fm.fmtransact.value == null)||(fm.fmtransact.value != "QUERY"))
  {
  	alert("���Ȳ�ѯ��¼����ɾ����");  	
  	return false;
  }

  //����������Ӧ��ɾ������
  if (confirm("��ȷʵ��ɾ���ü�¼��?"))
  {
  	document.all('HospitalCode').disabled=false;
  	var i = 0;
  	var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "DELETE";
  	document.getElementById("fm").submit(); //�ύ
  	initForm();
  }
  else
  {
    alert("��ȡ����ɾ��������");
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
/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult )
{
	var arrResult = new Array();
	//alert(arrQueryResult[0][0]);
	if( arrQueryResult != null )
	{
		
//		  var strSql = "select hospitcode,a.hospitalname,a.hospitalgrade,mngcom,a.validitydate,a.address,(select codename from ldcode where codetype='hospitalgrade' and code=a.hospitalgrade) "
//         		     + ",(select name from ldcom where comcode=a.mngcom)"
//         		 		 + ",a.hosstate,(select codename from ldcode where codetype='hosstate' and code=a.hosstate), "
//         		 		 + " a.remark from  LDHospital a where 1=1 "
//         		     + " and trim(hospitcode) = '"+arrQueryResult[0][0]+"'";
		   
			var sqlid1="LDHospitalInputSql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("config.LDHospitalInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
			var strSql=mySql1.getString();
		  
	 	arrResult = easyExecSql(strSql);
	 if(arrResult!=null){
	  document.all('HospitalCode').value = arrResult[0][0];
    document.all('HospitalName').value = arrResult[0][1];
    document.all('HospitalGrade').value = arrResult[0][2];
    document.all('ManageCom').value = arrResult[0][3];
    document.all('ValidityDate').value = arrResult[0][4];
    document.all('Address').value = arrResult[0][5];
    document.all('HospitalGradeName').value = arrResult[0][6];
    document.all('ManageComName').value = arrResult[0][7];
    document.all('HosState').value = arrResult[0][8];
    document.all('HosStateName').value = arrResult[0][9];
    document.all('Remark').value = arrResult[0][10];
                       
                
             fm.fmtransact.value = "QUERY";
             document.all('HospitalCode').disabled=true;
	 } else {
		 alert("��ѯ���ҽԺ��Ϣʧ�ܣ�");
		 return false;
	 }
   }
} 
function showCodeName()
{
	showOneCodeName('HospitalGrade','HospitalGrade','HospitalGradeName');
}

