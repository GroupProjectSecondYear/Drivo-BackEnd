import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StaffWorkTimeComponent } from './staff-work-time.component';

describe('StaffWorkTimeComponent', () => {
  let component: StaffWorkTimeComponent;
  let fixture: ComponentFixture<StaffWorkTimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StaffWorkTimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StaffWorkTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
