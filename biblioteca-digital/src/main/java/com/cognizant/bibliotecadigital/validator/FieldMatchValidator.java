package com.cognizant.bibliotecadigital.validator;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.*;



public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {


	
    private String primeiroCampo;
    private String segundoCampo;
    private String mensagem;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        primeiroCampo = constraintAnnotation.primeiro();
        segundoCampo = constraintAnnotation.segundo();
        mensagem = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            final Object firstObj = BeanUtils.getProperty(value, primeiroCampo);
            final Object secondObj = BeanUtils.getProperty(value, segundoCampo);

            valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore)
        {
            // ignore
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(mensagem)
                    .addPropertyNode(primeiroCampo)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
