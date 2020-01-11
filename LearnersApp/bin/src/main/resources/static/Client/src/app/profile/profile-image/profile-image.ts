import { Component, OnInit } from '@angular/core';
import { FileUploadServiceService } from '../../service/file-upload/file-upload-service.service';
import { HttpClient } from '@angular/common/http';
import { prepareProfile } from 'selenium-webdriver/firefox';

export class ProfileImage{

  currentFileUpload: File;
  constructor(
    private uploadService :FileUploadServiceService
  ){}

  // upload(selectedFiles,userId) {   
  //   this.currentFileUpload = selectedFiles.item(0);
  //   this.uploadService.pushFileToStorage(this.currentFileUpload,userId).subscribe(
  //     response => {
  //         return response;
  //     },
  //     error => {
  //       console.log(error);
  //     }
  //   );  
  //   return -1;
  // }
  
}
