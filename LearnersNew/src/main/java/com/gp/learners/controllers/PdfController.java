package com.gp.learners.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.Pdf;
import com.gp.learners.repositories.PdfRepository;
import com.gp.learners.service.PdfService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class PdfController {

	@Autowired
	PdfService pdfService;

	@Autowired
	PdfRepository pdfRepository;

	@GetMapping("/pdfs")
	public List<Pdf> getPdfList() {
		return pdfService.getPdfList();
	}

	@GetMapping("pdf/{pdfId}")
	public ResponseEntity<Pdf> getPdf(@PathVariable("pdfId") Integer pdfId) {
		Pdf pdf = pdfService.getPdfByID(pdfId);
		if (pdf.getPdfId() != null) {
			return ResponseEntity.ok(pdf);
		}
		return ResponseEntity.noContent().build();
	}

	// add pdf
	@PostMapping("pdf/add")
	public ResponseEntity<Pdf> addPdf(@RequestBody Pdf pdf) {
		// System.out.println("stuId:"+stuId+" object:"+object);

		Pdf pdf1 = pdfService.addPdf(pdf);
		System.out.println(pdf1.getPdfId());
		if (pdf1.getPdfId() != null) {
			return ResponseEntity.ok(pdf1);
		}
		return ResponseEntity.noContent().build();
	}

	// delete Pdf
	@DeleteMapping("pdf/delete/{pdfId}")
	public ResponseEntity<Void> deletePdf(@PathVariable("pdfId") Integer pdfId) {
		System.out.println("Delete Pdf");
		String reply = pdfService.deletePdf(pdfId);
		if (reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	// update student Details
	@PutMapping("pdf/update")
	public ResponseEntity<Pdf> updatePdf(@Valid @RequestBody Pdf object) {
		System.out.println("In Pdf Update Controller");
		Pdf pdf = pdfService.updatePdf(object);
		if (pdf.getPdfId() != null) {
			return ResponseEntity.ok(pdf);
		}
		return ResponseEntity.notFound().build();
	}

}