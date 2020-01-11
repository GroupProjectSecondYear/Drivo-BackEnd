import { Component, Input } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';



@Component({
  selector: 'app-vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.scss']
})
export class VehicleListComponent {

 
  public gradientStroke;
  public chartColor;
  public canvas : any;
  public ctx;

  

  public gradientChartOptionsConfiguration: any;

 


  constructor() { }

  ngOnInit() {}
  
}

