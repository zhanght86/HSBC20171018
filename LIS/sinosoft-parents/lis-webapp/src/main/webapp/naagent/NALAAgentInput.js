//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var turnPage = new turnPageClass();
var tResourceName="naagent.NALAAgentInputSql";

window.onfocus=myonfocus;
//ȫ�ֱ�����������˺ͱ��������н�С��branchattr
var branchattrlength;
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
//�ύ�����水ť��Ӧ����
function submitForm()
{
    
	//tongmeng 2006-05-16 add
	//����ְ����ѯ��ְ��
//	alert("11:"+document.all('AgentGrade').value);
	if (queryOldAgentGrade()== false ) return false; 
//	alert("22:"+document.all('AgentGrade').value);
	//alert(mOperate);
	if (!confirm('ȷ�����Ĳ���'))
	{
		return false;
	}
  if (mOperate!='DELETE||MAIN')
  {
	    if (!beforeSubmit())
	    {
	      return false;
		}

    var QuafEndDate=document.all('QuafEndDate').value;
    var currDate=document.all('currDate').value;
    var DevNo1=document.all('DevNo1').value;
    var BankAccNo=document.all('BankAccNo').value;
    var ManageCom=document.all('ManageCom').value;
    if(QuafEndDate<currDate)
    {   
    	alert('֤��������ڱ�����ڵ��ڵ�ǰ����!');
        return false;
    }
    //2010-04-21 zjc  ��������֧���ӽ��顣
    //alert(ManageCom);
    //alert(ManageCom.substring(0,6));
    if(ManageCom.substring(0,6)=='863502')
    {
 	   // ������Ա�����֤�϶��Ǵ��ڵ�.����Ҫ��У��.��ְ�Ĳ���������Ա.
		if (document.all('initAgentState').value != '02'&&document.all('WFlag').value=='zy') {
			var strSQL = "";
			/*strSQL = "select * from LAAgent where 1=1 and (branchtype='1' or (branchtype<>'1'  and agentstate<'03')) "
					+ getWherePart('QuafNo');*/
			strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('QuafNo').value]);
			//alert(strSQL);
			var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
			if (strQueryResult) {
				alert('�ô������ʸ�֤���Ѵ���!');
				document.all('QuafNo').value = '';
				return false;
			}
		}
    	if(DevNo1=null||DevNo1=='')
        {   
        	alert('��������չҵ֤��û��¼�룡');
            return false;
        }
    	if(BankAccNo=null||BankAccNo=='')
        {   
        	alert('�������󣬹��ʴ����ʺ�û��¼�룡');
            return false;
        }
    }

  }
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=350;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.hideOperate.value=mOperate;
  if (fm.hideOperate.value=="")
  {
    alert("�����������ݶ�ʧ��");
  }
