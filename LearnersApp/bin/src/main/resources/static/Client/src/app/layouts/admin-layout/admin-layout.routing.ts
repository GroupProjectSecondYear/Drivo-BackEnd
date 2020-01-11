import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { StaffListComponent } from '../../staff/staff-list/staff-list.component';
import { PackageListComponent } from '../../package/package-list/package-list.component';
import { ReportListComponent } from '../../report/report-list/report-list.component';
import { VehicleListComponent } from '../../vehicle/vehicle-list/vehicle-list.component';
import { StudentListComponent } from '../../student/student-list/student-list.component';
import { StudentAddComponent } from '../../student/student-add/student-add.component';
import { StudentPackageAddComponent } from '../../student/student-package-add/student-package-add.component';
import { StudentPaymentComponent } from '../../student/student-payment/student-payment.component';
import { StudentMoreDetailsComponent } from '../../student/student-more-details/student-more-details.component';
import { AdminStaffStudentDashBoardComponent } from '../../adminStaff/admin-staff-student-dash-board/admin-staff-student-dash-board.component';
import { StudentExamResultAddComponent } from '../../student/student-exam-result-add/student-exam-result-add.component';
import { TimeTableComponent } from '../../timeTable/time-table/time-table.component';
import { LessonAddComponent } from '../../timeTable/lesson-add/lesson-add.component';
import { TimeSlotComponent } from '../../timeTable/time-slot/time-slot.component';
import { PathMapComponent } from '../../timeTable/path-map/path-map.component';
import { LessonUpdateComponent } from '../../timeTable/lesson-update/lesson-update.component';
import { StudentProfileComponent } from '../../profile/student-profile/student-profile.component';
import { TrialLessonBookComponent } from '../../LessonBooking/trial-lesson-book/trial-lesson-book.component';
import { TrialLessonListComponent } from '../../LessonBooking/trial-lesson-list/trial-lesson-list.component';
import { PackageAnalysisComponent } from '../../timeTable/package-analysis/package-analysis.component';
import { InstructorTimeTableComponent } from '../../instructor/instructor-time-table/instructor-time-table.component';
import { LessonAssignStudentComponent } from '../../instructor/lesson-assign-student/lesson-assign-student.component';
import { PractricalLessonChartStudentComponent } from '../../instructor/practrical-lesson-chart-student/practrical-lesson-chart-student.component';
import { StudentPaymentCheckComponent } from '../../student/student-payment-check/student-payment-check.component';
import { TrialLessonDayFeedbackComponent } from '../../LessonBooking/trial-lesson-day-feedback/trial-lesson-day-feedback.component';
import { TrialLessonDayFeedbackChartComponent } from '../../LessonBooking/trial-lesson-day-feedback-chart/trial-lesson-day-feedback-chart.component';
import { StaffSalaryComponent } from '../../staff/staff-salary/staff-salary.component';
import { StudentDeactivateComponent } from '../../student/student-deactivate/student-deactivate.component';
import { RouteGuardServiceService } from '../../service/RouteGurd/route-guard-service.service';
import { PackageAddComponent } from '../../package/package-add/package-add.component';
import { StaffSalaryInformationListComponent } from '../../staff/StaffSalaryInformation/staff-salary-information-list/staff-salary-information-list.component';
import { StaffSalaryInformationAddComponent } from '../../staff/StaffSalaryInformation/staff-salary-information-add/staff-salary-information-add.component';
import { StaffWorkTimeComponent } from '../../staff/StaffWorkTime/staff-work-time/staff-work-time.component';




export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard',  component: DashboardComponent, canActivate:[RouteGuardServiceService]},
    
    {path:'staff-list', component:StaffListComponent, canActivate:[RouteGuardServiceService]},
    {path:'staff-salary', component:StaffSalaryComponent, canActivate:[RouteGuardServiceService]},
    {path:'staff-salary-information-list', component:StaffSalaryInformationListComponent, canActivate:[RouteGuardServiceService]},
    {path:'staff-salary-information-add/:type/:id', component:StaffSalaryInformationAddComponent, canActivate:[RouteGuardServiceService]},
    {path:'staff-work-time', component:StaffWorkTimeComponent, canActivate:[RouteGuardServiceService]},

    {path:'package-list', component:PackageListComponent, canActivate:[RouteGuardServiceService]},
    {path:'package-add', component:PackageAddComponent, canActivate:[RouteGuardServiceService]},

    {path:'report-list', component:ReportListComponent, canActivate:[RouteGuardServiceService]},
    {path:'vehicle-list', component:VehicleListComponent, canActivate:[RouteGuardServiceService]},
   
    {path:'student-list', component:StudentListComponent, canActivate:[RouteGuardServiceService]},
    {path:'student-add', component:StudentAddComponent, canActivate:[RouteGuardServiceService]},
    {path:'student-package-add/:id/:name', component:StudentPackageAddComponent, canActivate:[RouteGuardServiceService]},
    {path:'student-payment/:id/:name', component:StudentPaymentComponent, canActivate:[RouteGuardServiceService]},
    {path:'student-more-details/:id', component:StudentMoreDetailsComponent, canActivate:[RouteGuardServiceService]},
    {path:'admin-staff-student-dash-board', component:AdminStaffStudentDashBoardComponent, canActivate:[RouteGuardServiceService]},
    {path:'student-exam-result-add', component:StudentExamResultAddComponent, canActivate:[RouteGuardServiceService]},
    {path:'student-payment-check', component:StudentPaymentCheckComponent, canActivate:[RouteGuardServiceService]},
    {path:'student-deactivate',  component:StudentDeactivateComponent, canActivate:[RouteGuardServiceService]},
    
    {path:'time-table', component:TimeTableComponent, canActivate:[RouteGuardServiceService]},
    {path:'lesson-add', component:LessonAddComponent, canActivate:[RouteGuardServiceService]},
    {path:'lesson-update/:id/:type', component:LessonUpdateComponent, canActivate:[RouteGuardServiceService]},
    {path:'time-slot', component:TimeSlotComponent, canActivate:[RouteGuardServiceService]},
    {path:'path-map', component:PathMapComponent, canActivate:[RouteGuardServiceService]},
    {path:'package-analysis', component:PackageAnalysisComponent, canActivate:[RouteGuardServiceService]},
   

    {path:'trial-lesson-book', component:TrialLessonBookComponent, canActivate:[RouteGuardServiceService]},
    {path:'trial-lesson-list/:package/:title', component:TrialLessonListComponent, canActivate:[RouteGuardServiceService]},
    {path:'trial-lesson-day-feedback/:userId/:packageId/:packageTitle', component:TrialLessonDayFeedbackComponent, canActivate:[RouteGuardServiceService]},
    {path:'trial-lesson-day-feedback-chart',  component:TrialLessonDayFeedbackChartComponent, canActivate:[RouteGuardServiceService]},

    {path:'student-profile' , component:StudentProfileComponent, canActivate:[RouteGuardServiceService]},

    {path:'instructor-time-table', component:InstructorTimeTableComponent, canActivate:[RouteGuardServiceService]},
    {path:'lesson-assign-student/:lessonId/:day/:timeSlot', component:LessonAssignStudentComponent, canActivate:[RouteGuardServiceService]},
    {path:'practrical-lesson-chart-student', component:PractricalLessonChartStudentComponent, canActivate:[RouteGuardServiceService]}


];
