<%
//�������ƣ�LLClaimReciInfoInit.jsp
//������: �ռ�����Ϣ¼��
//������:niuzj
//��������:2005-10-25
%>
<!--�û�У����-->
<%
//���ҳ��ؼ��ĳ�ʼ����
GlobalInput globalInput = (GlobalInput)session.getValue("GI");

if(globalInput == null) {
	out.println("session has expired");
	return;
}

String strOperator = globalInput.Operator;   //����Ա
String strManageCom = globalInput.ComCode;   //�������
%>
<script language="JavaScript">
var tResourceName="claim.LLClaimReciInfoInputSql";
//�����ϸ�ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");  //�ⰸ��
    document.all('CustomerNo').value = nullToEmpty("<%= tcustomerNo %>");  //�����˴���
    document.all('IsShow').value = nullToEmpty("<%= tIsShow %>"); //[����]��ť�ܷ���ʾ,0-����ʾ,1-��ʾ
    document.all('RgtObj').value = nullToEmpty("<%= tRgtObj %>"); //�������ձ�־,1-����,2-����
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}


function initInpBox()
{
	try
	{
		document.all('ReciCode').value = '';      //�ռ��˴���
		document.all('ReciName').value = '';      //�ռ�������
		
		document.all('Relation').value='';        //�ռ���������˹�ϵ
		document.all('ReciAddress').value='';     //�ռ��˵�ַ
		
		document.all('ReciPhone').value='';        //�ռ��˵绰
		document.all('ReciMobile').value='';       //�ռ����ֻ�
		
		document.all('ReciZip').value='';        //�ռ����ʱ�
		
		document.all('ReciSex').value='';        //�ռ����Ա�
		document.all('ReciSexName').value='';        
		
		document.all('ReciEmail').value='';     //�ռ���Email
		
		document.all('Remark').value = '';       //�ռ���ϸ��
	//	document.all('RemarkDisabled').value = '';
	
	  

	}
	catch(ex)
	{
		alert("��LLClaimReciInfoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initIsShow()
{
	//�ж�[����]��ť�ܷ���ʾ,0-����ʾ,1-��ʾ
		if(fm.IsShow.value == '0')
		{
			fm.save.disabled=true;
			fm.ReciCode.disabled=true;
			fm.ReciName.disabled=true;
			fm.Relation.disabled=true;
			fm.ReciAddress.disabled=true;
			fm.ReciPhone.disabled=true;
			fm.ReciMobile.disabled=true;
			fm.ReciZip.disabled=true;
			fm.ReciSex.disabled=true;
			fm.ReciEmail.disabled=true;
			fm.Remark.disabled=true;
		}
		if(fm.IsShow.value == '1')
		{
			fm.save.disabled=false;
		}
}

//��ʼ����ѯ,��ѯ���ⰸ�µ��ռ�����Ϣ
function initQuery()
{
	 /*var strSQL=" select a.recipients,a.reciname,a.reciaddress,a.recidetails,a.recirela, "
             +" a.reciphone,a.recimobile,a.recizip,a.recisex,a.reciemail "
             //+" (select codename from ldcode where codetype='sex' and code='"+a.recisex+"') as sexname "
             +" from llregister a where a.rgtno='"+document.all('ClmNo').value+"' ";   */ 
	var strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('ClmNo').value]);        
   var arr = easyExecSql( strSQL );
//   alert(arr);
//   alert(arr[0][0]);
   if(arr[0][0]=="" && arr[0][1]=="" && arr[0][2]=="" && arr[0][3]=="" && arr[0][4]=="" && arr[0][5]=="" && arr[0][6]=="" && arr[0][7]=="" && arr[0][8]=="" && arr[0][9]=="")   //û���κ��ռ�����Ϣ
   {
  	  //-------------------------------------------------------------
  	  //�ռ���У�飺������ⰸĬ�ϱ�����(������)������ⰸĬ��������
  	  //-------------------------------------------------------------
      //�����ж��ǲ�����������,0-�����,1-���
      var tDeadFlag = 0;
      /*var tSql = " select substr(d.reasoncode,2,2) from llappclaimreason d where "
               + " d.caseno = '" +document.all('ClmNo').value+ "'";*/
      var tSql = wrapSql(tResourceName,"querysqldes2",[document.all('ClmNo').value]);
      var tRcode = easyExecSql( tSql );
      if (tRcode)
      {
          for (var j = 0; j < tRcode.length; j++)
          {
              if (tRcode[j] == '02')
              {
                  tDeadFlag = 1;
                  break;
              }
          }
      }
      
      if(tDeadFlag == 0) //����ʰ���,��ѯ��������Ϣ
      {
      	  alert("�ڸ��ⰸ��δ�ҵ��κ��ռ�����Ϣ,��Ĭ��Ϊ��������Ϣ��");  
      	  //var strSQL1=" select b.customerno,b.name,b.sex,b.rgtaddress from ldperson b "
          //           +" where b.customerno ='" +document.all('CustomerNo').value+ "' ";
         /* var strSQL1=" select t.customerno,b.name, "
                     +" b.sex,(case b.sex when '0' then '��' when '1' then 'Ů' else '����' end) as sexname, "
                     +" t.postaladdress,t.zipcode,t.phone,t.mobile,t.email "
                     +" from lcaddress t,ldperson b "
                     +" where t.customerno=b.customerno "
                     +" and t.customerno='" +document.all('CustomerNo').value+ "' ";*/
          var strSQL1 = wrapSql(tResourceName,"querysqldes3",[document.all('CustomerNo').value]);
          var arr1=easyExecSql( strSQL1 );
          if(arr1)
          {
          	fm.ReciCode.value = arr1[0][0];      //�ռ��˴���
            fm.ReciName.value = arr1[0][1];      //�ռ�������
            fm.ReciSex.value = arr1[0][2];       //�ռ����Ա����
            fm.ReciSexName.value = arr1[0][3];   //�ռ����Ա�
            fm.ReciAddress.value = arr1[0][4];   //�ռ��˵�ַ 
            fm.ReciZip.value = arr1[0][5];       //�ռ����ʱ�
            fm.ReciPhone.value = arr1[0][6];     //�ռ��˵绰
            fm.ReciMobile.value = arr1[0][7];    //�ռ����ֻ�
            fm.ReciEmail.value = arr1[0][8];     //�ռ���Email                               
          }
      }
      else  //��ʰ���,��ѯ��������Ϣ
    	{   
    		  alert("�ڸ��ⰸ��δ�ҵ��κ��ռ�����Ϣ,��Ĭ��Ϊ��������Ϣ��");  
    		 // var strSQL2=" select b.customerno,b.name,b.sex from lcbnf b "
         //            +" where b.InsuredNo ='" +document.all('CustomerNo').value+ "' "; 
          /*var strSQL2=" select distinct t.customerno,a.name, "
                     +" a.sex,(case a.sex when '0' then '��' when '1' then 'Ů' else '����' end) as sexname, "
                     +" t.postaladdress,t.zipcode,t.phone,t.mobile,t.email "
                     +" from lcaddress t,lcbnf a "
                     +" where t.customerno=a.insuredno "
                     +" and a.insuredno='" +document.all('CustomerNo').value+ "' ";    */
          var strSQL2 = wrapSql(tResourceName,"querysqldes4",[document.all('CustomerNo').value]);
          var arr2=easyExecSql( strSQL2 );
          if(arr2)
          {
          	fm.ReciCode.value = arr2[0][0];      //�ռ��˴���
            fm.ReciName.value = arr2[0][1];      //�ռ�������
            fm.ReciSex.value = arr2[0][2];       //�ռ����Ա����
            fm.ReciSexName.value = arr2[0][3];   //�ռ����Ա�
            fm.ReciAddress.value = arr2[0][4];   //�ռ��˵�ַ 
            fm.ReciZip.value = arr2[0][5];       //�ռ����ʱ�
            fm.ReciPhone.value = arr2[0][6];     //�ռ��˵绰
            fm.ReciMobile.value = arr2[0][7];    //�ռ����ֻ�
            fm.ReciEmail.value = arr2[0][8];     //�ռ���Email 
          }                                       
    	}
         
  }
  else   //���ռ�����Ϣ,��ֱ����ʾ����
  {
  	  fm.ReciCode.value = arr[0][0];      //�ռ��˴���
      fm.ReciName.value = arr[0][1];      //�ռ�������                              
      fm.Relation.value = arr[0][4];      //�ռ���������˹�ϵ                           
      fm.ReciAddress.value = arr[0][2];   //�ռ��˵�ַ               
      fm.ReciPhone.value = arr[0][5];     //�ռ��˵绰                             
      fm.ReciMobile.value = arr[0][6];    //�ռ����ֻ�            
      fm.ReciZip.value = arr[0][7];       //�ռ����ʱ�                             
      fm.ReciSex.value = arr[0][8];       //�ռ����Ա� 
     // fm.ReciSexName.value = arr[0][9];       
      fm.ReciEmail.value = arr[0][9];     //�ռ���Email                           
      fm.Remark.value = arr[0][3];        //�ռ���ϸ�� 
  }      
        
}                                                                  
                                                     
function initForm()                                                
{                                                      
	try                                                              
	{                                                  
		initInpBox();                                                  
		initParam();                                      
		//�ж�[����]��ť�ܷ���ʾ,0-����ʾ,1-��ʾ                       
		initIsShow();  
		//��ʼ����ѯ,��ѯ���ⰸ�µ��ռ�����Ϣ                                   
		initQuery();                                                   
	}                                                    
	catch(re)
	{
		alert("��LLClaimReciInfoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}


</script>
