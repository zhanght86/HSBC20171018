
<!--
*******************************************************
* �������ƣ�ManageComLimit.jsp
* �����ܣ��û���ϢУ��ҳ��
* �������ڣ�2002-11-27
* ���¼�¼��  ������    ��������     ����ԭ��/����
*             tjj   2002-11-27    �½�
*      
*******************************************************
-->
 <%
    GlobalInput tGI = new GlobalInput();
     tGI=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
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
		
		tReturn = "( ManageCom >='"+tStartManageCom+"' and ManageCom <='"+tEndManageCom+"' or ManageCom = '"+ManageCom+"')";
		//alert(tReturn);
		return tReturn;
	}
	
	function parseManageComLimitlike()
	{
		var ManageCom = '<%=tGI.ManageCom%>';		
		var tReturn = " and ManageCom like '"+ ManageCom +"%25'";		
		return tReturn;
	}
	
	function parseManageComLimitlikeSqlChange()
	{		
		var tReturn = "" + '<%=tGI.ManageCom%>'+"%25";		
		return tReturn;
	}
	
		function getManageComLimit(cond)
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
		
		tReturn ="("+ cond + " between '"+tStartManageCom+"' and '"+tEndManageCom+"' or "+cond+" = '"+ManageCom+"')";
		return tReturn;
	}
	
	function getManageComLimitlike(cond)
	{
		var ManageCom = '<%=tGI.ManageCom%>';			
		var tReturn = " and "+ cond + " like '"+ManageCom+"%25'";
		return tReturn;
	}
	
	</script>
	<%
%>
