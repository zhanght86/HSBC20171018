//�������ƣ�UWCustomerQuality.js
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var tResourceName="uw.LLUWHospitalQualitySql";

function queryClick()
{
	// ��ʼ�����
	//initQuestGrid();
	//initContent();
	
	// ��дSQL���
  /*var strSql = "select PrtNo, ProposalContNo, NotePadCont, Operator, MakeDate, MakeTime from LCNotePad where PrtNo='" + PrtNo 
             + "' and ContNo='" + ContNo + "' and OperatePos='" + OperatePos + "'"
             + getWherePart('Operator', 'Operator')
             + getWherePart('MakeDate', 'MakeDate');*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[PrtNo,ContNo,OperatePos,document.all('Operator').value,document.all('MakeDate').value]);          
	
  turnPage.queryModal(strSql, QuestGrid); 
  
  fm.Content.value = "";
}


function showOne(parm1, parm2) {	
  //�жϸ����Ƿ�ȷʵ��ѡ��
	if(document.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = document.all(parm1).all('QuestGridNo').value - 1;
	  fm.Content.value = turnPage.arrDataCacheSet[index][2];
  }
}

function UpdateClick() {
 // if (trim(fm.Remark.value) == "") {
//    alert("������д��ע���ݣ�");
//    return;
//  }
//

  //У�����ҽԺ�����Ƿ�¼��
  if(document.all('HospitalCode').value==null||document.all('HospitalCode').value=='')
  {
  	 alert("����ѡ�����ҽԺ����!");
  	 return false;
  }
  getAllChecked();
  //return false;
  
  fm.PrtNo.value = PrtNo;
  fm.ContNo.value = ContNo;
//  fm.OperatePos.value = OperatePos; 
  
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {   
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
  initForm(PrtNo);
}

function initHospitCode()
{
	//alert(cCodeName+":"+Field.value);
 	//ҽԺ����,ҽԺ����,��������,��������
 	//alert(fm.customer.value);
 	/*var strSQL="select a.hospitcode,a.hospitalname,a.mngcom,(select shortname from ldcom where comcode=a.mngcom) "
 	          + " from ldhospital a ,lluwpenotice b"
 	          + " where a.hospitcode = b.hospitcode and b.ContNo='"+ContNo+"'"
 	          + " order by b.modifydate desc,b.modifytime desc";*/
 	var strSQL = wrapSql(tResourceName,"querysqldes2",[ContNo]);
 	//alert(strSQL);
   var arrResult=easyExecSql(strSQL);
   //alert(arrResult);
   if(arrResult!=null)
   {                     
    	document.all('HospitalCode').value = arrResult[0][0];
    	document.all('HospitalCode1').value = arrResult[0][0];
    	document.all('HospitalName').value = arrResult[0][1];
    	document.all('ManageCom').value = arrResult[0][2];
    	document.all('ManageComName').value = arrResult[0][3];
    	//��ѯ������¼.
	  	/*var tSQL = "select hospitalquality,managerquality,insidequality,qualityflag,remark,"
	  	         + " (select codename from ldcode where codetype='qualityflag' and code=a.qualityflag) "
	  	         + " from LCHospitalQualityRecord a "
	             + " where hospitcode='"+document.all('HospitalCode').value+"' and contno='"+ContNo+"' ";*/
	  	var tSQL = wrapSql(tResourceName,"querysqldes3",[document.all('HospitalCode').value,ContNo]);
	  	var arrResult1=easyExecSql(tSQL);
	  	if(arrResult1!=null)
	   	{
	   		 //��ʼ�����ҽԺ����
	   		 showBodyCheck(arrResult1[0][0],'Score');
	   		 showBodyCheck(arrResult1[0][1],'Manage');
	   		 showBodyCheck(arrResult1[0][2],'Inner');
	   		 document.all('QualityFlag').value = arrResult1[0][3];
	   		 document.all('QualityFlagName').value = arrResult1[0][5];
	    	document.all('Remark').value = arrResult1[0][4];
	  	}
  	}
  	else
  	{
  		 alert("���ҽԺ��ѯʧ��!û�б����˼��������Ϣ!");
  		 return false;
  	}	
  	  
   return;       
}	

