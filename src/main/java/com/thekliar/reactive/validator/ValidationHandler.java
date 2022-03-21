package com.thekliar.reactive.validator;

import com.thekliar.reactive.dto.BaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.lang.reflect.ParameterizedType;

public class ValidationHandler<T extends BaseDTO> {

  @Autowired
  private Validator validator;

  @SuppressWarnings("unchecked")
  public final Errors validate(final T dto) {

    Class<T> type = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];

    Errors errors = new BeanPropertyBindingResult(dto, type.getName());
    validator.validate(dto, errors);
    return errors;
  }
}
