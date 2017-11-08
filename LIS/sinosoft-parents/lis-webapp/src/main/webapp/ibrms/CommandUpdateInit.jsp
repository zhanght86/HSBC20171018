<%
	//程序名称：bomFunInit.jsp
	//程序功能：对BOM和词条的初始化
	//创建日期：2008-8-13
	//创建人  ：
	//更新记录：
%>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<%
	//添加页面控件的初始化。
%>


<script language="JavaScript">
	var tResourceName = 'ibrms.CommandUpdateSql';
	function initInpBox() {
		try {	
			
		var sqlid1="CommandUpdateSql1";
		var name = document.all('Name').value;
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(name);//指定传入的参数
		
	  var strSql=mySql1.getString();	
	  
			
			//var sql = "select * from lrcommand where name='"+name+"'";
			var arrResult = easyExecSql(strSql,1,0);
			var i=arrResult.length;
			if (arrResult == null) {
			  	return;
			  }else{
				 for(j=0;j<i;j++)
			 	 {
					document.all('Name').value = arrResult[j][0];
					document.all('display').value = arrResult[j][1];
					document.all('implenmation').value = arrResult[j][2];
					document.all('Valid').value = arrResult[j][3];			
					document.all('commandType').value = arrResult[j][4];
					document.all('ParaNum').value = arrResult[j][5];
					document.all('paratype').value = arrResult[j][6];	
					document.all('resulttype').value = arrResult[j][7];										
					document.all('Description').value = arrResult[j][8];		
					document.all('CommType').value = arrResult[j][9];		
					document.all('CommDetail').value = arrResult[j][10];		
				  }
			  }
			  
			  if(document.all('CommType').value!='0')
			  {
			  	CommDetailDiv.style.display = "";
			  }		
			  else
			  {
			  	CommDetailDiv.style.display = "none";
			  }
		} catch (ex) {

		}
	}

	function initSelBox() {
		try {
		
		} catch (ex) {
			alert("在CommandUpdateInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();			
		} catch (re) {
			alert("CommandUpdateInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
		}
	}

	
</script>
