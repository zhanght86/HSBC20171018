<%
//Creator :���	
//Date :2008-08-18
%>

<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
%>
<script language="JavaScript">
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("��ҳ��ʱ�������µ�¼");
		return;
	}
	String strOperator = globalInput.Operator;
%>   
function initInpBox()
{
  try
  {

    fm.reset();
    document.all('VersionNo').value = VersionNo;
    document.all('VersionState').value = VersionState;
    document.all('AcquisitionID').value = AcquisitionID;
  	document.all('KeyID').value = '';     
  	//document.all('KeyID1').value = '';     
  	document.all('KeyIDName').value = '';    
    document.all('KeyName').value = '';
    document.all('KeyOrder').value = '';    
    document.all('Remark').value = '';   
	if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			document.all('addbutton').disabled = true;				
			
			document.all('deletebutton').disabled = true;	
		}  
  }
  catch(ex)
  {
    alert("���г�ʼ���ǳ��ִ��󣡣�����");
  }
}


// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("CostDataKeyDefInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initCostDataKeyDefInputGrid();
  }
  catch(re)
  {
    alert("CostDataKeyDefInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initCostDataKeyDefInputGrid()
{
	var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�汾���";    	//����
      iArray[1][1]="300px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ݲɼ����";         			//����
      iArray[2][1]="300px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="������ʶ";         			//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         			//����
      iArray[4][1]="250px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[5]=new Array();
      iArray[5][0]="�������";         			//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����";         			//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      CostDataKeyDefInputGrid = new MulLineEnter( "document" , "CostDataKeyDefInputGrid" ); 
      CostDataKeyDefInputGrid.mulLineCount = 0;   
      CostDataKeyDefInputGrid.displayTitle = 1;
      CostDataKeyDefInputGrid.canSel=1;
      CostDataKeyDefInputGrid.selBoxEventFuncName = "ReturnData";
      CostDataKeyDefInputGrid.locked = 1;	
	 	 	CostDataKeyDefInputGrid.hiddenPlus = 1;
	  	CostDataKeyDefInputGrid.hiddenSubtraction = 1;
      CostDataKeyDefInputGrid.loadMulLine(iArray);  
      CostDataKeyDefInputGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
