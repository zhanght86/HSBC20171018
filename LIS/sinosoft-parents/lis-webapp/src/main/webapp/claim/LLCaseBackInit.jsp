<%
//Name: LLCaseBackInit.jsp
//function��������Ϣ��
//author: wl
//Date: 2006-9-6 14:33
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
	
//���ջ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('BackTypeQ').value = nullToEmpty("<%= tBackType %>");

    //alert(document.all('BackTypeQ').value);
    
    if(document.all('BackTypeQ').value=='1')
    {
    	divLLReport2.style.display='none';
        operateButton1.style.display="none";
    }
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

//��ť����ѡ����
function ButtonSelected()
{
    var submitflag = <%=tSubmitFlag%>;
    if(submitflag == 0 )
        fm.CaseBackSubmitBt.disabled = true;
    else
        fm.CaseBackSubmitBt.disabled = false;
}

//����ʼ��
function initForm()
{
  try
  {
      initParam();
      initLJsPayGrid();
      ButtonSelected();
      queryLJsPayGrid();
      selectCaseBack();
    
  }
  catch(re)
  {
    alert("��LLCaseBackInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


//��ʼ��Ӧ���ܱ�
function initLJsPayGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";               //�п�
      iArray[0][2]=10;                   //�����ֵ
      iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="֪ͨ�����";         //����
      iArray[1][1]="100px";              //�п�
      iArray[1][2]=100;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ���˿ͻ�����";     //����
      iArray[2][1]="110px";              //�п�
      iArray[2][2]=100;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��Ӧ�ս��";         //����
      iArray[3][1]="120px";              //�п�
      iArray[3][2]=100;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[4]=new Array();
      iArray[4][0]="��������";           //����
      iArray[4][1]="80px";               //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����ת�ʳɹ���־";   //����
      iArray[5][1]="80px";               //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 

      
      LJsPayGrid = new MulLineEnter( "fm" , "LJsPayGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LJsPayGrid.mulLineCount      = 0;   
      LJsPayGrid.displayTitle      = 1;
      LJsPayGrid.locked            = 0;
      LJsPayGrid.canSel            = 1; 
      LJsPayGrid.hiddenPlus        = 1;
      LJsPayGrid.hiddenSubtraction = 1;
      LJsPayGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
