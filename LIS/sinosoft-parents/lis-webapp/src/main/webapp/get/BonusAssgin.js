//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//�������ƣ�
//�����ܣ��ֺ��շ���������
//�������ڣ�2008-11-09 17:55:57
//������  ������ͥ
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  queryClick();
  if (LOBonusPolGrid.mulLineCount==0 )
  { 
  	content="����ʧ��,�����ʧ��ԭ������ǣ�1,����������,,�뼰ʱ���,����ԭ������Ǵ˱����Ѿ����뱣ȫ��Ŀ! \n2,��ȵķֺ췽�������� \n3,�����Ѿ���ֹ \n4,��������Ӧ����δ���ȵȣ����ʵ!";            
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
  }
  else
  { 
  	content="����ɹ�";
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
  }
  //queryClick();
}




//updateClick�¼�
function updateClick()
{		        
	         var tFiscalYear=fm.FiscalYear.value;
	         var tContNo=fm.ContNo.value;
	         	if(tFiscalYear==null || tFiscalYear=='')
	         {
	         	     alert("������������Ȳ���Ϊ��");
                 return false;
	         	}	
	        // var tNextFisCalYear="";
	        // var sql="select max(FiscalYear) from lobonuspol where contno='"+tContNo+"' and bonusflag='1' and  agetdate is not null";
    	    // var arrResult = easyExecSql(sql);
    	    // //˵���������״ηֺ�
    	    // //���ѯ�Ѿ�����δ����ĺ���
    	    // var tMaxFisCalYear="";
    	    // if(arrResult==null ||arrResult[0][0]=='')
    	    // {
    	    // 	var tSql="select min(FiscalYear) from lobonuspol where contno='"+tContNo+"' and bonusflag='0' and  agetdate is  null";
    	    //  var tarrResult = easyExecSql(tSql);	
    	    //  if(tarrResult==null || tarrResult[0][0]=='')
    	    //  {
    	    //  		 alert("��ѯ������ʷ�ֺ���ȳ���");
          //       return false;
    	    //  	}else
    	    //  		{
    	    //  			tMaxFisCalYear=tarrResult[0][0];
    	    //  			tNextFisCalYear=(parseInt(tarrResult[0][0])+1).toString();
    	    //  		}
    	    // 	}else
    	    // 		{   tMaxFisCalYear=arrResult[0][0]; 	     			
    	    //    	  tNextFisCalYear=(parseInt(arrResult[0][0])+1).toString(); 			
    	    // 			}
    	    // 	if(tNextFisCalYear!=tFiscalYear)
    	    // 	{
    	    // 			 alert("������������������󣬱������ѷֺ�������ֵΪ"+tMaxFisCalYear+"��Ӧ����"+tNextFisCalYear);
          //       return false;
    	    // 		
    	    // 		}		
	         	if(tContNo==null || tContNo=='')
	         {
	         	     alert("�����Ų���Ϊ�գ�����������");
                 return false;
	         	}	  
	          queryClick();
	          if(LOBonusPolGrid.mulLineCount>0)
            {   
	         	     alert("�����˷ֺ�����Ѿ����䣬�����ظ����䣡");
                 return false;
           	}      
           var showStr="���ڼ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
           var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
           fm.action="./BonusAssginSave.jsp";
           document.getElementById("fm").submit(); //�ύ		
           fm.updatebutton.disabled=true;

}           

//queryClick�¼�
function queryClick()
{
   var tFiscalYear=fm.FiscalYear.value;
   
   var sqlid830160838="DSHomeContSql830160838";
var mySql830160838=new SqlClass();
mySql830160838.setResourceName("get.BonusAssginInputSql");//ָ��ʹ�õ�properties�ļ���
mySql830160838.setSqlId(sqlid830160838);//ָ��ʹ�õ�Sql��id
mySql830160838.addSubPara(fm.FiscalYear.value);//ָ������Ĳ���
mySql830160838.addSubPara(fm.ContNo.value);//ָ������Ĳ���
var tSql_1=mySql830160838.getString();

   
//   var tSql_1=" select polno,contno,FiscalYear,BONUSMONEY,decode(BONUSFLAG,'1','�ѷ���','0','�Ѽ���'),BONUSCOEF,SGETDATE,"
//             +" decode(BONUSGETMODE,'1','�ۼ���Ϣ','2','�ֽ�','3','�ֽ����ڱ���','5','�����') from LOBonusPol where FiscalYear='"+fm.FiscalYear.value+"' AND contno='"+fm.ContNo.value+"'  and BONUSFLAG='1' order by PolNo,SGETDATE "; 
    turnPage.queryModal(tSql_1,LOBonusPolGrid);       
}           


