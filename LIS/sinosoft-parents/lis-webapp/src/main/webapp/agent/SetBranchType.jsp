<%
   String tBranchType = "";
   String tSql1 =" BranchLevel";
   String tSqlAgentGrade =""; //���ƴ�����ְ���Ĳ�ѯ������BranchType��ͬ��ְ����ʾ��ͬ��
   String tSqlAgentGrade1="";
   String tSqlDestAgentGrade =""; //��������ְ�������'����00'
   String tSqlBranchLevel =""; //�������۵�λ����Ĳ�ѯ������BranchType��ͬ��
   String tSqlBranchLevel1=""; //���ڻ���
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
   	//weikai 2005-4-18 ���һ�ֻ�������
   	if(tBranchType=="4")
   	{
   	    tSql1=" BranchLevel";
   	}
   	//�޸�ʱ�䣺 2006-03-29 LL ������ն�������
   	if(tBranchType=="5")
   	{
   	    tSql1=" BranchLevel";
   	}
   	
   	/* liujw */
   	tSqlAgentGrade = "1 and codealias = #"+tBranchType+"#";
   	tSqlAgentGrade1= "1 and code !=#F08# and codealias = #"+tBranchType+"#";
   	tSqlDestAgentGrade = "1 and (("+tBranchType+"=1 and (codealias is null or codealias = #"+tBranchType+"#)) or ("+tBranchType+"<>1 and codealias = #"+tBranchType+"#))";
   	tSqlBranchLevel = "1 and othersign = #"+tBranchType+"# and trim(codename) <> #֧��˾#";
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
