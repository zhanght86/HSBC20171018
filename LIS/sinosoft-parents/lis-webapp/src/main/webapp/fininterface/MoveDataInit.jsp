<%
//�������ƣ�WriteToFileInit.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI"); %>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<script>
 var Type = "<%=request.getParameter("DealType")%>";	

</script>
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initForm() {
  try 
  {	
        fm.cManageCom.value = <%= tGI.ManageCom %>;
  	initMoveDataGrid();  	
  	initQuery(); 	
  }
  catch(re) 
  {
    alert("InitForm �����з����쳣:��ʼ���������!");
  }
}

// ��ȡ����Ϣ�б�ĳ�ʼ��
var MoveDataGrid;
function initMoveDataGrid() {                               
  var iArray = new Array();
  try {
  
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            	//�п�
    iArray[0][2]=10;            			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���κ�";         		//����
    iArray[1][1]="120px";            	//�п�
    iArray[1][2]=10;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="��������";      	   		//����
    iArray[2][1]="100px";            			//�п�
    iArray[2][2]=10;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="��������";      	   		//����
    iArray[3][1]="130px";            			//�п�
    iArray[3][2]=10;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="����Ա";      	   		//����
    iArray[4][1]="60px";            			//�п�
    iArray[4][2]=10;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    
    MoveDataGrid = new MulLineEnter( "document" , "MoveDataGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    MoveDataGrid.mulLineCount = 5;   
    MoveDataGrid.displayTitle = 1;
    MoveDataGrid.hiddenPlus = 1;
    MoveDataGrid.hiddenSubtraction = 1;
    MoveDataGrid.canSel = 1;
    MoveDataGrid.canChk = 1;
    MoveDataGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
  }
 
</script>

	
