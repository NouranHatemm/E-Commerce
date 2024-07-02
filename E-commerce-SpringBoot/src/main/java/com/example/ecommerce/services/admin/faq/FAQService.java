package com.example.ecommerce.services.admin.faq;

import com.example.ecommerce.dto.FAQDto;

public interface FAQService {

    FAQDto postFAQ(Long productId, FAQDto faqDto);
}
