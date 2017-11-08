<%
//程序名称：LLClaimReciInfoInit.jsp
//程序功能: 收件人信息录入
//创建人:niuzj
//创建日期:2005-10-25
%>
<!--用户校验类-->
<%
//添加页面控件的初始化。
GlobalInput globalInput = (GlobalInput)session.getValue("GI");

if(globalInput == null) {
	out.println("session has expired");
	return;
}

String strOperator = globalInput.Operator;   //操作员
String strManageCom = globalInput.ComCode;   //管理机构
%>
<script language="JavaScript">
var tResourceName="claim.LLClaimReciInfoInputSql";
//接收上个页面传递过来的参数
function initParam()
{
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");  //赔案号
    document.all('CustomerNo').value = nullToEmpty("<%= tcustomerNo %>");  //出险人代码
    document.all('IsShow').value = nullToEmpty("<%= tIsShow %>"); //[保存]按钮能否显示,0-不显示,1-显示
    document.all('RgtObj').value = nullToEmpty("<%= tRgtObj %>"); //个险团险标志,1-个险,2-团险
}

//把null的字符串转为空
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
		document.all('ReciCode').value = '';      //收件人代码
		document.all('ReciName').value = '';      //收件人姓名
		
		document.all('Relation').value='';        //收件人与出险人关系
		document.all('ReciAddress').value='';     //收件人地址
		
		document.all('ReciPhone').value='';        //收件人电话
		document.all('ReciMobile').value='';       //收件人手机
		
		document.all('ReciZip').value='';        //收件人邮编
		
		document.all('ReciSex').value='';        //收件人性别
		document.all('ReciSexName').value='';        
		
		document.all('ReciEmail').value='';     //收件人Email
		
		document.all('Remark').value = '';       //收件人细节
	//	document.all('RemarkDisabled').value = '';
	
	  

	}
	catch(ex)
	{
		alert("在LLClaimReciInfoInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initIsShow()
{
	//判断[保存]按钮能否显示,0-不显示,1-显示
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

//初始化查询,查询该赔案下的收件人信息
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
   if(arr[0][0]=="" && arr[0][1]=="" && arr[0][2]=="" && arr[0][3]=="" && arr[0][4]=="" && arr[0][5]=="" && arr[0][6]=="" && arr[0][7]=="" && arr[0][8]=="" && arr[0][9]=="")   //没有任何收件人信息
   {
  	  //-------------------------------------------------------------
  	  //收件人校验：非身故赔案默认被保人(出险人)、身故赔案默认受益人
  	  //-------------------------------------------------------------
      //首先判断是不是死亡案件,0-非身故,1-身故
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
      
      if(tDeadFlag == 0) //非身故案件,查询出险人信息
      {
      	  alert("在该赔案下未找到任何收件人信息,将默认为被保人信息！");  
      	  //var strSQL1=" select b.customerno,b.name,b.sex,b.rgtaddress from ldperson b "
          //           +" where b.customerno ='" +document.all('CustomerNo').value+ "' ";
         /* var strSQL1=" select t.customerno,b.name, "
                     +" b.sex,(case b.sex when '0' then '男' when '1' then '女' else '不详' end) as sexname, "
                     +" t.postaladdress,t.zipcode,t.phone,t.mobile,t.email "
                     +" from lcaddress t,ldperson b "
                     +" where t.customerno=b.customerno "
                     +" and t.customerno='" +document.all('CustomerNo').value+ "' ";*/
          var strSQL1 = wrapSql(tResourceName,"querysqldes3",[document.all('CustomerNo').value]);
          var arr1=easyExecSql( strSQL1 );
          if(arr1)
          {
          	fm.ReciCode.value = arr1[0][0];      //收件人代码
            fm.ReciName.value = arr1[0][1];      //收件人姓名
            fm.ReciSex.value = arr1[0][2];       //收件人性别代码
            fm.ReciSexName.value = arr1[0][3];   //收件人性别
            fm.ReciAddress.value = arr1[0][4];   //收件人地址 
            fm.ReciZip.value = arr1[0][5];       //收件人邮编
            fm.ReciPhone.value = arr1[0][6];     //收件人电话
            fm.ReciMobile.value = arr1[0][7];    //收件人手机
            fm.ReciEmail.value = arr1[0][8];     //收件人Email                               
          }
      }
      else  //身故案件,查询受益人信息
    	{   
    		  alert("在该赔案下未找到任何收件人信息,将默认为受益人信息！");  
    		 // var strSQL2=" select b.customerno,b.name,b.sex from lcbnf b "
         //            +" where b.InsuredNo ='" +document.all('CustomerNo').value+ "' "; 
          /*var strSQL2=" select distinct t.customerno,a.name, "
                     +" a.sex,(case a.sex when '0' then '男' when '1' then '女' else '不详' end) as sexname, "
                     +" t.postaladdress,t.zipcode,t.phone,t.mobile,t.email "
                     +" from lcaddress t,lcbnf a "
                     +" where t.customerno=a.insuredno "
                     +" and a.insuredno='" +document.all('CustomerNo').value+ "' ";    */
          var strSQL2 = wrapSql(tResourceName,"querysqldes4",[document.all('CustomerNo').value]);
          var arr2=easyExecSql( strSQL2 );
          if(arr2)
          {
          	fm.ReciCode.value = arr2[0][0];      //收件人代码
            fm.ReciName.value = arr2[0][1];      //收件人姓名
            fm.ReciSex.value = arr2[0][2];       //收件人性别代码
            fm.ReciSexName.value = arr2[0][3];   //收件人性别
            fm.ReciAddress.value = arr2[0][4];   //收件人地址 
            fm.ReciZip.value = arr2[0][5];       //收件人邮编
            fm.ReciPhone.value = arr2[0][6];     //收件人电话
            fm.ReciMobile.value = arr2[0][7];    //收件人手机
            fm.ReciEmail.value = arr2[0][8];     //收件人Email 
          }                                       
    	}
         
  }
  else   //有收件人信息,就直接显示出来
  {
  	  fm.ReciCode.value = arr[0][0];      //收件人代码
      fm.ReciName.value = arr[0][1];      //收件人姓名                              
      fm.Relation.value = arr[0][4];      //收件人与出险人关系                           
      fm.ReciAddress.value = arr[0][2];   //收件人地址               
      fm.ReciPhone.value = arr[0][5];     //收件人电话                             
      fm.ReciMobile.value = arr[0][6];    //收件人手机            
      fm.ReciZip.value = arr[0][7];       //收件人邮编                             
      fm.ReciSex.value = arr[0][8];       //收件人性别 
     // fm.ReciSexName.value = arr[0][9];       
      fm.ReciEmail.value = arr[0][9];     //收件人Email                           
      fm.Remark.value = arr[0][3];        //收件人细节 
  }      
        
}                                                                  
                                                     
function initForm()                                                
{                                                      
	try                                                              
	{                                                  
		initInpBox();                                                  
		initParam();                                      
		//判断[保存]按钮能否显示,0-不显示,1-显示                       
		initIsShow();  
		//初始化查询,查询该赔案下的收件人信息                                   
		initQuery();                                                   
	}                                                    
	catch(re)
	{
		alert("在LLClaimReciInfoInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}


</script>
