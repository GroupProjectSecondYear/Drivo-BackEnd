import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StaffRole } from '../../../ClassModel/MapObject/StaffRole';
import { StaffServiceService } from '../../../service/StaffService/staff-service.service';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpError } from '../../../Shared/httpError/HttpError';
import { PaymentValidation } from '../../../Shared/validation/payment-validation/payment-validation';
import { SalaryInformationModel } from '../../../ClassModel/SalaryInformationModel';
import { AdminModel } from '../../../ClassModel/AdminModel';
import Swal from 'sweetalert2';
import { UserModel } from '../../../ClassModel/UserModel';


@Component({
  selector: 'app-staff-salary-information-add',
  templateUrl: './staff-salary-information-add.component.html',
  styleUrls: ['./staff-salary-information-add.component.scss']
})
export class StaffSalaryInformationAddComponent implements OnInit {

  type:Number;//type 1-->Add ,2-->Update
  salaryInformationId:Number;

  staffRoleList:StaffRole[]=[];
  staffSalaryInformationList:SalaryInformationModel[]=[];
  salaryInformation:SalaryInformationModel;
  selectedRoleName;

  selectRoleId:Number;
  fullDayaSalary:Number;
  halfDayaSalary:Number
  noPay:Number;

  errorSelectRoleId:String;
  errorFullDayaSalary:String;
  errorHalfDayaSalary:String;
  errorNoPay:String;

  paymentValidation = new PaymentValidation();

  constructor(
    private route :ActivatedRoute,
    private staffService :StaffServiceService,
    private router :Router
  ) { }

  ngOnInit() {
    this.type=this.route.snapshot.params['type'];//get student id by url
    this.salaryInformationId=this.route.snapshot.params['id'];//get student id by url
    if(this.type==1){
      this.getStaffRole();
    }else{
      this.getStaffSalaryInformation(this.salaryInformationId);
    }
  }

  getStaffRole(){
    let role1 = new StaffRole(2,"Administrative Staff Student");
    let role2 = new StaffRole(3,"Administrative Staff Instructor");
    let role3 = new StaffRole(4,"Instructor");

    this.staffRoleList.push(role1);
    this.staffRoleList.push(role2);
    this.staffRoleList.push(role3);
  }

  getStaffSalaryInformation(salaryInformationId){
    this.staffService.getStaffSalaryInformation(salaryInformationId).subscribe(
      response => {
        this.salaryInformation = response;
        
        this.selectRoleId=this.salaryInformation.staffType;
        if(this.selectRoleId==2){
          this.selectedRoleName="Administrative Staff Student";
        }else if(this.selectRoleId==3){
          this.selectedRoleName="Administrative Staff Instructor";
        }else{//role==4
          this.selectedRoleName="Instructor";
        }

        this.fullDayaSalary=this.salaryInformation.fullDaySalary;
        this.halfDayaSalary=this.salaryInformation.halfDaySalary;
        this.noPay=this.salaryInformation.nopay;
      },
      error => {
        console.log(error);
        this.handleErrorResponse(error);
      }
    );
  }

  registerStaffSalaryInformation(){
   
    this.errorSelectRoleId="";
    this.errorFullDayaSalary="";
    this.errorHalfDayaSalary="";
    this.errorNoPay="";

    let isError=false;

    if(this.selectRoleId==null){
      this.errorSelectRoleId="Must select the staff role";
      isError=true;
    }
    if(this.fullDayaSalary==null || this.fullDayaSalary<0  || !this.paymentValidation.isValidCash(this.fullDayaSalary)){
      this.errorFullDayaSalary="Insert Valid Salary";
      isError=true;
    }
    if(this.halfDayaSalary==null || this.halfDayaSalary<0  || !this.paymentValidation.isValidCash(this.halfDayaSalary)){
      this.errorHalfDayaSalary="Insert Valid Salary";
      isError=true;
    }
    if(this.noPay==null || this.noPay<0  || !this.paymentValidation.isValidCash(this.noPay)){
      this.errorNoPay="Insert Valid Nopay";
      isError=true;
    }

    if(!isError){
      let salaryInformation = new SalaryInformationModel(0,this.selectRoleId,this.fullDayaSalary,this.halfDayaSalary,this.noPay,null);
      
      if(this.type==1){
        let adminId = new AdminModel(0,"",new UserModel(+sessionStorage.getItem("userId"),"","",new Date(),1,1,0));
        salaryInformation.adminId=adminId;
        this.staffService.addStaffSalaryInformation(salaryInformation).subscribe(
          response => {
            Swal.fire({
              position: 'top-end',
              type: 'success',
              title: 'Save Successfulled.',
              showConfirmButton: false,
              timer: 2000
            });
            this.router.navigate(['staff-salary-information-list']);
          },
          error => {
            Swal.fire({
              position: 'center',
              type: 'error',
              title: 'Save not successfulled.',
              showConfirmButton: false,
              timer: 3000
            });
          }
        );
      }
      if(this.type==2){
        salaryInformation.adminId=this.salaryInformation.adminId;
        salaryInformation.salaryInformationId=this.salaryInformation.salaryInformationId;
        this.staffService.updateStaffSalaryInformation(salaryInformation).subscribe(
          response => {
            Swal.fire({
              position: 'top-end',
              type: 'success',
              title: 'Update successfulled.',
              showConfirmButton: false,
              timer: 2000
            });
            this.router.navigate(['staff-salary-information-list']);
          },
          error => {
            Swal.fire({
              position: 'top-end',
              type: 'error',
              title: 'Update not successfulled.',
              showConfirmButton: false,
              timer: 2500
            });
          }
        );
      }
    }
  }

  handleErrorResponse(error: HttpErrorResponse) {
    let httpError = new HttpError();
    httpError.ErrorResponse(error);
  }

}
