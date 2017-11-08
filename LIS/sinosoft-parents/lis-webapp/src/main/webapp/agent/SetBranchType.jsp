<%
   String tBranchType = "";
   String tSql1 =" BranchLevel";
   String tSqlAgentGrade =""; //限制代理人职级的查询条件（BranchType不同，职级显示不同）
   String tSqlAgentGrade1="";
   String tSqlDestAgentGrade =""; //上面所查职级在外加'清退00'
   String tSqlBranchLevel =""; //限制销售单位级别的查询条件（BranchType不同）
   String tSqlBranchLevel1=""; //续期机构
   String sqlmEdorType="";
   try
   {
      	tBranchType=request.getParameter("BranchType");
      	if (tBranchType!=null)
   		session.putValue("BranchType",tBranchType);
   	tBranchType=(String)session.getValue("BranchType");
   	if(tBranchType=="1")
   	{
   		tSql1=" BranchLevel";
   	}
   	if(tBranchType=="2")
   	{
   		tSql1=" BranchLevel2";
   	}
   	if(tBranchType=="3")
   	{
   		tSql1=" BranchLevel";
   	}
   	//weikai 2005-4-18 添加一种机构类型
   	if(tBranchType=="4")
   	{
   	    tSql1=" BranchLevel";
   	}
   	//修改时间： 2006-03-29 LL 添加续收督导类型
   	if(tBranchType=="5")
   	{
   	    tSql1=" BranchLevel";
   	}
   	
   	/* liujw */
   	tSqlAgentGrade = "1 and codealias = #"+tBranchType+"#";
   	tSqlAgentGrade1= "1 and code !=#F08# and codealias = #"+tBranchType+"#";
   	tSqlDestAgentGrade = "1 and (("+tBranchType+"=1 and (codealias is null or codealias = #"+tBranchType+"#)) or ("+tBranchType+"<>1 and codealias = #"+tBranchType+"#))";
   	tSqlBranchLevel = "1 and othersign = #"+tBranchType+"# and trim(codename) <> #支公司#";
    tSqlBranchLevel1= "1 and othersign = #"+tBranchType+"#"   ; 
    sqlmEdorType="1 and codealias=#"+tBranchType+"#";
   }
   catch(Exception e)
   {}
%>
<script language="javascript">
 
 function getBranchType()
 {
 var tBranchType;
 tBranchType=<%=tBranchType%>;
 return tBranchType;
 }
</script>
