import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StaffSalaryInformationListComponent } from './staff-salary-information-list.component';

describe('StaffSalaryInformationListComponent', () => {
  let component: StaffSalaryInformationListComponent;
  let fixture: ComponentFixture<StaffSalaryInformationListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StaffSalaryInformationListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StaffSalaryInformationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
