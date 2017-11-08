package com.sinosoft.report.f1report;
import org.apache.log4j.Logger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.util.Iterator;

import javax.swing.JPanel;

import com.f1j.ss.GRObject;
import com.f1j.swing.JBook;
import com.f1j.util.F1Exception;

public class AppletControl2 extends AppletControl1 {
private static Logger logger = Logger.getLogger(AppletControl2.class);
	private static final long serialVersionUID = 1L;
	java.util.List tJBookList = new java.util.ArrayList();
    int tPrintCount = 0;

    //Initialize the applet
    public void init() {
        //JBook book1 = new JBook();


        try {
            if (this.getParameter("PrintCount", "") != null && !this.getParameter("PrintCount",
                    "").
                    equals("")) {
                tPrintCount =
                        Integer.parseInt(this.getParameter("PrintCount", ""));
            }

            JPanel jp = new JPanel();
            jp.setLayout(new FlowLayout());
            if (tPrintCount > 0) {
                for (int i = 0; i < tPrintCount; i++) {
                    var0 = this.getParameter("ReportURL" + i, "");
                    var1 = this.getParameter("IsNeedExcel", "");
                    var2 = this.getParameter("DownLoadURL" + i, "");

                    logger.debug("111var0=" + var0);
                    logger.debug("111var1=" + var1);
                    logger.debug("111var2=" + var2);


                    URL url = new URL(getCodeBase(), var0);
                    var0 = url.toString();

                    url = new URL(getCodeBase(), var2);
                    var2 = url.toString();

                    logger.debug(var0 + var2);

                    book = new JBook();
                    book.readURL(var0);


                    book.setAllowDesigner(false);
                    book.setAllowEditHeaders(false);
                    book.setShowEditBar(false);
                    book.setAllowInCellEditing(false);
                    book.setAllowDelete(false);
                    book.setAllowSelections(false);
                    book.setAllowResize(false);
                    book.setPrintGridLines(false);

//                    if (i==1){
//                        book1 = book;
//                    }
                    //Added by AppleWood,必须把位置设置到可以显示条形码的地方，不然打印出错。
                    //目前发现，图片对象只有在图片在被查看以后才可打印。
                    book.setTopRow(0);
                    book.setLeftCol(0);
                    book.setViewScale(50);
                    
                    class myFocusListener implements FocusListener{
                    	JBook b;
	                    	myFocusListener(JBook b){
	                    		this.b=b;
	                    	}
	                    	public void focusGained(FocusEvent e) {
	                    		try {
									b.setViewScale(100);
								} catch (F1Exception ex) {
								}
							}
	
							public void focusLost(FocusEvent e) {
//								try {
//									book.setViewScale(50);
//								} catch (F1Exception e1) {
//								}									
							}
	                    }
                    book.addFocusListener(new myFocusListener(book));

                    GRObject gr = null;
                    book.setSelection(gr);
                    tJBookList.add(book);
                    jp.add(book);


                }
//                    getContentPane().add(new JLabel("1231311231321313213213213213"));
//                    getContentPane().add(new JLabel("sdafasdfsadfsdfsdfsdfsfsfdsaddf"));

            }
            getContentPane().setLayout(new BorderLayout(0, 0));
            getContentPane().add(BorderLayout.CENTER, jp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception {
        jButton2.setBackground(Color.white);
        jButton2.setFont(new java.awt.Font("新宋体", Font.PLAIN, 12));
        jButton2.setAlignmentX((float) 1.0);
        jButton2.setText("   设置   ");
        jButton2.addActionListener(new Applet2_jButton2_actionAdapter(this));
        jLabel1.setText("      ");
        jLabel2.setText("      ");
        jButton3.setBackground(Color.white);
        jButton3.setFont(new java.awt.Font("新宋体", Font.PLAIN, 12));
        jButton3.setText("   打印   ");
        jButton3.addActionListener(new Applet2_jButton3_actionAdapter(this));
        jButton4.setBackground(Color.white);
        jButton4.setFont(new java.awt.Font("新宋体", Font.PLAIN, 12));
        jButton4.setText("   关闭   ");
        jButton4.addActionListener(new Applet2_jButton4_actionAdapter(this));
        jButton5.setBackground(Color.white);
        jButton5.setFont(new java.awt.Font("新宋体", Font.PLAIN, 12));
        jButton5.setText("   下载   ");
        jButton5.addActionListener(new Applet2_jButton5_actionAdapter(this));
        jLabel3.setText("      ");
        jLabel4.setText("      ");
        jToolBar1.setBackground(Color.white);
        jToolBar1.setForeground(Color.white);
        this.setFont(new java.awt.Font("新宋体", Font.PLAIN, 12));
        jToolBar1.add(jButton3);
        jToolBar1.add(jLabel3);
//        jToolBar1.add(jButton5);
        jToolBar1.add(jLabel2);
        jToolBar1.add(jButton2);
        jToolBar1.add(jLabel1);
        jToolBar1.add(jButton4);
        this.getContentPane().
                add(jToolBar1, java.awt.BorderLayout.NORTH);

    }
    
    public boolean print() {
        try {
            //book.filePrint(true);
            if (tJBookList != null) {
                JBook book;
                for (Iterator it = tJBookList.iterator(); it.hasNext(); book.filePrint(true)) {
                    book = (JBook) it.next();
                }

            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
