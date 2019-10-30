import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserModel } from '../ClassModel/UserModel';
import { API_URL } from '../app.constants';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';



@Injectable({
  providedIn: 'root'
})
export class UserAuthenticationServiceService {


  constructor(
    private http:HttpClient,
    private router:Router
  ) { }

  // authenticate(email,password){
  //   return this.http.get<UserModel>(`${API_URL}/login/${email}`);
  // }


  //username --> email
  executeJWTAuthenticationService(username,password){
    return this.http.post<any>(
      `${API_URL}/authenticate`,{
        username,
        password
      }).pipe(
        map(
          data => {
            sessionStorage.setItem('AUTHENTICATED_USER', username);
            sessionStorage.setItem('TOKEN', `Bearer ${data.token}`);
            return data;
          }
        )
      );
  }

  getAuthenticatedUser() {
    return sessionStorage.getItem('AUTHENTICATED_USER');
  }

  getAuthenticatedToken() {
    if(this.getAuthenticatedUser())
      return sessionStorage.getItem('TOKEN');
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('AUTHENTICATED_USER');
    return !(user === null)
  }

  logout(){
    sessionStorage.removeItem('AUTHENTICATED_USER');
    sessionStorage.removeItem('TOKEN');
    sessionStorage.removeItem('userId');
    sessionStorage.removeItem('userRole');
  }
}
