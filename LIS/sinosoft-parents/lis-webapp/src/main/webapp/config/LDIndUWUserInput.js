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
 if(document.all("UserCode").value == ""||document.all("UserCode").value==null )
 { 
 	alert("������˱�ʦ���룡");
 	return ;
 }
 if(document.all("UpUwPopedom").value == ""||document.all("UpUwPopedom").value==null )
 { 
 	alert("�������ϼ��˱�����");
 	return ;
 } 
 //2009-2-6 --����ʱУ��ú˱�ʦ�Ƿ�Ϊϵͳ�д��ڵĹ���
//   	    var strSQL="select 1 from LDUser where UserCode='"+fm.UserCode.value+"'";
   	    
   		var sqlid0="LDIndUWUserInputSql0";
   		var mySql0=new SqlClass();
   		mySql0.setResourceName("config.LDIndUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
   		mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
   		mySql0.addSubPara(fm.UserCode.value);//ָ������Ĳ���
   		var strSQL=mySql0.getString();
   	    
	 	//alert(strSQL);
	 	var arrResult = easyExecSql(strSQL);
	 	if(arrResult == null)
	 	{
	 		alert("ϵͳ�в����ڸú˱�ʦ���룬���ʵ��");
	 		return false;
	 	}
   // --end
   
 if(!beforeSubmit()) return false;
 /*
if(UWResultGrid. mulLineCount < 1 )
  {
  	alert("�����ú˱�����!");
  	return false;
  }
 */
 
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
	var iHeight=250;     //�������ڵĸ߶�; 
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //ִ����һ������
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
   if(!UWResultGrid.checkValue("UWResultGrid"))
   {
   	return false;
   }
  //��Ӳ���	
  if(UWResultGrid. mulLineCount < 1 )
  {
  	alert("�����ú˱�����!");
  	return false;
  }
  if(!UWMaxAmountGrid.checkValue("UWMaxAmountGrid"))
   {
   	return false;
   }
  if(UWMaxAmountGrid. mulLineCount < 1 )  
  {
  	alert("�����ú˱���������!");
  	return false;
  }
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
	   	if( cCodeName == "uwpopedom")     
	   	{
	   		//alert('1');
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
//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  //mOperate="INSERT||MAIN";
  beforeSubmit();
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
  beforeSubmit();
  fm.fmtransact.value = "INSERT||MAIN" ;
  
}           
//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  if((fm.UserCode1.value == null)||(fm.UserCode1.value == ""))
  {
  	alert("���Ȳ�ѯ��¼�����޸ģ�");  	
  }

  //����������Ӧ�Ĵ���
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
  {
  	if(!beforeSubmit()) return false;
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
  showInfo=window.open("./LDIndUWUserQuery.html");
}           
//Click�¼����������ɾ����ͼƬʱ�����ú���

function deleteClick()
{
  //����������Ӧ��ɾ������
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
  }
  else
  {
    alert("��ȡ����ɾ��������");
  }
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
              		//divLDUWUser3.style.display = "none";
              	}
              	else
              	{
              		
              		type="2";
	            	//divLDUWUser3.style.display = "";
              	}//write by yaory   
              
              	//alert(type);
