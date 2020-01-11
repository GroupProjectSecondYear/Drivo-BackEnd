import { Component, OnInit } from '@angular/core';
import { StudentServiceService } from '../../service/student/student-service.service';
import { HttpError } from '../../Shared/httpError/HttpError';
import { HttpErrorResponse } from '@angular/common/http';
import { StudentModel } from '../../ClassModel/StudentModel';
import { UserValidation } from '../../Shared/validation/user-validation/user-validation';
import Swal from 'sweetalert2';
import { ProfileImage } from '../profile-image/profile-image';
import { FileUploadServiceService } from '../../service/file-upload/file-upload-service.service';
import { API_URL } from '../../app.constants';



@Component({
  selector: 'app-student-profile',
  templateUrl: './student-profile.component.html',
  styleUrls: ['./student-profile.component.scss']
})
export class StudentProfileComponent implements OnInit {

 userId;
 studentName;

 apiUrl = API_URL;

 studentData:StudentModel;
 isStudentDataLoad=false;
 isPasswordChange=false;

 encryptedPassword;

 errorName;
 errorAddress;
 //errorNic;
 errorTel;
 errorEmail;
 errorPassword;

 errorMessage;

 //user Validation Instance
 userValidation = new UserValidation();

 selectedFiles;
 showSpinner=false;
 
  constructor(
    private studentService :StudentServiceService,
    private fileUploadService :FileUploadServiceService
  ) { }

  ngOnInit() {
    this.userId=sessionStorage.getItem('userId');
    this.getStudent();
  }

  getStudent(){
    this.studentService.getStudentData(this.userId).subscribe(
      response => {
        this.studentData=response;
        this.studentName=this.studentData.name;
        
        this.encryptedPassword=this.studentData.userId.password;
        this.studentData.userId.password="000000";

        this.isStudentDataLoad=true;
      },
      error => {
        console.log(error);
      }
    )
  }

  save(){
  
    this.errorName="";
    //this.errorNic="";
    this.errorTel="";
    this.errorAddress="";
    this.errorEmail="";
    this.errorPassword="";

    //validate name
    if(this.studentData.name === ""){
      this.errorName="Name is mandatory";
    }

    //validate NIC
    // if(this.studentData.nic === ""){
    //   this.errorNic="NIC number is mandatory ";
    // }else if( !this.userValidation.isValidNicNumber(this.studentData.nic) ){
    //   this.errorNic="Enter Valid NIC Number";
    // }

    //Valid Number
    if(this.studentData.tel===""){
      this.errorTel="Telephone number is mandatory";
    }else if( !this.userValidation.isValidTelNumber(this.studentData.tel) ){
      this.errorTel="Enter Valid Telephone Number ";
    }

    //valid address
    if( this.studentData.address === "" ){
      this.errorAddress="Address is mandatory ";
    }

    //valid email
    if( this.studentData.userId.email === "" ){
      this.errorEmail="Email is mandatory ";
    }else if( !this.userValidation.isValidEmail(this.studentData.userId.email) ){
       this.errorEmail="Enter Valid Email Address";
    }

    //password
    if( this.studentData.userId.password === ""){
      this.errorPassword="Password is mandatory";
    }
  

    if(this.errorName=="" && this.errorTel=="" && this.errorAddress=="" && this.errorEmail=="" && this.errorPassword==""){
       
      //check password change or not
      if( (this.studentData.userId.password == "000000") ){//password not Change
        this.studentData.userId.password=this.encryptedPassword;
      }
      
      this.showSpinner=true;
      this.studentService.studentUpdate(this.studentData).subscribe(
         response => {
          this.studentName=this.studentData.name;
          
          if(response==1){
            //register success
            Swal.fire({
              position: 'top-end',
              type: 'success',
              title: 'Update Successful.',
              showConfirmButton: false,
              timer: 1500
            });
          }
          if(response==2){
            this.errorMessage="Updated email already exist.";
            Swal.fire({
              position: 'center',
              type: 'error',
              title: 'Update not Successful.',
              showConfirmButton: false,
              timer: 1500
            });
          }
          this.getStudent();
          this.showSpinner=false;
         },
         error => {
          Swal.fire({
            position: 'center',
            type: 'error',
            title: 'Update not Successful.',
            showConfirmButton: false,
            timer: 1500
          });
          this.showSpinner=false;
         }
       );
    }
  }

  //upload user image
  selectFile(event) {
    this.showSpinner=true;
    this.selectedFiles = event.target.files;
    this.fileUploadService.fileUpload(this.selectedFiles.item(0),this.userId,1).subscribe(
      response => {
        if(response == 0){
          this.errorMessage="File size should be less than 9MB";
        }else if(response==1){
          window.location.reload();
          Swal.fire({
            position: 'center',
            type: 'success',
            title: 'Update Successful.',
            showConfirmButton: false,
            timer: 1500
          });
        }else{
          Swal.fire({
            position: 'center',
            type: 'error',
            title: 'Update not Successful.',
            showConfirmButton: false,
            timer: 1500
          });
        }
        this.showSpinner=false;
        this.selectedFiles = undefined;
      },
      error => {
        this.showSpinner=false;
        console.log(error);
      }
    );
   }

  closeError(){
    this.errorMessage="";
  }

  handleErrorResponse(error: HttpErrorResponse) {
    let httpError = new HttpError();
    httpError.ErrorResponse(error);
  };
}