//  showSubmitFrame(mDebug);
  if (mOperate == "INSERT||MAIN")
    document.all('AgentState').value = document.all('initAgentState').value;
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{ 
  showInfo.close();
  //var wageSQL="select lawage.agentcode from lawage,laagent where lawage.agentcode ='"+document.all('AgentCode').value+"' and lawage.agentcode=laagent.agentcode and laagent.agentstate<'03'";
  var wageSQL = wrapSql(tResourceName,"querysqldes2",[document.all('AgentCode').value]);
       var wageQueryResult=easyQueryVer3(wageSQL,1,1,1)
       if (wageQueryResult)
       {//by jiaqiangli 2006-07-26 ֻ��û���Ӷ��Ĵ����˲ſ����޸�������Ϣ     	
        document.all('AgentGrade1').disabled = true;
        document.all('ManageCom').disabled = true;
        document.all('IntroAgency').disabled = true;
        document.all('BranchCode').disabled = true;
        document.all('GroupManagerName').disabled = true;
        document.all('DepManagerName').disabled = true;
        //by jiaqiangli 2006-08-03
        document.all('DirManagerName').disabled = true;
        document.all('MajordomoName').disabled = true;
        document.all('RearAgent').disabled = true;
        document.all('RearDepartAgent').disabled = true;
        document.all('RearSuperintAgent').disabled = true;
        document.all('RearAreaSuperintAgent').disabled = true;
       }
  document.all('hideIsManager').value = false;
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
    if(document.all('initAgentState').value != '02')
    {
     	showDiv(operateButton,"true"); 
     	showDiv(inputButton,"false"); 
    }
    //ִ����һ������
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 

	document.all('AgentGrade').disabled = false;
	document.all('AgentGrade1').disabled = false;
    document.all('ManageCom').disabled = false;
    document.all('IntroAgency').disabled = false;
    document.all('BranchCode').disabled = false;
    document.all('GroupManagerName').disabled = false;
    document.all('DepManagerName').disabled = false;
    //by jiaqiangli 2006-08-03
    document.all('DirManagerName').disabled = false;
    document.all('MajordomoName').disabled = false;
    document.all('RearAgent').disabled = false;
    document.all('RearDepartAgent').disabled = false;
    document.all('RearSuperintAgent').disabled = false;
    document.all('RearAreaSuperintAgent').disabled = false;
    
	  initForm();
  }
  catch(re)
  {
  	alert("��NALAAgent.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  


//function changeIdType1()
//{
//   û��onchange aftercodeselect ����
   //}
function beforeSubmit()
{

   
  //��Ӳ���	
  //document.all('ManageCom').value = document.all('hideManageCom').value;
    
  //by jiaqiangli 2006-09-04
  //�ܼ��¼�룬Ҫ��ֻ�ж������������������˹���Ȩ�޽��в���
  if(document.all('initOperate').value == 'INSERT') {
     if(ControlA09() == false) return false;
  }
  
  if( verifyInput() == false ) return false;
  
  
  
  if(checkPhone()== false) return false;
  
  //add by jiaqiangli 2007-05-14 �������ʸ�֤��Ϊ16��20λ�����ֻ�Ӣ����ĸ
  if (document.all('QuafNo').value!=null && document.all('QuafNo').value!="") {
	 if (document.all('QuafNo').value.length!=16 && document.all('QuafNo').value.length!=20) {
		alert("�������ʸ�֤�Ų�Ϊ16��20λ�����ֻ�Ӣ����ĸ");
		return false;
	  }
  }

  //tongmeng 2006-06-18 add
  //���ӶԻ����汾��ά��
  if(!checkEdition())
     return false;
     
    
   if(document.all('IdType').value!=null && document.all('IdType').value!='' && document.all('IdType').value=='0') 
   {   if(document.all('Birthday').value==null||document.all('Birthday').value=='')
   
   {alert("�������²���Ϊ��");
    return false;
   }
   if(document.all('Sex').value==null||document.all('Sex').value=='')
   
   {alert("�Ա���Ϊ��");
    return false;
   }
   
  var strChkIdNo = chkIdNo(trim(document.all('IDNo').value),trim(document.all('Birthday').value),trim(document.all('Sex').value))
  if (strChkIdNo != "")
  {  	
        alert(strChkIdNo);
	return false;
  }
  }
//  alert("111:"+document.all('UpAgent').value);
//  alert("222:"+document.all('AgentGrade').value);
  if ((trim(document.all('UpAgent').value)=='')&&(trim(document.all('AgentGrade').value)<='A03'))
  {
     alert('����ȷ�������ۻ����ĸ����ˣ�');
     return false;	
  }  
  if (trim(document.all('UpAgent').value)=='')
  {  	
     if (confirm("���Ƚ����ϼ������˵�ά����"))
     {     	
        resetForm();
	return false;
     }
     else
     {
	if (!confirm("ȷ���ô�����û���ϼ�������"))
	{
	  alert("���Ƚ����ϼ������˵�ά����");
	  resetForm();
	  return false;
	}
     }
  }
 

  //alert(document.all('AgentGrade').value);
  //alert(document.all('ManagerCode').value+" , "+document.all('AgentGrade').value+','+document.all('upBranchAttr').value);
  if (!judgeManager(document.all('ManagerCode').value,document.all('AgentGrade').value,document.all('upBranchAttr').value))
    return false;
  //add by tongmeng 2005-06-07
  //���˱���,8613,8635֮�⣬���๫˾�Ĵ������ʸ�֤���벻��Ϊ��
  //modify by tongmeng 2005-06-10
  //����8651�����µĴ������ʸ�֤�������Ϊ��
 // alert(document.all("ManageCom").value.substring(0,4));
 //modify by tomgmeng 2005-06-10
 //�޸�����Ѿ���˾�Ĵ����ˣ��򲻶���������ʸ�֤�Ƿ�Ϊ�����ж�
 //alert(document.all("initOperate").value);
 if(document.all("initOperate").value=='INSERT')
 {
 	//if((document.all("ManageCom").value!='86110000')&&trim(document.all("ManageCom").value.substring(0,4))!='8613'&&trim(document.all("ManageCom").value.substring(0,4))!='8635'&&trim(document.all("ManageCom").value.substring(0,4))!='8651')

 	//add by tongmeng 2005-06-10
 	//��̬�������ݿ⣬�ж��ĸ������Ĵ�������ԱʱӦ������������ʸ�֤��
 	//othersignΪ1��� Ҫ���������������ʸ�֤ 
	//othersignΪ0 ,��������ʸ�֤����Ϊ��
 	//var mSQL="select othersign from ldcode where codetype='station' and code='"+document.all("ManageCom").value+"'";
 	var mSQL = wrapSql(tResourceName,"querysqldes3",[document.all('ManageCom').value]);
 	//alert(mSQL);
 	var msqlResult = easyQueryVer3(mSQL,1,1,1);
 	//alert(msqlResult);
    if(!msqlResult){ 
    	return false; 
    }
    else  
    {
    	var tarr = decodeEasyQueryResult(msqlResult);
        var mflag = tarr[0][0];
        if(mflag==null||mflag=='1'||mflag=='')
        {
  			var tQuafNo=document.all("QuafNo").value;
  			tQuafNo=trim(tQuafNo);
    		if(tQuafNo==null||tQuafNo=='')
    		{
     			 alert("�������ʸ�֤���벻��Ϊ��!");
      				return false;
    		}
 	}
    }
}
    
//١��2005-03-15����
//�ж����ӵ��������Ƿ��������(�Ƿ���ô����˵�ְ�����,�Ƿ���ô�������ͬһ�����������)
//modify by tongmeng 2005-06-21
if(document.all('initOperate').value!='UPDATE||PART')
{
	if(document.all('AgentGrade').value>='A04')
	{
		if(trim(document.all('RearAgent').value)!='')
			{
   				 if(!DecideRearAgent('1'))
   				 {
        			alert("���������������,����������!");
    				document.all('RearAgent').value='';
        			return false;
    			 }
			}
	if(trim(document.all('RearDepartAgent').value)!='')
	{
    	if(!DecideRearAgent('2'))
    	{
        	alert("���������������,����������!");
    		document.all('RearDepartAgent').value='';
    		return false;
    	}
	}
	if(document.all('RearSuperintAgent').value!='')
	{
    	if(!DecideRearAgent('3'))
    	{
    		alert("���������������,����������!");
    		document.all('RearSuperintAgent').value='';
    		return false;
    	}
	}
	if(document.all('RearAreaSuperintAgent').value!='')
	{
    	if(!DecideRearAgent('4'))
    	{
       		alert("�ܼ��������������,����������!");
    		document.all('RearAreaSuperintAgent').value='';
    		return false;
    	}
	}
	}
}
  //tongmeng 2006-05-16 add
  //�����Ա��Ϊ��,��ʾ
  if(document.all('IntroAgency').value=='')
  {
  	if(!confirm("�Ƽ���Ϊ�գ��Ƿ񱣴�?"))
  	{
  		return false;
  	}
  }
   //���Ӧ�е��������Ƿ�¼����ȫ  tongmeng   
  if(document.all('AgentGrade').value>='A04')
  {
  	 if(!checkAllRearAgent())
  		return false;
  }
  //tongmeng 2006-05-16 add
  //�����Աһ������,���ӶԸ��������˵�У��
  //1 - �������������ͬʱΪ�� �������
  //2 - ���
  if (!checkRearAgent())
    return false;
  
  //by jiaqiangli 2006-07-28
  //��������Լ������
  //by jiaqiangli 2006-08-31 ����������ֻ����Աʱ
  if(document.all('initOperate').value == 'INSERT') {
  if (!checkRearRestrict())
     return false;
  }
  //modify by tongmeng 2006-02-09
  //ȥ������Ա��˾��������� 
  //
  /*
  //������Ա��Ա��������У��  
  //modify by pengcheng 2005-09-20
  if(document.all('initOperate').value == 'INSERT')
  {
   if(!checkage())
    return false;
  }
  */
    
  //��鵣������Ϣ�Ƿ�¼�� 
  //by jiaqiangli 2006-07-25
  //���е����˵���Ϣ��Ϊ��  
  if(!WarrantorGrid.checkValue())
    return false;
     
    var lineCount = 0;
    var tempObj = document.all('WarrantorGridNo'); //�����ڱ�fm��
    if (tempObj == null)
    {
      alert("����д��������Ϣ��");
      return false;
    }
    WarrantorGrid.delBlankLine("WarrantorGrid");
    lineCount = WarrantorGrid.mulLineCount;
    if (lineCount == 0)
    {
      alert("����д��������Ϣ��");
      return false;
    }else
    {
      var sValue;
      var strChkIdNo;	
      for(var i=0;i<lineCount;i++)
      {
      	sValue = WarrantorGrid.getRowColData(i,1);
      	//by jiaqiangli 2006-07-27 �˴��ķǿ�У����initWarrantorGrid��
      	//by jiaqiangli ע��˴���������ʽ��������е���
      	//by jiaqiangli ��ο�������������onkeyup�¼���������ʽ����
      	//by jiaqiangli 2007-03-20 ȡ���ַ�����������
      	if(checkWarrantorName(sValue))
      	{
      	   alert('Ҫ���'+(i+1)+'������������ֻ�����뺺�ֻ�Ӣ����ĸ');
      	   return false;
      	}
      	/*
      	sValue = WarrantorGrid.getRowColData(i,2);
      	if ((trim(sValue)=='')||(sValue==null))
      	{
      	   alert('�����뵣�����Ա�');
      	   return false;
      	}
      	sValue = WarrantorGrid.getRowColData(i,3);
      	if ((trim(sValue)=='')||(sValue==null))
      	{
      	   alert('�����뵣�������֤��');
      	   return false;
      	}*/
      	/*
      	sValue = WarrantorGrid.getRowColData(i,4);
      	if ((trim(sValue)=='')||(sValue==null))
      	{
      	   alert('�����뵣���˳������ڣ�');
      	   return false;
      	}*/
      	//by jiaqiangli 2006-07-27 ���ӵ������Ա������֤��У��
        strChkIdNo = WarrantorchkIdNo(trim(WarrantorGrid.getRowColData(i,3)),trim(WarrantorGrid.getRowColData(i,2)))
        if (strChkIdNo != "")
        {  	
          alert('��'+(i+1)+'��������'+strChkIdNo);
	  return false;
        }
      }	//end of for
    }
     var tbranchattr = document.all('BranchCode').value;
     var pstrSQL = "";
     // ١�� 2005-08-18 �޸�
     //����branchtype='1'����
        //pstrSQL = "select branchtype from labranchgroup where branchattr='"+tbranchattr+"' and branchtype='1'";
        pstrSQL = wrapSql(tResourceName,"querysqldes4",[tbranchattr]);
     var strQueryResult  = easyQueryVer3(pstrSQL, 1, 1, 1);
     var arr = decodeEasyQueryResult(strQueryResult);
     document.all('BranchType').value = trim(arr[0][0]);   
    
    document.all('AgentGrade').disabled = false;
    document.all('AgentGrade1').disabled = false;
    document.all('ManageCom').disabled = false;
    document.all('IntroAgency').disabled = false;
    document.all('BranchCode').disabled = false;
    document.all('GroupManagerName').disabled = false;
    document.all('DepManagerName').disabled = false;
    //by jiaqiangli 2006-08-03
    document.all('DirManagerName').disabled = false;
    document.all('MajordomoName').disabled = false;
    document.all('RearAgent').disabled = false;
    document.all('RearDepartAgent').disabled = false;
    document.all('RearSuperintAgent').disabled = false;
    document.all('RearAreaSuperintAgent').disabled = false;
    
    //by jiaqiangli 2006-08-04
    //����Ӧ������������˴������
    ControlRearInput();
    //by jiaqiangli 2006-09-06
    //�������޸ĵ����������֤�����±�Ϊ���ң�����save.jsp�ò���ֵ
    if (document.all('Name').disabled == true)
        document.all('Name').disabled = false;
    if (document.all('IdType').disabled == true)
        document.all('IdType').disabled = false;  
        
       
    if (document.all('IDNo').disabled == true)
        document.all('IDNo').disabled = false;
        
        //8g repair by jiaqiangli 2008-06-18
    if (document.all('BankCode').disabled == true)
        document.all('BankCode').disabled = false;
    if (document.all('BankAccNo').disabled == true)
        document.all('BankAccNo').disabled = false;
        
    return true;
}
//by jiaqiangli 2006-07-28 ��������Լ������
//������A��˾�����¼�벿������B����ôA��B����ͬ���������B��A��ֱ�����ܡ�
//��������C������BϽ�¡�����ְ����ͬ��
function checkRearRestrict()
{
	var lengtha,lengthb,branchattrlength;
	var branchattra,branchattrb=document.all('BranchCode').value;
	//��������ְ��:��a��ʾ������,��b��ʾ��������
	if(document.all('AgentGrade').value=='A09')
             lengthb=8;
        else if(document.all('AgentGrade').value=='A08')
             lengthb=10;
        else if(document.all('AgentGrade').value=='A07')
             lengthb=12;
        else if(document.all('AgentGrade').value=='A05') 
             lengthb=15;	
	var strSQL="";
	var strQueryResult;
	var arr;
	if(document.all('AgentGrade').value=='A05')
	{
		if(trim(document.all('RearAgent').value)!='')
		{
			//strSQL="select a.branchattr from labranchgroup a,latree b where a.agentgroup=b.agentgroup"
			  //    +" and b.agentcode='"+trim(document.all('RearAgent').value)+"' ";
			strSQL = wrapSql(tResourceName,"querysqldes5",[trim(document.all('RearAgent').value)]);
                        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                        arr=decodeEasyQueryResult(strQueryResult);
                        //���������ۻ���
                        branchattra=trim(arr[0][0]);
                        lengtha=branchattra.length;
                        //ȡ����֮��Ľ�С 
                        if(lengtha>=lengthb)
                            branchattrlength=lengthb;
                        else
                            branchattrlength=lengtha;
                        if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                        {
                        	alert('Ҫ�������˺���������'+document.all('RearAgent').value+'����ͬ�Ĳ������������ֱ������');
                        	document.all('RearAgent').value='';
                        	return false;
                        }  
		}
	}
        else if(document.all('AgentGrade').value=='A07')
        {
        	//���������˲�Ϊ�գ����������˿϶���Ϊ��
        	if(trim(document.all('RearDepartAgent').value)!='')
        	{
        		//strSQL="select a.branchattr from labranchgroup a,latree b where a.agentgroup=b.agentgroup"
			      //+" and b.agentcode='"+trim(document.all('RearDepartAgent').value)+"' ";
        		strSQL = wrapSql(tResourceName,"querysqldes5",[trim(document.all('RearDepartAgent').value)]);
                        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                        arr=decodeEasyQueryResult(strQueryResult);
                        //���������ۻ���
                        branchattra=trim(arr[0][0]);
                        lengtha=branchattra.length;
                        //ȡ����֮��Ľ�С 
                        if(lengtha>=lengthb)
                            branchattrlength=lengthb;
                        else
                            branchattrlength=lengtha;
                        if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                        {
                        	alert('Ҫ�������˺Ͳ�������'+document.all('RearDepartAgent').value+'����ͬ���������������ֱ������');
                        	document.all('RearDepartAgent').value='';
                        	return false;
                        }
                        //�ж����������Ƿ��ڲ������˵�Ͻ��,���������ͬһ���˵�������ж�(���Լ����Լ���Ͻ��)
                        if(trim(document.all('RearDepartAgent').value)!=trim(document.all('RearAgent').value))
                        {
                        	//��ʱ����������Բ�������Ϊ��������
                        	//strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              //+" and a.agentcode='"+trim(document.all('RearAgent').value)+"' ";
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //ȡ����֮��Ľ�С 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('�������˱����ڲ�������Ͻ��!');
                        	    document.all('RearAgent').value='';
                        	    return false;
                                }
                        } 
        	}
        } 
        else if(document.all('AgentGrade').value=='A08')
        {
        	if(trim(document.all('RearSuperintAgent').value)!='')
        	{
        		//strSQL="select a.branchattr from labranchgroup a,latree b where a.agentgroup=b.agentgroup"
			      //+" and b.agentcode='"+trim(document.all('RearSuperintAgent').value)+"' ";
        		strSQL = wrapSql(tResourceName,"querysqldes5",[trim(document.all('RearSuperintAgent').value)]);
                        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                        arr=decodeEasyQueryResult(strQueryResult);
                        //���������ۻ���
                        branchattra=trim(arr[0][0]);
                        lengtha=branchattra.length;
                        //ȡ����֮��Ľ�С 
                        if(lengtha>=lengthb)
                            branchattrlength=lengthb;
                        else
                            branchattrlength=lengtha;
                        if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                        {
                        	alert('Ҫ�������˺���������'+document.all('RearSuperintAgent').value+'����ͬ���ܼ��������ֱ������');
                        	document.all('RearSuperintAgent').value='';
                        	return false;
                        }
                        //�ж��鲿�������Ƿ����������˵�Ͻ�� 
                        if(trim(document.all('RearSuperintAgent').value)!=trim(document.all('RearDepartAgent').value))
                        {
                        	//��ʱ�������������������Ϊ��������
                        	/*strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              +" and a.agentcode='"+trim(document.all('RearDepartAgent').value)+"' ";*/
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearDepartAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //ȡ����֮��Ľ�С 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('�������˱�������������Ͻ��!');
                        	    document.all('RearDepartAgent').value='';
                        	    return false;
                                }
                        }
                        if(trim(document.all('RearSuperintAgent').value)!=trim(document.all('RearAgent').value))
                        {
                        	//��ʱ�������������������Ϊ��������
                        	/*strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              +" and a.agentcode='"+trim(document.all('RearAgent').value)+"' ";*/
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //ȡ����֮��Ľ�С 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('�������˱�������������Ͻ��!');
                        	    document.all('RearAgent').value='';
                        	    return false;
                                }
                        }
        	}
        }
        else if(document.all('AgentGrade').value=='A09')
        {
        	if(trim(document.all('RearAreaSuperintAgent').value)!='')
        	{
        		/*strSQL="select a.branchattr from labranchgroup a,latree b where a.agentgroup=b.agentgroup"
			      +" and b.agentcode='"+trim(document.all('RearAreaSuperintAgent').value)+"' ";*/
        		strSQL = wrapSql(tResourceName,"querysqldes5",[trim(document.all('RearAreaSuperintAgent').value)]);
                        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                        arr=decodeEasyQueryResult(strQueryResult);
                        //���������ۻ���
                        branchattra=trim(arr[0][0]);
                        lengtha=branchattra.length;
                        //ȡ����֮��Ľ�С 
                        if(lengtha>=lengthb)
                            branchattrlength=lengthb;
                        else
                            branchattrlength=lengtha;
                        if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                        {
                        	alert('Ҫ�������˺��ܼ�������'+document.all('RearAreaSuperintAgent').value+'����ͬ��Ӫ������');
                        	document.all('RearAreaSuperintAgent').value='';
                        	return false;
                        }
                        //�ж��鲿���������Ƿ����ܼ������˵�Ͻ��
                        if(trim(document.all('RearAreaSuperintAgent').value)!=trim(document.all('RearSuperintAgent').value))
                        {
                        	//��ʱ������������ܼ�������Ϊ��������
                        	/*strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              +" and a.agentcode='"+trim(document.all('RearSuperintAgent').value)+"' ";*/
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearSuperintAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //ȡ����֮��Ľ�С 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('�������˱������ܼ�������Ͻ��!');
                        	    document.all('RearSuperintAgent').value='';
                        	    return false;
                                }
                        }
                        if(trim(document.all('RearAreaSuperintAgent').value)!=trim(document.all('RearDepartAgent').value))
                        {
                        	//��ʱ������������ܼ�������Ϊ��������
                        	/*strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              +" and a.agentcode='"+trim(document.all('RearDepartAgent').value)+"' ";*/
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearDepartAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //ȡ����֮��Ľ�С 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('�������˱������ܼ�������Ͻ��!');
                        	    document.all('RearDepartAgent').value='';
                        	    return false;
                                }
                        }
                        if(trim(document.all('RearAreaSuperintAgent').value)!=trim(document.all('RearAgent').value))
                        {
                        	//��ʱ������������ܼ�������Ϊ��������
                        	/*strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              +" and a.agentcode='"+trim(document.all('RearAgent').value)+"' ";*/
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //ȡ����֮��Ľ�С 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('�������˱������ܼ�������Ͻ��!');
                        	    document.all('RearAgent').value='';
                        	    return false;
                                }
                        } 
        	}
        } 
        return true;
}
//by jiaqiangli 2006-07-27 ���ӵ���������У�� 
function checkWarrantorName(tName)
{
   //by jiaqiangli �����������ʹ���������һ��ֻ�����뺺�ֺ�Ӣ����ĸ
   //by jiaqiangli ������ʽע�� ע��˴��е���
   ///[^A-Za-z\u4E00-\u9FA5]/g ��ȥƥ�人�ֺ�Ӣ����ĸ������ַ� 
   var namePattern=/[^A-Za-z\u4E00-\u9FA5]/g; //ƥ�人�ֺ�Ӣ����ĸ
   if(!namePattern.test(tName)) 
   {
   	return false;
   }
   return true;
}       
/**
 * by jiaqiangli 2006-07-27 У�鵣�����Ա�����֤��
 * �����������֤�����룬�Ա�
 * ����  ����ֵ
 */
