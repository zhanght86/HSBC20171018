var turnPage = new turnPageClass();

function all_Query()
{
	 /*  var sql="select ManageCom,business,totalCount,throughCount,concat(round((throughCount / totalCount)*100,2),'%') as rate " +
	   		" from (select ManageCom,business,count(ResultFlag) as totalCount,sum(to_Number(ResultFlag)) as throughCount " +
	   		" from LRResultMain where 1=1 " ;
	   sql=prepareSQL(sql)+" group by ManageCom, business)";
	   if(sql==false)
	   {
		   alert("ƴдSQL��ѯ�������");
		   return ;
	   }*/
	  var sqlid1="ThPutStaSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName('ibrms.ThPutStaSql'); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		
		mySql1.addSubPara(fm.ManageCom.value);
		mySql1.addSubPara(fm.Business.value);
		mySql1.addSubPara(fm.RecordStartDate.value);
		mySql1.addSubPara(fm.RecordEndDate.value);
		
	  var strSql=mySql1.getString();	
	  
	   turnPage.queryModal(strSql, QueryGrpGrid);
}
function prepareSQL(sql)
{
	   sql+= getWherePart("ManageCom", "ManageCom")
		+ getWherePart("Business", "Business");
	  
	   try{
	       var RecordStartDate=fm.RecordStartDate.value.trim();
	       var RecordEndDate=fm.RecordEndDate.value.trim();
	       
	       if(!!RecordStartDate)
	       {
	    	   sql+=" and MakeDate>="+RecordStartDate;
	       }
	       
	       if(!!RecordEndDate)
	       {
	    	   sql+=" and MakeDate<="+RecordEndDate;
	       }
	   }catch(e)
	   {
		   alert("<��ʼ����>����<��ֹ����>��ȡ����");
		   return false;
	   }
	   return sql;
}
   function detail_Query()
   {
	   
   }
    function analyse_data_pol(){
    	if (QueryGrpGrid.mulLineCount == 0)
    	{
    	  alert ("���Ƚ��в�ѯ.");
    	  return false ;
    	}else {
    		turnPage.pageIndex = 1;
    		turnPage.firstPage();
    	easyQueryPrint(2,'QueryGrpGrid','turnPage') ;
    	return true ;}
    	}
    
    var sFeatures = "status:0;help:0;close:0;scroll:0;dialogWidth:1200px;dialogHeight:800px;resizable=0";
    + "dialogLeft:0;dialogTop:0;";
var win_father=window;

function easyQueryPrint(vFlag,vMultiline,vTurnPage) {
//urlStr����ӡ����URL�Ͳ�ѯ����
var urlStr = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage;
//window.showModalDialog(urlStr, win_father, sFeatures);  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1200;      //�������ڵĿ��; 
	var iHeight=800;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}