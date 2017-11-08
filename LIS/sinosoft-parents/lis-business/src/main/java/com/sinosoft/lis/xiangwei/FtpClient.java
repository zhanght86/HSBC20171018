package com.sinosoft.lis.xiangwei;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//import com.sinosoft.lis.midplat.util.PostpositionConfig;
import com.sinosoft.lis.xiangwei.PrintLog;
public class FtpClient
{
private static Logger logger = Logger.getLogger(FtpClient.class);

	private Socket clientSocket;
	private String ipAddress;
	private int port;
	private int timeout;

	public FtpClient()
	{
		try
		{
			loadConfig();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	/* 根据配置文件，读取远端ＦＴＰ服务器的配置信息 */

	public boolean loadConfig() throws UnknownHostException
	{
		FtpProperty fp = new FtpProperty();
		timeout = 60;
//		try
//		{
//			ipAddress = PostpositionConfig.getString("PSBCDataSerIP");
//			port = Integer.parseInt(PostpositionConfig.getString("PSBCDataSerPort"));
//		}
//		catch (Exception ex)
//		{
//			logger.debug("***获取邮储端文件服务器配置失败");
//			ex.printStackTrace();
//		}
		
//		ipAddress = fp.pps.getProperty("ncpai-ip");
//		port = Integer.parseInt(fp.pps.getProperty("ncpai-port"));
//		timeout = Integer.parseInt(fp.pps.getProperty("ncpai-timeout"));
//		ipAddress = "10.0.8.204";
//		port = 7001;
		ipAddress = "172.31.251.3";  //product
//		ipAddress = "110.31.251.3";  //test
		port = 7999;
//		数据文件端口 7999
//		timeout = 60;

		return true;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout()
	{
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	public boolean ncpaiFileInterface(FileTransCmd ftc)
	{

		return true;
	}

	/* 建立连接 */
	public boolean createSocketClient(String ip, int port)
	{

		PrintLog.printLog("start to connect to the host...");
		try
		{
			clientSocket = new Socket(ip, port);
			clientSocket.setKeepAlive(true);
			clientSocket.setTcpNoDelay(true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		PrintLog.printLog("connect to the host successed!");
		return true;

	}

	public boolean closeSocketClient(Socket clientSocket)
	{
		PrintLog.printLog("close the connect from the host...");
		try
		{
			clientSocket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		PrintLog.printLog("close the connect successed!");

		return true;

	}

	public boolean isConnected()
	{
		if (clientSocket.isConnected())
		{
			PrintLog.printLog("the socket is connecting ");
		}
		else
		{
			PrintLog.printLog("the socket has been shutdown");
			if (createSocketClient(ipAddress, port))
			{
				PrintLog.printLog(" reconnect sucess!!!");
			}
			else
			{
				return false;
			}
		}
		return true;
	}
	/* 发送数据报文到SERVER */
	public boolean sendDataToSocket(Socket clientSocket, byte[] sendData,
	        int timeout) throws IOException
	{
		DataOutputStream dops = null;
		try
		{
			if (isConnected())
			{
				PrintLog.printLog("start to send data[" + sendData.length
				        + "] to the host...");
				clientSocket.setSoTimeout(timeout * 1000);
				dops = new DataOutputStream(clientSocket.getOutputStream());
				dops.write(sendData,0,sendData.length);
				if (dops.size() != sendData.length)
				{
					PrintLog.printLog("send data not finished!!");

				}
				dops.flush();

			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * 接收数据报文从SERVER
	 */

	public byte[] recvDataFromSocket(Socket clientSocket, int l, int timeout)
	        throws IOException
	{
		String result = null;
		StringBuffer sb = new StringBuffer();
		DataInputStream dips = null;
		byte[] byTotal = null;
		
		try
		{
			if (isConnected())
			{
				PrintLog.printLog("start to recive Data from Host...");
				clientSocket.setSoTimeout(timeout * 1000);
				dips = new DataInputStream(clientSocket.getInputStream());
				int i = 0;

				byte by;
				byTotal = new byte[(int) l];
				while ((by = dips.readByte()) != -1)
				{
					byTotal[i] = by;
					i++;
					if (i == l)
					{
						break;
					}
				}

				result = new String(byTotal);
			}
			else
			{
				PrintLog.printLog("connecting is failed!");
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return byTotal;
	}

	/* 发送命令到HOST */

	public boolean sendCmdToSocket(Socket clientSocket, FileTransCmd ftc,
	        int timeout)
	{
		String sendStr = null;
		StringBuffer sb = new StringBuffer(300);
		for (int i = 0; i < 300; i++)
		{
			sb.insert(i, "\0");
		}

		sb.insert(0, ftc.getVs_outsys_code());
		sb.insert(7, ftc.getVs_req_flag());
		sb.insert(8, ftc.getVs_send_recv_flag());
		sb.insert(9, ftc.getVs_local_file_dir());

		sb.insert(73, ftc.getVs_local_file_name());
		sb.insert(137, ftc.getVs_remote_file_dir());
		sb.insert(201, ftc.getVs_remote_file_name());

		if (ftc.getVl_file_size() == 0)
		{
			sb.insert(265, "00000000");
		}
		else
		{
			String tmpStr = replaceStrWithInteger(ftc.getVl_file_size(), 8);
			sb.insert(265, tmpStr);
		}
		if (ftc.getVl_file_start_positon() == 0)
		{
			sb.insert(273, "00000000");
		}
		else
		{
			String tmpStr = replaceStrWithInteger(ftc
			        .getVl_file_start_positon(), 8);
			sb.insert(273, tmpStr);
		}
		if (ftc.getVl_trans_size() == 0)
		{
			sb.insert(281, "00000000");
		}
		else
		{
			String tmpStr = replaceStrWithInteger(ftc.getVl_trans_size(), 8);
			sb.insert(281, tmpStr);
		}

		if (ftc.getVs_retcode() == null)
		{
			sb.insert(289, "00");
		}
		else
		{
			sb.insert(289, ftc.getVs_retcode());
		}

		sendStr = sb.substring(0, FileTransCmd.CMDSTREAMLEN).toString();
		PrintLog.printLog("sendStr is:" + sendStr);

		try
		{
			if (sendDataToSocket(clientSocket, sendStr.getBytes(), timeout))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return true;
	}

	/*
	 * 接收命令从HOST
	 */

	public FileTransCmd recvCmdFromSocket(Socket clientSocket, int timeout)
	{
		FileTransCmd ftc = new FileTransCmd();
		String str = new String();
		byte[] bytStr = null;
		try
		{
			bytStr = recvDataFromSocket(clientSocket, FileTransCmd.CMDSTREAMLEN,
			        timeout);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		str = new String( bytStr);
		PrintLog.printLog("recvStr is:" + str);
		PrintLog.printLog("recvStr length is:" + str.length());

		ftc.setVs_outsys_code(str.substring(0, 7));
		ftc.setVs_req_flag(str.substring(7, 8));
		ftc.setVs_send_recv_flag(str.substring(8, 9));
		ftc.setVs_local_file_dir(str.substring(9, 73));
		ftc.setVs_local_file_name(str.substring(73, 137));
		ftc.setVs_remote_file_dir(str.substring(137, 201));
		ftc.setVs_remote_file_name(str.substring(201, 265));
		ftc.setVl_file_size(Integer.parseInt(str.substring(265, 273)));
		ftc.setVl_file_start_positon(Integer.parseInt(str.substring(273, 281)));
		ftc.setVl_trans_size(Integer.parseInt(str.substring(281, 289)));
		ftc.setVs_retcode(str.substring(289, 291));

		// ftc.displayFileCmd();

		return ftc;
	}
	/* 发送响应码给对方 */
	public boolean sendRetCode(Socket clientSocket, FileTransCmd ftc,
	        String ret_code)
	{
		ftc.setVs_retcode(ret_code);
		PrintLog.printLog("start to send ret_code to Host...");
		// ftc.displayFileCmd();
		if (sendCmdToSocket(clientSocket, ftc, timeout))
		{
			PrintLog.printLog("sendRetCode Success");
		}
		else
		{
			PrintLog.printLog("sendRetCode Error");
			return false;
		}
		return true;
	}

	/* 从服务器接收文件 */

	public boolean recvFileFromHost(Socket clientSocket, FileTransCmd ftc)
	        throws IOException
	{
		FileTransCmd ftcRcev = new FileTransCmd();
		String fileName = null;
		StringBuffer sb = new StringBuffer();
		File fi = null;
		FileOutputStream fops = null;
		/* 发送请求文件命令 */
		if (!sendCmdToSocket(clientSocket, ftc, timeout))
		{
			PrintLog.printLog("0:sendCmdToSocket error");
			return false;
		}
		PrintLog.printLog("0:发送命令到HOST成功!");

		if ((ftcRcev = recvCmdFromSocket(clientSocket, timeout)) == null)
		{
			PrintLog.printLog("1:recvCmdFromSocket error");
			return false;
		}
		PrintLog.printLog("1: 从HOST接收命令成功!");

		if (ftcRcev.getVs_retcode().compareTo(FileTransCmd.TRANSFER_OK) != 0)
		{
			PrintLog.printLog("接收文件:" + ftc.getVs_remote_file_name() + "错误:"
			        + ftcRcev.getVs_retcode());
			return false;
		}

		/* 设置从服务器端返回的文件大小 */
		ftc.setVl_file_size(ftcRcev.getVl_file_size());
		if (ftc.getVl_file_size() < 0)
		{
			PrintLog.printLog("接收文件大小不合法:" + ftc.getVl_file_size());
			return false;
		}

		sb.append(ftc.getVs_remote_file_dir());
		sb.append(ftc.getVs_remote_file_name());
		fileName = sb.toString();

		/* 建立本地文件 */
		fi = new File(fileName);
		if ( fi.exists())
		{
			PrintLog.printLog("Warning: local file :"+fileName+" exists");
		}
		try
		{
			fops = new FileOutputStream(fi); /* 打开文件输出流 */
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		/* 接收命令，根据命令，一个一个的接收数据文本 */
		String recvStr = null;
		while (true)
		{
			PrintLog.printLog("循环接收HOST命令...!");
			if ((ftcRcev = recvCmdFromSocket(clientSocket, timeout)) == null)
			{
				PrintLog.printLog("循环接收HOST命令失败！");
				fops.close();
				return false;
			}
			// ftcRcev.displayFileCmd();
			if (ftcRcev.getVl_trans_size() < 0)
			{
				PrintLog.printLog("循环接收文件传送字节流不合法!"
				        + ftcRcev.getVl_trans_size());
				fops.close();
				if (!sendRetCode(clientSocket, ftc, FileTransCmd.ERR_ERRORMSG))
				{
					PrintLog.printLog("发送接收文件失败信号失败!");
				}
				return false;
			}

			/* 接收数据文本 */
			byte[] bytStr = null;
			if ((bytStr = recvDataFromSocket(clientSocket, ftcRcev
			        .getVl_trans_size(), timeout)) == null)
			{
				PrintLog.printLog("接收文本文件失败");
				fops.close();
				if (!sendRetCode(clientSocket, ftc, FileTransCmd.ERR_FILETRANS))
				{
					PrintLog.printLog("发送接收文件失败信号失败!");
				}
				return false;
			}
			// PrintLog.printLog("接收数字:"+recvStr);
			// ftcRcev.displayFileCmd();
			/* 将接受文本写入文件 */
			fops.write(bytStr);

			PrintLog.printLog("接收文件长度：" + ftcRcev.getVl_trans_size());
			PrintLog.printLog("接收文件总长度:" + ftcRcev.getVl_file_size());

			/* 判断是否接受整个文件完毕 */
			if (ftcRcev.getVl_file_start_positon() + ftcRcev.getVl_trans_size() >= ftcRcev
			        .getVl_file_size())
			{
				PrintLog.printLog("接收文件完毕!,文件总长度:" + ftcRcev.getVl_file_size());
				if (!sendRetCode(clientSocket, ftc, FileTransCmd.TRANSFER_OK))
				{
					PrintLog.printLog("发送接收文件成功信号失败!");
				}
				fops.close();
				return true;
			}
			if (!sendRetCode(clientSocket, ftcRcev, FileTransCmd.TRANSFER_OK))
			{
				PrintLog.printLog("发送接收文件成功信号失败!");
			}

			PrintLog.printLog("接收文件片段成功");
		}
	}

	/* 发送文件到HOST */

	public boolean sendFileToHost(Socket clientSocket, FileTransCmd ftc)
	        throws IOException
	{
		FileInputStream fis = null;
		String fileName = null;
		StringBuffer sb = new StringBuffer();
		FileTransCmd ftcRecv = new FileTransCmd();

		
		sb.append(ftc.getVs_remote_file_dir());
		sb.append(ftc.getVs_remote_file_name());
		fileName = sb.toString();

		fis = new FileInputStream(fileName);
		PrintLog.printLog("文件大小：" + String.valueOf(fis.available()));

		if (fis.available() <= 0)
		{
			PrintLog.printLog("文件字节数不合法:" + fis.available());
			fis.close();
			return false;
		}

		ftc.setVl_file_size(fis.available());
		ftc.setVl_file_start_positon(0);

		/* 发送请求发送命令到服务器 */
		PrintLog.printLog("0:发送请求命令开始...");
		if (!sendCmdToSocket(clientSocket, ftc, timeout))
		{
			PrintLog.printLog("发送请求命令失败:");
			fis.close();
			return false;
		}

		PrintLog.printLog("1:发送请求命令成功...");

		/* 接收HOST命令 */

		if ((ftcRecv = recvCmdFromSocket(clientSocket, timeout)) == null)
		{
			PrintLog.printLog("接收HOST命令失败：");
			fis.close();
			return false;
		}
		PrintLog.printLog("2:接收HOST命令成功...");

		if (ftcRecv.getVs_retcode().compareTo("00") != 0)
		{
			PrintLog.printLog("HOST没有准备好:" + ftcRecv.getVs_retcode());
			fis.close();
			return false;
		}

		byte[] byt = new byte[FileTransCmd.FILEBUFSIZE];
		int len = 0;
		while ((len = fis.read(byt, 0, FileTransCmd.FILEBUFSIZE)) != -1)
		{
			ftc.setVl_trans_size(len);
			/* 发送命令到HOST */
			if (!sendCmdToSocket(clientSocket, ftc, timeout))
			{
				PrintLog.printLog("send cmd to Host failed!");
				fis.close();
				return false;
			}
			/* 发送数据到Host */
			
			//PrintLog.printLog("sendData len:"+len );
			//PrintLog.printLog("send Data is:"+ new String(byt));
			//PrintLog.printLog("sendData len :"+ (new String(byt).length()));
			
			
			if (!sendDataToSocket(clientSocket, byt, timeout))
			{
				PrintLog.printLog("send data to Host failed!");
				fis.close();
				return false;
			}
			ftc.setVl_file_start_positon(ftc.getVl_file_start_positon() + len);

			if (ftc.getVl_file_start_positon() >= ftc.getVl_file_size())
			{
				PrintLog.printLog("发送文件完毕!");
				fis.close();
				return false;

			}

			/* 接收HOST确认信息 */

			if ((ftcRecv = recvCmdFromSocket(clientSocket, timeout)) == null)
			{
				PrintLog.printLog("recive cmd from HOSt failed!");
				fis.close();
				return false;
			}

			if (ftcRecv.getVs_retcode().compareTo(FileTransCmd.TRANSFER_OK) != 0)
			{
				PrintLog.printLog("Host recive data failed!");
				fis.close();
				return false;
			}

		}

		return true;

	}

	/*
	 * 替换整形为字符串
	 */

	public String replaceStrWithInteger(int l, int len)
	{
		byte[] by = new byte[len];
		for (int ii = 0; ii < len; ii++)
		{
			by[ii] = '0';
		}
		String st = new String(by);

		String st1 = String.valueOf(l);
		StringBuffer sbTwo = new StringBuffer(st);
		int end = 0;
		end = len - st1.length();
		sbTwo.replace(end, len, st1);

		// logger.debug("haha is:"+ sbTwo.toString());

		return sbTwo.toString();
	}
	/**
	 * @param args  
	 * @param  
	 * ftpProperty.xml配置说明，ncpi-ip 配置 保险系统的FTP服务器IP地址，ncpai-port 配置
	 *              保险系统FTP服务器的端口， ncpai-timeout配置与保险系统的超时时间，一般为60秒
	 * 文件传输目录中，各保险公司代码目录参照文件传输说明书中的各保险公司命名规则
	 * 
	 * @return
	 */
	public static void main(String[] args)
	{

		boolean send_recv = false;

		FtpClient fc = new FtpClient();

		FileTransCmd ftc = new FileTransCmd();
		String FilePath = "";
		String FileName = "";
		
		if(args.length>=2)
		{
			FilePath=args[0];
			FileName=args[1];
		}
		if (args.length == 3 && "1".equals(args[2]))
		{
			send_recv = true;
		}
		
		/* 向保险系统接收文件 */
		if (send_recv)
		{
			ftc.setVs_outsys_code("9940001");             /*请求接收系统代码，固定*/
			ftc.setVs_req_flag("0");                      /*请求标志，固定*/
			ftc.setVs_send_recv_flag("1");                /*向保险系统接收文件标志，固定*/
			ftc.setVs_local_file_dir("insu/0013");        /*请求保险系统文件目录，insu固定，0001为各保险公司代码*/
			ftc.setVs_local_file_name(FileName);       /*请求文件名*/
			ftc.setVs_remote_file_dir(FilePath);             /*下载本地保存文件路径,完全路径*/
			ftc.setVs_remote_file_name(FileName);      /*本地文件名*/
		}
		else        /*向保险系统发送文件*/
		{ 
			ftc.setVs_outsys_code("9940001");               /*请求接收系统代码，固定*/   
			ftc.setVs_req_flag("0");                        /*请求标志，固定*/
			ftc.setVs_send_recv_flag("0");                  /*向保险系统发送文件标志,固定*/
			ftc.setVs_local_file_dir("insu/0013");          /*保险系统文件保存目录,insu固定，0001为各保险公司代码*/
			ftc.setVs_local_file_name(FileName);       /*保险系统接受文件名*/
			ftc.setVs_remote_file_dir(FilePath);               /*上传文件本地路径，完全路径*/
			ftc.setVs_remote_file_name(FileName);        /*上传文件名*/
		}
		
		logger.debug("send_recv: "+send_recv +" (true 接收文件,false 发送文件)");
		logger.debug("IP: "+fc.ipAddress);
		logger.debug("Port: "+fc.port);
		fc.createSocketClient(fc.ipAddress, fc.port);
		try
		{
			
			if (send_recv)
			{
				fc.recvFileFromHost(fc.clientSocket, ftc);
			}
			else
			{
				fc.sendFileToHost(fc.clientSocket, ftc);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		fc.closeSocketClient(fc.clientSocket);
	}

}