function WarrantorchkIdNo(iIdNo,iSex)
{
  var tmpStr="";
  var tmpInt=0;
  var strReturn = "";
  iIdNo = trim(iIdNo);
  iSex = trim(iSex);
  if ((iIdNo.length!=15) && (iIdNo.length!=18))
  {
    strReturn = "��������֤��λ������";
    return strReturn;
  }
  if (iSex!='0' && iSex!='1')
  {
  	strReturn = "������Ա���ȷ";
  	return strReturn;
  }
  if (iIdNo.length==15)
  {
      if (iSex=="0")
      {
      	tmpInt = parseInt(iIdNo.substring(14));
      	tmpInt = tmpInt % 2
      	if (tmpInt==0)
      	{
      	  strReturn = "������Ա������֤�ŵ���Ϣ��һ��";
          return strReturn;
      	}
      }
      else
      {
      	tmpInt = parseInt(iIdNo.substring(14));      	
      	tmpInt = tmpInt % 2
      	if (tmpInt!=0)
      	{
      	  strReturn = "������Ա������֤�ŵ���Ϣ��һ��";
          return strReturn;
      	}
      }  
      return strReturn;
  }
  if (iIdNo.length==18)
  {
      if (iSex=="0")
      {
      	tmpInt = parseInt(iIdNo.substring(16,17)); 
      	tmpInt = tmpInt % 2
      	if (tmpInt==0)
      	{
      	  strReturn = "������Ա������֤�ŵ���Ϣ��һ��";
          return strReturn;
      	}
      }
      else
      {
      	tmpInt = parseInt(iIdNo.substring(16,17));      	
      	tmpInt = tmpInt % 2
      	if (tmpInt!=0)
      	{
      	  strReturn = "������Ա������֤�ŵ���Ϣ��һ��";
          return strReturn;
      	}
      }
      return strReturn;
  }
}
//by jiaqiangli 2006-07-07 �绰���ֻ�Ҫ������һ����Ϊ��
function checkPhone()
{
	if(document.all('Phone').value==''&&document.all('Mobile').value=='')
	{
		alert('�绰���ֻ�Ҫ������һ����Ϊ��');
		return false;
	}
	return true;
}
//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,500,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  if (document.all('initOperate').value == 'INSERT')
  {
    mOperate="INSERT||MAIN";
    showDiv(operateButton,"false"); 
    showDiv(inputButton,"true"); 
    
    //document.all('AgentCode').value = '';
    if (document.all('AgentCode').value !='')
      resetForm();
  }else
    alert('�ڴ˲���������');	
}             

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{ if (document.all('initOperate').value == 'INSERT')
  {
    alert('�ڴ˲����޸ģ�');	
  }else
  {
    if ((document.all("AgentCode").value == null)||(document.all("AgentCode").value == ''))
    {
       alert('���Ȳ�ѯ��Ҫ�޸ĵĴ����˼�¼��');    
       document.all('AgentCode').focus();
    }else
    {
       //����������Ӧ�Ĵ���
       if (confirm("��ȷʵ���޸ĸü�¼��?"))
       {  	
          mOperate=document.all('initOperate').value;
          submitForm();
       }
       else
       {
         mOperate="";
         alert("��ȡ�����޸Ĳ�����");
       }
    }
  }
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{//����������Ӧ�Ĵ���
  mOperate="QUERY||MAIN";
  showInfo=window.open("./NALAAgentQueryInput.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  if (document.all('initOperate').value == 'INSERT')
  {
     alert('�ڴ˲���ɾ����');	
  }else
  {
     if ((document.all("AgentCode").value == null)||(document.all("AgentCode").value == ''))
     {
       alert('���Ȳ�ѯ��Ҫɾ���Ĵ����˼�¼��');    
       document.all('AgentCode').focus();
     }else
     {	
         //����������Ӧ��ɾ������
         if (confirm("��ȷʵ��ɾ���ü�¼��?"))
         {
           mOperate="DELETE||MAIN";  
           submitForm();
         }
         else
         {
           mOperate="";
           alert("��ȡ����ɾ��������");
         }
     }
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

function changeGroup()
{//by jiaqiangli 2006-07-24 �����Ʋ���
//   if (getWherePart('BranchCode')=='')
//     return false;
   if (queryOldAgentGrade()== false ) return false;  
   var tAgentGrade = trim(document.all('AgentGrade').value);
   if (tAgentGrade==null ||tAgentGrade==''){
     alert('����¼�������ְ����');
     document.all('BranchCode').value = '';
     return false;
   }
   
   var strSQL = "";
   /*strSQL = "select BranchAttr,ManageCom,BranchManager,AgentGroup,BranchManagerName,UpBranch,UpBranchAttr "
           +" from LABranchGroup where 1=1 "
           +" and BranchType = '1' and EndFlag <> 'Y' and BranchLevel = '01' and (state<>'1' or state is null)"
           + getWherePart('BranchAttr','BranchCode');
           //+ getWherePart('ManageCom');	*/
   strSQL = wrapSql(tResourceName,"querysqldes7",[document.all('BranchCode').value]);
     	 //alert(strSQL);
   var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('�����ڸ����ۻ�����');
   	document.all('BranchCode').value = '';
   	document.all('UpAgent').value = '';
   	document.all('GroupManagerName').value = '';
   	document.all('DepManagerName').value = '';
   	document.all('ManageCom').value = '';
   	//document.all('hideManageCom').value = '';
   	document.all('hideBranchCode').value = '';
   	document.all('ManagerCode').value = '';
   	document.all('upBranchAttr').value = '';
   	return false;
   }  
   var arr = decodeEasyQueryResult(strQueryResult);
   //�жϹ�����Ա
   //if (!judgeManager(arr[0][2],tAgentGrade,arr[0][6]))
     //return false;
   document.all('ManageCom').value = trim(arr[0][1]);
   //document.all('hideManageCom').value = trim(arr[0][1]);
   document.all('hideBranchCode').value = trim(arr[0][3]);
   document.all('ManagerCode').value = trim(arr[0][2]);
   document.all('upBranchAttr').value = trim(arr[0][6]);
   //var tBranchLevel = trim(arr[0][4]);
   if (tAgentGrade <= 'A05')
   {
     document.all('GroupManagerName').value = trim(arr[0][4]); //�龭��
     if (tAgentGrade <= 'A03')
        document.all('UpAgent').value = trim(arr[0][2]);
   }   
   
   //�ж��ϼ�������ȷ���ϼ�������
   if (arr[0][5]!=null && trim(arr[0][5])!='')
   {
        var tUpBranch = trim(arr[0][0]);  
        //�õ��ô����˵�ְ����Ӧ�Ļ�����ʾ����
        switch (eval(tAgentGrade.substr(1)))
        {
          case 4,5:
             tUpBranch = document.all('BranchCode').value;
             break;
          case 6: 
             tUpBranch = tUpBranch.substr(0,tUpBranch.length-3); //�߼�����
             break;
          case 7: 
             tUpBranch = tUpBranch.substr(0,tUpBranch.length-3); //�߼�����
             break;
          case 8: 
             tUpBranch = tUpBranch.substr(0,tUpBranch.length-6);   //������
             break;
        } 
          	
   	//��ѯ�ϼ�����
   	//modify by tongmeng 2005-08-01
   	//����branchtype='1'������
        /*strSQL = "select AgentGroup,BranchManager,BranchManagerName from LABranchGroup where 1=1 "
                +" and EndFlag <> 'Y' and AgentGroup = (select UpBranch from LABranchGroup where branchAttr = '"+tUpBranch+"' and branchtype='1' )" 
                +" and (state<>'1' or state is null) and branchtype='1' ";*/	
        strSQL = wrapSql(tResourceName,"querysqldes8",[tUpBranch]);
     	 //alert(strSQL);
     	// prompt("11"+strSQL);
     	 //
        strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
        if (!strQueryResult)
        {
           //if (document.all('AgentGrade').value!='A09')
   	     //alert('��¼���ۻ������ϼ�����������,�޷���ʾ������');
   	   //return false;
        }else
        {  
          arr = decodeEasyQueryResult(strQueryResult);
          //if (document.all('hideIsManager').value == 'true')
          if (tAgentGrade > 'A03')
            document.all('UpAgent').value = trim(arr[0][1]);
          if (tAgentGrade < 'A06')
            document.all('DepManagerName').value = trim(arr[0][2]);
        }
   }
   //by jiaqiangli 2006-07-27
   queryDistMajor(document.all('BranchCode').value);       
   return true;
}
//by jiaqiangli 2006-07-27 ��ѯ�����ܼ�����
function queryDistMajor(tBranchAttr)
{
   //by jiaqiangli 2006-07-25 ��������������
   var tupbranch=tBranchAttr.substr(0,12); 
  /* var tsql="select BranchManagerName from LABranchGroup where 1=1 "
           +" and EndFlag <> 'Y' and branchAttr = '"+tupbranch+"' " 
           +" and (state<>'1' or state is null) and branchtype='1' ";*/
   var tsql = wrapSql(tResourceName,"querysqldes9",[tupbranch]);
   var tQueryResult=easyQueryVer3(tsql, 1, 1, 1); 
   var tarr;
   if(tQueryResult)
   {
   	tarr=decodeEasyQueryResult(tQueryResult);
   	document.all('DirManagerName').value=tarr[0][0];
   }
   //by jiaqiangli 2006-07-25 �ܼ�����
   tupbranch=tBranchAttr.substr(0,10);
   /*tsql="select BranchManagerName from LABranchGroup where 1=1 "
           +" and EndFlag <> 'Y' and branchAttr = '"+tupbranch+"' " 
           +" and (state<>'1' or state is null) and branchtype='1' ";*/
   tsql = wrapSql(tResourceName,"querysqldes9",[tupbranch]);
   tQueryResult=easyQueryVer3(tsql, 1, 1, 1);
   if(tQueryResult)
   {
   	tarr=decodeEasyQueryResult(tQueryResult);
   	document.all('MajordomoName').value=tarr[0][0];
   } 
   return true;
}
function judgeManager(cManager,cAgentGrade,cZSValue)
{
   if ((cManager==null)||(trim(cManager)==''))
   {
   	//��Ա�Ǿ����ĵĴ����˲��ܲ��� 
   	if (cAgentGrade <= 'A03')
   	{
   		alert('������Ա�����ۻ����Ĺ�����Ա��');
   		document.all('AgentGrade').value = '';
   		return false;
        }
        //��Ա�����Ĵ����������˵�ְ�������ۻ����ļ���һ������ʾ���ô�������Ϊ������Ա
        else
        {     
        	if (cAgentGrade > 'A03')
        	{
        	    if (trim(cZSValue)=='0' && cAgentGrade > 'A05')
        	    {
        	    	 alert("��Ӫҵ��Ϊ��ֱϽ�飬�ô����˲����ڸ��飡");
   	                 document.all('BranchCode').value = '';
   	                 document.all('UpAgent').value = '';
   	                 document.all('GroupManagerName').value = '';
   	                 document.all('DepManagerName').value = '';
   	                 document.all('ManageCom').value = '';
   	                 //document.all('hideManageCom').value = '';
   	                 document.all('hideBranchCode').value = '';
   	                 document.all('ManagerCode').value = '';
   	                 document.all('upBranchAttr').value = '';
        	    	 return false;
        	    }
        	    var str = "";
        	    if (cAgentGrade < 'A06')
        	    {
        	      str = "�Ƿ�ָ���ô�����Ϊ�����۵�λ�Ĺ�����Ա��";
        	    }
        	    else
        	    {
        	    	//У��������Ա�ļ�����ֱϽ��ĸ����������Ƿ��Ӧ
        	    	var tBranch = trim(document.all('BranchCode').value);
        	    	var tAgentGrade = trim(document.all('AgentGrade').value);  
//        	    	alert(tAgentGrade);      	    	
                        switch (eval(tAgentGrade.substr(1)))
                        {
                           case 6: 
                           {
                            tBranch = tBranch.substr(0,tBranch.length-3); //�߼�����
                            break;
                          }
                          case 7: 
                          {
                           tBranch = tBranch.substr(0,tBranch.length-3); //�߼�����
                            break;
                          }
                          case 8: 
                          {
                            tBranch = tBranch.substr(0,tBranch.length-6);   //������
                            break;
                          }
                          case 9: 
                          {
                            tBranch = tBranch.substr(0,tBranch.length-8);   //��������
                            break;
                          }
                        } 
                        //modify by tongmeng 2005-08-01
                        //����branchtype='1'������
                       /* var strSQL = "select upBranchAttr from laBranchGroup where BranchAttr = '"+tBranch+"' and (state<>'1' or state is null)"
                                     +" and branchtype='1' ";	*/
                        var strSQL = wrapSql(tResourceName,"querysqldes10",[tBranch]);
                       // alert(strSQL);
                        var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
                        if (strQueryResult)
                        {                           	
                           var arr = decodeEasyQueryResult(strQueryResult);
                           if (arr[0][0]=='0')
                           {
        	              str = "�Ƿ�ָ�������۵�λΪ�ô����˵�ֱϽ�飿";
                           }
                           else
                           {
                              alert("��Ӫҵ�鲻�Ǹ�ְ����Ա��ֱϽ�飡");
                              document.all('AgentGrade').value = '';
                              return false;
                           }                              
                        }else
                        {
                        	alert("���ۻ���ά����ȫ��");
                        	return false;
                        }
        	    }
        	    if (confirm(str))
        	       document.all('hideIsManager').value = 'true';
        	    else
        	    {
        	       document.all('hideIsManager').value  = 'false';
        	       document.all('BranchCode').value     = '';
        	       //document.all('hideBranchCode').value = '';
        	       return false;
        	    }
        	}        	
        }
   }else
   {
   	//alert('  '+cManager);
   	var tACode = document.all('AgentCode').value;
   	if (tACode == null)
   	  tACode = '';
   	  ////////////////////////////////////
   	  //tongmeng 2005-03-09�޸� ���ӶԸû����Ĺ�����Ա�Ƿ���ְ���ж�
   	  if((cAgentGrade>'A03')&&(tACode != cManager))
   	  {var LSQL = "";
   	  	//LSQL="select agentcode from latree where agentcode ='"+cManager+"' and state<>'3'";
   	 LSQL = wrapSql(tResourceName,"querysqldes11",[cManager]);
   	  	//alert('cManager'+cManager);
       var LSQLResult=easyQueryVer3(LSQL,1,1,1)
       if(LSQLResult){
       	alert('�����۵�λ�Ѵ��ڹ�����Ա��');
   	      	document.all('BranchCode').value = '';
   	        document.all('UpAgent').value = '';
   	        document.all('GroupManagerName').value = '';
   	        document.all('DepManagerName').value = '';
   	        //document.all('hideManageCom').value = '';
   	        document.all('ManageCom').value = '';
   	        document.all('hideBranchCode').value = '';
                document.all('ManagerCode').value = '';
   	        document.all('upBranchAttr').value = '';
   	        document.all('DirManagerName').value = '';
   	        document.all('MajordomoName').value = '';
   	      	return false;
       	    }
   	  	}
   	  ////////////////////////////////////
   	  
  /* 	if ((cAgentGrade > 'A03')&&(tACode != cManager))
   	{
   		alert('�����۵�λ�Ѵ��ڹ�����Ա��');
   	      	document.all('BranchCode').value = '';
   	        document.all('UpAgent').value = '';
   	        document.all('GroupManagerName').value = '';
   	        document.all('DepManagerName').value = '';
   	        //document.all('hideManageCom').value = '';
   	        document.all('ManageCom').value = '';
   	        document.all('hideBranchCode').value = '';
                document.all('ManagerCode').value = '';
   	        document.all('upBranchAttr').value = '';
   	      	return false;
   	}*/
   	
   	if (cAgentGrade > 'A03')
   	   document.all('hideIsManager').value = 'true';
        else
           document.all('hideIsManager').value  = 'false'; 	
   }
   return true;
}

function changeIntroAgency()
{	
     //by jiaqiangli 2006-07-24
     //������������Ƽ��˺�����Ƽ��˴���ʱ�����޷��ƶ�������
//   if (getWherePart('IntroAgency')=='')
//     return false;
   var tagentcode=trim(document.all('AgentCode').value);
   var tintroagency=trim(document.all('IntroAgency').value);
   if(tagentcode != '')
   {
     if (tintroagency==tagentcode)
     {
        alert('������ԭ�����˱�����ͬ!')
       document.all('IntroAgency').value = '';
       return false;
     }
   }
   //by jiaqiangli �����Ƽ��˹������������ 6λ�������һ��
   //by jiaqiangli 2006-08-31 6λУ�������Ա��
//   if(document.all('initOperate').value == 'INSERT') {
       if(!checkIntroMag(document.all('ManageCom').value))
       {
          return false;
       }
//   }
   if(tintroagency != '') {
       var strSQL = "";
       //١�� 2005-08-18 �޸�
       // ����branchtype='1'����
       /*strSQL = "select AgentCode,ManageCom, AgentGroup from LAAgent  where 1=1 "
           + "and (AgentState is null or AgentState < '03') "
           +" and branchtype='1' "
           //+ "and a.AgentGroup = b.AgentGroup "
           //+ getWherePart('a.AgentGroup','hideAgentGroup')
           //+ getWherePart('a.ManageCom','ManageCom')
           + getWherePart('AgentCode','IntroAgency');	*/
       strSQL = wrapSql(tResourceName,"querysqldes12",[document.all('IntroAgency').value]);
       //alert(strSQL);
       var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
       if (!strQueryResult)
       {
   	   alert('�����ڸô����ˣ�');
   	   document.all('IntroAgency').value = '';
   	   return false;
        }  
 	//tongmeng 2006-05-16 add
 	getInputAgentName(1);
  }
   /*
   var arr = decodeEasyQueryResult(strQueryResult);
   document.all('AgentGroup').value = arr[0][1]
   document.all('ManageCom').value = arr[0][2];
   //document.all('hideManageCom').value = arr[0][2];
   document.all('hideAgentGroup').value = arr[0][3];*/
   return true;
}
//by jiaqiangli �����Ƽ��˹������������ 6λ�������һ��
function checkIntroMag(tManageCom)
{	//����Ϊ��,��϶����ڴ˴�����
	if((document.all('IntroAgency').value) != '')
	{
		var strSQL = "";
                /*strSQL = "select ManageCom from LAAgent  where 1=1 "
                + "and (AgentState is null or AgentState < '03') "
                +" and branchtype='1' "
                + getWherePart('AgentCode','IntroAgency');*/
                strSQL = wrapSql(tResourceName,"querysqldes13",[document.all('IntroAgency').value]);
                var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
                if (strQueryResult) {	
                   var arr = decodeEasyQueryResult(strQueryResult);
                   if ((tManageCom == null)||(tManageCom == '')) {
                   	alert('����¼�����ۻ�����¼���Ƽ���');
                	document.all('IntroAgency').value='';
                	return false;
                   }
                   else {
                      if(tManageCom.substr(0,6)!=arr[0][0].substr(0,6))
                      {
                	alert('�Ƽ����뱻�Ƽ���ǰ6λ���������һ��');
                	document.all('IntroAgency').value='';
                	return false;
                      } 
                  } 
                }
	}
	return true;
}
function afterQuery(arrQueryResult)
{	
	var arrResult = new Array();
	
	resetForm();
	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;		                                  
                document.all('AgentCode').value = arrResult[0][0];
                //document.all('Password').value = arrResult[0][3];
                //document.all('EntryNo').value = arrResult[0][4];
                document.all('Name').value = arrResult[0][5];
                document.all('Sex').value = arrResult[0][6];
                document.all('Birthday').value = arrResult[0][7];
                document.all('NativePlace').value = arrResult[0][8];
                document.all('Nationality').value = arrResult[0][9];
                //document.all('Marriage').value = arrResult[0][10];
                //document.all('CreditGrade').value = arrResult[0][11];
                //document.all('HomeAddressCode').value = arrResult[0][12];
                document.all('HomeAddress').value = arrResult[0][13];
                //document.all('PostalAddress').value = arrResult[0][14];
                document.all('ZipCode').value = arrResult[0][15];
                document.all('Phone').value = arrResult[0][16];
                document.all('BP').value = arrResult[0][17];
                document.all('Mobile').value = arrResult[0][18];
                document.all('EMail').value = arrResult[0][19];
                //document.all('MarriageDate').value = arrResult[0][20];
                document.all('IDNo').value = arrResult[0][21];
                //document.all('Source').value = arrResult[0][22];
                //document.all('BloodType').value = arrResult[0][23];
                document.all('PolityVisage').value = arrResult[0][24];
                document.all('Degree').value = arrResult[0][25];
                document.all('GraduateSchool').value = arrResult[0][26];
                document.all('Speciality').value = arrResult[0][27];
                document.all('PostTitle').value = arrResult[0][28];
                //document.all('ForeignLevel').value = arrResult[0][29];
                //document.all('WorkAge').value = arrResult[0][30];
                document.all('OldCom').value = arrResult[0][31];
                document.all('OldOccupation').value = arrResult[0][32];
                document.all('HeadShip').value = arrResult[0][33];
                //document.all('RecommendAgent').value = arrResult[0][34];
                //document.all('Business').value = arrResult[0][35];
                //document.all('SaleQuaf').value = arrResult[0][36];
                document.all('QuafNo').value = arrResult[0][37];
                //document.all('QuafStartDate').value = arrResult[0][38];
                document.all('QuafEndDate').value = arrResult[0][39];
                document.all('DevNo1').value = arrResult[0][40];
                //document.all('DevNo2').value = arrResult[0][41];
                //document.all('RetainContNo').value = arrResult[0][42];
                //document.all('AgentKind').value = arrResult[0][43];
                //document.all('DevGrade').value = arrResult[0][44];
                //document.all('InsideFlag').value = arrResult[0][45];
                //document.all('FullTimeFlag').value = arrResult[0][46];
                //document.all('NoWorkFlag').value = arrResult[0][47];
                document.all('TrainPeriods').value = arrResult[0][73];
                document.all('EmployDate').value = arrResult[0][49];
                //document.all('InDueFormDate').value = arrResult[0][50];
                //document.all('OutWorkDate').value = arrResult[0][51];
                //document.all('Approver').value = arrResult[0][57];
                //document.all('ApproveDate').value = arrResult[0][58];
                document.all('AssuMoney').value = arrResult[0][59];
                document.all('AgentState').value = arrResult[0][61];
                //document.all('QualiPassFlag').value = arrResult[0][62];
                //document.all('SmokeFlag').value = arrResult[0][63];
                document.all('RgtAddress').value = arrResult[0][64];
                document.all('BankCode').value = arrResult[0][65];
                document.all('BankAccNo').value = arrResult[0][66];
                document.all('Remark').value = arrResult[0][60];
                document.all('Operator').value = arrResult[0][67];   
                document.all('IdType').value=arrResult[0][77]
             //������Աʱ������ʾ��������ǰ��������Ϣ
             if (document.all('initAgentState').value != '02')
             {      
                //������Ϣ
//                alert('agentgroup:'+arrResult[0][81]);
                document.all('BranchCode').value = arrResult[0][81+1];
                document.all('hideBranchCode').value = arrResult[0][87+1];
                //document.all('hideManageCom').value = arrResult[0][2];
                document.all('ManageCom').value = arrResult[0][2];
                document.all('IntroAgency').value = arrResult[0][78+1];
                document.all('AgentSeries').value = arrResult[0][79+1];
                document.all('AgentGrade1').value = arrResult[0][80+1];
                //tongmeng 2006-05-18 add
                //��ѯ�ڲ�ְ��
                if (queryOldAgentGrade()== false ) return false; 
                //
                document.all('ManagerCode').value = arrResult[0][77+1];
   	        document.all('upBranchAttr').value = arrResult[0][86+1];
                if (arrResult[0][82+1]!=null && trim(arrResult[0][82+1])!='')
                {                	
                  if (arrResult[0][82+1].indexOf(":")!=-1)
                  {
                    var arrRear = arrResult[0][82+1].split(":");
                    document.all('RearAgent').value = arrRear.length>0?arrRear[0]:'';
                    document.all('RearDepartAgent').value = arrRear.length>1?arrRear[1]:'';
                    document.all('RearSuperintAgent').value = arrRear.length>2?arrRear[2]:'';
                    document.all('RearAreaSuperintAgent').value = arrRear.length>3?arrRear[3]:'';
                  }
                  else
                    document.all('RearAgent').value = arrResult[0][82+1];
                }
                //��ʽ��������
                //77-BranchManager 78-IntroAgency 79-AgentSeries 80-AgentGrade 81-BranchAttr(���������ʽ����) 
                //82-AscriptSeries 83-BranchLevel 84-upBranch 85-BranchManagerName 86-upBranchAttr 87-BranchCode(���������ʽ����)
                //alert(arrResult[0][77]+','+arrResult[0][0]);
                //trim(arrResult[0][77])!=trim(arrResult[0][0]) ;)&&(arrResult[0][80]<='A05')
                if (arrResult[0][77+1]!=null)//������Ա=�����˴���
                {
                  //if (arrResult[0][80] <= 'A03') 
                  if(document.all('AgentGrade').value<= 'A03')
                     document.all('UpAgent').value = arrResult[0][77+1]; //�龭�� 
                  document.all('GroupManagerName').value = arrResult[0][85+1];                 
                }
                //ȷ�������� arrResult[0][82]:�ϼ�����
                if ((arrResult[0][84+1]!=null)&&(trim(arrResult[0][84+1])!=''))
                {
                   /*var strSQL = "select BranchManager,BranchManagerName from LABranchGroup where 1=1 "
                              + " and EndFlag <> 'Y' and AgentGroup = '"+arrResult[0][84+1]+"' and (state<>'1' or state is null)";*/	
                   var strSQL = wrapSql(tResourceName,"querysqldes14",[arrResult[0][84+1]]);
     	           //alert('11--'+strSQL+'  '+arrResult[0][82]);
                   var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
                   if (strQueryResult)
                   {
                       var arr = decodeEasyQueryResult(strQueryResult);
                       if (trim(arrResult[0][77+1])==trim(arrResult[0][0]))
                         document.all('UpAgent').value = trim(arr[0][0]);
                       document.all('DepManagerName').value = trim(arr[0][1]); 
                   }
                }
                //if (arrResult[0][80] >= 'A06')
                if(document.all('AgentGrade').value>='A06')
                  document.all('DepManagerName').value = trim(arrResult[0][5]); //��������������
                //by jiaqiangli add 2006-07-27 ��Ҫ��ѯ����������ܼ�����
                queryDistMajor(trim(arrResult[0][81+1]));
            }
        }
        
        //tongmeng 2005-08-18 modify
        //add laagent.branchtype='1'
       //var wageSQL="select lawage.agentcode from lawage,laagent where lawage.agentcode ='"+document.all('AgentCode').value+"' and laagent.agentcode=lawage.agentcode and laagent.agentstate<'03' and laagent.branchtype='1'";
       var wageSQL = wrapSql(tResourceName,"querysqldes15",[document.all('AgentCode').value]);
       var wageQueryResult=easyQueryVer3(wageSQL,1,1,1)
       if (wageQueryResult)
       {//by jiaqiangli 2006-07-26 ���Ӷ��֮��������Ϣ�������޸�
        document.all('AgentGrade1').disabled = true;
        document.all('ManageCom').disabled = true;
        document.all('IntroAgency').disabled = true;
        document.all('BranchCode').disabled = true;
        document.all('GroupManagerName').disabled = true;
        document.all('DepManagerName').disabled = true;
        //by jiaqiangli 2006-08-03 �����ܼ�readonly���Ե�input���Բ������disabled����
        document.all('DirManagerName').disabled = true;
        document.all('MajordomoName').disabled = true;
        document.all('RearAgent').disabled = true;
        document.all('RearDepartAgent').disabled = true;
        document.all('RearSuperintAgent').disabled = true;
        document.all('RearAreaSuperintAgent').disabled = true;
       }
       
        
        WarrantorGrid.clearData("WarrantorGrid");
        easyQuery();
        //by jiaqiangli 2006-09-05
        //���ڻ�����Ϣά���У��������������֤�����޸ģ�Ҫ��ֻ�ж������������������˹���Ȩ�޽��в���
        //��ʼ��Ϣ�޸����ܹ�˾��������Ϣ�޸��ڷֹ�˾
        //by renhailong 2008-05-12
        //���ڻ�����Ϣά������ʼ��Ϣά�����Ͷ�����ԱҪ��ȫ����Ϊ�ܹ�˾
           //by 2009-08-14  ��Ȩ���·ŵ��ֹ�˾.
        if(document.all('hideManageCom').value.length > 4) {
        	   document.all('Name').disabled = true;
        	   document.all('IdType').disabled = true;
        	   document.all('IDNo').disabled = true;
        	   document.all('BankCode').disabled = true;
               document.all('BankAccNo').disabled = true;
     
       }
      
}
function easyQuery()
{  
   // ��дSQL���
   var strSQL = "";
   /*strSQL = "select * from LAWarrantor where 1=1 "
           + getWherePart('AgentCode');	*/
   strSQL = wrapSql(tResourceName,"querysqldes16",[document.all('AgentCode').value]);
     	 //alert(strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  
    //alert("��������Ϣ��ѯʧ�ܣ�");
    return false;
    }
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  //turnPage.arrDataCacheSet = chooseArray(tArr,[0,1,2,3,4,5]);
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = WarrantorGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = chooseArray(arrDataSet,[2,3,4,6,8,18,9,10,11]); 
  //����MULTILINE������ʾ��ѯ���
  
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}

function changeQuafNo()
{
    //onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')"
     //   onmouseover="value=value.replace(/[^a-zA-Z0-9]/g,'')"   
    //    onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^a-zA-Z0-9]/g,''))"          

	if (getWherePart('QuafNo')=='')
    {
     document.all('QuafNo').value = '';
   return false;
    }
   var strSQL = "";

   //������Ա�����֤�϶��Ǵ��ڵ�.����Ҫ��У��.��ְ�Ĳ���������Ա.
  if (document.all('initAgentState').value != '02')
   {
  //strSQL = "select * from LAAgent where 1=1 and (branchtype='1' or (branchtype<>'1'  and agentstate<'03')) "
  //+ getWherePart('QuafNo');	
  strSQL = wrapSql(tResourceName,"querysqldes17",[document.all('QuafNo').value]);
  //alert(strSQL);
  var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  if (strQueryResult)
  { 
  	alert('�ô������ʸ�֤���Ѵ���!');
  	document.all('QuafNo').value = '';
  	return false;
  }
   }
  return false;
}
function changeIDNo()
{
   if (getWherePart('IDNo')==''|| getWherePart('IdType')=='')
     {
      document.all('IDNo').value = '';
    return false;
     }
    var strSQL = "";
   //tongmeng 2005-08-18 modify
   //add branchtype='1'
   //tongmeng 2005-11-04 modify
   //�޸Ķ����֤��У�鷽ʽ
   //������Ա�����֤�϶��Ǵ��ڵ�.����Ҫ��У��.��ְ�Ĳ���������Ա.
   if (document.all('initAgentState').value != '02')
    {
   /*strSQL = "select * from LAAgent where 1=1 and (branchtype='1' or (branchtype<>'1'  and agentstate<'03')) "
   + getWherePart('IDNo')+getWherePart('IdType');*/
   strSQL = wrapSql(tResourceName,"querysqldes18",[document.all('IDNo').value,document.all('IdType').value]);
   var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
   if (strQueryResult)
   { 
   	alert('�����֤���Ѵ���!');
   	document.all('IDNo').value = '';
   	return false;
   }
   if(document.all('IdType').value=='0')
   { 
   var IDNo=trim(document.all('IDNo').value);
   if(IDNo.length==15 )
   {
   var IDNo=trim(document.all('IDNo').value);
   /*var tsql1="select agentcode from laagent where 1=1 and (branchtype='1' or (branchtype<>'1'  and agentstate<'03'))"
   +" and idno like substr('"+IDNo+"',0,6)||'%%'||substr('"+IDNo+"',7,9)||'%%' ";*/
   var tsql1 = wrapSql(tResourceName,"querysqldes19",[IDNo,IDNo]);
   var strQueryResult=easyQueryVer3(tsql1, 1, 1, 1);
   if (strQueryResult)
   {
   	alert('�Ѵ���18λ�����֤��');
  
   	document.all('IDNo').value = '';
   
   	return false;
   }  
    }    
    if(IDNo.length==18)
   {
      /*var tsql2="select agentcode from laagent where 1=1 and (branchtype='1' or (branchtype<>'1'  and agentstate<'03'))"
      +" and  idno like substr('"+IDNo+"',0,6) ||'%%'||substr('"+IDNo+"',9,9)||'%%' ";*/
      var tsql2 = wrapSql(tResourceName,"querysqldes20",[IDNo,IDNo]);
      var strQueryResult=easyQueryVer3(tsql2, 1, 1, 1);
      if (strQueryResult)
   {
   	alert('�Ѵ���15λ�����֤�ţ�');
   	document.all('IDNo').value = '';
   return false;
   } 
    }
    }
   }
  //add by renhailong 2008-07-22
if(document.all('initOperate').value == 'INSERT'){
   if(document.all('Name').value==null || document.all('Name').value=='')
   {alert('������¼������');
   document.all('IDNo').value = '';
   return false;}
   if(document.all('IdType').value==null || document.all('IdType').value=='')
   {alert('������¼��֤������');
   document.all('IDNo').value = '';
   return false;
   }
   strSQL = "";
   /*strSQL="select a.assumoney from laassumoney  a where 1=1 and branchtype='1' and ConfMakeDate is not null and AssuCheckState='0'"
    +" and not exists (select 1 from lajagetassumoney where a.SerialNo=SerialNo) "
    + getWherePart('agentname','Name')+getWherePart('IdType')+getWherePart('IDNo');*/
   strSQL = wrapSql(tResourceName,"querysqldes21",[document.all('Name').value,document.all('IdType').value,document.all('IDNo').value]);
    var strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('�ô�����û�н�Ѻ��');
   	document.all('AssuMoney').value = '';
   	document.all('IDNo').value = '';
    return false;
   }  
  var arr = decodeEasyQueryResult(strQueryResult);
 document.all('AssuMoney').value = trim(arr[0][0]);
   
   }
   return true; 
}
//У�����ɴ�����
function checkRearAgent()
{
   var strSQL = "",str = "";
   var strQueryResult = null;
   // tongmeng 2005-08-18 modify
   //add branchtype='1'
   //strSQL = "select AgentCode from LAAgent where (AgentState < '03' or AgentState is not null) and branchtype='1'"
   strSQL = wrapSql(tResourceName,"querysqldes22",["1"]);
   //���ɴ�����
   if (trim(document.all('RearAgent').value)=='')
     return true;
   if (trim(document.all('RearAgent').value)==trim(document.all('AgentCode').value)) 
   {
      alert('��ԭ�����˱�����ͬ!');
      document.all('RearAgent').value = '';
      return false;
   } 
   str = getWherePart('AgentCode','RearAgent');
            
   //alert(strSQL+str);
   strQueryResult = easyQueryVer3(strSQL+str, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('�����ڸ����ɴ����ˣ�');
   	document.all('RearAgent').value = '';
   	return false;
   }  
   //����������
   if (trim(document.all('RearDepartAgent').value)=='')
     return true;
   if(trim(document.all('RearDepartAgent').value)==trim(document.all('AgentCode').value))
   {
     alert('��ԭ�����˱�����ͬ!');
     document.all('RearDepartAgent').value = '';
     return false;
   }
   str = getWherePart('AgentCode','RearDepartAgent');
            
   //alert(strSQL+str);
   strQueryResult = easyQueryVer3(strSQL+str, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('�����ڸ����������ˣ�');
   	document.all('RearDepartAgent').value = '';
   	return false;
   } 
   //���ɶ�����������
   if (trim(document.all('RearSuperintAgent').value)=='')
     return true;
     
   if (trim(document.all('RearSuperintAgent').value)==trim(document.all('AgentCode').value))
   {
     alert('��ԭ�����˱�����ͬ!');
     document.all('RearSuperintAgent').value = '';
     return false;
   }  
   str = getWherePart('AgentCode','RearSuperintAgent');
            
   //alert(strSQL+str);
   strQueryResult = easyQueryVer3(strSQL+str, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('�����ڸ����ɶ��������ˣ�');
   	document.all('RearSuperintAgent').value = '';
   	return false;
   }
   //�������򶽵���������
   if (trim(document.all('RearAreaSuperintAgent').value)=='')
     return true;
   if (trim(document.all('RearAreaSuperintAgent').value)==trim(document.all('AgentCode').value))
   {
     alert('��ԭ�����˱�����ͬ!'); 
     document.all('RearAreaSuperintAgent').value = '';
     return false;
   } 
   str = getWherePart('AgentCode','RearAreaSuperintAgent');
            
   //alert(strSQL+str);
   strQueryResult = easyQueryVer3(strSQL+str, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('�����ڸ��������򶽵������ˣ�');
   	document.all('RearAreaSuperintAgent').value = '';
   	return false;
   }
   return true;
}

