<%
//�������ƣ�EdorNoticePrintInit.jsp
//�����ܣ�֪ͨ���ӡ
//�������ڣ�2005-08-03 15:39:06
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
	String strManageCom = globalInput.ComCode;
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('OtherNo').value = '';
	document.all('NoticeNo').value = '';
	document.all('NoticeType').value = '';
	document.all('ContNo').value = '';
	document.all('EdorAcceptNo').value = '';
	document.all('NoticeTypeName').value = '';
	document.all('ManageCom').value = comcode.substring(0,6);
		
  }
  catch(ex)
  {
    alert("��EdorNoticePrintInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
 
  }
  catch(ex)
  {
    alert("��EdorNoticePrintInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    var NoticeType = fm.NoticeType.value;
    initNoticeGrid(NoticeType);   
    initManageCom(); //��ʼ��������������ȡ6λ   
  }
  catch(re)
  {
    alert("EdorNoticePrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
	  iArray[1][0]="֪ͨ���";         		//����
	  iArray[1][1]="140px";            	//�п�
	  iArray[1][2]=100;            			//�����ֵ
	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  switch(tNoticeType)
	  {
	    case   "":
	    case "30":    //�ֺ�ҵ��������
	                  iArray[2]=new Array();
                	  iArray[2][0]="������";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                      iArray[3]=new Array();
	                  iArray[3][0]="�������";        //����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    
	    	          iArray[4]=new Array();
	                  iArray[4][0]="�ͻ�����";        //����
	                  iArray[4][1]="80px";            	//�п�
                  	  iArray[4][2]=100;            			//�����ֵ
                  	  iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[5]=new Array();
	                  iArray[5][0]="ҵ��Ա����";        //����
	                  iArray[5][1]="80px";            	//�п�
	                  iArray[5][2]=100;            		//�����ֵ
	                  iArray[5][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������
	    
	                  iArray[6]=new Array();
	                  iArray[6][0]="������Ч��";        //����
	                  iArray[6][1]="80px";            	//�п�
                 	  iArray[6][2]=100;            		//�����ֵ
                	  iArray[6][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[7]=new Array();
	                  iArray[7][0]="����״̬";        //����
	                  iArray[7][1]="0px";            	//�п�
                 	  iArray[7][2]=100;            		//�����ֵ
                	  iArray[7][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[8]=new Array();
	                  iArray[8][0]="�ֺ������";        //����
	                  iArray[8][1]="100px";            	//�п�
                 	  iArray[8][2]=100;            		//�����ֵ
                	  iArray[8][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������
	                  break;
	    case "34":    //����ֺ�ҵ��������
	                  iArray[2]=new Array();
                	  iArray[2][0]="���屣����";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                      iArray[3]=new Array();
	                  iArray[3][0]="�������";        //����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    
	    	          iArray[4]=new Array();
	                  iArray[4][0]="Ͷ����λ";        //����
	                  iArray[4][1]="80px";            	//�п�
                  	  iArray[4][2]=100;            			//�����ֵ
                  	  iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[5]=new Array();
	                  iArray[5][0]="ҵ��Ա����";        //����
	                  iArray[5][1]="80px";            	//�п�
	                  iArray[5][2]=100;            		//�����ֵ
	                  iArray[5][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������
	    
	                  iArray[6]=new Array();
	                  iArray[6][0]="������Ч��";        //����
	                  iArray[6][1]="80px";            	//�п�
                 	  iArray[6][2]=100;            		//�����ֵ
                	  iArray[6][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[7]=new Array();
	                  iArray[7][0]="����״̬";        //����
	                  iArray[7][1]="0px";            	//�п�
                 	  iArray[7][2]=100;            		//�����ֵ
                	  iArray[7][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[8]=new Array();
	                  iArray[8][0]="�ֺ������";        //����
	                  iArray[8][1]="100px";            	//�п�
                 	  iArray[8][2]=100;            		//�����ֵ
                	  iArray[8][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������
	                  break;
	    case "35":    //����ֺ�ҵ�������������嵥
	                  iArray[2]=new Array();
                	  iArray[2][0]="���屣����";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                      iArray[3]=new Array();
	                  iArray[3][0]="�������";        //����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    
	    	          iArray[4]=new Array();
	                  iArray[4][0]="Ͷ����λ";        //����
	                  iArray[4][1]="80px";            	//�п�
                  	  iArray[4][2]=100;            			//�����ֵ
                  	  iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[5]=new Array();
	                  iArray[5][0]="ҵ��Ա����";        //����
	                  iArray[5][1]="80px";            	//�п�
	                  iArray[5][2]=100;            		//�����ֵ
	                  iArray[5][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������
	    
	                  iArray[6]=new Array();
	                  iArray[6][0]="������Ч��";        //����
	                  iArray[6][1]="80px";            	//�п�
                 	  iArray[6][2]=100;            		//�����ֵ
                	  iArray[6][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[7]=new Array();
	                  iArray[7][0]="����״̬";        //����
	                  iArray[7][1]="0px";            	//�п�
                 	  iArray[7][2]=100;            		//�����ֵ
                	  iArray[7][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[8]=new Array();
	                  iArray[8][0]="�ֺ������";        //����
	                  iArray[8][1]="100px";            	//�п�
                 	  iArray[8][2]=100;            		//�����ֵ
                	  iArray[8][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������
	                  break;
	    case "BQ31":
	    case "BQ32":              
	    case "BQ27":
	    case "BQ28":  
	    
	                  iArray[2]=new Array();
                	  iArray[2][0]="��ȫ�����";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                      iArray[3]=new Array();
	                  iArray[3][0]="�������";        //����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������

	    	          iArray[4]=new Array();
	                  iArray[4][0]="�ͻ�����";        //����
	                  iArray[4][1]="80px";            	//�п�
                  	  iArray[4][2]=100;            			//�����ֵ
                  	  iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	                  break;                            //��Ϣ֪ͨ��   
	    case "BQ48":
	    case "BQ49":  
	    case "BQ51":  
	    case "BQ52":  
	                  iArray[2]=new Array();
                	  iArray[2][0]="��ȫ�����";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                      iArray[3]=new Array();
	                  iArray[3][0]="�������";        //����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	                  break;
        
    	case "41":
    	case "42":   
    	case "BQ21":
    	case "BQ22": 
    	case "BQ29":
    	case "BQ30": 
    	case "BQ34":
    	case "BQ38": 
    	case "BQ39": 
    	case "BQ10":
    	case "BQ17":
	                  iArray[2]=new Array();
                	  iArray[2][0]="������";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                      iArray[3]=new Array();
	                  iArray[3][0]="�������";        //����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������

	    	          iArray[4]=new Array();
	                  iArray[4][0]="�ͻ�����";        //����
	                  iArray[4][1]="80px";            	//�п�
                  	  iArray[4][2]=100;            			//�����ֵ
                  	  iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	    
                      iArray[5]=new Array();
	                  iArray[5][0]="ҵ��Ա����";        //����
	                  iArray[5][1]="80px";            	//�п�
	                  iArray[5][2]=100;            		//�����ֵ
	                  iArray[5][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[6]=new Array();
	                  iArray[6][0]="������Ч��";        //����
	                  iArray[6][1]="80px";            	//�п�
                 	  iArray[6][2]=100;            		//�����ֵ
                	  iArray[6][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[7]=new Array();
	                  iArray[7][0]="����״̬";        //����
	                  iArray[7][1]="0px";            	//�п�
                 	  iArray[7][2]=100;            		//�����ֵ
                	  iArray[7][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������
	    
	                  break;
    	case "BQ18":
	                  iArray[2]=new Array();
                	  iArray[2][0]="������";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                      iArray[3]=new Array();
	                  iArray[3][0]="�������";        //����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������

	    	          iArray[4]=new Array();
	                  iArray[4][0]="�ͻ�����";        //����
	                  iArray[4][1]="80px";            	//�п�
                  	  iArray[4][2]=100;            			//�����ֵ
                  	  iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	    
                      iArray[5]=new Array();
	                  iArray[5][0]="ҵ��Ա����";        //����
	                  iArray[5][1]="80px";            	//�п�
	                  iArray[5][2]=100;            		//�����ֵ
	                  iArray[5][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[6]=new Array();
	                  iArray[6][0]="������Ч��";        //����
	                  iArray[6][1]="80px";            	//�п�
                 	  iArray[6][2]=100;            		//�����ֵ
                	  iArray[6][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

                      iArray[7]=new Array();
	                  iArray[7][0]="����״̬";        //����
	                  iArray[7][1]="0px";            	//�п�
                 	  iArray[7][2]=100;            		//�����ֵ
                	  iArray[7][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������
	                  break;       
	    
    	case "BQ00":
	                  iArray[2]=new Array();
                	  iArray[2][0]="������";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                    iArray[3]=new Array();
	                  iArray[3][0]="�������";        //����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������


                    iArray[4]=new Array();
	                  iArray[4][0]="��������";        //����
	                  iArray[4][1]="120px";            	//�п�
	                  iArray[4][2]=100;            			//�����ֵ
	                  iArray[4][3]=0; 		
                    break;
    	case "BQ01":
	                  iArray[2]=new Array();
                	  iArray[2][0]="������";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                    iArray[3]=new Array();
	                  iArray[3][0]="�������";        //����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������


                    iArray[4]=new Array();
	                  iArray[4][0]="��������";        //����
	                  iArray[4][1]="120px";            	//�п�
	                  iArray[4][2]=100;            			//�����ֵ
	                  iArray[4][3]=0; 		
	                  
                    break;
                    
        case "BQ71":            
		case "BQ72":
	                  iArray[2]=new Array();
                	  iArray[2][0]="������";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	                  
	                  iArray[3]=new Array();
                	  iArray[3][0]="��ȫ�����";       		//����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                    iArray[4]=new Array();
	                  iArray[4][0]="�������";        //����
	                  iArray[4][1]="120px";            	//�п�
	                  iArray[4][2]=100;            			//�����ֵ
	                  iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������


                    iArray[5]=new Array();
	                  iArray[5][0]="֪ͨ����������";        //����
	                  iArray[5][1]="120px";            	//�п�
	                  iArray[5][2]=100;            			//�����ֵ
	                  iArray[5][3]=0; 		
	                  
                    break;    
	      	                         
	    default:      
	                  iArray[2]=new Array();
                	  iArray[2][0]="������";       		//����
	                  iArray[2][1]="120px";            	//�п�
	                  iArray[2][2]=100;            			//�����ֵ
	                  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

                      iArray[3]=new Array();
	                  iArray[3][0]="�������";        //����
	                  iArray[3][1]="120px";            	//�п�
	                  iArray[3][2]=100;            			//�����ֵ
	                  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������

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
