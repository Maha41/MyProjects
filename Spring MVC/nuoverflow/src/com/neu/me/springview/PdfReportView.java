package com.neu.me.springview;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.neu.me.pojo.Comments;
import com.neu.me.pojo.Items;

public class PdfReportView extends AbstractPdfView
{
	Items item;
	public PdfReportView(Items item) {
		// TODO Auto-generated constructor stub
		this.item= item;
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document pdfdoc, PdfWriter pdfwriter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Font font_helvetica_16_normal_blue = new Font(Font.HELVETICA, 16, Font.NORMAL, Color.BLUE); 
		
		Font font_times_12_bold_black = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.BLACK);
		Font font_times_14_bold_black = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLACK);
		
		Chunk c1 = new Chunk("Posted On:" +item.getPostDate(), font_helvetica_16_normal_blue);
		Paragraph c2 = new Paragraph(item.getItemName(), font_helvetica_16_normal_blue);
		
		Paragraph prg1 = new Paragraph("Description", font_times_14_bold_black);
		Paragraph prg2 = new Paragraph(item.getDescription(), font_times_12_bold_black);
		
		Paragraph prg3 = new Paragraph("Benifits", font_times_14_bold_black);
		Paragraph prg4 = new Paragraph(item.getBenefits(), font_times_12_bold_black);
		
		Paragraph prg5 = new Paragraph("Procedure", font_times_14_bold_black);
		Paragraph prg6 = new Paragraph(item.getProcedurestep(), font_times_12_bold_black);
		
		
		Paragraph prg7 = new Paragraph("Comments", font_times_14_bold_black);
		pdfdoc.add(c1);
		pdfdoc.add(c2);
		pdfdoc.add(prg1);
		pdfdoc.add(prg2);
		pdfdoc.add(prg3);
		pdfdoc.add(prg4);
		pdfdoc.add(prg5);
		pdfdoc.add(prg6);
		pdfdoc.add(prg7);
		
		int i = 1;
		
		for(Comments comment : item.getComments())
		{
			Paragraph prg = new Paragraph(i+":"+comment.getCommentDesc(), font_times_12_bold_black);
			pdfdoc.add(prg);
			i++;
		}
		
		
		
	}

	public Items getItem() {
		return item;
	}

	public void setItem(Items item) {
		this.item = item;
	}

}
