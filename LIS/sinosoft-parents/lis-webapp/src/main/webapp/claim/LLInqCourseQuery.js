var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
function LLInqCourseQuery()

{//Modify by zhaorx 2006-03-13
     /*var strSQL = "select ClmNo,InqNo,CouNo,InqDate,InqMode,InqSite,InqByPer,InqCourse,InqDept,InqPer1,InqPer2,Remark,"
                 + " (select lduser.username from lduser where lduser.usercode = LLinqcourse.operator) from LLinqcourse where 1=1 "
                 + getWherePart('ClmNo','ClmNo')
                 + getWherePart('InqNo','InqNo')
                 + " order by ClmNo";  */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqCourseQueryInputSql");
	mySql.setSqlId("LLInqCourseQuerySql1");
	mySql.addSubPara(fm.ClmNo.value );   
	mySql.addSubPara(fm.InqNo.value );   
     //prompt("查看调查过程",strSQL);           
     turnPage.queryModal(mySql.getString(), LLInqCourseGrid);
}
//选中LLInqCourseGrid响应事件
function LLInqCourseGridClick()
{
	initInpBox(); 
    var i = LLInqCourseGrid.getSelNo();
    if (i != '0')
    {  
    	i = i - 1;
	   fm.ClmNo1.value = LLInqCourseGrid.getRowColData(i,1);            
	   fm.InqNo1.value = LLInqCourseGrid.getRowColData(i,2);              
	   fm.CouNo.value = LLInqCourseGrid.getRowColData(i,3); 	    
	   fm.InqDate.value = LLInqCourseGrid.getRowColData(i,4);            
	   fm.InqMode.value = LLInqCourseGrid.getRowColData(i,5);            
	   fm.InqSite.value = LLInqCourseGrid.getRowColData(i,6);            
	   fm.InqByPer.value = LLInqCourseGrid.getRowColData(i,7);  
	   //alert("fm.InqCourse.value="+LLInqCourseGrid.getRowColData(i,8));	   
	   fm.InqCourse.value = LLInqCourseGrid.getRowColData(i,8);          
	   //fm.InqDept.value = LLInqCourseGrid.getRowColData(i,9);            
	   fm.InqPer1.value = LLInqCourseGrid.getRowColData(i,10);           
	   fm.InqPer2.value = LLInqCourseGrid.getRowColData(i,11);           
	   fm.Remark.value = LLInqCourseGrid.getRowColData(i,12);  
       showOneCodeName('llinqmode','InqMode','InqModeName');   
    }        
    LLInqCertificateQuery();
}


//影像查询---------------2005-08-14添加
function colQueryImage()
{
    var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打印赔案号条形码
function colBarCodePrint()
{
    fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    document.getElementById("fm").submit();
}

function LLInqCertificateQuery()
{
	/*var strSql="select cerno,certype,cername,case oriflag when '0' then '原件' when '1' then '复印件' end,cercount,remark from llinqcertificate where 1=1 "
			+" and clmno='"+fm.ClmNo1.value+"' "
			+" and inqno='"+fm.InqNo1.value+"' "
			+" and couno='"+fm.CouNo.value+"' "
			+" order by cerno";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqCourseQueryInputSql");
	mySql.setSqlId("LLInqCourseQuerySql2");
	mySql.addSubPara(fm.ClmNo1.value );   
	mySql.addSubPara(fm.InqNo1.value );  
	mySql.addSubPara(fm.CouNo.value );  
	turnPage2.queryModal(mySql.getString(), LLInqCertificateGrid);
}