function agentConfirm()
{

   if (getWherePart('AgentCode')=='')
   {
     return false;
   }     
   // ��дSQL���
   var strSQL = "";
   //tongmeng 2005-11-04 modify
   //ֻ�и��յ��˲�����������Ա����
   /*strSQL = "select a.* from LAAgent a where 1=1 and a.branchtype='1' "
          //+ "and a.AgentGroup = b.AgentGroup "
	  + getWherePart('a.AgentCode','AgentCode');*/
   strSQL = wrapSql(tResourceName,"querysqldes23",[document.all('AgentCode').value]);
//   alert(strSQL);   
   var strQueryResult = easyQueryVer3(strSQL,1,1,1);
   if(!strQueryResult)
   {
     alert('�����ڸô����ˣ�');
     document.all('AgentCode').value = '';
     return false;	
   }
   var arr = decodeEasyQueryResult(strQueryResult);
   //tongmeng 2005-11-07 add
   //��ֹ��������Աʱ���ô�������һ����ְ������Ա�Ĺ���
   var tIdno = arr[0][21];
   /*strQ = "select * from laagent where 1=1 and (branchtype='4' or branchtype='5') "
        + " and idno='"+tIdno+"' and agentstate<='02'" ;*/
   strQ = wrapSql(tResourceName,"querysqldes24",[tIdno]);
    //   prompt('1',strQ);
   var tQueryResult = easyQueryVer3(strQ,1,1,1);
   if(tQueryResult)
   {
     alert('����һ����ְ������Ա��ô��������֤����ͬ��');
     document.all('AgentCode').value = '';
     return false;	
   }
   var AgentState = arr[0][61];
   if (AgentState==null||AgentState=='01'||AgentState=='02')
   {
   	alert('�ô�����δ��ְ��������������Ա��');
   	document.all('AgentCode').value = '';
   	return false;
   }
   if (trim(AgentState) == '04')
   {
   	alert('�ô���������ְ���Σ������ٴ���Ա��');
   	document.all('AgentCode').value = '';
   	return false;
   }	 
  // alert(AgentState);
   if (trim(AgentState)=='03')
   {
   	//tongmeng 2006-02-28 modify
   	//�������Ա������,�,����Ļ�,������˾���������
   	
   	var tOperator = document.all('hideOperator').value;
   	var	currentdate=document.all('hideCurrentDate').value;
 
   	//var tTempAgentCode = document.all('AgentCode').value;
//   	if(tOperator!='000055'&&tOperator!='000029'&&tOperator!='000136')
//   	{ if(tTempAgentCode.substr(0,4)!='8621') {
//   		if (!compare(arr[0][51]))
//   		{
//   	  		alert("�ô���������ְ�����ſ�������˾!");
//   	  		document.all('AgentCode').value='';
//   	  		return false;
//   		}
//   	 }
//    }
//    else
//    {
//comment by jiaqiangli 2007-04-26 ��ΰ����ȡ������,�,�����8611��Ȩ
      //add by jiaqiangli 2007-06-28 8621�ϱ���Ӫ�������� �ָ�6���µ�У��
      //add by jiaqiangli 2008-03-26 ��������Ȩ��
    	
      
    	if(tOperator != '000146' && (tOperator!='210027'||currentdate>='2009-01-01'))
    	{
    		if (!compare(arr[0][51]))
   			{
   	  			alert("�ô���������ְ�����ſ�������˾!");
   	  			document.all('AgentCode').value='';
   	  			return false;
   			}
    	}
    //}	
   }
   
   //add by tongmeng 2005-08-10
   //�޸����ӶԿ���������Ա��˾������У��
    if (trim(AgentState)=='05')
    {
//    	var tSQL="select departtimes from ladimission where agentcode='"+document.all('AgentCode').value+"' ";
    	var tSQL = wrapSql(tResourceName,"querysqldes25",[document.all('AgentCode').value]);
    	var strQueryResult1 = easyQueryVer3(tSQL,1,1,1);
    	if(!strQueryResult1)
   		{
     		alert('��ѯ�ô�������ְ��Ϣ����!')
     		document.all('AgentCode').value = '';
     		return false;	
   		}
    	var arr1 = decodeEasyQueryResult(strQueryResult1);
    	if(arr1[0][0]=='2')
    	{
    		alert("�ô���������ְ���Σ������ٴ���Ա!");
    		document.all('AgentCode').value='';
    		return false;
    	}
    		//tongmeng 2006-02-28 modify
   			//�������Ա������,�,����Ļ�,������˾���������
   	
   	    var tOperator = document.all('hideOperator').value;
   	    var	currentdate=document.all('hideCurrentDate').value;
   	    //alert(tOperator);
   	    //var tTempAgentCode = document.all('AgentCode').value;
//    	if(tOperator!='000055'&&tOperator!='000029'&&tOperator!='000136')
//   	    {  if(tTempAgentCode.substr(0,4)!='8621') {
//    		if (!compare(arr[0][51]))
//   			{
//   	  			alert("�ô���������ְ�����ſ�������˾!");
//   	  			document.all('AgentCode').value='';
//   	  			return false;
//   			}
//   		 }
//   		}
//   		else
//    	{
//comment by jiaqiangli 2007-04-26 ��ΰ����ȡ������,�,�����8611��Ȩ
        //add by jiaqiangli 2007-06-28 8621�ϱ���Ӫ�������� �ָ�6���µ�У��
        //add by jiaqiangli 2008-03-26 ��������Ȩ��
    	if(tOperator != '000146' && (tOperator!='210027'||currentdate>='2009-01-01'))
    		{
    			if (!compare(arr[0][51]))
   				{
   	  				alert("�ô���������ְ�����ſ�������˾!");
   	  				document.all('AgentCode').value='';
   	  				return false;
   				}
    		}
    	//}	
    }
    
   
   if (!afterQuery(arr))
     
     return false;
      return true;
     
}

