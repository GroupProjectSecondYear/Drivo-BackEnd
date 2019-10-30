import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PackageModel } from '../../ClassModel/PackageModel';
import { API_URL } from '../../app.constants';
import { VehicleCategoryModel } from '../../ClassModel/MapObject/VehicleCategory';



@Injectable({
  providedIn: 'root'
})
export class PackageServiceService {

  constructor(
    private http:HttpClient
  ) { }

  public packageList(){
    return this.http.get<PackageModel[]>(`${API_URL}/packages`);
  }

  getOrderpackageList(lessonId){
    return this.http.get<PackageModel[]>(`${API_URL}/ /${lessonId}`);
  }

  getNumStudentPackage(packageId,transmissionType){
    return this.http.get<Number>(`${API_URL}/package/student/${packageId}/${transmissionType}`);
  }

  getVehicleCategoryList(){
    return this.http.get<VehicleCategoryModel[]>(`${API_URL}/package/vehicle/category`);
  }

  getVehcleCategoryTransmission(vehicleCategoryId){
    return this.http.get<Number>(`${API_URL}/package/vehicle/transmission/${vehicleCategoryId}`);
  }
}
