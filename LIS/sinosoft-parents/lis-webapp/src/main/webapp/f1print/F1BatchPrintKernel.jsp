<%@ page import="java.io.*"%><%@ 
page import="com.sinosoft.lis.f1print.AccessVtsFile"%><%@ 
page import="com.sinosoft.utility.*"%><%
            try {
                ByteArrayOutputStream dataStream;
                int idx = Integer.parseInt(request.getParameter("idx"));
                XmlExport[] tXmlExport = (XmlExport[]) session.getValue("xel");
                loggerDebug("F1BatchPrintKernel","===========tXmlExport==============" + tXmlExport.length);
                InputStream ins = null;
                if (tXmlExport != null && tXmlExport[idx] != null) {
                    ins = (InputStream) tXmlExport[idx].getInputStream();
                }

                if (ins != null) {
                    String strTemplatePath =
                            application.getRealPath("f1print/MStemplate/") + "/";
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
                InputStream insVTS = new ByteArrayInputStream(bArr);
                OutputStream ous = response.getOutputStream();
                ous.write(bArr);
                ous.flush();

                ous.close();
                if(idx==0){
                	insVTS.reset();
                	session.putValue("F1PrintData", insVTS);
                }
            } catch (java.net.MalformedURLException urlEx) {
                urlEx.printStackTrace();
            } catch (java.io.IOException ioEx) {
                ioEx.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
%>
