package com.sinosoft.lis.fininterface.utility;
import org.apache.log4j.Logger;



/**
 * <Title>:处理财务接口中每批生成数据的流水号</Title>
 * <Company>:SINSOFT</Company>
 * @creatTime 2007-2-9
 * @author lijs
 */
import java.sql.Connection;
import java.sql.SQLWarning;

import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;

public class FinCreateSerialNo {
private static Logger logger = Logger.getLogger(FinCreateSerialNo.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

    }

    /**在处理批次前自动生成系列号
     * 参数:size 长度
     * @param strNotype 表示流水号码的类型标识
     * @param size 表示流水号码长度
     *
     * @return String[] 流水号码数组
     */
    public static synchronized String[] getSerialNo(String strNotype, int size,
            int length) throws Exception {

        int num = 0; //标记初始的数字
        String tSBql = "select maxno from ldmaxno where notype = '?notype?' and nolimit = 'SN' for update";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSBql);
        sqlbv.put("notype", strNotype.trim());
        Connection conn = DBConnPool.getConnection();
        try {
            conn.setAutoCommit(false);
            ExeSQL exeSQL = new ExeSQL(conn);
            String rsData = null;
            rsData = exeSQL.getOneValue(sqlbv);
            /***********************************************************************/
            /** 创建新的序列号 **/
            //如果没有创建的话则直接创建并且添加size个序号
            if ((rsData == null) || rsData.equals("")) {
                tSBql = "insert into ldmaxno(notype, nolimit, maxno) values('?notype?','SN',?size?)";
                SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                sqlbv1.sql(tSBql);
                sqlbv1.put("notype", strNotype.trim());
                sqlbv1.put("size", size);
                exeSQL = new ExeSQL(conn);
                if (!exeSQL.execUpdateSQL(sqlbv1)) {
                    logger.debug("CreateMaxNo 插入失败，请重试!");
                    conn.rollback();
                    conn.close();
                    Exception e = new Exception("CreateMaxNo 插入失败");
                    throw e;
                } else {
                    num = 1;
                }
            } else {
                //如果已经有了,只要直接添加就可以
                num = Integer.parseInt(rsData);
                tSBql = "update ldmaxno set maxno = maxno + ?size? where notype = '?notype?' and nolimit = 'SN'";
                SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
                sqlbv2.sql(tSBql);
                sqlbv2.put("size", size);
                sqlbv2.put("notype", strNotype.trim());
                exeSQL = new ExeSQL(conn);
                if (!exeSQL.execUpdateSQL(sqlbv2)) {
                    logger.debug("CreateMaxNo 更新失败，请重试!");
                    conn.rollback();
                    conn.close();
                    Exception e = new Exception("CreateMaxNo 更新失败");
                    throw e;
                } else {
                    num = Integer.parseInt(rsData) + 1; //表示已经存在 并更新成功 则自动从下一个序列号开始增加
                }
            }
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            try {
                conn.rollback();
                conn.close();
            } catch (Exception ex1) {
                logger.debug("流水号的错误!" + ex1.getMessage());
                throw ex1;
            }
        }
        /****************************************************/
        String[] strArr = new String[size]; //返回数组的长度
        for (int i = 0; i < size; i++) {
            strArr[i] = getStringValue(num + i+2, length);
        }
        logger.debug("流水号生成完毕!");
        return strArr;
    }

    /**
     * 把相关的数字转换成相应长度的流水号
     * @param n 需要转换的数字,size则是转换后的长度
     * @return String
     */
    public static String getStringValue(int n, int size) {
        String str = Integer.toString(n);
        int len = str.length();
        String temp = "000000000000000000000000000000000000000000000";
        str = temp.substring(0, size - len) + str; //未满的用0补充
        return str;
    }

}
