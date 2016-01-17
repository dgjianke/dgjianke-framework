package com.cmcc.zysoft.spring_mvc.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmcc.zysoft.spring_mvc.model.User;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfView extends AbstractPdf5View {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Paragraph header = new Paragraph(new Chunk("PDF输出测试",
				getChineseFont(24)));
		document.add(header);
		User user = (User) model.get("user");
		document.add(new Paragraph(user.getUsername(),getChineseFont(12)));
	}

	private static final Font getChineseFont(float size) {
		Font FontChinese = null;
		try {
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			FontChinese = new Font(bfChinese, size, Font.NORMAL);
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		return FontChinese;
	}
}
