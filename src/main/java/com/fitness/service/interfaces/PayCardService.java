package com.fitness.service.interfaces;

import com.fitness.dto.pay_card_dto.PayCardCreateDto;
import com.fitness.dto.pay_card_dto.PayCardResponseDto;
import com.fitness.dto.pay_card_dto.PayCardUpdateDto;

public interface PayCardService extends BaseService<PayCardResponseDto, PayCardCreateDto, PayCardUpdateDto, Long> {
}
