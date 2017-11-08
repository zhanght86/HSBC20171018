<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2006-10-10, 2006-10-13, 2006-10-18, 2006-11-22
 * @direction: ��ȫ�����˱������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

 <%@page import="com.sinosoft.service.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    BusinessDelegate tBusinessDelegate;
    //�������ݱ���
    String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
    String sEdorNo = request.getParameter("EdorNo");
    String sEdorType = request.getParameter("EdorType");
    String sContNo = request.getParameter("ContNo");
    String sPolNo = request.getParameter("PolNo");
    String sInsuredNo = request.getParameter("InsuredNo");
    String sAppObj = request.getParameter("AppObj");

    //����������Ϣ
    String arrGridNo[] = request.getParameterValues("NewBnfGridNo");
    String arrInsuredNo[] = request.getParameterValues("NewBnfGrid1");
    String arrInsuredName[] = request.getParameterValues("NewBnfGrid2");
    String arrBnfType[] = request.getParameterValues("NewBnfGrid3");
    String arrName[] = request.getParameterValues("NewBnfGrid4");
    String arrBirthday[] = request.getParameterValues("NewBnfGrid13");
    String arrSex[] = request.getParameterValues("NewBnfGrid12");
    String arrIDType[] = request.getParameterValues("NewBnfGrid6");
    String arrIDNo[] = request.getParameterValues("NewBnfGrid7");
    String arrRelationToInsured[] = request.getParameterValues("NewBnfGrid8");
    String arrBnfGrade[] = request.getParameterValues("NewBnfGrid9");
    String arrBnfLot[] = request.getParameterValues("NewBnfGrid10");
    String arrPostalAddress[] = request.getParameterValues("NewBnfGrid14");
    String arrZipCode[] = request.getParameterValues("NewBnfGrid15");
    String arrRemark[] = request.getParameterValues("NewBnfGrid16");

    //��̨������
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

    //�ռ���������
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    //LPEdorItemSchema
    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    tLPEdorItemSchema.setEdorAcceptNo(sEdorAcceptNo);
    tLPEdorItemSchema.setEdorNo(sEdorNo);
    tLPEdorItemSchema.setEdorType(sEdorType);
    tLPEdorItemSchema.setContNo(sContNo);
    tLPEdorItemSchema.setPolNo(sPolNo);
    tLPEdorItemSchema.setInsuredNo(sInsuredNo);

    //LPBnfSet
    LPBnfSet tLPBnfSet = new LPBnfSet();
    if (arrGridNo != null)
    {
        for (int i = 0; i < arrGridNo.length; i++)
        {
            LPBnfSchema tLPBnfSchema = new LPBnfSchema();
            tLPBnfSchema.setEdorNo(sEdorNo);
            tLPBnfSchema.setEdorType(sEdorType);
            tLPBnfSchema.setContNo(sContNo);
            tLPBnfSchema.setPolNo(sPolNo);
            tLPBnfSchema.setInsuredNo(arrInsuredNo[i]);
            tLPBnfSchema.setBnfType(arrBnfType[i]);
            tLPBnfSchema.setName(arrName[i]);
            tLPBnfSchema.setSex(arrSex[i]);
            tLPBnfSchema.setBirthday(arrBirthday[i]);
            tLPBnfSchema.setIDType(arrIDType[i]);
            tLPBnfSchema.setIDNo(arrIDNo[i]);
            tLPBnfSchema.setRelationToInsured(arrRelationToInsured[i]);
            tLPBnfSchema.setBnfGrade(arrBnfGrade[i]);           
            tLPBnfSchema.setPostalAddress(arrPostalAddress[i]);
            tLPBnfSchema.setZipCode(arrZipCode[i]);
            tLPBnfSchema.setRemark(arrRemark[i]);
            //����ݶ�
            double dBnfLot = 0.00;
            try
            {
                dBnfLot = Double.parseDouble(arrBnfLot[i]);
                tLPBnfSchema.setBnfLot(dBnfLot);
            }
            catch (Exception ex)
            {
                tErrors.addOneError("�� " + (i + 1) + " ������ݶ���������ת���쳣��");
                break;
            }
            //���������, �� PolNo ����, ���ٰ� InsuredNo ϸ��
            tLPBnfSchema.setBnfNo(i + 1);
            tLPBnfSet.add(tLPBnfSchema);
            tLPBnfSchema = null;
        }
    }

    //VData
    VData tVData = new VData();
    tVData.add(tGlobalInput);
    tVData.add(tLPEdorItemSchema);
    tVData.add(tLPBnfSet);
 

    //���ú�̨����
    if (!tErrors.needDealError())
    {
        if (sAppObj != null && sAppObj.trim().equalsIgnoreCase("G"))
        {
            //LPGrpEdorItemSchema
            LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
            tLPGrpEdorItemSchema.setEdorAcceptNo(sEdorAcceptNo);
            tLPGrpEdorItemSchema.setEdorNo(sEdorNo);
            tLPGrpEdorItemSchema.setEdorType(sEdorType);
            tVData.add(tLPGrpEdorItemSchema);
           
            //GEdorDetailUI
            String busiName="GEdorDetailUI";
            tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            if(!tBusinessDelegate.submitData(tVData,"OPERATION",busiName))
            {    
               tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
            }
           
        }
        else
        {
            String busiName="EdorDetailUI";
            tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            if(!tBusinessDelegate.submitData(tVData,"OPERATION",busiName))
            {    
               tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
            }
        }
    }
 

    //ҳ�淴����Ϣ
    if (!tErrors.needDealError())
    {
        FlagStr = "Success";
        Content = "�����ɹ���";
    }
    else
    {
        FlagStr = "Fail";
        Content = "����ʧ�ܣ�ԭ���ǣ�" + tErrors.getFirstError();
    }
 
%>


<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>
