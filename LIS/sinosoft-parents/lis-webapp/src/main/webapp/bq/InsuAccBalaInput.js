//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//�������ƣ�
//�����ܣ����������������
//�������ڣ�2007-11-09 17:55:57
//������  ������ͥ
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 


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

    showInfo.close();
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

    showInfo.close();
  }
  //queryClick();
}




//updateClick�¼�
function updateClick()
{		        
	         var tRiskCode=fm.RiskCode.value;
	         var tBalaDate=fm.BalaDate.value;

	         	if(tRiskCode==null || tRiskCode=='')
	         {
	         	     alert("���ֲ���Ϊ��");
                 return false;
	         	}	         
	         if(tBalaDate==null || tBalaDate=='')
	         {
	         	     alert("�����ղ���Ϊ��");
                 return false;
	         	}

           var showStr="�����޸����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
           var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
           //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
		  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

           fm.action="./InsuAccBalaSave.jsp";
           document.getElementById("fm").submit(); //�ύ		
           fm.updatebutton.disabled=true;

}           

//queryClick�¼�
function queryClick()
{
   var tRiskCode=fm.RiskCode.value;
   
   var sqlid901094741="DSHomeContSql901094741";
var mySql901094741=new SqlClass();
mySql901094741.setResourceName("bq.InsuAccBalaInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901094741.setSqlId(sqlid901094741);//ָ��ʹ�õ�Sql��id
mySql901094741.addSubPara(tRiskCode);//ָ������Ĳ���
var tSql_1=mySql901094741.getString();
   
//   var tSql_1="select RISKCODE,(select riskshortname from lmrisk a where  a.riskcode=c.riskcode),PolNo,INSUACCNO,"
//             +" (select cvalidate from lcpol d where d.polno =c.polno),paydate,Money,(select trim(codename) from ldcode where codetype = 'finfeetype' and trim(code)=trim(MONEYTYPE))"
//             +" ,(select sum(money) from lcinsureacctrace r where  r.PolNo = c.PolNo and paydate >=date'1900-01-01' and paydate <=c.paydate) from  lcinsureacctrace  c where paydate like '%01%' and riskcode = '"+tRiskCode+"' order by PolNo,paydate ";             
    var brr = easyExecSql(tSql_1);
    if(!brr)
    {
        LMInsuAccRateGrid.clearData();
        alert("���ݿ���û���������������ݣ�");
        return false;
    }
    turnPage.queryModal(tSql_1,LMInsuAccRateGrid);       
}           


