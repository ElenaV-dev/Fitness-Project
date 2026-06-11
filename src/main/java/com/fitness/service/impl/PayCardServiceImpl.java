package com.fitness.service.impl;

import com.fitness.dto.pay_card_dto.PayCardCreateDto;
import com.fitness.dto.pay_card_dto.PayCardResponseDto;
import com.fitness.dto.pay_card_dto.PayCardUpdateDto;
import com.fitness.service.interfaces.PayCardService;

import java.util.List;

public class PayCardServiceImpl implements PayCardService {

    @Override
    public PayCardResponseDto findById(Long aLong) {
        return null;
    }

    @Override
    public List<PayCardResponseDto> findAll() {
        return List.of();
    }

    @Override
    public void save(PayCardCreateDto dto) {

    }

    @Override
    public void update(PayCardUpdateDto dto) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