//������Ա����������ְ�����
function compare(DepartDate)
{
	var d = new Date();
	var month,year,day;
	day = d.getDate();
	month = d.getMonth() + 1 - 6;
	
//	alert("month"+month);
	if (month>0)
	{
		year=d.getYear();
	}
       else	
	if (month == 0)
	{
		//tongmeng 2006-06-12 modify
		//���monthΪ0�Ļ�,Ӧ�ð�month��Ϊ12
	   month=12;
	   year=d.getYear()-1;
	}else
	if (month < 0)
	{
	   month=12+month;
	   year=d.getYear()-1;
	}	
	
	if (month.toString().length == 1)
	  month='0'+month;
	if (day.toString().length == 1)
	  day='0'+day;
	var dd = year+'-'+month+'-'+day;
//	alert("year"+year);
//	alert(month);
//	alert("day"+day);
//	alert(dd+'   '+DepartDate);
	if (trim(dd)<trim(DepartDate))
	  return false;
  	return true;
}
function saveForm()
{
	mOperate = "INSERT||MAIN";
	submitForm();
}
//١��2005-03-15����
//�ж����ӵ��������Ƿ��������(�Ƿ���ô����˵�ְ�����,�Ƿ���ô�������ͬһ�����������)
function DecideRearAgent(t)
{
	var tsql="";
	//tsql="select agentcode from latree where 1=1 and agentgrade like 'A%%' ";
	var sqlpara1='';
	var sqlpara2='';
	var sqlpara3='';
	var sqlpara4='';
	//��������
	if(t=='1')
	//tsql=tsql+" and agentcode='"+document.all('RearAgent').value+"' and agentgrade>'A03'";
	sqlpara1 = document.all('RearAgent').value;
	//��������
	else if(t=='2')
   //tsql=tsql+" and agentcode='"+document.all('RearDepartAgent').value+"' and agentgrade>'A05'";
	sqlpara2 = document.all('RearDepartAgent').value;
    //��������
	else if(t=='3')
    //tsql=tsql+" and agentcode='"+document.all('RearSuperintAgent').value+"' and agentgrade>'A07'";
	sqlpara3 = document.all('RearSuperintAgent').value;
    //�ܼ������� 
	else if(t=='4')
    //tsql=tsql+" and agentcode='"+document.all('RearAreaSuperintAgent').value+"' and agentgrade>'A08'";
	sqlpara4 = document.all('RearAreaSuperintAgent').value;
    
    //tsql=tsql+"and state<>'3'"+getWherePart("managecom","ManageCom","like");
    tsql = wrapSql(tResourceName,"querysqldes26",[sqlpara1,sqlpara2,sqlpara3,sqlpara4,document.all('ManageCom').value]);
    var tsqlResult = easyQueryVer3(tsql,1,1,1);
    if(!tsqlResult){ 
    	return false; 
    }    
    return true;    	    	    	  	
}

