package com.thekliar.reactive.handler;

import com.thekliar.reactive.dto.BaseDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidationHandler {

  private final Validator validator;

  public final <T extends BaseDto> Errors validate(final T dto) {
    String className = dto.getClass().getSimpleName();
    Errors errors = new BeanPropertyBindingResult(dto, className);
    validator.validate(dto, errors);
    return errors;
  }
}