function afterCodeSelect( cCodeName, Field )
{
	//alert(cCodeName+":"+Field.value);
 if(cCodeName=="HospitalCode"){
 	//ҽԺ����,ҽԺ����,��������,��������
 	//alert(fm.customer.value);
 	/*var strSQL="select hospitcode,hospitalname,mngcom,(select shortname from ldcom where comcode=a.mngcom) "
 	          + " from ldhospital a "
 	          + " where hospitcode = '"+Field.value+"'";*/
 	var strSQL = wrapSql(tResourceName,"querysqldes4",[Field.value]);
 	//alert(strSQL);
   var arrResult=easyExecSql(strSQL);
   //alert(arrResult);    
   if(arrResult!=null)
   {                     
    //	document.all('HospitalCode').value = arrResult[0][0];
    	document.all('HospitalCode1').value = arrResult[0][0];
    	document.all('HospitalName').value = arrResult[0][1];
    	document.all('ManageCom').value = arrResult[0][2];
    	document.all('ManageComName').value = arrResult[0][3];
  	}
  	else
  	{
  		 alert("���ҽԺ��ѯʧ��!");
  		 return false;
  	}
  	
  	//��ѯ������¼.
  	/*var tSQL = "select hospitalquality,managerquality,insidequality,qualityflag,remark,"
  	         + " (select codename from ldcode where codetype='qualityflag' and code=a.qualityflag) "
  	         + " from LCHospitalQualityRecord a "
             + " where hospitcode='"+document.all('HospitalCode').value+"' and contno='"+PrtNo+"' ";*/
  	var tSQL = wrapSql(tResourceName,"querysqldes3",[document.all('HospitalCode').value,PrtNo]);
  	var arrResult1=easyExecSql(tSQL);
  	if(arrResult1!=null)
   	{
   		 //��ʼ�����ҽԺ����
   		 showBodyCheck(arrResult1[0][0],'Score');
   		 showBodyCheck(arrResult1[0][1],'Manage');
   		 showBodyCheck(arrResult1[0][2],'Inner');
   		 document.all('QualityFlag').value = arrResult1[0][3];
   		 document.all('QualityFlagName').value = arrResult1[0][5];
    	document.all('Remark').value = arrResult1[0][4];
  	}
   
   return;       
 }	
// if(cCodeName=="paymode"){
//   	if(document.all('PayMode').value=="4"){
//   	  //divLCAccount1.style.display="";
//    }
//    else
//    {
//   	  //divLCAccount1.style.display="none";
//      //alert("accountImg===");	
//    }
// }
 //alert("aaa");
// afterCodeSelect2( cCodeName, Field );
}


function returnParent(){
  top.close();
	
}


function initHospitalCode(tPrtNo)
{
    var i,j,m,n;
    var returnstr;

    /*var strSql = " select hospitcode,(select hospitalname from ldhospital where hospitcode=a.hospitcode) "
        			 + " from lluwpenotice a where contno='"+tPrtNo+"' ";*/
    var strSql = wrapSql(tResourceName,"querysqldes5",[tPrtNo]);
    //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);

  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    //alert("��ѯʧ�ܣ�");
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
    for( i = 0;i < n; i++)
    {
        m = turnPage.arrDataCacheSet[i].length;
        //alert("M:"+m);
        if (m > 0)
        {
            for( j = 0; j< m; j++)
            {
                if (i == 0 && j == 0)
                {
                    returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
                }
                if (i == 0 && j > 0)
                {
                    returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
                }
                if (i > 0 && j == 0)
                {
                    returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
                }
                if (i > 0 && j > 0)
                {
                    returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
                }

            }
        }
        else
        {
            //alert("��ѯʧ��!!");
            return "";
        }
    }
}
else
{
    //alert("��ѯʧ��!");
    return "";
}
 // alert("returnstr:"+returnstr);
  fm.HospitalCode.CodeData = returnstr;
  //prompt('',returnstr);
  return returnstr;
}

//tongmeng 2008-10-10 add
//�����������Զ����������Ŀ
function showBodyCheck(SpiltString,ShowTYPE)
{
	clearAllCheck(ShowTYPE);
	
	var strValue;
     strValue=SpiltString.split("#");
    // alert(SpiltString+":"+strValue.length);
    for(n=0;n<strValue.length;n++)
    {
      //alert(strValue[n]);
      if(strValue[n]!=null&&strValue[n]!='')
      {
      	//alert("document.fm."+strValue[n]+".checked = true");
      	eval("document.fm."+strValue[n]+".checked = true");
      }
    }
}


//�������check,����Ϊ��ѡ��
function clearAllCheck(ShowTYPE)
{
  for (elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++)
		{
			if (window.document.forms[0].elements[elementsNum].type != null && window.document.forms[0].elements[elementsNum].type != "")
			{
				if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				&&window.document.forms[0].elements[elementsNum].ShowTYPE ==ShowTYPE
				//||window.document.forms[0].elements[elementsNum].ShowTYPE=='radio'
				)
				{
					//alert(window.document.forms[0].elements[elementsNum].ShowTYPE);
				  //alert(window.document.forms[0].elements[elementsNum].id);
				  window.document.forms[0].elements[elementsNum].checked= false;
				}
			}
		}
}


//��ȡ�����Ѿ�����ѡ������
function getAllChecked()
{
   var tAllChecked = "";
   var ShowTYPE = "";
   for(n=1;n<=3;n++)
   {
   	  if(n==1)
   	  {
   	  	ShowTYPE = "Score";
   	  }
   	  else if(n==2)
   	  {
   	  	 ShowTYPE = "Manage";
   	  }	
   	  else if(n==3)
   	  {
   	  	 ShowTYPE = "Inner";
   	  }	
   	  
   	  tAllChecked = "";
   		for (elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++)
			{
				if (window.document.forms[0].elements[elementsNum].type != null && window.document.forms[0].elements[elementsNum].type != "")
				{
					if(window.document.forms[0].elements[elementsNum].type=='checkbox'
					&&window.document.forms[0].elements[elementsNum].ShowTYPE==ShowTYPE
				//||window.document.forms[0].elements[elementsNum].type=='radio'
					)
					{
				  //alert(window.document.forms[0].elements[elementsNum].id);
				  	if(window.document.forms[0].elements[elementsNum].checked)
				  	{
				     	tAllChecked = tAllChecked + "#" + window.document.forms[0].elements[elementsNum].id;
				  	}
					}
				}
			}
			//alert(tAllChecked+":"+"fm.CheckedItem"+n+".value='"+tAllChecked+"'");
			eval("fm.CheckedItem"+n+".value='"+tAllChecked+"'");
		}
	//	alert(tAllChecked);
	//fm.CheckedItem.value = tAllChecked;
}