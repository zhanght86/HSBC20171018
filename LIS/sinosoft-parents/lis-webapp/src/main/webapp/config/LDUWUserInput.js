//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

function trimcode(){
  fm.UserCode.value = trim(document.all('UserCode').value);
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
function checkuseronly(comname)
{
	//add by yaory
	

} 
//�ύ�����水ť��Ӧ����
function submitForm()
{
 if(document.all("UserCode").value == ""||document.all("UserCode").value==null )
 { 
 	alert("������˱�ʦ���룡");
 	return ;
 }
 
 if(document.all("UWPopedom").value == ""||document.all("UWPopedom").value==null )
 { 
 	alert("��ѡ��˱�����");
 	return ;
 }
 
 if(document.all('UWPopedom').value == document.all('MaxGrade').value)//���ú˱�ʦ�ĺ˱�Ȩ��ΪE-��ǰΪ����� �ϼ��˱�������Ϊ'' modify by liuqh 2008-12-12
   {
      alert("�˺˱�ʦ�ĺ˱�Ȩ��Ϊ���:"+document.all('UWPopedom').value+", ���ϼ��˱�����Ϊ�գ�");
      document.all('UpUwPopedom').value ="";
      document.all('UpUserCodeName').value ="";
   }
 if((document.all("UpUwPopedom").value == ""||document.all("UpUwPopedom").value==null)&&document.all('UWPopedom').value!=document.all('MaxGrade').value )
 { 
 	alert("�������ϼ��˱�����");
 	return ;
 }
 if(checkUWGrade()==false) return false;
 
 //if(document.all("ClaimPopedom").value == ""||document.all("ClaimPopedom").value==null )
 //{ 
 //	alert("��ѡ�����⼶��");
 //	return ;
 //}
// var tt1=document.all("UWPopedom").value;
// var tt2=document.all("UpUwPopedom").value;
// if(tt1>=tt2)
// {
// 	alert("�ϼ�Ȩ�ޱ�������Լ���Ȩ�ޣ�");
// 	return ;
//}
 
  var i = 0;
  //if( verifyInput1() == false ) return false;
  
  fm.fmtransact.value="INSERT||MAIN";
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
  cleartDate();//�������
 
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

    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    queryClick();
    //ִ����һ������
  }else{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
	}
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
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
   //if(!UWResultGrid.checkValue("UWResultGrid"))
   //{
   //	return false;
   //}
  //��Ӳ���	
  //if(UWResultGrid. mulLineCount < 1 )
  //{
  //	alert("�����ú˱�����!");
  //	return false;
  //}
  //if(!UWMaxAmountGrid.checkValue("UWMaxAmountGrid"))
  // {
  // 	return false;
  // }
  //if(UWMaxAmountGrid. mulLineCount < 1 )  
  //{
  //	alert("�����ú˱���������!");
  //	return false;
  //}
  return true;

}

