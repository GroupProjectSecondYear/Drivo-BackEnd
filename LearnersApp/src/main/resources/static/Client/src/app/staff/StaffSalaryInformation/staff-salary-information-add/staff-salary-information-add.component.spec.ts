import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StaffSalaryInformationAddComponent } from './staff-salary-information-add.component';

describe('StaffSalaryInformationAddComponent', () => {
  let component: StaffSalaryInformationAddComponent;
  let fixture: ComponentFixture<StaffSalaryInformationAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StaffSalaryInformationAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StaffSalaryInformationAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
