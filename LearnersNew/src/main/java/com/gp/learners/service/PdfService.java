package com.gp.learners.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gp.learners.entities.Pdf;
import com.gp.learners.repositories.PdfRepository;

@Service
public class PdfService {

	@Autowired
	PdfRepository pdfRepository;

	//getPdfList
	public List<Pdf> getPdfList(){
		System.out.println("IN pdfrepo");
		List<Pdf> pdfList=pdfRepository.findAll();
		//Pdf pdf1=pdfList.get(0);
		return pdfList;
	}

	public Pdf getPdfByID(Integer pdfId) {
		if(pdfId != null) {
			if(pdfRepository.existsById(pdfId)) {
				return pdfRepository.getPdfById(pdfId);
			}
		}
		return new Pdf(); 
	}

	//Add PDF
		public String addPdf(Pdf pdf) {

			Pdf result=pdfRepository.save(pdf);
			if(result!=null)
				return "success";
			else
				return "notsuccess"; 
		}

		//delete Pdf
		public String deletePdf(Integer pdfId) {
			System.out.println("Delete Pdf Serv");
			if(pdfId != null) {
				if(pdfRepository.existsById(pdfId)) {
					pdfRepository.deleteById(pdfId);
					return "success";
				}
			}
			return "error";
		}



		//update Student Details
		public Pdf updatePdf(Pdf pdf) {
			if(pdfRepository.existsById(pdf.getPdfId())) {
				//Pdf pdf1=pdfRepository.getPdfById(pdf.getPdfId());
						return pdfRepository.save(pdf);
					}

			return new Pdf();
		}

}