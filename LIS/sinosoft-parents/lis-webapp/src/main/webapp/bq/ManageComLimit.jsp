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
     tGI=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
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
		  //add for MO ��ʹǨ�ƺ���Ǩ����˾�ܹ���ѯ������
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