package com.fitness.service.interfaces;

import com.fitness.dto.pay_card_dto.PayCardCreateDto;
import com.fitness.dto.pay_card_dto.PayCardResponseDto;
import com.fitness.dto.pay_card_dto.PayCardUpdateDto;

/**
 * Service for managing user pay card.
 */
public interface PayCardService extends BaseService<PayCardResponseDto, PayCardCreateDto, PayCardUpdateDto, Long> {
}
