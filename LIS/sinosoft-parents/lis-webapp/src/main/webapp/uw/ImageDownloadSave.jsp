<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2006-2-15
  Time: 15:53:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.lis.tb.*" %>
<%@ page import="com.sinosoft.lis.cbcheck.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>

<%
    String prtNo = request.getParameter("PrtNo");
    String subtype = request.getParameter("subtype");
    String scanNo = request.getParameter("ScanNo");
    ExeSQL tExeSQL = new ExeSQL();
    String aSQL = "";
    if (subtype != null && !subtype.equals("")) {
        if (scanNo != null && !scanNo.equals("")) {


            aSQL = " select tempa.bussno,"
                    + " '新契约',"
                    + " (select b.SubTypeName"
                    + " from es_doc_def b"
                    + " where b.busstype = 'TB'"
                    + " and b.subtype = tempa.subtype),"
                    + " tempa.docid,"
                    + " (select c.scanno"
                    + " from es_doc_main c"
                    + " where c.busstype = 'TB'"
                    + " and c.subtype = tempa.subtype"
                    + " and c.docid = tempa.docid), "
                    + " tempb.pagecode,"
                    + " concat(concat(trim(tempb.picpath) , trim(tempb.pagename)) ,"
                    + " trim(tempb.pagesuffix)),"
                    + " (select concat(concat(concat(concat('http://',substr(trim(tempb.picpathftp),1,Instr(tempb.picpathftp, '/'))),trim(tempb.picpath)) , trim(tempb.pagename)) ,trim(tempb.pagesuffix)) from dual)"
                    + " from (select (select bussno"
                    + " from es_doc_relation b"
                    + " where b.docid = a.docid"
                    + " and b.bussnotype = a.bussnotype) as bussno,"
                    + " (select subtype"
                    + " from es_doc_relation b"
                    + " where b.docid = a.docid"
                    + " and b.bussnotype = a.bussnotype) as subtype,"
                    + " a.docid"
                    + " from (select docid,"
                    + " (select max(bussnotype)"
                    + " from es_doc_relation"
                    + " where docid = c.docid) as bussnotype"
                    + " from (select distinct docid"
                    + " from es_doc_relation"
                    + " where 1 = 1"
                    + " and subtype = '" + subtype + "'"
                    + " and bussno = '" + prtNo + "'"
                    + " and busstype = 'TB'"
                    + " and exists"
                    + " (select 'x'"
                    + " from es_doc_main"
                    + " where docid = es_doc_relation.docid"
                    + " and scanno like '%%" + scanNo + "%%')) c) a) tempa,"
                    + " es_doc_pages tempb"
                    + " where tempb.docid = tempa.docid"
                    + " order by docid,tempb.pagecode";

        } else {
            aSQL = " select tempa.bussno,"
                    + " '新契约',"
                    + " (select b.SubTypeName"
                    + " from es_doc_def b"
                    + " where b.busstype = 'TB'"
                    + " and b.subtype = tempa.subtype),"
                    + " tempa.docid,"
                    + " (select c.scanno"
                    + " from es_doc_main c"
                    + " where c.busstype = 'TB'"
                    + " and c.subtype = tempa.subtype"
                    + " and c.docid = tempa.docid), "
                    + " tempb.pagecode,"
                    + " concat(concat(trim(tempb.picpath) , trim(tempb.pagename)) ,"
                    + " trim(tempb.pagesuffix)),"
                    + " (select concat(concat(concat(concat('http://',substr(trim(tempb.picpathftp),1,Instr(tempb.picpathftp, '/'))),trim(tempb.picpath)) , trim(tempb.pagename)) ,trim(tempb.pagesuffix)) from dual)"
                    + " from (select (select bussno"
                    + " from es_doc_relation b"
                    + " where b.docid = a.docid"
                    + " and b.bussnotype = a.bussnotype) as bussno,"
                    + " (select subtype"
                    + " from es_doc_relation b"
                    + " where b.docid = a.docid"
                    + " and b.bussnotype = a.bussnotype) as subtype,"
                    + " a.docid"
                    + " from (select docid,"
                    + " (select max(bussnotype)"
                    + " from es_doc_relation"
                    + " where docid = c.docid) as bussnotype"
                    + " from (select distinct docid"
                    + " from es_doc_relation"
                    + " where 1 = 1"
                    + " and subtype = '" + subtype + "'"
                    + " and bussno = '" + prtNo + "'"
                    + " and busstype = 'TB') c) a) tempa,"
                    + " es_doc_pages tempb"
                    + " where tempb.docid = tempa.docid"
                    + " order by docid,tempb.pagecode";


        }
    } else {
        if (scanNo != null && !scanNo.equals("")) {
            aSQL = " select tempa.bussno,"
                    + " '新契约',"
                    + " (select b.SubTypeName"
                    + " from es_doc_def b"
                    + " where b.busstype = 'TB'"
                    + " and b.subtype = tempa.subtype),"
                    + " tempa.docid,"
                    + " (select c.scanno"
                    + " from es_doc_main c"
                    + " where c.busstype = 'TB'"
                    + " and c.subtype = tempa.subtype"
                    + " and c.docid = tempa.docid), "
                    + " tempb.pagecode,"
                    + " concat(concat(trim(tempb.picpath) , trim(tempb.pagename)) ,"
                    + " trim(tempb.pagesuffix)),"
                    + " (select concat(concat(concat(concat('http://',substr(trim(tempb.picpathftp),1,Instr(tempb.picpathftp, '/'))),trim(tempb.picpath)) , trim(tempb.pagename)) ,trim(tempb.pagesuffix)) from dual)"
                    + " from (select (select bussno"
                    + " from es_doc_relation b"
                    + " where b.docid = a.docid"
                    + " and b.bussnotype = a.bussnotype) as bussno,"
                    + " (select subtype"
                    + " from es_doc_relation b"
                    + " where b.docid = a.docid"
                    + " and b.bussnotype = a.bussnotype) as subtype,"
                    + " a.docid"
                    + " from (select docid,"
                    + " (select max(bussnotype)"
                    + " from es_doc_relation"
                    + " where docid = c.docid) as bussnotype"
                    + " from (select distinct docid"
                    + " from es_doc_relation"
                    + " where 1 = 1"
                    + " and bussno = '" + prtNo + "'"
                    + " and busstype = 'TB'"
                    + " and exists"
                    + " (select 'x'"
                    + " from es_doc_main"
                    + " where docid = es_doc_relation.docid"
                    + " and scanno like '%%" + scanNo + "%%')) c) a) tempa,"
                    + " es_doc_pages tempb"
                    + " where tempb.docid = tempa.docid"
                    + " order by docid,tempb.pagecode";


        } else {
            aSQL = " select tempa.bussno,"
                    + " '新契约',"
                    + " (select b.SubTypeName"
                    + " from es_doc_def b"
                    + " where b.busstype = 'TB'"
                    + " and b.subtype = tempa.subtype),"
                    + " tempa.docid,"
                    + " (select c.scanno"
                    + " from es_doc_main c"
                    + " where c.busstype = 'TB'"
                    + " and c.subtype = tempa.subtype"
                    + " and c.docid = tempa.docid), "
                    + " tempb.pagecode,"
                    + " concat(concat(trim(tempb.picpath) , trim(tempb.pagename) ,"
                    + " trim(tempb.pagesuffix))),"
                    + " (select concat(concat(concat(concat('http://',substr(trim(tempb.picpathftp),1,Instr(tempb.picpathftp, '/'))),trim(tempb.picpath)) , trim(tempb.pagename)) ,trim(tempb.pagesuffix)) from dual)"
                    + " from (select (select bussno"
                    + " from es_doc_relation b"
                    + " where b.docid = a.docid"
                    + " and b.bussnotype = a.bussnotype) as bussno,"
                    + " (select subtype"
                    + " from es_doc_relation b"
                    + " where b.docid = a.docid"
                    + " and b.bussnotype = a.bussnotype) as subtype,"
                    + " a.docid"
                    + " from (select docid,"
                    + " (select max(bussnotype)"
                    + " from es_doc_relation"
                    + " where docid = c.docid) as bussnotype"
                    + " from (select distinct docid"
                    + " from es_doc_relation"
                    + " where 1 = 1"
                    + " and bussno = '" + prtNo + "'"
                    + " and busstype = 'TB') c) a) tempa,"
                    + " es_doc_pages tempb"
                    + " where tempb.docid = tempa.docid"
                    + " order by docid,tempb.pagecode";


        }

    }
    SSRS tSSRS = tExeSQL.execSQL(aSQL);


