import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { UserModel } from '../../ClassModel/UserModel';
import { API_URL } from '../../app.constants';


@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(
    private http:HttpClient
  ) { }
  

  userRegister(user:UserModel){
      return this.http.post<UserModel>(`${API_URL}/user/register`,user);
  }

  userDelete(userId:Number){
    return this.http.delete<any>(`${API_URL}/user/${userId}`);
  }

  getUser(email){
    return this.http.get<UserModel>(`${API_URL}/user/details/${email}`);
  }

}
