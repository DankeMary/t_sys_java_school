package net.tsystems.validator;

import net.tsystems.bean.StationBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class StationValidator implements Validator {
    public boolean supports(Class<?> aClass) {
        return StationBean.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        StationBean station = (StationBean) o;

        if (station.getName().length() > 65) {
            errors.rejectValue("name", "Size.stationForm.name", "Max length - 65");
        }
        if (station.getName().trim().equals(""))
            ValidationUtils.rejectIfEmpty(errors, "name", "NotEmpty", "Station name required");
    }
}
