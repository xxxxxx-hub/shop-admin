package com.fh.util;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FileUtil {


    public static void deleteFile(HttpServletRequest request, String filePath){
        //获取项目发布在tomcat下的绝对路径
        String realPath = request.getServletContext().getRealPath("/");
        File file = new File(realPath + "/" + filePath);
        //exists方法用于判断一个文件(文件夹)是否存在，如果存在返回true,否则返回false
        if(file.exists()){
            file.delete();
        }
    }

    public static String uploadFile(HttpServletRequest request, MultipartFile file, String folderName) throws IOException {
        //1.获取项目部署在tomcat下的绝对路径(图片最终要上传的文件夹的绝对路径)
        String folderPath = request.getServletContext().getRealPath(folderName);

        //2.判断图片最终要上传的那个文件夹是否存在
        File folder = new File(folderPath);
        //如果该文件夹不存在则创建该文件夹
        if (!folder.exists()) {
            //创建文件夹
            folder.mkdir();
        }

        //3.给用户上传的图片进行重命名
        //3.1 获取用户上传的文件的后缀名
        //abc.jpg
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //3.2 生成一个新的文件名(UUID,时间戳)
        String newFileName = UUID.randomUUID().toString() + suffix;

        //4.调用springMVC自带的方法将用户上传的文件保存到指定的文件夹中
        file.transferTo(new File(folderPath + "/" + newFileName));

        return folderName + "/" + newFileName;
    }

    /**
     * 文件下载
     * @param file
     * @param fileName
     * @param response
     */
    public static void downloadFile(File file, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream os = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bos = new BufferedOutputStream(os);
        //解决下载文件名中文乱码问题
        if(request.getHeader("User-Agent").toLowerCase().indexOf("firefox")!=-1){
            fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
        }else{
            fileName = URLEncoder.encode(fileName,"utf-8");
        }
        response.reset(); // 重点突出
        response.setCharacterEncoding("UTF-8"); // 重点突出
        response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        //定义一个长度为4096的字节数组
        byte[] bytes = new byte[4096];
        //先读他个4096字节
        int read = bis.read(bytes);
        while(read > 0){
            bos.write(bytes,0,read);
            read = bis.read(bytes);
        }
        bos.close();
        bis.close();
    }

	public static String calculateFileSize(long size) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (size < 1024) {
			return size+"B";
		} else if (size < 1024 * 1024) {
			return df.format((double)size / 1024)+"KB";
		} else if (size < 1024 * 1024 * 1024) {
			return df.format((double)size / (1024 * 1024))+"MB";
		} else {
			return df.format((double)size / (1024 * 1024 * 1024))+"GB";
		}
	}

	public static String buildPdfHtml(Map data, String pdfTemplateFile, String templatePath){
		// 将其转换为html
		StringWriter sw = null;
		try {
			Configuration configuration = new Configuration();
			// 解决freemarker的乱码问题
			configuration.setDefaultEncoding("utf-8");
			configuration.setClassForTemplateLoading(FileUtil.class, templatePath);
			Template template = configuration.getTemplate(pdfTemplateFile);
			sw = new StringWriter();
			template.process(data, sw);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return sw.toString();
	}



	public static File buildWord(Map data, String templateFile, String templatePath, String savePath){
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		File file = null;
		try {
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(FileUtil.class, templatePath);
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate(templateFile);
		file = new File(savePath+UUID.randomUUID().toString()+".docx");
		out = new FileOutputStream(file);
		osw = new OutputStreamWriter(out, "utf-8");
		template.process(data, osw);
		osw.flush();
		} catch (TemplateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (null != osw) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}




	public static String copyFile(File file, String fileName, String folderPath) {
		// 上传物理文件到服务器硬盘
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		String uploadFileName = null;
		try {
			// 构建读文件的流即输入流
			fis = new FileInputStream(file);
			// 构建输入缓冲区，提高读取文件的性能
			bis = new BufferedInputStream(fis);
			// 自动建立文件夹
			File folder = new File(folderPath);
			if (!folder.exists()) {
				// 创建文件夹
				folder.mkdirs();
			}
			// 为了保证上传文件的唯一性，可以通过uuid来解决
			// 为了避免中文乱码问题则新生成的文件名为uuid+原来文件名的后缀
			uploadFileName = UUID.randomUUID().toString()+getSuffix(fileName);
			// 构建写文件的流即输出流
			fos = new FileOutputStream(new File(folderPath+"/"+uploadFileName));
			// 构建输出缓冲区，提高写文件的性能
			bos = new BufferedOutputStream(fos);
			// 通过输入流读取数据并将数据通过输出流写到硬盘文件中
			byte[] buffer = new byte[4096];// 构建4k的缓冲区
			int s = 0;
			while ((s=bis.read(buffer)) != -1) {
				bos.write(buffer, 0, s);
				bos.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
					bos = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (bis != null) {
				try {
					bis.close();
					bis = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (fis != null) {
				try {
					fis.close();
					fis = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return uploadFileName;
	}

	public static String copyFile(InputStream is, String fileName, String folderPath) {
		// 上传物理文件到服务器硬盘
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		String uploadFileName = null;
		try {
			// 构建输入缓冲区，提高读取文件的性能
			bis = new BufferedInputStream(is);
			// 自动建立文件夹
			File folder = new File(folderPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			// 为了保证上传文件的唯一性，可以通过uuid来解决
			// 为了避免中文乱码问题则新生成的文件名为uuid+原来文件名的后缀
			uploadFileName = UUID.randomUUID().toString()+getSuffix(fileName);
			// 构建写文件的流即输出流
			fos = new FileOutputStream(new File(folderPath+"/"+uploadFileName));
			// 构建输出缓冲区，提高写文件的性能
			bos = new BufferedOutputStream(fos);
			// 通过输入流读取数据并将数据通过输出流写到硬盘文件中
			byte[] buffer = new byte[4096];// 构建4k的缓冲区
			int s = 0;
			while ((s=bis.read(buffer)) != -1) {
				bos.write(buffer, 0, s);
				bos.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
					bos = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (bis != null) {
				try {
					bis.close();
					bis = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return uploadFileName;
	}

	public static  void deleteFile(File f) {
		if (f.exists()) {
			f.delete();
		}
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String downloadFile, String fileName) {

		BufferedInputStream bis = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			File file=new File(downloadFile);
	        is = new FileInputStream(file);  //文件流的声明
	        os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
	        // 为了提高效率使用缓冲区流
	        bis = new BufferedInputStream(is);
	        bos = new BufferedOutputStream(os);
	        // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
	        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
	        	fileName = new String(fileName.getBytes("GB2312"),"ISO-8859-1");
	        } else {
	        	// 对文件名进行编码处理中文问题
	  	        fileName = URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
	  	        fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
	        }
	        response.reset(); // 重点突出
	        response.setCharacterEncoding("UTF-8"); // 重点突出
	        response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
	        response.setHeader("Content-Disposition", "attachment;filename="+ fileName);// 重点突出
	        int bytesRead = 0;
	        byte[] buffer = new byte[4096];
	        while ((bytesRead = bis.read(buffer)) != -1){ //重点
	            bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
	            bos.flush();
	        }

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
	        // 1. 进行关闭是为了释放资源
	        // 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != bis) {
					bis.close();
					bis = null;
				}
				if (null != bos) {
					bos.close();
					bos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file) {

		BufferedInputStream bis = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			String fileName = file.getName();
			is = new FileInputStream(file);  //文件流的声明
			os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
			// 为了提高效率使用缓冲区流
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(os);
			// 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("GB2312"),"ISO-8859-1");
			} else {
				// 对文件名进行编码处理中文问题
				fileName = URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
				fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
			}
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);// 重点突出
			int bytesRead = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead = bis.read(buffer)) != -1){ //重点
				bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
				bos.flush();
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != bis) {
					bis.close();
					bis = null;
				}
				if (null != bos) {
					bos.close();
					bos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	public static void xmlDownloadFile(HttpServletResponse response, Document document) {
		OutputStream os = null;
		XMLWriter xmlWriter = null;
		try {
	        os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
	        // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
	        response.reset(); // 重点突出
	        response.setCharacterEncoding("UTF-8"); // 重点突出
	        response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
	        response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".xml");// 重点突出
	        OutputFormat format = OutputFormat.createPrettyPrint();
	        format.setEncoding("utf-8");
	        xmlWriter = new XMLWriter(os, format);
	        xmlWriter.write(document);
	        xmlWriter.flush();
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
	        // 1. 进行关闭是为了释放资源
	        // 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != xmlWriter) {
					xmlWriter.close();
					xmlWriter = null;
				}
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
	public static void pdfDownloadFile(HttpServletResponse response, String htmlContent) {
		OutputStream os = null;
		com.itextpdf.text.Document document = null;
		PdfWriter writer = null;
		try {
			os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
			// 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".pdf");// 重点突出
			// step 1
			document = new com.itextpdf.text.Document();
			// step 2
			writer = PdfWriter.getInstance(document, os);
			// step 3
			document.open();
			// step 4
			XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			fontImp.register("simhei.ttf");
			XMLWorkerHelper.getInstance().parseXHtml(writer, document,
					new ByteArrayInputStream(htmlContent.getBytes("utf-8")), null, Charset.forName("UTF-8"), fontImp);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容
			if (null != document) {
				document.close();
				document = null;
			}
			if (null != writer) {
				writer.close();
				writer = null;
			}
			if (null != os) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void wordDownloadFile(HttpServletResponse response, String xmlContent) {
		OutputStream os = null;

		try {
			os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
			// 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".docx");// 重点突出

			os.write(xmlContent.getBytes());
			os.flush();

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容

			if (null != os) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void xlsDownloadFile(HttpServletResponse response, Workbook wb) {
		OutputStream os = null;
		try {
	        os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
	        // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
	        response.reset(); // 重点突出
	        response.setCharacterEncoding("UTF-8"); // 重点突出
	        response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
	        response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".xlsx");// 重点突出
	        wb.write(os);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
	        // 1. 进行关闭是为了释放资源
	        // 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	private static String getSuffix(String fileName) {
		int index = fileName.lastIndexOf(".");
		String suffix = fileName.substring(index);
		return suffix;
	}

	public static void excelDownload(XSSFWorkbook workBook, HttpServletResponse response) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
	        //让浏览器识别是什么类型的文件
	        response.reset(); // 重点突出
	        response.setCharacterEncoding("UTF-8"); // 重点突出
	        response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
	        // inline在浏览器中直接显示，不提示用户下载
	        // attachment弹出对话框，提示用户进行下载保存本地
	        // 默认为inline方式
	        response.setHeader("Content-Disposition", "attachment;filename="+UUID.randomUUID().toString()+".xlsx");
	        workBook.write(out);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if( null != out){
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}
	//导出下载
	public static void excelDownload1(XSSFWorkbook wirthExcelWB, HttpServletRequest request, HttpServletResponse response, String fileName) {
		OutputStream out = null;
		try {

			//解决下载文件名中文乱码问题
			if(request.getHeader("User-Agent").toLowerCase().indexOf("firefox")!=-1){
				fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
			}else{
				fileName = URLEncoder.encode(fileName,"utf-8");
			}

			out = response.getOutputStream();
			// 让浏览器识别是什么类型的文件
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
			// // 重点突出
			// inline在浏览器中直接显示，不提示用户下载
			// attachment弹出对话框，提示用户进行下载保存本地
			// 默认为inline方式
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			wirthExcelWB.write(out);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//反射导出Excel
	public static XSSFWorkbook generateWorkbook(List dataList, Class cls, String[] headerNameArr, String[] fieldNameArr, String sheetName) throws NoSuchFieldException, IllegalAccessException {
		//创建一个工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建一张工作表
		XSSFSheet sheet = workbook.createSheet(sheetName);
		//在工作表中创建表头行
		XSSFRow headerRow = sheet.createRow(0);
		//循环创建表头行中的每一个单元格
		for(int i = 0 ; i < headerNameArr.length ; i ++){
			XSSFCell headerCell = headerRow.createCell(i);
			headerCell.setCellValue(headerNameArr[i]);
		}

		//创建一个日期格式的单元格样式
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFDataFormat format= workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("yyyy年MM月dd日"));


		//遍历数据集合，循环创建每一个数据行
		for(int i = 0 ; i < dataList.size() ; i ++ ){
			XSSFRow row = sheet.createRow(i + 1);
			//获取当前遍历的对象
			Object obj = dataList.get(i);

			//filedNameArr:["id","name","createDate","updateDate"]
			for(int j = 0 ; j < fieldNameArr.length ; j ++){
				//获取当前遍历对象对应的类的对应的属性
				Field field = cls.getDeclaredField(fieldNameArr[j]);

				//设置暴力访问
				field.setAccessible(true);
				Object value = field.get(obj);
				//获取当前属性的数据类型
				Class fieldType = value.getClass();
				XSSFCell dataCell = row.createCell(j);

				//根据当前遍历属性的数据类型去给创建出来的单元格赋不同类型的值
				if(fieldType == int.class || fieldType == Integer.class){
					dataCell.setCellValue((Integer)value);
				}
				if(fieldType == BigDecimal.class){

					double doubleVal = ((BigDecimal) value).doubleValue();
					DataFormat d  = workbook.createDataFormat();
					cellStyle.setDataFormat(d.getFormat("￥#,##0.00"));
					dataCell.setCellValue(doubleVal);
				}
				if(fieldType == int.class || fieldType == Integer.class){
					dataCell.setCellValue((Integer)value);
				}else if(fieldType == double.class || fieldType == Double.class){
					dataCell.setCellValue((Double)value);
				}else if(fieldType == String.class){
					dataCell.setCellValue((String)value);
				}else if(fieldType == Date.class){
					dataCell.setCellValue((Date)value);
					dataCell.setCellStyle(cellStyle);
				}else if(fieldType == Long.class){
					dataCell.setCellValue((Long)value);
				}

			}
		}
		return workbook;
	}
	public static void xmlDownload(Document document, HttpServletResponse response) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		XMLWriter write = null;
		try {
			write = new XMLWriter(response.getOutputStream(),format);
			  //设置是否转义，默认值是true，代表转义
	        write.setEscapeText(false);
	        //让浏览器识别是什么类型的文件
	        response.reset(); // 重点突出
	        response.setCharacterEncoding("UTF-8"); // 重点突出
	        response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
	        // inline在浏览器中直接显示，不提示用户下载
	        // attachment弹出对话框，提示用户进行下载保存本地
	        // 默认为inline方式
	        response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID()+".xml");
			write.write(document);
			write.flush();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( null != write){
			try {
				write.close();
				write = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}




	public static void downloadFile2(File file, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream os = response.getOutputStream();
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bos = new BufferedOutputStream(os);
		//解决下载文件名中文乱码问题
		if(request.getHeader("User-Agent").toLowerCase().indexOf("firefox")!=-1){
			fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
		}else{
			fileName = URLEncoder.encode(fileName,"utf-8");
		}
		response.reset(); // 重点突出
		response.setCharacterEncoding("UTF-8"); // 重点突出
		response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		//定义一个长度为4096的字节数组
		byte[] bytes = new byte[4096];
		//先读他个4096字节
		int read = bis.read(bytes);
		while(read > 0){
			bos.write(bytes,0,read);
			read = bis.read(bytes);
		}
		bos.close();
		bis.close();
	}
}
