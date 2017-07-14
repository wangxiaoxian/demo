package com.test.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FtpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpUtil.class);
	private FTPClient ftpClient = new FTPClient();
	private String hostname;
	private Integer port;
	private String username;
	private String password;
	private String encoding = "GBK";
	private String objectName = this.getClass().getName() + "_" + System.currentTimeMillis();

	public String getHostname() {
		return hostname;
	}

	public Integer getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public FtpUtil(String hostname, int port, String username,
					String password, String encoding) throws IOException {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		if (encoding != null) {
			this.encoding = encoding;
		}
	}

	public FtpUtil(String hostname, String username, String password)
			throws IOException {
		this(hostname, 21, username, password, null);
	}

	public FtpUtil(String hostname, int port, String username, String password)
			throws IOException {
		this(hostname, port, username, password, null);
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	private boolean upload(File file, String remotePath, boolean isFirstDir,
			boolean ignoreFirst) throws IOException {
		if (file.isDirectory()) {
			uploadDir(file, remotePath, isFirstDir, ignoreFirst);
		} else {
			uploadFile(file, remotePath);
		}
		return true;
	}

	public boolean upload(File file, String remotePath, boolean ignoreFirst)
			throws IOException {
		return upload(file, remotePath, true, ignoreFirst);
	}

	private boolean uploadFile(File file, String remotePath) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		boolean dirChg = false;
		boolean rst = false;
		try {
			dirChg = ftpClient.changeWorkingDirectory(remotePath);
			rst = ftpClient.storeFile(file.getName(), inputStream);
			LOGGER.debug("ftp-path:"+remotePath);
			LOGGER.debug("上传文件:{},目录切换:{},结果:{},文件大小：{},文件大小：{}",
					new Object[] { file.getName(), dirChg, rst, file.length() });
		}catch(IOException e) {
			LOGGER.error("ftp exception:"+e);
		}
		finally {
			inputStream.close();
		}
		return rst;

	}

	private boolean uploadDir(File file, String remotePath, boolean isFirstDir,
			boolean ignoreFirst) throws IOException {
		ftpClient.changeWorkingDirectory(remotePath);
		if ((!isFirstDir) || !ignoreFirst) {
			ftpClient.mkd(file.getName());
			remotePath = remotePath + "/" + file.getName();
		}
		File[] fileList = file.listFiles();
		for (File f : fileList) {
			upload(f, remotePath, false, ignoreFirst);
		}
		return true;
	}

	public void download(String remotePath, String localBasePath)
			throws IOException {
		ftpClient.changeWorkingDirectory(remotePath);
		FTPFile[] fs = ftpClient.listFiles();
		for (FTPFile ff : fs) {
			if (ff.isDirectory()) {
				downloadDir(remotePath, ff, localBasePath, null);
			} else {
				downloadFile(ff, localBasePath, null);
			}
		}
	}

	public void download(String remotePath, String localBasePath,
			Pattern fileNamePattern) throws IOException {
		ftpClient.changeWorkingDirectory(remotePath);
		FTPFile[] fs = ftpClient.listFiles();
		for (FTPFile ff : fs) {
			if (ff.isDirectory()) {
				downloadDir(remotePath, ff, localBasePath, fileNamePattern);
			} else {
				downloadFile(ff, localBasePath, fileNamePattern);
				
			}
		}
	}

	private void downloadDir(String remotePath, FTPFile ftpFile,
			String localBasePath, Pattern fileNamePattern) throws IOException {
		localBasePath = localBasePath + "/" + ftpFile.getName();
		File file = new File(localBasePath);
		file.mkdir();
		ftpClient.changeWorkingDirectory(remotePath + "/" + ftpFile.getName());
		FTPFile[] fileList = ftpClient.listFiles();
		for (FTPFile ftpF : fileList) {
			download(remotePath + "/" + ftpF.getName(), localBasePath);
		}
		ftpClient.changeToParentDirectory();
	}

	public boolean move(String origPath, String targetPath) throws IOException {
		return ftpClient.rename(origPath, targetPath);
	}

	public boolean moveListFile(String origPath, String targetPath,
			final String pattern) throws IOException {
		LOGGER.debug("move-fileList-pattern:"+pattern);
		FTPFile[] fs = ftpClient.listFiles(origPath, new FTPFileFilter() {

			@Override
			public boolean accept(FTPFile file) {
				if (!StringUtils.isBlank(pattern)) {
					return true;
				}
				LOGGER.debug("match-file:"+file.getName());
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(file.getName());
				if(m.matches()){
					LOGGER.debug("matched-file:"+file.getName());
				}
				return m.matches();
			}

		});
		
		String fileNames = "";
		
		boolean result = true;
		for (FTPFile f : fs) {
			fileNames += f.getName()+";";
			if (!ftpClient.rename(origPath + "/" + f.getName(), targetPath
					+ "/" + f.getName())) {
				if (!ftpClient.rename(origPath + File.separator + f.getName(),
						targetPath + File.separator + f.getName())) {
					result = false;
				}
			}
		}
		LOGGER.debug("ftp-move-fileList:"+fileNames);
		return result;
	}

	private void downloadFile(FTPFile ftpFile, String localBasePath,
			Pattern fileNamePattern) throws IOException {
		boolean needDownload = checkNeedDownload(ftpFile, fileNamePattern);
		if (needDownload) {
			LOGGER.debug("下载文件:{} ", ftpFile.getName());
			File localFile = new File(localBasePath + "/" + ftpFile.getName());
			OutputStream is = null;
			try {
				is = new FileOutputStream(localFile);
				ftpClient.retrieveFile(ftpFile.getName(), is);
				is.flush();
				is.close();
				is = null;
			} finally {
				if (is != null) {
					is = null;
				}
			}
			LOGGER.debug("下载文件完成:{} ", ftpFile.getName());
			//删除文件
			ftpClient.deleteFile(ftpFile.getName());
		} else {
			LOGGER.debug("非目标文件，不需要下载:{} ", ftpFile.getName());
		}
	}

	private boolean checkNeedDownload(FTPFile ftpFile, Pattern fileNamePattern) {
		boolean needDownload = true;
		if (fileNamePattern != null) {
			needDownload = fileNamePattern.matcher(ftpFile.getName()).matches();
		}
		return needDownload;

	}

	public void delete(String remoteDir, String fileName) throws IOException {
		ftpClient.changeWorkingDirectory(remoteDir);
		ftpClient.deleteFile(fileName);
	}

	public void connect() throws IOException {
		LOGGER.debug("connect() begin objectName=" + this.objectName);
		if (!ftpClient.isAvailable()) {
			reconnect();
		}
		if (!ftpClient.isConnected()) {
			throw new IOException("连接ftp服务器失败 objectName=" + this.objectName);
		}
	}

	public void reconnect() throws IOException {
		LOGGER.debug("ftpClient is connection closed,need reconnect ! objectName=" + this.objectName);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setControlEncoding(encoding);
		ftpClient.connect(hostname, port);
		LOGGER.debug("ftpClient objectName:{} connect {}:{} " ,new Object[]{objectName,hostname,port});
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		boolean logined = ftpClient.login(username, password);
		LOGGER.debug("ftpClient objectName:{} login  {}/{} result:{} " ,new Object[]{objectName,username,password,logined});
		int reply = ftpClient.getReplyCode();
		if (((!logined) || (!FTPReply.isPositiveCompletion(reply)))
                && ftpClient.isConnected()) {
            LOGGER.debug("ftpClient disconnect() objectName=" + this.objectName);
            ftpClient.disconnect();
        }
	}

	public void disConnect() {
		try {
			if (ftpClient != null && ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			LOGGER.error("关闭ftp连接出现异常", e);
			return;
		}
	}

	public List<String> getFileNames(String remotePath, String pattern)
			throws IOException {
		List<String> filenames = new ArrayList<String>();
		ftpClient.changeWorkingDirectory(remotePath);
		FTPFile[] fs = ftpClient.listFiles();
		for (FTPFile f : fs) {
			if (!f.isDirectory() && f.getName().indexOf(pattern) >= 0) {
				filenames.add(f.getName());
			}
		}
		return filenames;
	}

	public List<String> getFileNames(String remotePath,int type)
			throws IOException {
		List<String> filenames = new ArrayList<String>();
		ftpClient.changeWorkingDirectory(remotePath);
		FTPFile[] fs = ftpClient.listFiles();
		for (FTPFile f : fs) {
			if (type == FTPFile.FILE_TYPE && !f.isDirectory()) {
				filenames.add(f.getName());
			} else if (type == FTPFile.DIRECTORY_TYPE && f.isDirectory()) {
				filenames.add(f.getName());
			}else if(type == FTPFile.UNKNOWN_TYPE){
				filenames.add(f.getName());
			}
		}
		return filenames;
	}

	public void downloadFile(String remotePath, String localBasePath,
			String filename) throws IOException {
		ftpClient.changeWorkingDirectory(remotePath);
		FTPFile[] fs = ftpClient.listFiles();
		for (FTPFile f : fs) {
			if (!f.isDirectory() && f.getName().equals(filename)) {
				downloadFile(f, localBasePath, null);
			}
		}
	}

	public void copyDirectiory(String sourceDir, String filename,
			String targetDir) throws IOException {
		LOGGER.debug("开始复制文件:{},原始目录:{},目标:{}",
				new Object[] { filename, sourceDir, targetDir });
		FTPFile[] ftpFiles = ftpClient.listFiles(sourceDir);
		for (FTPFile file : ftpFiles) {
			if (filename.equals(file.getName())) {
				copyFile(filename, sourceDir, targetDir);
			}
		}
	}

	private void copyFile(String sourceFileName, String sourceDir,
			String targetDir) throws IOException {
		LOGGER.debug("开始复制文件:"+sourceFileName);
		InputStream is = null;
		try {
			ftpClient.changeWorkingDirectory(sourceDir);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			is = ftpClient.retrieveFileStream(new String(sourceFileName
					.getBytes("GBK"), "GBK"));
			ftpClient.getReply();
			if (is != null) {
				ftpClient.changeWorkingDirectory(targetDir);
				ftpClient.storeFile(new String(sourceFileName.getBytes("GBK"),
						"GBK"), is);
				LOGGER.debug("成功复制文件:{},原始目录:{},目标:{}",
						new Object[] { sourceFileName, sourceDir, targetDir });
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public boolean upload(InputStream inputStream, String remotePath,String remoteFileName) throws IOException {
		boolean dirChg = false;
		boolean rst = false;
		try {
			connect();
			String[] dirArray = remotePath.split("/");
			//先进入顶级目录
			LOGGER.debug("进入顶级目录 objectName=" + this.objectName);
			ftpClient.changeWorkingDirectory("/");
			for(String dir : dirArray){
				if(dir != null && !"".equals(dir.trim())){
					LOGGER.debug("ftp objectName:{} 如果不存在，创建目录:{}",new Object[]{objectName,dir});
					ftpClient.makeDirectory(dir);
					LOGGER.debug("ftp objectName:{}  切换目录:{}",new Object[]{objectName,dir});
					ftpClient.changeWorkingDirectory(dir);
				}
			}
			this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			rst = ftpClient.storeFile(remoteFileName, inputStream);
			LOGGER.debug("objectName:{} ftp-path:",new Object[]{objectName,remotePath});
			LOGGER.debug("objectName{} 上传文件:{},目录切换:{},结果:{}",
					new Object[] { objectName,remoteFileName, dirChg, rst});
		}catch(IOException e) {
			LOGGER.error("ftp exception objectName:{} " + objectName,e);
		}
		finally {
			inputStream.close();
		}
		return rst;

	}
	
	public static void main(String[] args) {
		FtpUtil ftp = null;
		try {
			// ftp = new FTPUtils("192.168.233.129", 21, "docker", "docker",
			// "UTF-8");
			ftp = new FtpUtil("10.12.12.55", 21, "utboss", "aspire+888", "GBK");
			ftp.connect();
			ftp.download("/opt/aspire/product/utboss/test", "E:\\");
			// System.out.println(ftp.uploadFile(new File("E:\\t.txt"),
			// "/release"));
			// System.out.println(ftp.move("/release/t.txt",
			// "/release_tmp/t.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ftp.disConnect();
		}
	}


	public String getFileUrl(String remotePath,String remoteFileName) throws UnsupportedEncodingException {
		String enc = "utf-8";
		return String.format("ftp://%s:%s@%s:%d%s%s", URLEncoder.encode(this.getUsername(),enc),URLEncoder.encode(this.getPassword(),enc), this.getHostname(),this.getPort(),remotePath,remoteFileName);
	}
}
