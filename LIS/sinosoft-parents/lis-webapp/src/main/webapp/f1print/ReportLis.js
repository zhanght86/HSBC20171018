

var arrDataSet 
var showInfo;
var mDebug="0";
var FlagDel;
var turnPage = new turnPageClass();


//根据起始日期进行查询出要该日期范围内的批次号码
function PrintBill()
{
   if((fm.ManageCom.value=="")||(fm.ManageCom.value=="null"))
    {
    	alert("请选择管理机构!!");
    	   	return ;
    }
    if((fm.StartDate.value==""))
    {
    	alert("请输入统计起期!!!");
    		return;
    	
    }
     if((fm.EndDate.value==""))
    {
    	alert("请输入统计止期!!!");
    		return;
    	
    }
    if(fm.StartDate.value>fm.EndDate.value)
    {
    	alert("统计起期必须小于等于统计止期!!!");
    		return;    	
  
    }
   

    
 
    	
    	var i = 0;
    	//var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    	
    	fm.action = './ReportLisSave.jsp';
    	fm.target="f1print";
    	fm.submit(); //提交
    	
    
}



