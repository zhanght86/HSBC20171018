

var arrDataSet 
var showInfo;
var mDebug="0";
var FlagDel;
var turnPage = new turnPageClass();


//������ʼ���ڽ��в�ѯ��Ҫ�����ڷ�Χ�ڵ����κ���
function PrintBill()
{
   if((fm.ManageCom.value=="")||(fm.ManageCom.value=="null"))
    {
    	alert("��ѡ��������!!");
    	   	return ;
    }
    if((fm.StartDate.value==""))
    {
    	alert("������ͳ������!!!");
    		return;
    	
    }
     if((fm.EndDate.value==""))
    {
    	alert("������ͳ��ֹ��!!!");
    		return;
    	
    }
    if(fm.StartDate.value>fm.EndDate.value)
    {
    	alert("ͳ�����ڱ���С�ڵ���ͳ��ֹ��!!!");
    		return;    	
  
    }
   

    
 
    	
    	var i = 0;
    	//var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    	
    	fm.action = './ReportLisSave.jsp';
    	fm.target="f1print";
    	fm.submit(); //�ύ
    	
    
}



