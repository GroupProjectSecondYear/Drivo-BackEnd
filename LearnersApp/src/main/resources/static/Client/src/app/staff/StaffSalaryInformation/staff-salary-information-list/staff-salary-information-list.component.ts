import { Component, OnInit } from '@angular/core';
import { StaffServiceService } from '../../../service/StaffService/staff-service.service';
import { SalaryInformationModel } from '../../../ClassModel/SalaryInformationModel';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpError } from '../../../Shared/httpError/HttpError';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';



@Component({
  selector: 'app-staff-salary-information-list',
  templateUrl: './staff-salary-information-list.component.html',
  styleUrls: ['./staff-salary-information-list.component.scss']
})
export class StaffSalaryInformationListComponent implements OnInit {

  staffSalaryInformationList:SalaryInformationModel[]=[];

  constructor(
    private staffService:StaffServiceService,
    private router :Router
  ) { }

  ngOnInit() {
    this.getStaffSalaryInformation();
  }

  getStaffSalaryInformation(){
    this.staffService.getStaffSalaryInformationList().subscribe(
      response => {
        this.staffSalaryInformationList=response;
      },
      error => {
        console.log(error);
        this.handleErrorResponse(error);
      }
    );
  }

  deleteStaffSalary(salaryInformationId){
    this.staffService.deleteStaffSalaryInfromation(salaryInformationId).subscribe(
      response => {
        this.getStaffSalaryInformation();
        Swal.fire({
          position: 'center',
          type: 'success',
          title: 'Delete  Successfulled.',
          showConfirmButton: false,
          timer: 3000
        });
      },
      error => {
        Swal.fire({
          position: 'center',
          type: 'error',
          title: 'Delete not successfulled.',
          showConfirmButton: false,
          timer: 3000
        });
        console.log(error);
        this.handleErrorResponse(error);
      }
    );
  }

  /*
  type 1--> Add new StaffSalary Information
       2--> Update 
  */
  staffSalary(type,id){
    this.router.navigate(['staff-salary-information-add/',type,id]);
  }

  handleErrorResponse(error: HttpErrorResponse) {
    let httpError = new HttpError();
    httpError.ErrorResponse(error);
  }

}
