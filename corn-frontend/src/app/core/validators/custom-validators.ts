import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export class CustomValidators {

    static notWhitespace(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            if(control.value === null) {
                return null;
            }
            const isNotWhitespace = (control.value || '').trim().length > 0;
            return isNotWhitespace ? null : {'notWhitespace': true};
        }
    }
}
