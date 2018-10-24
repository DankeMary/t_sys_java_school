package net.tsystems.validator;

import net.tsystems.bean.TrainBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TrainValidator implements Validator {
    public boolean supports(Class<?> aClass) {
        return TrainBean.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        TrainBean train = (TrainBean) o;

        if (train.getNumber() == null) {
            errors.rejectValue("number", "NotNull", "Train number is mandatory");
        } else if (train.getNumber() > Integer.MAX_VALUE) {
            errors.rejectValue("number", "Size.trainForm.number", "Max train number - " + Integer.MAX_VALUE);
        } else if (train.getNumber() <= 0) {
            errors.rejectValue("number", "Size.trainForm.number", "Train number has to be positive");
        }

        if (train.getCapacity() == null) {
            errors.rejectValue("capacity", "NotNull", "Train capacity is mandatory");
        } else if (train.getCapacity() > Integer.MAX_VALUE) {
            errors.rejectValue("capacity", "Size.trainForm.capacity", "Max train capacity - " + Integer.MAX_VALUE);
        } else if (train.getCapacity() <= 0) {
            errors.rejectValue("capacity", "Size.trainForm.capacity", "Train capacity has to be positive");
        }
    }
}