/*********************************************************************
 *  ����ѡ��󴥷�ʱ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{

		if( cCodeName == "UWType")
        	{
        		
	          try
	          {
	            if (Field.value!='1')
	            {   
	              
	            	  divLDUWUser3.style.display = "";
	            	   
	             	  document.all('AddPoint').value="0";
	                document.all('AddPoint').readOnly=true;
	               
	            }
	            else
	            {
	            	divLDUWUser3.style.display = "none";
	            	   
	             	  document.all('AddPoint').value="";
	                document.all('AddPoint').readOnly=false;
	            }
	          }
	          catch(ex) {} 
	        } 	    	          
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

function getMaxGrade(){
//  var tMaxGradeSQL = "select max(code) from ldcode where codetype = 'uwgrppopedom'";
  
	var sqlid0="LDUWUserInputSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("config.LDUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	var tMaxGradeSQL=mySql0.getString();
  
  var tMaxGrade = easyExecSql(tMaxGradeSQL);
  fm.MaxGrade.value = tMaxGrade;
}
//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  //mOperate="INSERT||MAIN";
  //beforeSubmit();
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
  //beforeSubmit();
  fm.fmtransact.value = "INSERT||MAIN" ;
}           
//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
//  var tSql="select count(*) from lduwuser where uwtype='2' and usercode='"+fm.UserCode.value+"'";
  
	var sqlid1="LDUWUserInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("config.LDUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.UserCode.value);//ָ������Ĳ���
	var tSql=mySql1.getString();
  
  var tCount= easyExecSql(tSql);
  if(tCount=="0")
  {
     alert("����Ϊ[ "+fm.UserCode.value+" ]�ĺ˱�ʦ������,����ӣ�");
     return false;
  }
  if((fm.UserCode.value == null)||(fm.UserCode.value == "")||fm.UWPopedom.value==""||fm.UWPopedom.value==null
      ||fm.UWPopedom1.value == null||fm.UWPopedom1.value=="")
  {
  	alert("���Ȳ�ѯ��¼�����޸ģ�");  
  	return;	
  }
  //���ú˱�ʦ�ĺ˱�Ȩ��Ϊ���ʱ �ϼ��˱�������Ϊ'' modify by liuqh 2008-12-12
  if(document.all('UWPopedom').value == document.all('MaxGrade').value)
   {
      alert("�˺˱�ʦ�ĺ˱�Ȩ��Ϊ���:"+document.all('UWPopedom').value+", ���ϼ��˱�����Ϊ�գ�");
      document.all('UpUwPopedom').value ="";
      document.all('UpUserCodeName').value ="";
   }
   if((document.all("UpUwPopedom").value == ""||document.all("UpUwPopedom").value==null)&&document.all('UWPopedom').value!=document.all('MaxGrade').value )
   { 
    	alert("�������ϼ��˱�����");
    	return ;
   }
   if(checkUWGrade()==false) return false;
  //����������Ӧ�Ĵ���
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
  {
  	//if(!beforeSubmit()) return false;
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
  fm.fmtransact.value = "UPDATE||MAIN";
  document.getElementById("fm").submit(); //�ύ
  cleartDate();//�������

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
  //showInfo=window.open("./LDUWUserQuery.html");
  if(document.all("UserCode").value == ""||document.all("UserCode").value==null )
 { 
 	alert("������˱�ʦ���룡");
 	return false;
 }
  
//  var strSql = " select uwpopedom,(select max(codename) from ldcode where codetype='uwgrppopedom' "
//              +" and trim(code)=a.uwpopedom),upuwpopedom,(select max(codename) from ldcode where "
//              +" codetype='uwgrppopedom' and trim(code)=a.upuwpopedom),claimpopedom from lduwuser a "
//              +" where usercode='"+fm.UserCode.value+"' and UWType='2' ";
  
	var sqlid2="LDUWUserInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("config.LDUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.UserCode.value);//ָ������Ĳ���
	var strSql=mySql2.getString();
  
	var arrResult = easyExecSql(strSql);
  if(arrResult != null)
  {
	//alert(arrResult);
	fm.UWPopedom.value=arrResult[0][0];
	fm.UWPopedomName.value=arrResult[0][1];
	fm.UpUwPopedom.value=arrResult[0][2];
	fm.UpUserCodeName.value=arrResult[0][3];
	fm.ClaimPopedom.value=arrResult[0][4];
	fm.UWPopedom1.value=arrResult[0][0];
	fm.UserCode1.value=fm.UserCode.value;
	fm.UWType1.value="2";
	}else{
	  alert("����Ϊ[ "+fm.UserCode.value+" ]�ĺ˱�ʦ������!");
	  //fm.UserCode.value = "";
	  fm.UWPopedom.value = "";
	  fm.UWPopedomName.value = "";
	  fm.UpUwPopedom.value = "";
	  fm.UpUserCodeName.value = "";
	  return false;
	  }
   return true;
}           
//Click�¼����������ɾ����ͼƬʱ�����ú���

function deleteClick()
{
  //����������Ӧ��ɾ������
  if(queryClick()==false) return false;
  if (confirm("��ȷʵ��ɾ���ü�¼��?"))
  {
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
  fm.fmtransact.value = "DELETE||MAIN";
  document.getElementById("fm").submit(); //�ύ
  initForm();
  cleartDate();//�������
  }
  else
  {
    alert("��ȡ����ɾ��������");
  }
}  
//�������
function cleartDate(){
	document.all("UserCode").value="";
	document.all("UWPopedom").value="";
	document.all("UpUwPopedom").value="";
	document.all("UWPopedomName").value="";
	document.all("UpUserCodeName").value="";
}
function checkUWGrade(){
   var tUWPopedom = fm.UWPopedom.value;
   var tUpUWPopedom = fm.UpUwPopedom.value;
   if(tUWPopedom>tUpUWPopedom&&tUWPopedom!=document.all('MaxGrade').value)
   {
     alert("��ѡ���[�ϼ��˱�����]����[�˱�Ȩ��],������ѡ��");
     return false;
   }
   return true;
}
//Click�¼�������������ñ������ޡ�ʱ�����ú���      
function setclick()
{  
  
    getMaxAmntInfo();
   
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
	//var arrResult = new Array();
	//alert(arrQueryResult[0][0]);
	if( arrQueryResult != null )
	{   
		//alert(arrQueryResult[0][1]);
		var type;
                if(arrQueryResult[0][1]=='����')//edit by yaory Ҫ��������� 
              	{
              		type="1";
              		divLDUWUser3.style.display = "none";
              	}
              	else
              	{
              		
              		type="2";
	            	divLDUWUser3.style.display = "";
              	}//write by yaory   
              
              	//alert(type);
//		var strSQL="select UserCode,UWType,UpUserCode,UpUWPopedom,UWPopedom,Remark,Operator,ManageCom,MakeDate,MakeTime,ModifyDate,ModifyTime, AddPoint,ClaimPopedom,EdorPopedom,riskrate,specjob,lowestamnt,overage from LDUWUser where uwtype='"+type+"' and UserCode='"+arrQueryResult[0][0]+"'";
	 	//alert(strSQL);
		
		var sqlid3="LDUWUserInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("config.LDUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(type);//ָ������Ĳ���
		mySql3.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
		var strSQL=mySql3.getString();
		
	 	arrResult = easyExecSql(strSQL);
	 	//alert(arrResult[0][11]); 
	 	document.all('UserCode').value= arrResult[0][0];
	 	document.all('UserCode1').value= arrResult[0][0];
                //alert(3)
                document.all('UWType').value= arrResult[0][1];
                document.all('UWType1').value= arrResult[0][1];
               // document.all('UpUserCode').value= arrResult[0][2];
                // alert(3)
               document.all('UpUWPopedom').value= arrResult[0][3];
               document.all('UWPopedom1').value= arrResult[0][4];
                //alert(arrResult[0][4]);
               document.all('UWPopedom').value= arrResult[0][4];
                 //alert(3)
               document.all('Remark').value= arrResult[0][5];
                document.all('Operator').value= arrResult[0][6];
                document.all('ManageCom').value= arrResult[0][7];
                //alert(3)
                document.all('MakeDate').value= arrResult[0][8];
                document.all('MakeTime').value= arrResult[0][9];
                //alert(4)
                document.all('ModifyDate').value= arrResult[0][10];
                document.all('ModifyTime').value= arrResult[0][11];
                document.all('AddPoint').value= arrResult[0][12];
                document.all('ClaimPopedom').value= arrResult[0][13];
                document.all('edpopedom').value= arrResult[0][14];
                document.all('Rate').value= arrResult[0][15];
                document.all('SpecJob').value= arrResult[0][16];
                document.all('LowestAmnt').value= arrResult[0][17];
                document.all('OverAge').value= arrResult[0][18];
                //alert(arrResult[0][14])
                //alert(arrResult[0][1]);
                if(arrResult[0][1]=="1")//add by yaory
                {tt="contuwstate";}
                if(arrResult[0][1]=="2")
                {tt="tdhbconlusion";}
                initUWResultGrid();//add by yaory end add���³�ʼ����Ϊ�˸ı�����
//                var strSQL2="select UWState,UWStateName from LDUWGrade where uwtype='"+type+"' and UserCode='"+arrQueryResult[0][0]+"'";
                //alert(strSQL2);
                
        		var sqlid4="LDUWUserInputSql4";
        		var mySql4=new SqlClass();
        		mySql4.setResourceName("config.LDUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
        		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
        		mySql4.addSubPara(type);//ָ������Ĳ���
        		mySql4.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
        		var strSQL2=mySql4.getString();
                
                turnPage.queryModal(strSQL2, UWResultGrid);
                //add by yaory
                //alert(arrQueryResult[0][1]);
                
              	//alert(type);
//                strSQL2="select a.UWKind,b.codename,a.MaxAmnt from LDUWAmntGrade a,ldcode b where a.UWType='"+type+"' and a.UserCode='"+arrQueryResult[0][0]+"' and trim(a.Uwkind)=trim(b.code) and b.codetype='uwkind'"  ;
                
        		var sqlid5="LDUWUserInputSql5";
        		var mySql5=new SqlClass();
        		mySql5.setResourceName("config.LDUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
        		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
        		mySql5.addSubPara(type);//ָ������Ĳ���
        		mySql5.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
        		strSQL2=mySql5.getString();
                
                turnPage.queryModal(strSQL2, UWMaxAmountGrid);
                fm.UWType.style.readonly=true;
                showCodeName();                 
      }
} 

function hh()
{
	
	//alert(tt);
	var mm=fm.UWType.value;
	if(mm=="1")
	{tt="contuwstate";}
	if(mm=="2")
	{tt="tdhbconlusion";}
	//alert(tt);
	initUWResultGrid();
	//alert(UWType);
}
//��ѯ�˱�����������Ϣ  
function getMaxAmntInfo()
{
	
	var UWType=document.all("UWType").value;
	
	var UWPopedom=document.all("UWPopedom").value;
	if((UWType==null||UWType=="")||(UWPopedom==null||UWPopedom==""))
	{
		alert("������˱�ʦ���ͺ˱�Ȩ��");
		return ;
	}
	
	if(UWType!=null&&UWPopedom!=null)
	{ 
		
  //      var strSQL ="select Uwkind,(select codename from ldcode a where trim(a.codetype)='uwkind' and trim(a.code)=trim(Uwkind)),MaxAmnt from LDUWGradePerson where UWType='"+UWType+"' and UWPopedom='"+UWPopedom+"'";
//       var strSQL ="select uwkind,(select codename from ldcode a where trim(a.codetype)='uwkind' and trim(a.code)=trim(Uwkind)),MaxAmnt from LDUWGradePerson where UWType='"+UWType+"' and UWPopedom='"+UWPopedom+"'";
       
		var sqlid6="LDUWUserInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("config.LDUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(UWType);//ָ������Ĳ���
		mySql6.addSubPara(UWPopedom);//ָ������Ĳ���
		var strSQL=mySql6.getString();
	
	} 
       // alert("uwtype:"+UWType)
       // alert("UWPopedom:"+UWPopedom)
       //fm.Remark.value = strSQL;
       
      	turnPage.queryModal(strSQL, UWMaxAmountGrid);
}

function showCodeName()
{
	showOneCodeName('UWType','UWType','UWTypeName');
	showOneCodeName('uwgrppopedom','UWPopedom','UWPopedomName');
	showOneCodeName('edpopedom','edpopedom','edpopedomName');
	showOneCodeName('clPopedom','ClaimPopedom','claimpopedomName');
	showOneCodeName('uwgrppopedom','UpUwPopedom','UpUserCodeName');
	showOneCodeName('SpecJob','SpecJob','SpecJobName');
	showOneCodeName('LowestAmnt','LowestAmnt','LowestAmntName');
	showOneCodeName('OverAge','OverAge','OverAgeName');
}

