package com.yan.wang.template;

import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository {

	public String getTemplateContent();
}