package net.tsystems.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//@Component
public class TripValidator implements Validator {

    public boolean supports(Class<?> aClass) {
        return false;
    }

    //TODO
    public void validate(Object o, Errors errors) {
        /*if (trip.getTrain() != null && trainService.getTrainById(trip.getTrain()) == null){
            result.rejectValue("number", "NotNull", "Train number is mandatory");
        }*/
    }
}
