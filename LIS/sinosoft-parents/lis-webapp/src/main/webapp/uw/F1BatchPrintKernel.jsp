<%@ page import="java.io.*"%><%@ 
page import="com.sinosoft.lis.f1print.AccessVtsFile"%><%@ 
page import="com.sinosoft.utility.*"%><%
            loggerDebug("F1BatchPrintKernel","=============================F1BatchPrintKernel.jsp=====================================");
            try {
                loggerDebug("F1BatchPrintKernel","==========idx===============" + request.getParameter("idx"));
                ByteArrayOutputStream dataStream;
                int idx = Integer.parseInt(request.getParameter("idx"));
                loggerDebug("F1BatchPrintKernel","==============idx===========" + idx);
                XmlExport[] tXmlExport = (XmlExport[]) session.getValue("xel");
                loggerDebug("F1BatchPrintKernel","===========tXmlExport==============" + tXmlExport.length);
                InputStream ins = null;
                if (tXmlExport != null && tXmlExport[idx] != null) {
                    ins = (InputStream) tXmlExport[idx].getInputStream();
                }

                loggerDebug("F1BatchPrintKernel","=============111============" + ins);
                if (ins != null) {
                    loggerDebug("F1BatchPrintKernel","=========================" + ins);
                    String strTemplatePath =
                            application.getRealPath("f1print/NCLtemplate/") + "/";
                    F1PrintParser fp = null;

                    //ins = (InputStream)session.getValue("PrintStream");
                    //session.removeAttribute("PrintStream");

                    if (ins == null) {
                        XmlExport xmlExport = new XmlExport();

                        xmlExport.createDocument("new.vts", "printer");

                        fp =
                                new F1PrintParser(xmlExport.getInputStream(), strTemplatePath);
                    } else {
                        fp = new F1PrintParser(ins, strTemplatePath);
                    }

                    dataStream = new ByteArrayOutputStream();
                    // Output VTS file to a buffer
                    if (!fp.output(dataStream)) {
                        loggerDebug("F1BatchPrintKernel","F1PrintKernel.jsp : fail to parse print data");
                    }
                } else {

                    String[] strVFPath = (String[]) session.getValue("RealPath");
                    String strVFPathName = strVFPath[idx];
                    dataStream = new ByteArrayOutputStream();
                    // Load VTS file to buffer
                    //strVFPathName="e:\\ui\\f1print\\NCLtemplate\\new.vts";
                    AccessVtsFile.loadToBuffer(dataStream, strVFPathName);
                    loggerDebug("F1BatchPrintKernel","==> Read VTS file from disk ");
                // Put a stream from buffer which contains VTS file to client
                }
                byte[] bArr = dataStream.toByteArray();
                OutputStream ous = response.getOutputStream();
                ous.write(bArr);
                ous.flush();

                ous.close();
            } catch (java.net.MalformedURLException urlEx) {
                urlEx.printStackTrace();
            } catch (java.io.IOException ioEx) {
                ioEx.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
%>
