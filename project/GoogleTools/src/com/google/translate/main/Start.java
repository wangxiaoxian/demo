package com.google.translate.main;

import com.google.translate.service.GoogleTranslateService;

public class Start {

	public static void main(String[] args) {
		
		GoogleTranslateService service = new GoogleTranslateService();
		service.translate();
	}

}
