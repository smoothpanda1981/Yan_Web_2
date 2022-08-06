package com.yan.wang.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

	@Autowired
	private TemplateRepository templateRepository;

	public String getTemplateContent() {
		String result = "";
		System.out.println("2");

		result = templateRepository.getTemplateContent();

		return result;
	}
}
