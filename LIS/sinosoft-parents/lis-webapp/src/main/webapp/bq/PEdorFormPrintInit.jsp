<% 
//�������ƣ�PEdorFormPrint.jsp
//�����ܣ���ȫ����������ӡ����̨
//�������ڣ�2006-09-25 09:10:00
//������  ��wangyan
//���¼�¼��  ������    ��������      ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.BqNameFun"%>

<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
	String comcode = globalInput.ComCode;
  String[] dateArr = BqNameFun.getNomalMonth(PubFun.getCurrentDate());
%>                         

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
  	document.all('StartDate').value = '<%=dateArr[0]%>';
		document.all('EndDate').value = '<%=dateArr[1]%>';
		//document.all('SaleChnl').value = '';
		document.all('BillType').value = '';
		document.all('BillTypeName').value = '';
		document.all('ManageCom').value = "<%=comcode%>";
		document.all('ChnlType').value = '';
		document.all('ChnlSel').value = '';
		document.all('RiskFlag').value = "NO";
  }
  catch(ex)
  {
    alert("��PEdorFormPrintInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
function initSelBox()
{  
  try{}
  catch(ex)
  {
    alert("��PEdorFormPrintInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    var NoticeType = fm.BillType.value;
    initNoticeGrid(NoticeType);   
    initManageCom(); //��ʼ��������������ȡ6λ   
  }
  catch(re)
  {
    alert("PEdorFormPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var NoticeGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// Ͷ������Ϣ�б�ĳ�ʼ��
function initNoticeGrid(tNoticeType)
{                               
    var iArray = new Array();
    try
    {
		  iArray[0]=new Array();
		  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		  iArray[0][1]="30px";            	//�п�
		  iArray[0][2]=10;            			//�����ֵ
		  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		  iArray[1]=new Array();
		  iArray[1][0]="��������";         	//����
		  iArray[1][1]="200px";            	//�п�
		  iArray[1][2]=100;            			//�����ֵ
		  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
  
		  switch(tNoticeType)
		  {
		    case "":
		    default:
 		        iArray[2]=new Array();
	  	   	  iArray[2][0]="�������";       		  //����
	          iArray[2][1]="120px";            	//�п�
	          iArray[2][2]=100;            			//�����ֵ
	          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
            iArray[3]=new Array();
	          iArray[3][0]="��������";          //����
	          iArray[3][1]="60px";            	//�п�
	          iArray[3][2]=100;            			//�����ֵ
	          iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	    
						iArray[4]=new Array();
	          iArray[4][0]="��ʼ����";          //����
	          iArray[4][1]="80px";            	//�п�
            iArray[4][2]=100;            			//�����ֵ
            iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������

            iArray[5]=new Array();
	          iArray[5][0]="��ֹ����";          //����
	          iArray[5][1]="80px";            	//�п�
	          iArray[5][2]=100;             		//�����ֵ
	          iArray[5][3]=0; 					        //�Ƿ���������,1��ʾ����0��ʾ������
	    
	          iArray[6]=new Array();
	          iArray[6][0]="��������";          //����
	          iArray[6][1]="60px";            	//�п�
            iArray[6][2]=100;            		  //�����ֵ
            iArray[6][3]=0; 					        //�Ƿ���������,1��ʾ����0��ʾ������

            iArray[7]=new Array();
	          iArray[7][0]="��ǰ״̬";          //����
	          iArray[7][1]="60px";            	//�п�
            iArray[7][2]=100;            		  //�����ֵ
            iArray[7][3]=0; 					        //�Ƿ���������,1��ʾ����0��ʾ������
            
            iArray[8]=new Array();
	          iArray[8][0]="���к�";          //����
	          iArray[8][1]="0px";            	//�п�
            iArray[8][2]=0;            		  //�����ֵ
            iArray[8][3]=0; 					        //�Ƿ���������,1��ʾ����0��ʾ������            

	        break;           
	  }
		
      NoticeGrid = new MulLineEnter( "fm" , "NoticeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      NoticeGrid.mulLineCount = 10;   
      NoticeGrid.displayTitle = 1;
      NoticeGrid.canSel = 1;
      NoticeGrid.hiddenPlus=1;
      NoticeGrid.hiddenSubtraction=1;
      NoticeGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>