//		var strSQL="select UserCode,UWType,UpUserCode,UpUWPopedom,UWPopedom,Remark,Operator,ManageCom,MakeDate,MakeTime,ModifyDate,ModifyTime, AddPoint,ClaimPopedom,EdorPopedom,riskrate,specjob,lowestamnt,overage,uwprocessflag,surpassgradeflag,UWOperatorFlag from LDUWUser where uwtype='"+type+"' and UserCode='"+arrQueryResult[0][0]+"'";
	 	
   		var sqlid1="LDIndUWUserInputSql1";
   		var mySql1=new SqlClass();
   		mySql1.setResourceName("config.LDIndUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
   		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
   		mySql1.addSubPara(type);//ָ������Ĳ���
   		mySql1.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
   		var strSQL=mySql1.getString();
		
		//alert(strSQL);
	 	arrResult = easyExecSql(strSQL);
	 	//alert(arrResult[0][11]); 
	 	document.all('UserCode').value= arrResult[0][0];
	 	document.all('UserCode1').value= arrResult[0][0];
                //alert(3)
                document.all('UWType').value= arrResult[0][1];
                document.all('UWType1').value= arrResult[0][1];
               // document.all('UpUserCode').value= arrResult[0][2];
                // alert(3)
               document.all('UpUwPopedom').value= arrResult[0][3];
               document.all('UwPopedom1').value= arrResult[0][4];
                //alert(arrResult[0][4]);
               document.all('UWPopedom').value= arrResult[0][4];
                 //alert(3)
               document.all('Remark').value= arrResult[0][5];
                document.all('Operator').value= arrResult[0][6];
                document.all('ManageCom').value= arrResult[0][7];
                //alert(3)
                document.all('MakeDate').value= arrResult[0][8];
                document.all('MakeTime').value= arrResult[0][9];
                //alert(4);
                document.all('ModifyDate').value= arrResult[0][10];
                document.all('ModifyTime').value= arrResult[0][11];
                document.all('AddPoint').value= arrResult[0][12];
                document.all('ClaimPopedom').value= arrResult[0][13];
                document.all('edpopedom').value= arrResult[0][14];
                document.all('Rate').value= arrResult[0][15];
                document.all('SpecJob').value= arrResult[0][16];
                document.all('LowestAmnt').value= arrResult[0][17];
                document.all('OverAge').value= arrResult[0][18];
                //tongmeng 2008-12-03 Modify
                document.all('UWProcessFlag').value= arrResult[0][19];
                document.all('SurpassGradeFlag').value= arrResult[0][20];
                document.all('UWOperatorFlag').value= arrResult[0][21];
                
                //alert(arrResult[0][14])
                //alert(arrResult[0][1]);
                if(arrResult[0][1]=="1")//add by yaory
                {tt="contuwstate";}
                if(arrResult[0][1]=="2")
                {tt="tdhbconlusion";}
                initUWResultGrid();//add by yaory end add���³�ʼ����Ϊ�˸ı�����
//                var strSQL2="select UWState,UWStateName from LDUWGrade where uwtype='"+type+"' and UserCode='"+arrQueryResult[0][0]+"'";
                
           		var sqlid2="LDIndUWUserInputSql2";
           		var mySql2=new SqlClass();
           		mySql2.setResourceName("config.LDIndUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
           		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
           		mySql2.addSubPara(type);//ָ������Ĳ���
           		mySql2.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
           		var strSQL2=mySql2.getString();
                
                //alert(strSQL2);
                turnPage.queryModal(strSQL2, UWResultGrid);
                //add by yaory
                //alert(arrQueryResult[0][1]);
                
              	//alert(type);
//                strSQL2="select a.UWKind,b.codename,a.MaxAmnt from LDUWAmntGrade a,ldcode b where a.UWType='"+type+"' and a.UserCode='"+arrQueryResult[0][0]+"' and trim(a.Uwkind)=trim(b.code) and b.codetype='uwkind'"  ;
                
           		var sqlid3="LDIndUWUserInputSql3";
           		var mySql3=new SqlClass();
           		mySql3.setResourceName("config.LDIndUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
           		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
           		mySql3.addSubPara(type);//ָ������Ĳ���
           		mySql3.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
           		strSQL2=mySql3.getString();
                
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
        
  		var sqlid4="LDIndUWUserInputSql4";
   		var mySql4=new SqlClass();
   		mySql4.setResourceName("config.LDIndUWUserInputSql"); //ָ��ʹ�õ�properties�ļ���
   		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
   		mySql4.addSubPara(UWType);//ָ������Ĳ���
   		mySql4.addSubPara(UWPopedom);//ָ������Ĳ���
   		var strSQL=mySql4.getString();
	
	} 
       // alert("uwtype:"+UWType)
       // alert("UWPopedom:"+UWPopedom)
       //fm.Remark.value = strSQL;
       
      	turnPage.queryModal(strSQL, UWMaxAmountGrid);
}

function showCodeName()
{
	showOneCodeName('UWType','UWType','UWTypeName');
	showOneCodeName('uwpopedom','UWPopedom','UWPopedomName');
	showOneCodeName('edpopedom','edpopedom','edpopedomName');
	showOneCodeName('clPopedom','ClaimPopedom','claimpopedomName');
	showOneCodeName('uwpopedom','UpUwPopedom','UpUserCodeName');
	showOneCodeName('SpecJob','SpecJob','SpecJobName');
	showOneCodeName('LowestAmnt','LowestAmnt','LowestAmntName');
	showOneCodeName('OverAge','OverAge','OverAgeName');
	showOneCodeName('yesno','UWProcessFlag','UWProcessFlagName');
	showOneCodeName('yesno','SurpassGradeFlag','SurpassGradeFlagName');
	showOneCodeName('yesno','UWOperatorFlag','UWOperatorFlagName');
}

