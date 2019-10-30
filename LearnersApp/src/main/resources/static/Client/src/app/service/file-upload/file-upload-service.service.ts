import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL } from '../../app.constants';

@Injectable({
  providedIn: 'root'
})
export class FileUploadServiceService {

  constructor(
    private http :HttpClient
  ) { }

  /*
  type--> 1 = userProfileImage
          2 = PDF
  */
  fileUpload(file: File,userId:Number,type:number){    
    let formdata: FormData = new FormData();
    formdata.append('file', file);
    return this.http.post<Number>(`${API_URL}/file/upload/${userId}/${type}`,formdata);
  }

  // downLoadProfilePicture(userId){
  //   return this.http.get<any>(`${API_URL}/api/file/${userId}`);
  // }

  // getFiles(): Observable<any> {
  //   return this.http.get(`${API_URL}/api/file/all`);
  // }
}
