<!--
*******************************************************
* 程序名称：ManageComLimit.jsp
* 程序功能：用户信息校验页面
* 创建日期：2002-11-27
* 更新记录：  更新人    更新日期     更新原因/内容
*             tjj   2002-11-27    新建
*      
*******************************************************
-->
 <%
  GlobalInput tGI = new GlobalInput();
     tGI=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
 %>
	<script language="javascript">
	var ComLength=8;
		function parseManageComLimit()
	{
		var ManageCom = '<%=tGI.ManageCom%>';
		//		alert(ManageCom);
		var tStartManageCom,tEndManageCom;
		
		var tLength = trim(ManageCom).length;
		if (tLength<8)
		{
			var tPar1='0';
			var tPar2='9';
			var tReturn;
			//alert(tPar1);
			for (i=tLength;i<ComLength-1;i++)
			{
				tPar1 = tPar1 +'0'; 
				trim(tPar1);
			}
			
			for (i=tLength;i<ComLength-1;i++)
			{
				tPar2 = tPar2 +'9'; 
				trim(tPar2);
			}
			tStartManageCom = ManageCom + tPar1;
			tEndManageCom = ManageCom + tPar2;
		}
		else
		{
			tStartManageCom = ManageCom;
			tEndManageCom = ManageCom;
		}
		
		try {
		  //add for MO ，使迁移后在迁出公司能够查询出批单
  		tReturn = " ( ( ManageCom >='"+tStartManageCom+"' and ManageCom <='"+tEndManageCom+"') or ManageCom in "
  		        + "(select managecomnew from lpmove where 1=1 "  
  		        + getWherePart('PolNoOld', 'PolNo' )
  		        + getWherePart('EdorNo', 'EdorAppNo' )
  				    + getWherePart( 'EdorNo' ) + ") )";
	  }
	  catch (e) {
	    tReturn = " ManageCom >='"+tStartManageCom+"' and ManageCom <='"+tEndManageCom+"' ";
	  }
		//alert(tReturn);
		return tReturn;
	}
	</script>
	<%
%>