package br.gov.rs.defensoria.removal.report

import br.gov.rs.defensoria.removal.report.presentation.ResultRowPresenter
import br.gov.rs.defensoria.removal.report.presentation.ResultTablePresenter

import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.Phrase
import com.itextpdf.text.Rectangle
import com.itextpdf.text.Font.FontFamily
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter

class FechamentoResultPdfReport {
	public String[] defaultHeader = ['#', 'A','Servidor(a)', 'Lotacao Atual', 'Lotacao Pretendida']
	public Rectangle pageSize = PageSize.A4
	public Font headerTitleFont = new Font(FontFamily.HELVETICA, 14, 1, BaseColor.BLACK)
	public Font tableTitleFont = new Font(FontFamily.HELVETICA, 8.5, 1, BaseColor.BLACK)
	public Font bodyFont = new Font(FontFamily.HELVETICA, 9, 0, BaseColor.BLACK)
	public BaseColor borderColor = new BaseColor(237, 234, 232)
	public int marginSize = 30

    void generate(ResultTablePresenter result, String outfilename) {
		Document doc = new Document(pageSize, 15, 15, 20, 1)
		PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(outfilename))
		doc.open()
		PdfPTable pdfTable = new PdfPTable(defaultHeader.length)
		pdfTable.setTotalWidth((float)(PageSize.A4.getWidth() - marginSize))
		pdfTable.setLockedWidth(true)
		pdfTable.setHorizontalAlignment(Element.ALIGN_CENTER)
		pdfTable.setWidths([3,3,24,35,35] as int[])
		
		generateTableHeader(defaultHeader).each { pdfTable.addCell(it) }
		generateBody(result.rows).each { pdfTable.addCell(it) }
		
		doc.add(generateHeader(result.title))
		doc.add(pdfTable)
		doc.close()
	}
	
	private PdfPTable generateHeader(String title) {
		def header = new PdfPTable(2)
		header.setWidths([35, 65] as float[])
		header.setTotalWidth((float)(PageSize.A4.getWidth() - marginSize))
		header.setLockedWidth(true)
		header.setSpacingAfter(25)
		header.getDefaultCell().setBorderColor(borderColor)
		header.getDefaultCell().setBorder(Rectangle.NO_BORDER)
		
		def headerImage = Image.getInstance(this.getClass().getResource('/images/logo_dpe_txt.png'))
		headerImage.setBorder(Rectangle.NO_BORDER)
		header.addCell(headerImage)
		
		
		def headerCell = new PdfPCell(new Phrase(title, headerTitleFont))
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT)
		headerCell.setBorder(Rectangle.NO_BORDER)
		headerCell.setPaddingTop(10)
		header.addCell(headerCell)

		return header
	}
	
	private List<PdfPCell> generateTableHeader(String[] headerTitles) {
        List<PdfPCell> header = new ArrayList<>()
		headerTitles.eachWithIndex { it, idx ->
			PdfPCell cell = new PdfPCell(new Phrase(it, tableTitleFont))
			if(idx < 2) cell.setHorizontalAlignment(Element.ALIGN_CENTER)
			cell.setBorder(Rectangle.NO_BORDER)
			cell.setPaddingBottom(4)
			header.add(cell)
		}	
		return header
	}
	
	private List<PdfPCell> generateBody(List<ResultRowPresenter> rows) {
        List<PdfPCell> body = new ArrayList<>()
		rows.eachWithIndex { row, idx ->
			def rowRendered = renderRow(idx+1, row.antiquity, row.name, row.allocationName, row.pretensionName)
			rowRendered.each { body.add(it) }
		}
		return body
	}
	
	private List<PdfPCell> renderRow(int rowNumber, int antiquity, String name, String allocation, String pretension) {
        List<PdfPCell> cols = new ArrayList<>()
        cols.add(new PdfPCell(new Phrase(rowNumber as String, bodyFont)))
        cols.add(new PdfPCell(new Phrase(antiquity as String, bodyFont)))
        cols.add(new PdfPCell(new Phrase(name, bodyFont)))
        cols.add(new PdfPCell(new Phrase(allocation, bodyFont)))
        cols.add(new PdfPCell(new Phrase(pretension, bodyFont)))
		
		cols.each { 
			it.setPaddingBottom(5) 
			it.setBorderColor(borderColor)
		}
		if(rowNumber % 2 == 0)
        	cols.each{ it.setBackgroundColor(borderColor) }
		
		return cols
	}
}