function checkage()
{
  var managecom = document.all('hideManageCom').value;
  var idno=document.all('IDNo').value;
  
  var dirthdate=document.all('Birthday').value;
  var currentdate=getCurrentDate();
  //var strSQL="select months_between('"+currentdate+"','"+dirthdate+"') from dual";
  var strSQL = wrapSql(tResourceName,"querysqldes27",[currentdate,dirthdate]);
  var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
  var arr = decodeEasyQueryResult(strQueryResult);
  var months=arr[0][0];
  if(managecom!='86'&&(months>=612))
  {
      //var sql="select * from LAAgentAuthorize where idno='"+idno+"' and managecom like '"+managecom+"%%' and flag='1'";
      var sql = wrapSql(tResourceName,"querysqldes28",[idno,managecom]);
      strQueryResult = easyQueryVer3(sql, 1, 1, 1);
      if (strQueryResult)
      {
   	return true;
      } 	
  	
    alert("û��Ȩ�޶���ʮ�������ϵ���Ա��Ա!");
    return false;
  }
   return true;		
}
/////////////////////////////
//tongmeng 2006-04-26 add
//����ְ��ȥ��ѯ��Ӧ����ְ��
function queryOldAgentGrade()
{
  var sAgentGrade = document.all('AgentGrade1').value;
  /*var sSQL = "select gradecode from laagentgrade where gradecode like 'A%%' and branchtype='1' "
             + "  and gradeid =(select max(gradeid) from laagentgrade where gradecode1='"+sAgentGrade+"' ) ";*/
  var sSQL = wrapSql(tResourceName,"querysqldes29",["'"+sAgentGrade+"'"]);
  var strQueryResult = easyQueryVer3(sSQL,1,1,1);
  if(!strQueryResult)
  {
  	document.all('AgentGrade').value ='';
  	//by jiaqiangli 2006-10-08 add
  	alert('Mϵ��ְ��ת��ΪAϵ��ְ������!');
  	document.all('AgentGrade1').value ='';
  	document.all('BranchCode').value ='';
  	return false;
  }
  else
  {
  	var arr = decodeEasyQueryResult(strQueryResult);
  	var sResult=arr[0][0];
  //	if(sResult == 'A05')
  //	   	sResult = 'A04';
  //	if(sResult == 'A07')
  //	   	sResult = 'A06';
    document.all('AgentGrade').value = sResult; 
    return true;
  }
}
//////////////////////////
//tongmeng 2006-04-26 add
//���������Ա���������ܼ���������Ա,����ж༶������
//,���Ҷ༶�����˲���ͬ,���߲�ͬΪ��,������ʾ
//by jiaqiangli modified 2006-08-01 ����������
function checkAllRearAgent()
{
	var sAgentGrade = document.all('AgentGrade').value;
	var sRear1 = "";
	var sRear2 = "";
	var sRear3 = "";
	var sRear4 = "";
	//var stempFlag = "";
	//stempFlag: 00 :����������Ϊ��
	//           01 :���������˲�ͬ
	//           02 :©¼
	sRear1 = trim(document.all('RearAgent').value);
	sRear2 = trim(document.all('RearDepartAgent').value);
	sRear3 = trim(document.all('RearSuperintAgent').value);
	sRear4 = trim(document.all('RearAreaSuperintAgent').value);
	//������������
	if(sAgentGrade>='A04' && sAgentGrade<='A05')
	{
		if(sRear1 == null||sRear1 == '')
		{
			if(!confirm("��������Ϊ�գ��Ƿ񱣴�?"))
			{
				return false;
			}
		    else
		    	return true;
		}
	}
    else if(sAgentGrade>='A06' && sAgentGrade<='A07')
    {
    	if((sRear1 == null||sRear1 == '')&&(sRear2 == null||sRear2 == ''))
		{
			if(!confirm("���������˾�Ϊ�գ��Ƿ񱣴�?"))
			{
				return false;
			}
		    else
		    	return true;
		}
	    //����������һ��Ϊ��	
	    else 
	    {
	    	if(sRear1 == '' && sRear2 != '')
	    	{
	    		alert("��������©¼,����������");
	    		return false;
	    	}
	        else if(sRear1 != '' && sRear2 == '')
	        {
	        	if(!confirm("��������Ϊ�գ��Ƿ񱣴�?"))
			{
				return false;
			}
		        else
		    	        return true;
	        }
	        //���߶���Ϊ��
	        else if(sRear1 != sRear2)
	        {
	            if(!confirm("���������˲�ͬ,�Ƿ񱣴�?"))
	            	return false;
	            else 
	            	return true;
	        }
	    }
    } 
    else if(sAgentGrade=='A08')
    {   //����yyy(y��ʾΪ��)(n��ʾ��Ϊ��)
    	if((sRear1 == null||sRear1 == '')&&(sRear2 == null||sRear2 == '')&&(sRear3 == null||sRear3 == ''))
		{
			if(!confirm("���������˾�Ϊ�գ��Ƿ񱣴�?"))
			{
				return false;
			}
		    else
		    	return true;
		}
	    //����������һ��Ϊ��
	    else 
	    {   //����yyn,ynn,nyn,yny
	    	if(((sRear1 == '' || sRear2 == '')&&sRear3 != '') || (sRear1 == '' && sRear2 != ''&&sRear3 == ''))
	    	{
	    		alert("���������©¼,����������");
	    		return false;
	    	}
	    	//����nyy,nny
	        else if((sRear1 != '' && sRear2 == ''&&sRear3 == '') ||((sRear1 != '' && sRear2 != ''&&sRear3 == '')))
	        {
	        	if(!confirm("������©¼,ֻ���������˻�ֻ���鲿������,�Ƿ񱣴�?"))
	            	     return false;
	                else 
	            	     return true;
	        }
	    	//����nnn
	        else if((sRear1 != sRear2)||(sRear1 != sRear3))
	        {
	            if(!confirm("���������˲�ͬ,�Ƿ񱣴�?"))
	            	return false;
	            else 
	            	return true;
	        }
	    }
    } 
    else if(sAgentGrade=='A09')
    {   //����yyyy(y��ʾΪ��)(n��ʾ��Ϊ��)
    	if((sRear1 == null||sRear1 == '')&&(sRear2 == null||sRear2 == '')&&(sRear3 == null||sRear3 == '')&&(sRear4 == null||sRear4 == ''))
		{
			if(!confirm("���������˾�Ϊ�գ��Ƿ񱣴�?"))
			{
				return false;
			}
		    else
		    	return true;
		}
	    else 
	    {   //��һ������������7���,ǰ����������һΪ��
	    	//�ڶ�������������4���
	    	if(((sRear1 == '' || sRear2 == ''||sRear3 == '')&&sRear4 != '') ||
	    	   (sRear1 == '' && sRear2 != ''&&sRear3 == ''&&sRear4 == '') ||
	    	   (sRear1 == '' && sRear2 != ''&&sRear3 != ''&&sRear4 == '') ||
	    	   (sRear1 != '' && sRear2 == ''&&sRear3 != ''&&sRear4 == '') ||
	    	   (sRear1 == '' && sRear2 == ''&&sRear3 != ''&&sRear4 == '')
	    	   )
	    	{
	    		alert("��򲿻���������©¼,����������");
	    		return false;
	    	}
	    	//������3���
	        else if ((sRear1 != '' && sRear2 == ''&&sRear3 == ''&&sRear4 == '') ||
	                 (sRear1 != '' && sRear2 != ''&&sRear3 == ''&&sRear4 == '') ||
	                 (sRear1 != '' && sRear2 != ''&&sRear3 != ''&&sRear4 == '')
	                  )
	        {
	        	if(!confirm("������©¼,ֻ������鲿���鲿��������,�Ƿ񱣴�?"))
	            	     return false;
	                else 
	            	     return true;
	        }
	    	//����nnnn
	        else if((sRear1 != sRear2)||(sRear1 != sRear3)||(sRear1 != sRear4))
	        {
	            if(!confirm("���������˲�ͬ,�Ƿ񱣴�?"))
	            	return false;
	            else 
	            	return true;
	        }
	    }
    }
    return true; 
}
//tongmeng 2006-05-16 add
//��ʾ¼��Ĵ���������
function getInputAgentName(sType)
{
   var stempAgentGrade = "";
   stempAgentGrade = document.all('AgentGrade1').value;
   if(stempAgentGrade == null||stempAgentGrade == '')
   {	
   		alert("�������������ְ��");
   		switch(eval(sType))
   		{
   			case 1:
   				document.all('IntroAgency').value='';
   				break;
   			case 2:
   				document.all('RearAgent').value='';
   				break;
   	    	        case 3:
   				document.all('RearDepartAgent').value='';
   				break;
   			case 4:
   				document.all('RearSuperintAgent').value='';
   				break;
   			case 5:
   				document.all('RearAreaSuperintAgent').value='';
   				break;
   		}
   		return false;
   }
   //by jiaqiangli ��������������������ʾ
//   if(CheckRearInput(sType) == false)
//      return false;
   var sAgentCode = "";
   var sAlertName = "";
   switch(eval(sType))
   {
   		case 1:
   			sAlertName = '�Ƽ�������:';
   			sAgentCode = document.all('IntroAgency').value;
   			break;
   		case 2:
   			sAlertName = '������������:';
   			sAgentCode = document.all('RearAgent').value;
   			break;
   	        case 3:
   			sAlertName = '������������:';
   			sAgentCode = document.all('RearDepartAgent').value;
   			break;
   		case 4:
   			sAlertName = '������������:';
   			sAgentCode = document.all('RearSuperintAgent').value;
   			break;
   		case 5:
   			sAlertName = '�ܼ�����������:';
   			sAgentCode = document.all('RearAreaSuperintAgent').value;
   			break;
   }
   var sResultName = "";
   if(sAgentCode!='')
   {
   		//var tempSql = "select name from laagent where agentcode='"+sAgentCode+"' ";
   		var tempSql = wrapSql(tResourceName,"querysqldes30",[sAgentCode]);
   
   		var tempQueryResult = easyQueryVer3(tempSql, 1, 1, 1);
   	if(!tempQueryResult)
   	{
   		return false;
   	}
   	else
   	{
   	   	var arr = decodeEasyQueryResult(tempQueryResult);
  	   	sResultName=arr[0][0];
   	}
   	alert(sAlertName+sResultName);
   }
   return true;
}
function checkEdition()
{
	//ldcode  ��������ֹ�˾���չ�ϵ
	//relatype='agentedition'
	//code1 :���û������Ļ��� 
	//code2 :�������汾   01:�ϰ汾  02:�°汾
	//code3 :��������
	//othersign :����Ǩ��״̬  1:����Ǩ��ǰ  2:����Ǩ�ƺ�
	var dEdition = "";
	var dState = "";
	var dCom = trim(document.all('ManageCom').value);
	dCom = dCom.substr(0,4);
	/*var dSQL = "select code2,othersign from ldcoderela where relatype='agentedition' "
	         + " and code3 = '1' and code1='"+dCom+"' ";*/
	var dSQL = wrapSql(tResourceName,"querysqldes31",[dCom]);
	var dQueryResult = easyQueryVer3(dSQL, 1, 1, 1);
	if(!dQueryResult)
   	{
   		alert("û��ָ��"+dCom+"�Ļ������汾!");
   		return false;
   	}
   	else
   	{
   	   	var arr = decodeEasyQueryResult(dQueryResult);
  	   	dEdition = arr[0][0];
  	   	dState = arr[0][1];
  	   	if(dEdition=='01')
  	   	{
  	   		alert(dCom+"ʹ���ϰ������,�����ڴ˽��в���");
  	   		return false;
  	   	}
  	    else  if(dEdition=='02')
  	    {
  	    	if(dState=='1')
  	    	{
  	    		alert(dCom+"��û��������Ǩ��,��������Ǩ����ɺ��ٲ���!");
  	    		return false;
  	    	}
  	        else if(dState=='2')
  	        {
  	    		return true;
  	    	}
  	    }
   	}
   	return true;
}
//by jiaqiangli 2006-08-04
//��������ְ��ȷ����������������ҵ�������
//������������ڱ���֮ǰ������գ���ֹ�û�������ְ��֮��仯��ְ��
//�����ؼ��Ĳ�֧�֣�����һ�ַ�����ְ��ѡ��򻻳������
//���������˴���ʱ�����ж�
//���е���Щֻ�Ƿ����û�ϰ�ߣ����Ǽ�ʹ��¼���˲�Ӧ������ĺ�̨Ҳ������
function ControlRearInput()
{      
	var agentgrade=trim(document.all('AgentGrade').value);
	//ҵ��Աû��������
	if(agentgrade<'A04')
	{
		document.all('RearAgent').value = '';
                document.all('RearDepartAgent').value = '';
                document.all('RearSuperintAgent').value = '';
                document.all('RearAreaSuperintAgent').value = '';
	}
	//�龭�����ֻ����������
        else if(agentgrade<='A05')
        {
                document.all('RearDepartAgent').value = '';
                document.all('RearSuperintAgent').value = '';
                document.all('RearAreaSuperintAgent').value = '';
        }
        //���������ֻ���鲿������
        else if(agentgrade<='A07')
        {
                document.all('RearSuperintAgent').value = '';
                document.all('RearAreaSuperintAgent').value = '';
        }
        //���������ֻ���鲿��������
        else if(agentgrade=='A08')
        {
                document.all('RearAreaSuperintAgent').value = '';
        }
}
//by jiaqiangli 2006-08-04 ��������ʱ������Ҫ�ľ���
//sType ��ʾ¼���������
//2 ������������   3 ���벿������
//4 ������������   5 �����ܼ�������
function CheckRearInput(sType)
{
	//�Ƚ���ְ�����㵽��ְ��
	if (queryOldAgentGrade()== false ) return false; 
	var AAgentGrade=document.all('AgentGrade').value;
	if(sType!=1) {
	//ҵ��Աû��������
	if(AAgentGrade<'A04')
	{
		alert('ҵ��Աû��������');
		document.all('RearAgent').value = '';
                document.all('RearDepartAgent').value = '';
                document.all('RearSuperintAgent').value = '';
                document.all('RearAreaSuperintAgent').value = '';
                return false;
	}
	//�龭�����ֻ����������
        else if(AAgentGrade<='A05')
        {       
        	if(sType==3)
        	{
        		alert('�龭�����ֻ����������');
        		document.all('RearDepartAgent').value = '';
                        return false;
                }
                else if(sType==4)
                {
                	alert('�龭�����ֻ����������');
                        document.all('RearSuperintAgent').value = '';
                        return false;
                }
                else if(sType==5) 
                {
                	alert('�龭�����ֻ����������');
                        document.all('RearAreaSuperintAgent').value = '';
                        return false;
                }
        }
        //���������ֻ���鲿������
        else if(AAgentGrade<='A07')
        {
        	if(sType==4)
        	{
        		alert('���������ֻ���鲿������');
                        document.all('RearSuperintAgent').value = '';
                        return false;
                }
                else if(sType==5)
                {
                	alert('���������ֻ���鲿������');
                        document.all('RearAreaSuperintAgent').value = '';
                        return false;
                }
        }
        //���������ֻ���鲿��������
        else if(AAgentGrade=='A08')
        {
        	if(sType==5)
        	{
        		alert('���������ֻ���鲿��������');
                        document.all('RearAreaSuperintAgent').value = '';
                        return false;
                }
        }
        return true;
        }
}
function afterCodeSelect( cCodeName, Field ) {
	try	{
	   
	   
	   //�൱���������onchange�¼��Ľӿ�;
	    if(cCodeName =='IdType'){
	    
	     if (document.all('initAgentState').value != '02')
      { 
       if( document.all('hideManageCom').value.length > 2)
         { 
       
         if(document.all('IdType').value!='0')  
           {
           
           alert("¼������֤���������֤�����ͽ������ܹ�˾");
               document.all('IdType').value="";
                return false;}
            }
     }
	
	}
		if(cCodeName =='AgentGrade1')
		{
			var agentgrade = Field.value;
			if(agentgrade<'M03')
			{
		        document.all('RearAgent').disabled = true;
                document.all('RearDepartAgent').disabled = true;
                document.all('RearSuperintAgent').disabled = true;
                document.all('RearAreaSuperintAgent').disabled = true;
			}
			//�龭�����ֻ����������
       	 	else if(agentgrade=='M03')
        	{	
        	    document.all('RearAgent').disabled = false;
                document.all('RearDepartAgent').disabled = true;
                document.all('RearSuperintAgent').disabled = true;
                document.all('RearAreaSuperintAgent').disabled = true;
        	}
        	//���������ֻ���鲿������
        	else if(agentgrade=='M04')
        	{
        	    document.all('RearAgent').disabled = false;
        	    document.all('RearDepartAgent').disabled = false;
                document.all('RearSuperintAgent').disabled = true;
                document.all('RearAreaSuperintAgent').disabled = true;
       	 	}
        	//���������ֻ���鲿��������
        	else if(agentgrade=='M05')
        	{
        	    document.all('RearAgent').disabled = false;
        	    document.all('RearDepartAgent').disabled = false;
                document.all('RearSuperintAgent').disabled = false;
                document.all('RearAreaSuperintAgent').disabled = true;
        	}
        	else if(agentgrade=='M06')
        	{
        	    document.all('RearAgent').disabled = false;
        	    document.all('RearDepartAgent').disabled = false;
                document.all('RearSuperintAgent').disabled = false;
                document.all('RearAreaSuperintAgent').disabled = false;
        	}
    	}
    	
    	
	}
	catch( ex ) {
	}
}
function ControlA09()
{
	if(document.all('AgentGrade1').value=='M06'||document.all('AgentGrade1').value=='M05') {
		    //by jiaqiangli 2006-09-05
        	//�ܼ��¼�룬Ҫ��ֻ�ж������������������˹���Ȩ�޽��в���
        	//��ʼ��Ϣ�޸����ܹ�˾��������Ϣ�޸��ڷֹ�˾
        	//by zhujch 2008-06-18
        	//��ӶԶ����������������  Ҫ��ֻ�ж������������������˹���Ȩ�޽��в���
        	if(document.all('hideManageCom').value.length > 4) {
        		alert('�ܼ�򶽵���¼�룬Ҫ��ֻ�ж������������������˹���Ȩ�޽��в���');
        		document.all('AgentGrade1').value='';
        		document.all('BranchCode').value='';
        		return false;
        	}
	}
	return true;
}
