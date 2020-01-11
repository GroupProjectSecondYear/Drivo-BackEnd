import { Component, OnInit } from '@angular/core';
import { VehicleCategoryModel } from '../../ClassModel/MapObject/VehicleCategory';
import { PackageServiceService } from '../../service/package/package-service.service';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpError } from '../../Shared/httpError/HttpError';

@Component({
  selector: 'app-package-add',
  templateUrl: './package-add.component.html',
  styleUrls: ['./package-add.component.scss']
})
export class PackageAddComponent implements OnInit {

  vehicleCategoryListData:VehicleCategoryModel[];
  
  
  title;
  vehicleCategory:VehicleCategoryModel;
  manualLesson;
  autoLesson;
  price;
  studentBasicPayment

  transmission='';
  errorTransmission='';
  selectManualTransmission=false;
  selectAutoTransmission=false;
  isErrorTransmission=false;
  isTransmissionSelect=false;
  isDisableAutoLesson=true;
  isDisableManualLesson=true;

  constructor(
    private packageService :PackageServiceService
  ) { }

  ngOnInit() {
    this.getVehicleCategoryList();
  }

  getVehicleCategoryList(){
    this.packageService.getVehicleCategoryList().subscribe(
      response => {
        this.vehicleCategoryListData=response;
      },
      error => {
        console.log(error);
      }
    );
  }

  showTransmission(vehicleCategory:VehicleCategoryModel){
    this.isTransmissionSelect=false;
    this.isErrorTransmission=false;
    this.selectManualTransmission=false;
    this.selectAutoTransmission=false;
    this.isDisableManualLesson=true;
    this.isDisableAutoLesson=true;
    this.transmission='';
    this.packageService.getVehcleCategoryTransmission(vehicleCategory.vehicleCategoryId).subscribe(
      response => {
        if(response==3){//Has both manual/auto vehcles
          this.isTransmissionSelect=true;
          this.selectManualTransmission=true;
          this.isDisableManualLesson=false;
        }else if(response==1){
          this.transmission="Manual";
          this.selectManualTransmission=true;
          this.isDisableManualLesson=false;
        }else if(response==2){
          this.transmission="Auto"
          this.selectAutoTransmission=true;
          this.isDisableAutoLesson=false;
        }else{//response is 0
          this.isErrorTransmission=true;
          this.errorTransmission="There is no any vehicle in this vehicle category.";
        }
      },
      error => {
        console.log(error);
        this.handleErrorResponse(error);
      }
    );
  }

  selectTransmission(transmission:Number){
    if(transmission==1){
      (this.selectManualTransmission ? this.selectManualTransmission=false : this.selectManualTransmission=true); 
      (this.selectManualTransmission ? this.isDisableManualLesson=false : this.isDisableManualLesson=true);
    }
    if(transmission==2){
      (this.selectAutoTransmission ? this.selectAutoTransmission=false : this.selectAutoTransmission=true);
      (this.selectAutoTransmission ? this.isDisableAutoLesson=false : this.isDisableAutoLesson=true);
    }
  }

  registerPackage(){
    
  }

  private handleErrorResponse(error: HttpErrorResponse) {
    let httpError = new HttpError();
    httpError.ErrorResponse(error);
  }


}
