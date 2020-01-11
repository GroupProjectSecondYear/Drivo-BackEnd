import { Component, OnInit } from '@angular/core';
import { StudentModel } from '../../ClassModel/StudentModel';
import { StudentServiceService } from '../../service/student/student-service.service';
import { Router } from '@angular/router';
import { UserValidation } from '../../Shared/validation/user-validation/user-validation';

@Component({
  selector: 'app-student-payment-check',
  templateUrl: './student-payment-check.component.html',
  styleUrls: ['./student-payment-check.component.scss']
})
export class StudentPaymentCheckComponent implements OnInit {

  paymentNotCompletedStudent:StudentModel[]=[];
  filteredStudent:StudentModel[]=[];
  validation = new UserValidation();
   
   //Filter Option Implement
    private _searchTerm:string;
    get searchTerm(): string{
      return this._searchTerm;
    }
    set searchTerm(value:string){
      this._searchTerm=value;
      this.filteredStudent = this.filterStudent(value);
    }
  
    filterStudent(searchString:string){
       if(this.validation.isDigitContain(searchString)){
        return this.paymentNotCompletedStudent.filter(student => 
          student.nic.toLocaleLowerCase().indexOf(searchString.toLocaleLowerCase()) !== -1) ;
       }
       return this.paymentNotCompletedStudent.filter(student => 
          student.name.toLocaleLowerCase().indexOf(searchString.toLocaleLowerCase()) !== -1) ;
    }
    //Finish filter option implementation

  constructor(
    private studentService : StudentServiceService,
    private router :Router
  ) { }

  ngOnInit() {
    this.getpaymentNotCompleteStudent();
  }

  getpaymentNotCompleteStudent(){
    this.studentService.getpaymentNotCompleteStudent().subscribe(
      response => {
        this.paymentNotCompletedStudent=response;
        this.filteredStudent=this.paymentNotCompletedStudent;
      },
      error => {
        console.log(error);
      }
    );
  }

  addPayment(studentId){
    this.router.navigate(['student-payment',studentId]);
  }

}
