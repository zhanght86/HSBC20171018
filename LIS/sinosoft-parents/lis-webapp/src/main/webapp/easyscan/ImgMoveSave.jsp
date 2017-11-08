<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2006-12-06, 2006-12-18
 * @direction: Ӱ��Ǩ�Ʊ���
 ******************************************************************************/
%>

<%@ page import="com.sinosoft.lis.easyscan.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    //��̨������
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();
    
    //�������ݱ���
    String sOldManageCom = request.getParameter("OldManageCom");
    String sNewManageCom = request.getParameter("NewManageCom");
    String sStartDate = request.getParameter("StartDate");
    String sEndDate = request.getParameter("EndDate");
    String sLoginManageCom = request.getParameter("LoginManageCom");
    String sLoginOperator = request.getParameter("LoginOperator");
    String sDocID = request.getParameter("DocID"); //�ش��Żᴫ���˲���

    ExeSQL tes = new ExeSQL();
    SSRS ss = new SSRS();
    
    //DocIDΪ��˵�������ش�
    if(sDocID == null || sDocID.equals("") || sDocID.toLowerCase().equals("null")){
        //���һ�����ݿ����Ƿ�����ִ�б���Ǩ����ͬ����ʱ����
        String sql = "select * from ES_DOCMOVE_TASK "
                  + " where startdate = '"+ sStartDate+"'"
                  + " and enddate = '"+ sEndDate+"'"
                  + " and tomanagecom = '"+ sNewManageCom+"'"
                  + " and managecom = '"+ sOldManageCom+"'"
                  + " and tasktype = '1'"
                  + " and endtime='00:00:00'";
        ss = tes.execSQL(sql);
        if(ss == null || ss.getMaxRow()< 1){
            //�ռ���������
            //GlobalInput
            GlobalInput tGlobalInput = new GlobalInput();
            tGlobalInput.ManageCom = sLoginManageCom;
            tGlobalInput.Operator = sLoginOperator;
            //TransferData
            TransferData tTransferData = new TransferData();
            tTransferData.setNameAndValue("OldManageCom", sOldManageCom);
            tTransferData.setNameAndValue("NewManageCom", sNewManageCom);
            tTransferData.setNameAndValue("StartDate", sStartDate);
            tTransferData.setNameAndValue("EndDate", sEndDate);
            //VData
            VData tVData = new VData();
            tVData.add(tGlobalInput);
            tVData.add(tTransferData);
            //��������
            tGlobalInput = null;
            tTransferData = null;
            
            //���ú�̨����
            DocMoveUI tDocMoveUI = new DocMoveUI();
            if (!tDocMoveUI.submitData(tVData, "OPERATION"))
            {
                tErrors.copyAllErrors(tDocMoveUI.getErrors());
            }
            tDocMoveUI = null;
            tVData = null;
            
            //ҳ�淴����Ϣ
            if (!tErrors.needDealError())
            {
                FlagStr = "Success";
                Content = "Ǩ����֤�ɹ�������Ӱ��Ǩ���̣߳�";
            }
            else
            {
                FlagStr = "Fail";
                Content = "Ǩ����֤ʧ�ܣ�ԭ���ǣ�" + tErrors.getFirstError();
            }
            tErrors = null;
        
        }else{
            FlagStr = "Fail";
            Content = "��̨�߳�����ִ��Ǩ��,��ȴ�......<br>�Ժ������־�鿴�в�ѯ�ôδ��ͽ��.";      
        }
    }
    //DocID��Ϊ����˵�����ش�
    else if(sDocID != null && !sDocID.equals("")){
    
        //���һ�����ݿ����Ƿ�����ִ�б�������Ǩ����ͬ����ʱ����
        String sql = "select * from ES_DOCMOVE_LOG "
                  + " where 1 = 1"
                  + " and DocID = '"+ sDocID +"'"
                  + " and flag = '2'"; //Flag 0:��ȷ(�����ش�����ȷ),1:����,2:�ش���
        ss = tes.execSQL(sql);
        if(ss == null || ss.getMaxRow()< 1){
            //�ռ���������
            //GlobalInput
            GlobalInput tGlobalInput = new GlobalInput();
            tGlobalInput.ManageCom = sLoginManageCom;
            tGlobalInput.Operator = sLoginOperator;
            //TransferData
            TransferData tTransferData = new TransferData();
            tTransferData.setNameAndValue("OldManageCom", sOldManageCom);
            tTransferData.setNameAndValue("NewManageCom", sNewManageCom);
            tTransferData.setNameAndValue("StartDate", sStartDate);
            tTransferData.setNameAndValue("EndDate", sEndDate);
            tTransferData.setNameAndValue("DocID", sDocID);
            //VData
            VData tVData = new VData();
            tVData.add(tGlobalInput);
            tVData.add(tTransferData);
            //��������
            tGlobalInput = null;
            tTransferData = null;
            
            //���ú�̨����
            DocMoveUI tDocMoveUI = new DocMoveUI();
            if (!tDocMoveUI.submitData(tVData, "RESEND"))
            {
                tErrors.copyAllErrors(tDocMoveUI.getErrors());
            }
            tDocMoveUI = null;
            tVData = null;
            
            //ҳ�淴����Ϣ
            if (!tErrors.needDealError())
            {
                FlagStr = "Success";
                Content = "Ǩ����֤�ɹ�������Ӱ��Ǩ���̣߳�";
            }
            else
            {
                FlagStr = "Fail";
                Content = "Ǩ����֤ʧ�ܣ�ԭ���ǣ�" + tErrors.getFirstError();
            }
            tErrors = null;
        
        }else{
            FlagStr = "Fail";
            Content = "��̨�߳�����ִ��Ǩ��,��ȴ�......<br>�Ժ������־�鿴�в�ѯ�ôδ��ͽ��.";      
        }   
    } 
    //����ҳ����Ϣ
    out.print("0" + "~" + FlagStr + "~" + Content);
%>
