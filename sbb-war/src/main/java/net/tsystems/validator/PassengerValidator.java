package net.tsystems.validator;

import net.tsystems.bean.PassengerBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Date;

@Component
public class PassengerValidator implements Validator {
    public boolean supports(Class<?> aClass) {
        return PassengerBean.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        PassengerBean passenger = (PassengerBean) o;


        if (passenger.getFirstName().length() > 45) {
            errors.rejectValue("firstName", "Size.passengerForm.firstName", "Max length - 45");
        }
        if (passenger.getLastName().trim().equals(""))
            ValidationUtils.rejectIfEmpty(errors, "lastName", "NotEmpty", "Last name required");
        else
            if (passenger.getLastName().length() < 3 ||  passenger.getLastName().length() > 45) {
                errors.rejectValue("lastName", "Size.passengerForm.lastName", "Min length - 3, Max length - 45");
            }


        if (passenger.getBirthday() == null)
            errors.rejectValue("birthday", "NotEmpty", "Birthday cannot be empty");
        else {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -18); // to get previous year add -1
            Date yearForMature = cal.getTime();

            if (passenger.getBirthday().after(new Date()) || passenger.getBirthday().after(yearForMature))
                errors.rejectValue("birthday", "Immature", "You have to be older than 18");
        }
    }
}
