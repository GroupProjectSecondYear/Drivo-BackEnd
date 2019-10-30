import { Time } from '@angular/common';


export class PaymentValidation {

    regExp:any;
    test:Boolean

    constructor() { }

    isValidCash(payment){
        if(payment != null){
          this.regExp = new RegExp('^\\\d+(\\\.\\\d{1,2})?$');
          let test = this.regExp.test(payment);
          if(test){
            if(payment>0){
                return true;
            }
          }
          
        }
        return false;
    }
  }
  