%>
<html>
<head>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
</head>

<body>
<table class="common" border='0' width="100%">
    <tr class="common">
        <td class="title">
            单证号
        </td>
        <td class="title">
            影像类别
        </td>
        <td class="title">
            影像名称
        </td>
        <td class="title">
            单证编号
        </td>
        <td class="title">
            打印批次号
        </td>
        <td class="title">
            影像页码
        </td>
        <td class="title">
            &nbsp;
        </td>
    </tr>

    <%
        for (int i = 0; i < tSSRS.getMaxRow(); i++) {
    %>
    <tr class="common">
        <td class="common" nowrap="nowrap">
            <%=tSSRS.GetText(i + 1, 1)%>
        </td>
        <td class="common" nowrap="nowrap">
            <%=tSSRS.GetText(i + 1, 2)%>
        </td>
        <td class="common" nowrap="nowrap">
            <%=tSSRS.GetText(i + 1, 3)%>
        </td>
        <td class="common" nowrap="nowrap">
            <%=tSSRS.GetText(i + 1, 4)%>
        </td>
        <td class="common" nowrap="nowrap">
            <%=tSSRS.GetText(i + 1, 5)%>
        </td>
        <td class="common" nowrap="nowrap">
            第<%=tSSRS.GetText(i + 1, 6)%>页
        </td>
        <td class="common" nowrap="nowrap">
            <a href="<%=tSSRS.GetText(i+1,8)%>" target="_blank">下载</a>
        </td>
    </tr>
    <%
        }
    %>
</table>

</body>

</html>
